package com.jpmsoft.projectProducer.service.fund;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.entity.fund.GroupFundPlan;
import com.jpmsoft.projectProducer.pojo.entity.fund.GroupFundPlanSummaryDetail;
import com.jpmsoft.projectProducer.pojo.vo.fund.GroupFundPlanSummaryDetailVo;

import java.util.List;

public interface IGroupFundPlanSummaryDetailService extends IService<GroupFundPlanSummaryDetail> {
    List<GroupFundPlanSummaryDetail> getDetailByGfpsId(String primaryId);

    List<GroupFundPlanSummaryDetailVo> getCompanyDetailByGfpsId(String id);

    List<GroupFundPlanSummaryDetail> getDetailById(List<String> gfpDetailIds);

    List<GroupFundPlan> getGroupFundPlanById(List<String> gfpDetailIds);
}
