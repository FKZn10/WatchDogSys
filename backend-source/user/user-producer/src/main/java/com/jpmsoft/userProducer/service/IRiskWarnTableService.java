package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.RiskWarn.RiskWarnTableRequest;
import com.jpmsoft.userProducer.pojo.entity.RiskWarnTable;

import java.util.List;


public interface IRiskWarnTableService extends IService<RiskWarnTable> {

    void createOrUpdataRiskWarnTable(String RwiId, List<RiskWarnTableRequest> TableList);

    void deleteRiskWarnTable(String id);

    BaseResponse getRiskWarnTableList(String id);
}
