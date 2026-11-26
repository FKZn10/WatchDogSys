package com.jpmsoft.projectConsumer.controller.v1.oa;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaProblemFeedbackModel;
import com.jpmsoft.projectConsumer.feign.OaFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "OA问题登记反馈-问题反馈", value = "OA问题登记反馈-问题反馈")
@RequestMapping("/OA/ProblemFeedback")
public class OaProblemFeedbackController {
    @Autowired
    private OaFeign oaFeign;

    @ApiOperation(value = "新增或修改问题反馈")
    @PostMapping("/createOrUpdateOaProblemFeedback")
    public BaseResponse createOrUpdateOaProblemFeedback(@RequestBody OaProblemFeedbackModel request) {
        log.info("createOrUpdateOaProblemFeedback");
        return oaFeign.createOrUpdateOaProblemFeedback(request);
    }

    @ApiOperation(value = "分页查询问题反馈")
    @PostMapping("/getOaProblemFeedbackList")
    public BaseResponse getOaProblemFeedbackList(@RequestBody OaProblemFeedbackModel request) {
        log.info("getOaProblemFeedbackList");
        return oaFeign.getOaProblemFeedbackList(request);
    }

    @ApiOperation(value = "ID查询问题反馈")
    @GetMapping("/getOaProblemFeedbackById")
    public BaseResponse getOaProblemFeedbackById(@RequestParam String pmfId) {
        log.info("getOaProblemFeedbackById");
        return oaFeign.getOaProblemFeedbackById(pmfId);
    }

    @ApiOperation(value = "审批后刷新问题清单解决状态")
    @GetMapping("/approve2RefreshProblemStatus")
    public BaseResponse approve2RefreshProblemStatus(@RequestParam String pmfId) {
        log.info("approve2RefreshProblemStatus");
        return oaFeign.approve2RefreshProblemStatus(pmfId);
    }

    @ApiOperation(value = "问题详情ID查询问题反馈")
    @GetMapping("/getOaProblemFeedbackByPmdId")
    public BaseResponse getOaProblemFeedbackByPmdId(@RequestParam String pmdId) {
        log.info("getOaProblemFeedbackByPmdId");
        return oaFeign.getOaProblemFeedbackByPmdId(pmdId);
    }
}
