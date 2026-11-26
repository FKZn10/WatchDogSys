package com.jpmsoft.projectProducer.service.osm.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.requestAndResponse.osm.OsmCommencementReportDto;
import com.jpmsoft.common.model.requestAndResponse.osm.OsmSiteMeetingMinutesDto;
import com.jpmsoft.projectProducer.mapper.osm.OsmCommencementReportMapper;
import com.jpmsoft.projectProducer.pojo.entity.osm.OsmCommencementReport;
import com.jpmsoft.projectProducer.service.osm.IOsmCommencementReportService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 开工报告(OsmCommencementReport)表服务实现类
 *
 * @author c
 * @since 2024-12-05 11:55:00
 */
@Service
public class OsmCommencementReportServiceImpl extends ServiceImpl<OsmCommencementReportMapper, OsmCommencementReport> implements IOsmCommencementReportService {

    @Override
    public BaseResponse getCommencementReportList(OsmCommencementReportDto request) {
        QueryMap<OsmCommencementReportDto> params = new QueryMap<>(request);
        params.put("pId").put("pName").put("handlerUserName").put("usingSeals").
                put("sealsType").put("handlerUserName").put("state").put("createUname");
        IPage<OsmCommencementReportDto> page = new Page<>(request.getPage(), request.getSize());
        List<OsmCommencementReportDto> list = baseMapper.getCommencementReportList(params.getQuery(),page);
        return BaseResponse.ok(list, Long.valueOf(page.getTotal()).intValue());
    }


}
