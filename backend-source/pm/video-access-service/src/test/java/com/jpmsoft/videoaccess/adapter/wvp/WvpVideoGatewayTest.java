package com.jpmsoft.videoaccess.adapter.wvp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmsoft.videoaccess.api.ApiException;
import com.jpmsoft.videoaccess.config.WvpProperties;
import com.jpmsoft.videoaccess.dto.VideoRequests.*;
import com.jpmsoft.videoaccess.model.VideoModels.*;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import static org.assertj.core.api.Assertions.*;

/** Real random-port loopback HTTP transport. All credentials, IDs and video URLs are fixtures. */
class WvpVideoGatewayTest {
    private static final String DEVICE = "34020000001320000001", CHANNEL = "34020000001310000001";
    private static final String KEY = "synthetic-wvp-key";
    private static final String MEDIA = "https://media.example.invalid/media";
    private static final String STREAM = "test-stream";
    private final ObjectMapper json = new ObjectMapper().findAndRegisterModules();
    private final Camera camera = new Camera("company-camera", "company-project", "Gate", "East", DEVICE, CHANNEL);
    private HttpServer server;
    private ExecutorService executor;
    private WvpProperties properties;
    private final AtomicReference<String> body = new AtomicReference<>();
    private final AtomicInteger status = new AtomicInteger(), delay = new AtomicInteger(), redirected = new AtomicInteger();
    private final List<Call> calls = Collections.synchronizedList(new ArrayList<>());
    private final List<WvpHttpClient> clients = new ArrayList<>();
    private boolean disconnect;

    private static final class Call {
        final String path, method, key, companyToken;
        final Map<String, String> query = new LinkedHashMap<>();
        Call(com.sun.net.httpserver.HttpExchange exchange) throws Exception {
            path = exchange.getRequestURI().getPath(); method = exchange.getRequestMethod();
            key = exchange.getRequestHeaders().getFirst("api-key");
            companyToken = exchange.getRequestHeaders().getFirst("token");
            String raw = exchange.getRequestURI().getRawQuery();
            if (raw != null) for (String part : raw.split("&")) {
                String[] pair = part.split("=", 2);
                query.put(URLDecoder.decode(pair[0], "UTF-8"), URLDecoder.decode(pair[1], "UTF-8"));
            }
        }
    }
    @BeforeEach void start() throws Exception {
        body.set(streamReply()); status.set(200); delay.set(0); redirected.set(0);
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        executor = Executors.newCachedThreadPool(r -> { Thread t = new Thread(r); t.setDaemon(true); return t; });
        server.setExecutor(executor);
        server.createContext("/wvp/", exchange -> {
            try {
                calls.add(new Call(exchange));
                if (disconnect) return;
                // JDK 8 HttpServer needs request EOF before an empty response can
                // release a keep-alive connection. Drain the fixture request first.
                while (exchange.getRequestBody().read() != -1) { }
                if (delay.get() > 0) Thread.sleep(delay.get());
                if (status.get() == 302) exchange.getResponseHeaders().set("Location", "/redirect-target");
                byte[] bytes = body.get().getBytes(StandardCharsets.UTF_8);
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(status.get(), bytes.length == 0 ? -1 : bytes.length);
                if (bytes.length > 0) exchange.getResponseBody().write(bytes);
            } catch (InterruptedException exception) { Thread.currentThread().interrupt(); }
            catch (IOException exception) { /* A deliberately timed-out test client has closed the socket. */ }
            catch (Exception exception) { throw new RuntimeException(exception); }
            finally { exchange.close(); }
        });
        server.createContext("/redirect-target", exchange -> {
            redirected.incrementAndGet(); exchange.sendResponseHeaders(200, -1); exchange.close();
        });
        server.start();
        properties = new WvpProperties(); properties.setEnabled(true);
        properties.setBaseUrl("http://127.0.0.1:" + server.getAddress().getPort() + "/wvp/");
        properties.setApiKey(KEY); properties.setMediaBaseUrl(MEDIA);
    }
    @AfterEach void stop() throws Exception {
        for (WvpHttpClient client : clients) client.close();
        server.stop(0); executor.shutdownNow();
    }
    private WvpVideoGateway gateway() {
        WvpHttpClient client = new WvpHttpClient(properties, json); clients.add(client);
        return new WvpVideoGateway(client, properties);
    }
    private Preview main() { Preview p = new Preview(); p.setSscId(camera.sscId); p.setStreamType(StreamType.MAIN); return p; }
    private TimeRange range() {
        TimeRange q = new TimeRange(); q.setSscId(camera.sscId);
        q.setStartTime(OffsetDateTime.parse("2026-11-02T00:00:00Z"));
        q.setEndTime(OffsetDateTime.parse("2026-11-02T02:00:00Z")); return q;
    }
    private String streamReply() {
        return "{\"code\":0,\"data\":{\"app\":\"rtp\",\"stream\":\"" + STREAM
                + "\",\"https_hls\":\"" + MEDIA + "/rtp/" + STREAM + "/hls.m3u8?callId=synthetic-call&originTypeStr=rtp_push\","
                + "\"rtsp\":\"DO_NOT_EXPOSE\",\"mediaServer\":{\"secret\":\"DO_NOT_EXPOSE\"}}}";
    }
    private void error(Runnable operation, int expectedStatus, String expectedCode) {
        assertThatThrownBy(operation::run).isInstanceOfSatisfying(ApiException.class, e -> {
            assertThat(e.getStatus()).isEqualTo(expectedStatus); assertThat(e.getCode()).isEqualTo(expectedCode);
            assertThat(e.getMessage()).doesNotContain(KEY, "DO_NOT_EXPOSE", "private-credential");
        });
    }

