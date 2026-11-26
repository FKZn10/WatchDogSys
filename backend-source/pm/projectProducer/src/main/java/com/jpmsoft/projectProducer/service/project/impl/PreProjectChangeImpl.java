package com.jpmsoft.projectProducer.service.project.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectChangeModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.project.PreProjectChangeMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectChange;
import com.jpmsoft.projectProducer.service.other.*;
import com.jpmsoft.projectProducer.service.project.*;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 备注： 新增修改增加附件时  审批通过同步到项目接口需要同步操作
 */
@Service
public class PreProjectChangeImpl extends ServiceImpl<PreProjectChangeMapper, PreProjectChange> implements IPreProjectChangeService {

    @Resource
    private IPcConstructionAreaDtlService pcConstructionAreaDtlService;
    @Resource
    private IPcSourcesFundsService pcSourcesFundsService;
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IPreProjectService preProjectService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private IPcInvDetailService pcInvDetailService;
    @Resource
    private ISysConfigService sysConfigService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdatePreProjectChange(PreProjectChangeModel request, String uId) {
        PreProjectChange pojo = new PreProjectChange();
        BeanUtils.copyProperties(request, pojo);
        if (ValidateUtil.isEmpty(request.getPcId())) {
            QueryWrapper<PreProjectChange> wrapper = new QueryWrapper<>();
            wrapper.eq("P_Id", request.getpId());
            wrapper.and(wr -> wr.ne("State", ApprovalStatusEnum.Invalid.getIndex()).ne("State", ApprovalStatusEnum.Already_Over.getIndex()));
            if (baseMapper.selectCount(wrapper) > 0)
                return BaseResponse.fail(ResMsg.recordUnFinish);
            pojo.setPcCreateTime(new Date());
            pojo.setPUpdateTime(new Date());
            pojo.setPcId(GenerateUUID.getUUID());
            pojo.setDeleted(Boolean.FALSE);
            pojo.setState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(pojo);
            pcConstructionAreaDtlService.insertPcConstructionAreaDtls(request.getPcConstructionAreaDtls(), pojo.getPcId(), "create");
            pcSourcesFundsService.insertPcSourcesFunds(request.getPcSourcesFunds(), pojo.getPId(), pojo.getPcId(), pojo.getPcCreateUid(), pojo.getPcCreateUname(), "create", request.getTempSave());
            pcInvDetailService.insertPcInvDetail(request.getPcInvDetails(), pojo.getPId(), pojo.getPcId(), pojo.getPcCreateUid(), pojo.getPcCreateUname(), "create", request.getTempSave());
            sysFileService.saveFile(pojo.getPcId(), request.gettId(), pojo.getState() + "", pojo.getPcCreateUid(), pojo.getPcCreateUname()
                    , pojo.getPcCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormCopyAndRefreshRelationFile(
                    Arrays.asList(request.getFileRelationId(), request.getpAssignmentFile(), request.getpCommitteeApprovaDocument(), request.getpCityLeaderMeetingMinutes(), request.getpCityLeaderInstructions(), request.getpOther(), request.getpApprovaDocument(), request.getpLeaderMeetingMinutes(), request.getpLeaderInstructions(), request.getpEstimateFile(), request.getpRelationFile(), request.getpInvFile(), request.getpEconomicFile(), request.getpProposalFile(), request.getpReportFile()),
                    Arrays.asList(request.getFileIds(), request.getpAssignmentFileIds(), request.getpCommitteeApprovaDocumentIds(), request.getpCityLeaderMeetingMinutesIds(), request.getpCityLeaderInstructionsIds(), request.getpOtherIds(), request.getpApprovaDocumentIds(), request.getpLeaderMeetingMinutesIds(), request.getpLeaderInstructionsIds(), request.getpEstimateFileIds(), request.getpRelationFileIds(), request.getpInvFileIds(), request.getpEconomicFileIds(), request.getpProposalFileIds(), request.getpReportFileIds()), pojo.getPcId());
            apiUtilsService.insertFiledChangeRecord(preProjectService.getById(request.getpId()), pojo, "Pre_Project_Change", uId);
        } else {
            PreProjectChange entity = baseMapper.selectById(request.getPcId());
            if (JudgeUtil.checkObject(entity))
                return BaseResponse.fail(ResMsg.infoNotExist);
            pojo.setPcCreateTime(entity.getPcCreateTime());
            pojo.setPcCreateUid(entity.getPcCreateUid());
            pojo.setPcCreateUname(entity.getPcCreateUname());
            pojo.setPUpdateTime(new Date());
            pojo.setPCode(entity.getPCode());
            pojo.setState(entity.getState());
            pojo.setStateTime(entity.getStateTime());
            pojo.setDeleted(entity.getDeleted());
            baseMapper.updateById(pojo);
            pcConstructionAreaDtlService.insertPcConstructionAreaDtls(request.getPcConstructionAreaDtls(), pojo.getPcId(), "update");
            pcSourcesFundsService.insertPcSourcesFunds(request.getPcSourcesFunds(), pojo.getPId(), pojo.getPcId(), pojo.getPcCreateUid(), pojo.getPcCreateUname(), "update", request.getTempSave());
            pcInvDetailService.insertPcInvDetail(request.getPcInvDetails(), pojo.getPId(), pojo.getPcId(), pojo.getPcCreateUid(), pojo.getPcCreateUname(), "update", request.getTempSave());
            sysFileService.saveFile(entity.getPcId(), request.gettId(), entity.getState() + "", entity.getPcCreateUid(), entity.getPcCreateUname()
                    , entity.getPcCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getpAssignmentFile(), request.getpCommitteeApprovaDocument(), request.getpCityLeaderMeetingMinutes(), request.getpCityLeaderInstructions(), request.getpOther(), request.getpApprovaDocument(), request.getpLeaderMeetingMinutes(), request.getpLeaderInstructions(), request.getpEstimateFile(), request.getpRelationFile(), request.getpInvFile(), request.getpEconomicFile(), request.getpProposalFile(), request.getpReportFile()),
                    Arrays.asList(request.getFileIds(), request.getpAssignmentFileIds(), request.getpCommitteeApprovaDocumentIds(), request.getpCityLeaderMeetingMinutesIds(), request.getpCityLeaderInstructionsIds(), request.getpOtherIds(), request.getpApprovaDocumentIds(), request.getpLeaderMeetingMinutesIds(), request.getpLeaderInstructionsIds(), request.getpEstimateFileIds(), request.getpRelationFileIds(), request.getpInvFileIds(), request.getpEconomicFileIds(), request.getpProposalFileIds(), request.getpReportFileIds()), pojo.getPcId());
            apiUtilsService.insertFiledChangeRecord(entity, pojo, "Pre_Project_Change", uId);
        }
        return BaseResponse.ok(pojo.getPcId());
    }

