package com.jpmsoft.projectConsumer.controller.v1.constructionProcedure;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ConstructionProcedure.*;
import com.jpmsoft.projectConsumer.feign.BuildProcedureFegin;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@Api(tags = "建设程序定制表单接口", value = "建设程序定制表单接口")
@RequestMapping("/v1/buildProcedure")
public class BuildProcedureServiceController {
    @Autowired
    private BuildProcedureFegin buildProcedureFegin;

    /**
     * 行政许可
     */
    @PostMapping("/createAdministrativeLicensing")
    public BaseResponse createAdministrativeLicensing(@RequestBody AdministrativeLicensingRequest map) {
        log.info("createAdministrativeLicensing");
        return buildProcedureFegin.createAdministrativeLicensing(map);
    }

    @PostMapping("/getAdministrativeLicensingList")
    public BaseResponse getAdministrativeLicensingList(@RequestBody AdministrativeLicensingRequest page) {
        log.info("getAdministrativeLicensingList");
        return buildProcedureFegin.getAdministrativeLicensingList(page);
    }


    @GetMapping("/getAdministrativeLicensingInfoById")
    public BaseResponse getAdministrativeLicensingInfoById(@RequestParam String map) {
        log.info("getAdministrativeLicensingInfoById");
        return buildProcedureFegin.getAdministrativeLicensingInfoById(map);
    }

    @GetMapping("/deleteAdministrativeLicensing")
    public BaseResponse deleteAdministrativeLicensingById(@RequestParam String map) {
        log.info("deleteAdministrativeLicensing");
        return buildProcedureFegin.deleteAdministrativeLicensing(map);
    }

    @PostMapping("/updateAdministrativeLicensing")
    public BaseResponse updateAdministrativeLicensing(@RequestBody AdministrativeLicensingRequest map) {
        log.info("updateAdministrativeLicensing");
        return buildProcedureFegin.updateAdministrativeLicensing(map);
    }

    @PostMapping("/updateApproveStatusAdministrativeLicensing")
    public BaseResponse updateApproveStatusAdministrativeLicensing(@RequestBody AdministrativeLicensingRequest map) {
        log.info("updateApproveStatusAdministrativeLicensing");
        return buildProcedureFegin.updateApproveStatusAdministrativeLicensing(map);
    }

    /**
     * 绿化方案审批
     */
    @PostMapping("/createApprovalOfGreeningScheme")
    public BaseResponse createApprovalOfGreeningScheme(@RequestBody ApprovalOfGreeningSchemeRequest map) {
        log.info("createApprovalOfGreeningScheme");
        return buildProcedureFegin.createApprovalOfGreeningScheme(map);
    }

    @PostMapping("/getApprovalOfGreeningSchemeList")
    public BaseResponse getApprovalOfGreeningSchemeList(@RequestBody ApprovalOfGreeningSchemeRequest page) {
        log.info("getApprovalOfGreeningSchemeList");
        return buildProcedureFegin.getApprovalOfGreeningSchemeList(page);
    }

    @GetMapping("/getApprovalOfGreeningSchemeInfoById")
    public BaseResponse getApprovalOfGreeningSchemeInfoById(@RequestParam String map) {
        log.info("getApprovalOfGreeningSchemeInfoById");
        return buildProcedureFegin.getApprovalOfGreeningSchemeInfoById(map);
    }

    @GetMapping("/deleteApprovalOfGreeningScheme")
    public BaseResponse deleteApprovalOfGreeningScheme(@RequestParam String map) {
        log.info("deleteApprovalOfGreeningScheme");
        return buildProcedureFegin.deleteApprovalOfGreeningScheme(map);
    }

    @PostMapping("/updateApprovalOfGreeningScheme")
    public BaseResponse updateApprovalOfGreeningScheme(@RequestBody ApprovalOfGreeningSchemeRequest map) {
        log.info("updateApprovalOfGreeningScheme");
        return buildProcedureFegin.updateApprovalOfGreeningScheme(map);
    }

    @PostMapping("/updateApproveStatusApprovalOfGreeningScheme")
    public BaseResponse updateApproveStatusApprovalOfGreeningScheme(@RequestBody ApprovalOfGreeningSchemeRequest map) {
        log.info("updateApproveStatusApprovalOfGreeningScheme");
        return buildProcedureFegin.updateApproveStatusApprovalOfGreeningScheme(map);
    }

    /**
     * 建筑工程施工许可证
     */
    @PostMapping("/createBuildingConstructionPermits")
    public BaseResponse createBuildingConstructionPermits(@RequestBody BuildingConstructionPermitsRequest map) {
        log.info("createBuildingConstructionPermits");
        return buildProcedureFegin.createBuildingConstructionPermits(map);
    }

