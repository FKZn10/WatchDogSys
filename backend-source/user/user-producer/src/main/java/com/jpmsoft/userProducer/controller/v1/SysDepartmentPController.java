package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.UserDepartmentPRequest;
import com.jpmsoft.common.model.requestAndResponse.SafeResponsibility.SafeResponsibilityPostPersonDTO;
import com.jpmsoft.userProducer.pojo.dto.DepartmentPDto;
import com.jpmsoft.userProducer.service.IFlowManageService;
import com.jpmsoft.userProducer.service.ISysDepartmentPService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "项目部门", value = "项目部门")
@RequestMapping("/v1/OrganizationManage")
public class SysDepartmentPController {

    @Resource
    private ISysDepartmentPService sysDepartmentPService;
    @Resource
    private IFlowManageService flowManageService;

    @PostMapping("/getDepartment_PTreeList")
    public BaseResponse getDepartment_PTreeList(@RequestBody DepartmentPDto request) {
        log.info("getDepartment_PTreeList");
        return sysDepartmentPService.getDepartment_PTreeList(request);
    }


    @PostMapping("/CUDDepartment_P")
    public BaseResponse CUDDepartment_P(@RequestBody @Validated DepartmentPDto request) {
        log.info("CUDDepartment_P");
        return sysDepartmentPService.CUDDepartment_P(request);
    }


    @PostMapping("/OnDropDepartment_P")
    public BaseResponse OnDropDepartment_P(@RequestBody DepartmentPDto request) {
        log.info("OnDropDepartment_P");
        return sysDepartmentPService.OnDropDepartment_P(request);
    }


    @GetMapping("/getDepartmentResponseList")
    public BaseResponse getDepartmentResponseList(@RequestHeader(required = false) String token) {
        log.info("getDepartmentResponseList");
        return flowManageService.getDepartmentResponseList(token);
    }

    @ApiOperation(value = "复制项目组织机构 copyFrom 来源项目ID copyTo目标项目ID")
    @GetMapping("/copyProjectDepartmentP")
    public BaseResponse copyProjectDepartmentP(@RequestParam String copyFrom, @RequestParam String copyTo) {
        log.info("copyProjectDepartmentP");
        return sysDepartmentPService.copyProjectDepartmentP(copyFrom, copyTo);
    }

    @ApiOperation(value = "获取用户项目组织机构关联的所有项目ID集合")
    @GetMapping("/getUserAllSysDepPProject")
    public List<String> getUserAllSysDepPProject(@RequestParam String uId) {
        log.info("getUserAllSysDepPProject");
        return sysDepartmentPService.getUserAllSysDepPProject(uId);
    }


    @PostMapping("/AddUserDepartmentP")
    public BaseResponse AddUserDepartmentP(@RequestBody UserDepartmentPRequest request) {
        log.info("AddUserDepartmentP");
        return sysDepartmentPService.AddUserDepartmentP(request);
    }

    @PostMapping("/deleteOwnerDepartmentP")
    public BaseResponse deleteOwnerDepartmentP(@RequestBody DepartmentPDto request) {
        log.info("deleteOwnerDepartmentP");
        return sysDepartmentPService.deleteOwnerDepartmentP(request);
    }

    @GetMapping("/syncPpOwnerUnit")
    public BaseResponse syncPpOwnerUnit(@RequestParam("pId") String pId, @RequestParam("pdCode") String pdCode, @RequestParam("ownerUnit") String ownerUnit) {
        log.info("syncPpOwnerUnit");
        return sysDepartmentPService.syncPpOwnerUnit(pId, pdCode, ownerUnit);
    }

    @ApiOperation("同步部分项目部门到所有项目")
    @PostMapping("/syncDepartmentToProject")
    public BaseResponse syncDepartmentToProject(@RequestBody DepartmentPDto request) {
        log.info("syncDepartmentToProject");
        return sysDepartmentPService.syncDepartmentToProject(request);
    }

    /**
     * 根据项目ID和单位IDs获取项目部门编码
     *
     * @param pId
     * @param cIds
     * @return
     */
    @ApiOperation("查询单位所属项目组织机构编码")
    @GetMapping("/getPdCodeByCIds")
    public BaseResponse getPdCodeByCIds(@RequestParam("pId") String pId, @RequestParam("cIds") String cIds) {
        log.info("getPdCodeByCIds");
        return sysDepartmentPService.getPdCodeByCIds(pId, cIds);
    }

    @ApiOperation("同步安全责任人员岗位")
    @PostMapping("/syncSafeResponsibilityPostPerson")
    public BaseResponse syncSafeResponsibilityPostPerson(@RequestBody SafeResponsibilityPostPersonDTO request) {
        log.info("syncSafeResponsibilityPostPerson");
        return sysDepartmentPService.syncSafeResponsibilityPostPerson(request);
    }
}
