package com.jpmsoft.projectConsumer.controller.v1.design;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignConceptModel;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "方案设计报审", value = "方案设计报审")
@RequestMapping("/design/v1/designConcept")
public class DesignConceptController {
    @Autowired
    private BaseFeign baseFeign;

    @ApiOperation(value = "新增或修改方案设计报审")
    @PostMapping("/createOrUpdateDesignConcept")
    public BaseResponse createOrUpdateDesignConcept(@RequestBody DesignConceptModel request) {
        log.info("getDesignConceptList");
        return baseFeign.createOrUpdateDesignConcept(request);
    }

    @ApiOperation(value = "分页查询方案设计报审")
    @PostMapping("/getDesignConceptList")
    public BaseResponse getDesignConceptList(@RequestBody DesignConceptModel request, @RequestHeader String token) {
        log.info("getDesignConceptList");

        return baseFeign.getDesignConceptList(request, token);
    }

    @ApiOperation(value = "分页查询方案设计报审-不鉴权")
    @PostMapping("/getDesignConceptListNoAuth")
    public BaseResponse getDesignConceptListNoAuth(@RequestBody DesignConceptModel request) {
        log.info("getDesignConceptListNoAuth");
        return baseFeign.getDesignConceptListNoAuth(request);
    }

    @ApiOperation(value = "ID查询方案设计报审")
    @GetMapping("/getDesignConceptById")
    public BaseResponse getDesignConceptById(@RequestParam String dcId) {
        log.info("getDesignConceptById");
        return baseFeign.getDesignConceptById(dcId);
    }

    @ApiOperation(value = "获取方案设计报审的流程状态")
    @PostMapping("/updateDesignConceptApproveStatus")
    public BaseResponse updateDesignConceptApproveStatus(@RequestBody DesignConceptModel request) {
        log.info("updateDesignConceptApproveStatus");
        return baseFeign.updateDesignConceptApproveStatus(request);
    }

}