    @PostMapping("/getBuildingConstructionPermitsList")
    public BaseResponse getBuildingConstructionPermitsList(@RequestBody BuildingConstructionPermitsRequest map) {
        log.info("getBuildingConstructionPermitsList");
        return buildProcedureFegin.getBuildingConstructionPermitsList(map);
    }

    @GetMapping("/getBuildingConstructionPermitsInfoById")
    public BaseResponse getBuildingConstructionPermitsInfoById(@RequestParam String map) {
        log.info("getBuildingConstructionPermitsInfoById");
        return buildProcedureFegin.getBuildingConstructionPermitsInfoById(map);
    }

    @GetMapping("/deleteBuildingConstructionPermits")
    public BaseResponse deleteBuildingConstructionPermits(@RequestParam String map) {
        log.info("deleteBuildingConstructionPermits");
        return buildProcedureFegin.deleteBuildingConstructionPermits(map);
    }

    @PostMapping("/updateBuildingConstructionPermits")
    public BaseResponse updateBuildingConstructionPermits(@RequestBody BuildingConstructionPermitsRequest map) {
        log.info("updateBuildingConstructionPermits");
        return buildProcedureFegin.updateBuildingConstructionPermits(map);
    }

    @PostMapping("/updateApproveStatusBuildingConstructionPermits")
    public BaseResponse updateApproveStatusBuildingConstructionPermits(@RequestBody BuildingConstructionPermitsRequest map) {
        log.info("updateApproveStatusBuildingConstructionPermits");
        return buildProcedureFegin.updateApproveStatusBuildingConstructionPermits(map);
    }

    /**
     * 用地规划许可证
     */
    @PostMapping("/createLandUsePlanning")
    public BaseResponse createLandUsePlanning(@RequestBody LandUsePlanningRequest map) {
        log.info("createLandUsePlanning");
        return buildProcedureFegin.createLandUsePlanning(map);
    }

    @PostMapping("/getLandUsePlanningList")
    public BaseResponse getLandUsePlanningList(@RequestBody LandUsePlanningRequest map) {
        log.info("getLandUsePlanningList");
        return buildProcedureFegin.getLandUsePlanningList(map);
    }

    @GetMapping("/getLandUsePlanningInfoById")
    public BaseResponse getLandUsePlanningInfoById(@RequestParam String map) {
        log.info("getLandUsePlanningInfoById");
        return buildProcedureFegin.getLandUsePlanningInfoById(map);
    }

    @GetMapping("/deleteLandUsePlanning")
    public BaseResponse deleteLandUsePlanning(@RequestParam String map) {
        log.info("deleteLandUsePlanning");
        return buildProcedureFegin.deleteLandUsePlanning(map);
    }

    @PostMapping("/updateLandUsePlanning")
    public BaseResponse updateLandUsePlanning(@RequestBody LandUsePlanningRequest map) {
        log.info("updateLandUsePlanning");
        return buildProcedureFegin.updateLandUsePlanning(map);
    }

    @PostMapping("/updateApproveStatusLandUsePlanning")
    public BaseResponse updateApproveStatusLandUsePlanning(@RequestBody LandUsePlanningRequest map) {
        log.info("updateApproveStatusLandUsePlanning");
        return buildProcedureFegin.updateApproveStatusLandUsePlanning(map);
    }

    /**
     * 施工单位中标通知书
     */
    @PostMapping("/createNoticeOfConstructionUnit")
    public BaseResponse createNoticeOfConstructionUnit(@RequestBody NoticeOfConstructionUnitRequest map) {
        log.info("createNoticeOfConstructionUnit");
        return buildProcedureFegin.createNoticeOfConstructionUnit(map);
    }

    @PostMapping("/getNoticeOfConstructionUnitList")
    public BaseResponse getNoticeOfConstructionUnitList(@RequestBody NoticeOfConstructionUnitRequest page) {
        log.info("getNoticeOfConstructionUnitList");
        return buildProcedureFegin.getNoticeOfConstructionUnitList(page);
    }

    @GetMapping("/getNoticeOfConstructionUnitInfoById")
    public BaseResponse getNoticeOfConstructionUnitInfoById(@RequestParam String map) {
        log.info("getNoticeOfConstructionUnitInfoById");
        return buildProcedureFegin.getNoticeOfConstructionUnitInfoById(map);
    }

    @GetMapping("/deleteNoticeOfConstructionUnit")
    public BaseResponse deleteNoticeOfConstructionUnit(@RequestParam String map) {
        log.info("deleteNoticeOfConstructionUnit");
        return buildProcedureFegin.deleteNoticeOfConstructionUnit(map);
    }

    @PostMapping("/updateNoticeOfConstructionUnit")
    public BaseResponse updateNoticeOfConstructionUnit(@RequestBody NoticeOfConstructionUnitRequest map) {
        log.info("updateNoticeOfConstructionUnit");
        return buildProcedureFegin.updateNoticeOfConstructionUnit(map);
    }

