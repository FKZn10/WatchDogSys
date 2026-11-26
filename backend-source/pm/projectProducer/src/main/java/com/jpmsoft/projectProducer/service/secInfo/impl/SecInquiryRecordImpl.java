package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.model.request.SecInfo.SecInviteDetailDTO;
import com.jpmsoft.common.model.request.project.PpOwnerUnitDTO;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.ClassInfoUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecInquiryRecordMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecInquiryRecordDto;
import com.jpmsoft.projectProducer.pojo.entity.other.SysCompany;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInquiryRecord;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecInquiryRecordVo;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecQuotationUnitVo;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.other.*;
import com.jpmsoft.projectProducer.service.project.IPpOwnerUnitService;
import com.jpmsoft.projectProducer.service.secInfo.ISecInquiryRecordService;
import com.jpmsoft.projectProducer.service.secInfo.ISecInviteDetailService;
import com.jpmsoft.projectProducer.service.secInfo.ISecQuotationUnitService;
import com.jpmsoft.projectProducer.utils.extension.BaseFileImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/22 15:49
 */
@Service
public class SecInquiryRecordImpl extends BaseFileImpl<SecInquiryRecordMapper, SecInquiryRecord, SecInquiryRecordDto>
        implements ISecInquiryRecordService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISecQuotationUnitService secQuotationUnitService;

    @Resource
    private IPpOwnerUnitService ippOwnerUnitService;

    @Resource
    private ISysFileService sysFileService;

    @Resource
    private ISecInviteDetailService secInviteDetailService;

    @Autowired
    private ISysCompanyService iSysCompanyService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISysConfigService sysConfigService;

    @Override
    public BaseResponse createOrUpdateSecInquiryRecord(SecInquiryRecordDto request) {
        SecInquiryRecord entity = new SecInquiryRecord();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getId())) {
            entity.setCreateTime(new Date());
            entity.setStateTime(new Date());
            entity.setId(GenerateUUID.getUUID());
            entity.setState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(entity);
            secInviteDetailService.createOrUpdateSecInviteDetailSir(request.getSecInviteDetails(), entity.getId());
            secQuotationUnitService.createOrUpdateSecQuotationUnit(request.getSecQuotationUnits(), entity.getId());
            sysFileService.saveFile(entity.getId(), request.getTId(), entity.getState() + "", entity.getCreateUid(), entity.getCreateUname()
                    , entity.getCreateTime(), request.getPId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), request.getSecId(), request.getTCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId()), entity.getId());
            return BaseResponse.ok(entity.getId());
        } else {
            SecInquiryRecord ir = baseMapper.selectById(request.getId());
            if (ir == null) {
                return BaseResponse.fail(ResMsg.infoNotExist);
            }
            entity.setCreateTime(ir.getCreateTime());
            entity.setCreateUname(ir.getCreateUname());
            entity.setCreateUid(ir.getCreateUid());
            baseMapper.updateById(entity);
            secInviteDetailService.createOrUpdateSecInviteDetailSir(request.getSecInviteDetails(), entity.getId());
            secQuotationUnitService.createOrUpdateSecQuotationUnit(request.getSecQuotationUnits(), entity.getId());
            sysFileService.saveFile(entity.getId(), request.getTId(), entity.getState() + "", entity.getCreateUid(), entity.getCreateUname(),
                    entity.getCreateTime(), request.getPId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), request.getSecId(), request.getTCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId())
                    , Arrays.asList(request.getFileIds()), entity.getId());
        }
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getSecInquiryRecordById(String id) {

        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notId);
        }
        SecInquiryRecordVo data = baseMapper.getSecInquiryRecordById(id);
        if (data == null) {
            return BaseResponse.ok();
        }
        List<SecInviteDetailDTO> details = secInviteDetailService.getSecInviteDetailBySirIds(Arrays.asList(id));
        data.setSecInviteDetails(details);

        String sirIntendedUnit = data.getSirIntendedUnit();
        if (ValidateUtil.isNotEmpty(sirIntendedUnit)) {
            List<String> sirIntendedUnitIds = Arrays.asList(sirIntendedUnit.split("、"));
            String sirIntendedUnitName = "";
            if (sirIntendedUnitIds.size() > 1) {
                List<SysCompany> coms = iSysCompanyService.getCompanyByIds(sirIntendedUnitIds);
                sirIntendedUnitName = coms.stream().map(SysCompany::getcName).collect(Collectors.joining("、"));
                data.setSirIntendedUnitName(sirIntendedUnitName);
            }
        }

        return BaseResponse.ok(data, 0);
    }

    @Override
    public BaseResponse getSecInquiryRecordBySecId(String secId) {
        return BaseResponse.ok(baseMapper.getSecInquiryRecordBySecId(secId));
    }

    @Override
    public BaseResponse getSecInquiryRecordListClassInfo(SecInquiryRecordDto request, LogUserInfoVo user) {
        QueryMap<SecInquiryRecordDto> params = new QueryMap<>(request);
        params.put("secName").put("secType").put("secOrganizeForm").put("secBiddingType").put("isNew")
                .put("secTradingPlace").put("secPlace").put("tendereeName").put("sirPrerequisites").put("pId")
                .put("sirIntendedUnitName").put("sirRemark").put("createUname").put("pCompetentDepartmentName")
                .put(request.getCreateTimes(), "createTimeStart", "createTimeEnd").put("state").put("pName")
                .put(request.getSirConMoneys(), "sirConMoneyStart", "sirConMoneyEnd")
                .put(request.getSecStartTimes(), "secStartTimeStart", "secStartTimeEnd")
                .put(request.getSecBiddingCycles(), "secBiddingCycleStart", "secBiddingCycleEnd")
                .put("pNameList").put("secNameList").put("pCategoryList").put("createUnameList")
                .put("createTimeStr").put("stateList").put("participationOrFinished");
        List<ClassInfoAnnotation> classInfoAnnotationList = new ArrayList<ClassInfoAnnotation>();
        try {
            classInfoAnnotationList = ClassInfoUtils.getClassInfo(SecInquiryRecordDto.class.getName());
            String selectClause = ClassInfoUtils.getSelectClauseWithClassInfo(classInfoAnnotationList);

            // 创建人、流程处理人、抄送人、协同人
            String authSql = getSecInquiryRecordListClassAuthSql(request, user);
            if (ValidateUtil.isEmpty(authSql)) {
                return BaseResponse.fail(ResMsg.noPermission);
            }

            List<Map<String, Object>> result = baseMapper.getAllValues(authSql, selectClause, params.getQuery());
            classInfoAnnotationList = ClassInfoUtils.resultsHandle(result, classInfoAnnotationList);
        } catch (Exception e) {
            log.error("getSecInquiryRecordListClassInfo error:{}", e);
            return BaseResponse.ok(classInfoAnnotationList);
        }
        return BaseResponse.ok(classInfoAnnotationList);
    }

    @Override
    public BaseResponse getSecInquiryRecordListClass(SecInquiryRecordDto request, LogUserInfoVo user) {
        QueryMap<SecInquiryRecordDto> params = new QueryMap<>(request);
        params.put("secName").put("secType").put("secOrganizeForm").put("secBiddingType").put("isNew")
                .put("secTradingPlace").put("secPlace").put("tendereeName").put("sirPrerequisites").put("pId")
                .put("sirIntendedUnitName").put("sirRemark").put("createUname").put("pCompetentDepartmentName")
                .put(request.getCreateTimes(), "createTimeStart", "createTimeEnd").put("state").put("pName")
                .put(request.getSirConMoneys(), "sirConMoneyStart", "sirConMoneyEnd")
                .put(request.getSecStartTimes(), "secStartTimeStart", "secStartTimeEnd")
                .put(request.getSecBiddingCycles(), "secBiddingCycleStart", "secBiddingCycleEnd")
                .put("pNameList").put("secNameList").put("pCategoryList").put("createUnameList")
                .put("createTimeStr").put("stateList").put("participationOrFinished");

        // 创建人、流程处理人、抄送人、协同人
        String authSql = getSecInquiryRecordListClassAuthSql(request, user);
        if (ValidateUtil.isEmpty(authSql)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }

        IPage<SecInquiryRecordDto> page = new Page<>(request.getPage(), request.getSize());
        List<SecInquiryRecordVo> datas = baseMapper.getSecInquiryRecordListClass(authSql, page, params.getQuery());
        if (CollectionUtils.isEmpty(datas)) {
            return BaseResponse.ok(Collections.emptyList(), 0);
        }
        Set<String> pIdSet = datas.stream()
                .filter(d -> ValidateUtil.isNotEmpty(d.getPId()) && "007".equals(d.getPBuildModel()))
                .map(SecInquiryRecordVo::getPId)
                .collect(Collectors.toSet());

        Map<String, List<PpOwnerUnitDTO>> ppOwnerUnitMap = Collections.emptyMap();
        if (!pIdSet.isEmpty()) {
            List<PpOwnerUnitDTO> ppOwnerUnits = ippOwnerUnitService.listPpOwnerUnitByPIds(new ArrayList<>(pIdSet));
            ppOwnerUnitMap = ppOwnerUnits.stream()
                    .collect(Collectors.groupingBy(PpOwnerUnitDTO::getPId));
        }

        for (SecInquiryRecordVo d : datas) {
            if (ValidateUtil.isNotEmpty(d.getPId()) && "007".equals(d.getPBuildModel())) {
                List<PpOwnerUnitDTO> units = ppOwnerUnitMap.getOrDefault(d.getPId(), Collections.emptyList());
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
        return BaseResponse.ok(datas, Long.valueOf(page.getTotal()).intValue());
    }

    private String getSecInquiryRecordListClassAuthSql(SecInquiryRecordDto request, LogUserInfoVo user) {
        String njcj = sysConfigService.getConfigByCode("njcjCompanyId");
        if (ValidateUtil.isEmpty(njcj)) {
            njcj = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
        }
        String authSql = sysUserService.getAuthSql("Sec_Inquiry_Record", user.getId(), "Create_Uid", OutUserAuthEnum.Approver_Creator.getIndex(), "State", "id");
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
    public BaseResponse getSecInquiryRecordList(SecInquiryRecordDto request) {
        QueryMap<SecInquiryRecordDto> params = new QueryMap<>(request);
        params.put("secName").put("secType").put("secOrganizeForm").put("secBiddingType")
                .put("secTradingPlace").put("secPlace").put("tendereeName").put("sirPrerequisites").put("pId")
                .put("sirIntendedUnitName").put("sirRemark").put("createUname").put("pCompetentDepartmentName")
                .put(request.getCreateTimes(), "createTimeStart", "createTimeEnd").put("state").put("pName")
                .put(request.getSirConMoneys(), "sirConMoneyStart", "sirConMoneyEnd")
                .put(request.getSecStartTimes(), "secStartTimeStart", "secStartTimeEnd")
                .put(request.getSecBiddingCycles(), "secBiddingCycleStart", "secBiddingCycleEnd")
                .put("secId").put("projectId");
        IPage<SecInquiryRecordVo> page = new Page<>(request.getPage(), request.getSize());
        List<SecInquiryRecordVo> datas = baseMapper.getSecInquiryRecordList(page, params.getQuery());
        return BaseResponse.ok(datas, Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse getNewSecQuotationUnitBySecId(String secId) {
        if(ValidateUtil.isEmpty(secId)){
            return  BaseResponse.fail(ResMsg.notReqParam);
        }
        LambdaQueryWrapper<SecInquiryRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SecInquiryRecord::getState, 7)
                .orderByDesc(SecInquiryRecord::getCreateTime)
                .last("LIMIT 1");
        SecInquiryRecord data = baseMapper.selectOne(wrapper);
        if (ValidateUtil.isEmpty(data)){
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        List<SecQuotationUnitVo> datas = baseMapper.getSecQuotationUnitBySirId(data.getId());
        return BaseResponse.ok(datas);
    }
}
