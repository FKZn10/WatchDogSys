package com.jpmsoft.projectConsumer.controller.v1.contract;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.BindAwardTicketRequest;
import com.jpmsoft.common.model.request.Contract.ConContractRequest;
import com.jpmsoft.common.model.request.Contract.FlowSubmitCallbackRequest;
import com.jpmsoft.common.model.request.Contract.UploadPayInvoiceRequest;
import com.jpmsoft.common.model.request.pay.ConPayRequest;
import com.jpmsoft.common.model.requestAndResponse.Contract.*;
import com.jpmsoft.projectConsumer.feign.ConFeign;
import com.jpmsoft.projectConsumer.pojo.dto.contract.ConPayCountDto;
import com.jpmsoft.projectConsumer.pojo.dto.contract.ConPayFundStatisticsDTO;
import feign.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;


@Slf4j
@RestController
@Api(tags = "支付申请", value = "支付申请")
@RequestMapping("/v1/conPay")
public class ConPayController {
    @Autowired
    private ConFeign conFeign;

    @ApiOperation(value = "新增或修改支付申请")
    @PostMapping("/createOrUpdateConPay")
    public BaseResponse createOrUpdateConPay(@RequestBody ConPayRequest request) {
        log.info("createOrUpdateConPay");
        return conFeign.createOrUpdateConPay(request);
    }

    @ApiOperation(value = "修改支付申请")
    @PostMapping("/updateConPayInFlow")
    public BaseResponse updateConPayInFlow(@RequestBody ConPayRequest request) {
        log.info("updateConPayInFlow");
        return conFeign.updateConPayInFlow(request);
    }

    @ApiOperation(value = "判断合同是否可以创建支付申请 true可以")
    @GetMapping("/canToCreateConPay")
    public BaseResponse canToCreateConPay(@RequestParam String conId) {
        log.info("canToCreateConPay");
        return conFeign.canToCreateConPay(conId);
    }

    @ApiOperation(value = "分页查询支付申请")
    @PostMapping("/getConPayList")
    public BaseResponse getConPayList(@RequestBody ConPayRequest request, @RequestHeader String token) {
        log.info("getConPayList");
        return conFeign.getConPayList(request, token);
    }

    @ApiOperation(value = "导出合同支付列表")
    @PostMapping("/exportConPayList")
    public void exportConPayList(HttpServletResponse response,
                                 @RequestBody ConPayRequest request,
                                 @RequestHeader("token") String token) {
        log.info("exportConPayList");
        Response feignResponse = conFeign.exportConPayList(request, token);
        Response.Body body = feignResponse.body();
        if (body == null) {
            log.error("exportConPayList error: empty response body");
            return;
        }
        try (InputStream inputStream = body.asInputStream();
             OutputStream outputStream = response.getOutputStream()) {
            feignResponse.headers().getOrDefault(HttpHeaders.CONTENT_DISPOSITION, java.util.Collections.emptyList())
                    .stream().findFirst()
                    .ifPresent(value -> response.setHeader(HttpHeaders.CONTENT_DISPOSITION, value));
            feignResponse.headers().getOrDefault(HttpHeaders.CONTENT_TYPE, java.util.Collections.emptyList())
                    .stream().findFirst()
                    .ifPresent(response::setContentType);
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            log.error("exportConPayList error", e);
        }
    }

    @ApiOperation(value = "分页查询支付申请台账")
    @PostMapping("/getConPayStandingBook")
    public BaseResponse getConPayStandingBook(@RequestBody ConPayRequest request, @RequestHeader String token) {
        log.info("getConPayStandingBook");
        return conFeign.getConPayStandingBook(request, token);
    }

    @ApiOperation(value = "分页查询支付申请台账(根据合同分组取每组创建时间最新一条合同支付数据)")
    @PostMapping("/getConPayGroupByContract")
    public BaseResponse getConPayGroupByContract(@RequestBody ConPayRequest request, @RequestHeader String token) {
        log.info("getConPayGroupByContract");
        return conFeign.getConPayGroupByContract(request, token);
    }

