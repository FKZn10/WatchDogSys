package com.jpmsoft.projectProducer.service.investment.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.InvestmentControl.InvFundReleaseRequest;
import com.jpmsoft.common.model.response.InvestmentControl.InvFundReleaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.investment.InvFundReleaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvFundRelease;
import com.jpmsoft.projectProducer.service.investment.IInvFundReleaseService;
import com.jpmsoft.projectProducer.service.investment.IInvProjectPlanService;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class InvFundReleaseImpl extends ServiceImpl<InvFundReleaseMapper, InvFundRelease> implements IInvFundReleaseService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IInvProjectPlanService invProjectPlanService;
    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private ISysFileService sysFileService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateInvFundRelease(InvFundReleaseRequest request) {
        if (request.getInvProjectPlans() == null || request.getInvProjectPlans().size() < 1)
            return BaseResponse.fail(ResMsg.notReqParam);
        InvFundRelease entity = new InvFundRelease();
        String codeRule = null;
        if (request.getReqType() != null && request.getReqType() == 1) {
            BeanUtils.copyProperties(request, entity);
        } else {
            if (ValidateUtil.isEmpty(request.getIfrId()) && apiUtilsService.sequenceRestEnable("InvFundRelease") == true) {
                request.setIfrReplyCode(apiUtilsService.generateCodeByTCode("InvFundRelease", request));
                if (ValidateUtil.isEmpty(request.getIfrReplyCode()))
                    return BaseResponse.fail(ResMsg.codeGenerError);
                codeRule = "InvFundRelease";
            } else if (ValidateUtil.isEmpty(request.getIfrId()) && ValidateUtil.isEmpty(request.getIfrReplyCode())) {
                return BaseResponse.fail(ResMsg.codeIsEmpty);
            }
            BeanUtils.copyProperties(request, entity);
//            QueryWrapper<InvFundRelease> wrapper = new QueryWrapper<>();
//            wrapper.eq("Ifr_Reply_Code", request.getIfrReplyCode());
//            if (!ValidateUtil.isEmpty(entity.getIfrId()))
//                wrapper.ne("Ifr_Id", request.getIfrId());
//            if (baseMapper.selectCount(wrapper) > 0)
//                return BaseResponse.fail(ResMsg.replyNoExist);
        }
        if (ValidateUtil.isEmpty(entity.getIfrId())) {
            Date now = new Date();
            entity.setIfrCreateTime(now);
            entity.setStateTime(now);
            entity.setIfrId(GenerateUUID.getUUID());
            entity.setState(ValidateUtil.isEmpty(request.getState()) ? ApprovalStatusEnum.Not_Started.getIndex() : request.getState());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getIfrId(), request.gettId(), entity.getState() + "", entity.getIfrCreateUid(), entity.getIfrCreateUname()
                    , entity.getIfrCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(request.getIfrFile(), entity.getIfrId());
        } else {
            InvFundRelease model = baseMapper.selectById(request.getIfrId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            entity.setIfrCreateTime(model.getIfrCreateTime());
            entity.setIfrCreateUid(model.getIfrCreateUid());
            entity.setIfrCreateUname(model.getIfrCreateUname());
            entity.setState(model.getState());
            entity.setStateTime(model.getStateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getIfrId(), request.gettId(), entity.getState() + "", entity.getIfrCreateUid(), entity.getIfrCreateUname()
                    , entity.getIfrCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(request.getIfrFile(), request.getIfrFileIds(), entity.getIfrId());
        }
        invProjectPlanService.createOrUpdateInvProjectPlan(request.getInvProjectPlans(), entity.getIfrId());
        if (ValidateUtil.isNotEmpty(codeRule)) apiUtilsService.refershAutoNo("InvFundRelease", request);
        return BaseResponse.ok(entity.getIfrId());
    }


    @Override
    public BaseResponse getInvFundReleaseByIfrId(String ifrId) {
        InvFundReleaseResponse data = baseMapper.getInvFundReleaseByIfrId(ifrId);
        return BaseResponse.ok(data);
    }


    @Override
    public BaseResponse getInvFundReleaseList(InvFundReleaseRequest request) {
        QueryMap<InvFundReleaseRequest> params = new QueryMap<>(request);
        params.put("ifrFileName").put("ifrReplyCode").put("ifrCreateUname").put("state");
        List<InvFundReleaseResponse> datas = baseMapper.getInvFundReleaseList(params.getQuery());
        Integer total = baseMapper.getInvFundReleaseListCount(params.getQuery());
        return BaseResponse.ok(datas, total);
    }
}
