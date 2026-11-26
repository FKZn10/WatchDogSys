package com.jpmsoft.projectProducer.mapper.fund;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.fund.GroupFundPlan;
import com.jpmsoft.projectProducer.pojo.entity.fund.GroupFundPlanSummaryDetail;
import com.jpmsoft.projectProducer.pojo.vo.fund.GroupFundPlanSummaryDetailVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GroupFundPlanSummaryDetailMapper extends BaseMapper<GroupFundPlanSummaryDetail> {
    List<GroupFundPlanSummaryDetail> getDetailByGfpsId(@Param("primaryId")String primaryId);

    List<GroupFundPlanSummaryDetail> getDetailById(@Param("gfpDetailIds")List<String> gfpDetailIds);

    List<GroupFundPlan> getGroupFundPlanById(@Param("gfpDetailIds")List<String> gfpDetailIds);

    List<GroupFundPlanSummaryDetailVo> getCompanyDetailByGfpsId(@Param("id") String id);
}
