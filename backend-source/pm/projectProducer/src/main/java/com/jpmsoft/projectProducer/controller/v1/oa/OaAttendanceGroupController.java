package com.jpmsoft.projectProducer.controller.v1.oa;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaAttendanceGroupModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.oa.IOaAttendanceGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/OA/AttendanceGroup")
public class OaAttendanceGroupController {

    @Resource
    private IOaAttendanceGroupService oaAttendanceGroupService;


    @TenantId
    @PostMapping("/createOrUpdateAttendanceGroup")
    public BaseResponse createOrUpdateAttendanceGroup(@RequestBody OaAttendanceGroupModel request) {
        log.info("createOrUpdateAttendanceGroup");
        return oaAttendanceGroupService.createOrUpdateAttendanceGroup(request);
    }


    @TenantId
    @PostMapping("/createOrUpdateAttendanceGroupAndWeekday")
    public BaseResponse createOrUpdateAttendanceGroupAndWeekday(@RequestBody OaAttendanceGroupModel request) {
        log.info("createOrUpdateAttendanceGroupAndWeekday");
        return oaAttendanceGroupService.createOrUpdateAttendanceGroupAndWeekday(request);
    }

    @TenantId
    @PostMapping("/getAttendanceGroupList")
    public BaseResponse getAttendanceGroupList(@RequestBody OaAttendanceGroupModel request) {
        log.info("getAttendanceGroupList");
        return oaAttendanceGroupService.getAttendanceGroupList(request);
    }

    @GetMapping("/getAttendanceGroupById")
    public BaseResponse getAttendanceGroupById(@RequestParam String agId) {
        log.info("getAttendanceGroupById");
        return oaAttendanceGroupService.getAttendanceGroupById(agId);
    }
}
