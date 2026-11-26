package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.annotations.TenantId;
import com.jpmsoft.userProducer.pojo.dto.PositionDto;
import com.jpmsoft.userProducer.service.ISysPositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "职位", value = "职位")
@RequestMapping("/v1/OrganizationManage")
public class SysPositionController {
    @Resource
    private ISysPositionService sysPositionService;

    /**
     * 根据部门获取岗位信息
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "根据部门获取岗位信息")
    @PostMapping("/GetPositionList")
    public BaseResponse GetPositionList(@RequestBody PositionDto request) {
        log.info("GetPositionList");
        return sysPositionService.GetPositionList(request);
    }

    /**
     * 根据部门ID获取岗位Select
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "根据部门ID获取岗位Select")
    @PostMapping("/GetPSelectByDID")
    public BaseResponse GetPSelectByDID(@RequestBody PositionDto request) {
        log.info("GetPSelectByDID");
        return sysPositionService.GetPSelectByDID(request);
    }

    /**
     * 新增or修改岗位信息
     *
     * @param request
     * @return
     */
    @TenantId
    @ApiOperation(value = "新增or修改岗位信息")
    @PostMapping("/CreateOrUpdatePosition")
    public BaseResponse CreateOrUpdatePosition(@RequestBody PositionDto request) {
        log.info("CreateOrUpdatePosition");
        return sysPositionService.CreateOrUpdatePosition(request);
    }

    /**
     * 删除岗位
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "删除岗位")
    @PostMapping("/DeletePosition")
    public BaseResponse DeletePosition(@RequestBody PositionDto request) {
        log.info("DeletePosition");
        return sysPositionService.DeletePosition(request);
    }

    @ApiOperation(value = "设置责任岗位")
    @GetMapping("/setPositionDuty")
    public BaseResponse setPositionDuty(@RequestParam String pId, @RequestParam boolean enable) {
        log.info("setDuty");
        return sysPositionService.setDuty(pId, enable);
    }

    @ApiOperation(value = "角色ID查询关联的部门岗位信息")
    @GetMapping("/getPositionByRole")
    public BaseResponse getPositionByRole(@RequestParam String roleId) {
        log.info("getPositionByRole");
        return sysPositionService.getPositionByRole(roleId);
    }
}
