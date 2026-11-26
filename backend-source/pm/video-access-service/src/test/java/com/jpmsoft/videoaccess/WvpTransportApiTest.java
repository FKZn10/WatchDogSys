package com.jpmsoft.videoaccess;

import com.jpmsoft.videoaccess.adapter.wvp.WvpVideoGateway;
import com.jpmsoft.videoaccess.port.VideoPorts.VideoGateway;
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
import java.util.concurrent.atomic.AtomicInteger;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/** Enabling a vendor transport must not manufacture company camera permission. */
@SpringBootTest(properties = {"video.wvp.enabled=true", "video.wvp.api-key=synthetic-wvp-key",
        "video.wvp.media-base-url=https://media.example.invalid/media"})
@AutoConfigureMockMvc
class WvpTransportApiTest {
    private static final AtomicInteger calls = new AtomicInteger();
    private static final HttpServer stub;
    static {
        try {
            stub = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
            stub.createContext("/", exchange -> { calls.incrementAndGet(); exchange.sendResponseHeaders(500, -1); exchange.close(); });
            stub.start();
        } catch (Exception e) { throw new ExceptionInInitializerError(e); }
    }
    @DynamicPropertySource static void url(DynamicPropertyRegistry registry) {
        registry.add("video.wvp.base-url", () -> "http://127.0.0.1:" + stub.getAddress().getPort());
    }
    @Autowired MockMvc mvc;
    @Autowired VideoGateway gateway;
    @AfterAll static void stop() { stub.stop(0); }
    @Test void previewPtzAndPlaybackStillRequireVerifiedCompanyIdentity() throws Exception {
        assertThat(gateway).isInstanceOf(WvpVideoGateway.class);
        String[][] requests = {{"preview", "{\"sscId\":\"camera\",\"streamType\":\"MAIN\"}"},
                {"ptz/lease/acquire", "{\"sscId\":\"camera\"}"},
                {"ptz/lease/renew", "{\"sscId\":\"camera\",\"leaseId\":\"guessed\"}"},
                {"ptz/lease/release", "{\"sscId\":\"camera\",\"leaseId\":\"guessed\"}"},
                {"ptz", "{\"sscId\":\"camera\",\"action\":\"STOP\"}"},
                {"playback/control", "{\"sscId\":\"camera\",\"playbackId\":\"00000000-0000-0000-0000-000000000001\",\"revision\":1,\"action\":\"STOP\"}"}};
        for (String[] request : requests) {
            mvc.perform(post("/api/video/v1/" + request[0]).contentType(MediaType.APPLICATION_JSON)
                    .header("token", "synthetic-company-caller").content(request[1]))
                    .andExpect(status().isServiceUnavailable()).andExpect(jsonPath("$.errorCode").value("IDENTITY_NOT_CONFIGURED"));
        }
        assertThat(calls.get()).isZero();
    }
    @Test void browserCannotInjectAnUpstreamDeviceBinding() throws Exception {
        mvc.perform(post("/api/video/v1/preview").contentType(MediaType.APPLICATION_JSON)
                .content("{\"sscId\":\"camera\",\"deviceId\":\"injected\",\"streamType\":\"MAIN\"}"))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.errorCode").value("INVALID_JSON"));
        assertThat(calls.get()).isZero();
    }
}
