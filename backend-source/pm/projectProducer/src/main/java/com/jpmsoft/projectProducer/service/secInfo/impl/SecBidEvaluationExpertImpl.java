package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.SecInfo.SecBidEvaluationExpertRequest;
import com.jpmsoft.common.model.response.SecInfo.SecBidEvaluationExpertResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecBidEvaluationExpertMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecBidEvaluationExpert;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.secInfo.ISecBidEvaluationExpertService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class SecBidEvaluationExpertImpl extends ServiceImpl<SecBidEvaluationExpertMapper, SecBidEvaluationExpert> implements ISecBidEvaluationExpertService {


    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSecBidEvaluationExpert(SecBidEvaluationExpertRequest request) {
        SecBidEvaluationExpert sec = new SecBidEvaluationExpert();
        BeanUtils.copyProperties(request, sec);

        if (ValidateUtil.isEmpty(request.getSecId()))
            return BaseResponse.fail(ResMsg.notReqParam);

        if (ValidateUtil.isEmpty(request.getBeeId())) {
            sec.setBeeCreateTime(new Date());
            sec.setBeeId(GenerateUUID.getUUID());
            sec.setBeeState(ValidateUtil.isEmpty(request.getBeeState()) ? ApprovalStatusEnum.Not_Started.getIndex() : request.getBeeState());
            baseMapper.insert(sec);
            sysFileService.saveFile(sec.getBeeId(), request.gettId(), sec.getBeeState() + "", sec.getBeeCreateUid(), sec.getBeeCreateUname()
                    , sec.getBeeCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getBeeExpertApplication(), request.getBeeExpertFeeCollection(), request.getBeeExpertGign(), request.getBeeExpertStatement()), sec.getBeeId());

            return BaseResponse.ok(sec.getBeeId());
        } else {
            SecBidEvaluationExpert entity = baseMapper.selectById(request.getBeeId());
            if (entity == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(entity.getBeeState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            sec.setBeeCreateTime(entity.getBeeCreateTime());
            sec.setBeeCreateUid(entity.getBeeCreateUid());
            sec.setBeeCreateUname(entity.getBeeCreateUname());
            sec.setSecId(entity.getSecId());
            baseMapper.updateById(sec);
            sysFileService.saveFile(sec.getBeeId(), request.gettId(), sec.getBeeState() + "", sec.getBeeCreateUid(), sec.getBeeCreateUname()
                    , sec.getBeeCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getBeeExpertApplication(), request.getBeeExpertFeeCollection(), request.getBeeExpertGign(), request.getBeeExpertStatement()),
                    Arrays.asList(request.getFileIds(), request.getBeeExpertApplicationIds(), request.getBeeExpertFeeCollectionIds(), request.getBeeExpertGignIds(), request.getBeeExpertStatementIds()), sec.getBeeId());

            return BaseResponse.ok(sec.getBeeId());
        }
    }

    @Override
    public BaseResponse getSecBidEvaluationExpertList(SecBidEvaluationExpertRequest request) {
        QueryMap<SecBidEvaluationExpertRequest> params = new QueryMap<>(request);
        params.put("secName").put("beeEvaluationMethod").put("secId");
        List<SecBidEvaluationExpertResponse> datas = baseMapper.getSecBidEvaluationExpertList(params.getQuery());
        Integer total = baseMapper.getSecBidEvaluationExpertListCount(params.getQuery());
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getSecBidEvaluationExpertById(String beeId) {
        SecBidEvaluationExpertResponse data = baseMapper.getSecBidEvaluationExpertById(beeId);
        return BaseResponse.ok(data, 0);
    }
}