    @ApiOperation(value = "查询拨付合同列表")
    @PostMapping("/getConPayFundAllocation")
    public BaseResponse getConPayFundAllocation(@RequestBody ConPayRequest request, @RequestHeader String token) {
        log.info("getConPayFundAllocation");
        return conFeign.getConPayFundAllocation(request, token);
    }


    @ApiOperation(value = "合同ID分页查询支付申请（合同ID必填）")
    @PostMapping("/getConPayListByConId")
    public BaseResponse getConPayListByConId(@RequestBody ConPayRequest request) {
        log.info("getConPayListByConId");
        return conFeign.getConPayListByConId(request);
    }


    @ApiOperation(value = "ID查询支付申请")
    @GetMapping("/getConPayBypayId")
    public BaseResponse getConPayBypayId(@RequestParam String payId) {
        log.info("getConPayBypayId");
        return conFeign.getConPayBypayId(payId);
    }

    @ApiOperation(value = "合同ID查询上一次合同已支付数据维护记录")
    @PostMapping("/getConPayWHByConId")
    public BaseResponse getConPayWHByConId(@RequestBody ConPayRequest request) {
        log.info("getConPayWHByConId");
        return conFeign.getConPayWHByConId(request);
    }

    @ApiOperation(value = "查询最近一次审批通过的支付信息")
    @PostMapping("/getLastApprovedConPay")
    public BaseResponse getLastApprovedConPay(@RequestBody ConPayRequest request) {
        log.info("getLastApprovedConPay");
        return conFeign.getLastApprovedConPay(request);
    }

    @ApiOperation(value = "根据收款单位查询规费累计支付金额")
    @GetMapping("/getAccumulatedFeesByCId")
    public BaseResponse getAccumulatedFeesByCId(@RequestParam String cId, @RequestParam String payCategory) {
        log.info("getAccumulatedFeesByCId");
        return conFeign.getAccumulatedFeesByCId(cId, payCategory);
    }

    @ApiOperation(value = "支付累计(累计支付金额和累计完成工程量)")
    @PostMapping("/getPayCumulativeStatistics")
    public BaseResponse getPayCumulativeStatistics(@RequestBody ConPayRequest request) {
        log.info("getPayCumulativeStatistics");
        return conFeign.getPayCumulativeStatistics(request);
    }

    @ApiOperation(value = "支付累计(累计支付金额和累计完成工程量) 漕湖定制")
    @PostMapping("/getPayCumulativeStatisticsCH")
    public BaseResponse getPayCumulativeStatisticsCH(@RequestBody ConPayRequest request) {
        log.info("getPayCumulativeStatisticsCH");
        return conFeign.getPayCumulativeStatisticsCH(request);
    }

    @ApiOperation(value = "项目ID+年份支付累计金额")
    @GetMapping("/getPayCountByProAndYear")
    public BaseResponse getPayCountByProAndYear(@RequestParam String pId, @RequestParam String year) {
        log.info("getPayCountByProAndYear");
        return conFeign.getPayCountByProAndYear(pId, year);
    }


    @ApiOperation(value = "根据项目ID获取所有的支付信息")
    @GetMapping("/getConPayByPid")
    public BaseResponse getConPayByPid(@RequestParam String pId) {
        log.info("getConPayByPid");
        return conFeign.getConPayByPid(pId);
    }

    @ApiOperation(value = "项目ID+年份支付剩余金额")
    @GetMapping("/getPayProjectPaymentSurplus")
    public BaseResponse getPayProjectPaymentSurplus(@RequestParam String pId, @RequestParam String year) {
        log.info("getPayProjectPaymentSurplus");
        return conFeign.getPayProjectPaymentSurplus(pId, year);
    }

