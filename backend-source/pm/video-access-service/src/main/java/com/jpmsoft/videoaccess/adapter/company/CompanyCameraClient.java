package com.jpmsoft.videoaccess.adapter.company;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmsoft.videoaccess.api.ApiException;
import com.jpmsoft.videoaccess.config.CompanyDirectoryProperties;
import com.jpmsoft.videoaccess.config.VideoProperties;
import com.jpmsoft.videoaccess.dto.VideoRequests.CameraQuery;
import com.jpmsoft.videoaccess.model.VideoModels.Camera;
import com.jpmsoft.videoaccess.model.VideoModels.CameraPage;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.SocketTimeoutException;
import java.util.*;

/** Delegates list authentication/data filtering to the existing authorized PM endpoint. */
@Component
public class CompanyCameraClient {
    private final URI endpoint;
    private final RestTemplate http;
    private final ObjectMapper json;
    private final int maxBytes;

    public CompanyCameraClient(VideoProperties video, ObjectMapper json) {
        CompanyDirectoryProperties settings = video.getCompanyDirectory();
        this.endpoint = settings.isEnabled() ? validateEndpoint(settings.getListUrl()) : null;
        this.maxBytes = settings.getMaxResponseBytes();
        if (settings.getConnectTimeoutMs() <= 0 || settings.getReadTimeoutMs() <= 0
                || maxBytes < 1024 || maxBytes > 8 * 1024 * 1024) {
            throw new IllegalArgumentException("Company directory timeouts and response limit must be bounded");
        }
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory() {
            @Override protected void prepareConnection(HttpURLConnection connection, String method) throws IOException {
                super.prepareConnection(connection, method);
                connection.setInstanceFollowRedirects(false);
            }
        };
        factory.setConnectTimeout(settings.getConnectTimeoutMs());
        factory.setReadTimeout(settings.getReadTimeoutMs());
        this.http = new RestTemplate(factory);
        this.http.setErrorHandler(new ResponseErrorHandler() {
            public boolean hasError(ClientHttpResponse response) { return false; }
            public void handleError(ClientHttpResponse response) { /* Handled before parsing the response. */ }
        });
        this.json = json;
    }

    private URI validateEndpoint(String configured) {
        try {
            URI uri = URI.create(configured == null ? "" : configured);
            if (!("http".equals(uri.getScheme()) || "https".equals(uri.getScheme()))
                    || uri.getHost() == null || uri.getUserInfo() != null || uri.getQuery() != null
                    || uri.getFragment() != null || uri.getPath() == null
                    || !uri.getPath().endsWith("/getSmartSiteCameraList")) {
                throw new IllegalArgumentException();
            }
            return uri;
        } catch (IllegalArgumentException exception) {
            // The message deliberately does not contain the configured URL or credentials.
            throw new IllegalArgumentException("Configure the authorized camera-list endpoint without credentials or query parameters");
        }
    }

