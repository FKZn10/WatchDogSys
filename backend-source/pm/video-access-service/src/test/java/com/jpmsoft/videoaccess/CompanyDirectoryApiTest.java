package com.jpmsoft.videoaccess;

import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = "video.company-directory.enabled=true")
@AutoConfigureMockMvc
class CompanyDirectoryApiTest {
    private static final AtomicInteger calls = new AtomicInteger();
    private static final AtomicInteger replyStatus = new AtomicInteger(200);
    private static final AtomicReference<String> tokenSeen = new AtomicReference<>();
    private static final AtomicReference<String> body = new AtomicReference<>();
    private static final HttpServer stub;
    static {
        try {
            stub = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
            stub.createContext("/getSmartSiteCameraList", exchange -> {
                calls.incrementAndGet(); tokenSeen.set(exchange.getRequestHeaders().getFirst("token"));
                byte[] response = body.get().getBytes(StandardCharsets.UTF_8);
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(replyStatus.get(), response.length);
                exchange.getResponseBody().write(response); exchange.close();
            });
            stub.start();
        } catch (Exception exception) { throw new ExceptionInInitializerError(exception); }
    }
    @DynamicPropertySource static void directoryUrl(DynamicPropertyRegistry registry) {
        registry.add("video.company-directory.list-url", () -> "http://127.0.0.1:" + stub.getAddress().getPort() + "/getSmartSiteCameraList");
    }
    @Autowired MockMvc mvc;
    @BeforeEach void reset() {
        calls.set(0); replyStatus.set(200); tokenSeen.set(null);
        body.set("{\"statusCode\":200,\"success\":true,\"total\":0,\"data\":[]}");
    }
    @AfterAll static void close() { stub.stop(0); }

    @Test void forwardsCallerTokenAndReturnsOnlyMappedCameraFields() throws Exception {
        body.set("{\"statusCode\":200,\"success\":true,\"total\":1,\"data\":[{\"sscId\":\"cam\",\"pId\":\"child\","
                + "\"sscName\":\"Gate\",\"sscPosition\":\"East\",\"sscState\":1,\"sscSecret\":\"DO_NOT_EXPOSE\"}]}");
        mvc.perform(post("/api/video/v1/cameras/query").contentType(MediaType.APPLICATION_JSON)
                .header("token", "synthetic-web-caller").content("{\"pId\":\"parent\"}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.mode").value("COMPANY_DIRECTORY"))
                .andExpect(jsonPath("$.data[0].id").value("cam"))
                .andExpect(jsonPath("$.data[0].projectId").value("child"))
                .andExpect(jsonPath("$.data[0].status").value("unknown"))
                .andExpect(jsonPath("$.data[0].sscSecret").doesNotExist());
        assertThat(tokenSeen.get()).isEqualTo("synthetic-web-caller");
    }
    @Test void missingTokenDoesNotContactUpstream() throws Exception {
        mvc.perform(post("/api/video/v1/cameras/query").contentType(MediaType.APPLICATION_JSON).content("{\"pId\":\"project\"}"))
                .andExpect(status().isUnauthorized()).andExpect(jsonPath("$.errorCode").value("COMPANY_LOGIN_REQUIRED"));
        assertThat(calls.get()).isZero();
    }
    @Test void directoryFailureDoesNotBecomeSuccessfulEmptyData() throws Exception {
        replyStatus.set(403);
        mvc.perform(post("/api/video/v1/cameras/query").contentType(MediaType.APPLICATION_JSON)
                .header("token", "synthetic-web-caller").content("{\"pId\":\"project\"}"))
                .andExpect(status().isForbidden()).andExpect(jsonPath("$.success").value(false));
    }
    @Test void directoryIntegrationDoesNotEnablePtzOrPlaybackAuthorization() throws Exception {
        mvc.perform(post("/api/video/v1/ptz").contentType(MediaType.APPLICATION_JSON).header("token", "synthetic-web-caller")
                .content("{\"sscId\":\"cam\",\"action\":\"STOP\"}"))
                .andExpect(status().isServiceUnavailable()).andExpect(jsonPath("$.errorCode").value("IDENTITY_NOT_CONFIGURED"));
        assertThat(calls.get()).isZero();
    }
}
