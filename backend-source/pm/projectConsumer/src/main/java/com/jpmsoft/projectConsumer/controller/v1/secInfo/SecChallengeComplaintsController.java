package com.jpmsoft.projectConsumer.controller.v1.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecChallengeComplaintsRequest;
import com.jpmsoft.projectConsumer.feign.SecFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "中标质疑投诉处理", value = "中标质疑投诉处理")
@RequestMapping("/v1/challenge")
public class SecChallengeComplaintsController {
    @Autowired
    private SecFeign secFeign;

    @ApiOperation(value = "创建或修改中标质疑投诉处理")
    @PostMapping("/createOrUpdateSecChallengeComplaints")
    public BaseResponse createOrUpdateSecChallengeComplaints(@RequestBody SecChallengeComplaintsRequest request) {
        log.info("createOrUpdateSecChallengeComplaints");
        return secFeign.createOrUpdateSecChallengeComplaints(request);
    }

    @ApiOperation(value = "ID查询中标质疑投诉处理")
    @GetMapping("/getSecChallengeComplaintsById")
    public BaseResponse getSecChallengeComplaintsById(@RequestParam String sccId) {
        log.info("getSecChallengeComplaintsById");
        return secFeign.getSecChallengeComplaintsById(sccId);
    }

    @ApiOperation(value = "分页查询中标质疑投诉处理")
    @PostMapping("/getSecChallengeComplaintsList")
    public BaseResponse getSecChallengeComplaintsList(@RequestBody SecChallengeComplaintsRequest request) {
        log.info("getSecChallengeComplaintsList");
        return secFeign.getSecChallengeComplaintsList(request);
    }

    @ApiOperation(value = "分页查询中标质疑投诉处理(默认项目标段一对多)")
    @PostMapping("/getSecChallengeComplaintsListOne2Many")
    public BaseResponse getSecChallengeComplaintsListOne2Many(@RequestBody SecChallengeComplaintsRequest request) {
        log.info("getSecChallengeComplaintsListOne2Many");
        return secFeign.getSecChallengeComplaintsListOne2Many(request);
    }
}
