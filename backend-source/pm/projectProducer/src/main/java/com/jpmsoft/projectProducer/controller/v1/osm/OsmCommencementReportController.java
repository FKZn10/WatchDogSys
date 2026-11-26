package com.jpmsoft.projectProducer.controller.v1.osm;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.osm.OsmBreachPenaltyDto;
import com.jpmsoft.common.model.requestAndResponse.osm.OsmCommencementReportDto;
import com.jpmsoft.projectProducer.service.osm.IOsmCommencementReportService;
import org.springframework.web.bind.annotation.*;

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
    private IOsmCommencementReportService osmCommencementReportService;

    @PostMapping("/getCommencementReportList")
    public BaseResponse getCommencementReportList(@RequestBody OsmCommencementReportDto osmCommencementReportDto)
    {
        return osmCommencementReportService.getCommencementReportList(osmCommencementReportDto);
    }

}

