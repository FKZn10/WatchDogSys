package com.jpmsoft.projectConsumer.controller.v1.osm;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.osm.OsmBreachPenaltyDto;
import com.jpmsoft.projectConsumer.feign.OsmFeign;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 违约处罚(OsmBreachPenalty)表控制层
 *
 * @author c
 * @since 2024-12-05 11:54:56
 */
@RestController
@RequestMapping("/v1/osmBreachPenalty")
public class OsmBreachPenaltyController {

    @Resource
    private OsmFeign osmFeign;

    @PostMapping("/getBreachPenaltyList")
    public BaseResponse getBreachPenaltyList(@RequestBody OsmBreachPenaltyDto osmBreachPenaltyDto)
    {
        return osmFeign.getBreachPenaltyList(osmBreachPenaltyDto);
    }


    @PostMapping("/updateosmBreachPenalty")
    public BaseResponse updateosmBreachPenalty(@RequestBody OsmBreachPenaltyDto osmBreachPenaltyDto)
    {
        return osmFeign.updateosmBreachPenalty(osmBreachPenaltyDto);
    }


}

