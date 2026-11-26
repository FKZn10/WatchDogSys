package com.jpmsoft.projectProducer.service.maintenance.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.Maintenance.MaintenanceWarrantyRequest;
import com.jpmsoft.common.model.response.Maintenance.MaintenanceWarrantyResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.maintenance.MaintenanceWarrantyMapper;
import com.jpmsoft.projectProducer.pojo.entity.maintenance.MaintenanceWarranty;
import com.jpmsoft.projectProducer.service.maintenance.IMaintenanceWarrantyService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class MaintenanceWarrantyImpl extends ServiceImpl<MaintenanceWarrantyMapper, MaintenanceWarranty> implements IMaintenanceWarrantyService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateMaintenanceWarranty(MaintenanceWarrantyRequest request) {
        MaintenanceWarranty entity = new MaintenanceWarranty();
        BeanUtils.copyProperties(request, entity);

        if (ValidateUtil.isEmpty(request.getMwId())) {
            entity.setMwId(GenerateUUID.getUUID());
            entity.setMwCreateTime(new Date());
            entity.setMwStateTime(null);
            entity.setMwState(request.getMwState() == null ? ApprovalStatusEnum.Not_Started.getIndex() : request.getMwState());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getMwId(), request.gettId(), entity.getMwState() + "", entity.getMwCreateUid(), entity.getMwCreateUname()
                    , entity.getMwCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), entity.getSecId(), request.gettCode(), entity.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getMwId());

        } else {
            MaintenanceWarranty model = baseMapper.selectById(request.getMwId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getMwState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setMwCreateTime(model.getMwCreateTime());
            entity.setMwCreateUid(model.getMwCreateUid());
            entity.setMwCreateUname(model.getMwCreateUname());
            entity.setMwState(model.getMwState());
            entity.setMwStateTime(model.getMwStateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getMwId(), request.gettId(), entity.getMwState() + "", entity.getMwCreateUid(), entity.getMwCreateUname()
                    , entity.getMwCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), entity.getSecId(), request.gettCode(), entity.getConId());
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getMwId());

        }
        return BaseResponse.ok(entity.getMwId());

    }

    @Override
    public BaseResponse getMaintenanceWarrantyList(MaintenanceWarrantyRequest request, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "Mw_Create_Uid", OutUserAuthEnum.Organization.getIndex(), "Mw_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<MaintenanceWarrantyRequest> params = getFilter(request, authSql);
        IPage<MaintenanceWarrantyResponse> page = new Page<>(request.getPage(), request.getSize());
        List<MaintenanceWarrantyResponse> datas = baseMapper.getMaintenanceWarrantyList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getMaintenanceWarrantyListNoAuth(MaintenanceWarrantyRequest request) {
        QueryMap<MaintenanceWarrantyRequest> params = getFilter(request, null);
        IPage<MaintenanceWarrantyResponse> page = new Page<>(request.getPage(), request.getSize());
        List<MaintenanceWarrantyResponse> datas = baseMapper.getMaintenanceWarrantyList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    private QueryMap<MaintenanceWarrantyRequest> getFilter(MaintenanceWarrantyRequest request, String authSql) {
        QueryMap<MaintenanceWarrantyRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("conName").put("mwCId").put("secId")
                .put("mwState").put("mwCName").put("mwRemark").put("mwCreateUname").put("pId")
                .put(request.getMwCreateTimes(), "mwCreateTimeStart", "mwCreateTimeEnd");
        return params;
    }


    @Override
    public BaseResponse getMaintenanceWarrantyById(String mwId) {
        MaintenanceWarrantyResponse data = baseMapper.getMaintenanceWarrantyById(mwId);
        return BaseResponse.ok(data);
    }

}
