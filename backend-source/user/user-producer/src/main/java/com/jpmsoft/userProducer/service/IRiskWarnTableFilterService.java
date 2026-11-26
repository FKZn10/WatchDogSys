package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.RiskWarn.RiskWarnTableFilterRequest;
import com.jpmsoft.userProducer.pojo.entity.RiskWarnTableFilter;

import java.util.List;


public interface IRiskWarnTableFilterService extends IService<RiskWarnTableFilter> {
    void createOrUpdataRiskWarnTableFilter(String RwiId, String RwtId, List<RiskWarnTableFilterRequest> TableFilterList);

    void createOrUpdataRiskWarnTableFilter(String RwiId, List<RiskWarnTableFilter> TableFilterList);

    void deleteRiskWarnTableMap(String id);

    BaseResponse getRiskWarnTableFilter(String id);
}
