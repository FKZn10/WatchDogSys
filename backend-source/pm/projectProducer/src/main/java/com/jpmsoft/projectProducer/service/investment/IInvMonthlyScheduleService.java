package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.export.response.InvMonthlyScheduleModel;
import com.jpmsoft.common.model.request.InvestmentControl.InvMonthlyScheduleRequest;
import com.jpmsoft.common.model.request.InvestmentControl.InvPlanYearRequest;
import com.jpmsoft.common.model.response.InvestmentControl.InvMonthlyScheduleResponse;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvMonthlySchedule;

import java.util.List;

public interface IInvMonthlyScheduleService extends IService<InvMonthlySchedule> {

    void createOrUpdateInvMonthlySchedule(List<InvMonthlyScheduleRequest> datas, String ipvId);

    List<InvMonthlySchedule> getInvMonthlyScheduleByIpvId(String ipvId);

    List<InvMonthlyScheduleResponse> getInvMonthlyScheduleByIpvIds(List<String> ipvIds);

    List<InvMonthlyScheduleModel> getInvMonthlyScheduleByPidsAndYearMon(List<String> pIds, Integer year, Integer month);


    List<InvMonthlySchedule> getInvMonthlyScheduleByIpvIdList(List<String> ids);

    /**
     * 获取月度投资计划统计总览列表
     *
     * @param request
     * @param teId
     * @return
     */
    BaseResponse getInvMonthlyScheduleStatisticsOverviewList(InvPlanYearRequest request, String teId);
}
