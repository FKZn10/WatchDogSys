package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.model.request.SecInfo.SecBiddingNegotiationDto;
import com.jpmsoft.common.model.request.project.PpOwnerUnitDTO;
import com.jpmsoft.common.utils.ClassInfoUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecBiddingNegotiationMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecBiddingNegotiation;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.project.IPpOwnerUnitService;
import com.jpmsoft.projectProducer.service.secInfo.ISecBiddingNegotiationService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SecBiddingNegotiationServiceImpl extends FileImpl<SecBiddingNegotiationMapper, SecBiddingNegotiation, SecBiddingNegotiationDto> implements ISecBiddingNegotiationService {

    @Resource
    private IPpOwnerUnitService ippOwnerUnitService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISysConfigService sysConfigService;

    @Override
    public BaseResponse createOrUpdateSecBiddingNegotiation(SecBiddingNegotiationDto request) {
        SecBiddingNegotiation entity = new SecBiddingNegotiation();
        createOrUpdateFileEntity(entity, request);
        return BaseResponse.ok((entity.getId()));
    }

    @Override
    public BaseResponse getSecBiddingNegotiationListClassInfo(SecBiddingNegotiationDto request, LogUserInfoVo user) {
        QueryMap<SecBiddingNegotiationDto> queryMap = new QueryMap<>(request);
        queryMap.put("pNameList").put("secNameList").put("pCategoryList").put("isNew")
                .put("createUnameList").put("createTimeStr").put("stateList").put("participationOrFinished");
        List<ClassInfoAnnotation> classInfoAnnotationList = new ArrayList<>();
        try {
            classInfoAnnotationList = ClassInfoUtils.getClassInfo(SecBiddingNegotiationDto.class.getName());
            String selectClause = ClassInfoUtils.getSelectClauseWithClassInfo(classInfoAnnotationList);

            // 创建人、流程处理人、抄送人、协同人；在中标通知书审批通过后集团用户可以查看所有中标通知书已结束的，同单位的可以查看同单位的中标通知书已结束的
            String authSql = getSecBiddingNegotiationListClassAuthSql(request, user);
            if (ValidateUtil.isEmpty(authSql)) {
                return BaseResponse.fail(ResMsg.noPermission);
            }

            List<Map<String, Object>> result = baseMapper.getAllValues(authSql, selectClause, queryMap.getQuery());
            classInfoAnnotationList = ClassInfoUtils.resultsHandle(result, classInfoAnnotationList);
        } catch (Exception e) {
            log.error("getSecBiddingNegotiationListClassInfo error:{}", e);
            return BaseResponse.ok(classInfoAnnotationList);
        }
        return BaseResponse.ok(classInfoAnnotationList);
    }

    @Override
    public BaseResponse getSecBiddingNegotiationListClass(SecBiddingNegotiationDto request, LogUserInfoVo user) {
        QueryMap<SecBiddingNegotiationDto> queryMap = new QueryMap<>(request);
        queryMap.put("pNameList").put("secNameList").put("pCategoryList").put("isNew")
                .put("createUnameList").put("createTimeStr").put("stateList").put("participationOrFinished");

        // 创建人、流程处理人、抄送人、协同人；在中标通知书审批通过后集团用户可以查看所有中标通知书已结束的，同单位的可以查看同单位的中标通知书已结束的
        String authSql = getSecBiddingNegotiationListClassAuthSql(request, user);
        if (ValidateUtil.isEmpty(authSql)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }

        IPage<SecBiddingNegotiationDto> page = new Page<>(request.getPage(), request.getSize());
        List<SecBiddingNegotiationDto> data = baseMapper.getSecBiddingNegotiationListClass(authSql, page, queryMap.getQuery());
        if (CollectionUtils.isEmpty(data)) {
            return BaseResponse.ok(Collections.emptyList(), 0);
        }
        Set<String> pIdSet = data.stream()
                .filter(d -> ValidateUtil.isNotEmpty(d.getpId()) && "007".equals(d.getPBuildModel()))
                .map(SecBiddingNegotiationDto::getpId)
                .collect(Collectors.toSet());

        Map<String, List<PpOwnerUnitDTO>> ppOwnerUnitMap = Collections.emptyMap();
        if (!pIdSet.isEmpty()) {
            List<PpOwnerUnitDTO> ppOwnerUnits = ippOwnerUnitService.listPpOwnerUnitByPIds(new ArrayList<>(pIdSet));
            ppOwnerUnitMap = ppOwnerUnits.stream()
                    .collect(Collectors.groupingBy(PpOwnerUnitDTO::getPId));
        }

        for (SecBiddingNegotiationDto d : data) {
            if (ValidateUtil.isNotEmpty(d.getpId()) && "007".equals(d.getPBuildModel())) {
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
                d.setPBusinessUnit(sb.toString());
            } else {
                d.setPBusinessUnit(d.getpCompetentDepartmentName());
            }
        }
        return BaseResponse.ok(data, Integer.parseInt(Long.valueOf(page.getTotal()).toString()));
    }

    private String getSecBiddingNegotiationListClassAuthSql(SecBiddingNegotiationDto request, LogUserInfoVo user) {
        String njcj = sysConfigService.getConfigByCode("njcjCompanyId");
        if (ValidateUtil.isEmpty(njcj)) {
            njcj = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
        }
        String authSql = sysUserService.getAuthSql("Sec_Bidding_Negotiation", user.getId(), "Create_Uid", OutUserAuthEnum.Approver_Creator.getIndex(), "State", "id");
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
        return finishedSql + " and exists (select 1 from Sys_User su where su.U_Id = mn.Create_Uid and su.C_Id = '" + cId.replace("'", "''") + "')";
    }

    @Override
    public BaseResponse getSecBiddingNegotiationByBnId(String bnId) {
        if (ValidateUtil.isEmpty(bnId)) {
            return BaseResponse.fail(ResMsg.notId);
        }
        SecBiddingNegotiationDto data = baseMapper.getSecBiddingNegotiationByBnId(bnId);
        if (data == null) {
            return BaseResponse.ok();
        }
        return BaseResponse.ok(data, 0);
    }

    @Override
    public BaseResponse deleteSecBiddingNegotiationByBnId(String bnId) {
        if (ValidateUtil.isEmpty(bnId)) {
            return BaseResponse.fail(ResMsg.notId);
        }
        int result = baseMapper.deleteById(bnId);
        return BaseResponse.ok();
    }
}