    @Test void previewUsesPinnedPathServiceHeaderAndAWhitelistedBrowserUrl() throws Exception {
        StreamInfo result = gateway().preview(camera, main());
        assertThat(calls).hasSize(1);
        Call call = calls.get(0);
        assertThat(call.method).isEqualTo("GET");
        assertThat(call.path).isEqualTo("/wvp/api/play/start/" + DEVICE + "/" + CHANNEL);
        assertThat(call.key).isEqualTo(KEY); assertThat(call.companyToken).isNull(); assertThat(call.query).isEmpty();
        assertThat(result.sessionId).isEqualTo(STREAM); assertThat(result.protocol).isEqualTo("HLS");
        assertThat(result.expiresAt).isNull();
        assertThat(json.writeValueAsString(result)).doesNotContain(KEY, "DO_NOT_EXPOSE", "mediaServer", "rtsp");
    }
    @Test void unsupportedSubstreamAndUnboundOrMalformedIdsNeverContactWvp() {
        WvpVideoGateway gateway = gateway(); Preview sub = main(); sub.setStreamType(StreamType.SUB);
        error(() -> gateway.preview(camera, sub), 501, "WVP_STREAM_TYPE_UNSUPPORTED");
        error(() -> gateway.preview(new Camera("c", "p", "n", "l", null, null), main()), 409, "CAMERA_UNBOUND");
        error(() -> gateway.preview(new Camera("c", "p", "n", "l", "../../device", CHANNEL), main()), 409, "CAMERA_BINDING_INVALID");
        assertThat(calls).isEmpty();
    }
    @Test void defaultAndLegacyMainUseTheSameReadOnlyPlayEndpointWithoutUpdatingChannelConfiguration() {
        WvpVideoGateway gateway = gateway();
        Preview request = new Preview(); request.setSscId(camera.sscId);
        assertThat(request.getStreamType()).isEqualTo(StreamType.DEFAULT);
        gateway.preview(camera, request);
        gateway.preview(camera, main());
        assertThat(calls).hasSize(2);
        assertThat(calls.get(0).path).isEqualTo(calls.get(1).path).isEqualTo("/wvp/api/play/start/" + DEVICE + "/" + CHANNEL);
        for (Call call : calls) {
            assertThat(call.method).isEqualTo("GET");
            assertThat(call.query).isEmpty();
        }
    }
    @Test void ptzMapsBusinessSpeedAndStopDoesNotDependOnUpstreamDefaults() {
        body.set(""); WvpVideoGateway gateway = gateway();
        Ptz request = new Ptz(); request.setAction(PtzAction.START); request.setCommand(PtzCommand.UP_LEFT); request.setSpeed(new BigDecimal("0.5"));
        gateway.ptz(camera, request);
        assertThat(calls.get(0).path).isEqualTo("/wvp/api/front-end/ptz/" + DEVICE + "/" + CHANNEL);
        assertThat(calls.get(0).query).containsEntry("command", "upleft").containsEntry("horizonSpeed", "128")
                .containsEntry("verticalSpeed", "128").containsEntry("zoomSpeed", "8");
        request.setAction(PtzAction.STOP); request.setCommand(null); request.setSpeed(null);
        gateway.ptz(camera, request);
        assertThat(calls.get(1).query).containsEntry("command", "stop").containsEntry("horizonSpeed", "0")
                .containsEntry("verticalSpeed", "0").containsEntry("zoomSpeed", "0");
    }
    @Test void malformedPtzDoesNotSendADeviceCommand() {
        Ptz p = new Ptz(); p.setAction(PtzAction.START); p.setCommand(PtzCommand.LEFT); p.setSpeed(new BigDecimal("1.1"));
        error(() -> gateway().ptz(camera, p), 400, "INVALID_ARGUMENT");
        assertThat(calls).isEmpty();
    }
    @Test void playbackConvertsInstantsToConfiguredWvpLocalTime() {
        gateway().startPlayback(camera, range());
        assertThat(calls.get(0).path).isEqualTo("/wvp/api/playback/start/" + DEVICE + "/" + CHANNEL);
        assertThat(calls.get(0).query).containsEntry("startTime", "2026-11-02 08:00:00").containsEntry("endTime", "2026-11-02 10:00:00");
    }
    @Test void recordsKeepRealTimesAndDoNotExposePathsOrRawVendorMetadata() throws Exception {
        body.set("{\"code\":0,\"data\":{\"deviceId\":\"" + DEVICE + "\",\"channelId\":\"" + CHANNEL
                + "\",\"sumNum\":1,\"recordList\":[{\"startTime\":\"2026-11-02 08:30:00\","
                + "\"endTime\":\"2026-11-02 09:00:00\",\"filePath\":\"DO_NOT_EXPOSE\"}]}}");
        List<RecordingSegment> result = gateway().recordings(camera, range());
        assertThat(result).hasSize(1);
        assertThat(result.get(0).startTime).isEqualTo(OffsetDateTime.parse("2026-11-02T08:30:00+08:00"));
        assertThat(result.get(0).recordId).matches("[a-f0-9]{32}");
        assertThat(json.writeValueAsString(result)).doesNotContain("filePath", "DO_NOT_EXPOSE", DEVICE, CHANNEL);
        assertThat(calls.get(0).path).isEqualTo("/wvp/api/gb_record/query/" + DEVICE + "/" + CHANNEL);
    }
    @Test void explicitEmptyRecordSetIsAcceptedButIncompleteOrWrongChannelDataIsRejected() {
        body.set("{\"code\":0,\"data\":{\"sumNum\":0,\"recordList\":[]}}");
        assertThat(gateway().recordings(camera, range())).isEmpty();
        body.set("{\"code\":0,\"data\":{\"sumNum\":1,\"recordList\":[]}}");
        error(() -> gateway().recordings(camera, range()), 502, "WVP_INVALID_RESPONSE");
        body.set("{\"code\":0,\"data\":{\"channelId\":\"another-channel\",\"sumNum\":0,\"recordList\":[]}}");
        error(() -> gateway().recordings(camera, range()), 502, "WVP_INVALID_RESPONSE");
    }
    @Test void invalidRecordDateDoesNotBecomeAValidPlaybackSegment() {
        body.set("{\"code\":0,\"data\":{\"sumNum\":1,\"recordList\":[{\"startTime\":\"2026-02-30 08:00:00\",\"endTime\":\"2026-11-02 09:00:00\"}]}}");
        error(() -> gateway().recordings(camera, range()), 502, "WVP_INVALID_RESPONSE");
    }
    @Test void subsecondAndAmbiguousLocalTimesAreNotSilentlyChanged() {
        TimeRange q = range(); q.setStartTime(q.getStartTime().withNano(1));
        error(() -> gateway().startPlayback(camera, q), 400, "INVALID_ARGUMENT");
        properties.setZoneId("America/New_York");
        q.setStartTime(OffsetDateTime.parse("2026-11-01T05:30:00Z")); q.setEndTime(OffsetDateTime.parse("2026-11-01T07:30:00Z"));
        error(() -> gateway().startPlayback(camera, q), 400, "INVALID_ARGUMENT"); assertThat(calls).isEmpty();
    }
    @Test void internalPlaybackControlsUseExactRoutesAndDoNotRetry() {
        body.set(""); status.set(204); WvpVideoGateway gateway = gateway();
        gateway.pausePlayback(STREAM); gateway.resumePlayback(STREAM); gateway.seekPlayback(STREAM, 42); gateway.stopPlayback(camera, STREAM);
        assertThat(calls).extracting(c -> c.path).containsExactly("/wvp/api/playback/pause/" + STREAM,
                "/wvp/api/playback/resume/" + STREAM, "/wvp/api/playback/seek/" + STREAM + "/42",
                "/wvp/api/playback/stop/" + DEVICE + "/" + CHANNEL + "/" + STREAM);
        error(() -> gateway.seekPlayback(STREAM, -1), 400, "INVALID_ARGUMENT");
        error(() -> gateway.pausePlayback("../another-session"), 400, "INVALID_ARGUMENT"); assertThat(calls).hasSize(4);
    }
    @ParameterizedTest
    @CsvSource({"401,0,502,WVP_AUTH_FAILED", "403,0,502,WVP_AUTH_FAILED", "302,0,502,WVP_REDIRECT_REJECTED",
            "404,0,502,WVP_ENDPOINT_NOT_FOUND", "500,0,502,WVP_HTTP_ERROR", "200,401,502,WVP_AUTH_FAILED",
            "200,403,502,WVP_AUTH_FAILED", "200,404,404,WVP_RESOURCE_NOT_FOUND", "200,486,504,WVP_OPERATION_TIMEOUT",
            "200,100,502,WVP_OPERATION_FAILED"})
    void upstreamFailuresStayFailuresAndNeverEchoTheVendorMessage(int httpStatus, int code, int resultStatus, String resultCode) {
        status.set(httpStatus); body.set("{\"code\":" + code + ",\"msg\":\"private-credential " + KEY + "\"}");
        error(() -> gateway().preview(camera, main()), resultStatus, resultCode);
        assertThat(calls).hasSize(1); assertThat(redirected.get()).isZero();
    }
    @Test void malformedOrMissingDataIsNotSuccessfulVideo() {
        for (String bad : Arrays.asList("<html>not-json</html>", "{\"code\":\"0\",\"data\":{}}", "{\"code\":0}", "{\"code\":0,\"data\":[]}")) {
            body.set(bad); error(() -> gateway().preview(camera, main()), 502, "WVP_INVALID_RESPONSE");
        }
    }
    @Test void mediaUrlsMustMatchConfiguredOriginPathAndKnownParameters() throws Exception {
        for (String url : Arrays.asList("https://other.example.invalid/media/rtp/test-stream/hls.m3u8",
                MEDIA + "/wrong/test-stream/hls.m3u8", MEDIA + "/rtp/test-stream/hls.m3u8?secret=private-credential",
                MEDIA + "/rtp/test-stream/hls.m3u8?callId=" + KEY, MEDIA + "/rtp/test-stream/hls.m3u8#fragment",
                "javascript:alert(1)")) {
            com.fasterxml.jackson.databind.node.ObjectNode reply = (com.fasterxml.jackson.databind.node.ObjectNode) json.readTree(streamReply());
            ((com.fasterxml.jackson.databind.node.ObjectNode) reply.get("data")).put("https_hls", url);
            body.set(reply.toString()); error(() -> gateway().preview(camera, main()), 502, "WVP_MEDIA_URL_INVALID");
        }
    }
    @Test void explicitLoopbackMediaUsesTheHttpField() throws Exception {
        properties.setMediaBaseUrl("http://127.0.0.1:18092");
        com.fasterxml.jackson.databind.node.ObjectNode reply = (com.fasterxml.jackson.databind.node.ObjectNode) json.readTree(streamReply());
        ((com.fasterxml.jackson.databind.node.ObjectNode) reply.get("data")).put("hls", "http://127.0.0.1:18092/rtp/test-stream/hls.m3u8");
        body.set(reply.toString());
        assertThat(gateway().preview(camera, main()).url).startsWith("http://127.0.0.1:18092/");
    }
    @Test void timeoutsAndResponseLimitsAreBoundedWithoutRetries() {
        properties.setReadTimeoutMs(80); delay.set(400);
        error(() -> gateway().preview(camera, main()), 504, "WVP_TIMEOUT"); assertThat(calls).hasSize(1);
        delay.set(0); properties.setReadTimeoutMs(5000); properties.setMaxResponseBytes(1024);
        char[] large = new char[2048]; Arrays.fill(large, 'x'); body.set(new String(large));
        error(() -> gateway().preview(camera, main()), 502, "WVP_RESPONSE_TOO_LARGE");
    }
    @Test void abruptDisconnectIsNotRetriedOrReportedAsSuccessfulVideo() {
        disconnect = true;
        error(() -> gateway().preview(camera, main()), 503, "WVP_UNREACHABLE");
        assertThat(calls).hasSize(1);
    }
    @Test void credentialsAndUnsupportedMediaConfigurationAreRejectedWithoutEchoingSecrets() {
        for (String invalid : Arrays.asList("https://user:private-credential@example.invalid", "https://example.invalid?key=private-credential", "https://example.invalid/a/../b")) {
            properties.setBaseUrl(invalid);
            assertThatThrownBy(this::gateway).isInstanceOf(IllegalArgumentException.class).hasMessageNotContaining("private-credential");
        }
        properties.setBaseUrl("http://127.0.0.1:" + server.getAddress().getPort()); properties.setMediaBaseUrl("http://public.example.invalid");
        assertThatThrownBy(this::gateway).isInstanceOf(IllegalArgumentException.class);
        properties.setMediaBaseUrl(MEDIA); properties.setApiKey("bad\r\nheader");
        assertThatThrownBy(this::gateway).isInstanceOf(IllegalArgumentException.class).hasMessageNotContaining("header\r\n");
        assertThat(calls).isEmpty();
    }
}
