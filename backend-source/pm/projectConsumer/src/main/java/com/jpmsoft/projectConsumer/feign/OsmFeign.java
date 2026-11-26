package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.osm.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "project-producer", contextId = "osm")
public interface OsmFeign {

    @PostMapping("/v1/osmBreachPenalty/getBreachPenaltyList")
    BaseResponse getBreachPenaltyList(@RequestBody OsmBreachPenaltyDto osmBreachPenaltyDto);

    @PostMapping("/v1/osmBreachPenalty/updateosmBreachPenalty")
    BaseResponse updateosmBreachPenalty(@RequestBody OsmBreachPenaltyDto osmBreachPenaltyDto);

    @PostMapping("/v1/osmCommencementReport/getCommencementReportList")
    BaseResponse getCommencementReportList(@RequestBody OsmCommencementReportDto osmCommencementReportDto);

    @PostMapping("/v1/osmDesignBriefing/getDesignBriefingList")
    BaseResponse getDesignBriefingList(@RequestBody OsmDesignBriefingDto osmDesignBriefingDto);

    @PostMapping("/v1/osmSiteMeetingMinutes/getSiteMeetingMinutesList")
    BaseResponse getSiteMeetingMinutesList(@RequestBody OsmSiteMeetingMinutesDto osmSiteMeetingMinutesDto);

    @PostMapping("/v1/osmSpecialMeetingMinutes/getSpecialMeetingMinutesList")
    BaseResponse getSpecialMeetingMinutesList(@RequestBody OsmSpecialMeetingMinutesDto osmDesignBriefingDto);


}
