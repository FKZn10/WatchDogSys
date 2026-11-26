package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.base.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.jpmsoft.common.model.request.ConstructionProcedure.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "project-producer", contextId = "buildprocedure")
public interface BuildProcedureFegin {

    @PostMapping("/v1/buildProcedure/createAdministrativeLicensing")
    BaseResponse createAdministrativeLicensing(@RequestBody AdministrativeLicensingRequest map);


    @PostMapping("/v1/buildProcedure/getAdministrativeLicensingList")
    BaseResponse getAdministrativeLicensingList(@RequestBody AdministrativeLicensingRequest page);

    @GetMapping("/v1/buildProcedure/getAdministrativeLicensingInfoById")
    BaseResponse getAdministrativeLicensingInfoById(@RequestParam String map);


    @GetMapping("/v1/buildProcedure/deleteAdministrativeLicensing")
    BaseResponse deleteAdministrativeLicensing(@RequestParam String map);

    @PostMapping("/v1/buildProcedure/updateAdministrativeLicensing")
    BaseResponse updateAdministrativeLicensing(@RequestBody AdministrativeLicensingRequest map);

    @PostMapping("/v1/buildProcedure/updateApproveStatusAdministrativeLicensing")
    BaseResponse updateApproveStatusAdministrativeLicensing(@RequestBody AdministrativeLicensingRequest map);
    /*--*/


    @PostMapping("/v1/buildProcedure/createApprovalOfGreeningScheme")
    BaseResponse createApprovalOfGreeningScheme(@RequestBody ApprovalOfGreeningSchemeRequest map);


    @PostMapping("/v1/buildProcedure/getApprovalOfGreeningSchemeList")
    BaseResponse getApprovalOfGreeningSchemeList(@RequestBody ApprovalOfGreeningSchemeRequest page);


    @GetMapping("/v1/buildProcedure/getApprovalOfGreeningSchemeInfoById")
    BaseResponse getApprovalOfGreeningSchemeInfoById(@RequestParam String map);

    @GetMapping("/v1/buildProcedure/deleteApprovalOfGreeningScheme")
    BaseResponse deleteApprovalOfGreeningScheme(@RequestParam String map);

    @PostMapping("/v1/buildProcedure/updateApprovalOfGreeningScheme")
    BaseResponse updateApprovalOfGreeningScheme(@RequestBody ApprovalOfGreeningSchemeRequest map);

    @PostMapping("/v1/buildProcedure/updateApproveStatusApprovalOfGreeningScheme")
    BaseResponse updateApproveStatusApprovalOfGreeningScheme(@RequestBody ApprovalOfGreeningSchemeRequest map);
    /*---*/

    @PostMapping("/v1/buildProcedure/createBuildingConstructionPermits")
    BaseResponse createBuildingConstructionPermits(@RequestBody BuildingConstructionPermitsRequest map);


    @PostMapping("/v1/buildProcedure/getBuildingConstructionPermitsList")
    BaseResponse getBuildingConstructionPermitsList(@RequestBody BuildingConstructionPermitsRequest page);

    @GetMapping("/v1/buildProcedure/getBuildingConstructionPermitsInfoById")
    BaseResponse getBuildingConstructionPermitsInfoById(@RequestParam String map);

    @GetMapping("/v1/buildProcedure/deleteBuildingConstructionPermits")
    BaseResponse deleteBuildingConstructionPermits(@RequestParam String map);

    @PostMapping("/v1/buildProcedure/updateBuildingConstructionPermits")
    BaseResponse updateBuildingConstructionPermits(@RequestBody BuildingConstructionPermitsRequest map);

    @PostMapping("/v1/buildProcedure/updateApproveStatusBuildingConstructionPermits")
    BaseResponse updateApproveStatusBuildingConstructionPermits(@RequestBody BuildingConstructionPermitsRequest map);
    /*---*/

    @PostMapping("/v1/buildProcedure/createLandUsePlanning")
    BaseResponse createLandUsePlanning(@RequestBody LandUsePlanningRequest map);

