package com.jpmsoft.videoaccess.adapter.company;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmsoft.videoaccess.api.ApiException;
import com.jpmsoft.videoaccess.config.VideoProperties;
import com.jpmsoft.videoaccess.dto.VideoRequests.CameraQuery;
import com.jpmsoft.videoaccess.model.VideoModels.*;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.*;
import static org.assertj.core.api.Assertions.*;

/** Real loopback HTTP server; no company endpoint or real login credential is used. */
class CompanyCameraClientTest {
    private static final String ROUTE = "/pm/SmartSite/v1//camera/getSmartSiteCameraList";
    private static final String EMPTY = "{\"statusCode\":200,\"success\":true,\"data\":[],\"total\":0}";
    private final ObjectMapper json = new ObjectMapper();
    private HttpServer server;
    private ExecutorService executor;
    private VideoProperties properties;
    private final AtomicReference<String> body = new AtomicReference<>();
    private final AtomicInteger status = new AtomicInteger();
    private final AtomicInteger delayMs = new AtomicInteger();
    private final AtomicInteger redirected = new AtomicInteger();
    private final List<String> tokens = Collections.synchronizedList(new ArrayList<>());
    private final List<JsonNode> requests = Collections.synchronizedList(new ArrayList<>());

    @BeforeEach void start() throws Exception {
        body.set(EMPTY); status.set(200); delayMs.set(0); redirected.set(0);
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        executor = Executors.newCachedThreadPool(r -> { Thread t = new Thread(r); t.setDaemon(true); return t; });
        server.setExecutor(executor);
        server.createContext(ROUTE, exchange -> {
            tokens.add(exchange.getRequestHeaders().getFirst("token"));
            requests.add(json.readTree(exchange.getRequestBody()));
            try {
                if (delayMs.get() > 0) Thread.sleep(delayMs.get());
                if (status.get() == 302) exchange.getResponseHeaders().set("Location", "/redirect-target");
                byte[] bytes = body.get().getBytes(StandardCharsets.UTF_8);
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(status.get(), bytes.length);
                exchange.getResponseBody().write(bytes);
            } catch (InterruptedException exception) { Thread.currentThread().interrupt(); }
            finally { exchange.close(); }
        });
        server.createContext("/redirect-target", exchange -> {
            redirected.incrementAndGet(); exchange.sendResponseHeaders(200, -1); exchange.close();
        });
        server.start();
        properties = new VideoProperties();
        properties.getCompanyDirectory().setEnabled(true);
        properties.getCompanyDirectory().setListUrl("http://127.0.0.1:" + server.getAddress().getPort() + ROUTE);
    }
    @AfterEach void stop() { server.stop(0); executor.shutdownNow(); }
    private CompanyCameraClient client() { return new CompanyCameraClient(properties, json); }
    private CameraQuery query() { CameraQuery q = new CameraQuery(); q.setpId("parent-project"); q.setPage(2); return q; }
    private void expectError(CompanyCameraClient client, int expectedStatus, String code) {
        assertThatThrownBy(() -> client.query(query(), "synthetic-caller"))
                .isInstanceOfSatisfying(ApiException.class, e -> {
                    assertThat(e.getStatus()).isEqualTo(expectedStatus); assertThat(e.getCode()).isEqualTo(code);
                });
    }

