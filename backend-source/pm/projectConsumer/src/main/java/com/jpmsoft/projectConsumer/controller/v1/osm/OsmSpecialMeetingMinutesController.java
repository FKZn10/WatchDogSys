package com.jpmsoft.projectConsumer.controller.v1.osm;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.osm.OsmSpecialMeetingMinutesDto;
import com.jpmsoft.projectConsumer.feign.OsmFeign;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * 专题会议纪要(OsmSpecialMeetingMinutes)表控制层
 *
 */
@RestController
@RequestMapping("/v1/osmSpecialMeetingMinutes")
public class OsmSpecialMeetingMinutesController {

    @Resource
    private OsmFeign osmFeign;

    @PostMapping("/getSpecialMeetingMinutesList")
    public BaseResponse getSpecialMeetingMinutesList(@RequestBody OsmSpecialMeetingMinutesDto osmDesignBriefingDto)
    {
        return osmFeign.getSpecialMeetingMinutesList(osmDesignBriefingDto);
    }

}

