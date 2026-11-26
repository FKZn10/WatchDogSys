package com.jpmsoft.projectProducer.controller.v1.oa;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaAttendanceLeaveModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.oa.IOaAttendanceLeaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/OA/AttendanceLeave")
public class OaAttendanceLeaveController {

    @Resource
    private IOaAttendanceLeaveService oaAttendanceLeaveService;

    @TenantId
    @PostMapping("/createOrUpdateAttendanceLeave")
    public BaseResponse createOrUpdateAttendanceLeave(@RequestBody OaAttendanceLeaveModel request) {
        log.info("createOrUpdateAttendanceLeave");
        return oaAttendanceLeaveService.createOrUpdateAttendanceLeave(request);
    }

    @TenantId
    @PostMapping("/getAttendanceLeaveList")
    public BaseResponse getAttendanceLeaveList(@RequestBody OaAttendanceLeaveModel request) {
        log.info("getAttendanceLeaveList");
        return oaAttendanceLeaveService.getAttendanceLeaveList(request);
    }

    @GetMapping("/getAttendanceLeaveById")
    public BaseResponse getAttendanceLeaveById(@RequestParam String alId) {
        log.info("getAttendanceLeaveById");
        return oaAttendanceLeaveService.getAttendanceLeaveById(alId);
    }

    /**
     * 消假
     *
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse update(@RequestBody OaAttendanceLeaveModel request) {
        log.info("update");
        return oaAttendanceLeaveService.update(request);
    }

    /**
     * 消假
     *
     * @param id
     * @return
     */
    @GetMapping("/updateApproveStatus")
    public BaseResponse updateApproveStatus(@RequestParam String id) {
        log.info("updateApproveStatus");
        return oaAttendanceLeaveService.updateApproveStatus(id);
    }

    /**
     * 消假详情
     *
     * @param alId
     * @return
     */
    @GetMapping("/getById")
    public BaseResponse getById(@RequestParam String alId) {
        log.info("getById");
        return oaAttendanceLeaveService.getDetialById(alId);
    }


}
