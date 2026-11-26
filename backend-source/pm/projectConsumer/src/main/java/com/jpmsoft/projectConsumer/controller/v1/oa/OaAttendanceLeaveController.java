package com.jpmsoft.projectConsumer.controller.v1.oa;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaAttendanceLeaveModel;
import com.jpmsoft.projectConsumer.feign.OaFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "考勤-请假申请", value = "考勤-请假申请")
@RequestMapping("/OA/AttendanceLeave")
public class OaAttendanceLeaveController {
    @Autowired
    private OaFeign oaFeign;

    @ApiOperation(value = "新增或修改请假申请")
    @PostMapping("/createOrUpdateAttendanceLeave")
    public BaseResponse createOrUpdateAttendanceLeave(@RequestBody OaAttendanceLeaveModel request) {
        log.info("createOrUpdateAttendanceLeave");
        return oaFeign.createOrUpdateAttendanceLeave(request);
    }

    @ApiOperation(value = "分页查询请假申请")
    @PostMapping("/getAttendanceLeaveList")
    public BaseResponse getAttendanceLeaveList(@RequestBody OaAttendanceLeaveModel request) {
        log.info("getAttendanceLeaveList");
        return oaFeign.getAttendanceLeaveList(request);
    }

    @ApiOperation(value = "ID查询请假申请")
    @GetMapping("/getAttendanceLeaveById")
    public BaseResponse getAttendanceLeaveById(@RequestParam String alId) {
        log.info("getAttendanceLeaveById");
        return oaFeign.getAttendanceLeaveById(alId);
    }

    @PostMapping("/update")
    public BaseResponse update(@RequestBody OaAttendanceLeaveModel requestBean) {
        log.info("update");
        return oaFeign.update(requestBean);
    }

    @GetMapping("/updateApproveStatus")
    public BaseResponse updateApproveStatus(@RequestParam String id) {
        log.info("updateApproveStatus");
        return oaFeign.updateApproveStatus(id);
    }

    @GetMapping("/getById")
    public BaseResponse getById(@RequestParam String alId) {
        log.info("getById");
        return oaFeign.getById(alId);
    }
}
