package com.jpmsoft.videoaccess.adapter.wvp;

import com.fasterxml.jackson.databind.JsonNode;
import com.jpmsoft.videoaccess.api.ApiException;
import com.jpmsoft.videoaccess.config.WvpProperties;
import com.jpmsoft.videoaccess.dto.VideoRequests.*;
import com.jpmsoft.videoaccess.model.VideoModels.*;
import com.jpmsoft.videoaccess.port.VideoPorts.VideoGateway;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.*;

/** Vendor transport only. Identity, camera binding and session ownership remain business concerns. */
public final class WvpVideoGateway implements VideoGateway {
    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss").withResolverStyle(ResolverStyle.STRICT);
    private final WvpHttpClient client;
    private final URI mediaBase;
    private final ZoneId zone;
    private final String apiKey;

    public WvpVideoGateway(WvpHttpClient client, WvpProperties properties) {
        this.client = client;
        this.mediaBase = WvpHttpClient.endpoint(properties.getMediaBaseUrl(), "browser-facing media base URL");
        this.zone = ZoneId.of(properties.getZoneId());
        this.apiKey = properties.getApiKey();
        String host = mediaBase.getHost();
        boolean explicitIpTest = properties.isAllowInsecureMediaForTest() && ipv4Literal(host);
        if ("http".equals(mediaBase.getScheme()) && !explicitIpTest && !("127.0.0.1".equals(host) || "localhost".equals(host) || "[::1]".equals(host) || "::1".equals(host))) {
            throw new IllegalArgumentException("Browser media requires HTTPS, except for explicit loopback tests");
        }
    }

    private static boolean ipv4Literal(String host) {
        if (host == null || !host.matches("[0-9]{1,3}(\\.[0-9]{1,3}){3}")) return false;
        for (String octet : host.split("\\.")) if (Integer.parseInt(octet) > 255) return false;
        return true;
    }

    @Override public StreamInfo preview(Camera camera, Preview request) {
        binding(camera);
        // One fixed profile per channel. Preview never mutates WVP's persistent channel selection.
        // MAIN remains the old alias for DEFAULT; it does not promise physical main-stream output.
        if (request == null || !(request.getStreamType() == StreamType.DEFAULT || request.getStreamType() == StreamType.MAIN)) {
            throw new ApiException(501, "WVP_STREAM_TYPE_UNSUPPORTED", "首期统一使用通道默认档位，不支持按次请求切换子码流");
        }
        return stream(client.data(path("play", "start", camera), Collections.emptyMap()));
    }

    @Override public void ptz(Camera camera, Ptz request) {
        binding(camera);
        if (request == null || request.getAction() == null) throw invalidArgument();
        Map<String, String> query = new LinkedHashMap<>();
        String command;
        int movement = 0, zoom = 0;
        if (request.getAction() == PtzAction.STOP) {
            command = "stop";
        } else {
            BigDecimal speed = request.getSpeed();
            if (request.getCommand() == null || speed == null || speed.signum() < 0 || speed.compareTo(BigDecimal.ONE) > 0) throw invalidArgument();
            command = command(request.getCommand());
            movement = speed.multiply(BigDecimal.valueOf(255)).setScale(0, RoundingMode.HALF_UP).intValueExact();
            zoom = speed.multiply(BigDecimal.valueOf(15)).setScale(0, RoundingMode.HALF_UP).intValueExact();
        }
        query.put("command", command);
        query.put("horizonSpeed", String.valueOf(movement));
        query.put("verticalSpeed", String.valueOf(movement));
        query.put("zoomSpeed", String.valueOf(zoom));
        client.command(path("front-end", "ptz", camera), query);
    }

    @Override public List<RecordingSegment> recordings(Camera camera, TimeRange request) {
        binding(camera);
        Map<String, String> query = range(request);
        JsonNode data = client.data(path("gb_record", "query", camera), query);
        matchIfPresent(data, "deviceId", camera.deviceId);
        matchIfPresent(data, "channelId", camera.channelId);
        JsonNode rows = data.get("recordList"), total = data.get("sumNum");
        if (rows == null || !rows.isArray() || total == null || !total.isIntegralNumber()
                || !total.canConvertToInt() || total.intValue() < 0 || total.intValue() != rows.size()) {
            throw WvpHttpClient.invalidResponse();
        }
        List<RecordingSegment> result = new ArrayList<>();
        for (JsonNode row : rows) {
            OffsetDateTime start = recordedTime(text(row, "startTime")), end = recordedTime(text(row, "endTime"));
            if (!end.isAfter(start) || !end.isAfter(request.getStartTime()) || !start.isBefore(request.getEndTime())) throw WvpHttpClient.invalidResponse();
            String id = recordId(camera, start, end);
            result.add(new RecordingSegment(id, start, end));
        }
        return result;
    }

