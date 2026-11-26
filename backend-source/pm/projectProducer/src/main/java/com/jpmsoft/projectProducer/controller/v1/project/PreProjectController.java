package com.jpmsoft.projectProducer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.project.*;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConEcAndVisFeeModel;
import com.jpmsoft.common.model.response.Project.PreProjectResponse;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.dto.project.ProMonthlyReportDto;
import com.jpmsoft.projectProducer.pojo.dto.project.ProjectFundStatisticsDTO;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.project.IPreProjectService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/project")
public class PreProjectController {

    @Resource
    private IPreProjectService preProjectService;

    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ISysUserService sysUserService;

    @GetMapping("/existProjectName")
    public BaseResponse existProjectName(@RequestParam String pName, @RequestParam(required = false) String pId) {
        log.info("existProjectName");
        return BaseResponse.ok(preProjectService.existProjectName(pName, pId));
    }

    @GetMapping("/getProjectDivision")
    public BaseResponse getProjectDivision(@RequestParam(required = false) String province, @RequestParam(required = false) String city) {
        log.info("getProjectDivision");
        return preProjectService.getProjectDivision(province, city);
    }

    /**
     * 统计金额 总投资，概算，估算，匡算
     *
     * @param request
     * @param token
     * @return
     */
    @TenantId
    @PostMapping("/getPreProjectAmtSum")
    public BaseResponse getPreProjectAmtSum(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        log.info("getPreProjectAmtSum");
        String uid = redisUtils.getUid(token);
        return preProjectService.getPreProjectAmtSum(request, uid, true, sysUserService.onlyCreateorAndApproverSQL(uid, "conf.Con_Create_User_Id", "conf.con_id", "Con_Approval_State"));
    }


