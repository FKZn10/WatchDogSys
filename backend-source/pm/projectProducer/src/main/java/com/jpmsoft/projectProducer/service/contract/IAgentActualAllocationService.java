package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Contract.AgentActualAllocationModel;
import com.jpmsoft.projectProducer.pojo.entity.contract.AgentActualAllocation;

public interface IAgentActualAllocationService extends IService<AgentActualAllocation> {

    BaseResponse createOrUpdateAgentActualAllocation(AgentActualAllocationModel request);

    BaseResponse getAgentActualAllocationList(AgentActualAllocationModel request, String uId);

    BaseResponse getAgentActualAllocationById(String aalId);

    BaseResponse getAalThisPayByConId(String conId);

    BaseResponse getAalThisPayMoneyByAgentConId(String aalAgentConId);

}
