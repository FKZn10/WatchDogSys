package com.jpmsoft.projectProducer.service.riskWarn.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.RiskWarn.RiskWarQueryRequest;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.riskWarn.RiskWarnMapper;
import com.jpmsoft.projectProducer.pojo.entity.riskWarn.RiskWarn;
import com.jpmsoft.projectProducer.service.riskWarn.IRiskWarnAnnualQueryService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class RiskWarnAnnualQueryImpl extends ServiceImpl<RiskWarnMapper, RiskWarn> implements IRiskWarnAnnualQueryService {
    @Override
    public BaseResponse getRiskWarnAnnualQueryCount(RiskWarQueryRequest request) {
        Map<String, Object> params = new HashMap<>();
        params.put("starttime", request.getStartTime());
        params.put("Endtime", request.getEndTime());
        params.put("teId", ValidateUtil.isEmpty(request.getTeId()) ? null : request.getTeId());
        return BaseResponse.ok(baseMapper.selectRiskWarnAnnualQuery(params), 1);
    }
}
