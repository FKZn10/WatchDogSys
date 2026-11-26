package com.jpmsoft.projectProducer.controller.v1.enghonorpunish;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.enghonorpunish.EngHonorDto;
import com.jpmsoft.projectProducer.service.enghonorpunish.IEngHonorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 工程荣誉	(EngHonor)
 *
 * @since 2024-12-03 10:40:10
 */
@RestController
@RequestMapping("/v1/engHonor")
public class EngHonorController {

    @Resource
    private IEngHonorService engHonorService;

    @PostMapping("/getEngHonorList")
    public BaseResponse getEngHonorList(@RequestBody EngHonorDto engHonorDto)
    {
        return engHonorService.getEngHonorList(engHonorDto);
    }

}

