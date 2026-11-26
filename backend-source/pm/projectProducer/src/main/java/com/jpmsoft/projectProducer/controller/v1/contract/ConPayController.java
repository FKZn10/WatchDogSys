package com.jpmsoft.projectProducer.controller.v1.contract;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.FlowStatusEnum;
import com.jpmsoft.common.model.request.Contract.BindAwardTicketRequest;
import com.jpmsoft.common.model.request.Contract.ConContractRequest;
import com.jpmsoft.common.model.request.Contract.FlowSubmitCallbackRequest;
import com.jpmsoft.common.model.request.Contract.UploadPayInvoiceRequest;
import com.jpmsoft.common.model.request.pay.ConPayRequest;
import com.jpmsoft.common.model.requestAndResponse.Contract.*;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConPayCountDto;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConPayFundStatisticsDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPay;
import com.jpmsoft.projectProducer.service.contract.IConPayService;
import com.jpmsoft.projectProducer.service.performanceClaim.IPcAwardTicketService;
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
import java.util.Arrays;


@Slf4j
@RestController
@RequestMapping("/v1/conPay")
public class ConPayController {
    @Resource
    private IConPayService conPayService;
    @Resource
    private RedisUtils redisUtils;

    @Resource
    private IPcAwardTicketService pcAwardTicketService;

    @TenantId
    @PostMapping("/createOrUpdateConPay")
    public BaseResponse createOrUpdateConPay(@RequestBody ConPayRequest request) {
        log.info("createOrUpdateConPay");
        return conPayService.createOrUpdateConPay(request);
    }

    @PostMapping("/updateConPayInFlow")
    public BaseResponse updateConPayInFlow(@RequestBody ConPayRequest request) {
        log.info("updateConPayInFlow");
        return conPayService.updateConPayInFlow(request);
    }


    @GetMapping("/canToCreateConPay")
    public BaseResponse canToCreateConPay(@RequestParam String conId) {
        log.info("canToCreateConPay");
        return conPayService.canToCreateConPay(conId);
    }

    @TenantId
    @PostMapping("/getConPayList")
    public BaseResponse getConPayList(@RequestBody ConPayRequest request, @RequestHeader String token) {
        log.info("getConPayList");
        return conPayService.getConPayList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/exportConPayList")
    public void exportConPayList(HttpServletResponse response,
                                 @RequestBody ConPayRequest request,
                                 @RequestHeader("token") String token) {
        log.info("exportConPayList");
        try (Workbook workbook = conPayService.exportConPayList(request, redisUtils.getUid(token));
             ServletOutputStream outputStream = response.getOutputStream()) {
            String fileName = URLEncoder.encode("合同支付列表.xlsx", "UTF-8").replace("+", "%20");
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + fileName);
            response.setCharacterEncoding("UTF-8");
            workbook.write(outputStream);
        } catch (IOException e) {
            log.error("exportConPayList error", e);
        }
    }

