package com.jpmsoft.projectConsumer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConContractRequest;
import com.jpmsoft.common.model.request.Contract.ConContractRequestDto;
import com.jpmsoft.common.model.request.Contract.ConContractRequestNew;
import com.jpmsoft.common.model.request.pay.ConPayRequest;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConContractModel;
import com.jpmsoft.common.model.requestAndResponse.Contract.ContractLedgerForWJTParam;
import com.jpmsoft.common.model.requestAndResponse.Contract.ContractStampDutyModel;
import com.jpmsoft.projectConsumer.feign.ConFeign;
import com.jpmsoft.projectConsumer.pojo.dto.contract.ContractBudgetDto;
import com.jpmsoft.projectConsumer.pojo.dto.contract.ContractFundStatisticsDTO;
import com.jpmsoft.projectConsumer.pojo.dto.fundPlan.ProgressDeclarationMatchDTO;
import feign.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "合同", value = "合同")
@RequestMapping("/v1/contract")
public class ConContractController {
    @Resource
    private ConFeign conFeign;

    /**
     * 新增或修改合同
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "新增或修改合同")
    @PostMapping("/createOrUpdateContract")
    public BaseResponse createOrUpdateContract(@RequestBody ConContractRequest request) {
        log.info("createOrUpdateContract");
        return conFeign.createOrUpdateContract(request);
    }

    @ApiOperation(value = "国资平台合同数据接入")
    @PostMapping("/saveForGZ")
    public BaseResponse saveForGZ(@RequestBody ConContractRequestDto request) {
        log.info("saveForGZ");
        return conFeign.saveForGZ(request);
    }


    /**
     * 新增或修改合同
     * 北京工行推送数据专用
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "新增或修改合同 北京工行推送数据专用")
    @PostMapping("/BjCreateOrUpdateContract")
    public BaseResponse BjCreateOrUpdateContract(@RequestBody ConContractRequest request) {
        log.info("BjCreateOrUpdateContract");
        return conFeign.BjCreateOrUpdateContract(request);
    }

    @ApiOperation(value = "新增或修改合同 同步创建合同支付计划、合同金额明细")
    @PostMapping("/createOrUpdateContractWithMoneyPay")
    public BaseResponse createOrUpdateContractWithMoneyPay(@RequestBody ConContractModel request) {
        log.info("createOrUpdateContractWithMoneyPay");
        return conFeign.createOrUpdateContractWithMoneyPay(request);
    }

    /**
     * 获取合同列表
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取合同列表")
    @PostMapping("/getContractWithMoneyPayList")
    public BaseResponse getContractWithMoneyPayList(@RequestBody ConContractModel request, @RequestHeader String token) {
        log.info("getContractWithMoneyPayList");
        return conFeign.getContractWithMoneyPayList(request, token);
    }


    /**
     * 获取合同列表
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取合同列表")
    @PostMapping("/getContractList")
    public BaseResponse getContractList(@RequestBody ConContractRequest request, @RequestHeader String token) {
        log.info("getContractList");
        return conFeign.getContractList(request, token);
    }

    /**
     * 获取合同列表筛选项
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取合同列表筛选项")
    @PostMapping("/getContractListClassInfo")
    public BaseResponse getContractListClassInfo(@RequestBody ConContractRequest request, @RequestHeader String token) {
        log.info("getContractListClassInfo");
        return conFeign.getContractListClassInfo(request, token);
    }

    /**
     * 获取合同列表（广工）
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取合同列表（广工）")
    @PostMapping("/getContractGgList")
    public BaseResponse getContractGgList(@RequestBody ConContractRequest request, @RequestHeader String token) {
        log.info("getContractGgList");
        return conFeign.getContractGgList(request, token);
    }

    /**
     * 广工定制：通用合同、服务合同、货物合同、施工合同有补充合同后，在合同支付申请功能中，合同名称只能选择此项目名称中主合同，不能选择补充合同名称
     *
     * @param request
     * @return
     */
    @ApiOperation("获取主合同列表")
    @PostMapping("/getMainContractList")
    public BaseResponse getMainContractList(@RequestBody ConContractRequest request, @RequestHeader String token) {
        log.info("getMainContractList");
        return conFeign.getMainContractList(request, token);
    }

