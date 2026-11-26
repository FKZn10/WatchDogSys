package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.SecInfo.SecQeGroupDtlRequest;
import com.jpmsoft.common.model.request.SecInfo.SecQeGroupRequest;
import com.jpmsoft.common.model.response.SecInfo.SecQeGroupDtlResponse;
import com.jpmsoft.common.model.response.SecInfo.SecQeGroupResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecQeGroupMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecQeGroup;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.secInfo.ISecQeGroupDtlService;
import com.jpmsoft.projectProducer.service.secInfo.ISecQeGroupService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SecQeGroupImpl extends ServiceImpl<SecQeGroupMapper, SecQeGroup> implements ISecQeGroupService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISecQeGroupDtlService secQeGroupDtlService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSecQeGroup(SecQeGroupRequest request) {
        SecQeGroup sec = new SecQeGroup();
        BeanUtils.copyProperties(request, sec);

        if (ValidateUtil.isEmpty(request.getSecId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        List<SecQeGroupDtlRequest> models = request.getSqGroupDtls();
        if (ValidateUtil.isEmpty(request.getQegId())) {
            sec.setQegCreateTime(new Date());
            sec.setQegId(GenerateUUID.getUUID());
            sec.setQegState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(sec);
            secQeGroupDtlService.createOrUpdateQeGroupDtl(models, sec.getQegId());
            sysFileService.saveFile(sec.getQegId(), request.gettId(), sec.getQegState() + "", sec.getQegCreateUid(), sec.getQegCreateUname()
                    , sec.getQegCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), sec.getQegId());

            return BaseResponse.ok(sec.getQegId());
        } else {
            SecQeGroup entity = baseMapper.selectById(request.getQegId());
            if (entity == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(entity.getQegState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setQegRemark(request.getQegRemark());
            baseMapper.updateById(entity);
            secQeGroupDtlService.createOrUpdateQeGroupDtl(models, sec.getQegId());
            sysFileService.saveFile(entity.getQegId(), request.gettId(), entity.getQegState() + "", entity.getQegCreateUid(), entity.getQegCreateUname()
                    , entity.getQegCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), sec.getQegId());

            return BaseResponse.ok(sec.getQegId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteSecQeGroupByQegId(String qegId) {
        SecQeGroup entity = baseMapper.selectById(qegId);
        if (entity == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        if (secQeGroupDtlService.havingQeGroupDtlByQegId(qegId))
            return BaseResponse.fail(ResMsg.delAssociated);
        baseMapper.deleteById(qegId);
        sysFileService.deleteFileById(qegId);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getSecQeGroupByQegId(String qegId) {
        SecQeGroupResponse data = baseMapper.getSecQeGroupByQegId(qegId);
        if (data != null) {
            List<SecQeGroupDtlResponse> dtls = secQeGroupDtlService.getSecQeGroupDtlByQegId(qegId);
            data.setSqGroupDtls(dtls);
        }
        return BaseResponse.ok(data);
    }

}
