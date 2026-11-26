package com.jpmsoft.projectConsumer.controller.v1.peInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.PeInfo.PeGradeRuleRequest;
import com.jpmsoft.projectConsumer.feign.PeInfoFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "评价等级规则", value = "评价等级规则")
@RequestMapping("/v1/peGradeRule")
public class PeGradeRuleController {
    @Autowired
    private PeInfoFeign peInfoFeign;

    @ApiOperation(value = "创建或修改评价等级规则")
    @PostMapping("/createOrUpdatePeGradeRule")
    public BaseResponse createOrUpdatePeGradeRule(@RequestBody PeGradeRuleRequest request) {
        log.info("createOrUpdatePeGradeRule");
        return peInfoFeign.createOrUpdatePeGradeRule(request);
    }

    @ApiOperation(value = "获取评价等级规则列表")
    @PostMapping("/getPeGradeRuleList")
    public BaseResponse getPeGradeRuleList(@RequestBody PeGradeRuleRequest request, @RequestHeader String token) {
        log.info("getPeGradeRuleList");
        return peInfoFeign.getPeGradeRuleList(request, token);
    }

    @ApiOperation(value = "ID删除评价等级规则列表")
    @GetMapping("/deletePeGradeRuleByRId")
    public BaseResponse deletePeGradeRuleByRId(@RequestParam String rId) {
        log.info("deletePeGradeRuleByRId");
        return peInfoFeign.deletePeGradeRuleByRId(rId);
    }
}
