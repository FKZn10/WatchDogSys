package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.annotations.TenantId;
import com.jpmsoft.userProducer.pojo.dto.DepartmentDto;
import com.jpmsoft.userProducer.service.ISysDepartmentService;
import com.jpmsoft.userProducer.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "部门", value = "部门")
@RequestMapping("/v1/OrganizationManage")
public class SysDepartmentController {
    @Resource
    private ISysDepartmentService sysDepartmentService;
    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @ApiOperation(value = "获取部门树形集合")
    @PostMapping("/getDepartmentTreeList")
    public BaseResponse getDepartmentTreeList(@RequestBody DepartmentDto request) {
        log.info("getDepartmentTreeList");
        return sysDepartmentService.getDepartmentTreeList(request);
    }

    @ApiOperation("获取部门列表")
    @GetMapping("/getDepartmentList")
    public BaseResponse getDepartmentList(@RequestHeader String token) {
        log.info("getDepartmentList");
        return BaseResponse.ok(sysDepartmentService.getDepartmentList(redisUtils.getTeId(token)));
    }

    @TenantId
    @ApiOperation(value = "获取部门树形集合(北京工行专用)")
    @PostMapping("/getBjDepartmentTreeList")
    public BaseResponse getBjDepartmentTreeList(@RequestBody DepartmentDto request) {
        log.info("getBjDepartmentTreeList");
        return sysDepartmentService.getBjDepartmentTreeList(request);
    }

    @TenantId
    @ApiOperation(value = "新增修改删除部门")
    @PostMapping("/CUDDepartment")
    public BaseResponse CUDDepartment(@RequestBody @Validated DepartmentDto request) {
        log.info("CUDDepartment");
        return sysDepartmentService.CUDDepartment(request);
    }

    @TenantId
    @ApiOperation(value = "拖拽部门数据调换顺序")
    @PostMapping("/OnDropDepartment")
    public BaseResponse OnDropDepartment(@RequestBody DepartmentDto request) {
        log.info("OnDropDepartment");
        return sysDepartmentService.OnDropDepartment(request);
    }

    @ApiOperation(value = "获取用户所在组织机构下拉列表")
    @GetMapping("/getUserDepartmentSelect")
    public BaseResponse getUserDepartmentSelect(@RequestHeader String token) {
        log.info("getUserDepartmentSelect");
        return sysDepartmentService.getUserDepartmentSelect(redisUtils.getUid(token));
    }


    /**
     * 获取用户所有组织机构及其下机组织机构ID
     *
     * @param uId
     * @return
     */
    @GetMapping("/getAllChildDepartment")
    public List<String> getAllChildDepartment(@RequestParam String uId) {
        log.info("getAllChildDepartment");
        return sysDepartmentService.getAllChildDepartment(uId);
    }

    /**
     * 获取用户所在组织机构及其下级组织机构的单位部门的单位ID
     *
     * @param uId
     * @return
     */
    @GetMapping("/getAllChildDepCompany")
    public List<String> getAllChildDepCompany(@RequestParam String uId) {
        log.info("getAllChildDepCompany");
        return sysDepartmentService.getAllChildDepCompany(uId);
    }

    @ApiOperation(value = "获取用户所在组织机构")
    @GetMapping("/getUserDepartmentByUid")
    public BaseResponse getUserDepartmentByUid(String uId) {
        log.info("getUserDepartmentByUid");
        return sysDepartmentService.getUserDepartmentByUid(uId);
    }


    @ApiOperation(value = "获取用户所在组织机构ID")
    @GetMapping("/getUserDepIdsByUid")
    public BaseResponse getUserDepIdsByUid(String uId) {
        log.info("getUserDepIdsByUid");
        return sysDepartmentService.getUserDepIdsByUid(uId);
    }


    @ApiOperation(value = "根据项目id获取所属部门的所属单位id")
    @GetMapping("/getDepartmentCidByPid")
    public BaseResponse getDepartmentCidByPid(String pId) {
        log.info("getDepartmentCidByPid");
        return sysDepartmentService.getDepartmentCidByPid(pId);
    }

    @TenantId
    @ApiOperation(value = "获取部门树形集合")
    @PostMapping("/getLowCodeDepartmentTreeList")
    public BaseResponse getLowCodeDepartmentTreeList(@RequestBody DepartmentDto request) {
        log.info("getLowCodeDepartmentTreeList");
        return sysDepartmentService.getLowCodeDepartmentTreeList(request);
    }

    @TenantId
    @ApiOperation(value = "获取部门树形集合")
    @PostMapping("/getLowCodeDepartmentTreeListByLevel")
    public BaseResponse getLowCodeDepartmentTreeListByLevel(@RequestBody DepartmentDto request) {
        log.info("getLowCodeDepartmentTreeListByLevel");
        return sysDepartmentService.getLowCodeDepartmentTreeListByLevel(request);
    }

    @TenantId
    @ApiOperation(value = "获取部门树形集合")
    @PostMapping("/getLowCodeDepartmentList")
    public BaseResponse getLowCodeDepartmentList(@RequestBody DepartmentDto request) {
        log.info("getLowCodeDepartmentList");
        return sysDepartmentService.getLowCodeDepartmentList(request);
    }

    @ApiOperation(value = "根据部门ID获取其上级单位部门ID")
    @PostMapping("/getUserCompanyDep")
    public BaseResponse getUserCompanyDep(@RequestBody List<String> departmentIds, @RequestHeader String token) {
        log.info("getUserCompanyDep");
        return sysDepartmentService.getUserCompanyDep(departmentIds, token);
    }
}
