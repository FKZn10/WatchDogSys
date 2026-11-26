package com.jpmsoft.projectConsumer.controller.v1.oa;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaAttendanceMemberModel;
import com.jpmsoft.projectConsumer.feign.OaFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@Api(tags = "考勤-考勤人员", value = "考勤-考勤人员")
@RequestMapping("/OA/AttendanceMember")
public class OaAttendanceMemberController {
    @Autowired
    private OaFeign oaFeign;

    @ApiOperation(value = "新增或修改考勤人员")
    @PostMapping("/createOrUpdateAttendanceMember")
    public BaseResponse createOrUpdateAttendanceMember(@RequestBody OaAttendanceMemberModel request) {
        log.info("createOrUpdateAttendanceMember");
        return oaFeign.createOrUpdateAttendanceMember(request);
    }

    @ApiOperation(value = "新增或修改考勤人员(批量)")
    @PostMapping("/createOrUpdateAttendanceMemberByBath")
    public BaseResponse createOrUpdateAttendanceMemberByBath(@RequestBody List<OaAttendanceMemberModel> request) {
        log.info("createOrUpdateAttendanceMemberByBath");
        return oaFeign.createOrUpdateAttendanceMemberByBath(request);
    }

    @ApiOperation(value = "分页查询考勤人员")
    @PostMapping("/getAttendanceMemberList")
    public BaseResponse getAttendanceMemberList(@RequestBody OaAttendanceMemberModel request) {
        log.info("getAttendanceMemberList");
        return oaFeign.getAttendanceMemberList(request);
    }
}
