package com.jpmsoft.projectProducer.service.NewOA.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.NewOA.PreProjectRegister_NewOaRequest;
import com.jpmsoft.common.model.request.project.PreProjectRegisterRequest;
import com.jpmsoft.common.model.requestAndResponse.Project.ProSourcesFundsRegModel;
import com.jpmsoft.common.model.response.FileTemplateTree;
import com.jpmsoft.common.model.response.Project.ProjectRegisterResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.feign.UserProducerFeign;
import com.jpmsoft.projectProducer.mapper.NewOA.PreProjectRegister_NewOaMapper;
import com.jpmsoft.projectProducer.pojo.entity.NewOA.PreProjectRegister_NewOa;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectRegister;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.NewOA.IPreProjectRegister_NewOaService;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.project.IPreProjectService;
import com.jpmsoft.projectProducer.service.project.IProSourcesFundsRegService;
import com.jpmsoft.projectProducer.service.project.IProSourcesFundsService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import com.jpmsoft.projectProducer.utils.MathUtil;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

public class PreProjectRegister_NewOaImpl extends ServiceImpl<PreProjectRegister_NewOaMapper, PreProjectRegister_NewOa> implements IPreProjectRegister_NewOaService {

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
    private IProSourcesFundsService proSourcesFundsService;
    @Resource
    private UserProducerFeign userProducerFeign;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse CreateOrUpdatePreProjectRegister_NewOa(PreProjectRegister_NewOaRequest request) {
        PreProjectRegister_NewOa entity;
        String codeRule = "";
        if (ValidateUtil.isEmpty(request.getPrId())) {
            entity = new PreProjectRegister_NewOa();
            if (apiUtilsService.sequenceRestEnable("PreProjectRegister")) {
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
        QueryWrapper<PreProjectRegister_NewOa> wrapper = new QueryWrapper<>();
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
        entity.setPrConstructionUnitId(request.getPrConstructionUnitId());
        entity.setPrPlanStart(request.getPrPlanStart());
        entity.setPrPlanEnd(request.getPrPlanEnd());
        entity.setPrPmName(request.getPrPmName());
        entity.setPrPmTel(request.getPrPmTel());
        entity.setPrEstimateFile(request.getPrEstimateFile());
        entity.setPrInvNature(request.getPrInvNature());
        entity.setPrBuildingUnit(request.getPrBuildingUnit());
        entity.setPrMajorType(request.getPrMajorType());
        entity.setPrCompetentDepartment(request.getPrCompetentDepartment());
        entity.setPrInterventionYear(request.getPrInterventionYear());
        entity.setPrProjectStage(request.getPrProjectStage());
        entity.setPrSafetyFiling(request.getPrSafetyFiling());
        entity.setCplId(request.getCplId());
        entity.setPrApplyName(request.getPrApplyName());
        entity.setPrFiscalBudget(request.getPrFiscalBudget());
        entity.setTeId(request.getTeId());
        entity.setPrAddress(request.getPrAddress());
        entity.setPrCampus(request.getPrCampus());
        entity.setPrYear(request.getPrYear());
        entity.setPrOpinionFile(request.getPrOpinionFile());
        entity.setPrPlanFile(request.getPrPlanFile());
        entity.setPrFeasibilityFile(request.getPrFeasibilityFile());
        entity.setPrApproveFile(request.getPrApproveFile());
        if (ValidateUtil.isEmpty(request.getPrId())) {
            entity.setPrId(GenerateUUID.getUUID());
            entity.setPrCreateTime(new Date());
            entity.setPrApplicantId(request.getPrApplicantId());
            entity.setPrCreateUserName(request.getPrCreateUserName());
            entity.setPrStatus(ValidateUtil.isEmpty(request.getPrStatus()) ? "1" : request.getPrStatus());
            entity.setPrApprovalStatus(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getPrId(), request.gettId(), entity.getPrApprovalStatus() + "", entity.getPrApplicantId(), entity.getPrCreateUserName(), entity.getPrCreateTime(), null, request.getFileTitle(), request.getFileCode(), "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getPrCommitteeApprovaDocument(), request.getPrCityLeaderInstructions(), request.getPrOther(), request.getPrCityLeaderMeetingMinutes(), request.getPrApprovaDocument(), request.getPrLeaderMeetingMinutes(), request.getPrLeaderInstructions(), request.getPrEstimateFile(), request.getPrOpinionFile(), request.getPrPlanFile(), request.getPrFeasibilityFile(), request.getPrApproveFile()), entity.getPrId());
        } else {
            if (JudgeUtil.isApprovedState(entity.getPrApprovalStatus()))
                return BaseResponse.fail(ResMsg.stateCannotAct);
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getPrId(), request.gettId(), entity.getPrApprovalStatus() + "", entity.getPrApplicantId(), entity.getPrCreateUserName(), entity.getPrCreateTime(), null, request.getFileTitle(), request.getFileCode(), "update", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getPrCommitteeApprovaDocument(), request.getPrCityLeaderInstructions(), request.getPrOther(), request.getPrCityLeaderMeetingMinutes(), request.getPrApprovaDocument(), request.getPrLeaderMeetingMinutes(), request.getPrLeaderInstructions(), request.getPrEstimateFile(), request.getPrOpinionFile(), request.getPrPlanFile(), request.getPrFeasibilityFile(), request.getPrApproveFile())
                    , Arrays.asList(request.getFileIds(), request.getPrCommitteeApprovaDocument(), request.getPrCityLeaderInstructions(), request.getPrOther(), request.getPrCityLeaderMeetingMinutes(), request.getPrApprovaDocument(), request.getPrLeaderMeetingMinutes(), request.getPrLeaderInstructions(), request.getPrEstimateFile(), request.getPrOpinionFile(), request.getPrPlanFile(), request.getPrFeasibilityFile(), request.getPrApproveFile()), entity.getPrId());
        }
        //proSourcesFundsRegService.createOrUpdateProSourcesFundsReg(request.getProSourcesFundsRegs(), entity.getPrId());
        if (ValidateUtil.isNotEmpty(codeRule)) apiUtilsService.refershAutoNo(codeRule, request);
        return BaseResponse.ok(entity.getPrId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deletePreProjectRegister_NewOa(PreProjectRegister_NewOaRequest request) {
        PreProjectRegister_NewOa entity;
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
    public BaseResponse approvalPreProjectRegister_NewOa(PreProjectRegister_NewOaRequest request) {
        FileTemplateTree temp = userProducerFeign.getFileTemplateByCode("Project");
        if (ValidateUtil.isNull(temp)) return BaseResponse.fail(String.format(ResMsg.notFoundFileTmp, "Project"));
        PreProjectRegister_NewOa entity;
        SysFile fileentity;
        if (ValidateUtil.isEmpty(request.getPrId())) return BaseResponse.fail(ResMsg.notReqParam);
        else {
            entity = baseMapper.selectById(request.getPrId());
            if (entity == null) return BaseResponse.fail(ResMsg.infoNotExist);

            fileentity = sysFileService.getById(entity.getPrId());
        }
        entity.setPrLandApproval(request.getPrLandApproval());
        entity.setPrApprovalStatus(Integer.parseInt(fileentity.getFileflowstatus()));
        entity.setPrApprovalTime(new Date());
        //entity.setPrStatus(fileentity.getFileflowstatus());
        baseMapper.updateById(entity);

        if (Objects.equals(entity.getPrApprovalStatus(), ApprovalStatusEnum.Already_Over.getIndex())) {
            if (!preProjectService.registerProjectExist(entity.getPrId())) {
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
                preProjectService.insertPreProject(projectPojo);
                proSourcesFundsService.copyDataByFundsReg(entity.getPrId(), projectPojo.getpId());
                sysFileAnnexService.copyFileAnnex2NewRelation(Arrays.asList(entity.getPrCommitteeApprovaDocument(), entity.getPrCityLeaderMeetingMinutes(), entity.getPrCityLeaderInstructions(), entity.getPrOther(), entity.getPrApprovaDocument(), entity.getPrLeaderMeetingMinutes(), entity.getPrLeaderInstructions(), entity.getPrEstimateFile(), entity.getPrOpinionFile(), entity.getPrPlanFile(), entity.getPrFeasibilityFile(), entity.getPrApproveFile())
                        , Arrays.asList(projectPojo.getpCommitteeApprovaDocument(), projectPojo.getpCityLeaderMeetingMinutes(), projectPojo.getpCityLeaderInstructions(), projectPojo.getpOther(), projectPojo.getpApprovaDocument(), projectPojo.getpLeaderMeetingMinutes(), projectPojo.getpLeaderInstructions(), projectPojo.getpEstimateFile(), projectPojo.getpOpinionFile(), projectPojo.getpPlanFile(), projectPojo.getpFeasibilityFile(), projectPojo.getpApproveFile()), projectPojo.getpId());
                sysFileService.saveFile(projectPojo.getpId(), temp.gettId(), projectPojo.getState() + "", projectPojo.getpCreateUserId(), projectPojo.getpCreateUserName()
                        , projectPojo.getpCreateTime(), projectPojo.getpId(), temp.gettName(), temp.gettCode(), "create",
                        GenerateUUID.getUUID(), null, temp.gettCode(), null);
            }
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getPreProjectRegister_NewOaList(PreProjectRegister_NewOaRequest request) {
        QueryMap<PreProjectRegister_NewOaRequest> params = setFilter(request);
        IPage<ProjectRegisterResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ProjectRegisterResponse> datas = baseMapper.getPreProjectRegister_NewOaList(params.getQuery(), page);
        return BaseResponse.ok(amtDivision(datas), (int) page.getTotal());
    }

    @Override
    public BaseResponse getPreProjectRegister_NewOaStatisticsList(PreProjectRegister_NewOaRequest request) {
        QueryMap<PreProjectRegister_NewOaRequest> params = setFilter(request);
        IPage<ProjectRegisterResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ProjectRegisterResponse> datas = baseMapper.getPreProjectRegister_NewOaList(params.getQuery(), page);
        BigDecimal prEstimatedAmountSum = baseMapper.getEstimatedAmountSum(params.getQuery());
        Map<String, Object> data = new HashMap<>();
        data.put("prEstimatedAmountSum", prEstimatedAmountSum == null ? BigDecimal.ZERO : prEstimatedAmountSum);
        data.put("data", datas);
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    private QueryMap<PreProjectRegister_NewOaRequest> setFilter(PreProjectRegister_NewOaRequest request) {
        QueryMap<PreProjectRegister_NewOaRequest> params = new QueryMap<>(request);
        params.put("prStatus").put("prType").put("prCode").put("prName").put("prSafetyFiling").put("prCategory").put("prProjectStatus").put("prSourcesOfBunds").put("prApprovalStatus").put("prCreateUserName").put("prCampus");
        return params;
    }

    @Override
    public BaseResponse getPreProjectRegister_NewOaByDepCompany(PreProjectRegister_NewOaRequest request, LogUserInfoVo user) {
        List<String> cIds = userProducerFeign.getAllChildDepCompany(user.getId());
        if (CollectionUtils.isEmpty(cIds)) cIds = new ArrayList<>();
        //当前用户单位
        if (!cIds.contains(user.getCId())) cIds.add(user.getCId());
        QueryMap<PreProjectRegister_NewOaRequest> params = new QueryMap<>(request);
        params.put("prStatus").put("prType").put("prCode").put("prName").put("prSafetyFiling")
                .put("prCategory").put("prProjectStatus").put("prSourcesOfBunds")
                //建设单位过滤
                .put("constUnitIds", cIds);
        IPage<ProjectRegisterResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ProjectRegisterResponse> datas = baseMapper.getPreProjectRegister_NewOaList(params.getQuery(), page);
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
            models.add(model);
        });
        return models;
    }

    @Override
    public BaseResponse getPreProjectRegister_NewOaById(PreProjectRegister_NewOaRequest request) {
        return getPreProjectRegister_NewOaById(request, "gw");
    }

    @Override
    public BaseResponse getPreProjectRegister_NewOaWYById(PreProjectRegister_NewOaRequest request) {
        return getPreProjectRegister_NewOaById(request, "wy");
    }


    private BaseResponse getPreProjectRegister_NewOaById(PreProjectRegister_NewOaRequest request, String unit) {
        if (ValidateUtil.isEmpty(request.getPrId())) return BaseResponse.fail(ResMsg.notReqParam);
        ProjectRegisterResponse data = baseMapper.getPreProjectRegister_NewOaById(request.getPrId());
        if (data != null) {
            data.setPrEstimatedAmount(MathUtil.DecimalDivisionInt(data.getPrEstimatedAmount(), 10000));
            data.setPrReportedAmount(MathUtil.DecimalDivisionInt(data.getPrReportedAmount(), 10000));
            data.setPrConstructionCost(MathUtil.DecimalDivisionInt(data.getPrConstructionCost(), 10000));
            data.setPrContractPrice(MathUtil.DecimalDivisionInt(data.getPrContractPrice(), 10000));
            data.setPrPurchasePrice(MathUtil.DecimalDivisionInt(data.getPrPurchasePrice(), 10000));
            if (unit.equals("wy")) {//万元位
                List<ProSourcesFundsRegModel> funds = data.getProSourcesFundsRegs();
                List<ProSourcesFundsRegModel> newFds = new ArrayList<>();
                funds.forEach(fund -> {
                    fund.setFsMoney(MathUtil.DecimalDivisionInt(fund.getFsMoney(), 10000));
                    newFds.add(fund);
                });
                data.setProSourcesFundsRegs(newFds);
            }
        }
        return BaseResponse.ok(data);
    }

}
