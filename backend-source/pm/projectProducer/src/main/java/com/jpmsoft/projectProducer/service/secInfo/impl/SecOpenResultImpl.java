package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.SecInfo.SecOpenResultRequest;
import com.jpmsoft.common.model.response.SecInfo.SecOpenResultResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecOpenResultMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecOpenResult;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.secInfo.ISecOpenResultCandidateService;
import com.jpmsoft.projectProducer.service.secInfo.ISecOpenResultListService;
import com.jpmsoft.projectProducer.service.secInfo.ISecOpenResultService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SecOpenResultImpl extends ServiceImpl<SecOpenResultMapper, SecOpenResult> implements ISecOpenResultService {


    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISecOpenResultListService secOpenResultListService;
    @Resource
    private ISecOpenResultCandidateService secOpenResultCandidateService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSecOpenResult(SecOpenResultRequest request) {
        SecOpenResult sec = new SecOpenResult();
        BeanUtils.copyProperties(request, sec);

        if (ValidateUtil.isEmpty(request.getSecId()))
            return BaseResponse.fail(ResMsg.notReqParam);

        if (ValidateUtil.isEmpty(request.getpId()) && ValidateUtil.isNotEmpty(request.getSecProjects()) && request.getSecProjects().size() == 1) {
            request.setpId(request.getSecProjects().get(0).getpId());
        }
        if (ValidateUtil.isEmpty(request.getOrId())) {
            sec.setOrCreateTime(new Date());
            sec.setOrId(GenerateUUID.getUUID());
            sec.setOrState(ValidateUtil.isEmpty(request.getOrState()) ? ApprovalStatusEnum.Not_Started.getIndex() : request.getOrState());
            baseMapper.insert(sec);
            sysFileService.saveFile(sec.getOrId(), request.gettId(), sec.getOrState() + "", sec.getOrCreateUid(), sec.getOrCreateUname()
                    , sec.getOrCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getOrOpeningDocuments()), sec.getOrId());

        } else {
            SecOpenResult entity = baseMapper.selectById(request.getOrId());
            if (entity == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(entity.getOrState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            sec.setOrCreateTime(entity.getOrCreateTime());
            sec.setOrCreateUid(entity.getOrCreateUid());
            sec.setOrCreateUname(entity.getOrCreateUname());
            sec.setSecId(entity.getSecId());
            baseMapper.updateById(sec);
            sysFileService.saveFile(sec.getOrId(), request.gettId(), sec.getOrState() + "", sec.getOrCreateUid(), sec.getOrCreateUname()
                    , sec.getOrCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getOrOpeningDocuments()),
                    Arrays.asList(request.getFileIds(), request.getOrOpeningDocumentsIds()), sec.getOrId());

        }
        secOpenResultListService.createOrUpdateSecOpenResultList(request.getSecOpenResultLists(), sec.getOrId());
        secOpenResultCandidateService.createOrUpdateSecOpenResultCandidate(request.getSecOpenResultCandidateList(), sec.getOrId());
        return BaseResponse.ok(sec.getOrId());
    }

    @Override
    public BaseResponse getSecOpenResultList(SecOpenResultRequest request) {
        Map<String, Object> params = getFilteMap(request);
        List<SecOpenResultResponse> datas = baseMapper.getSecOpenResultList(params);
        Integer total = baseMapper.getSecOpenResultListCount(params);
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getSecOpenResultById(String osId) {
        SecOpenResultResponse data = baseMapper.getSecOpenResultById(osId);
        return BaseResponse.ok(data, 0);
    }

    @Override
    public BaseResponse getSecOpenResultListOne2Many(SecOpenResultRequest request) {
        Map<String, Object> params = getFilteMap(request);
        List<SecOpenResultResponse> datas = baseMapper.getSecOpenResultListOne2Many(params);
        Integer total = baseMapper.getSecOpenResultListOne2ManyCount(params);
        return BaseResponse.ok(datas, total);
    }

    private Map<String, Object> getFilteMap(SecOpenResultRequest request) {
        QueryMap<SecOpenResultRequest> params = new QueryMap<>(request);
        params.put("secId").put("secName").put("orProcurementAgencyName").put("orPlaceBidOpening")
                .put("orCreateUname").put("pName").put("orBidMethod").put("orBulletinPlatform")
                .put("orCandidateOne").put("orCandidateTwo").put("orCandidateThree").put("secBiddingType")
                .put(request.getOrCreateTimes(), "orCreateTimeStart", "orCreateTimeEnd")
                .put(request.getOrOpenDates(), "orOpenDateStart", "orOpenDateEnd")
                .put(request.getOrBulletinStarts(), "orBulletinStartStart", "orBulletinStartEnd")
                .put(request.getOrBulletinEnds(), "orBulletinEndStart", "orBulletinEndEnd")
                .put(request.getOrApplyNums(), "orApplyNumStart", "orApplyNumEnd")
                .put(request.getOrValidQuotes(), "orValidQuoteStart", "orValidQuoteEnd")
                .put(request.getOrQuoteOnes(), "orQuoteOneStart", "orQuoteOneEnd")
                .put(request.getOrQuoteTwos(), "orQuoteTwoStart", "orQuoteTwoEnd")
                .put(request.getOrQuoteThrees(), "orQuoteThreeStart", "orQuoteThreeEnd")
                .put(request.getOrBulletinResStarts(), "orBulletinResStartStart", "orBulletinResStartEnd")
                .put(request.getSecBiddingCycles(), "secBiddingCycleStart", "secBiddingCycleEnd")
                .put(request.getOrBulletinResEnds(), "orBulletinResEndStart", "orBulletinResEndEnd")
                .put("pId");
        return params.getQuery();
    }
}
