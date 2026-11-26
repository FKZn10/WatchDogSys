package com.jpmsoft.projectProducer.service.fund.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.projectProducer.mapper.fund.GroupFundPlanDetailMapper;
import com.jpmsoft.projectProducer.pojo.entity.fund.GroupFundPlanDetail;
import com.jpmsoft.projectProducer.service.fund.IGroupFundPlanDetailService;
import org.springframework.stereotype.Service;

/**
 * 项目资金计划明细服务实现类
 */
@Service
public class GroupFundPlanDetailImpl extends ServiceImpl<GroupFundPlanDetailMapper, GroupFundPlanDetail> implements IGroupFundPlanDetailService {
}
