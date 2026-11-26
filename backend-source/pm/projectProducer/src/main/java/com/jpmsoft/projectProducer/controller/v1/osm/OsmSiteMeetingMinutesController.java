package com.jpmsoft.projectProducer.controller.v1.osm;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.osm.OsmSiteMeetingMinutesDto;
import com.jpmsoft.projectProducer.service.osm.IOsmSiteMeetingMinutesService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 工地例会纪要(OsmSiteMeetingMinutes)表控制层
 *
 * @author c
 * @since 2024-12-05 11:55:00
 */
@RestController
@RequestMapping("/v1/osmSiteMeetingMinutes")
public class OsmSiteMeetingMinutesController {

    @Resource
    private IOsmSiteMeetingMinutesService osmSiteMeetingMinutesService;

    @PostMapping("/getSiteMeetingMinutesList")
    public BaseResponse getSiteMeetingMinutesList(@RequestBody OsmSiteMeetingMinutesDto osmSiteMeetingMinutesDto)
    {
        return osmSiteMeetingMinutesService.getSiteMeetingMinutesList(osmSiteMeetingMinutesDto);
    }

}

