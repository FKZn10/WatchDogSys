package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.jpbpm.mapper.FlowRuleMapper;
import com.jpmsoft.jpbpm.pojo.entity.FlowRule;
import com.jpmsoft.jpbpm.service.FlowRuleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class FlowRuleImpl extends ServiceImpl<FlowRuleMapper, FlowRule> implements FlowRuleService {


    @Override
    public List<FlowRule> getFlowRuleListByRuleFlowID(String RuleFlowID,String customerId) {
        QueryWrapper<FlowRule> wrapper = new QueryWrapper<>();
        wrapper.eq("RuleFlowID", RuleFlowID);
        wrapper.ne("RuleType", "Line");
        if(!"All".equals(customerId) ){
            wrapper.eq("Te_Id", customerId);
        }
        return baseMapper.selectList(wrapper);
    }

    @Override
    public FlowRule lineofretirement(String flowid, String Start_node, String end_node, String typestr,String customerId) {
        FlowRule flowruleobj =null;
        QueryWrapper<FlowRule> wrapper = new QueryWrapper<>();
        wrapper.eq("RuleBeginActID", Start_node);
        wrapper.eq("RuleEndActID", end_node);
        wrapper.eq("RuleFlowID", flowid);
        wrapper.eq("RuleSysType", typestr);
        if(!"All".equals(customerId) ){
            wrapper.eq("Te_Id", customerId);
        }
        List<FlowRule> datas = baseMapper.selectList(wrapper);
        if (datas != null && datas.size() > 0)
            flowruleobj = datas.get(0);
        if (flowruleobj == null) {
            FlowRule rule = new FlowRule();
            rule.setRuleid(GenerateUUID.getUUID());
            rule.setRulesystype(typestr);
            rule.setRuletype("Line");
            rule.setRulebeginactid(Start_node);
            rule.setRuleendactid(end_node);
            rule.setRuleflowid(flowid);
            rule.setRulename("");
            if (!"All".equals(customerId)) {
                rule.setTeId(customerId);
            }
            baseMapper.insert(rule);
            return rule;
        } else
            return flowruleobj;
    }
}
