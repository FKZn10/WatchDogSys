package com.jpmsoft.projectConsumer.controller.v1.oa;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaAttendanceLocationModel;
import com.jpmsoft.projectConsumer.feign.OaFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@Api(tags = "考勤-考勤地点", value = "考勤-考勤地点")
@RequestMapping("/OA/AttendanceLocation")
public class OaAttendanceLocationController {
    @Autowired
    private OaFeign oaFeign;

    @ApiOperation(value = "新增或修改考勤地点")
    @PostMapping("/createOrUpdateOaAttendanceLocation")
    public BaseResponse createOrUpdateOaAttendanceLocation(@RequestBody OaAttendanceLocationModel request) {
        log.info("createOrUpdateOaAttendanceLocation");
        return oaFeign.createOrUpdateOaAttendanceLocation(request);
    }

    @ApiOperation(value = "ID删除考勤地点")
    @GetMapping("/deleteOaAttendanceLocationById")
    public BaseResponse deleteOaAttendanceLocationById(@RequestParam String alId) {
        log.info("deleteOaAttendanceLocationById");
        return oaFeign.deleteOaAttendanceLocationById(alId);
    }

    @ApiOperation(value = "分页查询考勤地点")
    @PostMapping("/getOaAttendanceLocationList")
    public BaseResponse getOaAttendanceLocationList(@RequestBody OaAttendanceLocationModel request) {
        log.info("getOaAttendanceLocationList");
        return oaFeign.getOaAttendanceLocationList(request);
    }
}
