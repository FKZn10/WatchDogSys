package com.jpmsoft.projectProducer.service.riskWarn.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.mapper.riskWarn.RiskWarnMapper;
import com.jpmsoft.projectProducer.pojo.entity.riskWarn.RiskWarn;
import com.jpmsoft.projectProducer.service.riskWarn.IRiskWarnCockpitService;
import org.springframework.stereotype.Service;

@Service
public class RiskWarnCockpitImpl extends ServiceImpl<RiskWarnMapper, RiskWarn> implements IRiskWarnCockpitService {

    @Override
    public BaseResponse getRiskWarnCountByYearAndMonthly(int year, int monthly,String teId) {
        return BaseResponse.ok(baseMapper.getRiskWarnListCoutnByYearAndMonth(year, monthly,teId), 1);
    }

    @Override
    public BaseResponse getRiskWarnCountByUid(String userid) {
        return BaseResponse.ok();
    }
}
