package com.jpmsoft.projectProducer.mapper.challengePlan;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.request.challengePlan.ChallengePlanMonthlyPageRequest;
import com.jpmsoft.common.model.response.challengePlan.ChallengePlanMonthlyResponse;
import com.jpmsoft.projectProducer.pojo.entity.challengePlan.ChallengePlanMonthly;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author tianhelin
 * @date 2025/5/30
 */
@Mapper
public interface ChallengePlanMonthlyMapper extends BaseMapper<ChallengePlanMonthly> {
    /**
     * 获取月度计划列表
     *
     * @param request
     * @param offset
     * @param authSql
     * @return
     */
    List<ChallengePlanMonthlyResponse> getChallengePlanMonthlyList(@Param("params") ChallengePlanMonthlyPageRequest request, @Param("offset") Integer offset, @Param("authSql") String authSql);

    /**
     * 获取所有值
     *
     * @param selectClause
     * @param request
     * @param authSql
     * @return
     */
    List<Map<String, Object>> getAllValues(@Param("selectClause") String selectClause, @Param("params") ChallengePlanMonthlyPageRequest request, @Param("authSql") String authSql);

    /**
     * 获取月度计划总数
     *
     * @param request
     * @param authSql
     * @return
     */
    Integer getChallengePlanMonthlyCount(@Param("params") ChallengePlanMonthlyPageRequest request, @Param("authSql") String authSql);

    /**
     * 根据id获取月度计划信息
     *
     * @param id
     * @param authSql
     * @return
     */
    ChallengePlanMonthlyResponse getChallengePlanMonthlyById(@Param("id") String id, @Param("authSql") String authSql);

    /**
     * 获取后续的月度计划
     *
     * @param cpaId
     * @param declareYear
     * @param declareMonth
     * @return
     */
    List<ChallengePlanMonthly> getLaterChallengePlanMonthly(@Param("cpaId") String cpaId, @Param("declareYear") String declareYear, @Param("declareMonth") String declareMonth);
}