    @ApiOperation(value = "获取合同列表（新）")
    @PostMapping("/getContractListNew")
    public BaseResponse getContractListNew(@RequestBody ConContractRequestNew request, @RequestHeader String token) {
        log.info("getContractListNew");
        return conFeign.getContractListNew(request, token);
    }

    @ApiOperation(value = "合同列表查询(根据合同权限配置过滤)")
    @PostMapping("/getContractListByPermission")
    public BaseResponse getContractListByPermission(@RequestBody ConContractRequest request, @RequestHeader String token) {
        log.info("getContractListByPermission");
        return conFeign.getContractListByPermission(request, token);
    }

    /**
     * 危大工程获取合同列表
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取合同列表")
    @PostMapping("/getContractListForDangerousProject")
    public BaseResponse getContractListForDangerousProject(@RequestBody ConContractRequest request, @RequestHeader String token) {
        log.info("getContractListForDangerousProject");
        return conFeign.getContractListForDangerousProject(request, token);
    }

    @ApiOperation(value = "获取合同列表")
    @PostMapping("/getContractListNoAuth")
    public BaseResponse getContractListNoAuth(@RequestBody ConContractRequest request, @RequestHeader String token) {
        log.info("getContractListNoAuth");
        return conFeign.getContractListNoAuth(request, token);
    }

    @ApiOperation(value = "获取所有合同， 仅查询部分字段")
    @PostMapping("/getContractPartialDataList")
    public BaseResponse getContractPartialDataList(@RequestBody ConContractRequest request, @RequestHeader String token) {
        log.info("getContractPartialDataList");
        return conFeign.getContractPartialDataList(request, token);
    }

    @ApiOperation(value = "分页查询合同信息（概算清单信息，默认排除施工合同）")
    @PostMapping("/getContractBudgetList")
    public BaseResponse getContractBudgetList(@RequestBody ContractBudgetDto request, @RequestHeader String token) {
        return conFeign.getContractBudgetList(request, token);
    }

    /**
     * 项目id查询无概算合同汇总数据
     *
     * @param pId
     * @return
     */
    @ApiOperation(value = "项目id查询无概算合同汇总数据")
    @GetMapping("/getConBudgetSum")
    public BaseResponse getConBudgetSum(@RequestParam String pId) {
        return conFeign.getConBudgetSum(pId);
    }


    /**
     * 项目id查询合同总金额-智慧工地总仓
     *
     * @param pIds
     * @return
     */
    @ApiOperation(value = "项目id查询无概算合同汇总数据")
    @GetMapping("/getConMoneyTotal")
    public BaseResponse getConMoneyTotal(@RequestBody List<String> pIds) {
        return conFeign.getConMoneyTotal(pIds);
    }

    /**
     * ID查询合同详情
     *
     * @param conId
     * @return
     */
    @ApiOperation(value = "ID查询合同详情")
    @GetMapping("/getContractByConId")
    public BaseResponse getContractByConId(@RequestParam String conId) {
        log.info("getContractByConId");
        return conFeign.getContractByConId(conId);
    }

    @ApiOperation(value = "城建查询合同安全文明措施费")
    @GetMapping("/getContractSecurityMoney")
    public BaseResponse getContractSecurityMoney(@RequestParam String conId) {
        log.info("getContractSecurityMoney");
        return conFeign.getContractSecurityMoney(conId);
    }

    @ApiOperation(value = "漕湖结算带出信息查询")
    @GetMapping("/selectChContractSettlement")
    public BaseResponse selectChContractSettlement(@RequestParam String conId) {
        log.info("selectChContractSettlement");
        return conFeign.selectChContractSettlement(conId);
    }

