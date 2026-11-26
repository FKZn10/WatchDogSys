package com.jpmsoft.projectProducer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConContractRequest;
import com.jpmsoft.common.model.request.Contract.ConContractRequestDto;
import com.jpmsoft.common.model.request.Contract.ConContractRequestNew;
import com.jpmsoft.common.model.request.pay.ConPayRequest;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConContractModel;
import com.jpmsoft.common.model.requestAndResponse.Contract.ContractLedgerForWJTParam;
import com.jpmsoft.common.model.requestAndResponse.Contract.ContractStampDutyModel;
import com.jpmsoft.common.model.response.Contract.ConContractResponse;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.config.CommonResponse;
import com.jpmsoft.projectProducer.pojo.dto.contract.ContractBudgetDto;
import com.jpmsoft.projectProducer.pojo.dto.contract.ContractFundStatisticsDTO;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.ProgressDeclarationMatchDTO;
import com.jpmsoft.projectProducer.service.contract.IConContractService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/v1/contract")
public class ConContractController {
    @Resource
    private IConContractService conContractService;
    @Resource
    private RedisUtils redisUtils;

    /**
     * 新增或修改合同
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/createOrUpdateContract")
    public BaseResponse createOrUpdateContract(@RequestBody ConContractRequest request) {
        log.info("createOrUpdateContract");
        return conContractService.createOrUpdateContract(request);
    }

    /**
     * 国资平台合同数据接入
     *
     * @param request
     * @return
     */
    @PostMapping("/saveForGZ")
    public BaseResponse saveForGZ(@RequestBody ConContractRequestDto request) {
        log.info("saveForGZ");
        return conContractService.saveForGZ(request);
    }

    /**
     * 流程结束后更新合同编码
     *
     * @param id
     * @return
     */
    @GetMapping("/generateContractCode")
    public String generateContractCode(@RequestParam String id) {
        log.info("generateContractCode");
        return conContractService.generateContractCode(id);
    }

    /**
     * 判断合同是否可以作废
     *
     * @param id
     * @return
     */
    @GetMapping("/can2ProcessVoidingTheContract")
    public BaseResponse can2ProcessVoidingTheContract(@RequestParam String id) {
        log.info("can2ProcessVoidingTheContract");
        return BaseResponse.ok(conContractService.can2ProcessVoidingTheContract(id));
    }


