package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.userProducer.mapper.FlowRuleMapper;
import com.jpmsoft.userProducer.pojo.dto.FlowRuleDto;
import com.jpmsoft.userProducer.pojo.entity.FlowRule;
import com.jpmsoft.userProducer.service.IFlowRuleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FlowRuleImpl extends ServiceImpl<FlowRuleMapper, FlowRule> implements IFlowRuleService {

    /**
     * 新增流程步骤信息
     *
     * @param request
     * @return
     */
    @Override
    public void CreateFlowRule(FlowRuleDto request) {
        FlowRule flowRuleobj = new FlowRule();
        flowRuleobj.setRuletype(request.getRuletype());
        flowRuleobj.setRulesystype(request.getRulesystype());
        flowRuleobj.setRulename(request.getRulename());
        flowRuleobj.setRuleid(request.getRuleid());
        flowRuleobj.setRuleflowid(request.getRuleflowid());
        flowRuleobj.setRuleendactid(request.getRuleendactid());
        flowRuleobj.setRulecondition(request.getRulecondition());
        flowRuleobj.setRulebeginactid(request.getRulebeginactid());
        baseMapper.insert(flowRuleobj);
    }


    /**
     * 删除流程步骤信息
     *
     * @param flowId
     * @return
     */
    @Override
    public void DeleteFlowRule(String flowId) {
        QueryWrapper<FlowRule> FlowActivityWrapper = new QueryWrapper<>();
        FlowActivityWrapper.eq("RuleFlowID", flowId);
        baseMapper.delete(FlowActivityWrapper);
    }


}
