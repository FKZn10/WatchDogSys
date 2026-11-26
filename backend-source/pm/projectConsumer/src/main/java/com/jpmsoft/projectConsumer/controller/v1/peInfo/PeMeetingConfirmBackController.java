package com.jpmsoft.projectConsumer.controller.v1.peInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.PeInfo.PeMeetingConfirmBackRequest;
import com.jpmsoft.common.model.requestAndResponse.PeInfo.PeInfoModel;
import com.jpmsoft.projectConsumer.feign.PeInfoFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@Api(tags = "上会确认", value = "上会确认")
@RequestMapping("/v1/meetingConfirm")
public class PeMeetingConfirmBackController {
    @Autowired
    private PeInfoFeign peInfoFeign;

    @ApiOperation(value = "上会确认回退")
    @PostMapping("/meetingConfirmBack")
    public BaseResponse meetingConfirmBack(@RequestBody PeMeetingConfirmBackRequest request) {
        log.info("meetingConfirmBack");
        return peInfoFeign.meetingConfirmBack(request);
    }

    @ApiOperation(value = "获取上会确认回退列表")
    @PostMapping("/getMeetingConfirmBackList")
    public BaseResponse getMeetingConfirmBackList(@RequestBody PeMeetingConfirmBackRequest request, @RequestHeader String token) {
        log.info("getMeetingConfirmBackList");
        return peInfoFeign.getMeetingConfirmBackList(request, token);
    }

    @ApiOperation(value = "上会确认时修改")
    @PostMapping("/updatePeInfoWhenMeetingConfirm")
    public BaseResponse updatePeInfoWhenMeetingConfirm(@RequestBody PeInfoModel model) {
        log.info("meetingConfirmBack");
        return peInfoFeign.updatePeInfoWhenMeetingConfirm(model);
    }
}
