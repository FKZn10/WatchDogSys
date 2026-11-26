package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.project.*;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConEcAndVisFeeModel;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectDtlModel;
import com.jpmsoft.common.model.requestAndResponse.Workbench.WorkbenchOverviewModel;
import com.jpmsoft.common.model.response.Project.PreProjectResponse;
import com.jpmsoft.projectProducer.pojo.dto.project.ProMonthlyReportDto;
import com.jpmsoft.projectProducer.pojo.dto.project.ProjectFundStatisticsDTO;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

import java.util.List;
import java.util.Set;

public interface IPreProjectService extends IService<PreProject> {

    /**
     * 统计金额 总投资，概算，估算，匡算
     *
     * @param request
     * @param uId
     * @param exclude 是否排除作废和草稿状态的项目
     * @return
     */
    BaseResponse getPreProjectAmtSum(PreProjectRequest request, String uId, boolean exclude, String conAuthSql);

    /**
     * 获取项目信息列表
     *
     * @param request
     * @return
     */
    BaseResponse getProjectList(PreProjectRequest request, String uId);

    List<PreProjectResponse> getProjectListExcel(PreProjectRequest request, String uId);


    BaseResponse getProjectStatisticsList(PreProjectRequest request, String uId);

    List<PreProjectResponse> getAllProjectList(PreProjectRequest request, String uId);

    /**
     * 获取项目信息列表 (响应字段精简)
     *
     * @param request
     * @return
     */
    BaseResponse getProjectStreamlineList(PreProjectRequest request, String uId);

    BaseResponse getProjectListForInvPlan(PreProjectRequest request, String uId);

    BaseResponse getDepartmentListForInvPlan(PreProjectRequest request, String uId);

    /**
     * 城建定制接口
     * 已委托全过程咨询/招标代理,签订委托合同并且在建设程序模块->办理成果->立项批复已经审批结束之后,
     * 招标计划才可以选则的项目
     *
     * @param request
     * @param uId
     * @return
     */
    BaseResponse getProjectCjBiddingPlanList(PreProjectRequest request, String uId);

    /**
     * 获取项目信息列表 (响应字段精简)
     * 仅根据项目组织机构鉴权
     *
     * @param request
     * @param uId
     * @return
     */
    BaseResponse getProjectByProOrganization(PreProjectRequest request, String uId);

    /**
     * 获取项目信息列表 (响应字段精简)
     *
     * @param request
     * @return
     */
    BaseResponse getProjectListNoAuth(PreProjectRequest request);

    /**
     * 获取项目信息列表 (响应字段精简)
     * admin默认查询所有项目，其他用户仅查看当前用户组织机构相关项目
     *
     * @param request
     * @return
     */
    BaseResponse getProjectListBackstage(PreProjectRequest request, LogUserInfoVo user);

    BaseResponse createOrUpdatePreProject(PreProjectRequest request, String uId, boolean skipCodeGeneration);

    /**
     * 修改项目信息
     *
     * @param request
     * @return
     */
    BaseResponse createOrUpdatePreProject(PreProjectRequest request, String uId);

    BaseResponse createOrUpdatePreProjectCopy(PreProjectRequest request, String uId);

    /**
     * 国资平台项目数据接入
     *
     * @param request
     * @return
     */
    BaseResponse saveForGZ(PreProjectRequestDto request);


    /**
     * 修改项目信息
     *
     * @param request
     * @return
     */
    BaseResponse BjCreateOrUpdatePreProject(PreProjectRequest request, String uId);


    /**
     * 获取项目信息Select
     *
     * @return
     */
    BaseResponse getProjectSelect(PreProjectRequest request, String uId);

    /**
     * 查询所有项目信息
     * (部分字段)
     *
     * @return
     */
    BaseResponse getAllPreProject(PreProjectRequest request, String uId);


    BaseResponse getAllOutOrgPreProject(PreProjectRequest request, String uId);

    /**
     * ID查询
     *
     * @param request
     * @return
     */
    BaseResponse getProjectByPid(PreProjectRequest request);

    BaseResponse getProjectByPidCopy(PreProjectRequest request);

    /**
     * 获取项目信息列表
     * 单位万元
     *
     * @param request
     * @return
     */
    BaseResponse getProjectWYList(PreProjectRequest request, String uId);

    /**
     * ID查询
     * 单位万元
     *
     * @param request
     * @return
     */
    BaseResponse getProjectWYByPid(PreProjectRequest request);


    /**
     * 假删除
     *
     * @param pId
     * @return
     */
    BaseResponse deleteProjectByPid(String pId);


    List<PreProject> getProjectList(List<String> idlist);

    /**
     * 新增 内部使用
     *
     * @param entity
     */
    void insertPreProject(PreProject entity);

    /**
     * 新增 項目匡算
     *
     * @param entity
     */
    void insertPreProjectDtl(List<PreProjectDtlModel> entity, String pId);

    /**
     * 根据项目ID获取项目匡算信息
     *
     * @param pId
     */
    List<PreProjectDtlModel> getPreProjectDtlByPId(String pId);