    public CameraPage query(CameraQuery request, String token) {
        if (endpoint == null) throw new ApiException(503, "CATALOG_NOT_CONFIGURED", "公司摄像头列表地址未配置");
        if (token == null || token.trim().isEmpty()) {
            throw new ApiException(401, "COMPANY_LOGIN_REQUIRED", "请使用内部平台登录后的请求");
        }
        if (token.length() > 4096 || token.indexOf('\r') >= 0 || token.indexOf('\n') >= 0) {
            throw new ApiException(400, "INVALID_TOKEN_HEADER", "登录请求头格式不正确");
        }
        if (request == null || request.getpId() == null || request.getpId().trim().isEmpty()
                || request.getPage() < 1 || request.getSize() < 1 || request.getSize() > 100) {
            throw new ApiException(400, "INVALID_ARGUMENT", "项目和分页参数不正确");
        }
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("pId", request.getpId());
        body.put("page", request.getPage());
        body.put("size", request.getSize());
        try {
            return http.execute(endpoint, HttpMethod.POST, outbound -> {
                outbound.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                outbound.getHeaders().setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                outbound.getHeaders().set("token", token);
                json.writeValue(outbound.getBody(), body);
            }, inbound -> {
                int status = inbound.getRawStatusCode();
                rejectAuthStatus(status);
                if (status >= 300 && status < 400) {
                    throw new ApiException(502, "COMPANY_DIRECTORY_REDIRECT", "目录接口发生重定向，请核对配置地址");
                }
                if (status != 200) {
                    throw new ApiException(502, "COMPANY_DIRECTORY_HTTP_ERROR", "内部平台目录请求失败");
                }
                JsonNode envelope;
                try { envelope = json.readTree(readBounded(inbound.getBody())); }
                catch (JsonProcessingException exception) { throw invalidResponse(); }
                return map(envelope, request.getSize());
            });
        } catch (ApiException exception) {
            throw exception;
        } catch (ResourceAccessException exception) {
            Throwable cause = exception;
            while (cause != null) {
                if (cause instanceof SocketTimeoutException) {
                    throw new ApiException(504, "COMPANY_DIRECTORY_TIMEOUT", "内部平台目录请求超时");
                }
                cause = cause.getCause();
            }
            throw new ApiException(503, "COMPANY_DIRECTORY_UNREACHABLE", "内部平台目录暂不可达");
        } catch (RestClientException exception) {
            throw new ApiException(502, "COMPANY_DIRECTORY_HTTP_ERROR", "内部平台目录请求失败");
        }
    }

    private byte[] readBounded(InputStream input) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        int count;
        while ((count = input.read(buffer)) != -1) {
            if (out.size() + count > maxBytes) {
                throw new ApiException(502, "COMPANY_DIRECTORY_RESPONSE_TOO_LARGE", "内部平台目录响应超出限制");
            }
            out.write(buffer, 0, count);
        }
        return out.toByteArray();
    }

    private CameraPage map(JsonNode root, int pageSize) {
        if (root == null || !root.isObject() || !root.path("statusCode").isIntegralNumber()) throw invalidResponse();
        rejectAuthStatus(root.path("statusCode").asInt());
        if (root.path("statusCode").asInt() != 200 || !root.path("success").isBoolean()) throw invalidResponse();
        if (!root.path("success").booleanValue()) {
            throw new ApiException(502, "COMPANY_DIRECTORY_REJECTED", "内部平台未成功返回摄像头目录");
        }
        JsonNode rows = root.get("data"), total = root.get("total");
        if (rows == null || !rows.isArray() || rows.size() > pageSize || total == null
                || !total.isIntegralNumber() || !total.canConvertToInt() || total.intValue() < rows.size()) {
            throw invalidResponse();
        }
        List<Camera> cameras = new ArrayList<>();
        Set<String> ids = new HashSet<>();
        for (JsonNode row : rows) {
            String id = requiredText(row, "sscId"), project = requiredText(row, "pId");
            if (!ids.add(id)) throw invalidResponse();
            // Preserve the actual child project. Never infer a GB binding from sscCode/state/URL.
            cameras.add(new Camera(id, project, optionalText(row, "sscName", "未命名摄像头"),
                    optionalText(row, "sscPosition", ""), null, null));
        }
        return new CameraPage(cameras, total.intValue());
    }

    private String requiredText(JsonNode row, String key) {
        JsonNode value = row.get(key);
        if (value == null || !value.isTextual() || value.textValue().trim().isEmpty()) throw invalidResponse();
        return value.textValue();
    }
    private String optionalText(JsonNode row, String key, String fallback) {
        JsonNode value = row.get(key);
        if (value == null || value.isNull()) return fallback;
        if (!value.isTextual()) throw invalidResponse();
        return value.textValue().trim().isEmpty() ? fallback : value.textValue();
    }
    private void rejectAuthStatus(int status) {
        if (status == 401) throw new ApiException(401, "COMPANY_LOGIN_REQUIRED", "内部平台登录已失效或无效");
        if (status == 403) throw new ApiException(403, "COMPANY_ACCESS_DENIED", "内部平台拒绝目录访问");
    }
    private ApiException invalidResponse() {
        return new ApiException(502, "COMPANY_DIRECTORY_INVALID_RESPONSE", "内部平台目录响应格式不符合约定");
    }
}
