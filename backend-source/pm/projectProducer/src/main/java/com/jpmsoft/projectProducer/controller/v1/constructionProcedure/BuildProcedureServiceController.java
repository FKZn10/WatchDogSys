package com.jpmsoft.projectProducer.controller.v1.constructionProcedure;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ConstructionProcedure.*;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.constructionProcedure.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/buildProcedure")
public class BuildProcedureServiceController {

    @Resource
    private IAdministrativeLicensingService administrativeLicensingService;
    @Resource
    private IApprovalOfGreeningSchemeService approvalOfGreeningSchemeService;
    @Resource
    private IBuildingConstructionPermitsService buildingConstructionPermitsService;
    @Resource
    private ILandUsePlanningService landUsePlanningService;
    @Resource
    private INoticeOfConstructionUnitService noticeOfConstructionUnitService;
    @Resource
    private IPlanningConditionsService planningConditionsService;
    @Resource
    private IPlanningSchemeApprovalService planningSchemeApprovalService;
    @Resource
    private IProjectBuildOpinionService projectBuildOpinionService;
    @Resource
    private IProjectUseLandOpinionService projectUseLandOpinionService;
    @Resource
    private ISupervisionUnitWonService supervisionUnitWonService;

    /**
     * 行政许可
     */
    @TenantId
    @PostMapping("/createAdministrativeLicensing")
    public BaseResponse createAdministrativeLicensing(@RequestBody AdministrativeLicensingRequest map) {
        log.info("createAdministrativeLicensing");
        return administrativeLicensingService.createAdministrativeLicensing(map);
    }

    @TenantId
    @PostMapping("/getAdministrativeLicensingList")
    public BaseResponse getAdministrativeLicensingList(@RequestBody AdministrativeLicensingRequest page) {
        log.info("getAdministrativeLicensingList");
        return administrativeLicensingService.getAdministrativeLicensingList(page);
    }


    @GetMapping("/getAdministrativeLicensingInfoById")
    public BaseResponse getAdministrativeLicensingInfoById(@RequestParam String map) {
        log.info("getAdministrativeLicensingInfoById");
        return administrativeLicensingService.getAdministrativeLicensingInfoById(map);
    }

    @GetMapping("/deleteAdministrativeLicensing")
    public BaseResponse deleteAdministrativeLicensingById(@RequestParam String map) {
        log.info("deleteAdministrativeLicensing");
        return administrativeLicensingService.deleteAdministrativeLicensing(map);
    }

    @TenantId
    @PostMapping("/updateAdministrativeLicensing")
    public BaseResponse updateAdministrativeLicensing(@RequestBody AdministrativeLicensingRequest map) {
        log.info("updateAdministrativeLicensing");
        return administrativeLicensingService.updateAdministrativeLicensing(map);
    }

    @PostMapping("/updateApproveStatusAdministrativeLicensing")
    public BaseResponse updateApproveStatusAdministrativeLicensing(@RequestBody AdministrativeLicensingRequest map) {
        log.info("updateApproveStatusAdministrativeLicensing");
        return administrativeLicensingService.updateApproveStatusAdministrativeLicensing(map);
    }

    /**
     * 绿化方案审批
     */
    @TenantId
    @PostMapping("/createApprovalOfGreeningScheme")
    public BaseResponse createApprovalOfGreeningScheme(@RequestBody ApprovalOfGreeningSchemeRequest map) {
        log.info("createApprovalOfGreeningScheme");
        return approvalOfGreeningSchemeService.createApprovalOfGreeningScheme(map);
    }

    @TenantId
    @PostMapping("/getApprovalOfGreeningSchemeList")
    public BaseResponse getApprovalOfGreeningSchemeList(@RequestBody ApprovalOfGreeningSchemeRequest page) {
        log.info("getApprovalOfGreeningSchemeList");
        return approvalOfGreeningSchemeService.getApprovalOfGreeningSchemeList(page);
    }

    @GetMapping("/getApprovalOfGreeningSchemeInfoById")
    public BaseResponse getApprovalOfGreeningSchemeInfoById(@RequestParam String map) {
        log.info("getApprovalOfGreeningSchemeInfoById");
        return approvalOfGreeningSchemeService.getApprovalOfGreeningSchemeInfoById(map);
    }

