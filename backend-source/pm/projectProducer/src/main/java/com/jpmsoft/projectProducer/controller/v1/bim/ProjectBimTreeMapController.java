package com.jpmsoft.projectProducer.controller.v1.bim;

import com.jpmsoft.projectProducer.config.CommonResponse;
import com.jpmsoft.projectProducer.config.PageResult;
import com.jpmsoft.projectProducer.pojo.request.bim.RiskCategoryHiddenCountRequest;
import com.jpmsoft.projectProducer.pojo.vo.bim.*;
import com.jpmsoft.projectProducer.pojo.vo.contract.ContractVo;
import com.jpmsoft.projectProducer.service.bim.IProjectBimTreeMapService;
import com.jpmsoft.projectProducer.service.project.IPreProjectService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2022/6/10 10:11
 * @Description:
 * @Version: 1.0
 */
@RestController
@RequestMapping("/projectBimTree")
@CrossOrigin
public class ProjectBimTreeMapController {

    @Resource
    private IProjectBimTreeMapService projectBimTreeMapService;

    @Resource
    private IPreProjectService preProjectService;

    @GetMapping("/listProjectIdsByProjectName")
    public CommonResponse<List<String>> listProjectIdsByName(@RequestParam("projectName") String projectName) {
        return CommonResponse.ok(projectBimTreeMapService.listProjectIdsByName(projectName));
    }

    @GetMapping("/listContractIdsByContractName")
    public CommonResponse<List<String>> listContractIdsByName(@RequestParam("contractName") String contractName) {
        return CommonResponse.ok(projectBimTreeMapService.listContractIdsByName(contractName));
    }

    @GetMapping("/listProjectsByProjectName")
    public CommonResponse<List<ProjectVO>> listProjectsByProjectName(@RequestParam("projectName") String projectName) {
        return CommonResponse.ok(projectBimTreeMapService.listProjectsByName(projectName));
    }

    @GetMapping("/listProjectsByProjectIds")
    public CommonResponse<List<ProjectVO>> listProjectsByProjectIds(@RequestParam("projectIds") List<String> projectIds) {
        return CommonResponse.ok(projectBimTreeMapService.listProjectsByProjectIds(projectIds));
    }

    @GetMapping("/listContractsByContractName")
    public CommonResponse<List<ContractVo>> listContractsByContractName(@RequestParam("contractName") String contractName) {
        return CommonResponse.ok(projectBimTreeMapService.listContractsByName(contractName));
    }

    @GetMapping("/listContractByContractIds")
    public CommonResponse<List<ContractVo>> listContractByContractIds(@RequestParam("contractIds") List<String> contractIds) {
        return CommonResponse.ok(projectBimTreeMapService.listContractByContractIds(contractIds));
    }

    /**
     * 按项目 ID、名称和细分类别查询项目信息。
     *
     * @param projectIds             项目 ID 列表
     * @param projectName            项目名称关键字
     * @param projectCategoryDetails 可选的细分类别关键字
     * @return 匹配的项目信息
     */
    @GetMapping("/listProjectsByProjectIdsAndProjectName")
    public CommonResponse<List<ProjectBimInfoVO>> listProjectsByProjectIdsAndProjectName(
            @RequestParam("projectIds") List<String> projectIds,
            @RequestParam("projectName") String projectName,
            @RequestParam(value = "projectCategoryDetails", required = false) String projectCategoryDetails) {
        return CommonResponse.ok(projectBimTreeMapService.listProjectsByProjectIdsAndProjectName(
                projectIds, projectName, projectCategoryDetails));
    }

    @GetMapping("/pageQs")
    public CommonResponse<PageResult<QsBimPageVO>> pageQs(@RequestParam("qsrIds") List<String> qsrIds,
                                                          @RequestParam("pageNum") Integer pageNum,
                                                          @RequestParam("pageSize") Integer pageSize) {
        PageResult<QsBimPageVO> result = projectBimTreeMapService.pageQs(qsrIds, pageNum, pageSize);
        return CommonResponse.ok(result);
    }

    @GetMapping("/countQs")
    public CommonResponse<QsCountVO> countQs(@RequestParam("qsrIds") List<String> qsrIds){
        QsCountVO result = projectBimTreeMapService.countQs(qsrIds);
        return CommonResponse.ok(result);
    }

    @GetMapping("/getQsByQsrId")
    public CommonResponse<QsRecordBimResponse> getQsByQsrId(@RequestParam("qsrId") String qsrId){
        QsRecordBimResponse result = projectBimTreeMapService.getQsByQsrId(qsrId);
        return CommonResponse.ok(result);
    }
    @GetMapping("/listQsByQsrIds")
    public CommonResponse<List<QsRecordBimResponse>> listQsByQsrIds(@RequestParam("qsrIds") List<String> qsrIds){
        List<QsRecordBimResponse> result = projectBimTreeMapService.listQsByQsrIds(qsrIds);
        return CommonResponse.ok(result);
    }

