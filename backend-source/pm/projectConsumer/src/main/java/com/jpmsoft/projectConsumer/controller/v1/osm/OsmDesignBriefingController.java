package com.jpmsoft.projectConsumer.controller.v1.osm;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.osm.OsmDesignBriefingDto;
import com.jpmsoft.projectConsumer.feign.OsmFeign;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private OsmFeign osmFeign;

    @PostMapping("/getDesignBriefingList")
    public BaseResponse getDesignBriefingList(@RequestBody OsmDesignBriefingDto osmDesignBriefingDto)
    {
        return osmFeign.getDesignBriefingList(osmDesignBriefingDto);
    }

}

