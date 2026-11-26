package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.entity.SysFlowExplain;
import com.jpmsoft.userProducer.service.ISysFlowExplainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@Api(tags = "审批常用语", value = "审批常用语")
@RestController
@RequestMapping("/v1/flowExplain")
public class SysFlowExplainController {

    @Resource
    private ISysFlowExplainService sysFlowExplainService;

    @ApiOperation(value = "新增修改审批常用语")
    @PostMapping("/createOrUpdateFlowExplain")
    public BaseResponse createOrUpdateFlowExplain(@RequestBody SysFlowExplain request) {
        log.info("createOrUpdateFlowExplain");
        return sysFlowExplainService.createOrUpdateFlowExplain(request);
    }

    @ApiOperation(value = "查询审批常用语")
    @PostMapping("/getFlowExplainList")
    public BaseResponse getFlowExplainList(@RequestBody SysFlowExplain request) {
        log.info("getFlowExplainList");
        return sysFlowExplainService.getFlowExplainList(request);
    }


    @ApiOperation(value = "ID删除审批常用语")
    @GetMapping("/delFlowExplainById")
    public BaseResponse delFlowExplainById(@RequestParam String feId) {
        log.info("delFlowExplainById");
        return sysFlowExplainService.delFlowExplainById(feId);
    }
}
