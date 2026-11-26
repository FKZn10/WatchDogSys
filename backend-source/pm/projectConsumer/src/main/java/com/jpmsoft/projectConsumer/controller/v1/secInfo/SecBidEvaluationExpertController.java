package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecBidEvaluationExpertRequest;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "评标专家构成", value = "评标专家构成")
@RequestMapping("/v1/bee")
public class SecBidEvaluationExpertController {
    @Autowired
    private SecFeign secFeign;


    @ApiOperation(value = "创建或修改评标专家构成")
    @PostMapping("/createOrUpdateSecBidEvaluationExpert")
    public BaseResponse createOrUpdateSecBidEvaluationExpert(@RequestBody SecBidEvaluationExpertRequest request) {
        log.info("createOrUpdateSecBidEvaluationExpert");
        return secFeign.createOrUpdateSecBidEvaluationExpert(request);
    }

    @ApiOperation(value = "ID查询评标专家构成")
    @GetMapping("/getSecBidEvaluationExpertById")
    public BaseResponse getSecBidEvaluationExpertById(@RequestParam String beeId) {
        log.info("getSecBidEvaluationExpertById");
        return secFeign.getSecBidEvaluationExpertById(beeId);
    }

    @ApiOperation(value = "分页查询评标专家构成")
    @PostMapping("/getSecBidEvaluationExpertList")
    public BaseResponse getSecBidEvaluationExpertList(@RequestBody SecBidEvaluationExpertRequest request) {
        log.info("getSecBidEvaluationExpertList");
        return secFeign.getSecBidEvaluationExpertList(request);
    }
}
