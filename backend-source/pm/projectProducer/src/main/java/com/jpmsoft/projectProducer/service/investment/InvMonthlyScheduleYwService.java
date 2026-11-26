package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.InvestmentControl.InvMonthlyScheduleRequest;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvMonthlyScheduleYw;

import java.util.List;

public interface InvMonthlyScheduleYwService extends IService<InvMonthlyScheduleYw> {
    void createOrUpdateInvMonthlyScheduleYw(List<InvMonthlyScheduleRequest> datas, String ipvId);
}
