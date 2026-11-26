package com.jpmsoft.projectProducer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ContractAuthorityBathDTO;
import com.jpmsoft.common.model.request.SysContractAuthorityRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.contract.ISysContractAuthorityService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/v1/contractAuth")
public class SysContractAuthorityController {
    @Resource
    private ISysContractAuthorityService sysContractAuthorityService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createContractAuthority")
    public BaseResponse createContractAuthority(@RequestBody SysContractAuthorityRequest request) {
        log.info("createContractAuthority");
        return sysContractAuthorityService.createContractAuthority(request);
    }

    @TenantId
    @PostMapping("/createOrDelAuthBath")
    public BaseResponse createOrDelAuthBath(@RequestBody ContractAuthorityBathDTO request,@RequestHeader String  token)
    {
        log.info("createOrDelAuthBath");
        return sysContractAuthorityService.createOrDelAuthBath(request,redisUtils.getUserInfoByToken(token));
    }


    @PostMapping("/deleteContractAuthBath")
    public BaseResponse deleteContractAuthBath(@RequestBody List<String> ids)
    {
        log.info("deleteContractAuthBath");
        return sysContractAuthorityService.deleteContractAuthBath(ids);
    }

    @GetMapping("/deleteContractAuthorityByCaId")
    public BaseResponse deleteContractAuthorityByCaId(@RequestParam String caId) {
        log.info("deleteContractAuthorityByCaId");
        return sysContractAuthorityService.deleteContractAuthorityByCaId(caId);
    }

    @TenantId
    @PostMapping("/getContractAuthorityList")
    public BaseResponse getContractAuthorityList(@RequestBody SysContractAuthorityRequest request, @RequestHeader String token) {
        log.info("getContractAuthorityList");
        return sysContractAuthorityService.getContractAuthorityList(request, redisUtils.getUid(token));
    }


    /**
     * 根据单位获取其带权限的合同ID集合
     *
     * @param cId
     * @return
     */
    @GetMapping("/getConIdByCidFromContractAuthority")
    public List<String> getConIdByCidFromContractAuthority(@RequestParam String cId) {
        return sysContractAuthorityService.getConIdByCidFromContractAuthority(cId);
    }
}
