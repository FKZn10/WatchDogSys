package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.jpbpm.mapper.FlowExplainMapper;
import com.jpmsoft.jpbpm.pojo.entity.FlowExplain;
import com.jpmsoft.jpbpm.service.FlowExplainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FlowExplainImpl extends ServiceImpl<FlowExplainMapper, FlowExplain> implements  FlowExplainService{
}
