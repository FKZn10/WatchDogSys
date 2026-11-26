package com.jpmsoft.gmgws.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConContractRequest;
import com.jpmsoft.gmgws.pojo.entity.ContractStatistics;

public interface ContractStatisticsService  extends IService<ContractStatistics> {

    BaseResponse getContractStatisticsList(ConContractRequest request);
}
