package com.jpmsoft.projectConsumer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.project.PreProjectChangeNameRequest;
import com.jpmsoft.projectConsumer.feign.PreFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "项目更名", value = "项目更名")
@RequestMapping("/v1/proChangeName")
public class PreProjectChangeNameController {
    @Autowired
    private PreFeign preFeign;

    /**
     * 新增或者修改
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "新增或者修改项目更名")
    @PostMapping("/CreateOrUpdateProChangeName")
    public BaseResponse CreateOrUpdateProChangeName(@RequestBody PreProjectChangeNameRequest request) {
        log.info("CreateOrUpdateProChangeName");
        return preFeign.CreateOrUpdateProChangeName(request);
    }

    /**
     * 分页查询
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "分页查询项目更名")
    @PostMapping("/getPreProjectChangeNameByPage")
    public BaseResponse getPreProjectChangeNameByPage(@RequestBody PreProjectChangeNameRequest request) {
        log.info("getPreProjectChangeNameByPage");
        return preFeign.getPreProjectChangeNameByPage(request);
    }

    @ApiOperation(value = "ID查询项目更名")
    @GetMapping("/getPreProjectChangeNameById")
    public BaseResponse getPreProjectChangeNameById(@RequestParam String pcnId) {
        log.info("getPreProjectChangeNameById");
        return preFeign.getPreProjectChangeNameById(pcnId);
    }

    @ApiOperation(value = "审批后同步项目名称以及历史名称")
    @GetMapping("/approve2RefreshProjectName")
    public BaseResponse approve2RefreshProjectName(@RequestParam String pcnId) {
        log.info("approve2RefreshProjectName");
        return preFeign.approve2RefreshProjectName(pcnId);
    }


    /**
     * 删除
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "删除项目更名")
    @PostMapping("/deleteProChangeNameById")
    public BaseResponse deleteProChangeNameById(@RequestBody PreProjectChangeNameRequest request) {
        log.info("deleteProChangeNameById");
        return preFeign.deleteProChangeNameById(request);
    }


    /**
     * 修改审批状态
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "修改项目更名审批状态（修改为审批通过会自动调用approve2RefreshProjectName方法）")
    @PostMapping("/updateProChangeNameFlowState")
    public BaseResponse updateProChangeNameFlowState(@RequestBody PreProjectChangeNameRequest request) {
        log.info("updateProChangeNameFlowState");
        return preFeign.updateProChangeNameFlowState(request);
    }

    @ApiOperation(value = "获取项目曾用名List")
    @GetMapping("/getProjectNameUsedBeforeListById")
    public BaseResponse getProjectNameUsedBeforeListById(@RequestParam String pId) {
        log.info("getProjectNameUsedBeforeListById");
        return preFeign.getProjectNameUsedBeforeListById(pId);
    }
}
