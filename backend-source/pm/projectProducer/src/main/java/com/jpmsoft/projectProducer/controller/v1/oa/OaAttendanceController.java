package com.jpmsoft.projectProducer.controller.v1.oa;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaAttendanceModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.oa.IOaAttendanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/OA/Attendance")
public class OaAttendanceController {

    @Resource
    private IOaAttendanceService oaAttendanceService;

    @TenantId
    @PostMapping("/createOrUpdateAttendance")
    public BaseResponse createOrUpdateAttendance(@RequestBody OaAttendanceModel request) {
        log.info("createOrUpdateAttendance");
        return oaAttendanceService.createOrUpdateAttendance(request);
    }


    @TenantId
    @PostMapping("/getAttendanceList")
    public BaseResponse getAttendanceList(@RequestBody OaAttendanceModel request) {
        log.info("getAttendanceList");
        return oaAttendanceService.getAttendanceList(request);
    }
}
