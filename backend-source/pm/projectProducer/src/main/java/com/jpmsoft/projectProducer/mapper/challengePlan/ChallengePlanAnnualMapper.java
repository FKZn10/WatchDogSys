package com.jpmsoft.projectProducer.mapper.challengePlan;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.request.challengePlan.ChallengePlanAnnualRequest;
import com.jpmsoft.common.model.response.challengePlan.ChallengePlanAnnualResponse;
import com.jpmsoft.projectProducer.pojo.entity.challengePlan.ChallengePlanAnnual;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author tianhelin
 * @date 2025/5/30
 */
@Mapper
public interface ChallengePlanAnnualMapper extends BaseMapper<ChallengePlanAnnual> {
    /**
     * 获取年度计划列表
     *
     * @param request
     * @return
     */
    List<ChallengePlanAnnualResponse> getChallengePlanAnnualList(@Param("params") ChallengePlanAnnualRequest request, @Param("offset") Integer offset);

    /**
     * 根据id获取年度计划
     *
     * @param id
     * @return
     */
    ChallengePlanAnnualResponse getChallengePlanAnnualByid(@Param("id") String id);
}