    @PostMapping("/updateApproveStatusNoticeOfConstructionUnit")
    public BaseResponse updateApproveStatusNoticeOfConstructionUnit(@RequestBody NoticeOfConstructionUnitRequest map) {
        log.info("updateApproveStatusNoticeOfConstructionUnit");
        return buildProcedureFegin.updateApproveStatusNoticeOfConstructionUnit(map);
    }

    /**
     * 规划条件
     */
    @PostMapping("/createPlanningConditions")
    public BaseResponse createPlanningConditions(@RequestBody PlanningConditionsRequest map) {
        log.info("createPlanningConditions");
        return buildProcedureFegin.createPlanningConditions(map);
    }

    @PostMapping("/getPlanningConditionsList")
    public BaseResponse getPlanningConditionsList(@RequestBody PlanningConditionsRequest map) {
        log.info("getPlanningConditionsList");
        return buildProcedureFegin.getPlanningConditionsList(map);
    }

    @GetMapping("/getPlanningConditionsInfoById")
    public BaseResponse getPlanningConditionsInfoById(@RequestParam String map) {
        log.info("getPlanningConditionsInfoById");
        return buildProcedureFegin.getPlanningConditionsInfoById(map);
    }

    @GetMapping("/deletePlanningConditions")
    public BaseResponse deletePlanningConditions(@RequestParam String map) {
        log.info("deletePlanningConditions");
        return buildProcedureFegin.deletePlanningConditions(map);
    }

    @PostMapping("/updatePlanningConditions")
    public BaseResponse updatePlanningConditions(@RequestBody PlanningConditionsRequest map) {
        log.info("updatePlanningConditions");
        return buildProcedureFegin.updatePlanningConditions(map);
    }

    @PostMapping("/updateApproveStatusPlanningConditions")
    public BaseResponse updateApproveStatusPlanningConditions(@RequestBody PlanningConditionsRequest map) {
        log.info("updateApproveStatusPlanningConditions");
        return buildProcedureFegin.updateApproveStatusPlanningConditions(map);
    }

    /**
     * 规划方案批复
     */
    @PostMapping("/createPlanningSchemeApproval")
    public BaseResponse createPlanningSchemeApproval(@RequestBody PlanningSchemeApprovalRequest map) {
        log.info("createPlanningSchemeApproval");
        return buildProcedureFegin.createPlanningSchemeApproval(map);
    }

    @PostMapping("/getPlanningSchemeApprovalList")
    public BaseResponse getPlanningSchemeApprovalList(@RequestBody PlanningSchemeApprovalRequest page) {
        log.info("getPlanningSchemeApprovalList");
        return buildProcedureFegin.getPlanningSchemeApprovalList(page);
    }

    @GetMapping("/getPlanningSchemeApprovalListInfoById")
    public BaseResponse getPlanningSchemeApprovalListInfoById(@RequestParam String map) {
        log.info("getPlanningSchemeApprovalListInfoById");
        return buildProcedureFegin.getPlanningSchemeApprovalListInfoById(map);
    }

    @GetMapping("/deletePlanningSchemeApproval")
    public BaseResponse deletePlanningSchemeApprovalList(@RequestParam String map) {
        log.info("deletePlanningSchemeApproval");
        return buildProcedureFegin.deletePlanningSchemeApproval(map);
    }

    @PostMapping("/updatePlanningSchemeApproval")
    public BaseResponse updatePlanningSchemeApproval(@RequestBody PlanningSchemeApprovalRequest map) {
        log.info("updatePlanningSchemeApproval");
        return buildProcedureFegin.updatePlanningSchemeApproval(map);
    }

    @PostMapping("/updateApproveStatusPlanningSchemeApproval")
    public BaseResponse updateApproveStatusPlanningSchemeApproval(@RequestBody PlanningSchemeApprovalRequest map) {
        log.info("updateApproveStatusPlanningSchemeApproval");
        return buildProcedureFegin.updateApproveStatusPlanningSchemeApproval(map);
    }

    /**
     * 建设项目选址意见书
     */
    @PostMapping("/createProjectBuildOpinion")
    public BaseResponse createProjectBuildOpinion(@RequestBody ProjectBuildOpinionRequest map) {
        log.info("createProjectBuildOpinion");
        return buildProcedureFegin.createProjectBuildOpinion(map);
    }

    @PostMapping("/getProjectBuildOpinionList")
    public BaseResponse getProjectBuildOpinionList(@RequestBody ProjectBuildOpinionRequest map) {
        log.info("getProjectBuildOpinionList");
        return buildProcedureFegin.getProjectBuildOpinionList(map);
    }

    @GetMapping("/getProjectBuildOpinionInfoById")
    public BaseResponse getProjectBuildOpinionInfoById(@RequestParam String map) {
        log.info("getProjectBuildOpinionInfoById");
        return buildProcedureFegin.getProjectBuildOpinionInfoById(map);
    }

