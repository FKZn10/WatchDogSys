package com.jpmsoft.projectProducer.mapper.riskWarn;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.RiskWarn.RiskWarnIndexResponse;
import com.jpmsoft.common.model.response.RiskWarn.RiskWarnResponse;
import com.jpmsoft.projectProducer.pojo.entity.riskWarn.RiskWarn;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface RiskWarnMapper extends BaseMapper<RiskWarn> {

    /**
     * 按指标 ID 分组统计预警数量，按数量倒序查询排名。
     *
     * @param page 固定第一页的条数限制，不查询总数
     * @param params 数据权限与筛选条件
     * @return 指标名称及预警数量
     */
    List<Map<String, Object>> getRiskWarnRanking(@Param("page") IPage<Map<String, Object>> page,
                                               @Param("params") Map<String, Object> params);

    RiskWarnResponse getRiskWarnById(@Param("rwId") String rwId);

    List<RiskWarnResponse> getRiskWarnList(@Param("page")  IPage<RiskWarnResponse> page,@Param("params") Map<String, Object> params);

    Map<String, Object> getRiskWarnListCoutnByYearAndMonth(@Param("yaer") Integer year, @Param("month") Integer month, @Param("teId") String teId);


    List<Map<String, Object>> selectRiskWarnAnnualQuery(@Param("params") Map<String, Object> params);

    List<RiskWarnIndexResponse> getKeyIndicatorQuery(@Param("params") Map<String, Object> params);

    List<Map<String, Object>> getDepartmentTaskQuery(@Param("params") Map<String, Object> params);

    List<Map<String, Object>> getProjectTaskQuery(@Param("params") Map<String, Object> params);

    Integer getKeyIndicatorQueryCount(@Param("params") Map<String, Object> params);

    List<Map<String, Object>> getDepartmentTaskQueryCount(@Param("params") Map<String, Object> params);

    List<Map<String, Object>> getProjectTaskQueryCount(@Param("params") Map<String, Object> params);

    List<RiskWarnResponse> getRiskWarnListOutline(@Param("page") IPage<RiskWarnResponse> page, @Param("params") Map<String, Object> query);
}
