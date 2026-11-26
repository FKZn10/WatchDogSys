package com.jpmsoft.projectConsumer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectConsumer.feign.ConFeign;
import com.jpmsoft.projectConsumer.pojo.dto.contract.ContractPermissionCompanyDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "合同权限配置-合同参与单位", value = "合同权限配置-合同参与单位")
@RequestMapping("/v1/contractPermissionCompany")
public class ContractPermissionCompanyController {

    @Resource
    private ConFeign conFeign;

    @ApiOperation(value = "添加一个合同权限配置参与单位")
    @GetMapping("/addPermissionCompany")
    public BaseResponse addPermissionCompany(@RequestParam String conId, @RequestParam String cId, @RequestHeader String token) {
        log.info("addPermissionCompany");
        return conFeign.addPermissionCompany(conId, cId, token);
    }

    @ApiOperation(value = "ID删除")
    @GetMapping("/deletePermissionCompanyById")
    public BaseResponse deletePermissionCompanyById(@RequestParam String id) {
        log.info("deletePermissionCompanyById");
        return conFeign.deletePermissionCompanyById(id);
    }

    @ApiOperation(value = "分页查询合同下的参与单位")
    @PostMapping("/getPermissionCompanyList")
    public BaseResponse getPermissionCompanyList(@RequestBody ContractPermissionCompanyDTO request) {
        log.info("getPermissionCompanyList");
        return conFeign.getPermissionCompanyList(request);
    }
}
