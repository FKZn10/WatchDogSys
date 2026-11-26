package com.jpmsoft.videoaccess;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.videoaccess.api.ApiResponse;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class CommonResponseContractTest {
    private final ObjectMapper json = new ObjectMapper();
    @Test void companyCommonSuccessAndAcceptedResponsesPreserveTheFrontendEnvelope() throws Exception {
        ApiResponse<String> response = ApiResponse.ok("fixture", 3, "MODULE");
        assertThat(response).isInstanceOf(BaseResponse.class);
        JsonNode body = json.readTree(json.writeValueAsString(response));
        assertThat(body.size()).isEqualTo(8);
        assertThat(body.path("statusCode").asInt()).isEqualTo(200);
        assertThat(body.path("success").asBoolean()).isTrue();
        assertThat(body.path("data").asText()).isEqualTo("fixture");
        assertThat(body.path("total").asInt()).isEqualTo(3);
        assertThat(body.path("serverErrorMsg").isNull()).isTrue();
        assertThat(body.path("mode").asText()).isEqualTo("MODULE");
        body = json.readTree(json.writeValueAsString(ApiResponse.accepted("accepted", "MODULE")));
        assertThat(body.path("message").asText()).contains("受理").contains("不代表设备已执行");
        assertThat(body.path("success").asBoolean()).isTrue();
    }
    @Test void companyCommonErrorsKeepHttpStatusMeaningAndNeverPopulateInternalExceptionDetails() throws Exception {
        for (int status : new int[]{400, 409, 410, 501, 502, 503, 504}) {
            JsonNode body = json.readTree(json.writeValueAsString(ApiResponse.error(status, "FIXTURE_ERROR", "safe message", "MODULE")));
            assertThat(body.path("statusCode").asInt()).isEqualTo(status);
            assertThat(body.path("success").asBoolean()).isFalse();
            assertThat(body.path("errorCode").asText()).isEqualTo("FIXTURE_ERROR");
            assertThat(body.path("data").isNull()).isTrue();
            assertThat(body.path("serverErrorMsg").isNull()).isTrue();
        }
    }
}
