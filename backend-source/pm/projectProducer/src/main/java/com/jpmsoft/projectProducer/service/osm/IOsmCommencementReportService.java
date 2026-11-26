package com.jpmsoft.projectProducer.service.osm;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.osm.OsmCommencementReportDto;
import com.jpmsoft.projectProducer.pojo.entity.osm.OsmCommencementReport;

/**
 * 开工报告(OsmCommencementReport)表服务接口
 *
 * @author c
 * @since 2024-12-05 11:55:00
 */
public interface IOsmCommencementReportService extends IService<OsmCommencementReport> {


    BaseResponse getCommencementReportList(OsmCommencementReportDto osmCommencementReportDto);

}
