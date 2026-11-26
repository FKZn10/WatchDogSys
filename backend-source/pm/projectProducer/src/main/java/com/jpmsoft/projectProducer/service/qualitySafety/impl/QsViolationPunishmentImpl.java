package com.jpmsoft.projectProducer.service.qualitySafety.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.Qs.QsViolationPunishmentRequest;
import com.jpmsoft.common.model.response.Qs.QsViolationPunishmentResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.qualitySafety.QsViolationPunishmentMapper;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsViolationPunishment;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.qualitySafety.IQsViolationPunishmentService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class QsViolationPunishmentImpl extends ServiceImpl<QsViolationPunishmentMapper, QsViolationPunishment> implements IQsViolationPunishmentService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "vpId", codeParam = "vpCode", method = "setVpCode", codeRule = "QsViolationPunishment", beanClass = QsViolationPunishmentRequest.class)
    public BaseResponse createOrUpdateQsViolationPunishment(QsViolationPunishmentRequest request) {
        QsViolationPunishment entity = new QsViolationPunishment();
        if (ValidateUtil.isEmpty(request.getVpCode()))
            return BaseResponse.fail(ResMsg.codeIsEmpty);
        BeanUtils.copyProperties(request, entity);

        if (ValidateUtil.isEmpty(request.getVpId())) {
            entity.setVpId(GenerateUUID.getUUID());
            entity.setVpCreateTime(new Date());
            entity.setVpStateTime(null);
            entity.setVpState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getVpId(), request.gettId(), entity.getVpState() + "", entity.getVpCreateUid(), entity.getVpCreateUname()
                    , entity.getVpCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), request.getSecId(), request.gettCode(), entity.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getVpId());

        } else {
            QsViolationPunishment model = baseMapper.selectById(request.getVpId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getVpState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setVpCreateTime(model.getVpCreateTime());
            entity.setVpCreateUid(model.getVpCreateUid());
            entity.setVpCreateUname(model.getVpCreateUname());
            entity.setVpState(model.getVpState());
            entity.setVpStateTime(model.getVpStateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getVpId(), request.gettId(), entity.getVpState() + "", entity.getVpCreateUid(), entity.getVpCreateUname()
                    , entity.getVpCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), request.getSecId(), request.gettCode(), entity.getConId());
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getVpId());

        }
        return BaseResponse.ok(entity.getVpId());
    }

    @Override
    public BaseResponse getQsViolationPunishmentList(QsViolationPunishmentRequest request, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "Vp_Create_Uid", OutUserAuthEnum.NOT.getIndex(), "Vp_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<QsViolationPunishmentRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("conName").put("vpCId").put("vpCName").put("vpCreateUname").put("pId")
                .put("secId").put("vpType").put("vpState").put("vpStates").put("vpCode").put("vpPageCode").put("vpLevel").put("qstName")
                .put("vpDId");
        IPage<QsViolationPunishmentResponse> page = new Page<>(request.getPage(), request.getSize());
        List<QsViolationPunishmentResponse> datas = baseMapper.getQsViolationPunishmentList(page, params.getQuery());
        return BaseResponse.ok(datas, Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse getQsViolationPunishmentById(String vpId) {
        QsViolationPunishmentResponse data = baseMapper.getQsViolationPunishmentById(vpId);
        return BaseResponse.ok(data);
    }

}
