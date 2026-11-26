package com.jpmsoft.projectProducer.service.riskWarn;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.RiskWarn.RiskWarQueryRequest;
import com.jpmsoft.projectProducer.pojo.entity.riskWarn.RiskWarn;

public interface IProjectTaskQueryService extends IService<RiskWarn> {
    BaseResponse getProjectTaskQueryCount(RiskWarQueryRequest request);
}
