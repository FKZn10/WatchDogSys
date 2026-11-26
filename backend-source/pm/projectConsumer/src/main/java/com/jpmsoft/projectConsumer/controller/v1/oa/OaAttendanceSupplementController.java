package com.jpmsoft.projectConsumer.controller.v1.oa;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaAttendanceSupplementModel;
import com.jpmsoft.projectConsumer.feign.OaFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;


@Slf4j
@RestController
@Api(tags = "考勤-补卡申请", value = "考勤-补卡申请")
@RequestMapping("/OA/AttendanceSupplement")
public class OaAttendanceSupplementController {
    @Autowired
    private OaFeign oaFeign;

    @ApiOperation(value = "新增或修改补卡申请")
    @PostMapping("/createOrUpdateAttendanceSupplement")
    public BaseResponse createOrUpdateAttendanceSupplement(@RequestBody OaAttendanceSupplementModel request) {
        log.info("createOrUpdateAttendanceSupplement");
        return oaFeign.createOrUpdateAttendanceSupplement(request);
    }

    @ApiOperation(value = "分页查询补卡申请")
    @PostMapping("/getAttendanceSupplementList")
    public BaseResponse getAttendanceSupplementList(@RequestBody OaAttendanceSupplementModel request) {
        log.info("getAttendanceSupplementList");
        return oaFeign.getAttendanceSupplementList(request);
    }

    @ApiOperation(value = "ID查询补卡申请")
    @GetMapping("/getAttendanceSupplementById")
    public BaseResponse getAttendanceSupplementById(@RequestParam String asId) {
        log.info("getAttendanceSupplementById");
        return oaFeign.getAttendanceSupplementById(asId);
    }

    /**
     * 补卡后，修改考勤记录为正常
     * @param request
     * @return
     */
    @PostMapping("/updateAttendance")
    public BaseResponse updateAttendance(@RequestBody OaAttendanceSupplementModel request) throws ParseException {
        log.info("updateAttendance");
        return oaFeign.updateAttendance(request);
    }
}
