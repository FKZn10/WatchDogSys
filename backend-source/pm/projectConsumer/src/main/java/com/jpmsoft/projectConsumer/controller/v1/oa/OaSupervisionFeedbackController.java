package com.jpmsoft.projectConsumer.controller.v1.oa;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Oa.OaSupervisionFeedbackRequest;
import com.jpmsoft.projectConsumer.feign.OaFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "整改反馈记录", value = "整改反馈记录")
@RequestMapping("/OA/SupervisionFeedback")
public class OaSupervisionFeedbackController {
    @Autowired
    private OaFeign oaFeign;

    @ApiOperation(value = "新增或修改整改反馈记录")
    @PostMapping("/createOrUpdateOaSupervisionFeedback")
    public BaseResponse createOrUpdateOaSupervisionFeedback(@RequestBody OaSupervisionFeedbackRequest request) {
        log.info("createOrUpdateOaSupervisionFeedback");
        return oaFeign.createOrUpdateOaSupervisionFeedback(request);
    }

    @ApiOperation(value = "分页查询整改反馈记录")
    @PostMapping("/getOaSupervisionFeedbackList")
    public BaseResponse getOaSupervisionFeedbackList(@RequestBody OaSupervisionFeedbackRequest request) {
        log.info("getOaSupervisionFeedbackList");
        return oaFeign.getOaSupervisionFeedbackList(request);
    }

    @ApiOperation(value = "ID查询整改反馈记录")
    @GetMapping("/getOaSupervisionFeedbackBySfId")
    public BaseResponse getOaSupervisionFeedbackBySfId(@RequestParam String sfId) {
        log.info("getOaSupervisionFeedbackBySfId");
        return oaFeign.getOaSupervisionFeedbackBySfId(sfId);
    }
}
