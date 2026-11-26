package com.jpmsoft.projectConsumer.controller.v1.oa;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaAttendanceWeekdayModel;
import com.jpmsoft.projectConsumer.feign.OaFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api(tags = "考勤-工作日设置", value = "考勤-工作日设置")
@RequestMapping("/OA/AttendanceWeekday")
public class OaAttendanceWeekdayController {
    @Autowired
    private OaFeign oaFeign;

    @ApiOperation(value = "新增或修改工作日设置")
    @PostMapping("/createOrUpdateAttendanceWeekday")
    public BaseResponse createOrUpdateAttendanceWeekday(@RequestBody OaAttendanceWeekdayModel request) {
        log.info("createOrUpdateAttendanceWeekday");
        return oaFeign.createOrUpdateAttendanceWeekday(request);
    }

    @ApiOperation(value = "分页查询工作日设置")
    @PostMapping("/getAttendanceWeekdayList")
    public BaseResponse getAttendanceWeekdayList(@RequestBody OaAttendanceWeekdayModel request) {
        log.info("getAttendanceWeekdayList");
        return oaFeign.getAttendanceWeekdayList(request);
    }
}
