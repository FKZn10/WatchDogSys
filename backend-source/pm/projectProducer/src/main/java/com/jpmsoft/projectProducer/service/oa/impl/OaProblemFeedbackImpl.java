package com.jpmsoft.projectProducer.service.oa.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaProblemFeedbackModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.oa.OaProblemFeedbackMapper;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaProblemFeedback;
import com.jpmsoft.projectProducer.service.oa.IOaProblemDtlService;
import com.jpmsoft.projectProducer.service.oa.IOaProblemFeedbackService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 问题反馈
 */
@Service
public class OaProblemFeedbackImpl extends ServiceImpl<OaProblemFeedbackMapper, OaProblemFeedback> implements IOaProblemFeedbackService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IOaProblemDtlService oaProblemDtlService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateOaProblemFeedback(OaProblemFeedbackModel request) {
        OaProblemFeedback entity = new OaProblemFeedback();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(entity.getPmfId())) {
            entity.setPmfCreateTime(new Date());
            entity.setPmfId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getPmfId(), request.gettId(), entity.getPmfState() + "", entity.getPmfCreateUid(), entity.getPmfCreateUname(), entity.getPmfCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create", request.getFileRelationId(), null, request.gettCode(), request.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getPmfId());

        } else {
            OaProblemFeedback model = baseMapper.selectById(request.getPmfId());
            if (model == null) return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getPmfState())) return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setPmfState(model.getPmfState());
            entity.setPmfStateTime(model.getPmfStateTime());
            entity.setPmfCreateTime(model.getPmfCreateTime());
            entity.setPmfCreateUid(model.getPmfCreateUid());
            entity.setPmfCreateUname(model.getPmfCreateUname());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getPmfId(), request.gettId(), entity.getPmfState() + "", entity.getPmfCreateUid(), entity.getPmfCreateUname(), entity.getPmfCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update", request.getFileRelationId(), null, request.gettCode(), request.getConId());
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getPmfId());
        }
        return BaseResponse.ok(entity.getPmfId());
    }

    @Override
    public BaseResponse getOaProblemFeedbackList(OaProblemFeedbackModel request) {
        QueryMap<OaProblemFeedbackModel> params = new QueryMap<>(request);
        params.put("pName").put("conName").put("pId").put("conId").put("secId").put("secName").put("pmdTitle").put("pmdType").put("pmdContent").put("pmfState").put("pmfUid").put("pmfUname").put("pmfStatus");
        IPage<OaProblemFeedbackModel> page = new Page<>(request.getPage(), request.getSize());
        List<OaProblemFeedbackModel> datas = baseMapper.getOaProblemFeedbackList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getOaProblemFeedbackById(String pmfId) {
        OaProblemFeedbackModel data = baseMapper.getOaProblemFeedbackById(pmfId);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getOaProblemFeedbackByPmdId(String pmdId) {
        List<OaProblemFeedbackModel> data = baseMapper.getOaProblemFeedbackByPmdId(pmdId);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse approve2RefreshProblemStatus(String pmfId) {
        OaProblemFeedback pf = baseMapper.selectById(pmfId);
        if (pf == null) return BaseResponse.fail(ResMsg.infoNotExist);
        if (!Objects.equals(pf.getPmfState(), ApprovalStatusEnum.Already_Over.getIndex()))
            return BaseResponse.fail(ResMsg.stateCannotAct);
        if (pf.getPmfStatus() == 1) oaProblemDtlService.refreshProblemDtlStatus(pf.getPmdId(), pf.getPmfStatus());
        return BaseResponse.ok();
    }
}
