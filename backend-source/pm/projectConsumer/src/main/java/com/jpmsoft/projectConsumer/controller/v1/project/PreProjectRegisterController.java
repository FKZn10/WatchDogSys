package com.jpmsoft.projectConsumer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.project.PreProjectRegisterRequest;
import com.jpmsoft.projectConsumer.feign.PreFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@Api(tags = "项目注册", value = "项目注册")
@RequestMapping("/v1/projectRegister")
public class PreProjectRegisterController {
    @Autowired
    private PreFeign preFeign;

    /**
     * 创建修改
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "创建修改项目注册")
    @PostMapping("/CreateOrUpdatePreProjectRegister")
    public BaseResponse CreateOrUpdatePreProjectRegister(@RequestBody PreProjectRegisterRequest request) {
        log.info("CreateOrUpdatePreProjectRegister");
        return preFeign.CreateOrUpdatePreProjectRegister(request);
    }

    /**
     * 删除
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "删除项目注册")
    @PostMapping("/deletePreProjectRegister")
    public BaseResponse deletePreProjectRegister(@RequestBody PreProjectRegisterRequest request) {
        log.info("deletePreProjectRegister");
        return preFeign.deletePreProjectRegister(request);
    }

    /**
     * 审批
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "审批项目注册")
    @PostMapping("/approvalPreProjectRegister")
    public BaseResponse approvalPreProjectRegister(@RequestBody PreProjectRegisterRequest request) {
        log.info("approvalPreProjectRegister");
        return preFeign.approvalPreProjectRegister(request);
    }

    /**
     * 分页查询项目注册
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "分页查询项目注册")
    @PostMapping("/getPreProjectRegisterList")
    public BaseResponse getPreProjectRegisterList(@RequestBody PreProjectRegisterRequest request) {
        log.info("getPreProjectRegisterList");
        return preFeign.getPreProjectRegisterList(request);
    }

    @ApiOperation(value = "分页查询项目注册（统计批复匡算投资(立项)）")
    @PostMapping("/getPreProjectRegisterStatisticsList")
    public BaseResponse getPreProjectRegisterStatisticsList(@RequestBody PreProjectRegisterRequest request) {
        log.info("getPreProjectRegisterStatisticsList");
        return preFeign.getPreProjectRegisterStatisticsList(request);
    }

    @ApiOperation(value = "组织机构部门单位鉴权列表查询")
    @PostMapping("/getPreProjectRegisterByDepCompany")
    public BaseResponse getPreProjectRegisterByDepCompany(@RequestBody PreProjectRegisterRequest request, @RequestHeader String token) {
        log.info("getPreProjectRegisterByDepCompany");
        return preFeign.getPreProjectRegisterByDepCompany(request, token);
    }

    /**
     * ID查询单条项目注册
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "ID查询单条项目注册")
    @PostMapping("/getPreProjectRegisterById")
    public BaseResponse getPreProjectRegisterById(@RequestBody PreProjectRegisterRequest request) {
        log.info("getPreProjectRegisterById");
        return preFeign.getPreProjectRegisterById(request);
    }

    @ApiOperation(value = "ID查询单条项目注册（项目资金来源万元）")
    @PostMapping("/getPreProjectRegisterWYById")
    public BaseResponse getPreProjectRegisterWYById(@RequestBody PreProjectRegisterRequest request) {
        log.info("getPreProjectRegisterWYById");
        return preFeign.getPreProjectRegisterWYById(request);
    }
}