    @ApiOperation(value = "合同支付明细")
    @PostMapping("/getConPayFundStatistics")
    public BaseResponse getConPayFundStatistics(@RequestBody ConPayFundStatisticsDTO request) {
        log.info("getConPayFundStatistics");
        return conFeign.getConPayFundStatistics(request);
    }

    @ApiOperation(value = "校验合同支付金额是否满足")
    @PostMapping("/contractPayCount")
    public BaseResponse contractPayCount(@RequestBody ConPayCountDto request) {
        log.info("contractPayCount");
        return conFeign.contractPayCount(request);
    }

    @ApiOperation(value = "获取累计完成量")
    @GetMapping("/getPayDoneTotalByConId")
    public BigDecimal getPayDoneTotalByConId(@RequestParam String conId, @RequestParam String pType) {
        log.info("getPayDoneTotalByConId");
        return conFeign.getPayDoneTotalByConId(conId, pType);
    }

    @ApiOperation(value = "根据项目ID、合同ID判断是否有正在进行的流程")
    @GetMapping("/getContractListByPIdAndConId")
    public BaseResponse getContractListByPIdAndConId(@RequestParam String pId, @RequestParam String conId) {
        log.info("getContractListByPIdAndConId");
        return conFeign.getContractListByPIdAndConId(pId, conId);
    }

    @ApiOperation(value = "根据项目ID、合同ID获取应扣违约金")
    @GetMapping("/getPayDefaultTotalByPIdAndConId")
    public BaseResponse getPayDefaultTotalByPIdAndConId(@RequestParam String pId, @RequestParam String conId, @RequestParam(value = "type", required = false) String type) {
        log.info("getPayDefaultTotalByPIdAndConId");
        return conFeign.getPayDefaultTotalByPIdAndConId(pId, conId, type);
    }

    /**
     * 奖罚单绑定支付id
     *
     * @param bindAwardTicketRequest
     * @return
     */
    @PostMapping("/bindAwardTicket")
    public BaseResponse bindAwardTicket(@RequestBody BindAwardTicketRequest bindAwardTicketRequest) {
        return conFeign.awardTicketBindPay(bindAwardTicketRequest);
    }

    /**
     * 支付流程提交回调接口
     *
     * @param flowSubmitCallbackRequest
     * @return
     */
    @PostMapping("/flowSubmitCallback")
    public BaseResponse flowSubmitCallback(@RequestBody FlowSubmitCallbackRequest flowSubmitCallbackRequest) {
        return conFeign.conPayFlowSubmitCallback(flowSubmitCallbackRequest);
    }

    /**
     * 查询流程未结束的合同支付数量
     */
    @GetMapping("/countUnfinishedFlowByContractAndPayType")
    public BaseResponse countUnfinishedFlowByContractAndPayType(String contractId, String payType) {
        return conFeign.countUnfinishedFlowByContractAndPayType(contractId, payType);
    }


    /**
     * 城建合同支付台账
     */
    @PostMapping("/contractPayListForCj")
    public BaseResponse contractPayListForCj(@RequestBody ContractPayListForCj contractPayListForCj, @RequestHeader String token) {
        return conFeign.contractPayListForCj(contractPayListForCj, token);
    }

    /**
     * 城建合同支付台账
     */
    @PostMapping("/contractPayListForCjByPId")
    public BaseResponse contractPayListForCjByPId(@RequestBody ContractPayListForCjDTO request) {
        return conFeign.contractPayListForCjByPId(request);
    }

    /**
     * 城建合同支付台账-下钻支付
     */
    @PostMapping("/contractPayListForCjByConId")
    public BaseResponse contractPayListForCjByConId(@RequestBody ContractPayListForCjSub request) {
        return conFeign.contractPayListForCjByConId(request);
    }


    @GetMapping("/getConPayPrintByPayId")
    public BaseResponse getConPayPrintByPayId(@RequestParam String id) {
        log.info("getConPayPrintByPayId");
        return conFeign.getConPayPrintByPayId(id);
    }

