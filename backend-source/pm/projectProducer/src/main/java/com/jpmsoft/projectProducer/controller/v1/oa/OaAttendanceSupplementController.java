package com.jpmsoft.projectProducer.controller.v1.oa;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaAttendanceSupplementModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.oa.IOaAttendanceSupplementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;


@Slf4j
@RestController
@RequestMapping("/v1/OA/AttendanceSupplement")
public class OaAttendanceSupplementController {

    @Resource
    private IOaAttendanceSupplementService oaAttendanceSupplementService;

    @TenantId
    @PostMapping("/createOrUpdateAttendanceSupplement")
    public BaseResponse createOrUpdateAttendanceSupplement(@RequestBody OaAttendanceSupplementModel request) {
        log.info("createOrUpdateAttendanceSupplement");
        return oaAttendanceSupplementService.createOrUpdateAttendanceSupplement(request);
    }

    @TenantId
    @PostMapping("/getAttendanceSupplementList")
    public BaseResponse getAttendanceSupplementList(@RequestBody OaAttendanceSupplementModel request) {
        log.info("getAttendanceSupplementList");
        return oaAttendanceSupplementService.getAttendanceSupplementList(request);
    }

    @GetMapping("/getAttendanceSupplementById")
    public BaseResponse getAttendanceSupplementById(@RequestParam String asId) {
        log.info("getAttendanceSupplementById");
        return oaAttendanceSupplementService.getAttendanceSupplementById(asId);
    }

    /**
     * 补卡后，修改考勤记录为正常
     *
     * @param request
     * @return
     */
    @PostMapping("/updateAttendance")
    public BaseResponse updateAttendance(@RequestBody OaAttendanceSupplementModel request) throws ParseException {
        log.info("updateAttendance");
        return oaAttendanceSupplementService.updateAttendance(request);
    }
}
