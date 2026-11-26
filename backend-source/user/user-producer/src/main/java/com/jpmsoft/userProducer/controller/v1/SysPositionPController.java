package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.PositionPDto;
import com.jpmsoft.userProducer.service.ISysPositionPService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "项目岗位", value = "项目岗位")
@RequestMapping("/v1/OrganizationManage")
public class SysPositionPController {
    @Resource
    private ISysPositionPService sysPositionPService;

    @ApiOperation(value = "获取项目岗位列表")
    @PostMapping("/GetPosition_PList")
    public BaseResponse GetPosition_PList(@RequestBody PositionPDto request) {
        log.info("GetPosition_PList");
        return sysPositionPService.GetPosition_PList(request);
    }

    @ApiOperation(value = "城建查询项目组织机构集团本部或者项目管理单位所有岗位信息 入参jtOrGl传入jt则表示查询集团本部岗位信息")
    @GetMapping("/getProjectPositionP")
    public BaseResponse getProjectPositionP(@RequestParam String pId, @RequestParam String jtOrGl) {
        log.info("getProjectPositionP");
        return sysPositionPService.getProjectPositionP(pId, jtOrGl);
    }

    @ApiOperation(value = "用户ID获取其所有项目岗位")
    @GetMapping("/getPositionPByUid")
    public BaseResponse getPositionPByUid(@RequestParam String uId, @RequestParam(required = false) String pId) {
        log.info("getPositionPByUid");
        return sysPositionPService.getPositionPByUid(uId, pId);
    }


    /**
     * 新增or修改项目岗位信息
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "新增or修改项目岗位信息")
    @PostMapping("/CreateOrUpdatePosition_P")
    public BaseResponse CreateOrUpdatePosition_P(@RequestBody PositionPDto request) {
        log.info("CreateOrUpdatePosition_P");
        return sysPositionPService.CreateOrUpdatePosition_P(request);
    }


    /**
     * 删除项目岗位
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "删除项目岗位")
    @PostMapping("/DeletePosition_P")
    public BaseResponse DeletePosition_P(@RequestBody @Validated PositionPDto request) {
        log.info("DeletePosition_P");
        return sysPositionPService.DeletePosition_P(request);
    }


    /**
     * 根据项目部门ID获取项目岗位Select
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "根据项目部门ID获取项目岗位Select")
    @PostMapping("/GetP_PSelectByDID")
    public BaseResponse GetP_PSelectByDID(@RequestBody PositionPDto request) {
        log.info("GetP_PSelectByDID");
        return sysPositionPService.GetP_PSelectByDID(request);
    }

    @ApiOperation(value = "角色查询项目名称、所属组织名称、岗位名称")
    @GetMapping("/getPositionPByRole")
    public BaseResponse getPositionPByRole(@RequestParam String roleId) {
        log.info("getPositionPByRole");
        return sysPositionPService.getPositionPByRole(roleId);
    }

}
