package com.jpmsoft.projectProducer.service.oa.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.request.Oa.OaSupervisionFeedbackRequest;
import com.jpmsoft.common.model.response.Oa.OaSupervisionFeedbackResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.oa.OaSupervisionFeedbackMapper;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaSupervisionFeedback;
import com.jpmsoft.projectProducer.service.oa.IOaSupervisionFeedbackService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class OaSupervisionFeedbackImpl extends ServiceImpl<OaSupervisionFeedbackMapper, OaSupervisionFeedback> implements IOaSupervisionFeedbackService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateOaSupervisionFeedback(OaSupervisionFeedbackRequest request) {
        OaSupervisionFeedback entity = new OaSupervisionFeedback();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(entity.getSfId())) {
            entity.setSfCreateTime(new Date());
            entity.setSfId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getSfId(), request.gettId(), entity.getSfState() + "", entity.getSfCreateUid(), entity.getSfCreateUname()
                    , entity.getSfCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getSfId());

        } else {
            OaSupervisionFeedback model = baseMapper.selectById(request.getSfId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getSfState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setSfState(model.getSfState());
            entity.setSfStateTime(model.getSfStateTime());
            entity.setSfCreateTime(model.getSfCreateTime());
            entity.setSfCreateUid(model.getSfCreateUid());
            entity.setSfCreateUname(model.getSfCreateUname());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getSfId(), request.gettId(), entity.getSfState() + "", entity.getSfCreateUid(), entity.getSfCreateUname()
                    , entity.getSfCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getSfId());

        }
        return BaseResponse.ok(entity.getSfId());
    }

    @Override
    public BaseResponse getOaSupervisionFeedbackList(OaSupervisionFeedbackRequest request) {
        QueryMap<OaSupervisionFeedbackRequest> params = new QueryMap<>(request);
        params.put("stId").put("pName").put("stCode").put("stTitle")
                .put("sfDId").put("sfDPerson").put("sfDPhone").put("pId");
        IPage<OaSupervisionFeedbackResponse> page = new Page<>(request.getPage(), request.getSize());
        List<OaSupervisionFeedbackResponse> datas = baseMapper.getOaSupervisionFeedbackList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getOaSupervisionFeedbackBySfId(String sfId) {
        return BaseResponse.ok(baseMapper.getOaSupervisionFeedbackBySfId(sfId));
    }
}
