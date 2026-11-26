package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecDocumentReleaseModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecDocumentReleaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecDocumentRelease;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.secInfo.ISecAnswerQuestionService;
import com.jpmsoft.projectProducer.service.secInfo.ISecDocumentReleaseService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SecDocumentReleaseImpl extends ServiceImpl<SecDocumentReleaseMapper, SecDocumentRelease> implements ISecDocumentReleaseService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISecAnswerQuestionService secAnswerQuestionService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSecDocumentRelease(SecDocumentReleaseModel request) {
        SecDocumentRelease sec = new SecDocumentRelease();
        BeanUtils.copyProperties(request, sec);
        if (ValidateUtil.isEmpty(request.getSdrId())) {
            sec.setSdrCreateTime(new Date());
            sec.setSdrId(GenerateUUID.getUUID());
            sec.setState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(sec);
            sysFileService.saveFile(sec.getSdrId(), request.gettId(), sec.getState() + "", sec.getSdrCreateUid(), sec.getSdrCreateUname()
                    , sec.getSdrCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), request.getSecId(), request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), sec.getSdrId());

            return BaseResponse.ok(sec.getSdrId());
        } else {
            SecDocumentRelease entity = baseMapper.selectById(request.getSdrId());
            if (entity == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(entity.getState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            sec.setSdrCreateTime(entity.getSdrCreateTime());
            sec.setSdrCreateUid(entity.getSdrCreateUid());
            sec.setSdrCreateUname(entity.getSdrCreateUname());
            baseMapper.updateById(sec);
            sysFileService.saveFile(sec.getSdrId(), request.gettId(), sec.getState() + "", sec.getSdrCreateUid(), sec.getSdrCreateUname()
                    , sec.getSdrCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), request.getSecId(), request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), sec.getSdrId());

            return BaseResponse.ok(sec.getSdrId());
        }
    }

    @Override
    public BaseResponse getSecDocumentReleaseList(SecDocumentReleaseModel request) {
        QueryMap<SecDocumentReleaseModel> params = new QueryMap<>(request);
        params.put("secId").put("secName").put("secCode").put("sdrCreateUname").put("state").put("sdrBidYearMon")
                .put(request.getSdrCreateTimes(), "sdrCreateTimeStart", "sdrCreateTimeEnd")
                .put(request.getSdrBidOpenTimes(), "sdrBidOpenTimeStart", "sdrBidOpenTimeEnd")
                .put(request.getSdrControlPrices(), "sdrControlPriceStart", "sdrControlPriceEnd")
                .put(request.getSdrStages(), "sdrStageStart", "sdrStageEnd");
        List<SecDocumentReleaseModel> datas = baseMapper.getSecDocumentReleaseList(params.getQuery());
        Integer total = baseMapper.getSecDocumentReleaseListCount(params.getQuery());
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getSecDocumentReleaseById(String sdrId) {
        SecDocumentReleaseModel data = baseMapper.getSecDocumentReleaseById(sdrId);
        return BaseResponse.ok(data, 0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse appoval2SyncDocument(String sdrId) {
        SecDocumentRelease entity = baseMapper.selectById(sdrId);
        if (entity == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        if (entity.getState() != ApprovalStatusEnum.Already_Over.getIndex())
            return BaseResponse.fail(ResMsg.stateCannotAct);
        if (!secAnswerQuestionService.havingApprovedAnswerQuestion(entity.getSecId())) {
            if (!ValidateUtil.isEmpty(entity.getSecId()) && !ValidateUtil.isEmpty(entity.getSdrBidOpenTime()))
                baseMapper.appoval2SyncDocument(entity.getSecId(), entity.getSdrBidOpenTime());
        }
        return BaseResponse.ok();
    }

}
