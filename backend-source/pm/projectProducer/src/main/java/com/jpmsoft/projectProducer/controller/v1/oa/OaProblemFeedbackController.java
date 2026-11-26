package com.jpmsoft.projectProducer.controller.v1.oa;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaProblemFeedbackModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.oa.IOaProblemFeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/OA/ProblemFeedback")
public class OaProblemFeedbackController {
    @Resource
    private IOaProblemFeedbackService oaProblemFeedbackService;

    @TenantId
    @PostMapping("/createOrUpdateOaProblemFeedback")
    public BaseResponse createOrUpdateOaProblemFeedback(@RequestBody OaProblemFeedbackModel request) {
        log.info("createOrUpdateOaProblemFeedback");
        return oaProblemFeedbackService.createOrUpdateOaProblemFeedback(request);
    }

    @TenantId
    @PostMapping("/getOaProblemFeedbackList")
    public BaseResponse getOaProblemFeedbackList(@RequestBody OaProblemFeedbackModel request) {
        log.info("getOaProblemFeedbackList");
        return oaProblemFeedbackService.getOaProblemFeedbackList(request);
    }

    @GetMapping("/getOaProblemFeedbackById")
    public BaseResponse getOaProblemFeedbackById(@RequestParam String pmfId) {
        log.info("getOaProblemFeedbackById");
        return oaProblemFeedbackService.getOaProblemFeedbackById(pmfId);
    }

    @GetMapping("/approve2RefreshProblemStatus")
    public BaseResponse approve2RefreshProblemStatus(@RequestParam String pmfId) {
        log.info("approve2RefreshProblemStatus");
        return oaProblemFeedbackService.approve2RefreshProblemStatus(pmfId);
    }

    @GetMapping("/getOaProblemFeedbackByPmdId")
    public BaseResponse getOaProblemFeedbackByPmdId(@RequestParam String pmdId) {
        log.info("getOaProblemFeedbackByPmdId");
        return oaProblemFeedbackService.getOaProblemFeedbackByPmdId(pmdId);
    }
}
