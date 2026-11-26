package com.jpmsoft.projectConsumer.controller.v1.project;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.project.*;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConEcAndVisFeeModel;
import com.jpmsoft.projectConsumer.feign.PreFeign;
import com.jpmsoft.projectConsumer.pojo.dto.project.ProMonthlyReportDto;
import com.jpmsoft.projectConsumer.pojo.dto.project.ProjectFundStatisticsDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "项目", value = "项目")
@RequestMapping("/v1/project")
public class PreProjectController {
    @Resource
    private PreFeign preFeign;

    @ApiOperation(value = "项目名称判断是否存在非作废的项目")
    @GetMapping("/existProjectName")
    public BaseResponse existProjectName(@RequestParam String pName,@RequestParam(required = false) String pId) {
        log.info("existProjectName");
        return preFeign.existProjectName(pName,pId);
    }

    @ApiOperation(value = "项目名称判断是否存在非作废的项目")
    @GetMapping("/getProjectDivision")
    public BaseResponse getProjectDivision(@RequestParam(required = false) String province,@RequestParam(required = false) String city) {
        log.info("getProjectDivision");
        return preFeign.getProjectDivision(province,city);
    }

    @ApiOperation(value = "统计金额 总投资，概算，估算，匡算")
    @PostMapping("/getPreProjectAmtSum")
    public BaseResponse getPreProjectAmtSum(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        log.info("getPreProjectAmtSum");
        return preFeign.getPreProjectAmtSum(request, token);
    }

    /**
     * 获取项目信息列表
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取项目信息列表")
    @PostMapping("/getProjectList")
    public BaseResponse getProjectList(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        log.info("getProjectList");
        return preFeign.getProjectList(request, token);
    }

    /**
     * 分页查询项目进度及合同支付比例。
     *
     * @param request 项目分页查询条件
     * @param token 用户令牌
     * @return 带合同金额及支付占比的项目分页结果
     */
    @ApiOperation(value = "获取项目进度列表")
    @PostMapping("/getProjectProgress")
    public BaseResponse getProjectProgress(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        return preFeign.getProjectProgress(request, token);
    }

    @ApiOperation(value = "获取项目信息列表+统计")
    @PostMapping("/getProjectStatisticsList")
    public BaseResponse getProjectStatisticsList(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        log.info("getProjectStatisticsList");
        return preFeign.getProjectStatisticsList(request, token);
    }

    @ApiOperation(value = "获取项目信息列表+统计")
    @PostMapping("/getProjectTZList")
    public BaseResponse getProjectTZList(@RequestBody PreProjectTZRequest request, @RequestHeader String token) {
        log.info("getProjectTZList");
        return preFeign.getProjectTZList(request, token);
    }