    @PostMapping("/v1/buildProcedure/getLandUsePlanningList")
    BaseResponse getLandUsePlanningList(@RequestBody LandUsePlanningRequest page);

    @GetMapping("/v1/buildProcedure/getLandUsePlanningInfoById")
    BaseResponse getLandUsePlanningInfoById(@RequestParam String map);

    @GetMapping("/v1/buildProcedure/deleteLandUsePlanning")
    BaseResponse deleteLandUsePlanning(@RequestParam String map);

    @PostMapping("/v1/buildProcedure/updateLandUsePlanning")
    BaseResponse updateLandUsePlanning(@RequestBody LandUsePlanningRequest map);

    @PostMapping("/v1/buildProcedure/updateApproveStatusLandUsePlanning")
    BaseResponse updateApproveStatusLandUsePlanning(@RequestBody LandUsePlanningRequest map);
    /*---*/

    @PostMapping("/v1/buildProcedure/createNoticeOfConstructionUnit")
    BaseResponse createNoticeOfConstructionUnit(@RequestBody NoticeOfConstructionUnitRequest map);

    @PostMapping("/v1/buildProcedure/getNoticeOfConstructionUnitList")
    BaseResponse getNoticeOfConstructionUnitList(@RequestBody NoticeOfConstructionUnitRequest page);

    @GetMapping("/v1/buildProcedure/getNoticeOfConstructionUnitInfoById")
    BaseResponse getNoticeOfConstructionUnitInfoById(@RequestParam String map);

    @GetMapping("/v1/buildProcedure/deleteNoticeOfConstructionUnit")
    BaseResponse deleteNoticeOfConstructionUnit(@RequestParam String map);

    @PostMapping("/v1/buildProcedure/updateNoticeOfConstructionUnit")
    BaseResponse updateNoticeOfConstructionUnit(@RequestBody NoticeOfConstructionUnitRequest map);

    @PostMapping("/v1/buildProcedure/updateApproveStatusNoticeOfConstructionUnit")
    BaseResponse updateApproveStatusNoticeOfConstructionUnit(@RequestBody NoticeOfConstructionUnitRequest map);

    /**/
    @PostMapping("/v1/buildProcedure/createPlanningConditions")
    BaseResponse createPlanningConditions(@RequestBody PlanningConditionsRequest map);

    @PostMapping("/v1/buildProcedure/getPlanningConditionsList")
    BaseResponse getPlanningConditionsList(@RequestBody PlanningConditionsRequest page);

    @GetMapping("/v1/buildProcedure/getPlanningConditionsInfoById")
    BaseResponse getPlanningConditionsInfoById(@RequestParam String map);

    @GetMapping("/v1/buildProcedure/deletePlanningConditions")
    BaseResponse deletePlanningConditions(@RequestParam String map);

    @PostMapping("/v1/buildProcedure/updatePlanningConditions")
    BaseResponse updatePlanningConditions(@RequestBody PlanningConditionsRequest map);

    @PostMapping("/v1/buildProcedure/updateApproveStatusPlanningConditions")
    BaseResponse updateApproveStatusPlanningConditions(@RequestBody PlanningConditionsRequest map);
    /*__*/

    @PostMapping("/v1/buildProcedure/createPlanningSchemeApproval")
    BaseResponse createPlanningSchemeApproval(@RequestBody PlanningSchemeApprovalRequest map);

    @PostMapping("/v1/buildProcedure/getPlanningSchemeApprovalList")
    BaseResponse getPlanningSchemeApprovalList(@RequestBody PlanningSchemeApprovalRequest page);

    @GetMapping("/v1/buildProcedure/getPlanningSchemeApprovalListInfoById")
    BaseResponse getPlanningSchemeApprovalListInfoById(@RequestParam String map);

    @GetMapping("/v1/buildProcedure/deletePlanningSchemeApproval")
    BaseResponse deletePlanningSchemeApproval(@RequestParam String map);

