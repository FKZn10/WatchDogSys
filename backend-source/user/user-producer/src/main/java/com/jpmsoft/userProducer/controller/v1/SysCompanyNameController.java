package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.CompanyNameDto;
import com.jpmsoft.userProducer.service.ISysCompanyNameService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@Api(tags = "单位更名管理", value = "单位更名管理")
@RequestMapping("/v1/OrganizationManage")
public class SysCompanyNameController {

    @Resource
    private ISysCompanyNameService sysCompanyNameService;


    /**
     * 获取单位更名信息列表
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取单位更名信息列表")
    @PostMapping("/GetCompanyNameList")
    public BaseResponse GetCompanyNameList(@RequestBody CompanyNameDto request) {
        log.info("GetCompanyNameList");
        return sysCompanyNameService.GetCompanyNameList(request);
    }

    /**
     * 新增or修改单位更名记录
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "新增or修改单位更名记录")
    @PostMapping("/CreateOrUpdateCompanyName")
    public BaseResponse CreateOrUpdateCompanyName(@RequestBody CompanyNameDto request) {
        log.info("CreateOrUpdateCompanyName");
        return sysCompanyNameService.CreateOrUpdateCompanyName(request);
    }

    /**
     * 删除单位更名记录
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "删除单位更名记录")
    @PostMapping("/DeleteCompanyName")
    public BaseResponse DeleteCompanyName(@RequestBody @Validated CompanyNameDto request) {
        log.info("DeleteCompanyName");
        return sysCompanyNameService.DeleteCompanyName(request);
    }

    @ApiOperation(value = "获取单位曾用名List")
    @GetMapping("/getCompanyNameUsedBeforeListById")
    public BaseResponse getCompanyNameUsedBeforeListById(@RequestParam String cId) {
        log.info("getCompanyNameUsedBeforeListById");
        return sysCompanyNameService.getCompanyNameUsedBeforeListById(cId);
    }
}
