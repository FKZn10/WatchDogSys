package com.jpmsoft.projectConsumer.controller.v1.NewOA;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.NewOA.PreProjectRegister_NewOaRequest;
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
@RequestMapping("/v1/NewOA")
public class PreProjectRegister_NewOaController {
    @Autowired
    private PreFeign preFeign;

    /**
     * 创建修改
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "创建修改项目注册")
    @PostMapping("/CreateOrUpdatePreProjectRegisterNewOa")
    public BaseResponse CreateOrUpdatePreProjectRegister_NewOa(@RequestBody PreProjectRegister_NewOaRequest request) {
        log.info("CreateOrUpdatePreProjectRegister_NewOa");
        return preFeign.CreateOrUpdatePreProjectRegister_NewOa(request);
    }

    /**
     * 删除
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "删除项目注册")
    @PostMapping("/deletePreProjectRegisterNewOa")
    public BaseResponse deletePreProjectRegister_NewOa(@RequestBody PreProjectRegister_NewOaRequest request) {
        log.info("deletePreProjectRegister_NewOa");
        return preFeign.deletePreProjectRegister_NewOa(request);
    }

    /**
     * 审批
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "审批项目注册")
    @PostMapping("/approvalPreProjectRegisterNewOa")
    public BaseResponse approvalPreProjectRegister_NewOa(@RequestBody PreProjectRegister_NewOaRequest request) {
        log.info("approvalPreProjectRegister_NewOa");
        return preFeign.approvalPreProjectRegister_NewOa(request);
    }

    /**
     * 分页查询项目注册
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "分页查询项目注册")
    @PostMapping("/getPreProjectRegisterListNewOa")
    public BaseResponse getPreProjectRegister_NewOaList(@RequestBody PreProjectRegister_NewOaRequest request) {
        log.info("getPreProjectRegister_NewOaList");
        return preFeign.getPreProjectRegister_NewOaList(request);
    }

    @ApiOperation(value = "分页查询项目注册（统计批复匡算投资(立项)）")
    @PostMapping("/getPreProjectRegisterStatisticsListNewOa")
    public BaseResponse getPreProjectRegister_NewOaStatisticsList(@RequestBody PreProjectRegister_NewOaRequest request) {
        log.info("getPreProjectRegister_NewOaStatisticsList");
        return preFeign.getPreProjectRegister_NewOaStatisticsList(request);
    }

    @ApiOperation(value = "组织机构部门单位鉴权列表查询")
    @PostMapping("/getPreProjectRegisterByDepCompanyNewOa")
    public BaseResponse getPreProjectRegister_NewOaByDepCompany(@RequestBody PreProjectRegister_NewOaRequest request, @RequestHeader String token) {
        log.info("getPreProjectRegister_NewOaByDepCompanyNewOa");
        return preFeign.getPreProjectRegister_NewOaByDepCompany(request, token);
    }

    /**
     * ID查询单条项目注册
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "ID查询单条项目注册")
    @PostMapping("/getPreProjectRegisterByIdNewOa")
    public BaseResponse getPreProjectRegister_NewOaById(@RequestBody PreProjectRegister_NewOaRequest request) {
        log.info("getPreProjectRegister_NewOaById");
        return preFeign.getPreProjectRegister_NewOaById(request);
    }

    @ApiOperation(value = "ID查询单条项目注册（项目资金来源万元）")
    @PostMapping("/getPreProjectRegisterWYByIdNewOa")
    public BaseResponse getPreProjectRegister_NewOaWYById(@RequestBody PreProjectRegister_NewOaRequest request) {
        log.info("getPreProjectRegister_NewOaWYById");
        return preFeign.getPreProjectRegister_NewOaWYById(request);
    }
}
