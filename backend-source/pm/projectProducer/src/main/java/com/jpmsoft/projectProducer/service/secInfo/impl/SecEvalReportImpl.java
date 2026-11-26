package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.model.request.SecInfo.SecEvalReportClassInfoRequest;
import com.jpmsoft.common.model.request.SecInfo.SecEvalReportRequest;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecEvalReportDtlModel;
import com.jpmsoft.common.model.response.SecInfo.SecEvalReportResponse;
import com.jpmsoft.common.utils.ClassInfoUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecEvalReportMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecDocument;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecEvalReport;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.secInfo.ISecDocumentService;
import com.jpmsoft.projectProducer.service.secInfo.ISecEvalReportService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SecEvalReportImpl extends ServiceImpl<SecEvalReportMapper, SecEvalReport> implements ISecEvalReportService {
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISecDocumentService secDocumentService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISysConfigService sysConfigService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateEvalReport(SecEvalReportRequest request) {
        SecEvalReport sec = new SecEvalReport();
        BeanUtils.copyProperties(request, sec);

        if (ValidateUtil.isEmpty(request.getSecId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        if (ValidateUtil.isEmpty(request.getErId())) {
            sec.setErCreateTime(new Date());
            sec.setErId(GenerateUUID.getUUID());
            sec.setErState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(sec);
            sysFileService.saveFile(sec.getErId(), request.gettId(), sec.getErState() + "", sec.getErCreateUid(), sec.getErCreateUname()
                    , sec.getErCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), sec.getErId());

            return BaseResponse.ok(sec.getErId());
        } else {
            SecEvalReport entity = baseMapper.selectById(request.getErId());
            if (entity == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(entity.getErState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            sec.setErCreateTime(entity.getErCreateTime());
            sec.setErCreateUid(entity.getErCreateUid());
            sec.setErCreateUname(entity.getErCreateUname());
            sec.setSecId(entity.getSecId());
            sec.setErState(entity.getErState());
            sec.setErStateTime(entity.getErStateTime());
            baseMapper.updateById(sec);
            sysFileService.saveFile(sec.getErId(), request.gettId(), sec.getErState() + "", sec.getErCreateUid(), sec.getErCreateUname()
                    , sec.getErCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), sec.getSecId(), request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), sec.getErId());

            return BaseResponse.ok(sec.getErId());
        }
    }

    @Override
    public BaseResponse cancelSecEvalReportByErId(String erId) {
        SecEvalReport entity = baseMapper.selectById(erId);
        if (entity == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        if (JudgeUtil.nonModifyState(entity.getErState()))
            return BaseResponse.fail(ResMsg.stateNotModify);
        entity.setErState(ApprovalStatusEnum.Invalid.getIndex());
        baseMapper.updateById(entity);
        return BaseResponse.ok(entity.getErId());
    }

    @Override
    public BaseResponse getSecEvalReportByErId(String erId) {
        SecEvalReportResponse data = baseMapper.getSecEvalReportByErId(erId);
        SecDocument document = secDocumentService.getSecDocumentBySecIdAndState(data.getSecId(), ApprovalStatusEnum.Already_Over.getIndex());
        if (document != null) {
            data.setdAmount(document.getDAmount());
            data.setdScope(document.getDScope());
        }
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getSecEvalReportBySecId(String secId) {

        if (ValidateUtil.isEmpty(secId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }

        LambdaQueryWrapper<SecDocument> secDocumentWrapper = new LambdaQueryWrapper<>();
        secDocumentWrapper.eq(SecDocument::getSecId, secId);
        secDocumentWrapper.eq(SecDocument::getDState, ApprovalStatusEnum.Already_Over.getIndex());
        secDocumentWrapper.orderByDesc(SecDocument::getDCreateTime);
        secDocumentWrapper.last("LIMIT 1");
        SecDocument secDocument = secDocumentService.getOne(secDocumentWrapper);

        if (secDocument == null || !"1".equals(secDocument.getDType())) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }

        LambdaQueryWrapper<SecEvalReport> secEvalReportWrapper = new LambdaQueryWrapper<>();
        secEvalReportWrapper.eq(SecEvalReport::getSecId, secId);
        secEvalReportWrapper.eq(SecEvalReport::getErState, ApprovalStatusEnum.Already_Over.getIndex());
        secEvalReportWrapper.orderByDesc(SecEvalReport::getErCreateTime);
        secEvalReportWrapper.last("LIMIT 1");
        SecEvalReport secEvalReport = getOne(secEvalReportWrapper);

        if (secEvalReport == null) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        SecEvalReportResponse data = new SecEvalReportResponse();
        BeanUtils.copyProperties(secEvalReport, data);
        List<SecEvalReportDtlModel> secEvalReportDtlModels = baseMapper.getSecEvalReportDtlModel(secEvalReport.getErId())
                .stream()
                .sorted(Comparator.comparing(s -> {
                    try {
                        return Integer.parseInt(s.getErdSort());
                    } catch (NumberFormatException e) {
                        return Integer.MAX_VALUE;
                    }
                }))
                .collect(Collectors.toList());
        data.setSecEvalReportDtls(secEvalReportDtlModels);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getSecEvalReportList(SecEvalReportClassInfoRequest request, LogUserInfoVo user) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }

        // 创建人、流程处理人、抄送人、协同人
        String authSql = getSecEvalReportAuthSql(request, user);
        if (ValidateUtil.isEmpty(authSql)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }
        Page<SecEvalReportClassInfoRequest> page = new Page<>(request.getPage(), request.getSize());
        List<SecEvalReportResponse> list = baseMapper.getSecEvalReportList(authSql, page, request);
        return BaseResponse.ok(list, Math.toIntExact(page.getTotal()));
    }

    @Override
    public BaseResponse getSecEvalReportListClassInfo(SecEvalReportClassInfoRequest request, LogUserInfoVo user) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        List<ClassInfoAnnotation> classInfoAnnotationList = new ArrayList<>();
        try {
            classInfoAnnotationList = ClassInfoUtils.getClassInfo(SecEvalReportClassInfoRequest.class.getName());
            String selectClause = ClassInfoUtils.getSelectClauseWithClassInfo(classInfoAnnotationList);

            // 创建人、流程处理人、抄送人、协同人
            String authSql = getSecEvalReportAuthSql(request, user);
            if (ValidateUtil.isEmpty(authSql)) {
                return BaseResponse.fail(ResMsg.noPermission);
            }
            List<Map<String, Object>> result = baseMapper.getAllValues(authSql, selectClause, request);
            classInfoAnnotationList = ClassInfoUtils.resultsHandle(result, classInfoAnnotationList);
        } catch (Exception e) {
            log.error("getSecEvalReportListClassInfo error:{}", e);
            return BaseResponse.ok(classInfoAnnotationList);
        }
        return BaseResponse.ok(classInfoAnnotationList);
    }

    private String getSecEvalReportAuthSql(SecEvalReportClassInfoRequest request, LogUserInfoVo user) {
        String njcj = sysConfigService.getConfigByCode("njcjCompanyId");
        if (ValidateUtil.isEmpty(njcj)) {
            njcj = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
        }
        String authSql = sysUserService.getAuthSql("Sec_Eval_Report", user.getId(), "Er_Create_Uid", OutUserAuthEnum.Approver_Creator.getIndex(), "Er_State", "Er_Id");
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
        return finishedSql + " and exists (select 1 from Sys_User su where su.U_Id = mn.Er_Create_Uid and su.C_Id = '" + cId.replace("'", "''") + "')";
    }

    @Override
    public BaseResponse getSecEvalReportNoAuthList(SecEvalReportClassInfoRequest request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        String authSql = " where 1=1 ";
        Page<SecEvalReportClassInfoRequest> page = new Page<>(request.getPage(), request.getSize());
        List<SecEvalReportResponse> list = baseMapper.getSecEvalReportList(authSql, page, request);
        return BaseResponse.ok(list, Math.toIntExact(page.getTotal()));
    }
}
