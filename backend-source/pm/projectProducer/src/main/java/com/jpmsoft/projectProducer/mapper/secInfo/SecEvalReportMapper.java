package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.request.SecInfo.SecEvalReportClassInfoRequest;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecEvalReportDtlModel;
import com.jpmsoft.common.model.response.SecInfo.SecEvalReportResponse;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecEvalReport;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SecEvalReportMapper extends BaseMapper<SecEvalReport> {
    SecEvalReportResponse getSecEvalReportByErId(@Param("erId") String erId);

    List<SecEvalReportDtlModel> getSecEvalReportDtlModel(@Param("erId") String erId);

    List<SecEvalReportResponse> getSecEvalReportList(@Param("authSql") String authSql, @Param("page") Page<SecEvalReportClassInfoRequest> page, @Param("params") SecEvalReportClassInfoRequest request);

    List<Map<String, Object>> getAllValues(@Param("authSql") String authSql, @Param("selectClause") String selectClause, @Param("params") SecEvalReportClassInfoRequest request);
}
