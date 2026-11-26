package com.jpmsoft.projectConsumer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.pojo.dto.contract.ConContractChangeDTO;
import com.jpmsoft.projectConsumer.feign.ConFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/9/18 14:48
 */
@Slf4j
@RestController
@Api(tags = "合同变更", value = "合同变更")
@RequestMapping("/v1/contractChange")
public class ConContractChangeController {
    @Resource
    private ConFeign conFeign;

    @ApiOperation(value = "新增或修改合同变更")
    @PostMapping("/createOrUpdateConContractChange")
    public BaseResponse createOrUpdateConContractChange(@RequestBody ConContractChangeDTO request) {
        log.info("createOrUpdateConContractChange");
        return conFeign.createOrUpdateConContractChange(request);
    }

    @ApiOperation(value = "分页查询合同变更")
    @PostMapping("/getContractChangeList")
    public BaseResponse getContractChangeList(@RequestBody ConContractChangeDTO request, @RequestHeader String token) {
        log.info("getContractChangeList");
        return conFeign.getContractChangeList(request, token);
    }

    @ApiOperation(value = "ID查询合同变更")
    @GetMapping("/getContractChangeById")
    public BaseResponse getContractChangeById(@RequestParam String id) {
        log.info("getContractChangeById");
        return conFeign.getContractChangeById(id);
    }

    @ApiOperation(value = "审批通过更新合同数据")
    @GetMapping("/approve2RefreshContract")
    public BaseResponse approve2RefreshContract(@RequestParam String id) {
        log.info("approve2RefreshContract");
        return conFeign.approve2RefreshContract(id);
    }
}
