package com.jpmsoft.projectConsumer.controller.v1.design;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.design.DesignPileDto;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 桩基检测
 * Created By:李文涛 aaron
 * Created Time:2022/6/22 14:18
 */
@Slf4j
@RestController
@Api(tags = "桩基检测", value = "桩基检测")
@RequestMapping("/design/v1/designPile")
public class DesignPileController {

    @Autowired
    private BaseFeign baseFeign;

    @ApiOperation(value = "新增或修改桩基检测")
    @PostMapping("/createOrUpdateDesignPile")
    public BaseResponse createOrUpdateDesignPile(@RequestBody DesignPileDto request) {
        log.info("createOrUpdateDesignPile");
        return baseFeign.createOrUpdateDesignPile(request);
    }

    @ApiOperation(value = "分页查询桩基检测")
    @PostMapping("/getDesignPileList")
    public BaseResponse getDesignPileList(@RequestBody DesignPileDto request, @RequestHeader String token) {
        log.info("getDesignPileList");
        return baseFeign.getDesignPileList(request, token);
    }

    @ApiOperation(value = "分页查询桩基检测-不鉴权")
    @PostMapping("/getDesignPileListNoAuth")
    public BaseResponse getDesignPileListNoAuth(@RequestBody DesignPileDto request) {
        log.info("getDesignPileListNoAuth");
        return baseFeign.getDesignPileListNoAuth(request);
    }

    @ApiOperation(value = "ID查询桩基检测")
    @GetMapping("/getDesignPileById")
    public BaseResponse getDesignPileById(@RequestParam String dpId) {
        log.info("getDesignPileById");
        return baseFeign.getDesignPileById(dpId);
    }
}