    @GetMapping("/deleteApprovalOfGreeningScheme")
    public BaseResponse deleteApprovalOfGreeningScheme(@RequestParam String map) {
        log.info("deleteApprovalOfGreeningScheme");
        return approvalOfGreeningSchemeService.deleteApprovalOfGreeningScheme(map);
    }

    @TenantId
    @PostMapping("/updateApprovalOfGreeningScheme")
    public BaseResponse updateApprovalOfGreeningScheme(@RequestBody ApprovalOfGreeningSchemeRequest map) {
        log.info("updateApprovalOfGreeningScheme");
        return approvalOfGreeningSchemeService.updateApprovalOfGreeningScheme(map);
    }

    @PostMapping("/updateApproveStatusApprovalOfGreeningScheme")
    public BaseResponse updateApproveStatusApprovalOfGreeningScheme(@RequestBody ApprovalOfGreeningSchemeRequest map) {
        log.info("updateApproveStatusApprovalOfGreeningScheme");
        return approvalOfGreeningSchemeService.updateApproveStatusApprovalOfGreeningScheme(map);
    }

    /**
     * 建筑工程施工许可证
     */
    @TenantId
    @PostMapping("/createBuildingConstructionPermits")
    public BaseResponse createBuildingConstructionPermits(@RequestBody BuildingConstructionPermitsRequest map) {
        log.info("createBuildingConstructionPermits");
        return buildingConstructionPermitsService.createBuildingConstructionPermits(map);
    }

    @TenantId
    @PostMapping("/getBuildingConstructionPermitsList")
    public BaseResponse getBuildingConstructionPermitsList(@RequestBody BuildingConstructionPermitsRequest map) {
        log.info("getBuildingConstructionPermitsList");
        return buildingConstructionPermitsService.getBuildingConstructionPermitsList(map);
    }

    @GetMapping("/getBuildingConstructionPermitsInfoById")
    public BaseResponse getBuildingConstructionPermitsInfoById(@RequestParam String map) {
        log.info("getBuildingConstructionPermitsInfoById");
        return buildingConstructionPermitsService.getBuildingConstructionPermitsInfoById(map);
    }

    @GetMapping("/deleteBuildingConstructionPermits")
    public BaseResponse deleteBuildingConstructionPermits(@RequestParam String map) {
        log.info("deleteBuildingConstructionPermits");
        return buildingConstructionPermitsService.deleteBuildingConstructionPermits(map);
    }

    @TenantId
    @PostMapping("/updateBuildingConstructionPermits")
    public BaseResponse updateBuildingConstructionPermits(@RequestBody BuildingConstructionPermitsRequest map) {
        log.info("updateBuildingConstructionPermits");
        return buildingConstructionPermitsService.updateBuildingConstructionPermits(map);
    }

    @PostMapping("/updateApproveStatusBuildingConstructionPermits")
    public BaseResponse updateApproveStatusBuildingConstructionPermits(@RequestBody BuildingConstructionPermitsRequest map) {
        log.info("updateApproveStatusBuildingConstructionPermits");
        return buildingConstructionPermitsService.updateApproveStatusBuildingConstructionPermits(map);
    }

    /**
     * 用地规划许可证
     */
    @TenantId
    @PostMapping("/createLandUsePlanning")
    public BaseResponse createLandUsePlanning(@RequestBody LandUsePlanningRequest map) {
        log.info("createLandUsePlanning");
        return landUsePlanningService.createLandUsePlanning(map);
    }

    @TenantId
    @PostMapping("/getLandUsePlanningList")
    public BaseResponse getLandUsePlanningList(@RequestBody LandUsePlanningRequest map) {
        log.info("getLandUsePlanningList");
        return landUsePlanningService.getLandUsePlanningList(map);
    }

    @GetMapping("/getLandUsePlanningInfoById")
    public BaseResponse getLandUsePlanningInfoById(@RequestParam String map) {
        log.info("getLandUsePlanningInfoById");
        return landUsePlanningService.getLandUsePlanningInfoById(map);
    }

