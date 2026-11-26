package com.jpmsoft.projectProducer.service.riskWarn;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.entity.riskWarn.RiskWarn;

public interface IRiskWarnCockpitService extends IService<RiskWarn> {
    BaseResponse getRiskWarnCountByYearAndMonthly(int year, int monthly,String teId);

    BaseResponse getRiskWarnCountByUid(String userid);
}
