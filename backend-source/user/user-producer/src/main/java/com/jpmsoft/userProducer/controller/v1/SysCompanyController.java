package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.annotations.TenantId;
import com.jpmsoft.userProducer.pojo.dto.CompanyDto;
import com.jpmsoft.userProducer.service.ISysCompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@Api(tags = "单位", value = "单位")
@RestController
@RequestMapping("/v1/OrganizationManage")
public class SysCompanyController {

    @Resource
    private ISysCompanyService sysCompanyService;

    @ApiOperation(value = "获取单位select")
    @GetMapping("/GetCompanySelect")
    public BaseResponse GetCompanySelect(@RequestHeader(required = false) String token) {
        return sysCompanyService.GetCompanySelect(token);
    }

    @ApiOperation(value = "根据类型获取单位select")
    @GetMapping("/GetCompanySelectByType")
    public BaseResponse GetCompanySelectByType(@RequestParam String cType,@RequestHeader(required = false) String token) {
        return sysCompanyService.GetCompanySelectByType(cType,token);
    }

    @TenantId
    @ApiOperation(value = "获取单位列表")
    @PostMapping("/GetCompanyList")
    public BaseResponse GetCompanyList(@RequestBody CompanyDto data) {
        return sysCompanyService.GetCompanyList(data);
    }

    @TenantId
    @ApiOperation(value = "获取全部单位列表")
    @PostMapping("/GetAllCompanyList")
    public BaseResponse GetAllCompanyList(@RequestBody CompanyDto data) {
        return sysCompanyService.GetAllCompanyList(data);
    }

    @ApiOperation(value = "创建或修改单位")
    @TenantId
    @PostMapping("/CreateOrUpdateCompany")
    public BaseResponse CreateOrUpdateCompany(@RequestBody CompanyDto data) {
        return sysCompanyService.CreateOrUpdateCompany(data);
    }

    @ApiOperation(value = "国资单位数据接入")
    @PostMapping("/saveForGZ")
    public BaseResponse saveForGZ(@RequestBody CompanyDto data) {
        return sysCompanyService.saveForGZ(data);
    }

    @ApiOperation(value = "单位ID删除单位")
    @PostMapping("/DeleteCompany")
    public BaseResponse DeleteCompany(@RequestBody CompanyDto data) {
        return sysCompanyService.DeleteCompany(data);
    }

    @ApiOperation(value = "ID获取单位信息")
    @GetMapping("/getCompanyById")
    public BaseResponse getCompanyById(@RequestParam String id) {
        return sysCompanyService.getCompanyById(id);
    }

    @ApiOperation(value = "社会统一信用代码查询单位")
    @GetMapping("/getCompanyByCreditCode")
    public BaseResponse getCompanyByCreditCode(@RequestParam String code,@RequestHeader(required = false) String token) {
        return sysCompanyService.getCompanyByCreditCode(code,token);
    }

    @ApiOperation(value = "根据单位id,批量查询单位名称")
    @GetMapping("/GetCompanyNameByIds")
    public BaseResponse GetCompanyNameByIds(@RequestParam("ids") String ids) {
        log.info("getSecTendereeByIds");
        return sysCompanyService.GetCompanyNameByIds(ids);
    }

    /**
     * 根据pId查询项目组织机构单位信息
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "根据pId查询项目组织机构单位信息")
    @PostMapping("/getCompanyByPId")
    public BaseResponse getCompanyByPId(@RequestBody CompanyDto request) {
        return sysCompanyService.getCompanyByPId(request);
    }
}