    @GetMapping("/deleteLandUsePlanning")
    public BaseResponse deleteLandUsePlanning(@RequestParam String map) {
        log.info("deleteLandUsePlanning");
        return landUsePlanningService.deleteLandUsePlanning(map);
    }

    @TenantId
    @PostMapping("/updateLandUsePlanning")
    public BaseResponse updateLandUsePlanning(@RequestBody LandUsePlanningRequest map) {
        log.info("updateLandUsePlanning");
        return landUsePlanningService.updateLandUsePlanning(map);
    }

    @PostMapping("/updateApproveStatusLandUsePlanning")
    public BaseResponse updateApproveStatusLandUsePlanning(@RequestBody LandUsePlanningRequest map) {
        log.info("updateApproveStatusLandUsePlanning");
        return landUsePlanningService.updateApproveStatusLandUsePlanning(map);
    }

    /**
     * 施工单位中标通知书
     */
    @TenantId
    @PostMapping("/createNoticeOfConstructionUnit")
    public BaseResponse createNoticeOfConstructionUnit(@RequestBody NoticeOfConstructionUnitRequest map) {
        log.info("createNoticeOfConstructionUnit");
        return noticeOfConstructionUnitService.createNoticeOfConstructionUnit(map);
    }

    @TenantId
    @PostMapping("/getNoticeOfConstructionUnitList")
    public BaseResponse getNoticeOfConstructionUnitList(@RequestBody NoticeOfConstructionUnitRequest page) {
        log.info("getNoticeOfConstructionUnitList");
        return noticeOfConstructionUnitService.getNoticeOfConstructionUnitList(page);
    }

    @GetMapping("/getNoticeOfConstructionUnitInfoById")
    public BaseResponse getNoticeOfConstructionUnitInfoById(@RequestParam String map) {
        log.info("getNoticeOfConstructionUnitInfoById");
        return noticeOfConstructionUnitService.getNoticeOfConstructionUnitInfoById(map);
    }

    @GetMapping("/deleteNoticeOfConstructionUnit")
    public BaseResponse deleteNoticeOfConstructionUnit(@RequestParam String map) {
        log.info("deleteNoticeOfConstructionUnit");
        return noticeOfConstructionUnitService.deleteNoticeOfConstructionUnit(map);
    }

    @TenantId
    @PostMapping("/updateNoticeOfConstructionUnit")
    public BaseResponse updateNoticeOfConstructionUnit(@RequestBody NoticeOfConstructionUnitRequest map) {
        log.info("updateNoticeOfConstructionUnit");
        return noticeOfConstructionUnitService.updateNoticeOfConstructionUnit(map);
    }

    @PostMapping("/updateApproveStatusNoticeOfConstructionUnit")
    public BaseResponse updateApproveStatusNoticeOfConstructionUnit(@RequestBody NoticeOfConstructionUnitRequest map) {
        log.info("updateApproveStatusNoticeOfConstructionUnit");
        return noticeOfConstructionUnitService.updateApproveStatusNoticeOfConstructionUnit(map);
    }

    /**
     * 规划条件
     */
    @TenantId
    @PostMapping("/createPlanningConditions")
    public BaseResponse createPlanningConditions(@RequestBody PlanningConditionsRequest map) {
        log.info("createPlanningConditions");
        return planningConditionsService.createPlanningConditions(map);
    }

    @TenantId
    @PostMapping("/getPlanningConditionsList")
    public BaseResponse getPlanningConditionsList(@RequestBody PlanningConditionsRequest map) {
        log.info("getPlanningConditionsList");
        return planningConditionsService.getPlanningConditionsList(map);
    }

    @GetMapping("/getPlanningConditionsInfoById")
    public BaseResponse getPlanningConditionsInfoById(@RequestParam String map) {
        log.info("getPlanningConditionsInfoById");
        return planningConditionsService.getPlanningConditionsInfoById(map);
    }

    @GetMapping("/deletePlanningConditions")
    public BaseResponse deletePlanningConditions(@RequestParam String map) {
        log.info("deletePlanningConditions");
        return planningConditionsService.deletePlanningConditions(map);
    }

