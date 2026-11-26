package com.jpmsoft.videoaccess;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.jpmsoft.videoaccess.port.VideoPorts.VideoGateway;
import com.jpmsoft.videoaccess.model.VideoModels.StreamInfo;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = {"video.module.cameras[0].ssc-id=synthetic", "video.module.cameras[0].p-id=project-a",
        "video.module.cameras[0].device-id=34020000001320000001", "video.module.cameras[0].channel-id=34020000001310000001",
        "video.module.cameras[0].ptz-supported=true", "video.ptz.single-instance-enabled=true", "logging.level.VIDEO_CONTROL_AUDIT=INFO"})
@ActiveProfiles("module") @AutoConfigureMockMvc
class ModuleApiTest {
    @Autowired MockMvc mvc;
    @MockBean VideoGateway gateway;
    @BeforeEach void media() { when(gateway.preview(any(), any())).thenReturn(new StreamInfo("upstream-id", "HLS", "http://127.0.0.1/hls.m3u8", null)); }
    @Test void defaultPreviewIsExplicitlyChannelDefaultAndDoesNotClaimAPhysicalMainOrSubstream() throws Exception {
        String[] bodies = {"{\"sscId\":\"synthetic\"}", "{\"sscId\":\"synthetic\",\"streamType\":\"DEFAULT\"}",
                "{\"sscId\":\"synthetic\",\"streamType\":\"MAIN\"}"};
        for (String body : bodies) {
            String response = mvc.perform(post("/api/video/v1/preview").contentType(MediaType.APPLICATION_JSON).content(body))
                    .andExpect(status().isOk()).andExpect(jsonPath("$.data.streamSelection").value("CHANNEL_DEFAULT"))
                    .andExpect(jsonPath("$.data.actualStreamType").value("UNKNOWN")).andReturn().getResponse().getContentAsString();
            String id = new com.fasterxml.jackson.databind.ObjectMapper().readTree(response).path("data").path("sessionId").asText();
            mvc.perform(post("/api/video/v1/preview/release").contentType(MediaType.APPLICATION_JSON)
                    .content("{\"sscId\":\"synthetic\",\"sessionId\":\""+id+"\"}")).andExpect(status().isOk());
        }
        verify(gateway, times(2)).preview(any(), argThat(r -> r.getStreamType() == com.jpmsoft.videoaccess.dto.VideoRequests.StreamType.DEFAULT));
        verify(gateway).preview(any(), argThat(r -> r.getStreamType() == com.jpmsoft.videoaccess.dto.VideoRequests.StreamType.MAIN));
    }
    @Test void playbackHttpUsesOpaqueHandleRevisionAndOwnRenewStop() throws Exception {
        java.time.OffsetDateTime from=java.time.OffsetDateTime.parse("2026-11-04T08:00:00+08:00");
        when(gateway.recordings(any(),any())).thenReturn(java.util.Collections.singletonList(new com.jpmsoft.videoaccess.model.VideoModels.RecordingSegment("fixture",from,from.plusSeconds(120))));
        when(gateway.startPlayback(any(),any())).thenReturn(new StreamInfo("vendor-playback","HLS","http://127.0.0.1/playback/hls.m3u8",null));
        String opened=mvc.perform(post("/api/video/v1/playback/start").contentType(MediaType.APPLICATION_JSON)
                .content("{\"sscId\":\"synthetic\",\"startTime\":\"2026-11-04T08:00:00+08:00\",\"endTime\":\"2026-11-04T08:02:00+08:00\"}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.data.revision").value(1)).andReturn().getResponse().getContentAsString();
        String id=new com.fasterxml.jackson.databind.ObjectMapper().readTree(opened).path("data").path("playbackId").asText();
        assertThat(id).isNotEqualTo("vendor-playback");
        String handle="{\"sscId\":\"synthetic\",\"playbackId\":\""+id+"\"";
        mvc.perform(post("/api/video/v1/playback/renew").contentType(MediaType.APPLICATION_JSON).content(handle+"}"))
                .andExpect(status().isOk());
        mvc.perform(post("/api/video/v1/playback/control").contentType(MediaType.APPLICATION_JSON).content(handle+",\"revision\":1,\"action\":\"PAUSE\"}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.data.state").value("PAUSED_REQUESTED"));
        mvc.perform(post("/api/video/v1/playback/control").contentType(MediaType.APPLICATION_JSON).content(handle+",\"revision\":1,\"action\":\"SEEK\",\"positionTime\":\"2026-11-04T08:01:20+08:00\"}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.data.revision").value(2));
        mvc.perform(post("/api/video/v1/playback/control").contentType(MediaType.APPLICATION_JSON).content(handle+",\"revision\":1,\"action\":\"PAUSE\"}"))
                .andExpect(status().isConflict());
        mvc.perform(post("/api/video/v1/playback/control").contentType(MediaType.APPLICATION_JSON).content(handle+",\"revision\":2,\"action\":\"STOP\"}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.data.state").value("STOPPED"));
        mvc.perform(post("/api/video/v1/playback/renew").contentType(MediaType.APPLICATION_JSON).content(handle+"}"))
                .andExpect(status().isGone());
    }
    @Test void twoHttpViewersHaveIndependentTicketsAndReleaseIsIdempotent() throws Exception {
        String[] ids = new String[2];
        for (int i = 0; i < ids.length; i++) {
            String response = mvc.perform(post("/api/video/v1/preview").contentType(MediaType.APPLICATION_JSON)
                    .content("{\"sscId\":\"synthetic\",\"streamType\":\"MAIN\"}"))
                    .andExpect(status().isOk()).andExpect(jsonPath("$.data.renewAfterMs").value(5000))
                    .andReturn().getResponse().getContentAsString();
            ids[i] = new com.fasterxml.jackson.databind.ObjectMapper().readTree(response).path("data").path("sessionId").asText();
        }
        assertThat(ids[0]).isNotEqualTo(ids[1]).isNotEqualTo("upstream-id");
        for (int i = 0; i < 2; i++) mvc.perform(post("/api/video/v1/preview/release").contentType(MediaType.APPLICATION_JSON)
                .content("{\"sscId\":\"synthetic\",\"sessionId\":\"" + ids[0] + "\"}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.data.status").value("RELEASED"));
        mvc.perform(post("/api/video/v1/preview/renew").contentType(MediaType.APPLICATION_JSON)
                .content("{\"sscId\":\"synthetic\",\"sessionId\":\"" + ids[1] + "\"}"))
                .andExpect(status().isOk());
        mvc.perform(post("/api/video/v1/preview/renew").contentType(MediaType.APPLICATION_JSON)
                .content("{\"sscId\":\"synthetic\",\"sessionId\":\"" + ids[0] + "\"}"))
                .andExpect(status().isGone());
        mvc.perform(post("/api/video/v1/preview/release").contentType(MediaType.APPLICATION_JSON)
                .content("{\"sscId\":\"synthetic\",\"sessionId\":\"upstream-id\"}"))
                .andExpect(status().isBadRequest());
        verify(gateway, times(2)).preview(any(), any()); verifyNoMoreInteractions(gateway);
    }
    @Test void moduleListsOnlyRequestedProjectWithoutBuildingAnotherLogin() throws Exception {
        mvc.perform(post("/api/video/v1/cameras/query").contentType(MediaType.APPLICATION_JSON).content("{\"pId\":\"project-a\"}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.data[0].id").value("synthetic"))
                .andExpect(jsonPath("$.data[0].ptzSupported").value(true)).andExpect(jsonPath("$.data[0].deviceId").doesNotExist())
                .andExpect(jsonPath("$.mode").value("PLATFORM_MODULE"));
        mvc.perform(post("/api/video/v1/cameras/query").contentType(MediaType.APPLICATION_JSON).content("{\"pId\":\"project-b\"}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.total").value(0));
    }
    @Test void unknownCameraAndBrowserSuppliedUpstreamIdAreRejected() throws Exception {
        mvc.perform(post("/api/video/v1/preview").contentType(MediaType.APPLICATION_JSON).content("{\"sscId\":\"missing\",\"streamType\":\"MAIN\"}"))
                .andExpect(status().isNotFound());
        mvc.perform(post("/api/video/v1/preview").contentType(MediaType.APPLICATION_JSON).content("{\"sscId\":\"synthetic\",\"deviceId\":\"injected\"}"))
                .andExpect(status().isBadRequest());
    }
    @Test void actualLeaseEndpointsWorkInExplicitModuleMode() throws Exception {
        String acquired = mvc.perform(post("/api/video/v1/ptz/lease/acquire").contentType(MediaType.APPLICATION_JSON)
                .content("{\"sscId\":\"synthetic\"}")).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        String id = new com.fasterxml.jackson.databind.ObjectMapper().readTree(acquired).path("data").path("leaseId").asText();
        mvc.perform(post("/api/video/v1/ptz/lease/acquire").contentType(MediaType.APPLICATION_JSON).content("{\"sscId\":\"synthetic\"}"))
                .andExpect(status().isConflict());
        String body = "{\"sscId\":\"synthetic\",\"leaseId\":\"" + id + "\"}";
        mvc.perform(post("/api/video/v1/ptz/lease/renew").contentType(MediaType.APPLICATION_JSON).content(body)).andExpect(status().isOk());
        mvc.perform(post("/api/video/v1/ptz/lease/release").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk()).andExpect(jsonPath("$.data.status").value("ACCEPTED"));
    }
}
