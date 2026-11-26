package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.jpbpm.pojo.entity.FlowRule;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FlowRuleService extends IService<FlowRule> {

    List<FlowRule> getFlowRuleListByRuleFlowID(String RuleFlowID,String customerId);

    /**
     * 退回连接线生成方法
     *
     * @param flowid
     * @param Start_node
     * @param end_node
     * @param typestr
     * @return
     */
    FlowRule lineofretirement(String flowid, String Start_node, String end_node, String typestr,String customerId);
}
