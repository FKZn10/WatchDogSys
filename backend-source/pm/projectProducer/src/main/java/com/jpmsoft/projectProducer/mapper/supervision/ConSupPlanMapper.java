package com.jpmsoft.projectProducer.mapper.supervision;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.Supervisor.ConSupPlanResponse;
import com.jpmsoft.projectProducer.pojo.entity.supervision.ConSupPlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ConSupPlanMapper extends BaseMapper<ConSupPlan> {


    List<ConSupPlanResponse> getConSupPlanList(@Param("page") IPage<ConSupPlanResponse> page, @Param("params") Map<String, Object> params);

    ConSupPlanResponse getConSupPlanById(@Param("spId") String spId);
}
