package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.jpbpm.mapper.FlowKeyWordMapper;
import com.jpmsoft.jpbpm.pojo.entity.FlowKeyWord;
import com.jpmsoft.jpbpm.service.FlowKeyWordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FlowKeyWordImpl extends ServiceImpl<FlowKeyWordMapper, FlowKeyWord> implements FlowKeyWordService {
}
