package com.jpmsoft.projectProducer.service.cockpit.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.requestAndResponse.SmartSite.SmartSiteCameraModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.cockpit.SmartSiteCameraMapper;
import com.jpmsoft.projectProducer.pojo.entity.cockpit.SmartSiteCamera;
import com.jpmsoft.projectProducer.service.cockpit.EzvizPlaybackService;
import com.jpmsoft.projectProducer.service.cockpit.ISmartSiteCameraService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.project.IPreProjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SmartSiteCameraImpl extends ServiceImpl<SmartSiteCameraMapper, SmartSiteCamera> implements ISmartSiteCameraService {

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private IPreProjectService preProjectService;

    @Resource
    private com.jpmsoft.projectProducer.service.cockpit.EzvizDeviceService ezvizDeviceService;

    @Resource
    private com.jpmsoft.projectProducer.service.cockpit.EzvizPtzService ezvizPtzService;

    @Resource
    private EzvizPlaybackService ezvizPlaybackService;

    @Override
    public BaseResponse createOrUpdateSmartSiteCamera(SmartSiteCameraModel request) {
        SmartSiteCamera entity = new SmartSiteCamera();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getSscId())) {
            entity.setSscCreateTime(new Date());
            entity.setSscId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
        } else {
            SmartSiteCamera model = baseMapper.selectById(request.getSscId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            entity.setSscCreateTime(model.getSscCreateTime());
            entity.setSscCreateUid(model.getSscCreateUid());
            entity.setSscCreateUname(model.getSscCreateUname());
            baseMapper.updateById(entity);
        }
        return BaseResponse.ok(entity.getSscId());

    }

    @Override
    public BaseResponse getSmartSiteCameraList(SmartSiteCameraModel request, String uId) {
        String authSql = sysUserService.getAuthSql("Smart_Site_Camera", uId, "Ssc_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex());
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        return listQuerySmartSiteCamera(request, authSql);
    }

    @Override
    public BaseResponse getSmartSiteCameraList(SmartSiteCameraModel request) {
        return listQuerySmartSiteCamera(request, " where 1=1 ");
    }

    private BaseResponse listQuerySmartSiteCamera(SmartSiteCameraModel request, String authSql) {
        QueryMap<SmartSiteCameraModel> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("sscName").put("pId").put("pName")
                .put("sscCode").put("sscPlaybackAddress").put("sscState").put("sscCreateUname")
                .put("sscType").put("sscUrl").put("sscPosition").put("sscKind")
                .put("sscOverviewDisplay");
        IPage<SmartSiteCameraModel> page = new Page<>(request.getPage(), request.getSize());
        List<SmartSiteCameraModel> datas = baseMapper.getSmartSiteCameraList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getSmartSiteCameraById(String sscId) {
        SmartSiteCameraModel data = baseMapper.getSmartSiteCameraById(sscId);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getSmartSiteCameraPlayUrl(SmartSiteCameraModel request, String uId) {
        if (request == null || ValidateUtil.isEmpty(request.getSscId())) {
            return BaseResponse.fail(400, "缺少摄像头 ID");
        }
        if (!ezvizPlaybackService.isEnabled()) {
            return BaseResponse.fail(503, "萤石 EZOPEN 联调入口未启用");
        }
        if (ValidateUtil.isEmpty(uId)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }
        String authSql = sysUserService.getAuthSql("Smart_Site_Camera", uId, "Ssc_Create_Uid",
                OutUserAuthEnum.Unit_Auth.getIndex());
        if (ValidateUtil.isEmpty(authSql)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }
        SmartSiteCamera camera = baseMapper.getAuthorizedPlaybackCamera(
                request.getSscId(), request.getTeId(), authSql);
        if (camera == null || !request.getSscId().equals(camera.getSscId())
                || (ValidateUtil.isNotEmpty(request.getTeId()) && !request.getTeId().equals(camera.getTeId()))) {
            return BaseResponse.fail(ResMsg.noPermission);
        }
        try {
            return BaseResponse.ok(ezvizPlaybackService.createPlayUrl(camera));
        } catch (IllegalArgumentException e) {
            return BaseResponse.fail(422, e.getMessage());
        } catch (IllegalStateException e) {
            return BaseResponse.fail(502, e.getMessage());
        }
    }

    @Resource
    private com.jpmsoft.projectProducer.service.cockpit.EzvizRecordingService ezvizRecordingService;

    private SmartSiteCamera authorizedRecordingCamera(SmartSiteCameraModel request, String uId) {
        if (request == null || ValidateUtil.isEmpty(request.getSscId()))
            throw new com.jpmsoft.projectProducer.service.cockpit.EzvizRecordingService.Failure(400, "缺少摄像头 ID");
        if (ValidateUtil.isEmpty(uId)) throw new com.jpmsoft.projectProducer.service.cockpit.EzvizRecordingService.Failure(403, "缺少平台操作身份");
        String authSql = sysUserService.getAuthSql("Smart_Site_Camera", uId, "Ssc_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex());
        if (ValidateUtil.isEmpty(authSql)) throw new com.jpmsoft.projectProducer.service.cockpit.EzvizRecordingService.Failure(403, "无权操作该摄像头");
        SmartSiteCamera camera = baseMapper.getAuthorizedPlaybackCamera(request.getSscId(), request.getTeId(), authSql);
        if (camera == null || !request.getSscId().equals(camera.getSscId())
                || (ValidateUtil.isNotEmpty(request.getTeId()) && !request.getTeId().equals(camera.getTeId())))
            throw new com.jpmsoft.projectProducer.service.cockpit.EzvizRecordingService.Failure(403, "无权操作该摄像头");
        return camera;
    }

    @Override
    public BaseResponse getSmartSiteCameraRecordList(com.jpmsoft.projectProducer.pojo.dto.cockpit.CameraRecordingRequest request, String uId) {
        try { return BaseResponse.ok(ezvizRecordingService.records(authorizedRecordingCamera(request,uId),request)); }
        catch (com.jpmsoft.projectProducer.service.cockpit.EzvizRecordingService.Failure e) { return BaseResponse.fail(e.getCode(),e.getMessage()); }
        catch (IllegalStateException e) { return BaseResponse.fail(502,"萤石录像凭证获取失败"); }
    }

    @Override
    public BaseResponse getSmartSiteCameraRecordCalendar(com.jpmsoft.projectProducer.pojo.dto.cockpit.CameraRecordCalendarRequest request, String uId) {
        try { return BaseResponse.ok(ezvizRecordingService.calendar(authorizedRecordingCamera(request,uId),request)); }
        catch (com.jpmsoft.projectProducer.service.cockpit.EzvizRecordingService.Failure e) { return BaseResponse.fail(e.getCode(),e.getMessage()); }
        catch (IllegalStateException e) { return BaseResponse.fail(502,"萤石录像凭证获取失败"); }
    }

    @Override
    public BaseResponse getSmartSiteCameraPlaybackConfig(com.jpmsoft.projectProducer.pojo.dto.cockpit.CameraRecordingRequest request, String uId) {
        try { return BaseResponse.ok(ezvizRecordingService.playback(authorizedRecordingCamera(request,uId),request)); }
        catch (com.jpmsoft.projectProducer.service.cockpit.EzvizRecordingService.Failure e) { return BaseResponse.fail(e.getCode(),e.getMessage()); }
        catch (IllegalStateException e) { return BaseResponse.fail(502,"萤石录像凭证获取失败"); }
    }

    private SmartSiteCamera authorizedControlCamera(SmartSiteCameraModel request, String uId) {
        if (request == null || ValidateUtil.isEmpty(request.getSscId()))
            throw new com.jpmsoft.projectProducer.service.cockpit.EzvizPtzService.Failure(400, "缺少摄像头 ID");
        if (ValidateUtil.isEmpty(uId)) throw new com.jpmsoft.projectProducer.service.cockpit.EzvizPtzService.Failure(403, "缺少平台操作身份");
        String authSql = sysUserService.getAuthSql("Smart_Site_Camera", uId, "Ssc_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex());
        if (ValidateUtil.isEmpty(authSql)) throw new com.jpmsoft.projectProducer.service.cockpit.EzvizPtzService.Failure(403, "无权操作该摄像头");
        SmartSiteCamera camera = baseMapper.getAuthorizedPlaybackCamera(request.getSscId(), request.getTeId(), authSql);
        if (camera == null || !request.getSscId().equals(camera.getSscId())
                || (ValidateUtil.isNotEmpty(request.getTeId()) && !request.getTeId().equals(camera.getTeId())))
            throw new com.jpmsoft.projectProducer.service.cockpit.EzvizPtzService.Failure(403, "无权操作该摄像头");
        return camera;
    }

    @Override
    public BaseResponse getSmartSiteCameraCapabilities(SmartSiteCameraModel request, String uId) {
        try { return BaseResponse.ok(ezvizPtzService.capabilities(authorizedControlCamera(request, uId))); }
        catch (com.jpmsoft.projectProducer.service.cockpit.EzvizPtzService.Failure e) { BaseResponse result = BaseResponse.fail(e.getCode(), e.getMessage()); result.setData(e.getDetails()); return result; }
    }

    @Override
    public BaseResponse controlSmartSiteCameraPtz(com.jpmsoft.projectProducer.pojo.dto.cockpit.CameraPtzRequest request, String uId) {
        try { return BaseResponse.ok(ezvizPtzService.control(authorizedControlCamera(request, uId), request, uId)); }
        catch (com.jpmsoft.projectProducer.service.cockpit.EzvizPtzService.Failure e) { BaseResponse result = BaseResponse.fail(e.getCode(), e.getMessage()); result.setData(e.getDetails()); return result; }
    }

    @Override
    public BaseResponse getSmartSiteCameraStatuses(SmartSiteCameraModel request, String uId) {
        if (request == null || ValidateUtil.isEmpty(request.getpId())) return BaseResponse.fail(400, "缺少项目 ID");
        if (ValidateUtil.isEmpty(uId)) return BaseResponse.fail(ResMsg.noPermission);
        String authSql = sysUserService.getAuthSql("Smart_Site_Camera", uId, "Ssc_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex());
        if (ValidateUtil.isEmpty(authSql)) return BaseResponse.fail(ResMsg.noPermission);
        List<SmartSiteCamera> cameras = baseMapper.getAuthorizedStatusCameras(request.getpId(), request.getTeId(), authSql);
        if (cameras.size() > 200) return BaseResponse.fail(422, "单次最多查询 200 路摄像头");
        return BaseResponse.ok(ezvizDeviceService.query(cameras));
    }

    @Override
    public BaseResponse deleteSmartSiteCameraById(String sscId) {
        SmartSiteCamera model = baseMapper.selectById(sscId);
        if (model == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        baseMapper.deleteById(sscId);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getSmartSiteCameraByPid(String pId, String sod) {
        List<String> pIds = new ArrayList<>();
        if (preProjectService.isParentProject(pId))
            pIds = preProjectService.getAllChildPids(pId);
        else
            pIds.add(pId);
        List<SmartSiteCameraModel> datas = baseMapper.getSmartSiteCameraByPid(pIds, ValidateUtil.isEmpty(sod) ? null : sod);
        return BaseResponse.ok(datas);
    }

}
