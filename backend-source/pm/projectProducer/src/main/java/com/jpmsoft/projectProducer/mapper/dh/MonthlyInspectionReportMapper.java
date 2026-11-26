package com.jpmsoft.projectProducer.mapper.dh;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.dh.MonthlyInspectionReportDTO;
import com.jpmsoft.projectProducer.pojo.entity.dh.MonthlyInspectionReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface MonthlyInspectionReportMapper extends BaseMapper<MonthlyInspectionReport> {
    List<MonthlyInspectionReportDTO> getMonthlyInspectionReportList(@Param("params") Map<String, Object> params);

    List<Map<String, Object>> getAllValues(@Param("selectClause") String selectClause, @Param("params") Map<String, Object> query);
}
