package com.jpmsoft.projectProducer.controller.v1.enghonorpunish;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.enghonorpunish.EngPunishDto;
import com.jpmsoft.projectProducer.service.enghonorpunish.IEngPunishService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 工程处罚(EngPunish)
 *
 * @since 2024-12-03 10:40:10
 */
@RestController
@RequestMapping("/v1/engPunish")
public class EngPunishController {

    @Resource
    private IEngPunishService engPunishService;

    @PostMapping("/getEngPunishList")
    public BaseResponse getEngPunishList(@RequestBody EngPunishDto engPunishDto)
    {
        return engPunishService.getEngPunishList(engPunishDto);
    }


}

