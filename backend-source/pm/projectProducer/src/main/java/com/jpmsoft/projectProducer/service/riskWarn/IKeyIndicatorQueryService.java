package com.jpmsoft.projectProducer.service.riskWarn;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.RiskWarn.RiskWarnIndexRequest;
import com.jpmsoft.projectProducer.pojo.entity.riskWarn.RiskWarn;

public interface IKeyIndicatorQueryService extends IService<RiskWarn> {
    BaseResponse getKeyIndicatorQuery(RiskWarnIndexRequest request);
}
