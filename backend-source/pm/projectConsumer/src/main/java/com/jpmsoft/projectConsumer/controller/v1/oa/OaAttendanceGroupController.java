package com.jpmsoft.projectConsumer.controller.v1.oa;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaAttendanceGroupModel;
import com.jpmsoft.projectConsumer.feign.OaFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "考勤-考勤组", value = "考勤-考勤组")
@RequestMapping("/OA/AttendanceGroup")
public class OaAttendanceGroupController {
    @Autowired
    private OaFeign oaFeign;

    @ApiOperation(value = "新增或修改考勤组")
    @PostMapping("/createOrUpdateAttendanceGroup")
    public BaseResponse createOrUpdateAttendanceGroup(@RequestBody OaAttendanceGroupModel request) {
        log.info("createOrUpdateAttendanceGroup");
        return oaFeign.createOrUpdateAttendanceGroup(request);
    }

    @ApiOperation(value = "新增或修改考勤组,工作日设置")
    @PostMapping("/createOrUpdateAttendanceGroupAndWeekday")
    public BaseResponse createOrUpdateAttendanceGroupAndWeekday(@RequestBody OaAttendanceGroupModel request) {
        log.info("createOrUpdateAttendanceGroupAndWeekday");
        return oaFeign.createOrUpdateAttendanceGroupAndWeekday(request);
    }

    @ApiOperation(value = "分页查询考勤组")
    @PostMapping("/getAttendanceGroupList")
    public BaseResponse getAttendanceGroupList(@RequestBody OaAttendanceGroupModel request) {
        log.info("getAttendanceGroupList");
        return oaFeign.getAttendanceGroupList(request);
    }

    @ApiOperation(value = "ID查询考勤组(附带人员，工作日设置)")
    @GetMapping("/getAttendanceGroupById")
    public BaseResponse getAttendanceGroupById(@RequestParam String agId) {
        log.info("getAttendanceGroupById");
        return oaFeign.getAttendanceGroupById(agId);
    }
}
