package com.jpmsoft.projectConsumer.controller.v1.enghonorpunish;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.enghonorpunish.EngHonorDto;
import com.jpmsoft.projectConsumer.feign.EngHonorPunishFeign;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/v1/engHonor")
public class EngHonorController {

    @Resource
    private EngHonorPunishFeign engHonorPunishFeign;


    @PostMapping("/getEngHonorList")
    public BaseResponse getEngHonorList(@RequestBody EngHonorDto engHonorDto)
    {
        return engHonorPunishFeign.getEngHonorList(engHonorDto);
    }

}

