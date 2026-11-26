package com.jpmsoft.projectProducer.mapper.other;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.response.ProgressStatisticsResponse;
import com.jpmsoft.projectProducer.pojo.entity.investment.TInformationFilling;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MonthlyProgressMapper extends BaseMapper<TInformationFilling> {
    /**
     * 政府投资工程项目 进展情况统计表
     *
     * @param page
     * @param query
     * @return
     */
    List<ProgressStatisticsResponse> getProgressStatisticsList(@Param("page") Page<ProgressStatisticsResponse> page, @Param("params") Map<String, Object> query);

    /**
     * 政府投资工程项目 进展情况统计表 总数
     *
     * @param query
     * @return
     */
    Integer getProgressStatisticsListCount(@Param("params") Map<String, Object> query);
}
