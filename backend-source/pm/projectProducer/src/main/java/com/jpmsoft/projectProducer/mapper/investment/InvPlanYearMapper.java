package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.request.InvestmentControl.InvCompleteMonthRequest;
import com.jpmsoft.common.model.request.InvestmentControl.InvPlanYearRequest;
import com.jpmsoft.common.model.request.InvestmentControl.InvPlanYearSearchRequest;
import com.jpmsoft.common.model.requestAndResponse.Project.ProInvInfoModel;
import com.jpmsoft.common.model.response.InvestmentControl.InvCompleteMonthResponse;
import com.jpmsoft.common.model.response.InvestmentControl.InvPlanYearResponse;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvPlanYear;
import com.jpmsoft.projectProducer.pojo.entity.multiproject.MpcProjectInvestmentProgress;
import com.jpmsoft.projectProducer.pojo.vo.investment.InvPlanYearVo;
import com.jpmsoft.projectProducer.pojo.vo.project.ProjectInvStatisticsVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public interface InvPlanYearMapper extends BaseMapper<InvPlanYear> {

    List<InvPlanYearResponse> getInvPlanYearList(@Param("page") IPage<InvPlanYearResponse> page, @Param("params") Map<String, Object> params);

    List<InvPlanYearResponse> queryInvPlanYearSearchList(@Param("page") IPage<InvPlanYearResponse> page,
                                                         @Param("request") InvPlanYearSearchRequest request,
                                                         @Param("authSql") String authSql);

    InvPlanYearResponse getInvPlanYearByIpvId(@Param("ipvId") String ipvId);

    Map<String, BigDecimal> getInvStatisticsOverview(@Param("teId")  String teId);

    List<Map<String, Object>> getInvStatisticsGroupDep(@Param("teId")  String teId);

    List<Map<String, Object>> getInvPlanAmtByMonth(@Param("teId")  String teId);

    List<Map<String, Object>> getInvAmtByMonth(@Param("teId")  String teId);

    List<Map<String, Object>> getInvStatisticsByRanking(@Param("size") Integer size,@Param("teId")  String teId);

    ProInvInfoModel getInvInfoByPid(@Param("pId") String pId);

    BigDecimal getInvInfoByTimeYear(@Param("pId") String pId, @Param("startYear") Integer startYear, @Param("endYear") Integer endYear);

    ProjectInvStatisticsVo getInvPlanYearInvStatistics(Integer year);

    List<MpcProjectInvestmentProgress> mpcProjectInvestmentProgressCount();

    InvPlanYear selectInvPlanYear(@Param("projectId")String projectId,@Param("contractID")String contractID,@Param("year")Integer year);


    List<InvPlanYearResponse> getInvPlanYearGroup();

    List<Object> getValueByCode(@Param("value")String value );

    List<Object> getProjectValueByCode(@Param("value")String value );

    List<Object> getDepartValueByCode(@Param("value")String value );

    IPage<InvPlanYearVo> getInvPlanYearDataList(IPage<InvPlanYearVo> page,@Param("pName")String pName, @Param("strYear")String strYear,@Param("pCompetentDepartment")String pCompetentDepartment,@Param("pSecType")String pSecType,@Param("projectState")String projectState);

    InvPlanYearResponse getInvPlanYearByIpvIdXz(@Param("ipvId") String ipvId);

    List<Object> getComMonthvalueoCode(@Param("value") String dbValue, @Param("params") Map<String, Object> query);

    List<Object>  getCjJbr(@Param("params") Map<String, Object> query);

    List<InvCompleteMonthResponse> getMonthplanList(@Param("page") IPage<InvCompleteMonthRequest> page, @Param("params") Map<String, Object> params);

    List<Object> getCjZrdw(@Param("params") Map<String, Object> query);

    List<InvPlanYearResponse> getInvPlanYearStatisticsOverviewList(Page<InvPlanYearResponse> page, @Param("params") InvPlanYearRequest request);

    List<Object> getActualTime(@Param("value") String dbValue, @Param("params") Map<String, Object> query);
}