    @TenantId
    @PostMapping("/updatePlanningConditions")
    public BaseResponse updatePlanningConditions(@RequestBody PlanningConditionsRequest map) {
        log.info("updatePlanningConditions");
        return planningConditionsService.updatePlanningConditions(map);
    }

    @PostMapping("/updateApproveStatusPlanningConditions")
    public BaseResponse updateApproveStatusPlanningConditions(@RequestBody PlanningConditionsRequest map) {
        log.info("updateApproveStatusPlanningConditions");
        return planningConditionsService.updateApproveStatusPlanningConditions(map);
    }

    /**
     * 规划方案批复
     */
    @TenantId
    @PostMapping("/createPlanningSchemeApproval")
    public BaseResponse createPlanningSchemeApproval(@RequestBody PlanningSchemeApprovalRequest map) {
        log.info("createPlanningSchemeApproval");
        return planningSchemeApprovalService.createPlanningSchemeApproval(map);
    }

    @TenantId
    @PostMapping("/getPlanningSchemeApprovalList")
    public BaseResponse getPlanningSchemeApprovalList(@RequestBody PlanningSchemeApprovalRequest page) {
        log.info("getPlanningSchemeApprovalList");
        return planningSchemeApprovalService.getPlanningSchemeApprovalList(page);
    }

    @GetMapping("/getPlanningSchemeApprovalListInfoById")
    public BaseResponse getPlanningSchemeApprovalListInfoById(@RequestParam String map) {
        log.info("getPlanningSchemeApprovalListInfoById");
        return planningSchemeApprovalService.getPlanningSchemeApprovalListInfoById(map);
    }

    @GetMapping("/deletePlanningSchemeApproval")
    public BaseResponse deletePlanningSchemeApprovalList(@RequestParam String map) {
        log.info("deletePlanningSchemeApproval");
        return planningSchemeApprovalService.deletePlanningSchemeApproval(map);
    }

    @TenantId
    @PostMapping("/updatePlanningSchemeApproval")
    public BaseResponse updatePlanningSchemeApproval(@RequestBody PlanningSchemeApprovalRequest map) {
        log.info("updatePlanningSchemeApproval");
        return planningSchemeApprovalService.updatePlanningSchemeApproval(map);
    }

    @PostMapping("/updateApproveStatusPlanningSchemeApproval")
    public BaseResponse updateApproveStatusPlanningSchemeApproval(@RequestBody PlanningSchemeApprovalRequest map) {
        log.info("updateApproveStatusPlanningSchemeApproval");
        return planningSchemeApprovalService.updateApproveStatusPlanningSchemeApproval(map);
    }

    /**
     * 建设项目选址意见书
     */
    @TenantId
    @PostMapping("/createProjectBuildOpinion")
    public BaseResponse createProjectBuildOpinion(@RequestBody ProjectBuildOpinionRequest map) {
        log.info("createProjectBuildOpinion");
        return projectBuildOpinionService.createProjectBuildOpinion(map);
    }

    @TenantId
    @PostMapping("/getProjectBuildOpinionList")
    public BaseResponse getProjectBuildOpinionList(@RequestBody ProjectBuildOpinionRequest map) {
        log.info("getProjectBuildOpinionList");
        return projectBuildOpinionService.getProjectBuildOpinionList(map);
    }

    @GetMapping("/getProjectBuildOpinionInfoById")
    public BaseResponse getProjectBuildOpinionInfoById(@RequestParam String map) {
        log.info("getProjectBuildOpinionInfoById");
        return projectBuildOpinionService.getProjectBuildOpinionInfoById(map);
    }

    @GetMapping("/deleteProjectBuildOpinion")
    public BaseResponse deleteProjectBuildOpinion(@RequestParam String map) {
        log.info("deleteProjectBuildOpinion");
        return projectBuildOpinionService.deleteProjectBuildOpinion(map);
    }

    @TenantId
    @PostMapping("/updateProjectBuildOpinion")
    public BaseResponse updateProjectBuildOpinion(@RequestBody ProjectBuildOpinionRequest map) {
        log.info("updateProjectBuildOpinion");
        return projectBuildOpinionService.updateProjectBuildOpinion(map);
    }

