package com.jpmsoft.projectConsumer.controller.v1.construction;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Construction.ConCompleteModel;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "施工管理-完工申请", value = "施工管理-完工申请")
@RequestMapping("/v1/conComplete")
public class ConCompleteController {
    @Autowired
    private BaseFeign baseFeign;

    @ApiOperation(value = "新增或修改完工申请")
    @PostMapping("/createOrUpdateConComplete")
    public BaseResponse createOrUpdateConComplete(@RequestBody ConCompleteModel request) {
        log.info("createOrUpdateConComplete");
        return baseFeign.createOrUpdateConComplete(request);
    }

    @ApiOperation(value = "分页查询完工申请")
    @PostMapping("/getConCompleteList")
    public BaseResponse getConCompleteList(@RequestBody ConCompleteModel request, @RequestHeader String token) {
        log.info("getConCompleteList");
        return baseFeign.getConCompleteList(request, token);
    }

    @ApiOperation(value = "ID查询完工申请")
    @GetMapping("/getConCompleteById")
    public BaseResponse getConCompleteById(@RequestParam String coId) {
        log.info("getConCompleteById");
        return baseFeign.getConCompleteById(coId);
    }
}