    @GetMapping("/deleteProjectBuildOpinion")
    public BaseResponse deleteProjectBuildOpinion(@RequestParam String map) {
        log.info("deleteProjectBuildOpinion");
        return buildProcedureFegin.deleteProjectBuildOpinion(map);
    }

    @PostMapping("/updateProjectBuildOpinion")
    public BaseResponse updateProjectBuildOpinion(@RequestBody ProjectBuildOpinionRequest map) {
        log.info("updateProjectBuildOpinion");
        return buildProcedureFegin.updateProjectBuildOpinion(map);
    }

    @PostMapping("/updateApproveStatusProjectBuildOpinion")
    public BaseResponse updateApproveStatusProjectBuildOpinion(@RequestBody ProjectBuildOpinionRequest map) {
        log.info("updateApproveStatusProjectBuildOpinion");
        return buildProcedureFegin.updateApproveStatusProjectBuildOpinion(map);
    }

    /**
     * .项目用地预审意见
     */
    @PostMapping("/createProjectUseLandOpinion")
    public BaseResponse createProjectUseLandOpinion(@RequestBody ProjectUseLandOpinionRequest map) {
        log.info("createProjectUseLandOpinion");
        return buildProcedureFegin.createProjectUseLandOpinion(map);
    }

    @PostMapping("/getProjectUseLandOpinionList")
    public BaseResponse getProjectUseLandOpinionList(@RequestBody ProjectUseLandOpinionRequest map) {
        log.info("getProjectUseLandOpinionList");
        return buildProcedureFegin.getProjectUseLandOpinionList(map);
    }

    @GetMapping("/getProjectUseLandOpinionInfoById")
    public BaseResponse getProjectUseLandOpinionInfoById(@RequestParam String map) {
        log.info("getProjectUseLandOpinionInfoById");
        return buildProcedureFegin.getProjectUseLandOpinionInfoById(map);
    }

    @GetMapping("/deleteProjectUseLandOpinion")
    public BaseResponse deleteProjectUseLandOpinion(@RequestParam String map) {
        log.info("deleteProjectUseLandOpinion");
        return buildProcedureFegin.deleteProjectUseLandOpinion(map);
    }

    @PostMapping("/updateProjectUseLandOpinion")
    public BaseResponse updateProjectUseLandOpinion(@RequestBody ProjectUseLandOpinionRequest map) {
        log.info("updateProjectUseLandOpinion");
        return buildProcedureFegin.updateProjectUseLandOpinion(map);
    }

    @PostMapping("/updateApproveStatusProjectUseLandOpinion")
    public BaseResponse updateApproveStatusProjectUseLandOpinion(@RequestBody ProjectUseLandOpinionRequest map) {
        log.info("updateApproveStatusProjectUseLandOpinion");
        return buildProcedureFegin.updateApproveStatusProjectUseLandOpinion(map);
    }

    /**
     * 监理单位中标通知书
     */
    @PostMapping("/createSupervisionUnitWon")
    public BaseResponse createSupervisionUnitWon(@RequestBody SupervisionUnitWonRequest map) {
        log.info("createSupervisionUnitWon");
        return buildProcedureFegin.createSupervisionUnitWon(map);
    }

    @PostMapping("/getSupervisionUnitWonList")
    public BaseResponse getSupervisionUnitWonList(@RequestBody SupervisionUnitWonRequest page) {
        log.info("getSupervisionUnitWonList");
        return buildProcedureFegin.getSupervisionUnitWonList(page);
    }

    @GetMapping("/getSupervisionUnitWonInfoById")
    public BaseResponse getSupervisionUnitWonInfoById(@RequestParam String map) {
        log.info("getSupervisionUnitWonInfoById");
        return buildProcedureFegin.getSupervisionUnitWonInfoById(map);
    }

    @GetMapping("/deleteSupervisionUnitWon")
    public BaseResponse deleteSupervisionUnitWon(@RequestParam String map) {
        log.info("deleteSupervisionUnitWon");
        return buildProcedureFegin.deleteSupervisionUnitWon(map);
    }

    @PostMapping("/updateSupervisionUnitWon")
    public BaseResponse updateSupervisionUnitWon(@RequestBody SupervisionUnitWonRequest map) {
        log.info("updateSupervisionUnitWon");
        return buildProcedureFegin.updateSupervisionUnitWon(map);
    }

    @PostMapping("/updateApproveStatusSupervisionUnitWon")
    public BaseResponse updateApproveStatusSupervisionUnitWon(@RequestBody SupervisionUnitWonRequest map) {
        log.info("updateApproveStatusSupervisionUnitWon");
        return buildProcedureFegin.updateApproveStatusSupervisionUnitWon(map);
    }
}
