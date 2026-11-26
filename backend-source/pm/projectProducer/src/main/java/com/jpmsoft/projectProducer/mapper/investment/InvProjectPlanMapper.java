package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.InvestmentControl.InvProjectPlanResponse;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvProjectPlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface InvProjectPlanMapper extends BaseMapper<InvProjectPlan> {

    List<InvProjectPlanResponse> getInvProjectPlanList(@Param("page") IPage<InvProjectPlanResponse> page, @Param("params") Map<String, Object> params);

    InvProjectPlanResponse getInvProjectPlanById(@Param("ippId") String ippId);

    List<InvProjectPlanResponse> getInvProjectPlanByPIdAndYear(@Param("pId") String pId, @Param("year") Integer year);

    List<InvProjectPlan> getInvProjectPlanListByPids(@Param("pIds") List<String> pIds);

}