    /**
     * 获取项目信息列表 (响应字段精简)
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取项目信息列表 (响应字段精简)")
    @PostMapping("/getProjectStreamlineList")
    public BaseResponse getProjectStreamlineList(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        log.info("getProjectStreamlineList");
        return preFeign.getProjectStreamlineList(request, token);
    }

    /**
     * 城建资金计划项目列表
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "城建资金计划项目列表")
    @PostMapping("/getProjectListForInvPlan")
    public BaseResponse getProjectListForInvPlan(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        log.info("getProjectListForInvPlan");
        return preFeign.getProjectListForInvPlan(request, token);
    }

    /**
     * 城建资金计划项目列表
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "城建资金计划项目管理单位列表")
    @PostMapping("/getDepartmentListForInvPlan")
    public BaseResponse getDepartmentListForInvPlan(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        log.info("getDepartmentListForInvPlan");
        return preFeign.getDepartmentListForInvPlan(request, token);
    }

    @ApiOperation(value = "城建招标计划定制项目选择接口(合同+建设程序办理情况过滤)")
    @PostMapping("/getProjectCjBiddingPlanList")
    public BaseResponse getProjectCjBiddingPlanList(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        log.info("getProjectCjBiddingPlanList");
        return preFeign.getProjectCjBiddingPlanList(request, token);
    }


    @ApiOperation(value = "获取项目信息列表 (响应字段精简,根据项目组织机构鉴权)")
    @PostMapping("/getProjectByProOrganization")
    public BaseResponse getProjectByProOrganization(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        log.info("getProjectByProOrganization");
        return preFeign.getProjectByProOrganization(request, token);
    }

    /**
     * 分页查询项目列表 不鉴权
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "分页查询项目列表 不鉴权")
    @PostMapping("/getProjectListNoAuth")
    public BaseResponse getProjectListNoAuth(@RequestBody PreProjectRequest request) {
        log.info("getProjectStreamlineList");
        return preFeign.getProjectListNoAuth(request);
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
    @ApiOperation(value = "后台项目组织机构专用 admin默认查询所有项目不鉴权 其他用户查询其组织机构下的项目")
    @PostMapping("/getProjectListBackstage")
    public BaseResponse getProjectListBackstage(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        log.info("getProjectListBackstage");
        return preFeign.getProjectListBackstage(request, token);
    }

    /**
     * 修改项目信息
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "修改项目信息")
    @PostMapping("/createOrUpdatePreProject")
    public BaseResponse createOrUpdatePreProject(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        log.info("createOrUpdatePreProject");
        return preFeign.createOrUpdatePreProject(request, token);
    }

    @ApiOperation(value = "修改项目信息copy")
    @PostMapping("/createOrUpdatePreProjectCopy")
    public BaseResponse createOrUpdatePreProjectCopy(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        log.info("createOrUpdatePreProjectCopy");
        return preFeign.createOrUpdatePreProjectCopy(request, token);
    }

    @ApiOperation(value = "国资平台项目数据接入")
    @PostMapping("/saveForGZ")
    public BaseResponse saveForGZ(@RequestBody PreProjectRequestDto request) {
        log.info("saveForGZ");
        return preFeign.saveForGZ(request);
    }

    /**
     * 新增修改项目信息 北京工行专用（新增修改时推送项目信息）
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "新增修改项目信息 北京工行专用（新增修改时推送项目信息）")
    @PostMapping("/BjCreateOrUpdatePreProject")
    public BaseResponse BjCreateOrUpdatePreProject(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        log.info("BjCreateOrUpdatePreProject");
        return preFeign.BjCreateOrUpdatePreProject(request, token);
    }


    /**
     * 获取项目信息Select
     *
     * @return
     */
    @ApiOperation(value = "获取项目信息Select")
    @PostMapping("/getProjectSelect")
    public BaseResponse getProjectSelect(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        log.info("getProjectSelect");
        return preFeign.getProjectSelect(request, token);
    }

    /**
     * 查询所有项目信息 (部分字段)
     *
     * @return
     */
    @ApiOperation(value = "查询所有项目信息 (部分字段)")
    @PostMapping("/getAllPreProject")
    public BaseResponse getAllPreProject(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        log.info("getAllPreProject");
        return preFeign.getAllPreProject(request, token);
    }

    @ApiOperation(value = "查询所有项目信息 (部分字段,组织机构鉴权)")
    @PostMapping("/getAllOutOrgPreProject")
    public BaseResponse getAllOutOrgPreProject(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        log.info("getAllOutOrgPreProject");
        return preFeign.getAllOutOrgPreProject(request, token);
    }


    /**
     * ID查询项目
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "ID查询项目")
    @PostMapping("/getProjectByPid")
    public BaseResponse getProjectByPid(@RequestBody PreProjectRequest request) {
        log.info("getProjectByPid");
        return preFeign.getProjectByPid(request);
    }

    @ApiOperation(value = "ID查询项目copy")
    @PostMapping("/getProjectByPidCopy")
    public BaseResponse getProjectByPidCopy(@RequestBody PreProjectRequest request) {
        log.info("getProjectByPidCopy");
        return preFeign.getProjectByPidCopy(request);
    }


    /**
     * 假删除
     *
     * @param pId
     * @return
     */
    @ApiOperation(value = "项目假删除")
    @GetMapping("/deleteProjectByPid")
    public BaseResponse deleteProjectByPid(@RequestParam String pId) {
        log.info("deleteProjectByPid");
        return preFeign.deleteProjectByPid(pId);
    }

    /**
     * 统计项目，
     * 立项阶段，
     * 初步设计
     * 施工设计图
     *
     * @return
     */
    @ApiOperation(value = "统计项目(立项阶段,初步设计,施工设计图等)")
    @GetMapping("/statisticsProject")
    public BaseResponse statisticsProject() {
        log.info("statisticsProject");
        return preFeign.statisticsProject();
    }


    @ApiOperation(value = "ID查询项目(项目资金来源 万元)")
    @PostMapping("/getProjectWYByPid")
    public BaseResponse getProjectWYByPid(@RequestBody PreProjectRequest request) {
        log.info("getProjectWYByPid");
        return preFeign.getProjectWYByPid(request);
    }

    @ApiOperation(value = "获取项目信息列表(项目资金来源 万元)")
    @PostMapping("/getProjectWYList")
    public BaseResponse getProjectWYList(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        log.info("getProjectWYList");
        return preFeign.getProjectWYList(request, token);
    }

