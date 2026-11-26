package com.jpmsoft.projectProducer.service.secInfo.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.model.request.project.PpOwnerUnitDTO;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecSelectionRecordDtlModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecSelectionRecordModel;
import com.jpmsoft.common.utils.ClassInfoUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.common.utils.VoInfoUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecSelectionRecordMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertSelectDto;
import com.jpmsoft.projectProducer.pojo.entity.other.SysCompany;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.*;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecExpertExtractVo;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.other.*;
import com.jpmsoft.projectProducer.service.project.IPpOwnerUnitService;
import com.jpmsoft.projectProducer.service.secInfo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SecSelectionRecordImpl extends ServiceImpl<SecSelectionRecordMapper, SecSelectionRecord> implements ISecSelectionRecordService {

    @Resource
    private ISecSelectionRecordDtlService secSelectionRecordDtlService;
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISecExpertShieldService secExpertShieldService;
    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private ISecExpertSelectService secExpertSelectService;

    @Autowired
    private ISysConfigService sysConfigService;

    @Resource
    private IPpOwnerUnitService ippOwnerUnitService;

    @Resource
    private ISecInfoService secInfoService;

    @Resource
    private ISecDocumentService secDocumentService;

    @Resource
    private ISecAnswerQuestionService secAnswerQuestionService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISecInvitationNegotiationService secInvitationNegotiationService;
    @Resource
    private ISecBiddingNegotiationService secBiddingNegotiationService;
    @Resource
    private ISecInquiryRecordService secInquiryRecordService;
    @Resource
    private ISecInvitationSchemaService secInvitationSchemaService;
    @Resource
    private ISecInviteDetailService secInviteDetailService;
    @Resource
    private ISysCompanyService sysCompanyService;


    private SecSelectionRecord createOrUpdateEntity(SecSelectionRecordModel request, LogUserInfoVo user) {
        SecSelectionRecord entity = new SecSelectionRecord();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getSrId())) {
            entity.setSrId(GenerateUUID.getUUID());
            entity.setSrCreateTime(new Date());
            entity.setState(ValidateUtil.isNull(request.getState()) ? ApprovalStatusEnum.Not_Started.getIndex() : request.getState());
            entity.setStateTime(new Date());
            entity.setSrCreateUid(user.getId());
            entity.setSrCreateUname(user.getName());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getSrId(), request.gettId(), entity.getState() + "", entity.getSrCreateUid(), entity.getSrCreateUname()
                    , entity.getSrCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), request.getSecId(), request.gettCode(), request.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getExpertDemonstration()), entity.getSrId());
        } else {
            SecSelectionRecord pojo = baseMapper.selectById(request.getSrId());
            if (pojo == null)
                throw new JPMException(ResMsg.infoNotExist);
            entity.setSrCreateTime(pojo.getSrCreateTime());
            entity.setSrCreateUid(pojo.getSrCreateUid());
            entity.setSrCreateUname(pojo.getSrCreateUname());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getSrId(), request.gettId(), entity.getState() + "", entity.getSrCreateUid(), entity.getSrCreateUname()
                    , entity.getSrCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), request.getSecId(), request.gettCode(), request.getConId());
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getExpertDemonstration()), Arrays.asList(request.getFileIds(), request.getExpertDemonstrationIds()), entity.getSrId());
        }
        secSelectionRecordDtlService.createOrUpdateSecSelectionRecord(request.getSecSelectionRecordDtlModels(), entity.getSrId());
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSecSelectionRecord(SecSelectionRecordModel request, LogUserInfoVo user) {
        SecSelectionRecord entity = createOrUpdateEntity(request, user);
        return BaseResponse.ok(entity.getSrId());
    }
    @Override
    public BaseResponse getSecSelectionRecordClassInfo(SecSelectionRecordModel request, LogUserInfoVo user) {
        String authSql = getSecSelectionRecordListAuthSql(request, user);
        if (ValidateUtil.isEmpty(authSql)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }
        QueryMap<SecSelectionRecordModel> params = new QueryMap<>(request);
        params.put("excludeDiscard").put("srType").put("srContact").put("srPhone")
                .put(request.getSrAccessTimes(), "srAccessTimeStart", "srAccessTimeEnd")
                .put("pName").put("pId").put("secName").put("state")
                .put("currentUid", user.getId()).put("pNameList").put("secNameList").put("srModeList")
                .put("srCreateUnameList").put("stateList").put("pCategory").put("participationOrFinished")
                .put("authSql", authSql);
        List<ClassInfoAnnotation> classInfoAnnotationList = new ArrayList<>();
        try {
            classInfoAnnotationList = ClassInfoUtils.getClassInfo(SecSelectionRecordModel.class.getName());
            String selectClause = ClassInfoUtils.getSelectClauseWithClassInfo(classInfoAnnotationList);
            List<Map<String,Object>> result = baseMapper.getAllValues(selectClause, params.getQuery());
            classInfoAnnotationList = ClassInfoUtils.resultsHandle(result, classInfoAnnotationList);
        } catch (Exception e) {
            log.error("getSecSelectionRecordClassInfo error:{} ",e);
            return BaseResponse.ok(classInfoAnnotationList);
        }
        return BaseResponse.ok(classInfoAnnotationList);
    }

    @Override
    public BaseResponse getSecSelectionRecordInfoBySecId(SecSelectionRecordModel request) {
        if(ValidateUtil.isEmpty(request.getSecId())) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        SecInfo secInfo = secInfoService.getById(request.getSecId());
        LambdaQueryWrapper<SecDocument> secDocumentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        secDocumentLambdaQueryWrapper.eq(SecDocument::getSecId, request.getSecId());
        if (ValidateUtil.isNotEmpty(request.getpId())) {
            secDocumentLambdaQueryWrapper.eq(SecDocument::getPId, request.getpId());
        }
        secDocumentLambdaQueryWrapper.eq(SecDocument::getDState, ApprovalStatusEnum.Already_Over.getIndex());
        secDocumentLambdaQueryWrapper.orderByDesc(SecDocument::getDCreateTime);
        secDocumentLambdaQueryWrapper.last("LIMIT 1");
        SecDocument secDocument = secDocumentService.getOne(secDocumentLambdaQueryWrapper);

        LambdaQueryWrapper<SecAnswerQuestion> secAnswerQuestionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        secAnswerQuestionLambdaQueryWrapper.eq(SecAnswerQuestion::getSecId, request.getSecId());
        secAnswerQuestionLambdaQueryWrapper.eq(SecAnswerQuestion::getAqState, ApprovalStatusEnum.Already_Over.getIndex());
        if (ValidateUtil.isNotEmpty(request.getpId())) {
            secAnswerQuestionLambdaQueryWrapper.eq(SecAnswerQuestion::getPId, request.getpId());
        }
        secAnswerQuestionLambdaQueryWrapper.orderByDesc(SecAnswerQuestion::getAqCreateTime);
        secAnswerQuestionLambdaQueryWrapper.last(" limit 1 ");
        SecAnswerQuestion secAnswerQuestion = secAnswerQuestionService.getOne(secAnswerQuestionLambdaQueryWrapper);
        SecSelectionRecordModel model = new SecSelectionRecordModel();
        if (ValidateUtil.isNotNull(secDocument)) {
            model.setdQualiReview(secDocument.getDQualiReview());
            model.setdFunction(secDocument.getDFunction());
            model.setPlanOpenTime(secDocument.getDBidTime());
            model.setPlanOpenLocation(secDocument.getDLocation());
            if (ValidateUtil.isNotEmpty(secDocument.getDFunction())){
                model.setErMethod(secDocument.getDFunction());
                if ("99".equals(secDocument.getDFunction())) {
                    model.setErMethod(secDocument.getDFunctionText());
                }
            }
            model.setdCalibrationMethod(secDocument.getDCalibrationMethod());
            model.setErType(secDocument.getDType());
            if ("1".equals(secDocument.getDType())) {
                model.setErTenderSumLimit(secDocument.getDTenderSumLimit());
            } else if ("2".equals(secDocument.getDType())) {
                model.setErBiddingControlRate(secDocument.getDBiddingControlRate());
                model.setErConversionControlPrice(secDocument.getDConversionControlPrice());
            }
        }
        if (ValidateUtil.isNotNull(secAnswerQuestion)) {
            if (ValidateUtil.isNotNull(secAnswerQuestion.getAqPlanOpenTime()) || ValidateUtil.isNotNull(secAnswerQuestion.getAqPlanOpenLocation())){
                model.setPlanOpenTime(secAnswerQuestion.getAqPlanOpenTime());
                model.setPlanOpenLocation(secAnswerQuestion.getAqPlanOpenLocation());
            }
        }

        LambdaQueryWrapper<SecSelectionRecord> secSelectionRecordLambdaQueryWrapper = new LambdaQueryWrapper<>();
        secSelectionRecordLambdaQueryWrapper.eq(SecSelectionRecord::getSecId, request.getSecId());
        if (ValidateUtil.isNotNull(request.getpId())){
            secSelectionRecordLambdaQueryWrapper.eq(SecSelectionRecord::getPId, request.getpId());
        }
        secSelectionRecordLambdaQueryWrapper.eq(SecSelectionRecord::getState, ApprovalStatusEnum.Already_Over.getIndex());
        secSelectionRecordLambdaQueryWrapper.orderByDesc(SecSelectionRecord::getSrCreateTime);
        secSelectionRecordLambdaQueryWrapper.last("limit 1");
        SecSelectionRecord secSelectionRecord = getOne(secSelectionRecordLambdaQueryWrapper);
        if (ValidateUtil.isNotNull(secSelectionRecord) && ValidateUtil.isNotNull(secSelectionRecord.getSrBidTime())) {
            model.setErBidTime(secSelectionRecord.getSrBidTime());
        } else if (ValidateUtil.isNotNull(secDocument) && ValidateUtil.isNotNull(secDocument.getDBidTime())) {
            model.setErBidTime(secDocument.getDBidTime());
        }
        model.setSecId(request.getSecId());
        model.setSecName(secInfo.getSecName());
        return BaseResponse.ok(model);
    }

    @Override
    public BaseResponse getSecSelectionRecordList(SecSelectionRecordModel request, LogUserInfoVo user) {
        QueryMap<SecSelectionRecordModel> params = new QueryMap<>(request);
        String authSql = getSecSelectionRecordListAuthSql(request, user);
        if (ValidateUtil.isEmpty(authSql)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }
        params.put("excludeDiscard").put("srType").put("srContact").put("srPhone")
                .put(request.getSrAccessTimes(), "srAccessTimeStart", "srAccessTimeEnd")
                .put("pName").put("pId").put("secName").put("state")
                .put("currentUid", user.getId()).put("pNameList").put("secNameList").put("srModeList")
                .put("srCreateUnameList").put("stateList").put("pCategory").put("keyExpertSelection")
                .put("keyExpertSelectionList").put("participationOrFinished").put("authSql", authSql);
        IPage<SecSelectionRecordModel> page = new Page<>(request.getPage(), request.getSize());
        List<SecSelectionRecordModel> datas = baseMapper.getSecSelectionRecordList(page, params.getQuery());

        if (CollectionUtils.isEmpty(datas)) {
            return BaseResponse.ok(Collections.emptyList(), 0);
        }

        Set<String> pIdSet = datas.stream()
                .filter(d -> ValidateUtil.isNotEmpty(d.getpId()) && "007".equals(d.getpBuildModel()))
                .map(SecSelectionRecordModel::getpId)
                .collect(Collectors.toSet());

        Map<String, List<PpOwnerUnitDTO>> ppOwnerUnitMap = Collections.emptyMap();
        if (!pIdSet.isEmpty()) {
            List<PpOwnerUnitDTO> ppOwnerUnits = ippOwnerUnitService.listPpOwnerUnitByPIds(new ArrayList<>(pIdSet));
            ppOwnerUnitMap = ppOwnerUnits.stream()
                    .collect(Collectors.groupingBy(PpOwnerUnitDTO::getPId));
        }

        for (SecSelectionRecordModel data : datas) {
            if (ValidateUtil.isNotEmpty(data.getpId()) && "007".equals(data.getpBuildModel())) {
                List<PpOwnerUnitDTO> units = ppOwnerUnitMap.getOrDefault(data.getpId(), Collections.emptyList());
                StringBuilder sb = new StringBuilder();
                if (!CollectionUtils.isEmpty(units)) {
                    for (PpOwnerUnitDTO unit : units) {
                        sb.append(unit.getOwnerUnitName()).append(",");
                    }
                    if (sb.length() > 0) {
                        sb.deleteCharAt(sb.length() - 1);
                    }
                }
                sb.append(data.getpCompetentDepartmentName());
                data.setBusinessUnitName(sb.toString());
            } else {
                data.setBusinessUnitName(data.getpCompetentDepartmentName());
            }
            LocalDateTime now = LocalDateTime.now();

            if (data.getSrBidTime() != null) {
                LocalDateTime bidTime = data.getSrBidTime().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();

                LocalDateTime deadline = bidTime.toLocalDate().plusDays(1).atStartOfDay().plusHours(8);

                data.setShowHidden(now.isBefore(deadline) ? "02" : "01");
            } else {
                data.setShowHidden("02");
            }


        }

        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    private String getSecSelectionRecordListAuthSql(SecSelectionRecordModel request, LogUserInfoVo user) {
        String njcj = sysConfigService.getConfigByCode("njcjCompanyId");
        if (ValidateUtil.isEmpty(njcj)) {
            njcj = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
        }
        String authSql = sysUserService.getAuthSql("Sec_Selection_Record", user.getId(), "Sr_Create_Uid", OutUserAuthEnum.Approver_Creator.getIndex(), "State", "Sr_Id");
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
        return finishedSql + " and exists (select 1 from Sys_User su where su.U_Id = mn.Sr_Create_Uid and su.C_Id = '" + cId.replace("'", "''") + "')";
    }

    @Override
    public BaseResponse getSectionDocumentCount(SecSelectionRecordModel request) {
        String fileTemplateCode = sysConfigService.getConfigByCode("SecExpertRecordEnabledFT");
        String fileTemplateCodeNew = sysConfigService.getConfigByCode("SecExpertRecordEnabledFTNew");
        if (StringUtils.isBlank(fileTemplateCode) && StringUtils.isBlank(fileTemplateCodeNew)){
            return BaseResponse.ok(1);
        }
        long count = this.baseMapper.querySecDoumentCount(request.getSecId(), fileTemplateCode, fileTemplateCodeNew);
        long secInvitationNegotiationCount = secInvitationNegotiationService.count(Wrappers.<SecInvitationNegotiation>lambdaQuery()
                .eq(SecInvitationNegotiation::getSecId, request.getSecId())
                .eq(SecInvitationNegotiation::getState, ApprovalStatusEnum.Already_Over.getIndex())
        );
        long secBiddingNegotiationCount = secBiddingNegotiationService.count(Wrappers.<SecBiddingNegotiation>lambdaQuery()
                .eq(SecBiddingNegotiation::getSecId, request.getSecId())
                .eq(SecBiddingNegotiation::getState, ApprovalStatusEnum.Already_Over.getIndex())
        );
        return BaseResponse.ok(count + secInvitationNegotiationCount + secBiddingNegotiationCount);
    }

    @Override
    public BaseResponse getTenderingMethodByProjectAndSection(String pId, String secId) {
        if (ValidateUtil.isEmpty(pId) || ValidateUtil.isEmpty(secId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }

        List<SecInvitationSchema> invitationSchemas = secInvitationSchemaService.list(Wrappers.<SecInvitationSchema>lambdaQuery()
                .eq(SecInvitationSchema::getPId, pId)
                .eq(SecInvitationSchema::getSecId, secId)
                .eq(SecInvitationSchema::getState, ApprovalStatusEnum.Already_Over.getIndex()));
        List<SecBiddingNegotiation> negotiationRecords = secBiddingNegotiationService.list(Wrappers.<SecBiddingNegotiation>lambdaQuery()
                .eq(SecBiddingNegotiation::getPId, pId)
                .eq(SecBiddingNegotiation::getSecId, secId)
                .eq(SecBiddingNegotiation::getState, ApprovalStatusEnum.Already_Over.getIndex()));
        List<SecInquiryRecord> inquiryRecords = secInquiryRecordService.list(Wrappers.<SecInquiryRecord>lambdaQuery()
                .eq(SecInquiryRecord::getPId, pId)
                .eq(SecInquiryRecord::getSecId, secId)
                .eq(SecInquiryRecord::getState, ApprovalStatusEnum.Already_Over.getIndex()));

        int total = invitationSchemas.size() + negotiationRecords.size() + inquiryRecords.size();
        if (total == 0) {
            return BaseResponse.fail("未查询到已结束的招标记录！");
        }
        if (total > 1) {
            return BaseResponse.fail("查询到多条已结束的招标记录，无法确定评标方法！");
        }
        SecInfo secInfo = secInfoService.getById(secId);
        if (ValidateUtil.isNull(secInfo)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("erTenderSumLimit", secInfo.getSecMoney());
        if (invitationSchemas.size() == 1) {
            List<SecInvitationNegotiation> invitationRecords = secInvitationNegotiationService.list(Wrappers.<SecInvitationNegotiation>lambdaQuery()
                .eq(SecInvitationNegotiation::getPId, pId)
                .eq(SecInvitationNegotiation::getSecId, secId)
                .eq(SecInvitationNegotiation::getState, ApprovalStatusEnum.Already_Over.getIndex()));
            data.put("erMethod", invitationRecords.size() == 1 ? invitationRecords.get(0).getInFunctionText() : "");
            List<String> companyIds = baseMapper.getInvitationSchemaCompanyIds(invitationSchemas.get(0).getId());
            data.put("candidateUnits", getCandidateUnits(companyIds));
            return BaseResponse.ok(data);
        }
        if (negotiationRecords.size() == 1) {
            data.put("erMethod", negotiationRecords.get(0).getBnFunctionText());
            List<String> companyIds = secInviteDetailService.list(Wrappers.<SecInviteDetail>lambdaQuery()
                    .eq(SecInviteDetail::getSirId, negotiationRecords.get(0).getId())
                    .orderByAsc(SecInviteDetail::getSirSort)).stream()
                    .map(SecInviteDetail::getCId).filter(ValidateUtil::isNotEmpty).collect(Collectors.toList());
            data.put("candidateUnits", getCandidateUnits(companyIds));
            return BaseResponse.ok(data);
        }
        data.put("erMethod", "");
        List<String> companyIds = secInviteDetailService.list(Wrappers.<SecInviteDetail>lambdaQuery()
                .eq(SecInviteDetail::getSirId, inquiryRecords.get(0).getId())
                .orderByAsc(SecInviteDetail::getSirSort)).stream()
                .map(SecInviteDetail::getCId).filter(ValidateUtil::isNotEmpty).collect(Collectors.toList());
        data.put("candidateUnits", getCandidateUnits(companyIds));
        return BaseResponse.ok(data);
    }

    private List<Map<String, Object>> getCandidateUnits(List<String> companyIds) {
        if (CollectionUtils.isEmpty(companyIds)) {
            return Collections.emptyList();
        }
        List<SysCompany> companies = sysCompanyService.getCompanyByIds(companyIds);
        if (CollectionUtils.isEmpty(companies)) {
            return Collections.emptyList();
        }
        Map<String, SysCompany> companyMap = companies.stream()
                .collect(Collectors.toMap(SysCompany::getcId, company -> company, (left, right) -> left));
        List<Map<String, Object>> result = new ArrayList<>();
        for (String companyId : companyIds) {
            SysCompany company = companyMap.get(companyId);
            if (company != null) {
                Map<String, Object> unit = new HashMap<>();
                unit.put("cId", company.getcId());
                unit.put("cName", company.getcName());
                unit.put("cType", company.getcType());
                unit.put("cCreditcode", company.getcCreditcode());
                result.add(unit);
            }
        }
        return result;
    }

    @Override
    public BaseResponse getSecSelectionRecordById(String srId) {
        SecSelectionRecordModel data = baseMapper.getSecSelectionRecordById(srId);
        if (!CollectionUtils.isEmpty(data.getSecSelectionRecordDtlModels())) {
            List<SecSelectionRecordDtlModel> dtlModels=data.getSecSelectionRecordDtlModels();
            dtlModels.forEach(d->{
                d.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(d.getExIdNumber())));
            });
            data.setSecSelectionRecordDtlModels(dtlModels);
        }
        return BaseResponse.ok(data, 0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteSecSelectionRecordById(String srId) {
        SecSelectionRecord pojo = baseMapper.selectById(srId);
        if (pojo == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        secSelectionRecordDtlService.deleteAllBySrId(srId);
        secExpertShieldService.delSecExpertShieldBySrId(srId);
        baseMapper.deleteById(srId);
        sysFileService.deleteFileById(srId);
        return BaseResponse.ok();
    }



    /****************************************专家抽取***********************************************/

    @Override
    public BaseResponse createOrUpdateExpertExtract(SecSelectionRecordModel request, LogUserInfoVo user) {
        String secId = request.getSecId();
        String pId = request.getpId();
        long count = this.count(Wrappers.<SecSelectionRecord>lambdaQuery()
                .eq(SecSelectionRecord::getSecId, secId)
                .eq(SecSelectionRecord::getPId, pId)
                .ne(ValidateUtil.isNotEmpty(request.getSrId()), SecSelectionRecord::getSrId, request.getSrId())
                .ne(SecSelectionRecord::getState, ApprovalStatusEnum.Invalid.getIndex())
                .ne(SecSelectionRecord::getState, ApprovalStatusEnum.Already_Over.getIndex())
        );
        if (count > 0) {
            return BaseResponse.fail("该标段已存在流程中的专家抽取记录，不能重复抽取！");
        }
        SecSelectionRecord entity = createOrUpdateEntity(request, user);
        secExpertShieldService.createOrUpdateSecExpertShield(request.getSecExpertShields(), entity.getSrId());
        return BaseResponse.ok(entity.getSrId());
    }

    @Override
    public BaseResponse getSecExpertExtractById(String srId) {
        return BaseResponse.ok(idNumberVo(baseMapper.getSecExpertExtractById(srId)));
    }

    @Override
    public BaseResponse getSecExpertExtractByEsId(String esId) {
        return BaseResponse.ok(idNumberVo(baseMapper.getSecExpertExtractByEsId(esId)));
    }

    private SecExpertExtractVo idNumberVo(SecExpertExtractVo data) {
        if (data != null) {
            if (!CollectionUtils.isEmpty(data.getSecSelectionRecordDtlModels())) {
                List<SecSelectionRecordDtlModel> rds = data.getSecSelectionRecordDtlModels().stream().peek(e -> {
                    e.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(e.getExIdNumber())));
                }).collect(Collectors.toList());
                data.setSecSelectionRecordDtlModels(rds);
            }
        }
        return data;
    }

    @Override
    public BaseResponse getSecExpertExtractList(SecExpertSelectDto request, String uId) {
        QueryMap<SecExpertSelectDto> queryMap = new QueryMap<>(request);
        queryMap.put("pName").put("pId").put("secName").put("createUname").put("state");
        IPage<SecExpertExtractVo> page = new Page<>(request.getPage(), request.getSize());
        List<SecExpertExtractVo> datas = baseMapper.getSecExpertExtractList(page, queryMap.getQuery());
        return BaseResponse.ok(datas, Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse cancelExtract2cancelSelect(String srId) {
        SecSelectionRecord data = baseMapper.selectById(srId);
        if (data == null) return BaseResponse.fail(ResMsg.infoNotExist);
        if (Objects.equals(data.getState(), ApprovalStatusEnum.Invalid.getIndex())) {
            secExpertSelectService.updateState(data.getEsId(), ApprovalStatusEnum.Invalid.getIndex());
            sysFileService.onlyUpdateState(ApprovalStatusEnum.Invalid.getIndex(), data.getEsId());
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getSecSelectionRecordDtlModelById(String srId) {
         List<SecSelectionRecordDtlModel> data =baseMapper.getSecSelectionRecordDtlModelById(srId);
         if (data != null) {
            if (!CollectionUtils.isEmpty(data)) {
                data.stream().peek(e -> {
                    e.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(e.getExIdNumber())));
                }).collect(Collectors.toList());
            }
        }
        return BaseResponse.ok(data);
    }

    @Override
    public void updateStateById(Integer state, String srId) {
        if(StrUtil.isNotBlank(srId)){
            baseMapper.updateStateById(state,srId);
        }
    }

}