    @GetMapping("/listPlanTaskByTaskIds")
    public CommonResponse<List<PlanTaskBimPageVO>> listPlanTaskByTaskIds(@RequestParam("taskIds") List<String> taskIds){
        List<PlanTaskBimPageVO> result = projectBimTreeMapService.listPlanTaskByTaskIds(taskIds);
        return CommonResponse.ok(result);
    }

    @GetMapping("/listBoqDtlByDtlIds")
    public CommonResponse<List<ConBoqDtlBimPageVO>> listBoqDtlByDtlIds(@RequestParam("boqDtlIds") List<String> boqDtlIds){
        List<ConBoqDtlBimPageVO> result = projectBimTreeMapService.listBoqDtlByDtlIds(boqDtlIds);
        return CommonResponse.ok(result);
    }

    @GetMapping("/getAllAuthProjectId")
    public CommonResponse<List<String>> getAllAuthProjectId(@RequestParam String uid) {
        return CommonResponse.ok(preProjectService.getAllAuthProjectId(uid));
    }

    @GetMapping("/pagePlanTask")
    public CommonResponse<PageResult<PlanTaskBimPageVO>> pagePlanTask(@RequestParam("taskIds") List<String> taskIds,
                                                                      @RequestParam("pageNum") Integer pageNum,
                                                                      @RequestParam("pageSize") Integer pageSize) {
        PageResult<PlanTaskBimPageVO> result = projectBimTreeMapService.pagePlanTask(taskIds, pageNum, pageSize);
        return CommonResponse.ok(result);
    }

    @GetMapping("/listPlanTaskByPlanId")
    public CommonResponse<List<PlanTaskBimPageVO>> listPlanTaskByPlanId(@RequestParam("planId") String planId) {
        List<PlanTaskBimPageVO> result = projectBimTreeMapService.listPlanTaskByPlanId(planId);
        return CommonResponse.ok(result);
    }

    @GetMapping("/pagePlan")
    public CommonResponse<PageResult<PlanBimPageVO>> pagePlan(@RequestParam("planIds") List<String> planIds,
                                                                  @RequestParam("pageNum") Integer pageNum,
                                                                  @RequestParam("pageSize") Integer pageSize) {
        PageResult<PlanBimPageVO> result = projectBimTreeMapService.pagePlan(planIds, pageNum, pageSize);
        return CommonResponse.ok(result);
    }

    @GetMapping("/listBoqDtlByBoqId")
    public CommonResponse<List<ConBoqDtlBimPageVO>> listBoqDtlByBoqId(@RequestParam("boqId") String boqId) {
        List<ConBoqDtlBimPageVO> result = projectBimTreeMapService.listBoqDtlByBoqId(boqId);
        return CommonResponse.ok(result);
    }

    @GetMapping("/pageBoq")
    public CommonResponse<PageResult<ConBoqBimPageVO>> pageBoq(@RequestParam("boqIds") List<String> boqIds,
                                                              @RequestParam("pageNum") Integer pageNum,
                                                              @RequestParam("pageSize") Integer pageSize) {
        PageResult<ConBoqBimPageVO> result = projectBimTreeMapService.pageBoq(boqIds, pageNum, pageSize);
        return CommonResponse.ok(result);
    }

    @GetMapping("/pageBoqDtl")
    public CommonResponse<PageResult<ConBoqDtlBimPageVO>> pageBoqDtl(@RequestParam("boqDtlIds") List<String> boqDtlIds,
                                                                  @RequestParam("pageNum") Integer pageNum,
                                                                  @RequestParam("pageSize") Integer pageSize) {
        PageResult<ConBoqDtlBimPageVO> result = projectBimTreeMapService.pageBoqDtl(boqDtlIds, pageNum, pageSize);
        return CommonResponse.ok(result);
    }

    @PostMapping("/countHiddenByRiskEvaluateIds")
    public CommonResponse<Long> countHiddenByRiskEvaluateIds(@RequestBody RiskCategoryHiddenCountRequest request) {
        Long result = projectBimTreeMapService.countHiddenByRiskEvaluateIds(request);
        return CommonResponse.ok(result);
    }

    @PostMapping("/countQsRecord")
    public CommonResponse<List<QsRecordCountVO>> countQsRecord(@RequestBody RiskCategoryHiddenCountRequest request) {
        List<QsRecordCountVO> result = projectBimTreeMapService.countQsRecord(request);
        return CommonResponse.ok(result);
    }

}