    /**
     * 统计项目，
     * 立项阶段，
     * 初步设计
     * 施工设计图
     *
     * @return
     */
    BaseResponse statisticsProject(String teId);

    /**
     * 获取领导看板总览数据
     *
     * @return
     */
    WorkbenchOverviewModel getWorkbenchOverviewModel(String teId);


    BaseResponse updateProjectCodeById(String pId, String pCode, String uId);

    BaseResponse getInvestmentDate(String teId);

    /**
     * 根据ID查询项目部分信息即投资信息
     *
     * @param pId
     * @return
     */
    BaseResponse getProInvInfoByPid(String pId);


    /**
     * 年月获取项目月报
     * 导出
     *
     * @return
     */
    BaseResponse getProMonthlyReport(ProMonthlyReportDto request);

    /**
     * 判断是否是父项目
     *
     * @param pId
     * @return
     */
    Boolean isParentProject(String pId);

    /**
     * 获取所有子项目ID
     *
     * @param pId
     * @return
     */
    List<String> getAllChildPids(String pId);

    /**
     * 查询项目投资统计
     *
     * @param request
     * @return
     */
    BaseResponse getProjectInvStatistics(ProMonthlyReportDto request);

    /**
     * 项目ID查询批复信息
     *
     * @param pId
     * @return
     */
    BaseResponse getProApprovalInfo(String pId);


    /**
     * 校验项目注册是否已生成过项目
     *
     * @param prId
     * @return
     */
    Boolean registerProjectExist(String prId);

    /**
     * 获取当前用户可查看的所有项目列表
     *
     * @param uId
     * @return
     */
    BaseResponse getUserAllPid(LogUserInfoVo uId);

    /**
     * 用户ID获取其所在所有项目组织机构的项目信息
     *
     * @param id
     * @return
     */
    BaseResponse getAllProjectByOrganization(String id);


    /**
     * 项目资金统计
     *
     * @param request
     * @return
     */
    BaseResponse getProjectFundStatistics(ProjectFundStatisticsDTO request, String uId);


    BaseResponse getApproveBudgetAndRate(String pId);


    void updateProjectStage(String pId, String stage);


    List<String> getAllAuthProjectId(String uid);

    BaseResponse getPreProjectDtlByIds(List<String> pIds);

    BaseResponse getProjectCompetentDepartmentByPid(PreProjectRequest request);

    BaseResponse updateProjectAuthType(String id, Integer type);

    BaseResponse getProjectClassInfo(List<String> projectIds, String uid);

    BaseResponse getProjectTZList(PreProjectTZRequest request, String uid);

    Boolean updatePreState(String pid, String preState);

    BaseResponse updateProjectInfo(PreProjectRequest request, String uid);

    void projectCreate2ImpTempDepartment(String pId);

    void projectApproved2ImpTempDepartment(String pId);

    void projectApproved2ImpTempDepartment(String pId, Boolean isReserve);

    BaseResponse approvalData(ProjectRequest request, String token);

    BaseResponse queryDockptPreList(String dId, String pName, Integer page, Integer size);

    /**
     * 根据投资估算，概算，概算金额 限制招标计划，合同，支付的发起
     *
     * @param pid
     * @return
     */
    BaseResponse getProjectCjGsGsFenJie(String pid);

    BaseResponse getProjecListNJCJ(PreProjectTZRequest request, String token);

    BaseResponse getProjecListNJCJAuth(PreProjectTZRequest request, String uid);

    BaseResponse getPreprojectStaList(PreProjectApplicationStaRequest request, String uId);

    BaseResponse getPreprojectStaByPId(PreProjectApplicationStaRequest request);

    BaseResponse getApplicationStaList(PreProjectApplicationStaRequest request, String uid);

    BaseResponse getApplicationStaByPId(PreProjectApplicationStaRequest request);

    BaseResponse getProjecListGDGY(PreProjectRequest request);

    BaseResponse getProjectGDGY(PreProjectRequest request);

    BaseResponse getPIdByPprId(String pprId);

    void updateProjectPersonnel(String pId, String rpCreateUid);

    List<PreProjectResponse> getProjectListByPCompetentDepartmentName(Set<String> pIds);

    Boolean existProjectName(String pName,String pId);

    BaseResponse getProjectContractChangeList(PreProjectRequest request, String uid);

    BaseResponse getProjectContractChangeListBypId(ConEcAndVisFeeModel pId, String uid);

    BaseResponse getProjectDivision(String province, String city);

    BaseResponse getProjectHHTZList(PreProjectRequest request, String uid);

    BaseResponse getProjectFundStatisticsTree(ProjectFundStatisticsDTO request, String uId);

    BaseResponse refreshProjectCode(String pId, String oldPBuildModel);

    /**
     * 获取项目进度
     *
     * @param request 项目分页查询条件
     * @param uId 当前用户 ID
     * @return 带合同金额及支付占比的项目分页结果
     */
    BaseResponse getProjectProgress(PreProjectRequest request, String uId);

    PreProjectResponse selectById(String pId);
}
