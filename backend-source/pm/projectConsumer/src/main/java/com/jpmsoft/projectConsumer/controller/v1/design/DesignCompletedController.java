package com.jpmsoft.projectConsumer.controller.v1.design;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignCompletedModel;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "竣工图设计", value = "竣工图设计")
@RequestMapping("/design/v1/designCompleted")
public class DesignCompletedController {
    @Autowired
    private BaseFeign baseFeign;

    @ApiOperation(value = "新增或修改竣工图设计")
    @PostMapping("/createOrUpdateDesignCompleted")
    public BaseResponse createOrUpdateDesignCompleted(@RequestBody DesignCompletedModel request) {
        log.info("getDesignCompletedList");
        return baseFeign.createOrUpdateDesignCompleted(request);
    }

    @ApiOperation(value = "分页查询竣工图设计")
    @PostMapping("/getDesignCompletedList")
    public BaseResponse getDesignCompletedList(@RequestBody DesignCompletedModel request, @RequestHeader String token) {
        log.info("getDesignCompletedList");
        return baseFeign.getDesignCompletedList(request, token);
    }

    @ApiOperation(value = "分页查询竣工图设计-不鉴权")
    @PostMapping("/getDesignCompletedListNoAuth")
    public BaseResponse getDesignCompletedListNoAuth(@RequestBody DesignCompletedModel request) {
        log.info("getDesignCompletedListNoAuth");
        return baseFeign.getDesignCompletedListNoAuth(request);
    }

    @ApiOperation(value = "ID查询竣工图设计")
    @GetMapping("/getDesignCompletedById")
    public BaseResponse getDesignCompletedById(@RequestParam String dcId) {
        log.info("getDesignCompletedById");
        return baseFeign.getDesignCompletedById(dcId);
    }
}
