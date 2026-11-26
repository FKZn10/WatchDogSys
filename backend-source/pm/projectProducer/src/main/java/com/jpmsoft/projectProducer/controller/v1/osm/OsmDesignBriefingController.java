package com.jpmsoft.projectProducer.controller.v1.osm;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.osm.OsmDesignBriefingDto;
import com.jpmsoft.projectProducer.service.osm.IOsmDesignBriefingService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 设计交底(OsmDesignBriefing)表控制层
 *
 * @author c
 * @since 2024-12-05 11:55:00
 */
@RestController
@RequestMapping("/v1/osmDesignBriefing")
public class OsmDesignBriefingController {

    @Resource
    private IOsmDesignBriefingService osmDesignBriefingService;

    @PostMapping("/getDesignBriefingList")
    public BaseResponse getDesignBriefingList(@RequestBody OsmDesignBriefingDto osmDesignBriefingDto)
    {
        return osmDesignBriefingService.getDesignBriefingList(osmDesignBriefingDto);
    }

}