    /**
     * ID查询合同详情
     *
     * @param conIds
     * @return
     */
    @ApiOperation(value = "ID集合查询合同详情")
    @PostMapping("/getContractByConIds")
    public BaseResponse getContractByConIds(@RequestBody List<String> conIds) {
        log.info("getContractByConIds");
        return conFeign.getContractByConIds(conIds);
    }


    @ApiOperation(value = "ID查询合同详情 以及合同支付计划、合同金额明细")
    @GetMapping("/getContractWithMoneyPayByConId")
    public BaseResponse getContractWithMoneyPayByConId(@RequestParam String conId) {
        log.info("getContractWithMoneyPayByConId");
        return conFeign.getContractWithMoneyPayByConId(conId);
    }

    @ApiOperation(value = "ID查询解除合同详情")
    @GetMapping("/getRelieveContractByConId")
    public BaseResponse getRelieveContractByConId(@RequestParam String conId) {
        log.info("getRelieveContractByConId");
        return conFeign.getRelieveContractByConId(conId);
    }

    /**
     * 删除合同
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除合同")
    @GetMapping("/deleteContractByIds")
    public BaseResponse deleteContractByIds(@RequestParam String ids) {
        log.info("deleteContractByIds");
        return conFeign.deleteContractByIds(ids);
    }

    /**
     * 合同审批
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "合同审批")
    @PostMapping("/approvalContract")
    public BaseResponse approvalContract(@RequestBody ConContractRequest request) {
        log.info("approvalContract");
        return conFeign.approvalContract(request);
    }

    /**
     * 北京工行
     * 合同支付审批时推送总包合同数据
     *
     * @param payId
     * @return
     */
    @ApiOperation(value = "合同支付审批时推送总包合同数据(北京工行)")
    @GetMapping("/BjPushContractWhenConPay")
    public BaseResponse BjPushContractWhenConPay(@RequestParam String payId) {
        log.info("BjPushContractWhenConPay");
        return conFeign.BjPushContractWhenConPay(payId);
    }

    @ApiOperation(value = "修改合同编码")
    @GetMapping("/updateContractCodeById")
    public BaseResponse updateContractCodeById(@RequestParam String conId, @RequestParam String conCode) {
        log.info("updateContractCodeById");
        return conFeign.updateContractCodeById(conId, conCode);
    }

    @ApiOperation(value = "维护合同关键信息")
    @PostMapping("/updateContractKeyInfoById")
    public BaseResponse updateContractKeyInfoById(@RequestBody ConContractRequest request) {
        log.info("updateContractKeyInfoById");
        return conFeign.updateContractKeyInfoById(request);
    }

    @ApiOperation(value = "更新合同概算申报清单ID")
    @GetMapping("/updateContractBudget")
    public BaseResponse updateContractBudget(@RequestParam String conId, @RequestParam String bddId) {
        log.info("updateContractBudget");
        return conFeign.updateContractBudget(conId, bddId);
    }


    @ApiOperation(value = "当前合同是否可以作废（目前仅为是否选择资金计划）")
    @GetMapping("/can2ProcessVoidingTheContract")
    public BaseResponse can2ProcessVoidingTheContract(@RequestParam String id) {
        return conFeign.can2ProcessVoidingTheContract(id);
    }

    @ApiOperation(value = "合同资金统计")
    @PostMapping("/getContractFundStatistics")
    public BaseResponse getContractFundStatistics(@RequestBody ContractFundStatisticsDTO request, @RequestHeader String token) {
        return conFeign.getContractFundStatistics(request, token);
    }

    @ApiOperation(value = "项目标段ID下是否存在未作废的合同(有true 无false)")
    @GetMapping("/secContractExist")
    public BaseResponse secContractExist(@RequestParam String secId, @RequestParam String pId) {
        return conFeign.secContractExist(secId, pId);
    }