    @Override public StreamInfo startPlayback(Camera camera, TimeRange request) {
        binding(camera);
        return stream(client.data(path("playback", "start", camera), range(request)));
    }

    /** Internal transport methods only: the public controller must verify user/camera/session ownership first. */
    @Override public void pausePlayback(String upstreamStream) { control("pause", upstreamStream); }
    @Override public void resumePlayback(String upstreamStream) { control("resume", upstreamStream); }
    public void seekPlayback(String upstreamStream, long offsetSeconds) {
        segment(upstreamStream);
        if (offsetSeconds < 0) throw invalidArgument();
        client.command(new String[]{"api", "playback", "seek", upstreamStream, String.valueOf(offsetSeconds)}, Collections.emptyMap());
    }
    @Override public void stopPlayback(Camera camera, String upstreamStream) {
        binding(camera); segment(upstreamStream);
        client.command(new String[]{"api", "playback", "stop", camera.deviceId, camera.channelId, upstreamStream}, Collections.emptyMap());
    }
    @Override public void cancelPlaybackStart(Camera camera, TimeRange request) {
        // Pinned 569b361 PlayServiceImpl: playback stream IDs are deterministic by binding and range.
        Map<String, String> times = range(request);
        String start = times.get("startTime").replace("-", "").replace(":", "").replace(" ", "");
        String end = times.get("endTime").replace("-", "").replace(":", "").replace(" ", "");
        stopPlayback(camera, camera.deviceId + "_" + camera.channelId + "_" + start + "_" + end);
    }
    private void control(String action, String upstreamStream) {
        segment(upstreamStream);
        client.command(new String[]{"api", "playback", action, upstreamStream}, Collections.emptyMap());
    }

    private StreamInfo stream(JsonNode data) {
        String app = text(data, "app"), id = text(data, "stream");
        if (!safeSegment(app) || !safeSegment(id)) throw WvpHttpClient.invalidResponse();
        String url = optionalText(data, "https".equals(mediaBase.getScheme()) ? "https_hls" : "hls");
        if (url == null && "https".equals(mediaBase.getScheme())) url = optionalText(data, "hls");
        try {
            URI uri = URI.create(url == null ? "" : url);
            String expectedPath = mediaBase.getPath() + "/" + app + "/" + id + "/hls.m3u8";
            if (!Objects.equals(mediaBase.getScheme(), uri.getScheme()) || uri.getHost() == null
                    || !mediaBase.getHost().equalsIgnoreCase(uri.getHost()) || port(mediaBase) != port(uri)
                    || uri.getUserInfo() != null || uri.getFragment() != null || !expectedPath.equals(uri.getRawPath())
                    || uri.toString().length() > 4096 || uri.toString().contains(apiKey)
                    || URLDecoder.decode(uri.toString(), "UTF-8").contains(apiKey)) throw new IllegalArgumentException();
            Set<String> keys = new HashSet<>();
            if (uri.getRawQuery() != null) {
                for (String pair : uri.getRawQuery().split("&", -1)) {
                    String[] parts = pair.split("=", 2);
                    if (parts.length != 2 || !Arrays.asList("callId", "originTypeStr").contains(parts[0])
                            || !keys.add(parts[0]) || parts[1].isEmpty()
                            || URLDecoder.decode(parts[1], "UTF-8").chars().anyMatch(c -> c < 32 || c == 127)) throw new IllegalArgumentException();
                }
            }
            // WVP supplies no reliable expiry here. Never invent one, or return all upstream metadata.
            return new StreamInfo(id, "HLS", uri.toString(), null);
        } catch (java.io.UnsupportedEncodingException | IllegalArgumentException exception) {
            throw new ApiException(502, "WVP_MEDIA_URL_INVALID", "WVP 播放地址不符合已配置的媒体域名、路径或参数约定");
        }
    }

