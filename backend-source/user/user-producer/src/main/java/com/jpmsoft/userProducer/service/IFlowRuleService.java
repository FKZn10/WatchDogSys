package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.userProducer.pojo.dto.FlowRuleDto;
import com.jpmsoft.userProducer.pojo.entity.FlowRule;


public interface IFlowRuleService extends IService<FlowRule> {

    /**
     * 新增流程步骤信息
     *
     * @param request
     * @return
     */
    void CreateFlowRule(FlowRuleDto request);


    /**
     * 删除流程步骤信息
     *
     * @param flowId
     * @return
     */
    void DeleteFlowRule(String flowId);
}
