package com.jpmsoft.projectProducer.service.subcontract.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.request.Subcontract.SubcontractApplyRequest;
import com.jpmsoft.common.model.response.Subcontract.SubcontractApplyResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.subcontract.SubcontractApplyMapper;
import com.jpmsoft.projectProducer.pojo.entity.subcontract.SubcontractApply;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.subcontract.ISubcontractApplyService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SubcontractApplyImpl extends ServiceImpl<SubcontractApplyMapper, SubcontractApply> implements ISubcontractApplyService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSubcontractApply(SubcontractApplyRequest request) {
        SubcontractApply entity = new SubcontractApply();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getSaId())) {
            entity.setSaId(GenerateUUID.getUUID());
            entity.setSaCreateTime(new Date());
            entity.setSaStateTime(null);
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getSaId(), request.gettId(), entity.getSaState() + "", entity.getSaCreateUid(), entity.getSaCreateUname()
                    , entity.getSaCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getSaId());

            return BaseResponse.ok(entity.getSaId());
        } else {
            SubcontractApply model = baseMapper.selectById(request.getSaId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getSaState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setSaCreateUid(model.getSaCreateUid());
            entity.setSaCreateTime(model.getSaCreateTime());
            entity.setSaCreateUname(model.getSaCreateUname());
            entity.setSaState(model.getSaState());
            entity.setSaStateTime(model.getSaStateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getSaId(), request.gettId(), entity.getSaState() + "", entity.getSaCreateUid(), entity.getSaCreateUname()
                    , entity.getSaCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "update", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getSaId());

            return BaseResponse.ok(entity.getSaId());
        }
    }

    @Override
    public BaseResponse getSubcontractApplyList(SubcontractApplyRequest request) {
        QueryMap<SubcontractApplyRequest> params = new QueryMap<>(request);
        params.put("pName").put("saTitle").put("saGcUnit").put("saUnit").put("pId");
        IPage<SubcontractApplyResponse> page = new Page<>(request.getPage(), request.getSize());
        List<SubcontractApplyResponse> datas = baseMapper.getSubcontractApplyList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());

    }

    @Override
    public BaseResponse getSubcontractApplyBySaId(String saId) {
        return BaseResponse.ok(baseMapper.getSubcontractApplyBySaId(saId));
    }
}
