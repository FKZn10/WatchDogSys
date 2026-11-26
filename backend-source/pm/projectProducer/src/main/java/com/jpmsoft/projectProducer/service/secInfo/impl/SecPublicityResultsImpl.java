package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.SecInfo.SecPublicityResultsRequest;
import com.jpmsoft.common.model.response.SecInfo.SecPublicityResultsResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecPublicityResultsMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecPublicityResults;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.secInfo.ISecPublicityCpyService;
import com.jpmsoft.projectProducer.service.secInfo.ISecPublicityResultsService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import com.jpmsoft.projectProducer.utils.LogicUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class SecPublicityResultsImpl extends ServiceImpl<SecPublicityResultsMapper, SecPublicityResults> implements ISecPublicityResultsService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISecPublicityCpyService secPublicityCpyService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSecPublicityResults(SecPublicityResultsRequest request) {
        SecPublicityResults sec = new SecPublicityResults();
        BeanUtils.copyProperties(request, sec);

        if (ValidateUtil.isEmpty(request.getSecId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        if (ValidateUtil.isEmpty(request.getPrId())) {
            sec.setPrCreateTime(new Date());
            sec.setPrId(GenerateUUID.getUUID());
            sec.setPrState(ValidateUtil.isEmpty(request.getPrState()) ? ApprovalStatusEnum.Not_Started.getIndex() : request.getPrState());
            baseMapper.insert(sec);
            sysFileService.saveFile(sec.getPrId(), request.gettId(), sec.getPrState() + "", sec.getPrCreateUid(), sec.getPrCreateUname()
                    , sec.getPrCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getPrArmorApplication(), request.getPrResultRegistration(), request.getPrContentFile()), sec.getPrId());
        } else {
            SecPublicityResults entity = baseMapper.selectById(request.getPrId());
            if (entity == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(entity.getPrState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            sec.setPrCreateTime(entity.getPrCreateTime());
            sec.setPrCreateUid(entity.getPrCreateUid());
            sec.setPrCreateUname(entity.getPrCreateUname());
            sec.setSecId(entity.getSecId());
            baseMapper.updateById(sec);
            sysFileService.saveFile(sec.getPrId(), request.gettId(), sec.getPrState() + "", sec.getPrCreateUid(), sec.getPrCreateUname()
                    , sec.getPrCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getPrArmorApplication(), request.getPrResultRegistration(), request.getPrContentFile()),
                    Arrays.asList(request.getFileIds(), request.getPrArmorApplicationIds(), request.getPrResultRegistrationIds(), request.getPrContentFileIds()), sec.getPrId());
        }
        secPublicityCpyService.createOrUpdateSecPublicityCpy(request.getSecPublicityCpys(), sec.getPrId());
        return BaseResponse.ok(sec.getPrId());
    }

    @Override
    public BaseResponse getSecPublicityResultsList(SecPublicityResultsRequest request) {
        Map<String, Object> params = getFilterMap(request);
        List<SecPublicityResultsResponse> datas = baseMapper.getSecPublicityResultsList(params);
        Integer total = baseMapper.getSecPublicityResultsListCount(params);
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getSecPublicityResultsById(String prId) {
        SecPublicityResultsResponse data = baseMapper.getSecPublicityResultsById(prId);
        return BaseResponse.ok(data, 0);
    }

    @Override
    public BaseResponse getSecPublicityResultsListOne2Many(SecPublicityResultsRequest request) {
        Map<String, Object> params = getFilterMap(request);
        List<SecPublicityResultsResponse> datas = baseMapper.getSecPublicityResultsListOne2Many(params);
        Integer total = baseMapper.getSecPublicityResultsListOne2ManyCount(params);
        return BaseResponse.ok(datas, total);
    }

    private Map<String, Object> getFilterMap(SecPublicityResultsRequest request) {
        Map<String, Object> params = new HashMap<>();
        params.put("sortFiled", ValidateUtil.isEmpty(request.getSortFiled()) ? "Pr_Create_Time" : request.getSortFiled());
        params.put("sortType", ValidateUtil.isEmpty(request.getSortType()) ? "DESC" : request.getSortType());
        params.put("start", !JudgeUtil.checkObject(request.getPageStart()) ? request.getPageStart() : 0);
        params.put("size", !JudgeUtil.checkObject(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);

        params.put("prTenderee", ValidateUtil.isEmpty(request.getPrTenderee()) ? null : request.getPrTenderee());
        params.put("prBidWinner", ValidateUtil.isEmpty(request.getPrBidWinner()) ? null : request.getPrBidWinner());
        params.put("secId", ValidateUtil.isEmpty(request.getSecId()) ? null : request.getSecId());

        params.put("prState", ValidateUtil.isEmpty(request.getPrState()) ? null : request.getPrState());
        params.put("secName", ValidateUtil.isEmpty(request.getSecName()) ? null : request.getSecName());
        params.put("prTendereeName", ValidateUtil.isEmpty(request.getPrTendereeName()) ? null : request.getPrTendereeName());
        params.put("prTendereeTelephone", ValidateUtil.isEmpty(request.getPrTendereeTelephone()) ? null : request.getPrTendereeTelephone());
        params.put("prTendereeAddress", ValidateUtil.isEmpty(request.getPrTendereeAddress()) ? null : request.getPrTendereeAddress());
        params.put("prBidWinnerName", ValidateUtil.isEmpty(request.getPrBidWinnerName()) ? null : request.getPrBidWinnerName());
        params.put("prCreateUname", ValidateUtil.isEmpty(request.getPrCreateUname()) ? null : request.getPrCreateUname());
        LogicUtil.putParamToMap(params, request.getPrBidAmounts(), "prBidAmountStart", "prBidAmountEnd");
        LogicUtil.putParamToMap(params, request.getPrCreateTimes(), "prCreateTimeStart", "prCreateTimeEnd");
        LogicUtil.putParamToMap(params, request.getPrStartTimes(), "prStartTimeStart", "prStartTimeEnd");
        LogicUtil.putParamToMap(params, request.getPrEndTimes(), "prEndTimeStart", "prEndTimeEnd");
        params.put("pName", ValidateUtil.isEmpty(request.getpName()) ? null : request.getpName());
        params.put("teId", ValidateUtil.isEmpty(request.getTeId()) ? null : request.getTeId());
        params.put("prTitle", ValidateUtil.isEmpty(request.getPrTitle()) ? null : request.getPrTitle());
        return params;
    }
}
