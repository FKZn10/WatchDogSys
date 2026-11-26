package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.response.TaskResponse;
import com.jpmsoft.jpbpm.pojo.dto.HolidayVacationsDto;
import com.jpmsoft.jpbpm.pojo.entity.FlowActivity;
import com.jpmsoft.jpbpm.pojo.entity.HolidayVacations;

import java.util.Date;
import java.util.List;

public interface IHolidayVacationsService extends IService<HolidayVacations> {

    List<TaskResponse> timeEfficiencyCalculation(List<TaskResponse> oldList, Date minTime, Date currentTime, List<FlowActivity> activityList, String customerId);

    String computingTime(HolidayVacationsDto request, String customerId);

}
