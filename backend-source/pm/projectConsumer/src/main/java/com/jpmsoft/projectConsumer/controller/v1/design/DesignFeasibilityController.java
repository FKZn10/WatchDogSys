package com.jpmsoft.projectConsumer.controller.v1.design;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignFeasibilityModel;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "可研报告", value = "可研报告")
@RequestMapping("/design/v1/designFeasibility")
public class DesignFeasibilityController {
    @Autowired
    private BaseFeign baseFeign;

    @ApiOperation(value = "新增或修改可研报告")
    @PostMapping("/createOrUpdateDesignFeasibility")
    public BaseResponse createOrUpdateDesignFeasibility(@RequestBody DesignFeasibilityModel request) {
        log.info("getDesignFeasibilityList");
        return baseFeign.createOrUpdateDesignFeasibility(request);
    }

    @ApiOperation(value = "分页查询可研报告")
    @PostMapping("/getDesignFeasibilityList")
    public BaseResponse getDesignFeasibilityList(@RequestBody DesignFeasibilityModel request, @RequestHeader String token) {
        log.info("getDesignFeasibilityList");
        return baseFeign.getDesignFeasibilityList(request, token);
    }

    @ApiOperation(value = "分页查询可研报告-不鉴权")
    @PostMapping("/getDesignFeasibilityListNoAuth")
    public BaseResponse getDesignFeasibilityListNoAuth(@RequestBody DesignFeasibilityModel request) {
        log.info("getDesignFeasibilityListNoAuth");
        return baseFeign.getDesignFeasibilityListNoAuth(request);
    }

    @ApiOperation(value = "ID查询可研报告")
    @GetMapping("/getDesignFeasibilityById")
    public BaseResponse getDesignFeasibilityById(@RequestParam String dfId) {
        log.info("getDesignFeasibilityById");
        return baseFeign.getDesignFeasibilityById(dfId);
    }

    @ApiOperation(value = "获取可研报告的流程状态")
    @PostMapping("/updateDesignFeasibilityApproveStatus")
    public BaseResponse updateDesignFeasibilityApproveStatus(@RequestBody DesignFeasibilityModel request) {
        log.info("updateDesignFeasibilityApproveStatus");
        return baseFeign.updateDesignFeasibilityApproveStatus(request);
    }

}
