package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.SecInfo.SecControlPriceRequest;
import com.jpmsoft.common.model.response.SecInfo.SecControlPriceResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecControlPriceMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecControlPrice;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.secInfo.ISecControlCpyService;
import com.jpmsoft.projectProducer.service.secInfo.ISecControlPriceService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class SecControlPriceImpl extends ServiceImpl<SecControlPriceMapper, SecControlPrice> implements ISecControlPriceService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISecControlCpyService secControlCpyService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateControlPrice(SecControlPriceRequest request) {
        SecControlPrice sec = new SecControlPrice();
        BeanUtils.copyProperties(request, sec);
        if (ValidateUtil.isEmpty(request.getSecId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        if (ValidateUtil.isEmpty(request.getpId()) && ValidateUtil.isNotEmpty(request.getSecProjects()) && request.getSecProjects().size() == 1) {
            request.setpId(request.getSecProjects().get(0).getpId());
        }
        if (ValidateUtil.isEmpty(request.getCpId())) {
            sec.setCpCreateTime(new Date());
            sec.setCpId(GenerateUUID.getUUID());
            sec.setCpState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(sec);
            sysFileService.saveFile(sec.getCpId(), request.gettId(), sec.getCpState() + "", sec.getCpCreateUid(), sec.getCpCreateUname()
                    , sec.getCpCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getCpBoqDoc(), request.getCpConsultUnitDoc(), request.getCpFilingReceipt()
                    , request.getCpSelfPurchase(), request.getCpFocusPurchase(), request.getCpPurchaseDep(), request.getCpSubmitCensor(), request.getCpReviewComments(), request.getCpFinalOffer(), request.getCpSupplement(), request.getCpModify()
                    , request.getCpDelegation(), request.getCpCompileFile(), request.getCpDrawingFile(), request.getCpExamineFile(), request.getCpPublicityTable()), sec.getCpId());
        } else {
            SecControlPrice entity = baseMapper.selectById(request.getCpId());
            if (entity == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(entity.getCpState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            sec.setCpCreateTime(entity.getCpCreateTime());
            sec.setCpCreateUid(entity.getCpCreateUid());
            sec.setCpCreateUname(entity.getCpCreateUname());
            sec.setSecId(entity.getSecId());
            baseMapper.updateById(sec);
            sysFileService.saveFile(sec.getCpId(), request.gettId(), sec.getCpState() + "", sec.getCpCreateUid(), sec.getCpCreateUname()
                    , sec.getCpCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getCpBoqDoc(), request.getCpConsultUnitDoc(), request.getCpFilingReceipt()
                            , request.getCpSelfPurchase(), request.getCpFocusPurchase(), request.getCpPurchaseDep(), request.getCpSubmitCensor(), request.getCpReviewComments(), request.getCpFinalOffer(), request.getCpSupplement(), request.getCpModify()
                            , request.getCpDelegation(), request.getCpCompileFile(), request.getCpDrawingFile(), request.getCpExamineFile(), request.getCpPublicityTable()),
                    Arrays.asList(request.getFileIds(), request.getCpBoqDocIds(), request.getCpConsultUnitDocIds(), request.getCpFilingReceiptIds()
                            , request.getCpSelfPurchaseIds(), request.getCpFocusPurchaseIds(), request.getCpPurchaseDepIds(), request.getCpSubmitCensorIds(), request.getCpReviewCommentsIds(), request.getCpFinalOfferIds(), request.getCpSupplementIds(), request.getCpModifyIds()
                            , request.getCpDelegationIds(), request.getCpCompileFileIds(), request.getCpDrawingFileIds(), request.getCpExamineFileIds(), request.getCpPublicityTableIds()), sec.getCpId());
        }
        secControlCpyService.createOrUpdateControlCpy(request.getSecControlCpys(), sec.getCpId());
        return BaseResponse.ok(sec.getCpId());
    }

    @Override
    public BaseResponse deleteControlPriceBycpId(String cpId) {
        SecControlPrice entity = baseMapper.selectById(cpId);
        if (entity == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        baseMapper.deleteById(cpId);
        sysFileService.deleteFileById(cpId);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getControlPriceList(SecControlPriceRequest request) {
        QueryMap<SecControlPriceRequest> params = new QueryMap<>(request); //Cp_Create_Time
        params.put("cpAuditUnitName").put("cpType").put("cpState").put("cpConMode")
                .put("secName").put("cpPreparedByName").put("cpCreateUname").put("cpConsultingName")
                .put(request.getCpPreparedPrices(), "cpPreparedPriceStart", "cpPreparedPriceEnd")
                .put(request.getCpConstructPrices(), "cpConstructPriceStart", "cpConstructPriceEnd")
                .put(request.getCpEconomicIndicators(), "cpEconomicIndicatorStart", "cpEconomicIndicatorEnd")
                .put(request.getCpNonCompetitives(), "cpNonCompetitiveStart", "cpNonCompetitiveEnd")
                .put(request.getCpCreateTimes(), "cpCreateTimeStart", "cpCreateTimeEnd");
        List<SecControlPriceResponse> datas = baseMapper.getControlPriceList(params.getQuery());
        Integer total = baseMapper.getControlPriceListCount(params.getQuery());
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getControlPriceBycpId(String cpId) {
        SecControlPriceResponse data = baseMapper.getControlPriceBycpId(cpId);
        return BaseResponse.ok(data, 0);
    }

    @Override
    public BaseResponse getControlPriceBysecId(String secId) {
        SecControlPriceResponse data = baseMapper.getControlPriceBysecId(secId);
        return BaseResponse.ok(data, 0);
    }

    @Override
    public List<SecControlPriceResponse> getControlPriceBypId(String pId) {
        List<SecControlPriceResponse> data = baseMapper.getControlPriceBypId(pId);
        return data;
    }




}
