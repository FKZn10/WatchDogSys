package com.jpmsoft.projectConsumer.controller.v1.oa;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaAttendanceModel;
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
@Api(tags = "考勤-考勤记录", value = "考勤-考勤记录")
@RequestMapping("/OA/Attendance")
public class OaAttendanceController {
    @Autowired
    private OaFeign oaFeign;

    @ApiOperation(value = "新增或修改考勤记录")
    @PostMapping("/createOrUpdateAttendance")
    public BaseResponse createOrUpdateAttendance(@RequestBody OaAttendanceModel request) {
        log.info("createOrUpdateAttendance");
        return oaFeign.createOrUpdateAttendance(request);
    }

    @ApiOperation(value = "分页查询考勤记录")
    @PostMapping("/getAttendanceList")
    public BaseResponse getAttendanceList(@RequestBody OaAttendanceModel request) {
        log.info("getAttendanceList");
        return oaFeign.getAttendanceList(request);
    }
}
