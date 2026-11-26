package com.jpmsoft.projectProducer.controller.v1.cockpit;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SmartSite.SmartSiteCameraModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.cockpit.ISmartSiteCameraService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/SmartSite/v1/camera")
public class SmartSiteCameraController {
    @Resource
    private ISmartSiteCameraService smartSiteCameraService;
    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateSmartSiteCamera")
    public BaseResponse createOrUpdateSmartSiteCamera(@RequestBody SmartSiteCameraModel request) {
        log.info("createOrUpdateSmartSiteCamera");
        return smartSiteCameraService.createOrUpdateSmartSiteCamera(request);
    }

    @TenantId
    @PostMapping("/getSmartSiteCameraList")
    public BaseResponse getSmartSiteCameraList(@RequestBody SmartSiteCameraModel request, @RequestHeader String token) {
        log.info("getSmartSiteCameraList");
        return smartSiteCameraService.getSmartSiteCameraList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getSmartSiteCameraRecordList")
    public BaseResponse getSmartSiteCameraRecordList(@RequestBody com.jpmsoft.projectProducer.pojo.dto.cockpit.CameraRecordingRequest request, @RequestHeader("token") String token, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store");
        return smartSiteCameraService.getSmartSiteCameraRecordList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getSmartSiteCameraRecordCalendar")
    public BaseResponse getSmartSiteCameraRecordCalendar(@RequestBody com.jpmsoft.projectProducer.pojo.dto.cockpit.CameraRecordCalendarRequest request, @RequestHeader("token") String token, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store");
        return smartSiteCameraService.getSmartSiteCameraRecordCalendar(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getSmartSiteCameraPlaybackConfig")
    public BaseResponse getSmartSiteCameraPlaybackConfig(@RequestBody com.jpmsoft.projectProducer.pojo.dto.cockpit.CameraRecordingRequest request, @RequestHeader("token") String token, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store");
        return smartSiteCameraService.getSmartSiteCameraPlaybackConfig(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getSmartSiteCameraCapabilities")
    public BaseResponse getSmartSiteCameraCapabilities(@RequestBody SmartSiteCameraModel request,
            @RequestHeader("token") String token, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store");
        return smartSiteCameraService.getSmartSiteCameraCapabilities(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/controlSmartSiteCameraPtz")
    public BaseResponse controlSmartSiteCameraPtz(@RequestBody com.jpmsoft.projectProducer.pojo.dto.cockpit.CameraPtzRequest request,
            @RequestHeader("token") String token, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store");
        return smartSiteCameraService.controlSmartSiteCameraPtz(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getSmartSiteCameraStatuses")
    public BaseResponse getSmartSiteCameraStatuses(@RequestBody SmartSiteCameraModel request,
            @RequestHeader("token") String token, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store");
        return smartSiteCameraService.getSmartSiteCameraStatuses(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getSmartSiteCameraPlayUrl")
    public BaseResponse getSmartSiteCameraPlayUrl(@RequestBody SmartSiteCameraModel request,
                                                  @RequestHeader("token") String token,
                                                  HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store");
        return smartSiteCameraService.getSmartSiteCameraPlayUrl(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getSmartSiteCameraNoAuthList")
    public BaseResponse getSmartSiteCameraNoAuthList(@RequestBody SmartSiteCameraModel request) {
        log.info("getSmartSiteCameraNoAuthList");
        return smartSiteCameraService.getSmartSiteCameraList(request);
    }

    @GetMapping("/getSmartSiteCameraById")
    public BaseResponse getSmartSiteCameraById(@RequestParam String sscId) {
        log.info("getSmartSiteCameraById");
        return smartSiteCameraService.getSmartSiteCameraById(sscId);
    }

    @GetMapping("/deleteSmartSiteCameraById")
    public BaseResponse deleteSmartSiteCameraById(@RequestParam String sscId) {
        log.info("deleteSmartSiteCameraById");
        return smartSiteCameraService.deleteSmartSiteCameraById(sscId);
    }

    @GetMapping("/getSmartSiteCameraByPid")
    public BaseResponse getSmartSiteCameraByPid(@RequestParam(required = false) String pId, @RequestParam(required = false) String sod) {
        log.info("getSmartSiteCameraByPid");
        return smartSiteCameraService.getSmartSiteCameraByPid(pId, sod);
    }
}