    @PostMapping("/updateApproveStatusProjectBuildOpinion")
    public BaseResponse updateApproveStatusProjectBuildOpinion(@RequestBody ProjectBuildOpinionRequest map) {
        log.info("updateApproveStatusProjectBuildOpinion");
        return projectBuildOpinionService.updateApproveStatusProjectBuildOpinion(map);
    }

    /**
     * .项目用地预审意见
     */
    @TenantId
    @PostMapping("/createProjectUseLandOpinion")
    public BaseResponse createProjectUseLandOpinion(@RequestBody ProjectUseLandOpinionRequest map) {
        log.info("createProjectUseLandOpinion");
        return projectUseLandOpinionService.createProjectUseLandOpinion(map);
    }

    @TenantId
    @PostMapping("/getProjectUseLandOpinionList")
    public BaseResponse getProjectUseLandOpinionList(@RequestBody ProjectUseLandOpinionRequest map) {
        log.info("getProjectUseLandOpinionList");
        return projectUseLandOpinionService.getProjectUseLandOpinionList(map);
    }

    @GetMapping("/getProjectUseLandOpinionInfoById")
    public BaseResponse getProjectUseLandOpinionInfoById(@RequestParam String map) {
        log.info("getProjectUseLandOpinionInfoById");
        return projectUseLandOpinionService.getProjectUseLandOpinionInfoById(map);
    }

    @GetMapping("/deleteProjectUseLandOpinion")
    public BaseResponse deleteProjectUseLandOpinion(@RequestParam String map) {
        log.info("deleteProjectUseLandOpinion");
        return projectUseLandOpinionService.deleteProjectUseLandOpinion(map);
    }

    @TenantId
    @PostMapping("/updateProjectUseLandOpinion")
    public BaseResponse updateProjectUseLandOpinion(@RequestBody ProjectUseLandOpinionRequest map) {
        log.info("updateProjectUseLandOpinion");
        return projectUseLandOpinionService.updateProjectUseLandOpinion(map);
    }

    @PostMapping("/updateApproveStatusProjectUseLandOpinion")
    public BaseResponse updateApproveStatusProjectUseLandOpinion(@RequestBody ProjectUseLandOpinionRequest map) {
        log.info("updateApproveStatusProjectUseLandOpinion");
        return projectUseLandOpinionService.updateApproveStatusProjectUseLandOpinion(map);
    }

    /**
     * 监理单位中标通知书
     */
    @TenantId
    @PostMapping("/createSupervisionUnitWon")
    public BaseResponse createSupervisionUnitWon(@RequestBody SupervisionUnitWonRequest map) {
        log.info("createSupervisionUnitWon");
        return supervisionUnitWonService.createSupervisionUnitWon(map);
    }

    @TenantId
    @PostMapping("/getSupervisionUnitWonList")
    public BaseResponse getSupervisionUnitWonList(@RequestBody SupervisionUnitWonRequest page) {
        log.info("getSupervisionUnitWonList");
        return supervisionUnitWonService.getSupervisionUnitWonList(page);
    }

    @GetMapping("/getSupervisionUnitWonInfoById")
    public BaseResponse getSupervisionUnitWonInfoById(@RequestParam String map) {
        log.info("getSupervisionUnitWonInfoById");
        return supervisionUnitWonService.getSupervisionUnitWonInfoById(map);
    }

    @GetMapping("/deleteSupervisionUnitWon")
    public BaseResponse deleteSupervisionUnitWon(@RequestParam String map) {
        log.info("deleteSupervisionUnitWon");
        return supervisionUnitWonService.deleteSupervisionUnitWon(map);
    }

    @TenantId
    @PostMapping("/updateSupervisionUnitWon")
    public BaseResponse updateSupervisionUnitWon(@RequestBody SupervisionUnitWonRequest map) {
        log.info("updateSupervisionUnitWon");
        return supervisionUnitWonService.updateSupervisionUnitWon(map);
    }

    @PostMapping("/updateApproveStatusSupervisionUnitWon")
    public BaseResponse updateApproveStatusSupervisionUnitWon(@RequestBody SupervisionUnitWonRequest map) {
        log.info("updateApproveStatusSupervisionUnitWon");
        return supervisionUnitWonService.updateApproveStatusSupervisionUnitWon(map);
    }
}