    @PostMapping("/v1/buildProcedure/updatePlanningSchemeApproval")
    BaseResponse updatePlanningSchemeApproval(@RequestBody PlanningSchemeApprovalRequest map);


    @PostMapping("/v1/buildProcedure/updateApproveStatusPlanningSchemeApproval")
    BaseResponse updateApproveStatusPlanningSchemeApproval(@RequestBody PlanningSchemeApprovalRequest map);
    /**/
    @PostMapping("/v1/buildProcedure/createProjectBuildOpinion")
    BaseResponse createProjectBuildOpinion(@RequestBody ProjectBuildOpinionRequest map);

    @PostMapping("/v1/buildProcedure/getProjectBuildOpinionList")
    BaseResponse getProjectBuildOpinionList(@RequestBody ProjectBuildOpinionRequest map);

    @GetMapping("/v1/buildProcedure/getProjectBuildOpinionInfoById")
    BaseResponse getProjectBuildOpinionInfoById(@RequestParam String map);

    @GetMapping("/v1/buildProcedure/deleteProjectBuildOpinion")
    BaseResponse deleteProjectBuildOpinion(@RequestParam String map);

    @PostMapping("/v1/buildProcedure/updateProjectBuildOpinion")
    BaseResponse updateProjectBuildOpinion(@RequestBody ProjectBuildOpinionRequest map);
    @PostMapping("/v1/buildProcedure/updateApproveStatusProjectBuildOpinion")
    BaseResponse updateApproveStatusProjectBuildOpinion(@RequestBody ProjectBuildOpinionRequest map);
    /*--*/
    @PostMapping("/v1/buildProcedure/createProjectUseLandOpinion")
    BaseResponse createProjectUseLandOpinion(@RequestBody ProjectUseLandOpinionRequest map);

    @PostMapping("/v1/buildProcedure/getProjectUseLandOpinionList")
    BaseResponse getProjectUseLandOpinionList(@RequestBody ProjectUseLandOpinionRequest page);

    @GetMapping("/v1/buildProcedure/getProjectUseLandOpinionInfoById")
    BaseResponse getProjectUseLandOpinionInfoById(@RequestParam String map);

    @GetMapping("/v1/buildProcedure/deleteProjectUseLandOpinion")
    BaseResponse deleteProjectUseLandOpinion(@RequestParam String map);

    @PostMapping("/v1/buildProcedure/updateProjectUseLandOpinion")
    BaseResponse updateProjectUseLandOpinion(@RequestBody ProjectUseLandOpinionRequest map);


    @PostMapping("/v1/buildProcedure/updateApproveStatusProjectUseLandOpinion")
    BaseResponse updateApproveStatusProjectUseLandOpinion(@RequestBody ProjectUseLandOpinionRequest map);
    /*---*/
    @PostMapping("/v1/buildProcedure/createSupervisionUnitWon")
    BaseResponse createSupervisionUnitWon(@RequestBody SupervisionUnitWonRequest map);

    @PostMapping("/v1/buildProcedure/getSupervisionUnitWonList")
    BaseResponse getSupervisionUnitWonList(@RequestBody SupervisionUnitWonRequest page);

    @GetMapping("/v1/buildProcedure/getSupervisionUnitWonInfoById")
    BaseResponse getSupervisionUnitWonInfoById(@RequestParam String map);

    @GetMapping("/v1/buildProcedure/deleteSupervisionUnitWon")
    BaseResponse deleteSupervisionUnitWon(@RequestParam String map);

    @PostMapping("/v1/buildProcedure/updateSupervisionUnitWon")
    BaseResponse updateSupervisionUnitWon(@RequestBody SupervisionUnitWonRequest map);

    @PostMapping("/v1/buildProcedure/updateApproveStatusSupervisionUnitWon")
    BaseResponse updateApproveStatusSupervisionUnitWon(@RequestBody SupervisionUnitWonRequest map);
}
