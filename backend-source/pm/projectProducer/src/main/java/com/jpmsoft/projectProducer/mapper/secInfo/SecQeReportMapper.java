package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.SecInfo.SecQeReportResponse;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecQeReport;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SecQeReportMapper extends BaseMapper<SecQeReport> {
    SecQeReportResponse getQeReportByQerId(@Param("qerId") String qerId);

    List<SecQeReportResponse> getQeReportList(@Param("params") Map<String, Object> params);

    Integer getQeReportListCount(@Param("params") Map<String, Object> params);
}
