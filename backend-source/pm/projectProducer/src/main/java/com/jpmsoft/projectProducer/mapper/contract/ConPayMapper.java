package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.TaskProcessPrintResponse;
import com.jpmsoft.common.model.request.pay.ConPayRequest;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConPayReceiptDTO;
import com.jpmsoft.common.model.requestAndResponse.Contract.ContractPayListForCjModel;
import com.jpmsoft.common.model.requestAndResponse.Contract.EngineeringSpecialAccount;
import com.jpmsoft.common.model.response.pay.ConPayPrintResponse;
import com.jpmsoft.common.model.response.pay.ConPayResponse;
import com.jpmsoft.projectProducer.pojo.dto.contract.PayFundAllocationDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPay;
import com.jpmsoft.projectProducer.pojo.vo.contract.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public interface ConPayMapper extends BaseMapper<ConPay> {

    List<ConPayResponse> getConPayList(@Param("page") IPage<ConPayResponse> page, @Param("params") Map<String, Object> params, @Param("projectIds") List<String> projectIds);

    ConPayStandingBookVo getConPayStandingBook(@Param("params") Map<String, Object> params);

    List<ConPayResponse> getConPayGroupByContract(@Param("page") IPage<ConPayResponse> page, @Param("params") Map<String, Object> params);

    List<PayFundAllocationDTO> getConPayFundAllocation(@Param("params") ConPayRequest request);

    ConPayGroupByContractVo getConPayGroupByContractSum(@Param("params") Map<String, Object> params);

    List<ConPayResponse> getConPayListByConId(@Param("params") Map<String, Object> params);

    Integer getConPayListByConIdCount(@Param("params") Map<String, Object> params);

    ConPayResponse getConPayBypayId(@Param("payId") String payId);

    Map<String, BigDecimal> getAccumulatedFeesByCId(@Param("cId") String cId, @Param("payCategory") String payCategory);

    ConPayResponse getLastApprovedConPay(@Param("params") Map<String, Object> params);

    Map<String, Object> getConPayStatisticsOverview(@Param("teId") String teId);

    /**
     * 获取支付总览列表
     *
     * @param page
     * @param request
     * @return
     */
    List<ConPayResponse> getConPayStatisticsOverviewList(@Param("page") Page<ConPayResponse> page, @Param("params") ConPayRequest request);

    List<Map<String, Object>> getConPayStatisticsGroupByDep(@Param("teId") String teId);

    List<Map<String, Object>> getConPayStatisticsByMonth(@Param("teId") String teId);

    List<Map<String, Object>> getConPayStatisticsRanking(@Param("size") Integer size, @Param("teId") String teId);

    /**
     * 支付累计统计
     * 支付增加支付累计查询接口，根据传入的合同id、支付类型、支付类型详情（可为空）
     * 查出所有审批通过的支付信息，并根据本期支付金额（Pay_Apply）、
     * 本期完成工程量（Pay_Done_Total）计算出累计支付金额、累计完成工程量；
     * 累计支付金额再加上当前合同所有审批通过的预付款（01或0101）本次申请金额（Pay_Apply）的累计金额
     * 返回累计支付金额和累计完成工程量
     *
     * @param conId
     * @return
     */
    PayCumulativeStatisticsVO getPayCumulativeStatistics(@Param("conId") String conId, @Param("payType") String payType, @Param("teId") String teId, @Param("withoutDetailType") Boolean withoutDetailType);

    PayCumulativeStatisticsVO getPayCumulativeStatisticsCH(@Param("conId") String conId, @Param("payType") String payType, @Param("teId") String teId
            , @Param("payTypeDetailed") String payTypeDetailed, @Param("payPayee") String payPayee);

    BigDecimal getPayCountByProAndYear(@Param("pId") String pId, @Param("year") String year);

    List<ConPayResponse> getConPayBypayNotConPayApply(@Param("teId") String teId);


    List<ConPayFundStatisticsVO> getConPayFundStatistics(@Param("pId") IPage<ConPayFundStatisticsVO> page, @Param("params") Map<String, Object> params);

    ConPayFundStatisticsSumVO getConPayFundStatisticsSum(@Param("params") Map<String, Object> params);

    BigDecimal getPayDoneTotalByConId(@Param("conId") String conId, @Param("payType") String payType);

    BigDecimal getConPayWHByConId(@Param("conId") String conId);

    ConPayResponse getConPayByPId(@Param("projectId") String projectId, @Param("contractId") String contractId, @Param("payCategory") String payCategory);

    List<ConPayResponse> getConPayWithoutPayTypeByPId(@Param("projectId") String projectId, @Param("contractId") String contractId);

    @Select("select count(*) from Con_Pay where P_Id = #{pId} and Con_Id= #{conId} and Pay_State in (0,1,2,4)")
    Integer getContractListByPIdAndConId(@Param("pId") String pId, @Param("conId") String conId);


    BigDecimal getPayDefaultTotalByPIdAndConId(@Param("pId") String pId, @Param("conId") String conId, @Param("type") String type);

    List<ContractPayListForCjModel> getcontractPayListForCj(@Param("pName") String pName, @Param("page") Integer page, @Param("size") Integer size, @Param("authSql") String authSql);


    ConPayPrintResponse getConPayPrintByPayId(@Param("payId") String payId);

    List<TaskProcessPrintResponse> getTaskProcessList(@Param("params") Map<String, Object> params);

    List<EngineeringSpecialAccount> getEngineeringSpecialAccount(@Param("params") Map<String, Object> params);

    List<ConPayResponse> getConPayListByPId(@Param("projectId") String projectId);

    List<ConPayResponse> getPayList(@Param("conIds") List<String> conIds);

    Page<ConPayResponse> getConPayDataListTc(@Param("page") Page<ConPayResponse> page, @Param("request") ConPayRequest request, @Param("authSql") String authSql);

    List<ConPayResponse> getConPayDataListSumMoneyTc(@Param("request") ConPayRequest request, @Param("authSql") String authSql);

    List<ConPayResponse> getConPayDatadetailsTc(@Param("request") ConPayRequest request);

    List<ConPay> getConPayListByPidHB(@Param("conIds") List<String> conIds);

    List<ConPayResponse> getConPayListNJCJ2(@Param("page") Page<ConPayResponse> page, @Param("params") Map<String, Object> query);

    /**
     * 导出专用：单次查询全量数据，不使用分页（避免 N+1 查询导致超时）
     */
    List<ConPayResponse> getConPayListForExport(@Param("params") Map<String, Object> params,
                                                 @Param("projectIds") List<String> projectIds);

    BigDecimal getLastPayBalanceByPId(@Param("pId") String pId, @Param("contractPayType") String contractPayType, @Param("id") String id);

    List<ConPayReceiptDTO> getConPayReceiptListByPIdJT(@Param("pId") String projectId);

    List<ConPayResponse> getConPaySyncMarketInvestmentPayment();

    BigDecimal getLastPayActualPayTotal(@Param("pId") String pId, @Param("payCategory") String payCategory, @Param("payPayeeNamePro") String payPayeeNamePro);
}