    /**
     * 获取项目信息列表+统计
     *
     * @param request
     * @param token
     * @return
     */
    @TenantId
    @PostMapping("/getProjectStatisticsList")
    public BaseResponse getProjectStatisticsList(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        log.info("getProjectStatisticsList");
        log.info("request:{}", JacksonUtils.toJSONString(request));
        return preProjectService.getProjectStatisticsList(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getProjectTZList")
    public BaseResponse getProjectTZList(@RequestBody PreProjectTZRequest request, @RequestHeader String token) {
        log.info("getProjectTZList");
        return preProjectService.getProjectTZList(request, redisUtils.getUid(token));
    }

    /**
     * 获取项目信息列表
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/getProjectList")
    public BaseResponse getProjectList(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        log.info("getProjectList");
        return preProjectService.getProjectList(request, redisUtils.getUid(token));
    }

    /**
     * 获取项目信息列表不分页 用于导出
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/getProjectListExcel")
    public List<PreProjectResponse> getProjectListExcel(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        log.info("getProjectList");
        return preProjectService.getProjectListExcel(request, redisUtils.getUid(token));
    }

    /**
     * 工程报告月报导入时查询使用
     *
     * @param request
     * @param uId
     * @return
     */
    @TenantId
    @PostMapping("/getAllProjectList/{uId}")
    public List<PreProjectResponse> getAllProjectList(@RequestBody PreProjectRequest request, @PathVariable("uId") String uId) {
        log.info("getAllProjectList");
        return preProjectService.getAllProjectList(request, uId);
    }

    /**
     * 获取项目信息列表 (响应字段精简)
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/getProjectStreamlineList")
    public BaseResponse getProjectStreamlineList(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        log.info("getProjectStreamlineList");
        return preProjectService.getProjectStreamlineList(request, redisUtils.getUid(token));
    }

    @PostMapping("/getJSCProjectStreamlineList")
    public BaseResponse getJSCProjectStreamlineList(@RequestBody PreProjectRequest request, @RequestParam String uid) {
        log.info("getJSCProjectStreamlineList");
        return preProjectService.getProjectStreamlineList(request, uid);
    }

    @TenantId
    @PostMapping("/getProjectCjBiddingPlanList")
    public BaseResponse getProjectCjBiddingPlanList(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        log.info("getProjectCjBiddingPlanList");
        return preProjectService.getProjectCjBiddingPlanList(request, redisUtils.getUid(token));
    }


    @TenantId
    @PostMapping("/getProjectByProOrganization")
    public BaseResponse getProjectByProOrganization(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        log.info("getProjectByProOrganization");
        return preProjectService.getProjectByProOrganization(request, redisUtils.getUid(token));
    }


    /**
     * 分页查询项目列表 不鉴权
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/getProjectListNoAuth")
    public BaseResponse getProjectListNoAuth(@RequestBody PreProjectRequest request) {
        log.info("getProjectListNoAuth");
        return preProjectService.getProjectListNoAuth(request);
    }

    /**
     * 后台项目组织机构专用
     * admin默认查询所有项目不鉴权
     * 其他用户查询其组织机构下的项目
     *
     * @param request
     * @param token
     * @return
     */
    @TenantId
    @PostMapping("/getProjectListBackstage")
    public BaseResponse getProjectListBackstage(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        log.info("getProjectListBackstage");
        return preProjectService.getProjectListBackstage(request, redisUtils.getUserInfoByToken(token));
    }

    /**
     * 修改项目信息
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/createOrUpdatePreProject")
    public BaseResponse createOrUpdatePreProject(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        log.info("createOrUpdatePreProject");
        return preProjectService.createOrUpdatePreProject(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/createOrUpdatePreProjectCopy")
    public BaseResponse createOrUpdatePreProjectCopy(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        log.info("createOrUpdatePreProject");
        return preProjectService.createOrUpdatePreProjectCopy(request, redisUtils.getUid(token));
    }

    /**
     * 国资平台项目数据接入
     *
     * @param request
     * @return
     */
    @PostMapping("/saveForGZ")
    public BaseResponse saveForGZ(@RequestBody PreProjectRequestDto request) {
        log.info("saveForGZ");
        return preProjectService.saveForGZ(request);
    }

    /**
     * 新增修改项目信息
     * 北京工行专用（新增修改时推送项目信息）
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/BjCreateOrUpdatePreProject")
    public BaseResponse BjCreateOrUpdatePreProject(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        log.info("BjCreateOrUpdatePreProject");
        return preProjectService.BjCreateOrUpdatePreProject(request, redisUtils.getUid(token));
    }

    /**
     * 获取项目信息Select
     *
     * @return
     */
    @TenantId
    @PostMapping("/getProjectSelect")
    public BaseResponse getProjectSelect(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        log.info("getProjectSelect");
        return preProjectService.getProjectSelect(request, redisUtils.getUid(token));
    }

    /**
     * 查询所有项目信息 (部分字段)
     *
     * @return
     */
    @TenantId
    @PostMapping("/getAllPreProject")
    public BaseResponse getAllPreProject(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        log.info("getAllPreProject");
        return preProjectService.getAllPreProject(request, redisUtils.getUid(token));
    }


    @TenantId
    @PostMapping("/getAllOutOrgPreProject")
    public BaseResponse getAllOutOrgPreProject(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        log.info("getAllOutOrgPreProject");
        return preProjectService.getAllOutOrgPreProject(request, redisUtils.getUid(token));
    }


    /**
     * ID查询项目
     *
     * @param request
     * @return
     */
    @PostMapping("/getProjectByPid")
    public BaseResponse getProjectByPid(@RequestBody PreProjectRequest request) {
        log.info("getProjectByPid");
        return preProjectService.getProjectByPid(request);
    }

    @PostMapping("/getProjectByPidCopy")
    public BaseResponse getProjectByPidCopy(@RequestBody PreProjectRequest request) {
        log.info("getProjectByPidCopy");
        return preProjectService.getProjectByPidCopy(request);
    }

    /**
     * 假删除
     *
     * @param pId
     * @return
     */
    @GetMapping("/deleteProjectByPid")
    public BaseResponse deleteProjectByPid(@RequestParam String pId) {
        log.info("deleteProjectByPid");
        return preProjectService.deleteProjectByPid(pId);
    }

    @GetMapping("/statisticsProject")
    public BaseResponse statisticsProject(@RequestHeader String token) {
        log.info("statisticsProject");
        return preProjectService.statisticsProject(redisUtils.getTeId(token));
    }

    /**
     * 获取项目信息列表
     * 单位万元
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/getProjectWYList")
    public BaseResponse getProjectWYList(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        log.info("getProjectWYList");
        return preProjectService.getProjectWYList(request, redisUtils.getUid(token));
    }

    /**
     * ID查询
     * 单位万元
     *
     * @param request
     * @return
     */
    @PostMapping("/getProjectWYByPid")
    public BaseResponse getProjectWYByPid(@RequestBody PreProjectRequest request) {
        log.info("getProjectWYByPid");
        return preProjectService.getProjectWYByPid(request);
    }

    @GetMapping("/updateProjectCodeById")
    public BaseResponse updateProjectCodeById(@RequestParam String pId, @RequestParam String pCode, @RequestHeader String token) {
        log.info("updateProjectCodeById");
        return preProjectService.updateProjectCodeById(pId, pCode, redisUtils.getUid(token));
    }

    /**
     * 根据ID查询项目部分信息即投资信息
     *
     * @param pId
     * @return
     */
    @GetMapping("/getProInvInfoByPid")
    public BaseResponse getProInvInfoByPid(@RequestParam String pId) {
        log.info("getProInvInfoByPid");
        return preProjectService.getProInvInfoByPid(pId);
    }

    @TenantId
    @PostMapping("/getProMonthlyReport")
    public BaseResponse getProMonthlyReport(@RequestBody ProMonthlyReportDto request) {
        log.info("getProMonthlyReport");
        return preProjectService.getProMonthlyReport(request);
    }

    @TenantId
    @PostMapping("/getProjectInvStatistics")
    public BaseResponse getProjectInvStatistics(@RequestBody ProMonthlyReportDto request) {
        log.info("getProjectInvStatistics");
        return preProjectService.getProjectInvStatistics(request);
    }

    @GetMapping("/getProApprovalInfo")
    public BaseResponse getProApprovalInfo(@RequestParam String pId) {
        log.info("getProApprovalInfo");
        return preProjectService.getProApprovalInfo(pId);
    }

    @GetMapping("/getUserAllPid")
    public BaseResponse getUserAllPid(@RequestHeader String token) {
        log.info("getUserAllPid");
        return preProjectService.getUserAllPid(redisUtils.getUserInfoByToken(token));
    }

    @GetMapping("/getAllProjectByOrganization")
    public BaseResponse getAllProjectByOrganization(@RequestParam String uid) {
        log.info("getAllProjectByOrganization");
        return preProjectService.getAllProjectByOrganization(uid);
    }

    @GetMapping("/getUserAllPidByUid")
    public BaseResponse getUserAllPidByUid(@RequestParam String uId) {
        log.info("getUserAllPidByUid");
        return preProjectService.getUserAllPid(redisUtils.getUserInfoByUid(uId));
    }

    @TenantId
    @PostMapping("/getProjectFundStatistics")
    public BaseResponse getProjectFundStatistics(@RequestBody ProjectFundStatisticsDTO request, @RequestHeader String token) {
        log.info("getProjectFundStatistics");
        return preProjectService.getProjectFundStatistics(request, redisUtils.getUid(token));
    }

    @TenantId
    @PostMapping("/getProjectFundStatisticsTree")
    public BaseResponse getProjectFundStatisticsTree(@RequestBody ProjectFundStatisticsDTO request, @RequestHeader String token) {
        log.info("getProjectFundStatisticsTree");
        return preProjectService.getProjectFundStatisticsTree(request, redisUtils.getUid(token));
    }

    @GetMapping("/getAllAuthProjectId")
    public BaseResponse getAllAuthProjectId(@RequestParam String uid) {
        return BaseResponse.ok(preProjectService.getAllAuthProjectId(uid));
    }

    @GetMapping("/getApproveBudgetAndRate")
    public BaseResponse getApproveBudgetAndRate(@RequestParam String pId) {
        return preProjectService.getApproveBudgetAndRate(pId);
    }
    /**
     * 城建资金计划项目列表
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/getProjectListForInvPlan")
    public BaseResponse getProjectListForInvPlan(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        log.info("getProjectListForInvPlan");
        return preProjectService.getProjectListForInvPlan(request, redisUtils.getUid(token));
    }

    /**
     * 城建资金计划项目管理单位列表
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/getDepartmentListForInvPlan")
    public BaseResponse getDepartmentListForInvPlan(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        log.info("getDepartmentListForInvPlan");
        return preProjectService.getDepartmentListForInvPlan(request, redisUtils.getUid(token));
    }

    /**
     * 获取项目匡算清单树
     *
     * @param pIds
     * @return
     */
    @PostMapping("/getPreProjectDtlByIds")
    public BaseResponse getPreProjectDtlByIds(@RequestBody List<String> pIds) {
        log.info("getPreProjectDtlByIds");
        return preProjectService.getPreProjectDtlByIds(pIds);
    }

    /**
     * ID查询项目 全过程咨询/项目管理单位
     * P_Whole_Process_Management_Unit
     * pWholeProcessManagementUnit
     *
     * @param request
     * @return
     */
    @PostMapping("/getProjectCompetentDepartmentByPid")
    public BaseResponse getProjectCompetentDepartmentByPid(@RequestBody PreProjectRequest request) {
        log.info("getProjectCompetentDepartmentByPid");
        return preProjectService.getProjectCompetentDepartmentByPid(request);
    }

    @GetMapping("/updateProjectAuthType")
    public BaseResponse updateProjectAuthType(@RequestParam String id, @RequestParam Integer type) {
        log.info("updateProjectAuthType");
        return preProjectService.updateProjectAuthType(id, type);
    }

    @PostMapping("/getProjectClassInfo")
    public BaseResponse getProjectClassInfo(@RequestBody PreProjectIDsRequest request, @RequestHeader String token) {
        log.info("getProjectClassInfo");
        return preProjectService.getProjectClassInfo(request.getProjectIds(), redisUtils.getUid(token));
    }

    @PostMapping("/updateProjectInfo")
    public BaseResponse updateProjectInfo(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        log.info("updateProjectInfo");
        return preProjectService.updateProjectInfo(request, redisUtils.getUid(token));
    }

    /**
     * 项目创建时自动默认导入一个项目组织机构模板数据
     * 此接口用于低代码项目表单保存调用
     *
     * @param formPriKey
     * @return
     */
    @PostMapping("/projectCreate2ImpTempDepartment")
    public BaseResponse projectCreate2ImpTempDepartment(@RequestParam String formPriKey) {
        preProjectService.projectCreate2ImpTempDepartment(formPriKey);
        return BaseResponse.ok();
    }

    /**
     * 城建项目审批通过同步指定项目组织机构模板数据 并将项目上指定字段人员绑定到对应部门岗位
     *
     * @param formPriKey
     * @return
     */
    @PostMapping("/projectApproved2ImpTempDepartment")
    public BaseResponse projectApproved2ImpTempDepartment(@RequestParam String formPriKey) {
        preProjectService.projectApproved2ImpTempDepartment(formPriKey);
        return BaseResponse.ok();
    }

    @PostMapping("/approvalData")
    public BaseResponse approvalData(@RequestBody ProjectRequest request, @RequestHeader String token) {
        log.info("approvalData");
        return preProjectService.approvalData(request, token);
    }

    @GetMapping("/queryDockptPreList")
    public BaseResponse queryDockptPreList(@RequestParam String dId, @RequestParam(required = false) String pName, @RequestParam Integer page, @RequestParam Integer size) {
        return preProjectService.queryDockptPreList(dId, pName, page, size);
    }

    @GetMapping("/getProjectCjGsGsFenJie")
    public BaseResponse getProjectCjGsGsFenJie(@RequestParam String pid) {
        return preProjectService.getProjectCjGsGsFenJie(pid);
    }


    @PostMapping("/getProjecListNJCJ")
    public BaseResponse getProjecListNJCJ(@RequestBody PreProjectTZRequest request, @RequestHeader String token) {
        log.info("getProjecListNJCJ");
        return preProjectService.getProjecListNJCJ(request, token);
    }

    @PostMapping("/getProjecListNJCJAuth")
    public BaseResponse getProjecListNJCJAuth(@RequestBody PreProjectTZRequest request, @RequestHeader String token) {
        log.info("getProjecListNJCJAuth");
        return preProjectService.getProjecListNJCJAuth(request, redisUtils.getUid(token));
    }

    @PostMapping("/getProjecListGDGY")
    public BaseResponse getProjecListGDGY(@RequestBody PreProjectRequest request) {
        log.info("getProjecListGDGY");
        return preProjectService.getProjecListGDGY(request);
    }

    @PostMapping("/getProjectGDGY")
    public BaseResponse getProjectGDGY(@RequestBody PreProjectRequest request) {
        log.info("getProjectGDGY");
        return preProjectService.getProjectGDGY(request);
    }

    @GetMapping("/getPIdByPprId")
    public BaseResponse getPIdByPprId(@RequestParam("pprId") String pprId) {
        log.info("getPIdByPPrId");
        return preProjectService.getPIdByPprId(pprId);
    }

    @PostMapping("/updateProjectPersonnel")
    public void updateProjectPersonnel(@RequestParam("pId") String pId, @RequestParam("rpCreateUid") String rpCreateUid) {
        log.info("updateProjectPersonnel");
        preProjectService.updateProjectPersonnel(pId, rpCreateUid);
    }

    /**
     * 广工
     * 以项目统计查询设计变更和签证变更
     */
    @PostMapping("/getProjectContractChangeList")
    public BaseResponse getProjectContractChangeList(@RequestBody PreProjectRequest request,@RequestHeader String token) {
        log.info("getProjectChangeList");
        return preProjectService.getProjectContractChangeList(request,redisUtils.getUid(token));
    }

    /**
     * 广工
     * 根据指定项目ID 查询 设计变更 和 签证变更
     */
    @PostMapping("/getProjectContractChangeListBypId")
    public BaseResponse getProjectContractChangeListBypId(@RequestBody ConEcAndVisFeeModel request, @RequestHeader String token) {
        log.info("getProjectChangeListById");
        return preProjectService.getProjectContractChangeListBypId(request,redisUtils.getUid(token));
    }


    /**
     * 河海大学
     * 查询项目类型和年份
     */
    @PostMapping("/getProjectHHTZList")
    public BaseResponse getProjectHHTZList(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        log.info("getProjectHHTZList");
        return preProjectService.getProjectHHTZList(request,redisUtils.getUid(token));
    }

    /**
     * 在建设模式变更时刷新项目编码
     *
     * @param pId
     * @param oldPBuildModel
     * @return
     */
    @GetMapping("/refreshProjectCode")
    public BaseResponse refreshProjectCode(@RequestParam String pId, @RequestParam String oldPBuildModel) {
        log.info("refreshProjectCode");
        return preProjectService.refreshProjectCode(pId, oldPBuildModel);
    }

    /**
     * 获取项目进度
     *
     * @param request 项目分页查询条件
     * @param token 用户令牌
     * @return 带合同金额及支付占比的项目分页结果
     */
    @TenantId
    @PostMapping("/getProjectProgress")
    public BaseResponse getProjectProgress(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        log.info("getProjectProgress");
        return preProjectService.getProjectProgress(request, redisUtils.getUid(token));
    }
}
