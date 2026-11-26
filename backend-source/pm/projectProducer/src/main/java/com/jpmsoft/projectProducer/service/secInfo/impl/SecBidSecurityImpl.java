package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecBidSecurityModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecBidSecurityMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecBidSecurity;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.secInfo.ISecBidSecurityService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import com.jpmsoft.projectProducer.utils.MathUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SecBidSecurityImpl extends ServiceImpl<SecBidSecurityMapper, SecBidSecurity> implements ISecBidSecurityService {
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSecBidSecurity(SecBidSecurityModel request) {
        SecBidSecurity sec = new SecBidSecurity();
        BeanUtils.copyProperties(request, sec);

        if (ValidateUtil.isEmpty(request.getSecId()))
            return BaseResponse.fail(ResMsg.notReqParam);

        sec.setSbsCumulativePaymentAmount(MathUtil.DecimalMultiplicationInt(sec.getSbsCumulativePaymentAmount(), 10000));
        if (ValidateUtil.isEmpty(request.getSbsId())) {
            sec.setSbsCreateTime(new Date());
            sec.setSbsId(GenerateUUID.getUUID());
            sec.setSbsState(ValidateUtil.isEmpty(request.getSbsState()) ? ApprovalStatusEnum.Not_Started.getIndex() : request.getSbsState());
            baseMapper.insert(sec);
            sysFileService.saveFile(sec.getSbsId(), request.gettId(), sec.getSbsState() + "", sec.getSbsCreateUid(), sec.getSbsCreateUname()
                    , sec.getSbsCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), sec.getSbsId());

            return BaseResponse.ok(sec.getSbsId());
        } else {
            SecBidSecurity entity = baseMapper.selectById(request.getSbsId());
            if (entity == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(entity.getSbsState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            sec.setSbsCreateTime(entity.getSbsCreateTime());
            sec.setSbsCreateUid(entity.getSbsCreateUid());
            sec.setSbsCreateUname(entity.getSbsCreateUname());
            sec.setSecId(entity.getSecId());
            baseMapper.updateById(sec);
            sysFileService.saveFile(sec.getSbsId(), request.gettId(), sec.getSbsState() + "", sec.getSbsCreateUid(), sec.getSbsCreateUname()
                    , sec.getSbsCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), sec.getSbsId());

            return BaseResponse.ok(sec.getSbsId());
        }
    }

    @Override
    public BaseResponse getSecBidSecurityList(SecBidSecurityModel request) {
        QueryMap<SecBidSecurityModel> params = new QueryMap<>(request);
        params.put("sbsTitle").put("secId").put("sbsState")
                .put("secName").put("sbsCreateUname").put("sbsRemarks")
                .put(request.getSbsCreateTimes(), "sbsCreateTimeStart", "sbsCreateTimeEnd")
                .put(request.getSbsEnterPrices(), "sbsEnterPriceStart", "sbsEnterPriceEnd")
                .put(request.getSbsPayAmounts(), "sbsPayAmountStart", "sbsPayAmountEnd");
        List<SecBidSecurityModel> datas = baseMapper.getSecBidSecurityList(params.getQuery());
        Integer total = baseMapper.getSecBidSecurityListCount(params.getQuery());
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getSecBidSecurityById(String sbsId) {
        SecBidSecurityModel data = baseMapper.getSecBidSecurityById(sbsId);
        return BaseResponse.ok(data, 0);
    }
}
