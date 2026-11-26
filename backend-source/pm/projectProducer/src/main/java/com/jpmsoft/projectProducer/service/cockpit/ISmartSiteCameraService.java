package com.jpmsoft.projectProducer.service.cockpit;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SmartSite.SmartSiteCameraModel;
import com.jpmsoft.projectProducer.pojo.entity.cockpit.SmartSiteCamera;


public interface ISmartSiteCameraService extends IService<SmartSiteCamera> {

    BaseResponse createOrUpdateSmartSiteCamera(SmartSiteCameraModel request);

    BaseResponse getSmartSiteCameraList(SmartSiteCameraModel request, String uId);

    BaseResponse getSmartSiteCameraList(SmartSiteCameraModel request);

    BaseResponse getSmartSiteCameraById(String sscId);

    BaseResponse getSmartSiteCameraPlayUrl(SmartSiteCameraModel request, String uId);

    BaseResponse getSmartSiteCameraRecordList(com.jpmsoft.projectProducer.pojo.dto.cockpit.CameraRecordingRequest request, String uId);
    BaseResponse getSmartSiteCameraRecordCalendar(com.jpmsoft.projectProducer.pojo.dto.cockpit.CameraRecordCalendarRequest request, String uId);
    BaseResponse getSmartSiteCameraPlaybackConfig(com.jpmsoft.projectProducer.pojo.dto.cockpit.CameraRecordingRequest request, String uId);
    BaseResponse getSmartSiteCameraCapabilities(SmartSiteCameraModel request, String uId);
    BaseResponse controlSmartSiteCameraPtz(com.jpmsoft.projectProducer.pojo.dto.cockpit.CameraPtzRequest request, String uId);

    BaseResponse getSmartSiteCameraStatuses(SmartSiteCameraModel request, String uId);

    BaseResponse deleteSmartSiteCameraById(String sscId);

    /**
     * 传入父级项目id时查询出该项目下所有子项目的视频监控信息，
     * 传入子项目则返回子项目对应的视频监控信息，
     * 并支持显示位置Ssc_Overview_Display筛选
     *
     * @return
     */
    BaseResponse getSmartSiteCameraByPid(String pId, String sod);
}