    /**
     * 城建工程专户台账
     */
    @PostMapping("/getEngineeringSpecialAccount")
    public BaseResponse getEngineeringSpecialAccount(@RequestBody EngineeringSpecialAccount request, @RequestHeader String token) {
        return conFeign.getEngineeringSpecialAccount(request, token);
    }

    /**
     * 城建工程专户台账-集团账户收支明细
     */
    @PostMapping("/getEngineeringSpecialAccountForJt")
    public BaseResponse getEngineeringSpecialAccountForJt(@RequestBody EngineeringSpecialAccountForJT request) {
        return conFeign.getEngineeringSpecialAccountForJt(request);
    }

    /**
     * 城建工程专户台账-企业账户收支明细
     */
    @PostMapping("/getEngineeringSpecialAccountForQy")
    public BaseResponse getEngineeringSpecialAccountForQy(@RequestBody EngineeringSpecialAccountForQY request) {
        return conFeign.getEngineeringSpecialAccountForQy(request);
    }

    /**
     * 司库对接
     */
    @PostMapping("/postConpayForSKApi")
    public BaseResponse postConpayForSKApi(@RequestBody ConPayRequest request) {
        return conFeign.postConpayForSKApi(request);
    }

    /**
     * 累加结算款中的本次质保金金额，更新到合同中去
     *
     * @param request
     * @return
     */
    @PostMapping("/recalculateContractRetentionMoney")
    public BaseResponse recalculateContractRetentionMoney(@RequestBody ConPayRequest request) {
        return conFeign.updateContractRetentionMoney(request);
    }

    /**
     * 根据合同Id查询Con_Pay表中的payApply、payInvoice、Pay_Approval的和
     *
     * @param conId
     * @return
     */
    @GetMapping("/getConPayAmt")
    public BaseResponse getConPayAmt(@RequestParam String conId) {
        return conFeign.getConPayAmt(conId);
    }

    @PostMapping("/automaticallyFundsPayment")
    public BaseResponse automaticallyFundsPayment(@RequestBody ConPayRequest request, @RequestHeader String token) {
        return conFeign.automaticallyFundsPayment(request, token);
    }

    /**
     * 上传发票附件
     *
     * @param request
     * @return
     */
    @PostMapping("/uploadInvoice")
    public BaseResponse uploadInvoice(@RequestBody UploadPayInvoiceRequest request) {
        return conFeign.uploadConPayInvoice(request);
    }


    @GetMapping("/getConCompayPayTotalById")
    public BaseResponse getConCompayPayTotalById(@RequestParam String id) {
        return conFeign.getConCompayPayTotalById(id);
    }


    @GetMapping("/lastPeriodDeadline")
    public BaseResponse lastPeriodDeadline(@RequestParam String conId) {
        return conFeign.lastPeriodDeadline(conId);
    }

    @GetMapping("/getpreUnderPayment")
    public BaseResponse getpreUnderPayment(@RequestParam String pId) {
        return conFeign.getpreUnderPayment(pId);
    }

    @PostMapping("/getConPayDataListTc")
    public BaseResponse getConPayDataListTc(@RequestBody ConPayRequest request, @RequestHeader String token) {
        return conFeign.getConPayDataListTc(request, token);
    }

    @PostMapping("/getConPayDatadetailsTc")
    public BaseResponse getConPayDatadetailsTc(@RequestBody ConPayRequest request, @RequestHeader String token) {
        return conFeign.getConPayDatadetailsTc(request, token);
    }

    @GetMapping("/deleteConPayById")
    public BaseResponse deleteConPayById(@RequestParam String payId) {
        return conFeign.deleteConPayById(payId);
    }

    @GetMapping("/chectConPayById")
    public BaseResponse chectConPayById(@RequestParam("conId") String conId) {
        return conFeign.chectConPayByid(conId);
    }