    /**
     * 新增或修改合同
     * 北京工行推送数据专用
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/BjCreateOrUpdateContract")
    public BaseResponse BjCreateOrUpdateContract(@RequestBody ConContractRequest request) {
        log.info("BjCreateOrUpdateContract");
        return conContractService.BjCreateOrUpdateContract(request);
    }

    /**
     * 新增或修改合同
     * <p>
     * 同步创建合同支付计划、合同金额明细
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/createOrUpdateContractWithMoneyPay")
    public BaseResponse createOrUpdateContractWithMoneyPay(@RequestBody ConContractModel request) {
        log.info("createOrUpdateContractWithMoneyPay");
        return conContractService.createOrUpdateContractWithMoneyPay(request);
    }

    /**
     * 获取合同列表
     * 以及合同支付计划、合同金额明细
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/getContractWithMoneyPayList")
    public BaseResponse getContractWithMoneyPayList(@RequestBody ConContractModel request, @RequestHeader String token) {
        log.info("getContractWithMoneyPayList");
        return conContractService.getContractWithMoneyPayList(request, redisUtils.getUid(token));
    }

    /**
     * 获取合同列表
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/getContractList")
    public BaseResponse getContractList(@RequestBody ConContractRequest request, @RequestHeader String token) {
        log.info("getContractList");
        return conContractService.getContractList(request, redisUtils.getUid(token));
    }

    /**
     * 获取合同列表筛选项
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/getContractListClassInfo")
    public BaseResponse getContractListClassInfo(@RequestBody ConContractRequest request, @RequestHeader String token) {
        log.info("getContractListClassInfo");
        return conContractService.getContractListClassInfo(request, redisUtils.getUid(token));
    }

    /**
     * 获取合同列表（广工）
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/getContractGgList")
    public BaseResponse getContractGgList(@RequestBody ConContractRequest request, @RequestHeader String token) {
        log.info("getContractGgList");
        return conContractService.getContractGgList(request, redisUtils.getUid(token));
    }

    /**
     * 广工定制：①通用合同、服务合同、货物合同、施工合同有补充合同后，在合同支付申请功能中，合同名称只能选择此项目名称中主合同，不能选择补充合同名称
     *         ②选择主合同后，合同金额为主合同金额+补充合同金额
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/getMainContractList")
    public BaseResponse getMainContractList(@RequestBody ConContractRequest request, @RequestHeader String token) {
        log.info("getMainContractList");
        return conContractService.getMainContractList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getContractListNew")
    public BaseResponse getContractListNew(@RequestBody ConContractRequestNew request,@RequestHeader String token) {
        log.info("getContractListNew");
        return conContractService.getContractListNew(request,redisUtils.getUid(token));
    }


    @TenantId
    @PostMapping("/getContractListByPermission")
    public BaseResponse getContractListByPermission(@RequestBody ConContractRequest request, @RequestHeader String token) {
        log.info("getContractListByPermission");
        return conContractService.getContractListByPermission(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getContractListExcel")
    public List<ConContractResponse> getContractListExcel(@RequestBody ConContractRequest request, @RequestHeader String token) {
        log.info("getContractListExcel");
        return conContractService.getContractListExcel(request, redisUtils.getUid(token));
    }

    /**
     * 危大工程获取合同列表
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/getContractListForDangerousProject")
    public BaseResponse getContractListForDangerousProject(@RequestBody ConContractRequest request, @RequestHeader String token) {
        log.info("getContractListForDangerousProject");
        return conContractService.getContractListForDangerousProject(request, redisUtils.getUid(token));
    }


    @TenantId
    @PostMapping("/getContractListNoAuth")
    public BaseResponse getContractListNoAuth(@RequestBody ConContractRequest request, @RequestHeader String token) {
        log.info("getContractList");
        return conContractService.getContractListNoAuth(request, redisUtils.getUserInfoByToken(token));
    }

    @TenantId
    @PostMapping("/getContractPartialDataList")
    public BaseResponse getContractPartialDataList(@RequestBody ConContractRequest request, @RequestHeader String token) {
        log.info("getContractPartialDataList");
        return conContractService.getContractPartialDataList(request, redisUtils.getUserInfoByToken(token));
    }

    /**
     * 概算合同分页查询
     *
     * @param request
     * @param token
     * @return
     */
    @TenantId
    @PostMapping("/getContractBudgetList")
    public BaseResponse getContractBudgetList(@RequestBody ContractBudgetDto request, @RequestHeader String token) {
        return conContractService.getContractBudgetList(request, redisUtils.getUid(token));
    }

    /**
     * 项目id查询无概算合同汇总数据
     *
     * @param pId
     * @return
     */
    @GetMapping("/getConBudgetSum")
    public BaseResponse getConBudgetSum(@RequestParam String pId) {
        return conContractService.getConBudgetSum(pId);
    }

    /**
     * 合同id查询合同总金额-智慧工地总仓
     *
     * @param pIds
     * @return
     */
    @PostMapping("/getConMoneyTotal")
    public CommonResponse<BigDecimal> getConMoneyTotal(@RequestBody List<String> pIds) {
        return conContractService.getConMoneyTotal(pIds);
    }


    /**
     * ID查询合同详情
     *
     * @param conId
     * @return
     */
    @GetMapping("/getContractByConId")
    public BaseResponse getContractByConId(@RequestParam("conId") String conId, @RequestHeader String token) {
        log.info("getContractByConId");
        return conContractService.getContractByConId(conId);
    }

