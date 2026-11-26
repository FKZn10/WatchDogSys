package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.InvestmentControl.InvMonthlyScheduleResponse;
import com.jpmsoft.common.model.response.InvestmentControl.InvPlanYearResponse;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvPlanYearYw;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface InvPlanYearYwMapper extends BaseMapper<InvPlanYearYw> {
    InvPlanYearResponse getInvPlanYearYwByIpvId(@Param("ipvId") String ipvId);

    List<InvPlanYearResponse> getInvPlanYearList(@Param("page") IPage<InvPlanYearResponse> page, @Param("params") Map<String, Object> params);

    List<InvPlanYearResponse> getInvPlanYearYwBypId(@Param("pId") String pId,@Param("year") String year);

    List<InvPlanYearResponse> getInvPlanYearSummaryYwList(@Param("params") Map<String, Object> params, @Param("ids") List<String> ids) ;

    List<InvPlanYearResponse> getInvPlanYearYwListExcel( @Param("params") Map<String, Object> params);


    List<InvMonthlyScheduleResponse> getInvMonthlyScheduleByIpvList(@Param("ipvIds") List<String> ipvIds);

    List<InvPlanYearResponse> getInvPlanYearSummaryYwGroupById(@Param("page") IPage<InvPlanYearResponse> page,@Param("params") Map<String, Object> params);

    List<InvPlanYearYw> selectInvCompleteMonthList(@Param("projectId")String projectId, @Param("contractID")String contractID,  @Param("year")Integer year);
}