    @TenantId
    @PostMapping("/getConPayStandingBook")
    public BaseResponse getConPayStandingBook(@RequestBody ConPayRequest request, @RequestHeader String token) {
        log.info("getConPayStandingBook");
        return conPayService.getConPayStandingBook(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getConPayGroupByContract")
    public BaseResponse getConPayGroupByContract(@RequestBody ConPayRequest request, @RequestHeader String token) {
        log.info("getConPayGroupByContract");
        return conPayService.getConPayGroupByContract(request, redisUtils.getUid(token));
    }


    @TenantId
    @PostMapping("/getConPayFundAllocation")
    public BaseResponse getConPayFundAllocation(@RequestBody ConPayRequest request, @RequestHeader String token)
    {
        log.info("getConPayFundAllocation");
        return conPayService.getConPayFundAllocation(request, redisUtils.getUid(token));
    }


    @TenantId
    @PostMapping("/getConPayListByConId")
    public BaseResponse getConPayListByConId(@RequestBody ConPayRequest request) {
        log.info("getConPayListByConId");
        return conPayService.getConPayListByConId(request);
    }

    @PostMapping("/getConPayWHByConId")
    public BaseResponse getConPayWHByConId(@RequestBody ConPayRequest request) {
        log.info("getConPayWHByConId");

        return BaseResponse.ok(conPayService.getConPayWHByConId(request));
    }

    @GetMapping("/getConPayBypayId")
    public BaseResponse getConPayBypayId(@RequestParam String payId) {
        log.info("getConPayBypayId");
        return conPayService.getConPayBypayId(payId);
    }

    @GetMapping("/getAccumulatedFeesByCId")
    public BaseResponse getAccumulatedFeesByCId(@RequestParam String cId,@RequestParam String payCategory) {
        log.info("getAccumulatedFeesByCId");
        return conPayService.getAccumulatedFeesByCId(cId,payCategory);
    }
    @TenantId
    @PostMapping("/getLastApprovedConPay")
    public BaseResponse getLastApprovedConPay(@RequestBody ConPayRequest request) {
        log.info("getLastApprovedConPay");
        return conPayService.getLastApprovedConPay(request);
    }

    @TenantId
    @PostMapping("/getPayCumulativeStatistics")
    public BaseResponse getPayCumulativeStatistics(@RequestBody ConPayRequest request) {
        log.info("getPayCumulativeStatistics");
        return conPayService.getPayCumulativeStatistics(request);
    }

    @TenantId
    @PostMapping("/getPayCumulativeStatisticsCH")
    public BaseResponse getPayCumulativeStatisticsCH(@RequestBody ConPayRequest request) {
        log.info("getPayCumulativeStatisticsCH");
        return conPayService.getPayCumulativeStatisticsCH(request);
    }

    @GetMapping("/getPayCountByProAndYear")
    public BaseResponse getPayCountByProAndYear(@RequestParam String pId, @RequestParam String year) {
        log.info("getPayCountByProAndYear");
        return BaseResponse.ok(conPayService.getPayCountByProAndYear(pId, year));
    }

    @GetMapping("/getConPayByPid")
    public BaseResponse getConPayByPid(@RequestParam String pId) {
        log.info("getConPayByPid");
        return conPayService.getConPayByPid(pId);
    }

    @GetMapping("/getPayProjectPaymentSurplus")
    public BaseResponse getPayProjectPaymentSurplus(@RequestParam String pId, @RequestParam String year) {
        log.info("getPayProjectPaymentSurplus");
        return conPayService.getPayProjectPaymentSurplus(pId, year);
    }

    @TenantId
    @PostMapping("/getConPayFundStatistics")
    public BaseResponse getConPayFundStatistics(@RequestBody ConPayFundStatisticsDTO request) {
        log.info("getConPayFundStatistics");
        return conPayService.getConPayFundStatistics(request);
    }


    @PostMapping("/contractPayCount")
    public BaseResponse contractPayCount(@RequestBody ConPayCountDto request) {
        log.info("contractPayCount");
        return conPayService.contractPayCount(request);
    }

    /*
     * 根据合同ID获取累计完成工作量
     * */
    @GetMapping("/getPayDoneTotalByConId")
    public BigDecimal getPayDoneTotalByConId(@RequestParam String conId, @RequestParam String pType) {
        log.info("getPayDoneTotalByConId");
        return conPayService.getPayDoneTotalByConId(conId, pType);
    }

    /*
     * 根据项目ID、合同ID判断是否有正在进行的流程
     * */
    @GetMapping("/getContractListByPIdAndConId")
    public BaseResponse getContractListByPIdAndConId(@RequestParam String pId, @RequestParam String conId) {
        log.info("getContractListByPIdAndConId");
        return conPayService.getContractListByPIdAndConId(pId, conId);
    }

    /*
     * 根据项目ID、合同ID获取应扣违约金
     * */
    @GetMapping("/getPayDefaultTotalByPIdAndConId")
    public BaseResponse getPayDefaultTotalByPIdAndConId(@RequestParam String pId, @RequestParam String conId, @RequestParam(value = "type", required = false) String type) {
        log.info("getPayDefaultTotalByPIdAndConId");
        return conPayService.getPayDefaultTotalByPIdAndConId(pId, conId, type);
    }

    /**
     * 奖罚单绑定支付id
     *
     * @param bindAwardTicketRequest
     * @return
     */
    @PostMapping("/bindAwardTicket")
    public BaseResponse bindAwardTicket(@RequestBody BindAwardTicketRequest bindAwardTicketRequest) {
        pcAwardTicketService.bindPay(bindAwardTicketRequest);
        return BaseResponse.ok();
    }

    /**
     * 支付流程提交回调接口
     *
     * @param flowSubmitCallbackRequest
     * @return
     */
    @PostMapping("/flowSubmitCallback")
    public BaseResponse flowSubmitCallback(@RequestBody FlowSubmitCallbackRequest flowSubmitCallbackRequest) {
        conPayService.flowSubmitCallback(flowSubmitCallbackRequest.getPayId());
        return BaseResponse.ok();
    }

    @GetMapping("/countUnfinishedFlowByContractAndPayType")
    public BaseResponse countUnfinishedFlowByContractAndPayType(String contractId, String payType) {
        long count = conPayService.count(new LambdaQueryWrapper<ConPay>()
                .eq(ConPay::getConId, contractId)
                .eq(ConPay::getPayType, payType)
                .in(ConPay::getPayState, Arrays.asList(
                        FlowStatusEnum.Null.getIndex(), FlowStatusEnum.Send.getIndex(),
                        FlowStatusEnum.SendBack.getIndex(), FlowStatusEnum.SendBackStart.getIndex(),
                        FlowStatusEnum.New.getIndex(), FlowStatusEnum.Pause.getIndex(),
                        FlowStatusEnum.ChangeFlow.getIndex())));
        return BaseResponse.ok(count);
    }

    /**
     *
     * 城建合同支付台账
     */
    @PostMapping("/contractPayListForCj")
    public BaseResponse contractPayListForCj(@RequestBody ContractPayListForCj contractPayListForCj,@RequestHeader String token) {
        log.info("contractPayListForCj");
        return conPayService.contractPayListForCj(contractPayListForCj,redisUtils.getUid(token));
    }

    /**
     *
     * 城建合同支付台账-下钻合同
     */
    @PostMapping("/contractPayListForCjByPId")
    public BaseResponse contractPayListForCjByPId(@RequestBody ContractPayListForCjDTO request) {
        log.info("contractPayListForCjByPId");
        return conPayService.contractPayListForCjByPId(request);
    }

    /**
     *
     * 城建合同支付台账-下钻支付
     */
    @PostMapping("/contractPayListForCjByConId")
    public BaseResponse contractPayListForCjByConId(@RequestBody ContractPayListForCjSub request) {
        log.info("contractPayListForCjByConId");
        return conPayService.contractPayListForCjByConId(request);
    }


    @GetMapping("/getConPayPrintByPayId")
    public BaseResponse getConPayPrintByPayId(@RequestParam String id) {
        log.info("getConPayPrintByPayId");
        return conPayService.getConPayPrintByPayId(id);
    }


    /**
     *
     * 城建工程专户台账
     */
    @PostMapping("/getEngineeringSpecialAccount")
    public BaseResponse getEngineeringSpecialAccount(@RequestBody EngineeringSpecialAccount request,@RequestHeader String token) {
        log.info("getEngineeringSpecialAccount");
        return conPayService.getEngineeringSpecialAccount(request,redisUtils.getUid(token));
    }

    /**
     *
     * 城建工程专户台账-集团账户收支明细
     */
    @PostMapping("/getEngineeringSpecialAccountForJt")
    public BaseResponse getEngineeringSpecialAccountForJt(@RequestBody EngineeringSpecialAccountForJT request) {
        log.info("getEngineeringSpecialAccountForJt");
        return conPayService.getEngineeringSpecialAccountForJt(request);
    }

    /**
     *
     * 城建工程专户台账-企业账户收支明细
     */
    @PostMapping("/getEngineeringSpecialAccountForQy")
    public BaseResponse getEngineeringSpecialAccountForQy(@RequestBody EngineeringSpecialAccountForQY request) {
        log.info("getEngineeringSpecialAccountForQy");
        return conPayService.getEngineeringSpecialAccountForQy(request);
    }

    /*
    司库的对接
     */
    @PostMapping("/postConpayForSKApi")
    public BaseResponse postConpayForSKApi(@RequestBody ConPayRequest request) {
        log.info("postConpayForSKApi");
        return conPayService.postConpayForSKApi(request);
    }

    /**
     * 累加结算款中的本次质保金金额，更新到合同中去
     * @param request
     * @return
     */
    @PostMapping("/recalculateContractRetentionMoney")
    public BaseResponse recalculateContractRetentionMoney(@RequestBody ConPayRequest request){
        conPayService.updateContractRetentionMoney(request.getConId());
        return BaseResponse.ok();
    }


    //根据合同Id查询Con_Pay表中的payApply、payInvoice、Pay_Approval的和的和
    @GetMapping("/getConPayAmt")
    public BaseResponse getConPayAmt(@RequestParam String conId){
        return  conPayService.getConPayAmt(conId);
    }


    /**
     * 合同支付完成后自动生成资金支付申请并生成代办流程，文教头定制
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/automaticallyFundsPayment")
    public BaseResponse automaticallyFundsPayment(@RequestBody ConPayRequest request,@RequestHeader String token){
        return  conPayService.automaticallyFundsPayment(request,token);
    }

    /**
     * 上传发票附件
     * @param request
     * @return
     */
    @PostMapping("/uploadInvoice")
    public BaseResponse uploadInvoice(@RequestBody UploadPayInvoiceRequest request){
        conPayService.uploadInvoice(request);
        return BaseResponse.ok();
    }

    @GetMapping("/getConCompayPayTotalById")
    public BaseResponse getConCompayPayTotalById(@RequestParam String id){
        return  conPayService.getConCompayPayTotalById(id);
    }


    @GetMapping("/lastPeriodDeadline")
    public BaseResponse lastPeriodDeadline(@RequestParam String  conId){
        return conPayService.lastPeriodDeadline(conId);
    }

    @GetMapping("/getpreUnderPayment")
    public  BaseResponse getpreUnderPayment(@RequestParam  String pId){
        return conPayService.getpreUnderPayment(pId);
    }


    @PostMapping("/getConPayDataListTc")
    public  BaseResponse getConPayDataListTc(@RequestBody ConPayRequest request,@RequestHeader String token){
        return conPayService.getConPayDataListTc(request,redisUtils.getUid(token));
    }

    @PostMapping("/getConPayDatadetailsTc")
    public  BaseResponse getConPayDatadetailsTc(@RequestBody ConPayRequest request,@RequestHeader String token){
        return conPayService.getConPayDatadetailsTc(request,token);
    }

    @GetMapping("/deleteConPayById")
    public  BaseResponse deleteConPayById(@RequestParam String payId){
        return conPayService.deleteConPayById(payId);
    }

    @GetMapping("/chectConPayById")
    public BaseResponse chectConPayById(@RequestParam String conId) {
        return conPayService.chectConPayByid(conId);
    }

    @PostMapping("/flashbackConPay")
    public BaseResponse flashbackConPay(@RequestParam("size") String size) {
        return conPayService.flashbackConPay(size);
    }

    @GetMapping("/getFlashback")
    public BaseResponse getFlashback(){
        return conPayService.getFlashback();
    }


    @GetMapping("/getConPayApproveByPid")
    public BaseResponse getConPayApproveByPid(@RequestParam String pId) {
        return conPayService.getConPayApproveByPid(pId);
    }

    @GetMapping("/getConPayApproveByConId")
    public BaseResponse getConPayApproveByConId(@RequestParam String conId) {
        return conPayService.getConPayApproveByConId(conId);
    }

    @PostMapping("/getConPayListNJCJ2")
    public BaseResponse getConPayListNJCJ2(@RequestBody ConPayRequest request, @RequestHeader String token) {
        log.info("getConPayListNJCJ2");
        return conPayService.getConPayListNJCJ2(request, redisUtils.getUid(token));
    }

    @GetMapping("/getLastPayBalanceByPId")
    public BaseResponse getLastPayBalanceByPId(
            @RequestParam("pId") String pId,
            @RequestParam("contractPayType") String contractPayType,
            @RequestParam(value = "id", required = false) String id
    ) {
        log.info("getLastPayBalanceByPId");
        return conPayService.getLastPayBalanceByPId(pId, contractPayType, id);
    }

    /**
     * 更新支付状态
     *
     * @param payId
     * @return
     */
    @GetMapping("/refreshPaymentStatus")
    public BaseResponse refreshPaymentStatus(@RequestParam String payId) {
        log.info("refreshPaymentStatus");
        return conPayService.refreshPaymentStatus(payId);
    }

    // 合同支付台账 文教头
    @PostMapping("/getConPayListWJT")
    public BaseResponse getConPayListWJT(@RequestBody ConContractRequest request, @RequestHeader String token) {
        log.info("getConPayListWJT");
        return conPayService.getConPayListWJT(request, redisUtils.getUid(token));
    }

    /**
     * 根据合同id获取累计付款金额
     *
     * @param conId
     * @return
     */
    @GetMapping("/getPayActualPayTotalByConId")
    public BaseResponse getPayActualPayTotalByConId(@RequestParam String conId) {
        log.info("getPayActualPayTotalByConId");
        return conPayService.getPayActualPayTotalByConId(conId);
    }

    /**
     * 支付编码重复性校验
     *
     * @param payCode
     * @param payId
     * @return
     */
    @GetMapping("/judgmentPayCodeRepeat")
    public BaseResponse judgmentPayCodeRepeat(@RequestParam("payCode") String payCode, @RequestParam(value = "payId", required = false) String payId) {
        log.info("judgmentPayCodeRepeat");
        return conPayService.judgmentPayCodeRepeat(payCode, payId);
    }

    /**
     * 获取上一次支付的实际支付总金额
     *
     * @param pId
     * @param payCategory
     * @param payPayeeNamePro
     * @return
     */
    @GetMapping("/getLastPayActualPayTotal")
    public BaseResponse getLastPayActualPayTotal(@RequestParam String pId, @RequestParam String payCategory, @RequestParam String payPayeeNamePro) {
        log.info("getLastPayActualPayTotal");
        return conPayService.getLastPayActualPayTotal(pId, payCategory, payPayeeNamePro);
    }
}