    /**
     * 城建查询合同安全文明措施费
     *
     * @param conId
     * @return
     */
    @GetMapping("/getContractSecurityMoney")
    public BaseResponse getContractSecurityMoney(@RequestParam String conId) {
        log.info("getContractSecurityMoney");
        return conContractService.getContractSecurityMoney(conId);
    }

    /**
     * 漕湖结算带出信息查询
     *
     * @param conId
     * @return
     */
    @GetMapping("/selectChContractSettlement")
    public BaseResponse selectChContractSettlement(@RequestParam String conId) {
        log.info("selectChContractSettlement");
        return conContractService.selectChContractSettlement(conId);
    }

    /**
     * ID集合查询合同详情
     *
     * @param conIds
     * @return
     */
    @PostMapping("/getContractByConIds")
    public BaseResponse getContractByConIds(@RequestBody List<String> conIds) {
        log.info("getContractByConIds");
        return conContractService.getContractByConIds(conIds);
    }


    /**
     * ID查询合同详情
     * 以及合同支付计划、合同金额明细
     *
     * @param conId
     * @return
     */
    @GetMapping("/getContractWithMoneyPayByConId")
    public BaseResponse getContractWithMoneyPayByConId(@RequestParam String conId) {
        log.info("getContractWithMoneyPayByConId");
        return conContractService.getContractWithMoneyPayByConId(conId);
    }

    @GetMapping("/getRelieveContractByConId")
    public BaseResponse getRelieveContractByConId(@RequestParam String conId) {
        log.info("getRelieveContractByConId");
        return conContractService.getRelieveContractByConId(conId);
    }

    /**
     * 删除合同
     *
     * @param ids
     * @return
     */
    @GetMapping("/deleteContractByIds")
    public BaseResponse deleteContractByIds(@RequestParam String ids) {
        log.info("deleteContractByIds");
        return conContractService.deleteContractByIds(ids);
    }

    /**
     * 合同审批
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/approvalContract")
    public BaseResponse approvalContract(@RequestBody ConContractRequest request) {
        log.info("approvalContract");
        return conContractService.approvalContract(request);
    }


    /**
     * 北京工行
     * 合同支付审批时推送总包合同数据
     *
     * @param payId
     * @return
     */
    @GetMapping("/BjPushContractWhenConPay")
    public BaseResponse BjPushContractWhenConPay(@RequestParam String payId) {
        log.info("BjPushContractWhenConPay");
        return conContractService.BjPushContractWhenConPay(payId);
    }


    @GetMapping("/updateContractCodeById")
    public BaseResponse updateContractCodeById(@RequestParam String conId, @RequestParam String conCode) {
        log.info("updateContractCodeById");
        return conContractService.updateContractCodeById(conId, conCode);
    }

    /**
     * 维护合同关键信息
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/updateContractKeyInfoById")
    public BaseResponse updateContractKeyInfoById(@RequestBody ConContractRequest request) {
        log.info("updateContractKeyInfoById");
        return conContractService.updateContractKeyInfoById(request);
    }

    /**
     * 更新合同概算申报清单ID
     *
     * @param conId
     * @param bddId
     * @return
     */
    @GetMapping("/updateContractBudget")
    public BaseResponse updateContractBudget(@RequestParam String conId, @RequestParam String bddId) {
        log.info("updateContractBudget");
        return conContractService.updateContractBudget(conId, bddId);
    }

    /**
     * 合同资金统计
     *
     * @param request
     * @param token
     * @return
     */
    @TenantId
    @PostMapping("/getContractFundStatistics")
    public BaseResponse getContractFundStatistics(@RequestBody ContractFundStatisticsDTO request, @RequestHeader String token) {
        log.info("getContractFundStatistics");
        return conContractService.getContractFundStatistics(request, redisUtils.getUid(token));
    }