    @ApiOperation(value = "修改项目编码")
    @GetMapping("/updateProjectCodeById")
    public BaseResponse updateProjectCodeById(@RequestParam String pId, @RequestParam String pCode, @RequestHeader String token) {
        log.info("updateProjectCodeById");
        return preFeign.updateProjectCodeById(pId, pCode, token);
    }

    @ApiOperation(value = "根据ID查询项目部分信息即投资信息")
    @GetMapping("/getProInvInfoByPid")
    public BaseResponse getProInvInfoByPid(@RequestParam String pId) {
        log.info("getProInvInfoByPid");
        return preFeign.getProInvInfoByPid(pId);
    }

    @ApiOperation(value = "根据ID查询项目部分信息-批复信息")
    @GetMapping("/getProApprovalInfo")
    public BaseResponse getProApprovalInfo(@RequestParam String pId) {
        log.info("getProApprovalInfo");
        return preFeign.getProApprovalInfo(pId);
    }

    @ApiOperation(value = "年月获取项目月报")
    @PostMapping("/getProMonthlyReport")
    public BaseResponse getProMonthlyReport(@RequestBody ProMonthlyReportDto request) {
        log.info("getProMonthlyReport");
        return preFeign.getProMonthlyReport(request);
    }

    @ApiOperation(value = "年月查询项目投资统计")
    @PostMapping("/getProjectInvStatistics")
    public BaseResponse getProjectInvStatistics(@RequestBody ProMonthlyReportDto request) {
        log.info("getProjectInvStatistics");
        return preFeign.getProjectInvStatistics(request);
    }

    @ApiOperation(value = "获取当前用户可查看的所有项目ID列表")
    @GetMapping("/getUserAllPid")
    public BaseResponse getUserAllPid(@RequestHeader String token) {
        log.info("getUserAllPid");
        return preFeign.getUserAllPid(token);
    }

    @ApiOperation(value = "用户ID获取可查看的所有项目ID列表")
    @GetMapping("/getUserAllPidByUid")
    public BaseResponse getUserAllPidByUid(@RequestParam String uId) {
        log.info("getUserAllPidByUid");
        return preFeign.getUserAllPidByUid(uId);
    }

    @ApiOperation(value = "用户ID获取其所有项目组织机构关联的项目信息")
    @GetMapping("/getAllProjectByOrganization")
    public BaseResponse getAllProjectByOrganization(@RequestParam String uid) {
        log.info("getAllProjectByOrganization");
        return preFeign.getAllProjectByOrganization(uid);
    }

    @ApiOperation(value = "项目资金统计")
    @PostMapping("/getProjectFundStatistics")
    public BaseResponse getProjectFundStatistics(@RequestBody ProjectFundStatisticsDTO request, @RequestHeader String token) {
        log.info("getProjectFundStatistics");
        return preFeign.getProjectFundStatistics(request, token);
    }

    @ApiOperation(value = "项目资金统计树形")
    @PostMapping("/getProjectFundStatisticsTree")
    public BaseResponse getProjectFundStatisticsTree(@RequestBody ProjectFundStatisticsDTO request, @RequestHeader String token) {
        log.info("getProjectFundStatisticsTree");
        return preFeign.getProjectFundStatisticsTree(request, token);
    }

    @ApiOperation(value = "获取该用户有权查看的所有项目ID")
    @GetMapping("/getAllAuthProjectId")
    public BaseResponse getAllAuthProjectId(@RequestParam String uid) {
        log.info("getAllAuthProjectId");
        return preFeign.getAllAuthProjectId(uid);
    }


    @ApiOperation(value = "项目id返回项目概算金额、合同的金额汇总值、概算已使用比例")
    @GetMapping("/getApproveBudgetAndRate")
    public BaseResponse getApproveBudgetAndRate(@RequestParam String pId) {
        log.info("getApproveBudgetAndRate");
        return preFeign.getApproveBudgetAndRate(pId);
    }


    @ApiOperation(value = "获取项目匡算清单树")
    @PostMapping("/getPreProjectDtlByIds")
    public BaseResponse getPreProjectDtlByIds(@RequestBody List<String> pIds) {
        log.info("getPreProjectDtlByIds");
        return preFeign.getPreProjectDtlByIds(pIds);
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
        return preFeign.getProjectCompetentDepartmentByPid(request);
    }

    @ApiOperation(value = "修改项目鉴权类型")
    @GetMapping("/updateProjectAuthType")
    public BaseResponse updateProjectAuthType(@RequestParam String id, @RequestParam Integer type) {
        log.info("updateProjectAuthType");
        return preFeign.updateProjectAuthType(id, type);
    }

