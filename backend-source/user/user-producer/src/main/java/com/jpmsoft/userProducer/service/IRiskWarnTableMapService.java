package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.RiskWarn.RiskWarnTableMapRequest;
import com.jpmsoft.userProducer.pojo.entity.RiskWarnTableMap;

import java.util.List;


public interface IRiskWarnTableMapService extends IService<RiskWarnTableMap> {
    void createOrUpdataRiskWarnTableMap(String id, List<RiskWarnTableMapRequest> TableMapList);

    void deleteRiskWarnTableMap(String id);

    BaseResponse getRiskWarnTableMap(String id);
}
