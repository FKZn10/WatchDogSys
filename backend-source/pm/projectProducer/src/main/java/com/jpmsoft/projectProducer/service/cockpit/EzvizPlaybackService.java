package com.jpmsoft.projectProducer.service.cockpit;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmsoft.projectProducer.pojo.entity.cockpit.SmartSiteCamera;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.Clock;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EzvizPlaybackService {
    private static final String TOKEN_ENDPOINT = "https://open.ys7.com/api/lapp/token/get";
    private static final String PLAYER_PAGE = "https://open.ys7.com/console/jssdk/mobile.html";
    private static final long REFRESH_AHEAD_MILLIS = 60_000L;
    private static final Pattern EZOPEN_LIVE = Pattern.compile(
            "ezopen://open\\.ys7\\.com/([A-Za-z0-9]+)/([0-9]+)(?:\\.hd)?\\.live");

    private final boolean enabled;
    private final RestTemplate http;
    private final Clock clock;
    private final ObjectMapper json = new ObjectMapper();
    private final Map<String, Token> tokens = new ConcurrentHashMap<>();
    // 仅供已有空类型记录迁移期间使用；国标 10/旧 01 等类型绝不进入萤石。
    @Value("${ezviz.preview.allow-legacy-empty-kind:false}")
    private boolean allowLegacyEmptyKind;

    @Autowired
    public EzvizPlaybackService(@Value("${ezviz.preview.allow-app-token-link:false}") boolean enabled) {
        this(enabled, createHttpClient(), Clock.systemUTC());
    }

    EzvizPlaybackService(boolean enabled, RestTemplate http, Clock clock) {
        this.enabled = enabled;
        this.http = http;
        this.clock = clock;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Map<String, Object> createPlayUrl(SmartSiteCamera camera) {
        if (!enabled) {
            throw new IllegalStateException("萤石 EZOPEN 联调入口未启用");
        }
        String kind = trim(camera.getSscKind());
        boolean legacyEmptyKind = kind.isEmpty() && allowLegacyEmptyKind;
        // 新字典以 11 表示萤石，兼容已保存的旧 02，不自动修改数据库。
        if (!"11".equals(kind) && !"02".equals(kind) && !legacyEmptyKind) {
            throw new IllegalArgumentException(("10".equals(kind) || "01".equals(kind))
                    ? "该摄像头为 GB28181 接入，请使用国标预览入口"
                    : "请先将摄像头类型配置为萤石云（11）");
        }
        String source = trim(camera.getSscCode());
        Matcher live = EZOPEN_LIVE.matcher(source);
        if (!live.matches()) {
            throw new IllegalArgumentException("摄像头编码需填写萤石 EZOPEN 直播地址");
        }
        String serial = trim(camera.getSscSerial());
        String channel = trim(camera.getSscChannelNo());
        if ((!serial.isEmpty() && !serial.equalsIgnoreCase(live.group(1)))
                || (!channel.isEmpty() && !channel.equals(live.group(2)))) {
            throw new IllegalArgumentException("EZOPEN 地址与摄像头序列号或通道配置不一致");
        }
        String key = trim(camera.getSscKey());
        String secret = trim(camera.getSscSecret());
        if (key.isEmpty() || secret.isEmpty()) {
            throw new IllegalArgumentException("摄像头缺少萤石应用凭证");
        }
        Token token = accessToken(key, secret);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("playUrl", PLAYER_PAGE + "?accessToken=" + encode(token.value)
                + "&url=" + encode(source) + "&themeId=mobileLive");
        result.put("expireTime", token.expireTime);
        return result;
    }

    // Package-private: shared by preview and read-only channel status; never return secrets.
    String[] statusBinding(SmartSiteCamera camera) {
        String kind = trim(camera.getSscKind());
        if (!"11".equals(kind) && !"02".equals(kind)) throw new IllegalArgumentException("Unsupported provider");
        Matcher m = EZOPEN_LIVE.matcher(trim(camera.getSscCode()));
        if (!m.matches()) throw new IllegalArgumentException("Invalid source");
        if ((!trim(camera.getSscSerial()).isEmpty() && !trim(camera.getSscSerial()).equalsIgnoreCase(m.group(1)))
                || (!trim(camera.getSscChannelNo()).isEmpty() && !trim(camera.getSscChannelNo()).equals(m.group(2))))
            throw new IllegalArgumentException("Binding mismatch");
        if (trim(camera.getSscKey()).isEmpty() || trim(camera.getSscSecret()).isEmpty())
            throw new IllegalArgumentException("Missing credentials");
        return new String[]{m.group(1), m.group(2)};
    }

    String statusAccessToken(SmartSiteCamera camera) {
        return accessToken(trim(camera.getSscKey()), trim(camera.getSscSecret())).value;
    }

    // Only exposes playback necessities to the recording adapter, never application secrets.
    Map<String,Object> recordingToken(SmartSiteCamera camera) {
        Token t=accessToken(trim(camera.getSscKey()),trim(camera.getSscSecret()));
        Map<String,Object> result=new LinkedHashMap<>();result.put("accessToken",t.value);result.put("expireTime",t.expireTime);return result;
    }

    void invalidateAccessToken(SmartSiteCamera camera) {
        String secret = trim(camera.getSscSecret());
        tokens.computeIfPresent(trim(camera.getSscKey()), (key, cached) -> secret.equals(cached.secret) ? null : cached);
    }

    private Token accessToken(String key, String secret) {
        return tokens.compute(key, (ignored, cached) -> {
            if (cached != null && secret.equals(cached.secret)
                    && cached.expireTime - clock.millis() > REFRESH_AHEAD_MILLIS) {
                return cached;
            }
            return fetchToken(key, secret);
        });
    }

    private Token fetchToken(String key, String secret) {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("appKey", key);
        form.add("appSecret", secret);
        try {
            String body = http.postForObject(TOKEN_ENDPOINT, form, String.class);
            if (body != null) {
                JsonNode response = json.readTree(body);
                if (response != null && "200".equals(response.path("code").asText())) {
                    JsonNode data = response.path("data");
                    String value = data.path("accessToken").asText("");
                    long expireTime = data.path("expireTime").asLong();
                    if (!value.trim().isEmpty() && expireTime > clock.millis()) {
                        return new Token(value, expireTime, secret);
                    }
                }
            }
        } catch (RestClientException | IOException ignored) {
            // 上游响应或异常可能包含凭证，向调用方只返回固定错误。
        }
        throw new IllegalStateException("萤石取 Token 失败，请检查该摄像头的应用凭证及网络");
    }

    private static RestTemplate createHttpClient() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(3_000);
        factory.setReadTimeout(5_000);
        return new RestTemplate(factory);
    }

    private static String trim(String value) {
        return value == null ? "" : value.trim();
    }

    private static String encode(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("无法编码萤石播放地址");
        }
    }

    private static final class Token {
        private final String value;
        private final long expireTime;
        private final String secret;

        private Token(String value, long expireTime, String secret) {
            this.value = value;
            this.expireTime = expireTime;
            this.secret = secret;
        }
    }
}
