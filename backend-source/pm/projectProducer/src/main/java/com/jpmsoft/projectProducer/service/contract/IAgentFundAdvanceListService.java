package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Contract.AgentFundAdvanceListModel;
import com.jpmsoft.projectProducer.pojo.entity.contract.AgentFundAdvanceList;

import java.util.List;

public interface IAgentFundAdvanceListService extends IService<AgentFundAdvanceList> {


    void createOrUpdateAgentFundAdvanceList(List<AgentFundAdvanceListModel> datas, String afaId);

    BaseResponse getAfalThisMoneyByAfalConId(String afalConId);
}