    @PostMapping("/getProjectClassInfo")
    public BaseResponse getProjectClassInfo(@RequestBody PreProjectIDsRequest request, @RequestHeader String token) {
        log.info("getProjectClassInfo");
        return preFeign.getProjectClassInfo(request, token);
    }

    @PostMapping("/updateProjectInfo")
    public BaseResponse updateProjectInfo(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        log.info("updateProjectInfo");
        return preFeign.updateProjectInfo(request, token);
    }

    @GetMapping("/projectApproved2ImpTempDepartment")
    public BaseResponse projectApproved2ImpTempDepartment(@RequestParam String pId) {
        log.info("projectApproved2ImpTempDepartment");
        return preFeign.projectApproved2ImpTempDepartment(pId);
    }

    @PostMapping("/approvalData")
    public BaseResponse approvalData(@RequestBody ProjectRequest request, @RequestHeader String token) {
        log.info("approvalData");
        return preFeign.approvalData(request, token);
    }


    @GetMapping("/queryDockptPreList")
    public BaseResponse queryDockptPreList(@RequestParam String cId, @RequestParam(required = false) String pName, @RequestParam Integer page, @RequestParam Integer size) {
        log.info("queryDockptPreList");
        return preFeign.queryDockptPreList(cId, pName, page, size);
    }

    @ApiOperation(value = "根据投资估算，概算，概算金额 限制招标计划，合同，支付的发起")
    @GetMapping("/getProjectCjGsGsFenJie")
    public BaseResponse getProjectCjGsGsFenJie(@RequestParam String pid) {
        return preFeign.getProjectCjGsGsFenJie(pid);
    }

    @ApiOperation(value = "城建项目查询，仅审批人可见")
    @PostMapping("/getProjecListNJCJ")
    public BaseResponse getProjecListNJCJ(@RequestBody PreProjectTZRequest request, @RequestHeader String token) {
        return preFeign.getProjecListNJCJ(request, token);
    }

    @ApiOperation(value = "城建项目查询，正常组织机构级别鉴权（城建根据基础配置 成员企业部门可平级穿透达到企业看成员企业，集团看所有的目的）")
    @PostMapping("/getProjecListNJCJAuth")
    public BaseResponse getProjecListNJCJAuth(@RequestBody PreProjectTZRequest request, @RequestHeader String token) {
        return preFeign.getProjecListNJCJAuth(request, token);
    }

    @ApiOperation(value = "项目注册台账，广东工业大学")
    @PostMapping("/getProjecListGDGY")
    public BaseResponse getProjecListGDGY(@RequestBody PreProjectRequest request) {
        return preFeign.getProjecListGDGY(request);
    }

    @ApiOperation(value = "根据项目储备id获取项目id")
    @GetMapping("/getPIdByPprId")
    public BaseResponse getPIdByPprId(@RequestParam("pprId") String pprId) {
        log.info("getPIdByPprId");
        return preFeign.getPIdByPprId(pprId);
    }



    /**
     * 广工
     * 以项目统计查询设计变更和签证变更
     */
    @PostMapping("/getProjectContractChangeList")
    public BaseResponse getProjectContractChangeList(@RequestBody PreProjectRequest request,@RequestHeader String token) {
        log.info("getProjectChangeList");
        return preFeign.getProjectContractChangeList(request,token);
    }

    /**
     * 广工
     * 根据指定项目ID 查询 设计变更 和 签证变更
     */
    @PostMapping("/getProjectContractChangeListBypId")
    public BaseResponse getProjectContractChangeListBypId(@RequestBody ConEcAndVisFeeModel request, @RequestHeader String token) {
        log.info("getProjectChangeListById");
        return preFeign.getProjectContractChangeListBypId(request,token);
    }

    @PostMapping("/getProjectHHTZList")
    public BaseResponse getProjectHHTZList(@RequestBody PreProjectRequest request, @RequestHeader String token) {
        log.info("getProjectHHTZList");
        return preFeign.getProjectHHTZList(request,token);
    }

    /**
     * 在建设模式变更时刷新项目编码
     *
     * @param pId
     * @param oldPBuildModel
     * @return
     */
    @ApiOperation("在建设模式变更时刷新项目编码")
    @GetMapping("/refreshProjectCode")
    public BaseResponse refreshProjectCode(@RequestParam String pId, @RequestParam String oldPBuildModel) {
        log.info("refreshProjectCode");
        return preFeign.refreshProjectCode(pId, oldPBuildModel);
    }
}
