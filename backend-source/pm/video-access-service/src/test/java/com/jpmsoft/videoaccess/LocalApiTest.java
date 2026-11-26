package com.jpmsoft.videoaccess;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("local")
class LocalApiTest {
    @Autowired MockMvc mvc;
    @Autowired TestRestTemplate http;

    @Test void startsRealHttpServerWithoutClaimingBusinessReady() {
        String body = http.getForObject("/api/video/v1/health", String.class);
        assertThat(body).contains("\"businessReady\":false", "\"registryEnabled\":false", "LOCAL_DEMO");
    }
    @Test void emptyProjectReturnsCompanyShapeAndExplicitDemoMode() throws Exception {
        mvc.perform(post("/api/video/v1/cameras/query").contentType(MediaType.APPLICATION_JSON)
                .content("{\"pId\":\"demo-project\"}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.total").value(0)).andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.mode").value("LOCAL_DEMO"));
    }
    @Test void projectCannotBeSelectedOutsideDemoPermission() throws Exception {
        mvc.perform(post("/api/video/v1/cameras/query").contentType(MediaType.APPLICATION_JSON)
                .content("{\"pId\":\"other-project\"}"))
                .andExpect(status().isForbidden()).andExpect(jsonPath("$.errorCode").value("PROJECT_FORBIDDEN"));
    }
    @Test void rejectsMissingProjectAndInvalidPagination() throws Exception {
        for (String json : new String[]{"{}", "{\"pId\":\"demo-project\",\"page\":0}",
                "{\"pId\":\"demo-project\",\"size\":101}"}) {
            mvc.perform(post("/api/video/v1/cameras/query").contentType(MediaType.APPLICATION_JSON).content(json))
                    .andExpect(status().isBadRequest()).andExpect(jsonPath("$.errorCode").value("INVALID_ARGUMENT"));
        }
    }
    @Test void unknownCameraNeverReturnsFakeVideoUrl() throws Exception {
        mvc.perform(post("/api/video/v1/preview").contentType(MediaType.APPLICATION_JSON)
                .content("{\"sscId\":\"not-purchased\"}"))
                .andExpect(status().isNotFound()).andExpect(jsonPath("$.errorCode").value("CAMERA_NOT_FOUND"));
    }
    @Test void protocolErrorsAreClientErrorsRatherThanInternalFailures() throws Exception {
        mvc.perform(get("/api/video/v1/cameras/query"))
                .andExpect(status().isMethodNotAllowed()).andExpect(header().string("Allow", "POST"))
                .andExpect(jsonPath("$.errorCode").value("METHOD_NOT_ALLOWED"));
        mvc.perform(post("/api/video/v1/cameras/query").contentType(MediaType.TEXT_PLAIN).content("{}"))
                .andExpect(status().isUnsupportedMediaType())
                .andExpect(jsonPath("$.errorCode").value("UNSUPPORTED_MEDIA_TYPE"));
    }
    @Test void rejectsUnknownFieldsInsteadOfAcceptingClientIdentity() throws Exception {
        mvc.perform(post("/api/video/v1/cameras/query").contentType(MediaType.APPLICATION_JSON)
                .content("{\"pId\":\"demo-project\",\"userId\":\"admin\"}"))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.errorCode").value("INVALID_JSON"));
    }
    @Test void rejectsReversedRecordingRangeAndDatesWithoutTimezone() throws Exception {
        mvc.perform(post("/api/video/v1/recordings/query").contentType(MediaType.APPLICATION_JSON)
                .content("{\"sscId\":\"test\",\"startTime\":\"2026-11-02T12:00:00+08:00\",\"endTime\":\"2026-11-02T11:00:00+08:00\"}"))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.errorCode").value("INVALID_ARGUMENT"));
        mvc.perform(post("/api/video/v1/recordings/query").contentType(MediaType.APPLICATION_JSON)
                .content("{\"sscId\":\"test\",\"startTime\":\"2026-11-02T11:00:00\",\"endTime\":\"2026-11-02T12:00:00\"}"))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.errorCode").value("INVALID_JSON"));
    }
    @Test void validatesStartCommandAndNormalizedSpeed() throws Exception {
        for (String json : new String[]{"{\"sscId\":\"test\",\"action\":\"START\"}",
                "{\"sscId\":\"test\",\"action\":\"START\",\"command\":\"UP\",\"speed\":2}"}) {
            mvc.perform(post("/api/video/v1/ptz").contentType(MediaType.APPLICATION_JSON).content(json))
                    .andExpect(status().isBadRequest()).andExpect(jsonPath("$.errorCode").value("INVALID_ARGUMENT"));
        }
    }
    @Test void seekRequiresAnExplicitPosition() throws Exception {
        mvc.perform(post("/api/video/v1/playback/control").contentType(MediaType.APPLICATION_JSON)
                .content("{\"sscId\":\"test\",\"playbackId\":\"session\",\"action\":\"SEEK\"}"))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.errorCode").value("INVALID_ARGUMENT"));
    }
}
