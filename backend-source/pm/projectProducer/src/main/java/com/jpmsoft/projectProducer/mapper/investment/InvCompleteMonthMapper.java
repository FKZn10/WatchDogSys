package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.request.InvestmentControl.InvCompleteMonthRequest;
import com.jpmsoft.common.model.request.InvestmentControl.InvCompleteMonthSearchRequest;
import com.jpmsoft.common.model.response.InvestmentControl.InvCompleteMonthResponse;
import com.jpmsoft.common.model.response.Project.PreProjectStreamlineResponse;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvCompleteMonth;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


public interface InvCompleteMonthMapper extends BaseMapper<InvCompleteMonth> {


    List<InvCompleteMonthResponse> getInvCompleteMonthList(@Param("page") IPage<InvCompleteMonthResponse> page, @Param("params") Map<String, Object> params);

    List<InvCompleteMonthResponse> queryInvCompleteMonthSearchList(@Param("page") IPage<InvCompleteMonthResponse> page,
                                                         @Param("request") InvCompleteMonthSearchRequest request,
                                                         @Param("authSql") String authSql);


    InvCompleteMonthResponse getInvCompleteMonthByIcmId(@Param("icmId") String icmId);


    List<InvCompleteMonthResponse> exportInvCompleteMonthStatistics(@Param("params") Map<String, Object> params);


    @Select("select Icm_Budget_Investment from  Inv_Complete_Month where p_Id=#{pId} and Icm_State=7 order by Icm_State_Time desc limit 1")
    BigDecimal getLastIcmBudgetInvestmentByPid(@Param("pId") String pId);


    @Select("select sum(Icm_Monthly_Investment) from  Inv_Complete_Month where p_Id=#{pId} and Icm_State=7  and Icm_Reporting_Year=#{year}")
    BigDecimal getCurrentYearIcmBudgetInvByPid(@Param("pId") String pId,@Param("year") String year);

    void updateActualStartTime(@Param("projectId") String projectId,@Param("icmReportingYear") String icmReportingYear, @Param("actualStartTime") Date actualStartTime);

    void updateStartById(@Param("icmId") String icmId, @Param("currentMonthStart") String currentMonthStart);

    void updateCompleteById(@Param("icmId") String icmId, @Param("currentMonthComplete") String currentMonthComplete);

    void updateActualEndTime(@Param("projectId") String projectId,@Param("icmReportingYear") String icmReportingYear, @Param("actualEndTime") Date actualEndTime);

    List<PreProjectStreamlineResponse> queryProjectByInv(@Param("icmReportingYear") String icmReportingYear, @Param("ipvTypeList")  List<String> ipvTypeList, @Param("competentDepartmentList")  List<String> competentDepartmentList);

    InvCompleteMonth getInvCompleteMonth(@Param("pId")String pId,@Param("gfpMonth")Integer gfpMonth,@Param("gfpYear")Integer gfpYear);

    List<InvCompleteMonth> selectInvCompleteMonthList(@Param("projectId")String projectId,@Param("year")Integer year);

    List<InvCompleteMonth> getInvCompleteMonthGroup();

    List<Object> getValueByCode(@Param("value")String value );

    List<Object> getInvPlanYearValueByCode(@Param("value")String value );

    List<Object> getProjectValueByCode(@Param("value")String value );

    List<Object> getDepartValueByCode(@Param("value")String value );

    List<InvCompleteMonthResponse> getInvCompleteMonthByIpvIds(@Param("ipvIds") List<String> ipvIds);

    List<InvCompleteMonth> getInvestmentCumulativeByPidHB(@Param("conIds") List<String> conIds);

    List<InvCompleteMonthResponse> getInvCompleteMonthStatisticsOverviewList(@Param("page") Page<InvCompleteMonthResponse> page, @Param("params") InvCompleteMonthRequest request);
}
