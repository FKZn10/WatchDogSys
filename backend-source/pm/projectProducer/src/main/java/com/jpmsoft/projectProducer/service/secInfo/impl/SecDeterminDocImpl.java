package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.SecInfo.SecDeterminDocRequest;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecDeterminDocMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecDeterminDoc;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.secInfo.ISecDeterminDocService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class SecDeterminDocImpl extends ServiceImpl<SecDeterminDocMapper, SecDeterminDoc> implements ISecDeterminDocService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateDeterminDoc(SecDeterminDocRequest request) {
        SecDeterminDoc sec = new SecDeterminDoc();
        BeanUtils.copyProperties(request, sec);

        if (ValidateUtil.isEmpty(request.getSecId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        if (ValidateUtil.isEmpty(request.getBdId())) {
            sec.setBdCreateTime(new Date());
            sec.setBdId(GenerateUUID.getUUID());
            sec.setBdState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(sec);
            sysFileService.saveFile(sec.getBdId(), request.gettId(), sec.getBdState() + "", sec.getBdCreateUid(), sec.getBdCreateUname()
                    , sec.getBdCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), sec.getBdId());

        } else {
            SecDeterminDoc entity = baseMapper.selectById(request.getBdId());
            if (entity == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(entity.getBdState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            sec.setBdCreateTime(entity.getBdCreateTime());
            sec.setBdCreateUid(entity.getBdCreateUid());
            sec.setBdCreateUname(entity.getBdCreateUname());
            sec.setSecId(entity.getSecId());
            sec.setBdState(entity.getBdState());
            sec.setBdStateTime(entity.getBdStateTime());
            baseMapper.updateById(sec);
            sysFileService.saveFile(sec.getBdId(), request.gettId(), sec.getBdState() + "", sec.getBdCreateUid(), sec.getBdCreateUname()
                    , sec.getBdCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), sec.getBdId());
        }
        return BaseResponse.ok(sec.getBdId());
    }

    @Override
    public BaseResponse cancelSecDeterminDocByBdId(String bdId) {
        SecDeterminDoc entity = baseMapper.selectById(bdId);
        if (entity == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        if (JudgeUtil.nonModifyState(entity.getBdState()))
            return BaseResponse.fail(ResMsg.stateNotModify);
        entity.setBdState(ApprovalStatusEnum.Invalid.getIndex());
        baseMapper.updateById(entity);
        return BaseResponse.ok(entity.getBdId());
    }

    @Override
    public BaseResponse getSecDeterminDocByBdId(String bdId) {
        return BaseResponse.ok(baseMapper.getSecDeterminDocByBdId(bdId));
    }
}
