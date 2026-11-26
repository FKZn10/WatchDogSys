package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.ImportTDepartmentDto;
import com.jpmsoft.userProducer.pojo.dto.TDepartmentDto;
import com.jpmsoft.userProducer.service.ISysTDepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "部门模板", value = "部门模板")
@RequestMapping("/v1/OrganizationManage")
public class SysTDepartmentController {
    @Resource
    private ISysTDepartmentService sysTDepartmentService;

    @ApiOperation(value = "部门模板树形集合")
    @GetMapping("/getT_Department_TreeList")
    public BaseResponse getT_Department_TreeList(@RequestParam(required = false) String cId,
                                                 @RequestParam(required = false) String toId) {
        log.info("getT_Department_TreeList");
        return sysTDepartmentService.getT_Department_TreeList(cId, toId);
    }

    /**
     * 新增修改删除部门模板
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "新增修改删除部门模板")
    @PostMapping("/CUDT_Department")
    public BaseResponse CUDT_Department(@RequestBody TDepartmentDto request) {
        log.info("CUDT_Department");
        return sysTDepartmentService.CUDT_Department(request);
    }

    /**
     * 同级项目部门拖动改变排序
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "同级项目部门拖动改变排序")
    @PostMapping("/OnDropT_Department")
    public BaseResponse OnDropT_Department(@RequestBody TDepartmentDto request) {
        log.info("OnDropT_Department");
        return sysTDepartmentService.OnDropT_Department(request);
    }

    /**
     * 导入项目部门模板
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "导入项目部门模板")
    @PostMapping("/ImportT_Department")
    public BaseResponse ImportT_Department(@RequestBody ImportTDepartmentDto request) {
        log.info("ImportT_Department");
        return sysTDepartmentService.ImportT_Department(request);
    }

    @ApiOperation(value = "导入项目部门模板,已存在项目部门则更新补充差异部门")
    @PostMapping("/syncTDepartment")
    public BaseResponse syncTDepartment(@RequestBody ImportTDepartmentDto request) {
        log.info("syncTDepartment");
        return sysTDepartmentService.syncTDepartment(request);
    }

    /**
     * 同步部分项目部门模板到指定模板
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "同步部分项目部门模板到指定模板")
    @PostMapping("/syncTDepartmentToOrganization")
    public BaseResponse syncTDepartmentToOrganization(@RequestBody ImportTDepartmentDto request) {
        log.info("syncTDepartmentToOrganization");
        return sysTDepartmentService.syncTDepartmentToOrganization(request);
    }
}
