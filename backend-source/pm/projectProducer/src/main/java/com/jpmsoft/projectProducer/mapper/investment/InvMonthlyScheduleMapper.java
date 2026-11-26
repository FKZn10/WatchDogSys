package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.export.response.InvMonthlyScheduleModel;
import com.jpmsoft.common.model.request.InvestmentControl.InvPlanYearRequest;
import com.jpmsoft.common.model.response.InvestmentControl.InvMonthlyScheduleResponse;
import com.jpmsoft.common.model.response.InvestmentControl.InvPlanYearResponse;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvMonthlySchedule;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface InvMonthlyScheduleMapper extends BaseMapper<InvMonthlySchedule> {

    List<InvMonthlyScheduleResponse> getInvMonthlyScheduleByIpvIds(@Param("ipvIds") List<String> ipvIds);


    List<InvMonthlyScheduleModel> getInvMonthlyScheduleByPidsAndYearMon(@Param("pIds") List<String> pIds, @Param("year") Integer year, @Param("month") Integer month);

    InvMonthlySchedule getInvMonthlySchedule(@Param("pId")String pId,@Param("gfpMonth")Integer gfpMonth,@Param("gfpYear")Integer gfpYear);

    InvMonthlySchedule selectInvMonthlySchedule(@Param("projectId")String projectId,@Param("contractID")String contractID,@Param("year")Integer year,@Param("month")Integer month);

    List<InvMonthlySchedule> selectInvMonthlyScheduleByInvPlanYear(@Param("ipvId")String ipvId);

    List<InvPlanYearResponse> getInvMonthlyScheduleStatisticsOverviewList(@Param("page") Page<InvPlanYearResponse> page, @Param("params") InvPlanYearRequest request);
}
