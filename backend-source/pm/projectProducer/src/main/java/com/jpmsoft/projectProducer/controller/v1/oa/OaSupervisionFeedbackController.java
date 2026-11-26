package com.jpmsoft.projectProducer.controller.v1.oa;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Oa.OaSupervisionFeedbackRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.oa.IOaSupervisionFeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/OA/SupervisionFeedback")
public class OaSupervisionFeedbackController {
    @Resource
    private IOaSupervisionFeedbackService oaSupervisionFeedbackService;

    @TenantId
    @PostMapping("/createOrUpdateOaSupervisionFeedback")
    public BaseResponse createOrUpdateOaSupervisionFeedback(@RequestBody OaSupervisionFeedbackRequest request) {
        log.info("createOrUpdateOaSupervisionFeedback");
        return oaSupervisionFeedbackService.createOrUpdateOaSupervisionFeedback(request);
    }

    @TenantId
    @PostMapping("/getOaSupervisionFeedbackList")
    public BaseResponse getOaSupervisionFeedbackList(@RequestBody OaSupervisionFeedbackRequest request) {
        log.info("getOaSupervisionFeedbackList");
        return oaSupervisionFeedbackService.getOaSupervisionFeedbackList(request);
    }

    @GetMapping("/getOaSupervisionFeedbackBySfId")
    public BaseResponse getOaSupervisionFeedbackBySfId(@RequestParam String sfId) {
        log.info("getOaSupervisionFeedbackBySfId");
        return oaSupervisionFeedbackService.getOaSupervisionFeedbackBySfId(sfId);
    }
}
