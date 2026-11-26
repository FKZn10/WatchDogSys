package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Contract.AgentFundAdvanceModel;
import com.jpmsoft.projectProducer.pojo.entity.contract.AgentFundAdvance;

import java.util.List;

public interface IAgentFundAdvanceService extends IService<AgentFundAdvance> {

    BaseResponse createOrUpdateAgentFundAdvance(AgentFundAdvanceModel request);

    BaseResponse getAgentFundAdvanceList(AgentFundAdvanceModel request, String uId);

    BaseResponse getAgentFundAdvanceById(String afaId);

    BaseResponse getAfaThisMoneyByConId(String conId);

    List<AgentFundAdvance> getFundAdvanceNotConPayApply(String teId);
}
