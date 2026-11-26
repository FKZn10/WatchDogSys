package com.jpmsoft.projectProducer.controller.v1.oa;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaAttendanceWeekdayModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.oa.IOaAttendanceWeekdayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/OA/AttendanceWeekday")
public class OaAttendanceWeekdayController {

    @Resource
    private IOaAttendanceWeekdayService oaAttendanceWeekdayService;

    @TenantId
    @PostMapping("/createOrUpdateAttendanceWeekday")
    public BaseResponse createOrUpdateAttendanceWeekday(@RequestBody OaAttendanceWeekdayModel request) {
        log.info("createOrUpdateAttendanceWeekday");
        return oaAttendanceWeekdayService.createOrUpdateAttendanceWeekday(request);
    }

    @TenantId
    @PostMapping("/getAttendanceWeekdayList")
    public BaseResponse getAttendanceWeekdayList(@RequestBody OaAttendanceWeekdayModel request) {
        log.info("getAttendanceWeekdayList");
        return oaAttendanceWeekdayService.getAttendanceWeekdayList(request);
    }
}
