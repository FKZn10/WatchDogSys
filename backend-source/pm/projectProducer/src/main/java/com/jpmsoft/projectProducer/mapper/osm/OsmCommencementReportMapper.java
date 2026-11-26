package com.jpmsoft.projectProducer.mapper.osm;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.osm.OsmCommencementReportDto;
import com.jpmsoft.projectProducer.pojo.entity.osm.OsmCommencementReport;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 开工报告(OsmCommencementReport)表数据库访问层
 *
 * @author c
 * @since 2024-12-05 11:55:00
 */
public interface OsmCommencementReportMapper extends BaseMapper<OsmCommencementReport> {


    List<OsmCommencementReportDto> getCommencementReportList(@Param("params") Map<String, Object> params, @Param("page") IPage<OsmCommencementReportDto> page);

}

