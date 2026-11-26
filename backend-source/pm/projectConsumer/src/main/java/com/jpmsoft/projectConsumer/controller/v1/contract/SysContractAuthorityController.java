package com.jpmsoft.projectConsumer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ContractAuthorityBathDTO;
import com.jpmsoft.common.model.request.SysContractAuthorityRequest;
import com.jpmsoft.projectConsumer.feign.ConFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@Api(tags = "合同权限配置", value = "合同权限配置")
@RequestMapping("/v1/contractAuth")
public class SysContractAuthorityController {
    @Autowired
    private ConFeign conFeign;

    @ApiOperation(value = "创建合同权限配置")
    @PostMapping("/createContractAuthority")
    public BaseResponse createContractAuthority(@RequestBody SysContractAuthorityRequest request) {
        log.info("createContractAuthority");
        return conFeign.createContractAuthority(request);
    }

    @ApiOperation(value = "批量创建或删除(根据批量合同ID和批量用户ID或者单位ID|action：delete、add)")
    @PostMapping("/createOrDelAuthBath")
    public BaseResponse createOrDelAuthBath(@RequestBody ContractAuthorityBathDTO request, @RequestHeader String  token){
        log.info("createOrDelAuthBath");
        return conFeign.createOrDelAuthBath(request,token);
    }

    @ApiOperation(value = "ID批量删除")
    @PostMapping("/deleteContractAuthBath")
    public BaseResponse deleteContractAuthBath (@RequestBody List<String> ids) {
        log.info("deleteContractAuthBath");
        return conFeign.deleteContractAuthBath(ids);
    }

    @ApiOperation(value = "ID删除合同权限配置")
    @GetMapping("/deleteContractAuthorityByCaId")
    public BaseResponse deleteContractAuthorityByCaId(@RequestParam String caId) {
        log.info("deleteContractAuthorityByCaId");
        return conFeign.deleteContractAuthorityByCaId(caId);
    }

    @ApiOperation(value = "分页查询合同权限配置")
    @PostMapping("/getContractAuthorityList")
    public BaseResponse getContractAuthorityList(@RequestBody SysContractAuthorityRequest request, @RequestHeader String token) {
        log.info("getContractAuthorityList");
        return conFeign.getContractAuthorityList(request, token);
    }
}
