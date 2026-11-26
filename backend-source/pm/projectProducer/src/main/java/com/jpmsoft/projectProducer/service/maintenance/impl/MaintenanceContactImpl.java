package com.jpmsoft.projectProducer.service.maintenance.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.Maintenance.MaintenanceContactRequest;
import com.jpmsoft.common.model.response.Maintenance.MaintenanceContactResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.maintenance.MaintenanceContactMapper;
import com.jpmsoft.projectProducer.pojo.entity.maintenance.MaintenanceContact;
import com.jpmsoft.projectProducer.service.maintenance.IMaintenanceContactService;
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
public class MaintenanceContactImpl extends ServiceImpl<MaintenanceContactMapper, MaintenanceContact> implements IMaintenanceContactService {


    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateMaintenanceContact(MaintenanceContactRequest request) {
        MaintenanceContact entity = new MaintenanceContact();
        BeanUtils.copyProperties(request, entity);

        if (ValidateUtil.isEmpty(request.getMcId())) {
            entity.setMcId(GenerateUUID.getUUID());
            entity.setMcCreateTime(new Date());
            entity.setMcStateTime(null);
            entity.setMcState(request.getMcState() == null ? ApprovalStatusEnum.Not_Started.getIndex() : request.getMcState());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getMcId(), request.gettId(), entity.getMcState() + "", entity.getMcCreateUid(), entity.getMcCreateUname()
                    , entity.getMcCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), entity.getSecId(), request.gettCode(), entity.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getMcId());

        } else {
            MaintenanceContact model = baseMapper.selectById(request.getMcId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getMcState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setMcCreateTime(model.getMcCreateTime());
            entity.setMcCreateUid(model.getMcCreateUid());
            entity.setMcCreateUname(model.getMcCreateUname());
            entity.setMcState(model.getMcState());
            entity.setMcStateTime(model.getMcStateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getMcId(), request.gettId(), entity.getMcState() + "", entity.getMcCreateUid(), entity.getMcCreateUname()
                    , entity.getMcCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), entity.getSecId(), request.gettCode(), entity.getConId());
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getMcId());

        }
        return BaseResponse.ok(entity.getMcId());

    }

    @Override
    public BaseResponse getMaintenanceContactList(MaintenanceContactRequest request, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "Mc_Create_Uid", OutUserAuthEnum.Organization.getIndex(), "Mc_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<MaintenanceContactRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("conName").put("mcCId")
                .put("secId").put("pId");
        IPage<MaintenanceContactResponse> page = new Page<>(request.getPage(), request.getSize());
        List<MaintenanceContactResponse> datas = baseMapper.getMaintenanceContactList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getMaintenanceContactById(String mrId) {
        MaintenanceContactResponse data = baseMapper.getMaintenanceContactById(mrId);
        return BaseResponse.ok(data);
    }

}
