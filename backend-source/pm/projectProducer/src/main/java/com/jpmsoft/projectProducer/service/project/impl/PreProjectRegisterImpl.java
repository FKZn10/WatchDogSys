package com.jpmsoft.projectProducer.service.project.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.common.model.request.project.PreProjectRegisterRequest;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectDtlModel;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectRegisterDtlModel;
import com.jpmsoft.common.model.requestAndResponse.Project.ProSourcesFundsRegModel;
import com.jpmsoft.common.model.response.FileTemplateTree;
import com.jpmsoft.common.model.response.Project.ProjectRegisterResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.feign.UserProducerFeign;
import com.jpmsoft.projectProducer.mapper.project.PreProjectRegisterMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectRegister;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.project.*;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import com.jpmsoft.projectProducer.utils.MathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PreProjectRegisterImpl extends ServiceImpl<PreProjectRegisterMapper, PreProjectRegister> implements IPreProjectRegisterService {

    @Resource
    private IPreProjectService preProjectService;

    @Resource
    private ISysFileService sysFileService;

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Resource
    private IApiUtilsService apiUtilsService;

    @Resource
    private IProSourcesFundsRegService proSourcesFundsRegService;

    @Resource
    private IPreProjectRegisterDtlService preProjectRegisterDtlService;

    @Resource
    private IProSourcesFundsService proSourcesFundsService;
    @Resource
    private UserProducerFeign userProducerFeign;
    @Autowired
    private ISysConfigService sysConfigService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse CreateOrUpdatePreProjectRegister(PreProjectRegisterRequest request) {
        PreProjectRegister entity;
        String codeRule = "";
        if (ValidateUtil.isEmpty(request.getPrId())) {
            entity = new PreProjectRegister();
            if (apiUtilsService.sequenceRestEnable("PreProjectRegister") == true) {
                entity.setPrCode(apiUtilsService.generateCodeByTCode("PreProjectRegister", request));
                if (ValidateUtil.isEmpty(entity.getPrCode())) return BaseResponse.fail(ResMsg.codeGenerError);
                codeRule = "PreProjectRegister";
            } else if (ValidateUtil.isEmpty(request.getPrCode())) {
                return BaseResponse.fail(ResMsg.codeIsEmpty);
            } else entity.setPrCode(request.getPrCode());
        } else {
            entity = baseMapper.selectById(request.getPrId());
            if (entity == null) return BaseResponse.fail(ResMsg.infoNotExist);
        }
        entity.setPrName(request.getPrName());
        QueryWrapper<PreProjectRegister> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(request.getTeId())) wrapper.eq("Te_Id", request.getTeId());
        wrapper.and(c -> c.eq("Pr_Name", entity.getPrName()).or().eq("Pr_Code", entity.getPrCode()));
        if (!ValidateUtil.isEmpty(request.getPrId())) wrapper.ne("PR_ID", request.getPrId());
        wrapper.ne("PR_Approval_Status", ApprovalStatusEnum.Invalid.getIndex());
        if (baseMapper.selectCount(wrapper) > 0) return BaseResponse.fail(ResMsg.nameCodeRepeat);

        entity.setPrUseUnitLetter(request.getPrUseUnitLetter());
        entity.setPrUseUnit(request.getPrUseUnit());
//        entity.setPrCode(request.getPrCode());
        entity.setPrType(request.getPrType());
        entity.setPrSourcesOfBunds(request.getPrSourcesOfBunds());
        entity.setPrRemark(request.getPrRemark());
        entity.setPrReason(request.getPrReason());
        entity.setPrOther(request.getPrOther());
        BigDecimal prEstimatedAmount = new BigDecimal(0);
        if (request.getPrEstimatedAmount() != null)
            prEstimatedAmount = MathUtil.DecimalMultiplicationInt(request.getPrEstimatedAmount(), 10000);
        entity.setPrEstimatedAmount(prEstimatedAmount);
        entity.setPrCommitteeProjectCode(request.getPrCommitteeProjectCode());
        entity.setPrCityLeaderMeetingMinutes(request.getPrCityLeaderMeetingMinutes());
        entity.setPrProjectStatus(request.getPrProjectStatus());
        entity.setPrCommitteeApprovaDocument(request.getPrCommitteeApprovaDocument());
        entity.setPrCityLeaderInstructions(request.getPrCityLeaderInstructions());
        entity.setPrCategory(request.getPrCategory());
        entity.setPrBuildingScale(request.getPrBuildingScale());
        entity.setPrNature(request.getPrNature());
        entity.setPrReportedAmount(MathUtil.DecimalMultiplicationInt(request.getPrReportedAmount(), 10000));
        entity.setPrApprovaDocument(request.getPrApprovaDocument());
        entity.setPrLeaderMeetingMinutes(request.getPrLeaderMeetingMinutes());
        entity.setPrLeaderInstructions(request.getPrLeaderInstructions());
        entity.setPrConstructionCost(MathUtil.DecimalMultiplicationInt(request.getPrConstructionCost(), 10000));
        entity.setPrContractPrice(MathUtil.DecimalMultiplicationInt(request.getPrContractPrice(), 10000));
        entity.setPrPurchasePrice(MathUtil.DecimalMultiplicationInt(request.getPrPurchasePrice(), 10000));
        entity.setPrFiscalBudget(MathUtil.DecimalMultiplicationInt(request.getPrFiscalBudget(), 10000));
        entity.setPrApproveBudget(MathUtil.DecimalMultiplicationInt(request.getPrApproveBudget(), 10000));
        entity.setPrConstructionUnitId(request.getPrConstructionUnitId());
        entity.setPrPlanStart(request.getPrPlanStart());
        entity.setPrPlanEnd(request.getPrPlanEnd());
        entity.setPrPmName(request.getPrPmName());
        entity.setPrPmTel(request.getPrPmTel());
        entity.setPrEstimateFile(request.getPrEstimateFile());
        entity.setPrScaleDescribe(request.getPrScaleDescribe());
        entity.setPrInvNature(request.getPrInvNature());
        entity.setPrBuildingUnit(request.getPrBuildingUnit());
        entity.setPrMajorType(request.getPrMajorType());
        entity.setPrCompetentDepartment(request.getPrCompetentDepartment());
        entity.setPrInterventionYear(request.getPrInterventionYear());
        entity.setPrProjectStage(request.getPrProjectStage());
        entity.setPrSafetyFiling(request.getPrSafetyFiling());
        entity.setCplId(request.getCplId());
        entity.setPrApplyName(request.getPrApplyName());
        entity.setTeId(request.getTeId());
        entity.setPrAddress(request.getPrAddress());
        entity.setPrCampus(request.getPrCampus());
        entity.setPrYear(request.getPrYear());
        entity.setPrOpinionFile(request.getPrOpinionFile());
        entity.setPrPlanFile(request.getPrPlanFile());
        entity.setPrFeasibilityFile(request.getPrFeasibilityFile());
        entity.setPrApproveFile(request.getPrApproveFile());
        entity.setPrWidth(request.getPrWidth());
        entity.setPrLength(request.getPrLength());
        entity.setPrLandArea(request.getPrLandArea());
        InterfaceEncDecDTO encDec = sysConfigService.getInterfaceEncDec();
        if (ValidateUtil.isEmpty(request.getPrId())) {
            entity.setPrId(GenerateUUID.getUUID());
            entity.setPrCreateTime(new Date());
            entity.setPrApplicantId(request.getPrApplicantId());
            entity.setPrCreateUserName(request.getPrCreateUserName());
            entity.setPrStatus(ValidateUtil.isEmpty(request.getPrStatus()) ? "1" : request.getPrStatus());
            entity.setPrApprovalStatus(ApprovalStatusEnum.Not_Started.getIndex());
            entity.setSign(apiUtilsService.sign(encDec, encDec.getPrSign(), entity));
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getPrId(), request.gettId(), entity.getPrApprovalStatus() + "", entity.getPrApplicantId(), entity.getPrCreateUserName(), entity.getPrCreateTime(), null, request.getFileTitle(), request.getFileCode(), "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getPrCommitteeApprovaDocument(), request.getPrCityLeaderInstructions(), request.getPrOther(), request.getPrCityLeaderMeetingMinutes(), request.getPrApprovaDocument(), request.getPrLeaderMeetingMinutes(), request.getPrLeaderInstructions(), request.getPrEstimateFile(), request.getPrOpinionFile(), request.getPrPlanFile(), request.getPrFeasibilityFile(), request.getPrApproveFile()), entity.getPrId());
        } else {
            if (JudgeUtil.isApprovedState(entity.getPrApprovalStatus()))
                return BaseResponse.fail(ResMsg.stateCannotAct);
            entity.setSign(apiUtilsService.sign(encDec, encDec.getPrSign(), entity));
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getPrId(), request.gettId(), entity.getPrApprovalStatus() + "", entity.getPrApplicantId(), entity.getPrCreateUserName(), entity.getPrCreateTime(), null, request.getFileTitle(), request.getFileCode(), "update", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getPrCommitteeApprovaDocument(), request.getPrCityLeaderInstructions(), request.getPrOther(), request.getPrCityLeaderMeetingMinutes(), request.getPrApprovaDocument(), request.getPrLeaderMeetingMinutes(), request.getPrLeaderInstructions(), request.getPrEstimateFile(), request.getPrOpinionFile(), request.getPrPlanFile(), request.getPrFeasibilityFile(), request.getPrApproveFile())
                    , Arrays.asList(request.getFileIds(), request.getPrCommitteeApprovaDocIds(), request.getPrCityLeaderInstructionsIds(), request.getPrOtherIds(), request.getPrCityLeaderMeetingMinutesIds(), request.getPrApprovaDocumentIds(), request.getPrLeaderMeetingMinutesIds(), request.getPrLeaderInstructionsIds(), request.getPrEstimateFileIds(), request.getPrOpinionFileIds(), request.getPrPlanFileIds(), request.getPrFeasibilityFileIds(), request.getPrApproveFileIds()), entity.getPrId());
        }
        proSourcesFundsRegService.createOrUpdateProSourcesFundsReg(request.getProSourcesFundsRegs(), entity.getPrId());
        preProjectRegisterDtlService.createOrUpdatePreProjectRegisterDtl(request.getPreProjectRegisterDtl(), entity.getPrId());
        if (ValidateUtil.isNotEmpty(codeRule)) apiUtilsService.refershAutoNo(codeRule, request);
        return BaseResponse.ok(entity.getPrId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deletePreProjectRegister(PreProjectRegisterRequest request) {
        PreProjectRegister entity;
        if (ValidateUtil.isEmpty(request.getPrId())) return BaseResponse.fail(ResMsg.notReqParam);
        else {
            entity = baseMapper.selectById(request.getPrId());
            if (entity == null) return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (ApprovalStatusEnum.Not_Started.getIndex() != entity.getPrApprovalStatus() && ApprovalStatusEnum.Returned.getIndex() != entity.getPrApprovalStatus())
            return BaseResponse.fail(ResMsg.stateNotDel);
        proSourcesFundsRegService.deleteProSourcesFundsRegByPrId(entity.getPrId());
        baseMapper.deleteById(entity.getPrId());
        sysFileService.deleteFileById(entity.getPrId());
        return BaseResponse.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse approvalPreProjectRegister(PreProjectRegisterRequest request) {
        FileTemplateTree temp = userProducerFeign.getFileTemplateByCode("Project");
        if (ValidateUtil.isNull(temp)) return BaseResponse.fail(String.format(ResMsg.notFoundFileTmp, "Project"));
        PreProjectRegister entity;
        List<PreProjectRegisterDtlModel> dtlentity;
        SysFile fileentity;
        if (ValidateUtil.isEmpty(request.getPrId())) return BaseResponse.fail(ResMsg.notReqParam);
        else {
            entity = baseMapper.selectById(request.getPrId());
            if (entity == null) return BaseResponse.fail(ResMsg.infoNotExist);

            fileentity = sysFileService.getById(entity.getPrId());

            dtlentity = preProjectRegisterDtlService.getPreProjectRegisterDtlByPrId(request.getPrId());
        }
        entity.setPrLandApproval(request.getPrLandApproval());
        entity.setPrApprovalStatus(Integer.parseInt(fileentity.getFileflowstatus()));
        entity.setPrApprovalTime(new Date());
        //entity.setPrStatus(fileentity.getFileflowstatus());
        baseMapper.updateById(entity);

        if (Objects.equals(entity.getPrApprovalStatus(), ApprovalStatusEnum.Already_Over.getIndex())) {
            if (!preProjectService.registerProjectExist(entity.getPrId())) {
                InterfaceEncDecDTO ied = sysConfigService.getInterfaceEncDec();
                //已经生成过项目的项目注册则不再生成项目
                Date now = new Date();
                //结束 生成项目
                PreProject projectPojo = new PreProject();
                projectPojo.setpId(GenerateUUID.getUUID());
                projectPojo.setpName(entity.getPrName());
                projectPojo.setpCode(entity.getPrCode());
                projectPojo.setpCreateUserName(entity.getPrCreateUserName());
                projectPojo.setpCreateUserId(entity.getPrApplicantId());
                projectPojo.setpType(entity.getPrType());
                projectPojo.setpCreateTime(now);
                projectPojo.setpParentId("0");
                projectPojo.setpSourcesFunds(entity.getPrSourcesOfBunds());
                projectPojo.setpUseUnit(entity.getPrUseUnit());
                projectPojo.setpProjectCode(entity.getPrCommitteeProjectCode());
                projectPojo.setpCalculateRoughlyAmount(entity.getPrEstimatedAmount());
                projectPojo.setPrId(entity.getPrId());
                projectPojo.setpOverview(entity.getPrReason());
                projectPojo.setpCommitteeApprovaDocument(GenerateUUID.getUUID());
                projectPojo.setpCityLeaderMeetingMinutes(GenerateUUID.getUUID());
                projectPojo.setpCityLeaderInstructions(GenerateUUID.getUUID());
                projectPojo.setpOther(GenerateUUID.getUUID());
                projectPojo.setpNature(entity.getPrNature());
                projectPojo.setpReportedAmount(entity.getPrReportedAmount());
                projectPojo.setpLeaderMeetingMinutes(GenerateUUID.getUUID());
                projectPojo.setpLeaderInstructions(GenerateUUID.getUUID());
                projectPojo.setpApprovaDocument(GenerateUUID.getUUID());
                projectPojo.setpProjectDirectorTel(entity.getPrPmTel());
                projectPojo.setpConstructionCost(entity.getPrConstructionCost());
                projectPojo.setpContractPrice(entity.getPrContractPrice());
                projectPojo.setpPurchasePrice(entity.getPrPurchasePrice());
                projectPojo.setpConstructionUnit(entity.getPrConstructionUnitId());
                projectPojo.setpEstimateFile(GenerateUUID.getUUID());
                projectPojo.setpProjectDirector(entity.getPrPmName());
                projectPojo.setpScaleDescribe(entity.getPrScaleDescribe());
                projectPojo.setpNameHistory(projectPojo.getpName());
                projectPojo.setpBuildingScale(entity.getPrBuildingScale());
                projectPojo.setpBuildingUnit(entity.getPrBuildingUnit());
                projectPojo.setpStatus("1");
                projectPojo.setpMajorType(entity.getPrMajorType());
                projectPojo.setState(entity.getPrApprovalStatus());
                projectPojo.setStateTime(now);
                projectPojo.setpCompetentDepartment(entity.getPrCompetentDepartment());
                projectPojo.setpCategory(entity.getPrCategory());
                projectPojo.setpInterventionYear(entity.getPrInterventionYear());
                projectPojo.setpProjectStage(entity.getPrProjectStage());
                projectPojo.setpSafetyFiling(entity.getPrSafetyFiling());
                projectPojo.setCplId(entity.getCplId());
                projectPojo.setpApplyName(entity.getPrApplyName());
                projectPojo.setpFiscalBudget(entity.getPrFiscalBudget());
                projectPojo.setTeId(entity.getTeId());
                projectPojo.setpAddress(entity.getPrAddress());
                projectPojo.setpPlanStartDate(entity.getPrPlanStart());
                projectPojo.setpPlanCompleteDate(entity.getPrPlanEnd());
                projectPojo.setpApproveBudget(entity.getPrApproveBudget());
                projectPojo.setpCampus(entity.getPrCampus());
                projectPojo.setpOpinionFile(GenerateUUID.getUUID());
                projectPojo.setpPlanFile(GenerateUUID.getUUID());
                projectPojo.setpFeasibilityFile(GenerateUUID.getUUID());
                projectPojo.setpApproveFile(GenerateUUID.getUUID());
                projectPojo.setpLength(entity.getPrLength());
                projectPojo.setpWidth(entity.getPrWidth());
                projectPojo.setpSiteArea(entity.getPrLandArea());
                projectPojo.setSign(apiUtilsService.sign(ied, ied.getProjectSign(), projectPojo));
                preProjectService.insertPreProject(projectPojo);
                proSourcesFundsService.copyDataByFundsReg(entity.getPrId(), projectPojo.getpId());
                sysFileAnnexService.copyFileAnnex2NewRelation(Arrays.asList(entity.getPrCommitteeApprovaDocument(), entity.getPrCityLeaderMeetingMinutes(), entity.getPrCityLeaderInstructions(), entity.getPrOther(), entity.getPrApprovaDocument(), entity.getPrLeaderMeetingMinutes(), entity.getPrLeaderInstructions(), entity.getPrEstimateFile(), entity.getPrOpinionFile(), entity.getPrPlanFile(), entity.getPrFeasibilityFile(), entity.getPrApproveFile())
                        , Arrays.asList(projectPojo.getpCommitteeApprovaDocument(), projectPojo.getpCityLeaderMeetingMinutes(), projectPojo.getpCityLeaderInstructions(), projectPojo.getpOther(), projectPojo.getpApprovaDocument(), projectPojo.getpLeaderMeetingMinutes(), projectPojo.getpLeaderInstructions(), projectPojo.getpEstimateFile(), projectPojo.getpOpinionFile(), projectPojo.getpPlanFile(), projectPojo.getpFeasibilityFile(), projectPojo.getpApproveFile()), projectPojo.getpId());
                sysFileService.saveFile(projectPojo.getpId(), temp.gettId(), projectPojo.getState() + "", projectPojo.getpCreateUserId(), projectPojo.getpCreateUserName()
                        , projectPojo.getpCreateTime(), projectPojo.getpId(), temp.gettName(), temp.gettCode(), "create",
                        GenerateUUID.getUUID(), null, temp.gettCode(), null);

                List<PreProjectDtlModel> projectDtllist = new ArrayList<>();
                //對項目表插入匡算信息
                if (CollectionUtil.isNotEmpty(dtlentity)) {
                    for (PreProjectRegisterDtlModel item : dtlentity) {
                        PreProjectDtlModel projectDtl = new PreProjectDtlModel();
                        projectDtl.setPpdId(GenerateUUID.getUUID());
                        projectDtl.setPpdNumber(item.getPrdNumber());
                        projectDtl.setPpdFid(projectPojo.getpId());
                        projectDtl.setPpdParenetId(item.getPrdParenetId());
                        projectDtl.setPpdCode(item.getPrdCode());
                        projectDtl.setPpdName(item.getPrdName());
                        projectDtl.setPpdUnit(item.getPrdUnit());
                        projectDtl.setPpdCount(item.getPrdCount());
                        projectDtl.setPpdPrice(item.getPrdPrice());
                        projectDtl.setPpdTotal(item.getPrdTotal());
                        projectDtl.setPpdRemark(item.getPrdRemark());
                        projectDtl.setPpdOrder(item.getPrdOrder());
                        projectDtl.setPpdLevel(item.getPrdLevel());
                        projectDtl.setPpdType(item.getPrdType());
                        projectDtl.setPpdState(item.getPrdState());
                        projectDtl.setPpdPath(item.getPrdPath());
                        projectDtl.setPpdLeafNode(item.getPrdLeafNode());
                        projectDtllist.add(projectDtl);
                    }
                    preProjectService.insertPreProjectDtl(projectDtllist, projectPojo.getpId());
                }
            }
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getPreProjectRegisterList(PreProjectRegisterRequest request) {
        QueryMap<PreProjectRegisterRequest> params = setFilter(request);
        IPage<ProjectRegisterResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ProjectRegisterResponse> datas = getPreProjectRegisterLists(params, page);
        if (!CollectionUtils.isEmpty(datas)) {
            InterfaceEncDecDTO encDec = sysConfigService.getInterfaceEncDec();
            if (encDec.getPrListVerify()) {
                List<PreProjectRegister> list = baseMapper.selectBatchIds(datas.stream().map(ProjectRegisterResponse::getPrId).collect(Collectors.toList()));
                list.forEach(d -> {
                    apiUtilsService.signVerify(encDec, encDec.getPrListVerify(), d, d.getSign());
                });
            }
        }
        return BaseResponse.ok(amtDivision(datas), (int) page.getTotal());
    }

    private List<ProjectRegisterResponse> getPreProjectRegisterLists(QueryMap<PreProjectRegisterRequest> params, IPage<ProjectRegisterResponse> page) {
        String code = sysConfigService.getConfigByCode("PreProjectRegisterWithoutFlowStop");
        List<ProjectRegisterResponse> datas = new ArrayList<>();
        if (code != null) {
            if (code.equals("1")) {
                datas = baseMapper.getPreProjectRegisterListWithoutFlowStop(params.getQuery(), page);
            } else {
                datas = baseMapper.getPreProjectRegisterList(params.getQuery(), page);
            }
        } else {
            datas = baseMapper.getPreProjectRegisterList(params.getQuery(), page);
        }
        return datas;
    }

    @Override
    public BaseResponse getPreProjectRegisterStatisticsList(PreProjectRegisterRequest request) {
        QueryMap<PreProjectRegisterRequest> params = setFilter(request);
        IPage<ProjectRegisterResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ProjectRegisterResponse> datas = baseMapper.getPreProjectRegisterList(params.getQuery(), page);
        BigDecimal prEstimatedAmountSum = baseMapper.getEstimatedAmountSum(params.getQuery());
        Map<String, Object> data = new HashMap<>();
        data.put("prEstimatedAmountSum", prEstimatedAmountSum == null ? BigDecimal.ZERO : prEstimatedAmountSum);
        data.put("data", datas);
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    private QueryMap<PreProjectRegisterRequest> setFilter(PreProjectRegisterRequest request) {
        QueryMap<PreProjectRegisterRequest> params = new QueryMap<>(request);
        params.put("prStatus").put("prType").put("prCode").put("prName").put("prSafetyFiling").put("prCategory").put("prProjectStatus").put("prSourcesOfBunds").put("prApprovalStatus").put("prCreateUserName").put("prCampus");
        return params;
    }

    @Override
    public BaseResponse getPreProjectRegisterByDepCompany(PreProjectRegisterRequest request, LogUserInfoVo user) {
        List<String> cIds = userProducerFeign.getAllChildDepCompany(user.getId());
        if (CollectionUtils.isEmpty(cIds)) cIds = new ArrayList<>();
        //当前用户单位
        if (!cIds.contains(user.getCId())) cIds.add(user.getCId());
        QueryMap<PreProjectRegisterRequest> params = new QueryMap<>(request);
        params.put("prStatus").put("prType").put("prCode").put("prName").put("prSafetyFiling")
                .put("prCategory").put("prProjectStatus").put("prSourcesOfBunds").put("prApprovalStatus")
                //建设单位过滤
                .put("constUnitIds", cIds);
        IPage<ProjectRegisterResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ProjectRegisterResponse> datas = getPreProjectRegisterLists(params, page);
        BigDecimal prEstimatedAmountSum = baseMapper.getEstimatedAmountSum(params.getQuery());
        Map<String, Object> data = new HashMap<>();
        data.put("prEstimatedAmountSum", prEstimatedAmountSum == null ? BigDecimal.ZERO : prEstimatedAmountSum);
        data.put("data", datas);
        return BaseResponse.ok(data, (int) page.getTotal());
    }


    /**
     * 列表查询金额字段除以1W
     *
     * @param datas
     * @return
     */
    private List<ProjectRegisterResponse> amtDivision(List<ProjectRegisterResponse> datas) {
        List<ProjectRegisterResponse> models = new ArrayList<>();
        datas.forEach(model -> {
            model.setPrEstimatedAmount(MathUtil.DecimalDivisionInt(model.getPrEstimatedAmount(), 10000));
            model.setPrReportedAmount(MathUtil.DecimalDivisionInt(model.getPrReportedAmount(), 10000));
            model.setPrConstructionCost(MathUtil.DecimalDivisionInt(model.getPrConstructionCost(), 10000));
            model.setPrContractPrice(MathUtil.DecimalDivisionInt(model.getPrContractPrice(), 10000));
            model.setPrPurchasePrice(MathUtil.DecimalDivisionInt(model.getPrPurchasePrice(), 10000));
            model.setPrFiscalBudget(MathUtil.DecimalDivisionInt(model.getPrFiscalBudget(), 10000));
            model.setPrApproveBudget(MathUtil.DecimalDivisionInt(model.getPrApproveBudget(), 10000));
            models.add(model);
        });
        return models;
    }

    @Override
    public BaseResponse getPreProjectRegisterById(PreProjectRegisterRequest request) {
        return getPreProjectRegisterById(request, "gw");
    }

    @Override
    public BaseResponse getPreProjectRegisterWYById(PreProjectRegisterRequest request) {
        return getPreProjectRegisterById(request, "wy");
    }


    private BaseResponse getPreProjectRegisterById(PreProjectRegisterRequest request, String unit) {
        if (ValidateUtil.isEmpty(request.getPrId())) return BaseResponse.fail(ResMsg.notReqParam);
        ProjectRegisterResponse data = baseMapper.getPreProjectRegisterById(request.getPrId());
        if (data != null) {
            data.setPrEstimatedAmount(MathUtil.DecimalDivisionInt(data.getPrEstimatedAmount(), 10000));
            data.setPrReportedAmount(MathUtil.DecimalDivisionInt(data.getPrReportedAmount(), 10000));
            data.setPrConstructionCost(MathUtil.DecimalDivisionInt(data.getPrConstructionCost(), 10000));
            data.setPrContractPrice(MathUtil.DecimalDivisionInt(data.getPrContractPrice(), 10000));
            data.setPrPurchasePrice(MathUtil.DecimalDivisionInt(data.getPrPurchasePrice(), 10000));
            data.setPrFiscalBudget(MathUtil.DecimalDivisionInt(data.getPrFiscalBudget(), 10000));
            data.setPrApproveBudget(MathUtil.DecimalDivisionInt(data.getPrApproveBudget(), 10000));
            if (unit.equals("wy")) {//万元位
                List<ProSourcesFundsRegModel> funds = data.getProSourcesFundsRegs();
                List<ProSourcesFundsRegModel> newFds = new ArrayList<>();
                funds.forEach(fund -> {
                    fund.setFsMoney(MathUtil.DecimalDivisionInt(fund.getFsMoney(), 10000));
                    newFds.add(fund);
                });
                data.setProSourcesFundsRegs(newFds);
            }

            List<PreProjectRegisterDtlModel> child = preProjectRegisterDtlService.getPreProjectRegisterDtlByPrId(data.getPrId());
            data.setPreProjectRegisterDtl(child);
        }
        return BaseResponse.ok(data);
    }

}
