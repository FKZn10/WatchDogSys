package com.jpmsoft.projectProducer.service.fund.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.projectProducer.mapper.fund.GroupFundPlanSummaryDetailMapper;
import com.jpmsoft.projectProducer.pojo.entity.fund.GroupFundPlan;
import com.jpmsoft.projectProducer.pojo.entity.fund.GroupFundPlanSummaryDetail;
import com.jpmsoft.projectProducer.pojo.vo.fund.GroupFundPlanSummaryDetailVo;
import com.jpmsoft.projectProducer.service.fund.IGroupFundPlanSummaryDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupFundPlanSummaryDetailImpl extends ServiceImpl<GroupFundPlanSummaryDetailMapper, GroupFundPlanSummaryDetail> implements IGroupFundPlanSummaryDetailService {
    @Override
    public List<GroupFundPlanSummaryDetail> getDetailByGfpsId(String primaryId) {
        return baseMapper.getDetailByGfpsId(primaryId);
    }

    @Override
    public List<GroupFundPlanSummaryDetailVo> getCompanyDetailByGfpsId(String id) {
        return baseMapper.getCompanyDetailByGfpsId(id);
    }

    @Override
    public List<GroupFundPlanSummaryDetail> getDetailById(List<String> gfpDetailIds) {
        return baseMapper.getDetailById(gfpDetailIds);
    }

    @Override
    public List<GroupFundPlan> getGroupFundPlanById(List<String> gfpDetailIds) {
        return baseMapper.getGroupFundPlanById(gfpDetailIds);
    }
}
