package com.jpmsoft.projectProducer.controller.v1.oa;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaAttendanceMemberModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.oa.IOaAttendanceMemberService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/OA/AttendanceMember")
public class OaAttendanceMemberController {

    @Resource
    private IOaAttendanceMemberService oaAttendanceMemberService;
    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateAttendanceMember")
    public BaseResponse createOrUpdateAttendanceMember(@RequestBody OaAttendanceMemberModel request) {
        log.info("createOrUpdateAttendanceMember");
        return oaAttendanceMemberService.createOrUpdateAttendanceMember(request);
    }

    @PostMapping("/createOrUpdateAttendanceMemberByBath")
    public BaseResponse createOrUpdateAttendanceMemberByBath(@RequestBody List<OaAttendanceMemberModel> request, @RequestHeader String token) {
        log.info("createOrUpdateAttendanceMemberByBath");
        return oaAttendanceMemberService.createOrUpdateAttendanceMemberByBath(request,redisUtils.getTeId(token));
    }

    @TenantId
    @PostMapping("/getAttendanceMemberList")
    public BaseResponse getAttendanceMemberList(@RequestBody OaAttendanceMemberModel request) {
        log.info("getAttendanceMemberList");
        return oaAttendanceMemberService.getAttendanceMemberList(request);
    }
}
