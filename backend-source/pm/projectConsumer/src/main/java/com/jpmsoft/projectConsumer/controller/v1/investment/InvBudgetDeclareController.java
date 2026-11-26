package com.jpmsoft.projectConsumer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InvestmentControl.InvBudgetDeclareRequest;
import com.jpmsoft.projectConsumer.feign.InvFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "概算申报", value = "概算申报")
@RequestMapping("/inv/v1/invBudgetDeclare")
public class InvBudgetDeclareController {
    @Resource
    private InvFeign invFeign;

    @ApiOperation(value = "新增或修改概算申报")
    @PostMapping("/createOrUpdateInvBudgetDeclare")
    public BaseResponse createOrUpdateInvBudgetDeclare(@RequestBody InvBudgetDeclareRequest request) {
        log.info("createOrUpdateInvBudgetDeclare");
        return invFeign.createOrUpdateInvBudgetDeclare(request);
    }

    @ApiOperation(value = "分页查询概算申报")
    @PostMapping("/getInvBudgetDeclareList")
    public BaseResponse getInvBudgetDeclareList(@RequestBody InvBudgetDeclareRequest request, @RequestHeader String token) {
        log.info("getInvBudgetDeclareList");
        return invFeign.getInvBudgetDeclareList(request, token);
    }

    @ApiOperation(value = "ID查询概算申报")
    @GetMapping("/getInvBudgetDeclareByBdId")
    public BaseResponse getInvBudgetDeclareByBdId(@RequestParam String bdId) {
        log.info("getInvBudgetDeclareByBdId");
        return invFeign.getInvBudgetDeclareByBdId(bdId);
    }

    @ApiOperation(value = "ID删除概算申报")
    @GetMapping("/deleteInvBudgetDeclareByBdId")
    public BaseResponse deleteInvBudgetDeclareByBdId(@RequestParam String bdId) {
        log.info("deleteInvBudgetDeclareByBdId");
        return invFeign.deleteInvBudgetDeclareByBdId(bdId);
    }

    @ApiOperation(value = "项目ID集合获取对应概算清单树")
    @PostMapping("/getInvBudgetDeclareDtlByPIds")
    public BaseResponse getInvBudgetDeclareDtlByPIds(@RequestBody List<String> pIds) {
        log.info("getInvBudgetDeclareDtlByPIds");
        return invFeign.getInvBudgetDeclareDtlByPIds(pIds);
    }

    @ApiOperation(value = "项目ID获取对应概算信息")
    @GetMapping("/getInvBudgetDeclareByPId")
    public BaseResponse getInvBudgetDeclareByPId(@RequestParam String pId) {
        log.info("getInvBudgetDeclareByPId");
        return invFeign.getInvBudgetDeclareByPId(pId);
    }

    @ApiOperation(value = "项目ID获取概算详情（项目下已审批结束的概算必须唯一，多条报错）")
    @GetMapping("/getInvBudgetDeclareDetailByPId")
    public BaseResponse getInvBudgetDeclareDetailByPId(@RequestParam String pId) {
        log.info("getInvBudgetDeclareDetailByPId");
        return invFeign.getInvBudgetDeclareDetailByPId(pId);
    }


    @ApiOperation(value = "项目ID获取概算单位信息")
    @GetMapping("/getInvBudegetCompanyByPid")
    public BaseResponse getInvBudegetCompanyByPid(@RequestParam String pId) {
        log.info("getInvBudegetCompanyByPid");
        return invFeign.getInvBudegetCompanyByPid(pId);
    }

    @ApiOperation(value = "项目ID获取概算审定金额减项目下合同金额汇总")
    @GetMapping("/getProjectBudgetBalance")
    public BaseResponse getProjectBudgetBalance(@RequestParam String pId) {
        log.info("getProjectBudgetBalance");
        return invFeign.getProjectBudgetBalance(pId);
    }


    @ApiOperation(value = "查询已使用概算金额百分比含本次合同金额")
    @GetMapping("/getConBudgetRate")
    public BaseResponse getConBudgetRate(@RequestParam String pId, @RequestParam BigDecimal conMoney) {
        log.info("getConBudgetRate");
        return invFeign.getConBudgetRate(pId, conMoney);
    }

    @ApiOperation(value = "项目ID获取概算和概算清单")
    @GetMapping("/getInvBudgetDeclareAndDtlByPid")
    public BaseResponse getInvBudgetDeclareAndDtlByPid(@RequestParam String pId) {
        log.info("getInvBudgetDeclareAndDtlByPid");
        return invFeign.getInvBudgetDeclareAndDtlByPid(pId);
    }

    @ApiOperation(value = "审批同步申请金额bdMoney到项目表的pApproveBudget")
    @GetMapping("/approve2PApproveBudget")
    public BaseResponse approve2PApproveBudget(@RequestParam String bdId) {
        log.info("approve2PApproveBudget");
        return invFeign.approve2PApproveBudget(bdId);
    }

    @ApiOperation(value = "检测项目是否存在非作废的概算")
    @GetMapping("/havingBudgetWithPid")
    public BaseResponse havingBudgetWithPid(@RequestParam String pId,@RequestParam(value = "type", required = false) String type) {
        log.info("havingBudgetWithPid");
        return invFeign.havingBudgetWithPid(pId,type);
    }

    @ApiOperation(value = "根据项目ID获取前置步骤数据")
    @GetMapping("/getPreStageListByPIdAndType")
    public BaseResponse getPreStageListByPIdAndType(@RequestParam String pId,@RequestParam String type) {
        log.info("getPreStageListByPIdAndType");
        return invFeign.getPreStageListByPIdAndType(pId,type);
    }

    @ApiOperation(value = "回调接口")
    @PostMapping("/approvalInvBudgetDeclare")
    public BaseResponse approvalInvBudgetDeclare(@RequestBody InvBudgetDeclareRequest request) {
        log.info("approvalInvBudgetDeclare");
        return invFeign.approvalInvBudgetDeclare(request);
    }


    @ApiOperation(value = "获取概算清单详细数据")
    @GetMapping("/getInvBudgetDeclareDtoById")
    public BaseResponse getInvBudgetDeclareDtoById(@RequestParam String id) {
        log.info("getInvBudgetDeclareDtoById");
        return invFeign.getInvBudgetDeclareDtoById(id);
    }

    @ApiOperation(value = "通过项目id获取概算清单树")
    @GetMapping("/getInvTypeBudgetDeclareDtlTreeByPid")
    public BaseResponse getInvTypeBudgetDeclareDtlTreeByPid(@RequestParam String pId)
    {
        log.info("getInvTypeBudgetDeclareDtlTreeByPid");
        return invFeign.getInvTypeBudgetDeclareDtlTreeByPid(pId);
    }

}
