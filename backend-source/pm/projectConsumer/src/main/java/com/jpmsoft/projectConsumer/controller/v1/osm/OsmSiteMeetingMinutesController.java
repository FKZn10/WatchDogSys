package com.jpmsoft.projectConsumer.controller.v1.osm;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.osm.OsmSiteMeetingMinutesDto;
import com.jpmsoft.projectConsumer.feign.OsmFeign;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * 工地例会纪要(OsmSiteMeetingMinutes)表控制层
 */
@RestController
@RequestMapping("/v1/osmSiteMeetingMinutes")
public class OsmSiteMeetingMinutesController {

    @Resource
    private OsmFeign osmFeign;

    @PostMapping("/getSiteMeetingMinutesList")
    public BaseResponse getSiteMeetingMinutesList(@RequestBody OsmSiteMeetingMinutesDto osmSiteMeetingMinutesDto)
    {
        return osmFeign.getSiteMeetingMinutesList(osmSiteMeetingMinutesDto);
    }

}

