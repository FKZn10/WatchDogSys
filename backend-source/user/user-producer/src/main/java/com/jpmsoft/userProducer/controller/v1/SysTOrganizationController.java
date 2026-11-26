package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.annotations.TenantId;
import com.jpmsoft.userProducer.pojo.dto.SysTOrganizationDto;
import com.jpmsoft.userProducer.service.ISysTOrganizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: 李文涛 aaron
 * @date: 2022/9/29 10:06
 */
@Slf4j
@RestController
@Api(tags = "项目组织机构模板", value = "项目组织机构模板")
@RequestMapping("/v1/OrganizationManage")
public class SysTOrganizationController {

    @Resource
    private ISysTOrganizationService sysTOrganizationService;


    @TenantId
    @ApiOperation(value = "创建修改项目组织机构模板")
    @PostMapping("/createOrUpdateSysTOrganization")
    public BaseResponse createOrUpdateSysTOrganization(@RequestBody SysTOrganizationDto request) {
        log.info("createOrUpdateSysTOrganization");
        return sysTOrganizationService.createOrUpdateSysTOrganization(request);
    }

    @TenantId
    @ApiOperation(value = "分页查询项目组织机构模板")
    @PostMapping("/getSysTOrganizationList")
    public BaseResponse getSysTOrganizationList(@RequestBody SysTOrganizationDto request) {
        log.info("getSysTOrganizationList");
        return sysTOrganizationService.getSysTOrganizationList(request);
    }

    @ApiOperation(value = "ID查询项目组织机构模板")
    @GetMapping("/getSysTOrganizationById")
    public BaseResponse getSysTOrganizationById(@RequestParam String id) {
        log.info("getSysTOrganizationById");
        return sysTOrganizationService.getSysTOrganizationById(id);
    }


    @ApiOperation(value = "ID删除项目组织机构模板")
    @GetMapping("/delTOrganizationById")
    public BaseResponse delTOrganizationById(@RequestParam String id) {
        log.info("delTOrganizationById");
        return sysTOrganizationService.delTOrganizationById(id);
    }


}