    @ApiOperation(value = "根据项目、标段查询合同信息")
    @GetMapping("/getSecContract")
    public BaseResponse getSecContract(@RequestParam String secId, @RequestParam String pId) {
        log.info("getSecContract");
        return conFeign.getSecContract(secId, pId);
    }


    @ApiOperation(value = "项目id查询所有合同信息只需返回基本的合同名称，合同编码，合同id")
    @GetMapping("/getConContractByPid")
    public BaseResponse getConContractByPid(@RequestParam String id) {
        return conFeign.getConContractByPid(id);
    }

    @ApiOperation(value = " 查询合同金额(合同金额加减补充合同调整金额)")
    @GetMapping("/getContractMoney")
    public BaseResponse getContractMoney(@RequestParam String id) {
        return conFeign.getContractMoney(id);
    }

    @ApiOperation(value = "合同签订回调接口")
    @PostMapping("/updateConContractApproveStatus")
    public BaseResponse updateConContractApproveStatus(@RequestBody ConContractRequest request) {
        return conFeign.updateConContractApproveStatus(request);
    }

    @ApiOperation(value = "判断合同金额是否超过预警值")
    @GetMapping("/chekConAmountOver")
    public BaseResponse chekConAmountOver(@RequestParam String pId, @RequestParam BigDecimal amount) {
        return conFeign.chekConAmountOver(pId, amount);
    }

    @ApiOperation(value = "获取合同印花税明细")
    @PostMapping("/getContractStampDuty")
    public BaseResponse getContractStampDuty(@RequestBody ContractStampDutyModel data) {
        return conFeign.getContractStampDuty(data);
    }

