package com.jpmsoft.projectProducer.controller.v1.oa;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.ReportedPersonnelModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.oa.IReportedPersonnelService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/OA/repPersonnel")
public class ReportedPersonnelController {

    @Resource
    private IReportedPersonnelService reportedPersonnelService;

    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateReportedPersonnel")
    public BaseResponse createOrUpdateReportedPersonnel(@RequestBody ReportedPersonnelModel request) {
        log.info("createOrUpdateReportedPersonnel");
        return reportedPersonnelService.createOrUpdateReportedPersonnel(request);
    }

    @TenantId
    @PostMapping("/createOrUpdateReportedPersonnelCj")
    public BaseResponse createOrUpdateReportedPersonnelCj(@RequestBody ReportedPersonnelModel request) {
        log.info("createOrUpdateReportedPersonnelCj");
        return reportedPersonnelService.createOrUpdateReportedPersonnelCj(request);
    }


    @TenantId
    @PostMapping("/getReportedPersonnelList")
    public BaseResponse getReportedPersonnelList(@RequestBody ReportedPersonnelModel request, @RequestHeader String token) {
        log.info("getReportedPersonnelList");
        return reportedPersonnelService.getReportedPersonnelList(request, redisUtils.getUid(token));
    }

    @GetMapping("/getReportedPersonnelById")
    public BaseResponse getReportedPersonnelById(@RequestParam String rpId) {
        log.info("getReportedPersonnelById");
        return reportedPersonnelService.getReportedPersonnelById(rpId);
    }


    @GetMapping("/approveReportedPersonnelById")
    public BaseResponse approveReportedPersonnelById(@RequestParam String rpId) {
        log.info("approveReportedPersonnelById");
        return reportedPersonnelService.approveReportedPersonnelById(rpId);
    }

    /**
     * 北林人员变更
     *
     * @param rpId
     * @param token
     * @return
     */
    @GetMapping("/xazhjj/approve2SyncDepartmentP")
    public BaseResponse approve2SyncDepartmentP(@RequestParam String rpId, @RequestHeader String token) {
        log.info("approve2SyncDepartmentP");
        return reportedPersonnelService.approve2SyncDepartmentP(rpId, redisUtils.getUserInfoByToken(token));
    }


}
