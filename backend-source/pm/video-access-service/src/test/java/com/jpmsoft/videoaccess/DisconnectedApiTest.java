package com.jpmsoft.videoaccess;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DisconnectedApiTest {
    @Autowired MockMvc mvc;
    @Test void defaultModeDoesNotGrantAccessOrMasqueradeAsAnEmptyDatabase() throws Exception {
        mvc.perform(post("/api/video/v1/cameras/query").contentType(MediaType.APPLICATION_JSON)
                .content("{\"pId\":\"any-project\"}"))
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.errorCode").value("IDENTITY_NOT_CONFIGURED"))
                .andExpect(jsonPath("$.mode").value("DISCONNECTED"));
    }
}
