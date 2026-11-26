package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.request.SecInfo.SecPrequalificationReportRequest;
import com.jpmsoft.common.model.response.SecInfo.SecPrequalificationReportResponse;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecPrequalificationReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 资格预审情况报告 Mapper 接口
 */
@Mapper
public interface SecPrequalificationReportMapper extends BaseMapper<SecPrequalificationReport> {
    List<SecPrequalificationReportResponse> getSecPrequalificationReportList(@Param("authSql") String authSql, @Param("page") Page<SecPrequalificationReportResponse> page, @Param("params") SecPrequalificationReportRequest request);

    List<Map<String, Object>> getAllValues(@Param("authSql") String authSql, @Param("selectClause") String selectClause, @Param("params") SecPrequalificationReportRequest request);
}
