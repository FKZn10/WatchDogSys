package com.jpmsoft.projectConsumer.controller.v1.cockpit;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SmartSite.SmartSiteCameraModel;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@Api(tags = "摄像头信息", value = "摄像头信息")
@RequestMapping("/SmartSite/v1/camera")
public class SmartSiteCameraController {
    // 默认开启
    public static Boolean PROXY_SERVICE_STATUS = true;
    @Autowired
    private BaseFeign baseFeign;
    @GetMapping("/getProxyService")
    public BaseResponse getProxyService(){
        return BaseResponse.ok(PROXY_SERVICE_STATUS);
    }
    @GetMapping("/startProxyService")
    public BaseResponse startProxyService(){
        try{
            Process process = Runtime.getRuntime().exec("systemctl start proxy");
        }catch (Exception e){

        }finally {
            PROXY_SERVICE_STATUS = true;
        }
        return BaseResponse.ok();
    }
    @GetMapping("/stopProxyService")
    public BaseResponse stopProxyService(){
        try{
            Process process = Runtime.getRuntime().exec("systemctl stop proxy");
        }catch (Exception e){
        }finally {
            PROXY_SERVICE_STATUS = false;
        }
        return BaseResponse.ok();
    }

    @ApiOperation(value = "创建或修改摄像头信息")
    @PostMapping("/createOrUpdateSmartSiteCamera")
    public BaseResponse createOrUpdateSmartSiteCamera(@RequestBody SmartSiteCameraModel request) {
        log.info("createOrUpdateSmartSiteCamera");
        return baseFeign.createOrUpdateSmartSiteCamera(request);
    }

    @ApiOperation(value = "分页摄像头信息")
    @PostMapping("/getSmartSiteCameraList")
    public BaseResponse getSmartSiteCameraList(@RequestBody SmartSiteCameraModel request, @RequestHeader String token) {
        log.info("getSmartSiteCameraList");
        return baseFeign.getSmartSiteCameraList(request, token);
    }

    @PostMapping("/getSmartSiteCameraRecordList")
    public BaseResponse getSmartSiteCameraRecordList(@RequestBody com.jpmsoft.projectConsumer.pojo.dto.cockpit.CameraRecordingRequest request, @RequestHeader("token") String token, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store");
        return baseFeign.getSmartSiteCameraRecordList(request, token);
    }

    @PostMapping("/getSmartSiteCameraRecordCalendar")
    public BaseResponse getSmartSiteCameraRecordCalendar(@RequestBody com.jpmsoft.projectConsumer.pojo.dto.cockpit.CameraRecordCalendarRequest request, @RequestHeader("token") String token, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store");
        return baseFeign.getSmartSiteCameraRecordCalendar(request, token);
    }

    @PostMapping("/getSmartSiteCameraPlaybackConfig")
    public BaseResponse getSmartSiteCameraPlaybackConfig(@RequestBody com.jpmsoft.projectConsumer.pojo.dto.cockpit.CameraRecordingRequest request, @RequestHeader("token") String token, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store");
        return baseFeign.getSmartSiteCameraPlaybackConfig(request, token);
    }

    @PostMapping("/getSmartSiteCameraCapabilities")
    public BaseResponse getSmartSiteCameraCapabilities(@RequestBody SmartSiteCameraModel request,
            @RequestHeader("token") String token, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store");
        return baseFeign.getSmartSiteCameraCapabilities(request, token);
    }

    @PostMapping("/controlSmartSiteCameraPtz")
    public BaseResponse controlSmartSiteCameraPtz(@RequestBody com.jpmsoft.projectConsumer.pojo.dto.cockpit.CameraPtzRequest request,
            @RequestHeader("token") String token, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store");
        return baseFeign.controlSmartSiteCameraPtz(request, token);
    }

    @PostMapping("/getSmartSiteCameraStatuses")
    public BaseResponse getSmartSiteCameraStatuses(@RequestBody SmartSiteCameraModel request,
            @RequestHeader("token") String token, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store");
        return baseFeign.getSmartSiteCameraStatuses(request, token);
    }

    @ApiOperation(value = "获取萤石实时播放地址")
    @PostMapping("/getSmartSiteCameraPlayUrl")
    public BaseResponse getSmartSiteCameraPlayUrl(@RequestBody SmartSiteCameraModel request,
                                                  @RequestHeader("token") String token,
                                                  HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store");
        return baseFeign.getSmartSiteCameraPlayUrl(request, token);
    }

    @ApiOperation(value = "分页摄像头信息(无鉴权)")
    @PostMapping("/getSmartSiteCameraNoAuthList")
    public BaseResponse getSmartSiteCameraNoAuthList(@RequestBody SmartSiteCameraModel request) {
        log.info("getSmartSiteCameraNoAuthList");
        return baseFeign.getSmartSiteCameraNoAuthList(request);
    }

    @ApiOperation(value = "ID查询摄像头信息")
    @GetMapping("/getSmartSiteCameraById")
    public BaseResponse getSmartSiteCameraById(@RequestParam String sscId) {
        log.info("getSmartSiteCameraById");
        return baseFeign.getSmartSiteCameraById(sscId);
    }

    @ApiOperation(value = "ID删除摄像头信息")
    @GetMapping("/deleteSmartSiteCameraById")
    public BaseResponse deleteSmartSiteCameraById(@RequestParam String sscId) {
        log.info("deleteSmartSiteCameraById");
        return baseFeign.deleteSmartSiteCameraById(sscId);
    }

    @ApiOperation(value = "项目ID(子项目)获取摄像头信息")
    @GetMapping("/getSmartSiteCameraByPid")
    public BaseResponse getSmartSiteCameraByPid(@RequestParam(required = false) String pId, @RequestParam(required = false) String sod) {
        log.info("getSmartSiteCameraByPid");
        return baseFeign.getSmartSiteCameraByPid(pId, sod);
    }
}