    @Override
    public BaseResponse getPreProjectChangeList(PreProjectChangeModel request, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "Pc_Create_Uid", OutUserAuthEnum.NOT.getIndex(), "State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<PreProjectChangeModel> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pId").put("pProjectAbbreviation").put("pOriginalName")
                .put("pNameInitials").put("pUseUnit").put("pAddress").put("pCompetentDepartment").put("pSafetyFiling")
                .put("pProjectDirector").put("pProjectStage").put("pConstructionUnit").put("pCheckType")
                .put("pParentId").put("pProjectCode").put("pName").put("pCode").put("pIsProjectApproval")
                .put("pAssignmentNo").put("pType").put("pIntroduction").put("state").put("pSuperviseType")
                .put("pGroupType").put("pMajorType").put("pProjectStatus").put("pConstructionUnitName");
        IPage<PreProjectChangeModel> page = new Page<>(request.getPage(), request.getSize());
        List<PreProjectChangeModel> data = baseMapper.getPreProjectChangeList(page, params.getQuery());
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    @Override
    public BaseResponse getPreProjectChangeById(String pcId) {
        return BaseResponse.ok(baseMapper.getPreProjectChangeById(pcId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse approvalSyncProInfo(String pcId, String uId) {
        PreProjectChange pojo = baseMapper.selectById(pcId);
        if (!Objects.equals(pojo.getState(), ApprovalStatusEnum.Already_Over.getIndex()))
            return BaseResponse.fail(ResMsg.stateCannotAct);
        PreProject pro = preProjectService.getById(pojo.getPId());
        if (pro == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        PreProject entity = new PreProject();
        BeanUtils.copyProperties(pojo, entity);
        //不允许修改值
        entity.setpStatus(pro.getpStatus());
        entity.setState(pro.getState());
        entity.setStateTime(pro.getStateTime());
        entity.setpCreateTime(pro.getpCreateTime());
        entity.setpCreateUserId(pro.getpCreateUserId());
        entity.setpCreateUserName(pro.getpCreateUserName());
        entity.setpUpdateTime(new Date());
        entity.setpUpdateUserId(pro.getpCreateUserId());
        entity.setDeleted(pro.getDeleted());
        entity.setpCode(pro.getpCode());
        entity.setpSafetyFiling(pro.getpSafetyFiling());

        //文件字段使用新的relationId
        entity.setpAssignmentFile(GenerateUUID.getUUID());
        entity.setpCommitteeApprovaDocument(GenerateUUID.getUUID());
        entity.setpCityLeaderMeetingMinutes(GenerateUUID.getUUID());
        entity.setpCityLeaderInstructions(GenerateUUID.getUUID());
        entity.setpOther(GenerateUUID.getUUID());
        entity.setpApprovaDocument(GenerateUUID.getUUID());
        entity.setpLeaderMeetingMinutes(GenerateUUID.getUUID());
        entity.setpLeaderInstructions(GenerateUUID.getUUID());
        entity.setpEstimateFile(GenerateUUID.getUUID());
        entity.setpInvFile(GenerateUUID.getUUID());
        entity.setpEconomicFile(GenerateUUID.getUUID());
        entity.setpProposalFile(GenerateUUID.getUUID());
        entity.setpReportFile(GenerateUUID.getUUID());
        PreProject change = new PreProject();
        BeanUtils.copyProperties(pro, change);
        //不拷贝空值 空值不修改
        BeanUtil.copyProperties(entity, change, CopyOptions.create().setIgnoreNullValue(true));
        InterfaceEncDecDTO ied = sysConfigService.getInterfaceEncDec();
        entity.setSign(apiUtilsService.sign(ied, ied.getProjectSign(), change));
        preProjectService.updateById(entity);
        apiUtilsService.insertFiledChangeRecord(pro, entity, "Pre_Project", uId);
        SysFile filePojo = sysFileService.getById(entity.getpId());
        pcConstructionAreaDtlService.syncPcConstructionAreaDtl2Project(pcId, entity.getpId());
        pcSourcesFundsService.syncPcSourcesFunds2Project(pcId, entity.getpId(), entity.getpCreateUserId(), entity.getpCreateUserName());
        pcInvDetailService.syncPcInvDetail2Project(pcId, entity.getpId(), entity.getpCreateUserId(), entity.getpCreateUserName());
        if (filePojo != null) {
            filePojo.setFileRelationId(pojo.getPRelationFile());
            sysFileService.updateById(filePojo);
        }
        sysFileAnnexService.replaceFormFile(Arrays.asList(pojo.getPAssignmentFile(),
                        pojo.getPCommitteeApprovaDocument(),
                        pojo.getPCityLeaderMeetingMinutes(),
                        pojo.getPCityLeaderInstructions(),
                        pojo.getPOther(),
                        pojo.getPApprovaDocument(),
                        pojo.getPLeaderMeetingMinutes(),
                        pojo.getPLeaderInstructions(),
                        pojo.getPEstimateFile(),
                        pojo.getPInvFile(),
                        pojo.getPEconomicFile(),
                        pojo.getPProposalFile(),
                        pojo.getPReportFile()),
                Arrays.asList(entity.getpAssignmentFile(),
                        entity.getpCommitteeApprovaDocument(),
                        entity.getpCityLeaderMeetingMinutes(),
                        entity.getpCityLeaderInstructions(),
                        entity.getpOther(),
                        entity.getpApprovaDocument(),
                        entity.getpLeaderMeetingMinutes(),
                        entity.getpLeaderInstructions(),
                        entity.getpEstimateFile(),
                        entity.getpInvFile(),
                        entity.getpEconomicFile(),
                        entity.getpProposalFile(),
                        entity.getpReportFile()), entity.getpId());
        return BaseResponse.ok(pcId);
    }
}