    private Map<String, String> range(TimeRange request) {
        if (request == null || request.getStartTime() == null || request.getEndTime() == null
                || !request.getEndTime().isAfter(request.getStartTime())
                || request.getStartTime().getNano() != 0 || request.getEndTime().getNano() != 0) throw invalidArgument();
        LocalDateTime start = request.getStartTime().atZoneSameInstant(zone).toLocalDateTime();
        LocalDateTime end = request.getEndTime().atZoneSameInstant(zone).toLocalDateTime();
        if (zone.getRules().getValidOffsets(start).size() != 1 || zone.getRules().getValidOffsets(end).size() != 1) throw invalidArgument();
        Map<String, String> result = new LinkedHashMap<>();
        result.put("startTime", TIME.format(start)); result.put("endTime", TIME.format(end));
        return result;
    }
    private OffsetDateTime recordedTime(String value) {
        try {
            LocalDateTime local = LocalDateTime.parse(value, TIME);
            if (zone.getRules().getValidOffsets(local).size() != 1) throw WvpHttpClient.invalidResponse();
            return local.atZone(zone).toOffsetDateTime();
        } catch (DateTimeParseException exception) { throw WvpHttpClient.invalidResponse(); }
    }
    private String recordId(Camera camera, OffsetDateTime start, OffsetDateTime end) {
        try {
            byte[] bytes = MessageDigest.getInstance("SHA-256").digest((camera.deviceId + "|" + camera.channelId + "|" + start.toInstant() + "|" + end.toInstant()).getBytes(StandardCharsets.UTF_8));
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < 16; i++) result.append(String.format(Locale.ROOT, "%02x", bytes[i] & 255));
            return result.toString();
        } catch (NoSuchAlgorithmException exception) { throw new IllegalStateException("SHA-256 unavailable"); }
    }
    private void matchIfPresent(JsonNode data, String field, String expected) {
        if (data.hasNonNull(field) && !expected.equals(text(data, field))) throw WvpHttpClient.invalidResponse();
    }
    private String text(JsonNode node, String key) {
        String result = optionalText(node, key);
        if (result == null || result.isEmpty()) throw WvpHttpClient.invalidResponse();
        return result;
    }
    private String optionalText(JsonNode node, String key) {
        JsonNode value = node.get(key);
        if (value == null || value.isNull()) return null;
        if (!value.isTextual()) throw WvpHttpClient.invalidResponse();
        return value.textValue().isEmpty() ? null : value.textValue();
    }
    private String[] path(String resource, String action, Camera camera) { return new String[]{"api", resource, action, camera.deviceId, camera.channelId}; }
    private static int port(URI uri) { return uri.getPort() != -1 ? uri.getPort() : "https".equals(uri.getScheme()) ? 443 : 80; }
    private void binding(Camera camera) {
        if (camera == null || !camera.isBound()) throw new ApiException(409, "CAMERA_UNBOUND", "摄像头尚未绑定国标设备和通道");
        if (!camera.deviceId.matches("[0-9]{20}") || !camera.channelId.matches("[0-9]{20}")) throw new ApiException(409, "CAMERA_BINDING_INVALID", "已配置的国标设备和通道编号不符合约定");
    }
    private void segment(String value) { if (!safeSegment(value)) throw invalidArgument(); }
    private boolean safeSegment(String value) { return value != null && value.matches("[A-Za-z0-9_-][A-Za-z0-9_.-]{0,127}") && !value.equals(".") && !value.equals(".."); }
    private ApiException invalidArgument() { return new ApiException(400, "INVALID_ARGUMENT", "请检查控制参数或秒精度的时间范围"); }
    private String command(PtzCommand value) {
        switch (value) {
            case UP: return "up"; case DOWN: return "down"; case LEFT: return "left"; case RIGHT: return "right";
            case UP_LEFT: return "upleft"; case UP_RIGHT: return "upright"; case DOWN_LEFT: return "downleft"; case DOWN_RIGHT: return "downright";
            case ZOOM_IN: return "zoomin"; case ZOOM_OUT: return "zoomout"; default: throw invalidArgument();
        }
    }
}
