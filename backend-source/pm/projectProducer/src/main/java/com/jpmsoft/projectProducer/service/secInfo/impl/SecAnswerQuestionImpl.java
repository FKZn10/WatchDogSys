package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.model.request.SecInfo.SecAnswerQuestionRequest;
import com.jpmsoft.common.model.request.project.PpOwnerUnitDTO;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecAnswerQuestionDtlModel;
import com.jpmsoft.common.model.response.SecInfo.SecAnswerQuestionResponse;
import com.jpmsoft.common.utils.ClassInfoUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecAnswerQuestionMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecAnswerQuestion;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.project.IPpOwnerUnitService;
import com.jpmsoft.projectProducer.service.secInfo.*;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SecAnswerQuestionImpl extends ServiceImpl<SecAnswerQuestionMapper, SecAnswerQuestion> implements ISecAnswerQuestionService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISecAnswerQuestionDtlService secAnswerQuestionDtlService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISysConfigService sysConfigService;

    @Resource
    private IPpOwnerUnitService ippOwnerUnitService;

    @Resource
    private ISecAnswerQuestionDtlService iSecAnswerQuestionDtlService;

    @Resource
    private ISecInquiryRecordService secInquiryRecordService;

    @Resource
    private ISecBiddingNegotiationService secBiddingNegotiationService;

    @Resource
    private ISecDocumentService secDocumentService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSecAnswerQuestion(SecAnswerQuestionRequest request) {
        SecAnswerQuestion sec = new SecAnswerQuestion();
        BeanUtils.copyProperties(request, sec);
        if (ValidateUtil.isEmpty(request.getSecId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        if (ValidateUtil.isEmpty(request.getpId()) && ValidateUtil.isNotEmpty(request.getSecProjects()) && request.getSecProjects().size() == 1) {
            request.setpId(request.getSecProjects().get(0).getpId());
        }
        if (ValidateUtil.isEmpty(request.getAqId())) {
            sec.setAqCreateTime(new Date());
            sec.setAqId(GenerateUUID.getUUID());
            sec.setAqState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(sec);
            iSecAnswerQuestionDtlService.createOrUpdateSecAnswerQuestionDtl(request.getAnswerQuestionDtlModels(),sec.getAqId());
            sysFileService.saveFile(sec.getAqId(), request.gettId(), sec.getAqState() + "", sec.getAqCreateUid(), sec.getAqCreateUname()
                    , sec.getAqCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getAqOtherDoc(), request.getAqClarification(), request.getAqClarificationNotice(),request.getAqFile(),request.getAqAnswerFile()), sec.getAqId());

        } else {
            SecAnswerQuestion entity = baseMapper.selectById(request.getAqId());
            if (entity == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(entity.getAqState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            sec.setAqCreateTime(entity.getAqCreateTime());
            sec.setAqCreateUid(entity.getAqCreateUid());
            sec.setAqCreateUname(entity.getAqCreateUname());
            sec.setSecId(entity.getSecId());
            baseMapper.updateById(sec);
            iSecAnswerQuestionDtlService.createOrUpdateSecAnswerQuestionDtl(request.getAnswerQuestionDtlModels(),sec.getAqId());
            sysFileService.saveFile(sec.getAqId(), request.gettId(), sec.getAqState() + "", sec.getAqCreateUid(), sec.getAqCreateUname()
                    , sec.getAqCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getAqOtherDoc(), request.getAqClarification(), request.getAqClarificationNotice(),request.getAqFile(),request.getAqAnswerFile()),
                    Arrays.asList(request.getFileIds(), request.getAqOtherDocIds(), request.getAqClarificationIds(), request.getAqClarificationNoticeIds(),request.getAqFileIds(),request.getAqAnswerFileIds()), sec.getAqId());

        }
        secAnswerQuestionDtlService.createOrUpdateSecAnswerQuestionDtl(request.getAnswerQuestionDtlModels(), sec.getAqId());
        return BaseResponse.ok(sec.getAqId());
    }

    @Override
    public BaseResponse deleteSecAnswerQuestionByaqId(String aqId) {
        SecAnswerQuestion entity = baseMapper.selectById(aqId);
        if (entity == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        secAnswerQuestionDtlService.delSecAnswerQuestionDtlByAqId(aqId);
        baseMapper.deleteById(aqId);
        sysFileService.deleteFileById(aqId);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getSecAnswerQuestionListClassInfo(SecAnswerQuestionRequest request, LogUserInfoVo user) {
        Map<String, Object> params = getFilterMap(request, user.getId());
        List<ClassInfoAnnotation> classInfoAnnotationList = new ArrayList<ClassInfoAnnotation>();
        try {
            classInfoAnnotationList = ClassInfoUtils.getClassInfo(SecAnswerQuestionRequest.class.getName());
            String selectClause = ClassInfoUtils.getSelectClauseWithClassInfo(classInfoAnnotationList);

            // 创建人、流程处理人、抄送人、协同人
            String authSql = getSecAnswerQuestionListClassAuthSql(request, user);
            if (ValidateUtil.isEmpty(authSql)) {
                return BaseResponse.fail(ResMsg.noPermission);
            }

            List<Map<String, Object>> result = baseMapper.getAllValues(authSql, selectClause,params);
            classInfoAnnotationList = ClassInfoUtils.resultsHandle(result, classInfoAnnotationList);
        } catch (Exception e) {
            log.error("getSecWorkSchemaListClassInfo error:{}", e);
            return BaseResponse.ok(classInfoAnnotationList);
        }
        return BaseResponse.ok(classInfoAnnotationList);
    }

    @Override
    public BaseResponse getSecAnswerQuestionListClass(SecAnswerQuestionRequest request, LogUserInfoVo user) {
        Map<String, Object> params = getFilterMap(request, user.getId());

        // 创建人、流程处理人、抄送人、协同人
        String authSql = getSecAnswerQuestionListClassAuthSql(request, user);
        if (ValidateUtil.isEmpty(authSql)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }

        List<SecAnswerQuestionResponse> data = baseMapper.getSecAnswerQuestionListClass(authSql, params);

        Integer total = baseMapper.getSecAnswerQuestionListClassCount(authSql, params);
        if (CollectionUtils.isEmpty(data)) {
            return BaseResponse.ok(Collections.emptyList(), 0);
        }
        Set<String> pIdSet = data.stream()
                .filter(d -> ValidateUtil.isNotEmpty(d.getpId()) && "007".equals(d.getpBuildModel()))
                .map(SecAnswerQuestionResponse::getpId)
                .collect(Collectors.toSet());

        Map<String, List<PpOwnerUnitDTO>> ppOwnerUnitMap = Collections.emptyMap();
        if (!pIdSet.isEmpty()) {
            List<PpOwnerUnitDTO> ppOwnerUnits = ippOwnerUnitService.listPpOwnerUnitByPIds(new ArrayList<>(pIdSet));
            ppOwnerUnitMap = ppOwnerUnits.stream()
                    .collect(Collectors.groupingBy(PpOwnerUnitDTO::getPId));
        }

        for (SecAnswerQuestionResponse d : data) {
            if (ValidateUtil.isNotEmpty(d.getpId()) && "007".equals(d.getpBuildModel())) {
                List<PpOwnerUnitDTO> units = ppOwnerUnitMap.getOrDefault(d.getpId(), Collections.emptyList());
                StringBuilder sb = new StringBuilder();
                if (!CollectionUtils.isEmpty(units)) {
                    for (PpOwnerUnitDTO unit : units) {
                        sb.append(unit.getOwnerUnitName()).append(",");
                    }
                    if (sb.length() > 0) {
                        sb.deleteCharAt(sb.length() - 1);
                    }
                }
                if (sb.length() > 0 && ValidateUtil.isNotEmpty(d.getpCompetentDepartmentName())) {
                    sb.append("、").append(d.getpCompetentDepartmentName());
                } else if (sb.length() == 0) {
                    sb.append(d.getpCompetentDepartmentName());
                }
                d.setpBusinessUnit(sb.toString());
            } else {
                d.setpBusinessUnit(d.getpCompetentDepartmentName());
            }
        }
        return BaseResponse.ok(data,total);
    }

    private String getSecAnswerQuestionListClassAuthSql(SecAnswerQuestionRequest request, LogUserInfoVo user) {
        String njcj = sysConfigService.getConfigByCode("njcjCompanyId");
        if (ValidateUtil.isEmpty(njcj)) {
            njcj = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
        }
        String authSql = sysUserService.getAuthSql("Sec_Answer_Question", user.getId(), "Aq_Create_Uid", OutUserAuthEnum.Approver_Creator.getIndex(), "Aq_State", "Aq_Id");
        if (ValidateUtil.isEmpty(authSql)) {
            return null;
        }
        boolean groupUser = ValidateUtil.isNotEmpty(njcj) && Objects.equals(njcj, user.getCId());
        boolean myParticipation = ValidateUtil.isNotEmpty(request.getParticipationOrFinished()) && request.getParticipationOrFinished().contains("01");
        boolean finished = ValidateUtil.isNotEmpty(request.getParticipationOrFinished()) && request.getParticipationOrFinished().contains("02");
        String finishedSql = "exists (select 1 from Sec_Win_Notice swn where swn.Sec_Id = mn.Sec_Id and swn.Wn_State = 7)";
        String sameCompanyFinishedSql = getSameCompanyFinishedSql(user.getCId(), finishedSql);
        if (finished) {
            if (groupUser) {
                authSql = " where " + finishedSql + " ";
            } else {
                authSql = appendAndAuthSql(appendOrAuthSql(authSql, sameCompanyFinishedSql), finishedSql);
            }
        } else if (groupUser && !myParticipation) {
            authSql = appendOrAuthSql(authSql, finishedSql);
        } else if (!myParticipation) {
            authSql = appendOrAuthSql(authSql, sameCompanyFinishedSql);
        }
        return authSql.replaceFirst("(?i)\\bwhere\\b", " left join Sec_Bidding_Project sbpr on sbpr.Bp_Id=sbp.Bp_Id left join Pre_Project fpro on fpro.P_Id=sbpr.P_ID where ");
    }

    private String appendOrAuthSql(String authSql, String orSql) {
        if (ValidateUtil.isEmpty(orSql)) {
            return authSql;
        }
        int whereIndex = authSql.toLowerCase().indexOf("where");
        if (whereIndex < 0) {
            return authSql;
        }
        return authSql.substring(0, whereIndex) + "where ((" + authSql.substring(whereIndex + "where".length()) + ") or (" + orSql + ")) ";
    }

    private String appendAndAuthSql(String authSql, String andSql) {
        if (ValidateUtil.isEmpty(andSql)) {
            return authSql;
        }
        return authSql + " and (" + andSql + ") ";
    }

    private String getSameCompanyFinishedSql(String cId, String finishedSql) {
        if (ValidateUtil.isEmpty(cId)) {
            return null;
        }
        return finishedSql + " and exists (select 1 from Sys_User su where su.U_Id = mn.Aq_Create_Uid and su.C_Id = '" + cId.replace("'", "''") + "')";
    }

    @Override
    public BaseResponse getSecAnswerQuestionList(SecAnswerQuestionRequest request, String uid) {
        Map<String, Object> params = getFilterMap(request, uid);
        List<SecAnswerQuestionResponse> datas = baseMapper.getSecAnswerQuestionList(params);
        Integer total = baseMapper.getSecAnswerQuestionListCount(params);
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getSecAnswerQuestionByaqId(String aqId) {
        SecAnswerQuestionResponse data = baseMapper.getSecAnswerQuestionByaqId(aqId);
/*        // 询价
        if ("05".equals(data.getSecBiddingType())){
            LambdaQueryWrapper<SecInquiryRecord> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SecInquiryRecord::getSecId, data.getSecId());
            queryWrapper.eq(SecInquiryRecord::getState, ApprovalStatusEnum.Already_Over.getIndex());
            queryWrapper.orderByDesc(SecInquiryRecord::getCreateTime);
            queryWrapper.last("LIMIT 1");
            SecInquiryRecord secInquiryRecord = secInquiryRecordService.getOne(queryWrapper);
            data.setAqPlanOpenLocation(secInquiryRecord.getSirPlanOpenPlace());
            data.setAqPlanOpenTime(secInquiryRecord.getSirPlanOpenTime());
        } else if ("07".equals(data.getSecBiddingType())) {
            LambdaQueryWrapper<SecBiddingNegotiation> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SecBiddingNegotiation::getSecId, data.getSecId());
            queryWrapper.eq(SecBiddingNegotiation::getPId, data.getpId());
            queryWrapper.eq(SecBiddingNegotiation::getState, ApprovalStatusEnum.Already_Over.getIndex());
            queryWrapper.orderByDesc(SecBiddingNegotiation::getCreateTime);
            queryWrapper.last("LIMIT 1");
            SecBiddingNegotiation secBiddingNegotiation = secBiddingNegotiationService.getOne(queryWrapper);
            data.setAqPlanOpenLocation(secBiddingNegotiation.getBnPlanOpenLocation());
            data.setAqPlanOpenTime(secBiddingNegotiation.getBnPlanOpenTime());
        } else {
            LambdaQueryWrapper<SecDocument> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SecDocument::getSecId, data.getSecId());
            queryWrapper.eq(SecDocument::getPId, data.getpId());
            queryWrapper.eq(SecDocument::getDState, ApprovalStatusEnum.Already_Over.getIndex());
            queryWrapper.orderByDesc(SecDocument::getDCreateTime);
            queryWrapper.last("LIMIT 1");
            SecDocument secDocument = secDocumentService.getOne(queryWrapper);
            data.setAqPlanOpenLocation(secDocument.getDLocation());
            data.setAqPlanOpenTime(secDocument.getDBidTime());
        }*/
        if (data != null) {
            List<SecAnswerQuestionDtlModel> models = secAnswerQuestionDtlService.getSecAnswerQuestionDtlByAqId(aqId);
            data.setAnswerQuestionDtlModels(models);
        }
        return BaseResponse.ok(data, 0);
    }

    @Override
    public BaseResponse approveSyncDocumentBidTimeAndLocation(String aqId) {
        SecAnswerQuestion entity = baseMapper.selectById(aqId);
        if (entity == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        if (entity.getAqState() != ApprovalStatusEnum.Already_Over.getIndex())
            return BaseResponse.fail(ResMsg.stateCannotAct);
        String secId = entity.getSecId();
        Date bidTime = entity.getAqIsChangeTime() != null && entity.getAqIsChangeTime() == true ? entity.getAqBidTime() : null;
        String location = entity.getAqIsChangeLocation() != null && entity.getAqIsChangeLocation() == true ? entity.getAqLocation() : null;
        if (bidTime != null || !ValidateUtil.isEmpty(location))
            baseMapper.approveSyncDocumentBidTimeAndLocation(secId, bidTime, location);
        return BaseResponse.ok();
    }

    @Override
    public Boolean havingApprovedAnswerQuestion(String secId) {
        QueryWrapper<SecAnswerQuestion> wrapper = new QueryWrapper<>();
        wrapper.eq("Aq_Is_Change_Time", 1);
        wrapper.eq("Sec_Id", secId);
        wrapper.eq("Aq_State", ApprovalStatusEnum.Already_Over.getIndex());
        if (baseMapper.selectCount(wrapper) > 0)
            return true;
        return false;
    }

    @Override
    public BaseResponse getSecAnswerQuestionListOne2Many(SecAnswerQuestionRequest request, String uid) {
        Map<String, Object> params = getFilterMap(request, uid);
        List<SecAnswerQuestionResponse> datas = baseMapper.getSecAnswerQuestionListOne2Many(params);
        Integer total = baseMapper.getSecAnswerQuestionListOne2ManyCount(params);
        return BaseResponse.ok(datas, total);
    }

    private Map<String, Object> getFilterMap(SecAnswerQuestionRequest request, String uId) {
        QueryMap<SecAnswerQuestionRequest> queryMap = new QueryMap<>(request);
        queryMap.put("authSql", sysUserService.onlyShowCancel(uId, "aq.Aq_Create_Uid", "Aq_State"))
                .put("aqDelayStopBids").put("aqTitle").put("aqDocType").put("aqItemType").put("isNew")
                .put("secName").put("aqContent").put("aqCreateUname").put("pName")
                .put("aqIsThere").put("aqLocation").put("secType").put("secBiddingType")
                .put(request.getAqBidTimes(), "aqBidTimeStart", "aqBidTimeEnd")
                .put(request.getAqCreateTimes(), "aqCreateTimeStart", "aqCreateTimeEnd")
                .put("pNameList").put("secNameList").put("pCategoryList").put("aqCreateUnameList")
                .put("aqCreateTimeStr").put("aqStateList").put("pId").put("secId").put("participationOrFinished");
        return queryMap.getQuery();
    }
}
