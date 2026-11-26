package com.jpmsoft.projectConsumer.controller.v1.osm;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.osm.OsmCommencementReportDto;
import com.jpmsoft.projectConsumer.feign.OsmFeign;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * 开工报告(OsmCommencementReport)表控制层
 *
 * @author c
 * @since 2024-12-05 11:55:00
 */
@RestController
@RequestMapping("/v1/osmCommencementReport")
public class OsmCommencementReportController {

    @Resource
    private OsmFeign osmFeign;

    @PostMapping("/getCommencementReportList")
    public BaseResponse getCommencementReportList(@RequestBody OsmCommencementReportDto osmCommencementReportDto)
    {
        return osmFeign.getCommencementReportList(osmCommencementReportDto);
    }

}

