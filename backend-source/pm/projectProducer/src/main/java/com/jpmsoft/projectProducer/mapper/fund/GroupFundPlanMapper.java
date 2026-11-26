package com.jpmsoft.projectProducer.mapper.fund;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.fund.EngineeringFundApplication;
import com.jpmsoft.projectProducer.pojo.entity.fund.GroupFundPlan;
import com.jpmsoft.projectProducer.pojo.request.fund.GroupFundPlanRequest;
import com.jpmsoft.projectProducer.pojo.vo.fund.GroupFundPlanDetailVo;
import com.jpmsoft.projectProducer.pojo.vo.fund.GroupFundPlanVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface GroupFundPlanMapper extends BaseMapper<GroupFundPlan> {
    List<GroupFundPlanVo> getGroupFundPlanList(@Param("params")Map<String, Object> query,@Param("page")IPage<GroupFundPlanVo> page);

    GroupFundPlanVo getGroupFundPlanById(@Param("id")String id);

    List<EngineeringFundApplication> getEngineeringFundApplicationByPId(@Param("projectId")String projectId);

    List<GroupFundPlanVo> getFundPlanInfo(@Param("params")Map<String, Object> query);

    List<GroupFundPlan> getGroupFundPlans(@Param("projectId")String projectId);

    GroupFundPlanVo getProjectInfo(@Param("pId") String pId);

    List<GroupFundPlanDetailVo> getGroupFundPlanDetail(@Param("params") GroupFundPlanRequest request);

    void deleteUseSealApplycationByUsaId(@Param("usaId") String usaId);

    Integer existConMeasurePrice(@Param("gfpId") String gfpId, @Param("cmpId") String cmpId);
}