    @ApiOperation(value = "合同印花税明细导出EXCEL")
    @PostMapping("/exportContractStampDuty")
    public void exportContractStampDuty(HttpServletResponse response, @RequestBody ContractStampDutyModel request, @RequestHeader String token) {
        log.info("exportContractStampDuty");
        Response feignResponse = conFeign.exportContractStampDuty(request, token);
        Response.Body body = feignResponse.body();
        try (InputStream inputStream = body.asInputStream();
             OutputStream outputStream = response.getOutputStream()) {
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, feignResponse.headers().get(HttpHeaders.CONTENT_DISPOSITION).stream().findFirst().get());
            response.setContentType(feignResponse.headers().get(HttpHeaders.CONTENT_TYPE).stream().findFirst().get());
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            log.error("exportContractStampDuty  error");
        }
    }


    @ApiOperation(value = "获取合同所有乙方单位")
    @GetMapping("/getConPayPartyBPayList")
    public BaseResponse getConPayPartyBPayList(@RequestParam String conId) {
        log.info("getConPayPartyBPayList");
        return conFeign.getConPayPartyBPayList(conId);
    }

    @PostMapping("/getContractLedgerForWJT")
    public BaseResponse getContractLedgerForWJT(@RequestBody ContractLedgerForWJTParam param) {
        return conFeign.getContractLedgerForWJT(param);
    }


    @ApiOperation(value = "维护合同信息（文教投）")
    @PostMapping("/updateContractInfoWJTById")
    public BaseResponse updateContractInfoWJTById(@RequestBody ConContractRequest request) {
        log.info("updateContractInfoWJTById");
        return conFeign.updateContractInfoWJTById(request);
    }


    @ApiOperation(value = "获取合同乙方单位合同价信息")
    @GetMapping("/slectConConsortiumDtlListByConId")
    public BaseResponse slectConConsortiumDtlListByConId(@RequestParam String conId) {
        log.info("slectConConsortiumDtlListByConId");
        return conFeign.slectConConsortiumDtlListByConId(conId);
    }


    @ApiOperation(value = "合同信息签订附件上传（文教投）")
    @PostMapping("/updateContractInfoWJTQD")
    public BaseResponse updateContractInfoWJTQD(@RequestBody ConContractRequest request) {
        log.info("updateContractInfoWJTQD");
        return conFeign.updateContractInfoWJTQD(request);
    }


    @ApiOperation(value = "获取竣工决算列表")
    @PostMapping("/getFinalAccountDataList")
    public BaseResponse getFinalAccountDataList(@RequestBody ProgressDeclarationMatchDTO dto, @RequestHeader String token) {
        log.info("getFinalAccountDataList");
        return conFeign.getFinalAccountDataList(dto, token);
    }

    @ApiOperation(value = "获取初步审计列表")
    @PostMapping("/getPreliminaryAuditDataList")
    public BaseResponse getPreliminaryAuditDataList(@RequestBody ProgressDeclarationMatchDTO dto, @RequestHeader String token) {
        log.info("getPreliminaryAuditDataList");
        return conFeign.getPreliminaryAuditDataList(dto, token);
    }


    @ApiOperation(value = "获取结算审计列表")
    @PostMapping("/getTCSettlementAuditDataList")
    public BaseResponse getTCSettlementAuditDataList(@RequestBody ProgressDeclarationMatchDTO dto, @RequestHeader String token) {
        log.info("getTCSettlementAuditDataList");
        return conFeign.getTCSettlementAuditDataList(dto, token);
    }

    @ApiOperation(value = "获取尾款支付列表")
    @PostMapping("/getBalancePaymentDataList")
    public BaseResponse getBalancePaymentDataList(@RequestBody ProgressDeclarationMatchDTO dto, @RequestHeader String token) {
        log.info("getBalancePaymentDataList");
        return conFeign.getBalancePaymentDataList(dto, token);
    }


    @PostMapping("/getBalancePaymentPlanDataList")
    public BaseResponse getBalancePaymentPlanDataList(@RequestBody ProgressDeclarationMatchDTO dto, @RequestHeader String token) {
        log.info("getBalancePaymentPlanDataList");
        return conFeign.getBalancePaymentPlanDataList(dto, token);
    }

    @PostMapping("/getConpayByIds")
    public BaseResponse getConpayByIds(@RequestBody ConPayRequest request){
        return conFeign.getConpayByIds(request);
    }

    @PostMapping("/getConpayByIdsGDGY")
    public BaseResponse getConpayByIdsGDGY(@RequestBody ConPayRequest request){
        return conFeign.getConpayByIdsGDGY(request);
    }

    @ApiOperation("获取项目投资控制台账（河北）")
    @PostMapping("/getInvestmentControlLedgerHB")
    public BaseResponse getInvestmentControlLedgerHB(@RequestBody ConContractRequest request) {
        log.info("getInvestmentControlLedgerHB");
        return conFeign.getInvestmentControlLedgerHB(request);
    }

    @ApiOperation("更新合同信息（只更改合同表）")
    @PostMapping("/updateContractById")
    public BaseResponse updateContractById(@RequestBody ConContractRequest request) {
        log.info("updateContractById");
        return conFeign.updateContractById(request);
    }

    @ApiOperation("合同统计")
    @PostMapping("/getContractStatistics")
    public BaseResponse getContractStatistics(@RequestBody ConContractRequest request) {
        log.info("getContractStatistics");
        return conFeign.getContractStatistics(request);
    }

    @ApiOperation(value = "根据乙方单位ID列表汇总已结束合同金额")
    @PostMapping("/getFinishedContractMoneyByCompanyIds")
    public BaseResponse getFinishedContractMoneyByCompanyIds(@RequestBody List<String> companyIds,
                                                              @RequestHeader("token") String token) {
        log.info("getFinishedContractMoneyByCompanyIds");
        return conFeign.getFinishedContractMoneyByCompanyIds(companyIds, token);
    }

    /**
     * 同步合同乙方单位到项目参建单位
     *
     * @param conId
     * @return
     */
    @ApiOperation("同步合同乙方单位到项目参建单位")
    @GetMapping("/syncToParticipantUnit")
    public BaseResponse syncToParticipantUnit(@RequestParam String conId, @RequestHeader String token) {
        log.info("syncToParticipantUnit");
        return conFeign.syncToParticipantUnit(conId, token);
    }
}
