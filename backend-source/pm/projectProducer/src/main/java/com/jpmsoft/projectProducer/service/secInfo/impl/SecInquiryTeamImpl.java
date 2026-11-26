package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecInquiryTeamGroupModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecInquiryTeamModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecInquiryTeamMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInquiryTeam;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.secInfo.ISecInquiryTeamGroupService;
import com.jpmsoft.projectProducer.service.secInfo.ISecInquiryTeamService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SecInquiryTeamImpl extends ServiceImpl<SecInquiryTeamMapper, SecInquiryTeam> implements ISecInquiryTeamService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISecInquiryTeamGroupService secInquiryTeamGroupService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSecInquiryTeam(SecInquiryTeamModel request) {
        SecInquiryTeam sec = new SecInquiryTeam();
        BeanUtils.copyProperties(request, sec);

        if (ValidateUtil.isEmpty(request.getSecId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        List<SecInquiryTeamGroupModel> models = request.getSecInquiryTeamGroupModels();
        if (ValidateUtil.isEmpty(request.getSitId())) {
            sec.setSitCreateTime(new Date());
            sec.setSitId(GenerateUUID.getUUID());
            sec.setState(request.getState() == null ? ApprovalStatusEnum.Not_Started.getIndex() : request.getState());
            baseMapper.insert(sec);
            secInquiryTeamGroupService.createOrUpdateInquiryTeamGroup(models, sec.getSitId());
            sysFileService.saveFile(sec.getSitId(), request.gettId(), sec.getState() + "", sec.getSitCreateUid(), sec.getSitCreateUname()
                    , sec.getSitCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), sec.getSitId());

            return BaseResponse.ok(sec.getSitId());
        } else {
            SecInquiryTeam entity = baseMapper.selectById(request.getSitId());
            if (entity == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(entity.getState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setSitRemarks(request.getSitRemarks());
            baseMapper.updateById(entity);
            secInquiryTeamGroupService.createOrUpdateInquiryTeamGroup(models, sec.getSitId());
            sysFileService.saveFile(entity.getSitId(), request.gettId(), entity.getState() + "", entity.getSitCreateUid(), entity.getSitCreateUname()
                    , entity.getSitCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), sec.getSitId());

            return BaseResponse.ok(sec.getSitId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteSecInquiryTeamBySitId(String sitId) {
        SecInquiryTeam entity = baseMapper.selectById(sitId);
        if (entity == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        if (secInquiryTeamGroupService.havingInquiryTeamGroupBySitId(sitId))
            return BaseResponse.fail(ResMsg.delAssociated);
        baseMapper.deleteById(sitId);
        sysFileService.deleteFileById(sitId);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getSecInquiryTeamBySitId(String sitId) {
        SecInquiryTeamModel data = baseMapper.getSecInquiryTeamBySitId(sitId);
        if (data != null) {
            List<SecInquiryTeamGroupModel> dtls = secInquiryTeamGroupService.getSecInquiryTeamGroupBySitId(sitId);
            data.setSecInquiryTeamGroupModels(dtls);
        }
        return BaseResponse.ok(data);
    }
}