    @GetMapping("/secContractExist")
    public BaseResponse secContractExist(@RequestParam String secId, @RequestParam String pId) {
        log.info("secContractExist");
        return BaseResponse.ok(conContractService.secContractExist(secId, pId));
    }


    @GetMapping("/getSecContract")
    public BaseResponse getSecContract(@RequestParam String secId, @RequestParam String pId) {
        log.info("getSecContract");
        return conContractService.getSecContract(secId, pId);
    }

    @GetMapping("/getConContractByPid")
    public BaseResponse getConContractByPid(@RequestParam String id) {
        log.info("getConContractByPid");
        return conContractService.getConContractByPid(id);
    }

    /**
     * 查询合同金额
     * 合同金额加减补充合同调整金额
     *
     * @param id
     * @return
     */
    @GetMapping("/getContractMoney")
    public BaseResponse getContractMoney(@RequestParam String id) {
        log.info("getContractMoney");
        return BaseResponse.ok(conContractService.getContractMoney(id));
    }

    //流程回调
    @PostMapping("/updateConContractApproveStatus")
    public BaseResponse updateConContractApproveStatus(@RequestBody ConContractRequest request) {
        log.info("updateConContractApproveStatus");
        return conContractService.updateConContractApproveStatus(request);
    }

    /*
    判断合同金额是否超过预警值
     */
    @GetMapping("/chekConAmountOver")
    public BaseResponse chekConAmountOver(@RequestParam String pId, @RequestParam BigDecimal amount) {
        log.info("chekConAmountOver");
        return conContractService.chekConAmountOver(pId, amount);
    }

    /*
    获取合同印花税明细
    */
    @PostMapping("/getContractStampDuty")
    public BaseResponse getContractStampDuty(@RequestBody ContractStampDutyModel data) {
        log.info("getContractStampDuty");
        return conContractService.getContractStampDuty(data);
    }