    @PostMapping("/flashbackConPay")
    public BaseResponse flashbackConPay(@RequestParam("size") String size) {
        return conFeign.flashbackConPay(size);
    }

    @GetMapping("/getFlashback")
    public BaseResponse getFlashback() {
        return conFeign.getFlashback();
    }


    /**
     * 根据项目id查询最近一次审批通过的支付信息
     *
     * @param pId
     * @return
     */
    @GetMapping("/getConPayApproveByPid")
    public BaseResponse getConPayApproveByPid(@RequestParam String pId) {
        return conFeign.getConPayApproveByPid(pId);
    }


    /**
     * 根据合同id查询最近一次审批通过的支付信息
     *
     * @param conId
     * @return
     */
    @GetMapping("/getConPayApproveByConId")
    public BaseResponse getConPayApproveByConId(@RequestParam String conId) {
        return conFeign.getConPayApproveByConId(conId);
    }

    /**
     * 合同支付分页查询-城建-2
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation(value = "合同支付分页查询-城建-2")
    @PostMapping("/getConPayListNJCJ2")
    public BaseResponse getConPayListNJCJ2(@RequestBody ConPayRequest request, @RequestHeader String token) {
        log.info("getConPayListNJCJ2");
        return conFeign.getConPayListNJCJ2(request, token);
    }

    @ApiOperation("获取最新的集团已安排资金")
    @GetMapping("/getLastPayBalanceByPId")
    public BaseResponse getLastPayBalanceByPId(
            @RequestParam("pId") String pId,
            @RequestParam("contractPayType") String contractPayType,
            @RequestParam(value = "id", required = false) String id
    ) {
        log.info("getLastPayBalanceByPId");
        return conFeign.getLastPayBalanceByPId(pId, contractPayType, id);
    }

    /**
     * 更新支付状态
     *
     * @param payId
     * @return
     */
    @ApiOperation(value = "更新支付状态")
    @GetMapping("/refreshPaymentStatus")
    public BaseResponse refreshPaymentStatus(@RequestParam String payId) {
        log.info("refreshPaymentStatus");
        return conFeign.refreshPaymentStatus(payId);
    }

    @PostMapping("/getConPayListWJT")
    public BaseResponse getConPayListWJT(@RequestBody ConContractRequest request, @RequestHeader String token) {
        log.info("getConPayListWJT");
        return conFeign.getConPayListWJT(request, token);
    }

    /**
     * 根据合同id获取累计付款金额
     *
     * @param conId
     * @return
     */
    @ApiOperation("根据合同id获取累计付款金额")
    @GetMapping("/getPayActualPayTotalByConId")
    public BaseResponse getPayActualPayTotalByConId(@RequestParam String conId) {
        log.info("getPayActualPayTotalByConId");
        return conFeign.getPayActualPayTotalByConId(conId);
    }

    /**
     * 支付编码重复性校验
     *
     * @param payCode
     * @param payId
     * @return
     */
    @ApiOperation("支付编码重复性校验")
    @GetMapping("/judgmentPayCodeRepeat")
    public BaseResponse judgmentPayCodeRepeat(@RequestParam("payCode") String payCode, @RequestParam(value = "payId", required = false) String payId) {
        log.info("judgmentPayCodeRepeat");
        return conFeign.judgmentPayCodeRepeat(payCode, payId);
    }

    /**
     * 获取上一次支付的实际支付总金额
     *
     * @param pId
     * @param payCategory
     * @param payPayeeNamePro
     * @return
     */
    @ApiOperation("获取上一次支付的实际支付总金额")
    @GetMapping("/getLastPayActualPayTotal")
    public BaseResponse getLastPayActualPayTotal(@RequestParam String pId, @RequestParam String payCategory, @RequestParam String payPayeeNamePro) {
        log.info("getLastPayActualPayTotal");
        return conFeign.getLastPayActualPayTotal(pId, payCategory, payPayeeNamePro);
    }
}
