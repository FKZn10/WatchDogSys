package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.request.Contract.ConContractRequest;
import com.jpmsoft.common.model.request.Contract.ConContractRequestNew;
import com.jpmsoft.common.model.requestAndResponse.Contract.*;
import com.jpmsoft.common.model.response.Contract.ConContractResponse;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContract;
import com.jpmsoft.projectProducer.pojo.entity.multiproject.MpcFunds;
import com.jpmsoft.projectProducer.pojo.vo.contract.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public interface ConContractMapper extends BaseMapper<ConContract> {

    List<Map<String, Object>> getAllValues(@Param("selectClause") String selectClause,
                                           @Param("params") Map<String, Object> params);

    ChContractSettlementVO selectChContractSettlement(@Param("conId") String conId);

    @Select("select count(*) from Fund_Plan_Dtl fpd left join Fund_Plan fp on fp.Id=fpd.Fp_Id  where fpd.Con_Id=#{id} and fp.state!=3")
    Integer getFundPlanContractCount(@Param("id") String id);

    List<ConContractResponse> getContractList(@Param("page") IPage<ConContractResponse> page, @Param("params") Map<String, Object> params);

    List<ConContractResponse> getContractGgList(@Param("page") IPage<ConContractResponse> page, @Param("params") Map<String, Object> params);

    List<ConContractResponse> getContractListByPermission(@Param("page") IPage<ConContractResponse> page, @Param("params") Map<String, Object> params);

    List<ConContractResponse> getContractListExcel(@Param("params") Map<String, Object> params);


    List<ConContractResponse> getContractListAmtSum( @Param("params") Map<String, Object> params);


    List<ConContractResponse> getContractListForDangerousProject(@Param("page") IPage<ConContractResponse> page, @Param("params") Map<String, Object> params);

    List<ContractPartialVO> getContractPartialDataList(@Param("params") Map<String, Object> params);

    List<ContractBudgetVo> getContractBudgetList(@Param("page") IPage<ContractBudgetVo> page, @Param("params") Map<String, Object> params);

    ConBudgetSumVo getConBudgetSum(@Param("pId") String pId);

    //与getContractList共用getContractListCount
    List<ConContractModel> getContractWithMoneyPayList(@Param("page") IPage<ConContractModel> page, @Param("params") Map<String, Object> params);

    ConContractResponse getContractByConId(@Param("conId") String conId);

    ConContractResponse getRelieveContractByConId(@Param("conId") String conId);

    List<Map> getContractStatisticsGroupConType(@Param("teId") String teId);

    Map getContractStatisticsOverview(@Param("teId") String teId);

    /**
     * 获取合同总览列表
     *
     * @param page
     * @param request
     * @param teId
     * @return
     */
    List<ConContractResponse> getContractStatisticsOverviewList(@Param("page") Page<ConContractResponse> page, @Param("params") ConContractRequest request, @Param("teId") String teId);

    Integer getContractPartyBNum(@Param("teId") String teId);

    Integer getContractPartyBNumToday(@Param("teId") String teId);

    List<Map<String, Object>> getContractStatisticsSigning(@Param("teId") String teId);

    List<Map<String, Object>> getContractStatisticsBiddingType(@Param("teId") String teId);

    /**
     * 获取合同支付（额外指出，零星指出，项目部费用）
     * 北京工行推送使用  仅查询 Pay_Type=02 进度款的数据
     *
     * @param conId
     * @return
     */
    Map<String, BigDecimal> getAdditionalExpenditure(@Param("conId") String conId, @Param("payId") String payId);

    @Update("update  Con_Contract set Con_Approval_State='3'  where Con_Id=#{relieveConId}")
    Integer disableMainContract(@Param("relieveConId") String relieveConId);

    @Update("update  Con_Contract set Con_State=#{conState}  where Con_Id=#{relieveConId}")
    Integer updateContractConState(@Param("conState") String conState, @Param("relieveConId") String relieveConId);

    List<MpcFunds> countContract();


    BigDecimal getConMoneyTotalSum(@Param("conIds") List<String> conIds);

    BigDecimal getConMoneyTotalSumByPid(@Param("pId") String pId);


    List<ContractFundStatisticsVO> getContractFundStatistics(@Param("page") IPage<ContractFundStatisticsVO> page, @Param("params") Map<String, Object> params);

    ContractFundStatisticsSumVO getContractFundStatisticsSum(@Param("params") Map<String, Object> params);

    List<Map<String, Object>> getConContractByPid(@Param("id") String id);

    List<ConContract> getConContractBySecId(@Param("secId") String secId);

    List<ContractStampDutyModel> getContractStampDuty(@Param("page") IPage<ContractStampDutyModel> page, @Param("params") Map<String, Object> params);
    List<ContractStampDutyModel> getContractStampDutyList(@Param("params") Map<String, Object> params);

    ConContract getConMoneyTotal(@Param("conId") String  conId);

    // 文教投合同台账
    Page<ContractLedgerForWJTVO> getContractLedgerForWJT(@Param("param") ContractLedgerForWJTParam param, @Param("objectPage") Page<Object> objectPage);

    @Select("select sum(Pay_Security_This) from con_pay where con_id=#{conId} and pay_State=7")
    BigDecimal getContractSecurityMoney(@Param("conId") String  conId);


    List<ConContractResponse> getFinalAccountDataList(@Param("page") IPage<ConContractResponse> page, @Param("params") Map<String, Object> params);

    List<ConContractResponse> getPreliminaryAuditDataList(@Param("page") IPage<ConContractResponse> page, @Param("params") Map<String, Object> params);

    List<ConContractResponse> getTCSettlementAuditDataList(@Param("page") IPage<ConContractResponse> page, @Param("params") Map<String, Object> params);
    List<ConContractResponse> getBalancePaymentDataList(@Param("page") IPage<ConContractResponse> page, @Param("params") Map<String, Object> params);

    List<ConContractResponse> getBalancePaymentPlanDataList(@Param("page") IPage<ConContractResponse> page, @Param("params") Map<String, Object> params);


    int getgetCompleteById(@Param("conId") String conId);

    List<ConContractModel> childProjectContracts(@Param("params") Map<String, Object> params);

    List<ConContractResponse> getContractListNew(@Param("page") IPage<ConContractRequestNew> page, @Param("params") ConContractRequestNew params,@Param("authSql")String authSql);

    BigDecimal getTotalMoney(@Param("params")ConContractRequestNew request,@Param("authSql")String authSql);

    List<ConPayeeDtlModel> getConPayeeDtlList(@Param("conIds") List<String> conIds);

    List<ConEstimateModel> getConEstimateList(@Param("conIds") List<String> conIds);

    List<ConContractResponse> getConContractByConProjectId(@Param("params") Map<String, Object> params);

    Map<String, Object> getContractStatistics(@Param("params") ConContractRequest request);

    BigDecimal getFinishedContractMoneyByCompanyIds(@Param("companyIds") List<String> companyIds,
                                                     @Param("teId") String teId);
}
