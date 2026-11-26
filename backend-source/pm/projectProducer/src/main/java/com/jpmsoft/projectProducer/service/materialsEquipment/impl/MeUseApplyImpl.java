package com.jpmsoft.projectProducer.service.materialsEquipment.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.MeEnter.MeUseApplyReuqest;
import com.jpmsoft.common.model.response.MeEnter.MeUseApplyResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.materialsEquipment.MeUseApplyMapper;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeUseApply;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeUseApplyService;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeUsedNumberService;
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
public class MeUseApplyImpl extends ServiceImpl<MeUseApplyMapper, MeUseApply> implements IMeUseApplyService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IMeUsedNumberService meUsedNumberService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateMeUseApply(MeUseApplyReuqest request) {
        MeUseApply entity = new MeUseApply();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getUaId())) {
            entity.setUaCreateTime(new Date());
            entity.setUaId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getUaId(), request.gettId(), entity.getUaState() + "", entity.getUaCreateUid(), entity.getUaCreateUname()
                    , entity.getUaCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getUaId());
        } else {
            MeUseApply model = baseMapper.selectById(request.getUaId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getUaState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setUaState(model.getUaState());
            entity.setUaStateTime(model.getUaStateTime());
            entity.setUaCreateTime(model.getUaCreateTime());
            entity.setUaCreateUid(model.getUaCreateUid());
            entity.setUaCreateUname(model.getUaCreateUname());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getUaId(), request.gettId(), entity.getUaState() + "", entity.getUaCreateUid(), entity.getUaCreateUname()
                    , entity.getUaCreateTime(), entity.getPId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getUaId());
        }
        meUsedNumberService.createOrUpdateMeUsedNumber(request.getApplyDtls(),entity.getUaId());
        return BaseResponse.ok(entity.getUaId());
    }

    @Override
    public BaseResponse getMeUseApplyList(MeUseApplyReuqest request, String uId) {
        String authSql = sysUserService.getAuthSql("Me_Use_Apply", uId, "Ua_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Ua_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<MeUseApplyReuqest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pId").put("conId").put("metName");
        IPage<MeUseApplyResponse> page = new Page<>(request.getPage(), request.getSize());
        List<MeUseApplyResponse> datas = baseMapper.getMeUseApplyList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getMeUseApplyByUaId(String uaId) {
        MeUseApplyResponse data = baseMapper.getMeUseApplyByUaId(uaId);
        return BaseResponse.ok(data);
    }
}
