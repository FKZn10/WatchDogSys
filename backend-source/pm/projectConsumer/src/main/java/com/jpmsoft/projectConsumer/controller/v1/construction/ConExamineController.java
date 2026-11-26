package com.jpmsoft.projectConsumer.controller.v1.construction;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Construction.ConExamineModel;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@Api(tags = "施工管理-施工（准备）审核", value = "施工管理-施工（准备）审核")
@RequestMapping("/v1/conExamine")
public class ConExamineController {
    @Autowired
    private BaseFeign baseFeign;

    @ApiOperation(value = "新增或修改施工（准备）审核")
    @PostMapping("/createOrUpdateConExamine")
    public BaseResponse createOrUpdateConExamine(@RequestBody ConExamineModel request) {
        log.info("createOrUpdateConExamine");
        return baseFeign.createOrUpdateConExamine(request);
    }

    @ApiOperation(value = "分页查询施工（准备）审核")
    @PostMapping("/getConExamineList")
    public BaseResponse getConExamineList(@RequestBody ConExamineModel request, @RequestHeader String token) {
        log.info("getConExamineList");
        return baseFeign.getConExamineList(request, token);
    }

    @ApiOperation(value = "ID查询施工（准备）审核")
    @GetMapping("/getConExamineById")
    public BaseResponse getConExamineById(@RequestParam String exId) {
        log.info("getConExamineById");
        return baseFeign.getConExamineById(exId);
    }
}
