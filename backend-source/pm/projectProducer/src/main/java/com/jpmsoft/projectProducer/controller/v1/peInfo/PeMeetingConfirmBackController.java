package com.jpmsoft.projectProducer.controller.v1.peInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.PeInfo.PeMeetingConfirmBackRequest;
import com.jpmsoft.common.model.requestAndResponse.PeInfo.PeInfoModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.peInfo.IPeInfoService;
import com.jpmsoft.projectProducer.service.peInfo.IPeMeetingConfirmBackService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/meetingConfirm")
public class PeMeetingConfirmBackController {
    @Resource
    private IPeMeetingConfirmBackService peMeetingConfirmBackService;

    @Resource
    private IPeInfoService peInfoService;

    @Resource
    private RedisUtils redisUtils;


    @PostMapping("/updatePeInfoWhenMeetingConfirm")
    public BaseResponse updatePeInfoWhenMeetingConfirm(@RequestBody PeInfoModel model) {
        log.info("meetingConfirmBack");
        return peInfoService.updatePeInfoWhenMeetingConfirm(model);
    }

    @PostMapping("/meetingConfirmBack")
    public BaseResponse meetingConfirmBack(@RequestBody PeMeetingConfirmBackRequest request) {
        log.info("meetingConfirmBack");
        return peMeetingConfirmBackService.meetingConfirmBack(request);
    }

    @TenantId
    @PostMapping("/getMeetingConfirmBackList")
    public BaseResponse getMeetingConfirmBackList(@RequestBody PeMeetingConfirmBackRequest request, @RequestHeader String token) {
        log.info("getMeetingConfirmBackList");
        return peMeetingConfirmBackService.getMeetingConfirmBackList(request, redisUtils.getUid(token));
    }
}
