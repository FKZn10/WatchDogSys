package com.jpmsoft.projectProducer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.contract.ContractPermissionCompanyDTO;
import com.jpmsoft.projectProducer.service.contract.IContractPermissionCompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 合同权限配置-合同参与单位
 *
 * @Author: 李文涛 aaron
 * @date: 2025/2/6 16:03
 */
@Slf4j
@RestController
@RequestMapping("/v1/contractPermissionCompany")
public class ContractPermissionCompanyController {

    @Resource
    private IContractPermissionCompanyService contractPermissionCompanyService;

    @GetMapping("/addPermissionCompany")
    public BaseResponse addPermissionCompany(@RequestParam String conId, @RequestParam String cId, @RequestHeader String token) {
        log.info("addPermissionCompany");
        return contractPermissionCompanyService.addPermissionCompany(conId, cId, token);
    }

    @GetMapping("/deletePermissionCompanyById")
    public BaseResponse deletePermissionCompanyById(@RequestParam String id) {
        log.info("deletePermissionCompanyById");
        return contractPermissionCompanyService.deletePermissionCompanyById(id);
    }

    @PostMapping("/getPermissionCompanyList")
    public BaseResponse getPermissionCompanyList(@RequestBody ContractPermissionCompanyDTO request) {
        log.info("getPermissionCompanyList");
        return contractPermissionCompanyService.getPermissionCompanyList(request);
    }
}
