package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.model.request.SecInfo.SecWinNoticeRequest;
import com.jpmsoft.common.model.response.SecInfo.SecWinNoticeResponse;
import com.jpmsoft.common.utils.ClassInfoUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecWinNoticeDtlMapper;
import com.jpmsoft.projectProducer.mapper.secInfo.SecWinNoticeMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.SysCompany;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecDocument;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecPublicityResults;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecWinNotice;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.other.*;
import com.jpmsoft.projectProducer.service.secInfo.*;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SecWinNoticeImpl extends ServiceImpl<SecWinNoticeMapper, SecWinNotice> implements ISecWinNoticeService {
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISecWinUnitService secWinUnitService;
    @Resource
    private ISecWinUnitMemberService secWinUnitMemberService;
    @Resource
    private ISecDocumentService secDocumentService;
    @Resource
    private ISecPublicityResultsService secPublicityResultsService;

    @Autowired
    private ISysCompanyService iSysCompanyService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private SecWinNoticeDtlMapper secWinNoticeDtlMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateWinNotice(SecWinNoticeRequest request) {
        SecWinNotice sec = new SecWinNotice();
        BeanUtils.copyProperties(request, sec);
        if (ValidateUtil.isEmpty(request.getSecId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        if (ValidateUtil.isEmpty(request.getpId()) && ValidateUtil.isNotEmpty(request.getSecProjects()) && request.getSecProjects().size() == 1) {
            request.setpId(request.getSecProjects().get(0).getpId());
        }
        if (ValidateUtil.isEmpty(request.getWnId())) {
            sec.setWnCreateTime(new Date());
            sec.setWnId(GenerateUUID.getUUID());
            sec.setWnState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(sec);
            secWinUnitService.createOrUpdateSecWinUnit(request.getWinUnits(), sec.getWnId());
            secWinUnitMemberService.crateOrUpdateWinUnitMember(request.getWinUnitMembers(), sec.getWnId());
            sysFileService.saveFile(sec.getWnId(), request.gettId(), sec.getWnState() + "", sec.getWnCreateUid(), sec.getWnCreateUname()
                    , sec.getWnCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getWnWinFile(), request.getWnAwardFile(),
                    request.getWnBidFile(), request.getWnCalibrateFile(),request.getWnPublicityFile()), sec.getWnId());

            return BaseResponse.ok(sec.getWnId());
        } else {
            SecWinNotice entity = baseMapper.selectById(request.getWnId());
            if (entity == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(entity.getWnState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            sec.setWnCreateTime(entity.getWnCreateTime());
            sec.setWnCreateUid(entity.getWnCreateUid());
            sec.setWnCreateUname(entity.getWnCreateUname());
            sec.setWnState(entity.getWnState());
            sec.setWnStateTime(entity.getWnStateTime());
            sec.setSecId(entity.getSecId());
            baseMapper.updateById(sec);
            secWinUnitService.createOrUpdateSecWinUnit(request.getWinUnits(), sec.getWnId());
            secWinUnitMemberService.crateOrUpdateWinUnitMember(request.getWinUnitMembers(), sec.getWnId());
            sysFileService.saveFile(sec.getWnId(), request.gettId(), sec.getWnState() + "", sec.getWnCreateUid(), sec.getWnCreateUname()
                    , sec.getWnCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getWnWinFile(), request.getWnAwardFile(), request.getWnBidFile(), request.getWnCalibrateFile(),request.getWnPublicityFile())
                    , Arrays.asList(request.getFileIds(), request.getWnWinFileIds(), request.getWnAwardFileIds(), request.getWnBidFileIds(), request.getWnCalibrateFileIds(),request.getWnPublicityFileIds()), sec.getWnId());

            return BaseResponse.ok(sec.getWnId());
        }
    }

    @Override
    public BaseResponse getWinNoticeList(SecWinNoticeRequest request) {
        Map<String, Object> params = getFilterMap(request);
        List<SecWinNoticeResponse> datas = baseMapper.getWinNoticeList(params);
        Integer total = baseMapper.getWinNoticeListCount(params);
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getWinNoticeByWnId(String wnId) {
        SecWinNoticeResponse data = baseMapper.getWinNoticeByWnId(wnId);
        String wnWinUnit = data.getWnWinUnit();
        if(ValidateUtil.isNotEmpty(wnWinUnit)) {
            List<String> wnWinUnitIds = Arrays.asList(wnWinUnit.split("、"));
            String wnWinUnitName = "";
            if (wnWinUnitIds.size() > 1) {
                List<SysCompany> coms = iSysCompanyService.getCompanyByIds(wnWinUnitIds);
                wnWinUnitName = coms.stream().map(SysCompany::getcName).collect(Collectors.joining("、"));
                data.setWnWinUnitName(wnWinUnitName);
            }
        }

        if (data == null)
            return BaseResponse.ok();
        data.setSecWinUnits(secWinUnitService.getWinUnitListByWnId(wnId));
        data.setSecWinUnitMembers(secWinUnitMemberService.getWinUnitMemberByWnId(wnId));
        SecDocument document = secDocumentService.getSecDocumentBySecIdAndState(data.getSecId(), ApprovalStatusEnum.Already_Over.getIndex());
        if (document != null) {
            data.setdScope(document.getDScope());
            data.setdAmount(document.getDAmount());
            data.setdDeterminType(document.getDDeterminType());
        }
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getLatestApproveWinNotice(String secId) {
        SecWinNoticeResponse data = baseMapper.getLatestApproveWinNotice(secId);
        if (data == null)
            return BaseResponse.ok();
        data.setSecWinUnits(secWinUnitService.getWinUnitListByWnId(data.getWnId()));
        data.setSecWinUnitMembers(secWinUnitMemberService.getWinUnitMemberByWnId(data.getWnId()));
        SecDocument document = secDocumentService.getSecDocumentBySecIdAndState(data.getSecId(), ApprovalStatusEnum.Already_Over.getIndex());
        if (document != null) {
            data.setdScope(document.getDScope());
            data.setdAmount(document.getDAmount());
            data.setdDeterminType(document.getDDeterminType());
        }
        data.setSecWinNoticeDtl(secWinNoticeDtlMapper.getSecWinNoticeDtlByWnId(data.getWnId()));
        return BaseResponse.ok(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteWinNoticeByWnId(String wnId) {
        SecWinNotice entity = baseMapper.selectById(wnId);
        if (entity == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        secWinUnitService.deleteSecWinUnitByWnId(wnId);
        secWinUnitMemberService.deleteWinUnitMemberByWnId(wnId);
        baseMapper.deleteById(wnId);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getWinNoticeListOne2Many(SecWinNoticeRequest request) {
        Map<String, Object> params = getFilterMap(request);
        List<SecWinNoticeResponse> datas = baseMapper.getWinNoticeListOne2Many(params);
        Integer total = baseMapper.getWinNoticeListOne2ManyCount(params);
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getWinNoticeCompleteBySecId(String secId) {
        if (ValidateUtil.isEmpty(secId)){
            return BaseResponse.fail("标段id为空");
        }
        LambdaQueryWrapper<SecPublicityResults> secPublicityResultsLambdaQueryWrapper = new LambdaQueryWrapper<>();
        secPublicityResultsLambdaQueryWrapper.eq(SecPublicityResults::getSecId, secId);
        secPublicityResultsLambdaQueryWrapper.eq(SecPublicityResults::getPrState, 7);
        List<SecPublicityResults> secPublicityResults = secPublicityResultsService.list(secPublicityResultsLambdaQueryWrapper);
        BigDecimal prBidAmount = BigDecimal.ZERO;
        for (SecPublicityResults secPublicityResult : secPublicityResults) {
            if (secPublicityResult.getPrBidAmount() != null) {
                prBidAmount = prBidAmount.add(secPublicityResult.getPrBidAmount());
            }
        }

        BigDecimal dAmount = BigDecimal.ZERO;
        LambdaQueryWrapper<SecDocument> secDocumentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        secDocumentLambdaQueryWrapper.eq(SecDocument::getSecId, secId);
        secDocumentLambdaQueryWrapper.eq(SecDocument::getDState, 7);

        List<SecDocument> secDocuments = secDocumentService.list(secDocumentLambdaQueryWrapper);

        for (SecDocument secDocument : secDocuments) {
            if (secDocument.getDAmount() != null) {
                dAmount = dAmount.add(secDocument.getDAmount());
            }
        }

        Map<String, BigDecimal> map = new HashMap<>();
        map.put("prBidAmount", prBidAmount);
        map.put("dAmount", dAmount);
        return BaseResponse.ok(map);
    }

    private Map<String, Object> getFilterMap(SecWinNoticeRequest request) {
        QueryMap<SecWinNoticeRequest> params = new QueryMap<>(request);
        params.put("wnRemark").put("wnWinUnit").put("wnCreateUname").put("wnCType").put("secBiddingType")
                .put("wnManagePerson").put("cName").put("wnState").put("pName").put("pId").put("secPlace").put("secName")
                .put(request.getWnAmounts(), "wnAmountStart", "wnAmountEnd")
                .put("secId")
                .put(request.getWnLowers(), "wnLowerStart", "wnLowerEnd")
                .put(request.getWnCreateTimes(), "wnCreateTimeStart", "wnCreateTimeEnd").put("secWinUnits");
        return params.getQuery();
    }

    @Override
    public BaseResponse getWinNoticeListClass(SecWinNoticeRequest request, LogUserInfoVo user) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }

        // 创建人、流程处理人、抄送人、协同人
        String authSql = getWinNoticeListClassAuthSql(request, user);
        if (ValidateUtil.isEmpty(authSql)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }
        Page<SecWinNoticeResponse> page = new Page<>(request.getPage(), request.getSize());
        List<SecWinNoticeResponse> datas = baseMapper.getWinNoticeListClass(authSql, page, request);
        return BaseResponse.ok(datas, Math.toIntExact(page.getTotal()));
    }

    @Override
    public BaseResponse getWinNoticeListClassInfo(SecWinNoticeRequest request, LogUserInfoVo user) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        List<ClassInfoAnnotation> classInfoAnnotationList = new ArrayList<>();
        try {
            classInfoAnnotationList = ClassInfoUtils.getClassInfo(SecWinNoticeRequest.class.getName());
            String selectClause = ClassInfoUtils.getSelectClauseWithClassInfo(classInfoAnnotationList);

            // 创建人、流程处理人、抄送人、协同人
            String authSql = getWinNoticeListClassAuthSql(request, user);
            if (ValidateUtil.isEmpty(authSql)) {
                return BaseResponse.fail(ResMsg.noPermission);
            }
            List<Map<String, Object>> result = baseMapper.getAllValues(authSql, selectClause, request);
            classInfoAnnotationList = ClassInfoUtils.resultsHandle(result, classInfoAnnotationList);
        } catch (Exception e) {
            log.error("getWinNoticeListClassInfo error:{}", e);
            return BaseResponse.ok(classInfoAnnotationList);
        }
        return BaseResponse.ok(classInfoAnnotationList);
    }

    private String getWinNoticeListClassAuthSql(SecWinNoticeRequest request, LogUserInfoVo user) {
        String njcj = sysConfigService.getConfigByCode("njcjCompanyId");
        if (ValidateUtil.isEmpty(njcj)) {
            njcj = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
        }
        String authSql = sysUserService.getAuthSql("Sec_Win_Notice", user.getId(), "Wn_Create_Uid", OutUserAuthEnum.Approver_Creator.getIndex(), "Wn_State", "Wn_Id");
        if (ValidateUtil.isEmpty(authSql)) {
            return null;
        }
        boolean groupUser = ValidateUtil.isNotEmpty(njcj) && Objects.equals(njcj, user.getCId());
        boolean myParticipation = ValidateUtil.isNotEmpty(request.getParticipationOrFinished()) && request.getParticipationOrFinished().contains("01");
        boolean finished = ValidateUtil.isNotEmpty(request.getParticipationOrFinished()) && request.getParticipationOrFinished().contains("02");
        String finishedSql = "mn.Wn_State = 7";
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
        return finishedSql + " and exists (select 1 from Sys_User su where su.U_Id = mn.Wn_Create_Uid and su.C_Id = '" + cId.replace("'", "''") + "')";
    }

    @Override
    public BaseResponse getWinningBidInformation(String secId) {
        if (ValidateUtil.isEmpty(secId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        List<Map<String, Object>> result = baseMapper.getWinningBidInformationFromCalibrationReport(secId);
        if (ValidateUtil.isEmpty(result)) {
            result = baseMapper.getWinningBidInformationFromEvalReport(secId);
        }
        if (ValidateUtil.isEmpty(result)) {
            return BaseResponse.fail("未找到中标信息");
        }
        List<String> allCIds = result.stream()
                .map(map -> Objects.toString(map.get("cId"), ""))
                .filter(ValidateUtil::isNotEmpty)
                .flatMap(cIds -> Arrays.stream(cIds.split("；")))
                .map(String::trim)
                .filter(ValidateUtil::isNotEmpty)
                .distinct()
                .collect(Collectors.toList());
        Map<String, String> companyNameMap = ValidateUtil.isEmpty(allCIds) ? Collections.emptyMap() :
                iSysCompanyService.list(Wrappers.<SysCompany>lambdaQuery()
                                .in(SysCompany::getcId, allCIds)
                                .select(SysCompany::getcId, SysCompany::getcName)
                        ).stream()
                        .filter(company -> ValidateUtil.isNotEmpty(company.getcId()) && ValidateUtil.isNotEmpty(company.getcName()))
                        .collect(Collectors.toMap(SysCompany::getcId, SysCompany::getcName, (oldName, newName) -> oldName));
        for (Map<String, Object> map : result) {
            String cIds = Objects.toString(map.get("cId"), "");
            List<String> companyNames = Arrays.stream(cIds.split("；"))
                    .map(String::trim)
                    .map(companyNameMap::get)
                    .filter(ValidateUtil::isNotEmpty)
                    .collect(Collectors.toList());
            map.put("cName", String.join("；", companyNames));
        }
        return BaseResponse.ok(result);
    }
}
