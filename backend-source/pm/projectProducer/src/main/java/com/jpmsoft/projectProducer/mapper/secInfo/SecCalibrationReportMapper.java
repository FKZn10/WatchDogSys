package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.request.SecInfo.SecCalibrationReportRequest;
import com.jpmsoft.common.model.response.SecInfo.SecCalibrationReportResponse;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecCalibrationReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 定标情况报告 Mapper 接口
 */
@Mapper
public interface SecCalibrationReportMapper extends BaseMapper<SecCalibrationReport> {
    List<SecCalibrationReportResponse> getSecCalibrationReportList(@Param("authSql") String authSql, @Param("page") Page<SecCalibrationReportResponse> page, @Param("params") SecCalibrationReportRequest request);

    List<Map<String, Object>> getAllValues(@Param("authSql") String authSql, @Param("selectClause") String selectClause, @Param("params") SecCalibrationReportRequest request);
}
