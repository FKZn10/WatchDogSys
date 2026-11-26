package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Contract.AgentActualAllocationModel;
import com.jpmsoft.projectProducer.pojo.entity.contract.AgentActualAllocation;
import com.jpmsoft.projectProducer.pojo.request.evaluate.BaseEvaluateRequest;
import com.jpmsoft.projectProducer.pojo.request.evaluate.investment.InvPlanEvaluateRequest;
import com.jpmsoft.projectProducer.pojo.response.IndicatorDataResponse;

public interface IContractEvaluateService {

    IndicatorDataResponse getContractScMoney(BaseEvaluateRequest request);

    IndicatorDataResponse getConPayTotalScMoney(BaseEvaluateRequest request);

}
