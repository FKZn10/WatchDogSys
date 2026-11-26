package com.jpmsoft.projectProducer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InvestmentControl.InvBudgetDeclareRequest;
import com.jpmsoft.common.model.request.project.PreProjectRegisterRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.investment.IInvBudgetDeclareService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import io.lettuce.core.dynamic.annotation.Param;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/invBudgetDeclare")
public class InvBudgetDeclareController {

    @Resource
    private IInvBudgetDeclareService invBudgetDeclareService;

    @Resource
    private RedisUtils redisUtils;

    @TenantId
    @PostMapping("/createOrUpdateInvBudgetDeclare")
    public BaseResponse createOrUpdateInvBudgetDeclare(@RequestBody InvBudgetDeclareRequest request) {
        log.info("createOrUpdateInvBudgetDeclare");
        return invBudgetDeclareService.createOrUpdateInvBudgetDeclare(request);
    }

    @TenantId
    @PostMapping("/getInvBudgetDeclareList")
    public BaseResponse getInvBudgetDeclareList(@RequestBody InvBudgetDeclareRequest request, @RequestHeader String token) {
        log.info("getInvBudgetDeclareList");
        return invBudgetDeclareService.getInvBudgetDeclareList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getInvBudgetDeclareByBdId")
    public BaseResponse getInvBudgetDeclareByBdId(@RequestParam String bdId) {
        log.info("getInvBudgetDeclareByBdId");
        return invBudgetDeclareService.getInvBudgetDeclareByBdId(bdId);
    }

    @GetMapping("/deleteInvBudgetDeclareByBdId")
    public BaseResponse deleteInvBudgetDeclareByBdId(@RequestParam String bdId) {
        log.info("deleteInvBudgetDeclareByBdId");
        return invBudgetDeclareService.deleteInvBudgetDeclareByBdId(bdId);
    }

    @PostMapping("/getInvBudgetDeclareDtlByPIds")
    public BaseResponse getInvBudgetDeclareDtlByPIds(@RequestBody List<String> pIds) {
        log.info("getInvBudgetDeclareDtlByPIds");
        return invBudgetDeclareService.getInvBudgetDeclareDtlByPIds(pIds);
    }

    @GetMapping("/getInvBudgetDeclareByPId")
    public BaseResponse getInvBudgetDeclareByPId(@RequestParam String pId) {
        log.info("getInvBudgetDeclareDtlByPIds");
        return invBudgetDeclareService.getInvBudgetDeclareByPId(pId);
    }

    @GetMapping("/getInvBudgetDeclareAndDtlByPid")
    public BaseResponse getInvBudgetDeclareAndDtlByPid(@RequestParam String pId) {
        log.info("getInvBudgetDeclareAndDtlByPid");
        return invBudgetDeclareService.getInvBudgetDeclareAndDtlByPid(pId);
    }

    @GetMapping("/approve2PApproveBudget")
    public BaseResponse approve2PApproveBudget(@RequestParam String bdId) {
        log.info("approve2PApproveBudget");
        return invBudgetDeclareService.approve2PApproveBudget(bdId);
    }

    @GetMapping("/getProjectBudgetBalance")
    public BaseResponse getProjectBudgetBalance(@RequestParam String pId) {
        log.info("getProjectBudgetBalance");
        return invBudgetDeclareService.getProjectBudgetBalance(pId);
    }

    @GetMapping("/getConBudgetRate")
    public BaseResponse getConBudgetRate(@RequestParam String pId, @RequestParam BigDecimal conMoney) {
        log.info("getConBudgetRate");
        return invBudgetDeclareService.getConBudgetRate(pId, conMoney);
    }

    @GetMapping("/getInvBudegetCompanyByPid")
    public BaseResponse getInvBudegetCompanyByPid(@RequestParam String pId) {
        log.info("getInvBudegetCompanyByPid");
        return invBudgetDeclareService.getInvBudegetCompanyByPid(pId);
    }

    @GetMapping("/havingBudgetWithPid")
    public BaseResponse havingBudgetWithPid(@RequestParam String pId, @RequestParam(value = "type", required = false) String type) {
        log.info("havingBudgetWithPid");
        return BaseResponse.ok(invBudgetDeclareService.havingBudgetWithPid(pId,type));
    }

    @GetMapping("/getPreStageListByPIdAndType")
    public BaseResponse getPreStageListByPIdAndType(@RequestParam String pId,@RequestParam String type)
    {
        log.info("getPreStageListByPIdAndType");
        return BaseResponse.ok(invBudgetDeclareService.getPreStageListByPIdAndType(pId,type));
    }

    /**
     * 审批
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/approvalInvBudgetDeclare")
    public BaseResponse approvalInvBudgetDeclare(@RequestBody InvBudgetDeclareRequest request) {
        log.info("approvalInvBudgetDeclare");
        return invBudgetDeclareService.approvalInvBudgetDeclare(request);
    }


    /**
     * 获取概算清单详细数据
     */
    @GetMapping("/getInvBudgetDeclareDtoById")
    public BaseResponse getInvBudgetDeclareDtoById(@RequestParam String id)
    {
        log.info("getInvBudgetDeclareDtoById");
        return BaseResponse.ok(invBudgetDeclareService.getInvBudgetDeclareDtoById(id));
    }

    /**
     * 获取概算清单树
     * @param pId
     * @return
     */
    @GetMapping("/getInvTypeBudgetDeclareDtlTreeByPid")
    public BaseResponse getInvTypeBudgetDeclareDtlTreeByPid(@RequestParam String pId)
    {
        log.info("getInvTypeBudgetDeclareDtlTreeByPid");
        return BaseResponse.ok(invBudgetDeclareService.getInvTypeBudgetDeclareDtlTreeByPid(pId));
    }

    /**
     * 按项目ID获取概算详情（项目下已审批结束的概算必须唯一，多条直接报错）
     *
     * @param pId 项目ID
     * @return 概算详情
     */
    @GetMapping("/getInvBudgetDeclareDetailByPId")
    public BaseResponse getInvBudgetDeclareDetailByPId(@RequestParam String pId) {
        log.info("getInvBudgetDeclareDetailByPId");
        return invBudgetDeclareService.getInvBudgetDeclareDetailByPId(pId);
    }


}
