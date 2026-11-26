package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConContractRequest;
import com.jpmsoft.common.model.request.Contract.UploadPayInvoiceRequest;
import com.jpmsoft.common.model.request.pay.ConPayRequest;
import com.jpmsoft.common.model.requestAndResponse.Contract.*;
import com.jpmsoft.common.model.response.pay.ConPayResponse;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConPayCountDto;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConPayFundStatisticsDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPay;
import org.apache.poi.ss.usermodel.Workbook;

import java.math.BigDecimal;
import java.util.List;


public interface IConPayService extends IService<ConPay> {

    BaseResponse createOrUpdateConPay(ConPayRequest request);

    BaseResponse createOrUpdateConPay(ConPayRequest request, Boolean skipApproval);

    BaseResponse canToCreateConPay(String conId);

    BaseResponse getConPayList(ConPayRequest request, String uId);

    Workbook exportConPayList(ConPayRequest request, String uId);

    BaseResponse getConPayStandingBook(ConPayRequest request, String uId);

    /**
     * 合同分组排序取一条
     *
     * @param request
     * @param uId
     * @return
     */
    BaseResponse getConPayGroupByContract(ConPayRequest request, String uId);

    /**
     * 资金拨付查询
     *
     * @param request
     * @param uId
     * @return
     */
    BaseResponse getConPayFundAllocation(ConPayRequest request, String uId);

    BaseResponse getConPayBypayId(String payId);

    BaseResponse getAccumulatedFeesByCId(String cId, String payCategory);

    BigDecimal getConPayWHByConId(ConPayRequest request);

    BaseResponse getLastApprovedConPay(ConPayRequest request);

    BaseResponse getConPayStatisticsOverview(String teId);

    /**
     * 获取支付总览列表
     *
     * @param request
     * @param teId
     * @return
     */
    BaseResponse getConPayStatisticsOverviewList(ConPayRequest request, String teId);

    BaseResponse getConPayStatisticsGroupByDep(String teId);

    BaseResponse getConPayStatisticsByMonth(String teId);

    BaseResponse getConPayStatisticsRanking(Integer size, String teId);

    BaseResponse getPayCumulativeStatistics(ConPayRequest request);

    /**
     * 漕湖支付
     * 支付批次根据详细类型区分
     * 累计违约金根据请款单位过滤
     *
     * @param request
     * @return
     */
    BaseResponse getPayCumulativeStatisticsCH(ConPayRequest request);

    BigDecimal getPayCountByProAndYear(String pId, String year);

    BaseResponse getConPayListByConId(ConPayRequest request);

    @Deprecated
    BaseResponse bjPushConPayData(String payId);

    List<ConPayResponse> getConPayBypayNotConPayApply(String teId);


    BaseResponse getConPayByPid(String pId);

    BaseResponse getPayProjectPaymentSurplus(String pId, String year);

    BaseResponse updateConPayInFlow(ConPayRequest request);

    BaseResponse getConPayFundStatistics(ConPayFundStatisticsDTO request);

    /**
     * 合同ID查询最新一条审批通过的数据
     *
     * @param conId
     * @return
     */
    ConPay getLastApproveConPayByConId(String conId, String payType);


    BaseResponse contractPayCount(ConPayCountDto request);


    BigDecimal getPayDoneTotalByConId(String conId, String payType);

    BaseResponse getContractListByPIdAndConId(String pId, String conId);

    BaseResponse getPayDefaultTotalByPIdAndConId(String pId, String conId, String type);

    void flowSubmitCallback(String payId);


    BaseResponse contractPayListForCj(ContractPayListForCj contractPayListForCj, String uId);

    BaseResponse contractPayListForCjByPId(ContractPayListForCjDTO request);

    BaseResponse contractPayListForCjByConId(ContractPayListForCjSub request);

    BaseResponse getConPayPrintByPayId(String id);

    BaseResponse getEngineeringSpecialAccount(EngineeringSpecialAccount request, String uId);

    BaseResponse getEngineeringSpecialAccountForJt(EngineeringSpecialAccountForJT request);

    BaseResponse getEngineeringSpecialAccountForQy(EngineeringSpecialAccountForQY request);


    BaseResponse postConpayForSKApi(ConPayRequest request);

    void updateContractRetentionMoney(String conId);

    BaseResponse getConPayAmt(String conId);

    BaseResponse automaticallyFundsPayment(ConPayRequest request, String token);

    void uploadInvoice(UploadPayInvoiceRequest request);


    BaseResponse getConCompayPayTotalById(String id);

    List<ConPayResponse> getPayList(List<String> conIds);

    BaseResponse lastPeriodDeadline(String conId);

    BaseResponse getpreUnderPayment(String pId);

    List<ConPay> getConAndPayApplicationType(String conId, String type);

    BaseResponse getConPayDataListTc(ConPayRequest request, String uId);

    BaseResponse getConPayDatadetailsTc(ConPayRequest request, String token);

    BaseResponse deleteConPayById(String payId);

    BaseResponse chectConPayByid(String conId);

    BaseResponse flashbackConPay(String size);

    BaseResponse getFlashback();

    BaseResponse getConPayApproveByPid(String pId);

    BaseResponse getConPayApproveByConId(String conId);

    List<ConPay> getConPayListByPidHB(List<String> conIds);

    BaseResponse getConPayListNJCJ2(ConPayRequest request, String uId);

    BaseResponse getLastPayBalanceByPId(String pId, String contractPayType, String id);

    /**
     * 更新支付状态
     *
     * @param payId
     * @return
     */
    BaseResponse refreshPaymentStatus(String payId);

    BaseResponse getConPayListWJT(ConContractRequest request, String uid);

    /**
     * 根据合同id获取累计付款金额
     *
     * @param conId
     * @return
     */
    BaseResponse getPayActualPayTotalByConId(String conId);

    /**
     * 支付编码重复性校验
     *
     * @param payCode
     * @param payId
     * @return
     */
    BaseResponse judgmentPayCodeRepeat(String payCode, String payId);

    BaseResponse getLastPayActualPayTotal(String pId, String payCategory, String payPayeeNamePro);
}

