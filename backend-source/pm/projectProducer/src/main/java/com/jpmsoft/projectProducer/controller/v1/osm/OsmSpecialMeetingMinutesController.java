package com.jpmsoft.projectProducer.controller.v1.osm;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.osm.OsmDesignBriefingDto;
import com.jpmsoft.common.model.requestAndResponse.osm.OsmSpecialMeetingMinutesDto;
import com.jpmsoft.projectProducer.service.osm.IOsmSpecialMeetingMinutesService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 专题会议纪要(OsmSpecialMeetingMinutes)表控制层
 *
 * @author c
 * @since 2024-12-05 11:55:00
 */
@RestController
@RequestMapping("/v1/osmSpecialMeetingMinutes")
public class OsmSpecialMeetingMinutesController {

    @Resource
    private IOsmSpecialMeetingMinutesService osmSpecialMeetingMinutesService;


    @PostMapping("/getSpecialMeetingMinutesList")
    public BaseResponse getSpecialMeetingMinutesList(@RequestBody OsmSpecialMeetingMinutesDto osmDesignBriefingDto)
    {
        return osmSpecialMeetingMinutesService.getSpecialMeetingMinutesList(osmDesignBriefingDto);
    }

}

