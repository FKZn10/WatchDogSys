package com.jpmsoft.videoaccess.adapter.wvp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NullNode;
import com.jpmsoft.videoaccess.api.ApiException;
import com.jpmsoft.videoaccess.config.WvpProperties;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/** HTTP contract pinned to WVP 569b361 (v2.7.4-20250709). No retries for stateful GETs. */
public final class WvpHttpClient implements AutoCloseable {
    private final URI base;
    private final String apiKey;
    private final int maxBytes;
    private final ObjectMapper json;
    private final RestTemplate http;
    private final CloseableHttpClient transport;

    public WvpHttpClient(WvpProperties properties, ObjectMapper json) {
        this.base = endpoint(properties.getBaseUrl(), "WVP base URL");
        this.apiKey = properties.getApiKey();
        this.maxBytes = properties.getMaxResponseBytes();
        if (apiKey == null || apiKey.trim().isEmpty() || apiKey.length() > 8192
                || apiKey.chars().anyMatch(c -> c < 32 || c == 127)) {
            throw new IllegalArgumentException("Configure a WVP service API key without control characters");
        }
        if (properties.getConnectTimeoutMs() < 1 || properties.getConnectTimeoutMs() > 60000
                || properties.getReadTimeoutMs() < 1 || properties.getReadTimeoutMs() > 60000
                || maxBytes < 1024 || maxBytes > 8 * 1024 * 1024) {
            throw new IllegalArgumentException("WVP timeouts and response limit must be bounded");
        }
        // The JDK URLConnection transport may retry GET after a disconnect. These GETs
        // can control devices, so use a transport with automatic retries disabled.
        this.transport = HttpClients.custom().disableAutomaticRetries().disableRedirectHandling()
                .disableCookieManagement().setMaxConnTotal(64).setMaxConnPerRoute(64).build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(transport);
        factory.setConnectTimeout(properties.getConnectTimeoutMs());
        factory.setReadTimeout(properties.getReadTimeoutMs());
        factory.setConnectionRequestTimeout(properties.getConnectTimeoutMs());
        this.http = new RestTemplate(factory);
        this.http.setErrorHandler(new ResponseErrorHandler() {
            public boolean hasError(ClientHttpResponse response) { return false; }
            public void handleError(ClientHttpResponse response) { }
        });
        this.json = json;
    }

    static URI endpoint(String configured, String label) {
        try {
            URI uri = URI.create(configured == null ? "" : configured);
            if (!("http".equals(uri.getScheme()) || "https".equals(uri.getScheme()))
                    || uri.getHost() == null || uri.getUserInfo() != null || uri.getRawQuery() != null
                    || uri.getFragment() != null || uri.getPort() == 0 || uri.getPort() > 65535
                    || !uri.normalize().equals(uri) || uri.getRawPath().contains("%")
                    || uri.getPath().contains("//") || uri.getPath().contains("\\")) {
                throw new IllegalArgumentException();
            }
            String text = uri.toString();
            return URI.create(text.endsWith("/") ? text.substring(0, text.length() - 1) : text);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Configure " + label + " without credentials, query, fragment or ambiguous path");
        }
    }

    JsonNode data(String[] path, Map<String, String> query) {
        JsonNode response = request(path, query, false);
        JsonNode data = response.get("data");
        if (data == null || !data.isObject()) throw invalidResponse();
        return data;
    }

    void command(String[] path, Map<String, String> query) { request(path, query, true); }

    private JsonNode request(String[] path, Map<String, String> query, boolean allowEmpty) {
        UriComponentsBuilder uri = UriComponentsBuilder.fromUri(base).pathSegment(path);
        query.forEach(uri::queryParam);
        URI target = uri.build().encode(StandardCharsets.UTF_8).toUri();
        try {
            return http.execute(target, HttpMethod.GET, request -> {
                request.getHeaders().set("api-key", apiKey);
                request.getHeaders().setAccept(java.util.Collections.singletonList(MediaType.APPLICATION_JSON));
            }, response -> {
                int status = response.getRawStatusCode();
                if (status == 401 || status == 403) throw authenticationFailure();
                if (status >= 300 && status < 400) throw new ApiException(502, "WVP_REDIRECT_REJECTED", "WVP 返回重定向，已拒绝转发凭据");
                if (status == 404) throw new ApiException(502, "WVP_ENDPOINT_NOT_FOUND", "WVP 接口不存在，请核对固定版本和路径");
                if (status < 200 || status >= 300) throw new ApiException(502, "WVP_HTTP_ERROR", "WVP HTTP 请求失败");
                byte[] body = read(response.getBody());
                if (body.length == 0 && allowEmpty && (status == 200 || status == 204)) return NullNode.instance;
                JsonNode payload;
                try { payload = json.readTree(body); }
                catch (IOException exception) { throw invalidResponse(); }
                if (payload == null || !payload.isObject() || !payload.path("code").isIntegralNumber()
                        || !payload.path("code").canConvertToInt()) throw invalidResponse();
                int code = payload.path("code").intValue();
                if (code == 401 || code == 403) throw authenticationFailure();
                if (code == 404) throw new ApiException(404, "WVP_RESOURCE_NOT_FOUND", "WVP 未找到设备、通道或会话");
                if (code == 486) throw new ApiException(504, "WVP_OPERATION_TIMEOUT", "WVP 等待设备响应超时");
                if (code != 0) throw new ApiException(502, "WVP_OPERATION_FAILED", "WVP 未完成请求，请检查组件及设备状态");
                return payload;
            });
        } catch (ResourceAccessException exception) {
            for (Throwable cause = exception; cause != null; cause = cause.getCause()) {
                if (cause instanceof SocketTimeoutException || cause instanceof ConnectTimeoutException) {
                    throw new ApiException(504, "WVP_TIMEOUT", "连接或等待 WVP 响应超时");
                }
            }
            throw new ApiException(503, "WVP_UNREACHABLE", "暂时无法连接 WVP");
        } catch (RestClientException exception) {
            throw new ApiException(502, "WVP_HTTP_ERROR", "WVP HTTP 请求失败");
        }
    }

    private byte[] read(InputStream input) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int count;
        while ((count = input.read(buffer)) != -1) {
            if (bytes.size() + count > maxBytes) throw new ApiException(502, "WVP_RESPONSE_TOO_LARGE", "WVP 响应超过配置限制");
            bytes.write(buffer, 0, count);
        }
        return bytes.toByteArray();
    }

    private ApiException authenticationFailure() {
        // This is a service credential failure, not an invalid company user's login.
        return new ApiException(502, "WVP_AUTH_FAILED", "WVP 服务端认证失败，请检查服务凭据与授权");
    }
    @Override public void close() throws IOException { transport.close(); }
    static ApiException invalidResponse() { return new ApiException(502, "WVP_INVALID_RESPONSE", "WVP 响应格式不符合固定版本约定"); }
}
