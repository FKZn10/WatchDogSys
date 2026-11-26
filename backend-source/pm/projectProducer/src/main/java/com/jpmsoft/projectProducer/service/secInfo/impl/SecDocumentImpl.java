package com.jpmsoft.projectProducer.service.secInfo.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.model.request.SecInfo.SecDocumentRequest;
import com.jpmsoft.common.model.request.project.PpOwnerUnitDTO;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecModularsGroupByProjectModel;
import com.jpmsoft.common.model.response.Project.PreProjectResponse;
import com.jpmsoft.common.model.response.SecInfo.SecDocumentResponse;
import com.jpmsoft.common.model.response.SecInfo.SecInviteRosterVo;
import com.jpmsoft.common.utils.ClassInfoUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecDocumentMapper;
import com.jpmsoft.projectProducer.mapper.secInfo.SecSelectionRecordMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecDocmentPreCheckDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecBiddingNegotiation;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecDocument;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfo;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInquiryRecord;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.project.IPpOwnerUnitService;
import com.jpmsoft.projectProducer.service.project.IPreProjectService;
import com.jpmsoft.projectProducer.service.secInfo.*;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SecDocumentImpl extends ServiceImpl<SecDocumentMapper, SecDocument> implements ISecDocumentService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;

    @Resource
    private ISysConfigService sysConfigService;

    @Resource
    private ISecInfoService secInfoService;
    @Resource
    private ISecProjectService secProjectService;
    @Resource
    private ISecInviteRosterService secInviteRosterService;

    @Resource
    private SecSelectionRecordMapper secSelectionRecordMapper;

    @Resource
    private IPpOwnerUnitService ippOwnerUnitService;

    @Resource
    private IPreProjectService preProjectService;
    @Resource
    private ISecBiddingNegotiationService secBiddingNegotiationService;
    @Resource
    private ISecInquiryRecordService secInquiryRecordService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSecDocument(SecDocumentRequest request) {
        SecDocument sec = new SecDocument();
        BeanUtils.copyProperties(request, sec);
        if (ValidateUtil.isEmpty(request.getSecId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        if (ValidateUtil.isEmpty(request.getpId()) && ValidateUtil.isNotEmpty(request.getSecProjects()) && request.getSecProjects().size() == 1) {
            request.setpId(request.getSecProjects().get(0).getpId());
        }
        if (ValidateUtil.isEmpty(request.getdId())) {
            sec.setDCreateTime(new Date());
            sec.setDId(GenerateUUID.getUUID());
            sec.setDState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(sec);
            sysFileService.saveFile(sec.getDId(), request.gettId(), sec.getDState() + "", sec.getDCreateUid(), sec.getDCreateUname()
                    , sec.getDCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getdApplyFile(), request.getdListFile(), request.getdOtherFile(),request.getdBidFinalFile(), request.getControlPriceFile()), sec.getDId());
        } else {
            SecDocument entity = baseMapper.selectById(request.getdId());
            if (entity == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
//            if (JudgeUtil.nonModifyState(entity.getDState()))
//                return BaseResponse.fail(ResMsg.stateNotModify);
            sec.setDCreateTime(entity.getDCreateTime());
            sec.setDCreateUid(entity.getDCreateUid());
            sec.setDCreateUname(entity.getDCreateUname());
            sec.setSecId(entity.getSecId());
            sec.setDState(entity.getDState());
            sec.setDStateTime(entity.getDStateTime());
            baseMapper.updateById(sec);
            sysFileService.saveFile(sec.getDId(), request.gettId(), sec.getDState() + "", sec.getDCreateUid(), sec.getDCreateUname()
                    , sec.getDCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getdApplyFile(), request.getdListFile(), request.getdOtherFile(),request.getdBidFinalFile(), request.getControlPriceFile()),
                    Arrays.asList(request.getFileIds(), request.getdApplyFileIds(), request.getdListFileIds(), request.getdOtherFileIds(),request.getdBidFinalFileIds(), request.getControlPriceFileIds()), sec.getDId());
        }
        secInviteRosterService.createOrUpdateSecInviteRoster(request.getSecInviteRosters(), sec.getDId());
        return BaseResponse.ok(sec.getDId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteSecDocumentBydId(String dId) {
        SecDocument entity = baseMapper.selectById(dId);
        if (entity == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        secInviteRosterService.delSecInviteRosterBydId(dId);
        baseMapper.deleteById(dId);
        sysFileService.deleteFileById(dId);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getSecDocumentListClassInfo(SecDocumentRequest request, LogUserInfoVo user) {
        Map<String, Object> params = getFilterMap(request);
        List<ClassInfoAnnotation> classInfoAnnotationList = new ArrayList<ClassInfoAnnotation>();
        try {
            classInfoAnnotationList = ClassInfoUtils.getClassInfo(SecDocumentRequest.class.getName());
            String selectClause = ClassInfoUtils.getSelectClauseWithClassInfo(classInfoAnnotationList);

            // 创建人、流程处理人、抄送人、协同人；在中标通知书审批通过后集团用户可以查看所有中标通知书已结束的，同单位的可以查看同单位的中标通知书已结束的
            String authSql = getSecDocumentListClassAuthSql(request, user);
            if (ValidateUtil.isEmpty(authSql)) {
                return BaseResponse.fail(ResMsg.noPermission);
            }

            List<Map<String, Object>> result = baseMapper.getAllValues(authSql, selectClause, params);
            classInfoAnnotationList = ClassInfoUtils.resultsHandle(result, classInfoAnnotationList);
        } catch (Exception e) {
            log.error("getDesignResultsIssuedClassInfo error:{}", e);
            return BaseResponse.ok(classInfoAnnotationList);
        }
        return BaseResponse.ok(classInfoAnnotationList);
    }

    @Override
    public BaseResponse getSecDocumentListClass(SecDocumentRequest request, LogUserInfoVo user) {
        // 创建人、流程处理人、抄送人、协同人；在中标通知书审批通过后集团用户可以查看所有中标通知书已结束的，同单位的可以查看同单位的中标通知书已结束的
        String authSql = getSecDocumentListClassAuthSql(request, user);
        if (ValidateUtil.isEmpty(authSql)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }

        Map<String, Object> params = getFilterMap(request);
        List<SecDocumentResponse> datas = baseMapper.getSecDocumentListClass(authSql, params);
        if (CollectionUtils.isEmpty(datas)) {
            return BaseResponse.ok(Collections.emptyList(), 0);
        }
        Set<String> pIdSet = datas.stream()
                .filter(d -> ValidateUtil.isNotEmpty(d.getpId()) && "007".equals(d.getpBuildModel()))
                .map(SecDocumentResponse::getpId)
                .collect(Collectors.toSet());

        Map<String, List<PpOwnerUnitDTO>> ppOwnerUnitMap = Collections.emptyMap();
        if (!pIdSet.isEmpty()) {
            List<PpOwnerUnitDTO> ppOwnerUnits = ippOwnerUnitService.listPpOwnerUnitByPIds(new ArrayList<>(pIdSet));
            ppOwnerUnitMap = ppOwnerUnits.stream()
                    .collect(Collectors.groupingBy(PpOwnerUnitDTO::getPId));
        }

        for (SecDocumentResponse data : datas) {
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
                if (sb.length() > 0 && ValidateUtil.isNotEmpty(data.getpCompetentDepartmentName())) {
                    sb.append("、").append(data.getpCompetentDepartmentName());
                } else if (sb.length() == 0) {
                    sb.append(data.getpCompetentDepartmentName());
                }
                data.setpBusinessUnit(sb.toString());
            } else {
                data.setpBusinessUnit(data.getpCompetentDepartmentName());
            }
        }
        Integer total = baseMapper.getSecDocumentListClassCount(authSql, params);
        return BaseResponse.ok(datas, total);
    }

    /**
     * 获取鉴权SQL
     *
     * @param request 请求参数
     * @param user 用户信息
     * @return 鉴权SQL
     */
    private String getSecDocumentListClassAuthSql(SecDocumentRequest request, LogUserInfoVo user) {
        String njcj = sysConfigService.getConfigByCode("njcjCompanyId");
        if (ValidateUtil.isEmpty(njcj)) {
            njcj = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
        }
        String authSql = sysUserService.getAuthSql("Sec_Document", user.getId(), "D_Create_Uid", OutUserAuthEnum.Approver_Creator.getIndex(), "D_State", "D_Id");
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
        return finishedSql + " and exists (select 1 from Sys_User su where su.U_Id = mn.D_Create_Uid and su.C_Id = '" + cId.replace("'", "''") + "')";
    }

    @Override
    public BaseResponse getpBusinessUnit(List<String> pIds) {
        Set<String> pIdsSet = new HashSet<String>(pIds);
        if (pIdsSet.isEmpty()) {
            return BaseResponse.ok();
        }
        List<PreProjectResponse> preProjectList = preProjectService.getProjectListByPCompetentDepartmentName(pIdsSet);
        Map<String, List<PpOwnerUnitDTO>> ppOwnerUnitMap = Collections.emptyMap();

        // 创建用于返回的Map
        Map<String, String> result = new HashMap<>();

        for (PreProjectResponse data : preProjectList) {
            String pBusinessUnit = "";
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
                if (sb.length() > 0 && ValidateUtil.isNotEmpty(data.getpCompetentDepartmentName())) {
                    sb.append("、").append(data.getpCompetentDepartmentName());
                } else if (sb.length() == 0) {
                    sb.append(data.getpCompetentDepartmentName());
                }
                pBusinessUnit = sb.toString();
            } else {
                pBusinessUnit = data.getpCompetentDepartmentName();
            }

            // 将pId和拼接后的字段放入结果Map中
            if (ValidateUtil.isNotEmpty(data.getpId())) {
                result.put(data.getpId(), pBusinessUnit);
            }
        }

        return BaseResponse.ok(result);
    }


    @Override
    public BaseResponse getSecDocumentList(SecDocumentRequest request) {
        Map<String, Object> params = getFilterMap(request);
        List<SecDocumentResponse> datas = baseMapper.getSecDocumentList(params);
        Integer total = baseMapper.getSecDocumentListCount(params);
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getSecDocumentListNJCJ(SecDocumentRequest request) {
        Map<String, Object> params = getFilterMap(request);
        IPage<SecDocumentResponse> page = new Page<>(request.getPage(), request.getSize());
        List<SecDocumentResponse> datas = baseMapper.getSecDocumentListNJCJ(params,page);
        //招标文件多次办理，取最后一次
        List<SecDocumentResponse> resultList=new ArrayList<>();
        if(CollectionUtil.isNotEmpty(datas)){
            Map<String, List<SecDocumentResponse>> map = datas.stream().collect(Collectors.groupingBy(SecDocumentResponse::getSecId, Collectors.toList()));
            for (Map.Entry<String, List<SecDocumentResponse>> item : map.entrySet()) {
                List<SecDocumentResponse> value = item.getValue();
                List<SecDocumentResponse> collect = value.stream().filter(e -> e.getdCreateTime() != null).sorted(Comparator.comparing(SecDocumentResponse::getdCreateTime).reversed()).collect(Collectors.toList());
                SecDocumentResponse secDocumentResponse = collect.get(0);
                resultList.add(secDocumentResponse);
            }
        }
        /*List<SecSelectionRecord> secExpertBidTimeList = secSelectionRecordMapper.getSecExpertBidTimeList(params);
        Map<String, Date> secExpertBidTimeMap =new HashMap<>(16);
        if(CollectionUtil.isNotEmpty(secExpertBidTimeList)){
            secExpertBidTimeMap = secExpertBidTimeList.stream().collect(Collectors.toMap(SecSelectionRecord::getSecId, SecSelectionRecord::getSrBidTime, (v1, v2) -> v1));
        }
        for (SecDocumentResponse data : datas) {
            if(secExpertBidTimeMap.containsKey(data.getSecId())){
                data.setdBidTime(secExpertBidTimeMap.get(data.getSecId()));
            }
        }
        Integer total = baseMapper.getSecDocumentListCount(params);*/
        return BaseResponse.ok(resultList, resultList.size());

    }

    @Override
    public BaseResponse getSecDocumentBydId(String dId) {
        SecDocumentResponse data = baseMapper.getSecDocumentBydId(dId);
        List<SecInviteRosterVo> rosters = secInviteRosterService.getSecInviteRosterBydIds(Arrays.asList(dId));
        data.setSecInviteRosters(rosters);
        return BaseResponse.ok(data, 0);
    }

    @Override
    public BaseResponse getSecDocumentBySecId(String secId) {
        List<SecDocumentResponse> datas = baseMapper.getSecDocumentBySecId(secId);
        return BaseResponse.ok(datas);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse updateSecDocumentById(SecDocumentRequest request) {
        SecDocument entity = baseMapper.selectById(request.getdId());
        if (entity == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        entity.setDUrl(request.getdUrl());
        entity.setDBidTime(request.getdBidTime());
        entity.setDLocation(request.getdLocation());
        entity.setDBidPubDate(request.getdBidPubDate());
        entity.setDFunctionText(request.getdFunctionText());
        entity.setDIsTwoStage(request.getdIsTwoStage());
        baseMapper.updateById(entity);
        //刷新dOtherFile
        sysFileAnnexService.refreshRelationFile(request.getdOtherFile(), request.getdOtherFileIds(), entity.getDId());
        //secInviteRosterService.createOrUpdateSecInviteRoster(request.getSecInviteRosters(),entity.getDId());
        return BaseResponse.ok(entity.getDId());
    }

    @Override
    public SecDocument getSecDocumentBySecIdAndState(String secId, Integer state) {
        QueryWrapper<SecDocument> wrapper = new QueryWrapper<>();
        wrapper.eq("Sec_Id", secId);
        wrapper.eq("D_State", state);
        List<SecDocument> datas = baseMapper.selectList(wrapper);
        if (datas != null && datas.size() > 0)
            return datas.get(0);
        else
            return null;
    }

    @Override
    public BaseResponse getSecDocumentGroupByProject(SecDocumentRequest request, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "P_Create_User_ID", OutUserAuthEnum.Organization.getIndex());
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        Map<String, Object> params = new HashMap<>();
        params.put("authSql", authSql);
        params.put("start", !JudgeUtil.checkObject(request.getPageStart()) ? request.getPageStart() : 0);
        params.put("size", !JudgeUtil.checkObject(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);
        params.put("secFlowState", !JudgeUtil.checkObject(request.getSecFlowState()) ? request.getSecFlowState() : null);
        params.put("teId", ValidateUtil.isNotEmpty(request.getTeId()) ? request.getTeId() : null);
        List<SecModularsGroupByProjectModel> pros = baseMapper.getSecDocumentProjectList(params);
        Integer total = baseMapper.getSecDocumentProjectListCount(params);
        if (ValidateUtil.isNotEmpty(pros)) {
            //项目ID
            List<String> pIds = pros.stream().parallel().map(SecModularsGroupByProjectModel::getpId).collect(Collectors.toList());
            List<SecDocumentResponse> sbps = baseMapper.getSecDocumentListByPids(pIds, params);
            List<SecModularsGroupByProjectModel> datas = new ArrayList<>();
            pros.forEach(data -> {
                List<SecDocumentResponse> sbp = sbps.stream().parallel().filter(n -> n.getpId().equals(data.getpId())).collect(Collectors.toList());
                data.setSecModulars(sbp);
                datas.add(data);
            });
            pros = null;
            return BaseResponse.ok(datas, total);
        }
        return BaseResponse.ok(pros, total);
    }

    @Override
    public BaseResponse getSecDocumentListOne2Many(SecDocumentRequest request) {
        Map<String, Object> params = getFilterMap(request);
        List<SecDocumentResponse> datas = baseMapper.getSecDocumentListOne2Many(params);
        Integer total = baseMapper.getSecDocumentListOne2ManyCount(params);
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse can2CreateSecDocument(String secId) {
        String conf = sysConfigService.getConfigByCode("cjSecDocmentPreCheck");
        if (ValidateUtil.isEmpty(conf)) return BaseResponse.fail(String.format(ResMsg.notConf, "cjSecDocmentPreCheck"));
        List<String> secTypes = null;
        List<String> cpdCodes = null;
        try {
            SecDocmentPreCheckDto check = JacksonUtils.parseObject(conf, SecDocmentPreCheckDto.class);
            secTypes = check.getSecType();
            cpdCodes = check.getCpdCode();
        } catch (Exception ex) {
            return BaseResponse.fail(String.format(ResMsg.confFormatError, "cjSecDocmentPreCheck"));
        }
        SecInfo sec = secInfoService.getById(secId);
        if (ValidateUtil.isNull(sec)) return BaseResponse.fail(ResMsg.infoNotExist);
        if (ValidateUtil.isNotEmpty(secTypes)) {
            if (secTypes.contains(sec.getSecType())) {
                //需要验证的标段类型
                return BaseResponse.ok(secProjectService.checkSecProjectCp(secId, cpdCodes));
            }
        }
        return BaseResponse.ok(true);
    }

    @Override
    public Date getBidTime(String secId) {
        QueryWrapper<SecDocument> wrapper = new QueryWrapper<>();
        wrapper.eq("Sec_Id", secId);
        wrapper.eq("D_State", ApprovalStatusEnum.Already_Over.getIndex());
        wrapper.last(" limit 1");
        SecDocument data = baseMapper.selectOne(wrapper);
        if (data != null) return data.getDBidTime();
        return null;
    }

    private Map<String, Object> getFilterMap(SecDocumentRequest request) {
        QueryMap<SecDocumentRequest> params = new QueryMap<>(request);
        params.put("dDeterminType").put("dPriceMode").put("dState").put("secName").put("dScope").put("dReleaseVersion")
                .put("dCreateUname").put("dPriceMode").put("dBidMonYear").put("dBidderManager").put("dPerformanceGuaranteeType")
                .put("dQualiReview").put("pName").put("dIsEvaluateSeparation").put("dInvestorsQuali")
                .put("dBiddingMethod").put("dReceiveJointBidd").put("isNew")
                .put(request.getdAmounts(), "dAmountStart", "dAmountEnd")
                .put(request.getdUpperLimits(), "dUpperLimitStart", "dUpperLimitEnd")
                .put(request.getdCreateTimes(), "dCreateTimeStart", "dCreateTimeEnd")
                .put(request.getSecMoneys(), "secMoneyStart", "secMoneyEnd")
                .put("pNameList").put("secNameList").put("pTypeList").put("dCreateUnameList")
                .put("dCreateTimeStr").put("dStateList").put("pCategoryList").put("pId").put("secId")
                .put("participationOrFinished");
        return params.getQuery();
    }

    /**
     * 获取招标文件的费率，根据审批时间取最新
     *
     * @param secId
     * @return
     */
    @Override
    public BaseResponse getDocumentRate(String secId) {
        if (ValidateUtil.isEmpty(secId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        List<AbstractMap.SimpleEntry<Date, Map<String, Object>>> rateCandidates = new ArrayList<>();
        // 公开招标文件及控制价
        SecDocument document = getOne(Wrappers.<SecDocument>lambdaQuery()
                .eq(SecDocument::getSecId, secId)
                .eq(SecDocument::getDState, ApprovalStatusEnum.Already_Over.getIndex())
                .orderByDesc(SecDocument::getDCreateTime)
                .last(" limit 1 ")
        );
        if (ValidateUtil.isNotNull(document)) {
            addDocumentRate(rateCandidates, document.getDStateTime(), document.getDBiddingControlRate(), document.getDType(), document.getDTenderSumLimit());
        }
        // 竞争性谈判文件及控制价
        SecBiddingNegotiation secBiddingNegotiation = secBiddingNegotiationService.getOne(Wrappers.<SecBiddingNegotiation>lambdaQuery()
                .eq(SecBiddingNegotiation::getSecId, secId)
                .eq(SecBiddingNegotiation::getState, ApprovalStatusEnum.Already_Over.getIndex())
                .orderByDesc(SecBiddingNegotiation::getCreateTime)
                .last(" limit 1 ")
        );
        if (ValidateUtil.isNotNull(secBiddingNegotiation)) {
            addDocumentRate(rateCandidates, secBiddingNegotiation.getStateTime(), secBiddingNegotiation.getBnBiddingControlRate(), secBiddingNegotiation.getBnType(), secBiddingNegotiation.getBnTenderSumLimit());
        }
        // 询价文件及控制价
        SecInquiryRecord secInquiryRecord = secInquiryRecordService.getOne(Wrappers.<SecInquiryRecord>lambdaQuery()
                .eq(SecInquiryRecord::getSecId, secId)
                .eq(SecInquiryRecord::getState, ApprovalStatusEnum.Already_Over.getIndex())
                .orderByDesc(SecInquiryRecord::getCreateTime)
                .last(" limit 1 ")
        );
        if (ValidateUtil.isNotNull(secInquiryRecord)) {
            addDocumentRate(rateCandidates, secInquiryRecord.getStateTime(), secInquiryRecord.getSirBiddingControlRate(), secInquiryRecord.getSirType(), secInquiryRecord.getSirTenderSumLimit());
        }

        Optional<AbstractMap.SimpleEntry<Date, Map<String, Object>>> latestRate = rateCandidates.stream()
                .max(Map.Entry.comparingByKey());
        return latestRate.map(dateMapSimpleEntry -> BaseResponse.ok(dateMapSimpleEntry.getValue())).orElseGet(() -> BaseResponse.fail(ResMsg.infoNotExist));
    }

    private void addDocumentRate(List<AbstractMap.SimpleEntry<Date, Map<String, Object>>> rateCandidates, Date stateTime, BigDecimal rate, String type, BigDecimal tenderSumLimit) {
        if (ValidateUtil.isNotNull(stateTime)) {
            Map<String, Object> rateMap = new HashMap<>();
            rateMap.put("biddingControlRate", rate);
            rateMap.put("type", type);
            rateMap.put("tenderSumLimit", tenderSumLimit);
            rateCandidates.add(new AbstractMap.SimpleEntry<>(stateTime, rateMap));
        }
    }
}