    @PostMapping("/exportContractStampDuty")
    public void exportContractStampDuty(HttpServletResponse response,
                                        @RequestBody ContractStampDutyModel request,
                                        @RequestHeader String token) {
        try {
            Workbook workbook = conContractService.exportContractStampDuty(request, redisUtils.getUid(token));
            String fileName = URLEncoder.encode("合同印花税明细.xls", "UTF-8");
            // 告诉浏览器用什么软件可以打开此文件
            response.setHeader("content-Type", "application/vnd.ms-excel");
            // 下载文件的默认名称
            response.setHeader("Content-Disposition", fileName);
            //编码
            response.setCharacterEncoding("UTF-8");
            //多sheet
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (IOException e) {
            log.error("合同印花税明细导出EXCEL出错{}", e.getMessage(), e);
        }
    }

    //获取所有乙方单位
    @GetMapping("/getConPayPartyBPayList")
    public BaseResponse getConPayPartyBPayList(@RequestParam String conId) {
        return conContractService.getConPayPartyBPayList(conId);
    }

    /**
     * 文教投合同台账
     *
     * @return
     */
    @PostMapping("/getContractLedgerForWJT")
    public BaseResponse getContractLedgerForWJT(@RequestBody ContractLedgerForWJTParam param) {
        return conContractService.getContractLedgerForWJT(param);
    }

    /**
     * 维护合同信息（文教投）
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/updateContractInfoWJTById")
    public BaseResponse updateContractInfoWJTById(@RequestBody ConContractRequest request) {
        log.info("updateContractInfoWJTById");
        return conContractService.updateContractInfoWJTById(request);
    }

    /**
     * 合同信息签订附件上传（文教投）
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/updateContractInfoWJTQD")
    public BaseResponse updateContractInfoWJTQD(@RequestBody ConContractRequest request) {
        log.info("updateContractInfoWJTQD");
        return conContractService.updateContractInfoWJTQD(request);
    }


    @PostMapping("/getFinalAccountDataList")
    public BaseResponse  getFinalAccountDataList(@RequestBody ProgressDeclarationMatchDTO dto,@RequestHeader String token){
        log.info("getFinalAccountDataList");
        return conContractService.getFinalAccountDataList(dto,redisUtils.getUid(token));
    }

    @PostMapping("/getPreliminaryAuditDataList")
    public BaseResponse  getPreliminaryAuditDataList(@RequestBody ProgressDeclarationMatchDTO dto,@RequestHeader String token){
        log.info("getPreliminaryAuditDataList");
        return conContractService.getPreliminaryAuditDataList(dto,redisUtils.getUid(token));
    }


    @PostMapping("/getTCSettlementAuditDataList")
    public BaseResponse  getTCSettlementAuditDataList(@RequestBody ProgressDeclarationMatchDTO dto,@RequestHeader String token){
        log.info("getTCSettlementAuditDataList");
        return conContractService.getTCSettlementAuditDataList(dto,redisUtils.getUid(token));
    }

    @PostMapping("/getBalancePaymentDataList")
    public BaseResponse  getBalancePaymentDataList(@RequestBody ProgressDeclarationMatchDTO dto,@RequestHeader String token){
        log.info("getBalancePaymentDataList");
        return conContractService.getBalancePaymentDataList(dto,redisUtils.getUid(token));
    }


    @PostMapping("/getBalancePaymentPlanDataList")
    public BaseResponse  getBalancePaymentPlanDataList(@RequestBody ProgressDeclarationMatchDTO dto,@RequestHeader String token){
        log.info("getBalancePaymentPlanDataList");
        return conContractService.getBalancePaymentPlanDataList(dto,redisUtils.getUid(token));
    }

    @PostMapping("/getConpayByIds")
    public BaseResponse getConpayByIds(@RequestBody ConPayRequest request){
        return conContractService.getConpayByIds(request);
    }

    @PostMapping("/getConpayByIdsGDGY")
    public BaseResponse getConpayByIdsGDGY(@RequestBody ConPayRequest request){
        return conContractService.getConpayByIdsGDGY(request);
    }

    @PostMapping("/getContractListByName")
    public List<ConContractResponse> getContractListByName(@RequestBody Set<String> request, @RequestHeader String token){
        return conContractService.getContractListByName(request,redisUtils.getUid(token));
    }

    @PostMapping("/getInvestmentControlLedgerHB")
    public BaseResponse getInvestmentControlLedgerHB(@RequestBody ConContractRequest request) {
        log.info("getInvestmentControlLedgerHB");
        return conContractService.getInvestmentControlLedgerHB(request);
    }

    @PostMapping("/updateContractById")
    public BaseResponse updateContractById(@RequestBody ConContractRequest request) {
        log.info("updateContractById");
        return conContractService.updateContractById(request);
    }

    @PostMapping("/exportContractFiles")
    public List<ConContractResponse> exportContractFiles(@RequestBody ConContractRequest request) {
        log.info("exportContractFiles");
        return conContractService.exportContractFiles(request);
    }

    @PostMapping("/getContractStatistics")
    public BaseResponse getContractStatistics(@RequestBody ConContractRequest request) {
        log.info("getContractStatistics");
        return conContractService.getContractStatistics(request);
    }

    @PostMapping("/getFinishedContractMoneyByCompanyIds")
    public BaseResponse getFinishedContractMoneyByCompanyIds(@RequestBody List<String> companyIds,
                                                              @RequestHeader("token") String token) {
        log.info("getFinishedContractMoneyByCompanyIds");
        return conContractService.getFinishedContractMoneyByCompanyIds(companyIds, redisUtils.getTeId(token));
    }

    /**
     * 同步合同乙方单位到项目参建单位
     *
     * @param conId
     * @return
     */
    @GetMapping("/syncToParticipantUnit")
    public BaseResponse syncToParticipantUnit(@RequestParam String conId, @RequestHeader String token) {
        log.info("syncToParticipantUnit");
        return conContractService.syncToParticipantUnit(conId, redisUtils.getUserInfoByToken(token));
    }
}
