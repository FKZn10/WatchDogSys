package com.jpmsoft.projectProducer.controller.v1.osm;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConContractRequest;
import com.jpmsoft.common.model.requestAndResponse.osm.OsmBreachPenaltyDto;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.osm.IOsmBreachPenaltyService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequestMapping("/v1/osmBreachPenalty")
public class OsmBreachPenaltyController {

    @Resource
    private IOsmBreachPenaltyService osmBreachPenaltyService;

    @PostMapping("/getBreachPenaltyList")
    public BaseResponse getBreachPenaltyList(@RequestBody OsmBreachPenaltyDto osmBreachPenaltyDto)
    {
        return osmBreachPenaltyService.getBreachPenaltyList(osmBreachPenaltyDto);
    }

    /**
     * 违约处罚新增确认
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/updateosmBreachPenalty")
    public BaseResponse updateosmBreachPenalty(@RequestBody OsmBreachPenaltyDto request) {
        log.info("updateContractInfoWJTQD");
        return osmBreachPenaltyService.updateosmBreachPenalty(request);
    }

}

