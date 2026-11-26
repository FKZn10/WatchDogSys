package com.jpmsoft.projectProducer.controller.v1.oa;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaAttendanceLocationModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.oa.IOaAttendanceLocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@RequestMapping("/v1/OA/AttendanceLocation")
public class OaAttendanceLocationController {

    @Resource
    private IOaAttendanceLocationService oaAttendanceLocationService;

    @TenantId
    @PostMapping("/createOrUpdateOaAttendanceLocation")
    public BaseResponse createOrUpdateOaAttendanceLocation(@RequestBody OaAttendanceLocationModel request) {
        log.info("createOrUpdateOaAttendanceLocation");
        return oaAttendanceLocationService.createOrUpdateOaAttendanceLocation(request);
    }

    @GetMapping("/deleteOaAttendanceLocationById")
    public BaseResponse deleteOaAttendanceLocationById(@RequestParam String alId) {
        log.info("deleteOaAttendanceLocationById");
        return oaAttendanceLocationService.deleteOaAttendanceLocationById(alId);
    }

    @TenantId
    @PostMapping("/getOaAttendanceLocationList")
    public BaseResponse getOaAttendanceLocationList(@RequestBody OaAttendanceLocationModel request) {
        log.info("getOaAttendanceLocationList");
        return oaAttendanceLocationService.getOaAttendanceLocationList(request);
    }
}
