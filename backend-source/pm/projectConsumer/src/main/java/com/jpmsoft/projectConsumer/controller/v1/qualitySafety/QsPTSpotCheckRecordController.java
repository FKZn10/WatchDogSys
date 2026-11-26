package com.jpmsoft.projectConsumer.controller.v1.qualitySafety;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Qs.QsPTSpotCheckRecordRequest;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@Slf4j
@RestController
@RequestMapping("/safety/v1/qsPTSpotCheckRecord")
public class QsPTSpotCheckRecordController {

    @Resource
    private BaseFeign baseFeign;

    @PostMapping("/updateSpotCheckRectifyPic")
    public BaseResponse updateSpotCheckRectifyPic(@RequestBody QsPTSpotCheckRecordRequest request) {
        log.info("updateSpotCheckRectifyPic");
        return baseFeign.updateSpotCheckRectifyPic(request);
    }

}
