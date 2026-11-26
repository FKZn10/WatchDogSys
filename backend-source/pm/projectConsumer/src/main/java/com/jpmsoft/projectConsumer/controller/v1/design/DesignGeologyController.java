package com.jpmsoft.projectConsumer.controller.v1.design;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignGeologyModel;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "地质勘察", value = "地质勘察")
@RequestMapping("/design/v1/designGeology")
public class DesignGeologyController {
    @Autowired
    private BaseFeign baseFeign;

    @ApiOperation(value = "新增或修改地质勘察")
    @PostMapping("/createOrUpdateDesignGeology")
    public BaseResponse createOrUpdateDesignGeology(@RequestBody DesignGeologyModel request) {
        log.info("getDesignGeologyList");
        return baseFeign.createOrUpdateDesignGeology(request);
    }

    @ApiOperation(value = "分页查询地质勘察")
    @PostMapping("/getDesignGeologyList")
    public BaseResponse getDesignGeologyList(@RequestBody DesignGeologyModel request, @RequestHeader String token) {
        log.info("getDesignGeologyList");
        return baseFeign.getDesignGeologyList(request, token);
    }

    @ApiOperation(value = "分页查询地质勘察-不鉴权")
    @PostMapping("/getDesignGeologyListNoAuth")
    public BaseResponse getDesignGeologyListNoAuth(@RequestBody DesignGeologyModel request) {
        log.info("getDesignGeologyListNoAuth");
        return baseFeign.getDesignGeologyListNoAuth(request);
    }

    @ApiOperation(value = "ID查询地质勘察")
    @GetMapping("/getDesignGeologyById")
    public BaseResponse getDesignGeologyById(@RequestParam String dgId) {
        log.info("getDesignGeologyById");
        return baseFeign.getDesignGeologyById(dgId);
    }
}
