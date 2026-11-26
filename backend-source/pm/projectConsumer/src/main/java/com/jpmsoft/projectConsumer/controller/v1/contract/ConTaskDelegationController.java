package com.jpmsoft.projectConsumer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.contract.ConTaskDelegationDto;
import com.jpmsoft.projectConsumer.feign.ConFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: 李文涛 aaron
 * @date: 2022/9/5 14:32
 */
@Slf4j
@RestController
@Api(tags = "任务委托单", value = "任务委托单")
@RequestMapping("/v1/conTaskDelegation")
public class ConTaskDelegationController {

    @Autowired
    private ConFeign conFeign;


    @ApiOperation(value = "新增修改任务委托单")
    @PostMapping("/createOrUpdateConTaskDelegation")
    public BaseResponse createOrUpdateConTaskDelegation(@RequestBody ConTaskDelegationDto request) {
        log.info("createOrUpdateConTaskDelegation");
        return conFeign.createOrUpdateConTaskDelegation(request);
    }

    @ApiOperation(value = "分页查询任务委托单")
    @PostMapping("/getConTaskDelegationList")
    public BaseResponse getConTaskDelegationList(@RequestBody ConTaskDelegationDto request, @RequestHeader String token) {
        log.info("getConTaskDelegationList");
        return conFeign.getConTaskDelegationList(request, token);
    }

    @ApiOperation(value = "ID查询任务委托单")
    @GetMapping("/getConTaskDelegationById")
    public BaseResponse getConTaskDelegationById(@RequestParam String id) {
        log.info("getConTaskDelegationById");
        return conFeign.getConTaskDelegationById(id);
    }

    @ApiOperation(value = "合同ID查询审批通过的任务委托单")
    @GetMapping("/getConTaskDelegationByConId")
    public BaseResponse getConTaskDelegationByConId(@RequestParam String conId) {
        log.info("getConTaskDelegationByConId");
        return conFeign.getConTaskDelegationByConId(conId);
    }
}