    @Test void sendsOnlyTheAllowedQueryAndUsesTheCurrentCallersToken() {
        CompanyCameraClient client = client();
        assertThat(client.query(query(), "synthetic-caller-A").cameras).isEmpty();
        assertThat(client.query(query(), "synthetic-caller-B").total).isZero();
        assertThat(tokens).containsExactly("synthetic-caller-A", "synthetic-caller-B");
        assertThat(requests.get(0).size()).isEqualTo(3);
        assertThat(requests.get(0).path("pId").asText()).isEqualTo("parent-project");
        assertThat(requests.get(0).path("page").asInt()).isEqualTo(2);
        assertThat(requests.get(0).path("size").asInt()).isEqualTo(20);
        assertThat(requests.get(0).has("token")).isFalse();
    }
    @Test void mapsWhitelistedFieldsAndNeverGuessesStatusOrBinding() throws Exception {
        body.set("{\"statusCode\":200,\"success\":true,\"total\":1,\"data\":[{"
                + "\"sscId\":\"camera-1\",\"pId\":\"child-project\",\"sscName\":\"Gate\",\"sscPosition\":\"East\","
                + "\"sscState\":1,\"sscCode\":\"UNCONFIRMED_CODE\",\"sscSecret\":\"DO_NOT_EXPOSE\",\"sscUrl\":\"DO_NOT_EXPOSE_URL\"}]}");
        Camera camera = client().query(query(), "synthetic-caller").cameras.get(0);
        assertThat(camera.pId).isEqualTo("child-project");
        assertThat(camera.isBound()).isFalse();
        CameraView view = new CameraView(camera);
        assertThat(view.status).isEqualTo("unknown");
        assertThat(view.id).isEqualTo("camera-1");
        assertThat(view.name).isEqualTo("Gate");
        assertThat(json.writeValueAsString(view)).doesNotContain("sscSecret", "DO_NOT_EXPOSE", "UNCONFIRMED_CODE");
    }
    @Test void rejectsMissingOrMalformedTokensBeforeSendingAnyRequest() {
        CompanyCameraClient client = client();
        assertThatThrownBy(() -> client.query(query(), null)).isInstanceOfSatisfying(ApiException.class,
                e -> assertThat(e.getStatus()).isEqualTo(401));
        assertThatThrownBy(() -> client.query(query(), "bad\r\nheader")).isInstanceOfSatisfying(ApiException.class,
                e -> assertThat(e.getStatus()).isEqualTo(400));
        assertThat(tokens).isEmpty();
    }
    @Test void preservesHttpAndBusinessAuthenticationFailures() {
        for (int code : new int[]{401, 403}) {
            status.set(code);
            expectError(client(), code, code == 401 ? "COMPANY_LOGIN_REQUIRED" : "COMPANY_ACCESS_DENIED");
            status.set(200);
            body.set("{\"statusCode\":" + code + ",\"success\":false}");
            expectError(client(), code, code == 401 ? "COMPANY_LOGIN_REQUIRED" : "COMPANY_ACCESS_DENIED");
        }
    }
    @Test void businessFailureAndMalformedPayloadAreNotConvertedToEmptyLists() {
        body.set("{\"statusCode\":200,\"success\":false,\"data\":[],\"total\":0}");
        expectError(client(), 502, "COMPANY_DIRECTORY_REJECTED");
        for (String invalid : Arrays.asList("<html>Login</html>", "{}", "null",
                "{\"statusCode\":200,\"success\":true,\"data\":null,\"total\":0}",
                "{\"statusCode\":200,\"success\":true,\"data\":[],\"total\":-1}",
                "{\"statusCode\":200,\"success\":true,\"data\":[{\"sscName\":\"no id\"}],\"total\":1}")) {
            body.set(invalid); expectError(client(), 502, "COMPANY_DIRECTORY_INVALID_RESPONSE");
        }
    }
    @Test void redirectIsRejectedWithoutForwardingTheTokenToAnotherLocation() {
        status.set(302);
        expectError(client(), 502, "COMPANY_DIRECTORY_REDIRECT");
        assertThat(redirected.get()).isZero();
    }
    @Test void readTimeoutIsReportedAsTimeout() {
        properties.getCompanyDirectory().setReadTimeoutMs(80);
        delayMs.set(400);
        expectError(client(), 504, "COMPANY_DIRECTORY_TIMEOUT");
    }
    @Test void responseSizeIsBounded() {
        properties.getCompanyDirectory().setMaxResponseBytes(1024);
        char[] chars = new char[2048]; Arrays.fill(chars, 'x'); body.set(new String(chars));
        expectError(client(), 502, "COMPANY_DIRECTORY_RESPONSE_TOO_LARGE");
    }
    @Test void configurationCannotContainCredentialsOrTargetOtherOperations() {
        for (String invalid : Arrays.asList("https://user:private@example.test/getSmartSiteCameraList",
                "https://example.test/deleteSmartSiteCameraById", "https://example.test/getSmartSiteCameraList?token=private")) {
            properties.getCompanyDirectory().setListUrl(invalid);
            assertThatThrownBy(this::client).isInstanceOf(IllegalArgumentException.class)
                    .hasMessageNotContaining("private");
        }
    }
}
