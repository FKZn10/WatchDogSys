package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.FilterObject;
import com.jpmsoft.common.model.export.response.MonthlyReportExportModel;
import com.jpmsoft.common.model.request.project.PreProjectApplicationStaRequest;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConEcAndVisFeeModel;
import com.jpmsoft.common.model.requestAndResponse.Project.ProInvInfoModel;
import com.jpmsoft.common.model.requestAndResponse.Workbench.WorkbenchOverviewModel;
import com.jpmsoft.common.model.response.Project.*;
import com.jpmsoft.projectProducer.pojo.dto.project.ConfigRelationDTO;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConTypeStatisticsVO;
import com.jpmsoft.projectProducer.pojo.vo.project.*;
import com.jpmsoft.projectProducer.pojo.vo.sys.SysUserVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;


public interface PreProjectMapper extends BaseMapper<PreProject> {


    @Select("select d_t_id from config_Relation where d_id=#{did} and (P_Build_Model is null or P_Build_Model = '') limit 1")
    String selectConfigRelation(@Param("did") String did);

    @Select("select * from config_Relation where P_Build_Model = #{pBuildModel} and State = 7")
    List<ConfigRelationDTO> getConfigRelationListByPBuildModel(@Param("pBuildModel") String pBuildModel);

    ProAmtSumModel getPreProjectConSum(@Param("params") Map<String, Object> params, @Param("exclude") boolean exclude);
    ProAmtSumModel getPreProjectAmtSum(@Param("params") Map<String, Object> params, @Param("exclude") boolean exclude);

    List<PreProjectResponse> getPreProjectList(@Param("params") Map<String, Object> params);
    List<PreProjectResponse> getProjectListExcel(@Param("params") Map<String, Object> params);

    ProAmtSumModel getPreProjectConSumTZ(@Param("params") Map<String, Object> params, @Param("exclude") boolean exclude);
    ProAmtSumModel getPreProjectAmtSumTZ(@Param("params") Map<String, Object> params, @Param("exclude") boolean exclude);
    List<PreProjectResponse> getProjectTZList(@Param("params") Map<String, Object> params);
    Integer getProjectTZListCount(@Param("params") Map<String, Object> params);
    List<ConTypeStatisticsVO> getConTypeStatistics(@Param("conAuthSql") String conAuthSql,@Param("projectIds") List<String> projectIds);
    List<PreProjectResponse> getProjectListConSum(@Param("params") Map<String, Object> params,@Param("projectIds") List<String> projectIds);

    /**
     * 查询当前页项目的合同金额与已审批合同支付金额。
     */
    List<PreProjectResponse> getProjectProgressAmounts(@Param("conAuthSql") String conAuthSql, @Param("projectIds") List<String> projectIds);

    /** 按创建时间取得当前页每个项目最新全景记录的文件。 */
    List<PreProjectResponse> getLatestPanoramaFiles(@Param("projectIds") List<String> projectIds);


    List<PreProjectResponse> getAllProjectList(@Param("params") Map<String, Object> params);

    List<PreProjectResponse> getAllProjectByOrganization(@Param("uid") String uid);

    Integer getPreProjectListCount(@Param("params") Map<String, Object> params);


    List<PreProjectStreamlineResponse> getProjectStreamlineList(@Param("params") Map<String, Object> params);
    List<PreProjectStreamlineResponse> getProjectListForInvPlan(@Param("params") Map<String, Object> params);

    List<PreProjectStreamlineResponse> getDepartmentListForInvPlan(@Param("params") Map<String, Object> params);

    List<String> getAllAuthProjectId(@Param("authSql")  String authSql);

    Integer getProjectStreamlineListCount(@Param("params") Map<String, Object> params);
    Integer getProjectListForInvPlanCount(@Param("params") Map<String, Object> params);

    List<PreProjectStreamlineResponse> getProjectListNoAuth(@Param("params") Map<String, Object> params);

    Integer getProjectListNoAuthCount(@Param("params") Map<String, Object> params);

    List<PreProjectStreamlineResponse> getProjectListBackstage(@Param("page") IPage<PreProjectStreamlineResponse> page, @Param("params") Map<String, Object> params);

    List<PreProjectWhenGetAllResponse> getAllPreProject(@Param("deleted") Boolean deleted, @Param("authSql") String authSql
            , @Param("params") Map<String, Object> params);

    PreProjectResponse getPreProjectById(@Param("id") String id);


    @Select(" select " +
            " P_Project_Stage  as \"projectStage\", " +
            " count(1)  as \"stageCount\" " +
            " from" +
            " Pre_Project  " +
            " where P_Status=1 and deleted!=true " +
            " and P_Project_Stage is not null " +
            " group by P_Project_Stage ")
    List<Map<String, Object>> statisticsProject();


    WorkbenchOverviewModel getWorkbenchOverviewModel(@Param("qsAddToday") String qsAddToday,@Param("year") int year,@Param("teId")  String teId);

    List<InvestmentAccountModel> getInvestmentDate(@Param("teId") String teId);


    ProInvInfoModel getProInvInfoByPid(@Param("pId") String pId);

    List<MonthlyReportExportModel> getProMonthlyReport(@Param("start") Integer start, @Param("size") Integer size,
                                                       @Param("sortFiled") String sortFiled, @Param("sortType") String sortType,
                                                       @Param("year") Integer year);

    @Select("select P_Parent_ID from Pre_Project where P_ID=#{pId}")
    String getProjectParentId(@Param("pId") String pId);

    @Select("select P_ID from Pre_Project where P_Parent_ID=#{pId} and State!=3")
    List<String> getAllChildPids(@Param("pId") String pId);

    @Select("select sum(P_Inv_Total) from Pre_Project where state=7 and deleted=false")
    BigDecimal getProjectInvTotal();

    ProApprovalInfoVo getProApprovalInfo(@Param("pId") String pId);

    List<String> getUserAllPid(@Param("authSql") String authSql,@Param("teId") String teId);


    /**
     * 项目资金统计
     */
    List<ProjectFundStatisticsVO> getProjectFundStatistics(@Param("page") IPage<ProjectFundStatisticsVO> page, @Param("params") Map<String, Object> params);

    ProjectFundStatisticsSumVO getProjectFundStatisticsSum(@Param("params") Map<String, Object> params);

    @Update("update Pre_Project set P_Project_Stage=#{stage} where P_ID=#{pId}")
    Integer updateProjectStage(@Param("pId")  String pId,@Param("stage") String stage);


    ProjectApproveBudgetRateDTO getApproveBudgetAndRate(@Param("pId") String pId);
    List<Object> getValueByCodeWithPid(@Param("value")String value ,@Param("projectIds")List<String> projectIds);
    List<FilterObject> getCompanyValueByCodeWithPid(@Param("value")String value ,@Param("projectIds")List<String> projectIds);
    List<FilterObject> getPersonValueByCodeWithPid(@Param("value")String value ,@Param("projectIds")List<String> projectIds);
    Integer getPreProjectTZListCount(@Param("params") Map<String, Object> query);

    @Select(" select  to_id from SYS_T_Organization where to_status=1 limit 1")
    String getOneTOrganizationId();

    @Select(" select  to_id from SYS_T_Organization where to_name=#{name} limit 1")
    String getOneTOrganizationIdByName(@Param("name") String name);

    List<SysUserVo> queryDockptPreList(@Param("dIds") List<String> dIds);

    List<PreProjectResponse> getSYSDepartmentPList(@Param("pdIds") List<String> pdIds);

    ProjectCjGsGsFenJieVO getProjectCjGsGsFenJie(@Param("pid")  String pid);

    IPage<PreProjectApplicationStaResponse> getPreprojectStaList(@Param("page") Page<PreProjectApplicationStaResponse> page,@Param("request")  PreProjectApplicationStaRequest request);

    IPage<PreProjectApplicationStaResponse> getPreprojectStaByPId(@Param("page") Page<PreProjectApplicationStaResponse> page,@Param("request") PreProjectApplicationStaRequest request);

    IPage<PreProjectApplicationStaResponse> getApplicationStaList(@Param("page") Page<PreProjectApplicationStaResponse> page,@Param("request")  PreProjectApplicationStaRequest request);

    IPage<PreProjectApplicationStaResponse> getApplicationStaByPId(Page<Object> objectPage, PreProjectApplicationStaRequest request);

    /**
     * 批量查询当前页项目的未删除直接子项目，不附加项目可见性条件。
     *
     * @param parentIds 当前页父项目 ID
     * @return 子项目 ID 和父项目 ID
     */
    List<PreProjectWhenGetAllResponse> getProjectChildrenByParentIds(@Param("parentIds") List<String> parentIds);

    /**
     * 沿用全量项目查询的权限及筛选条件，取得当前页中存在可见子项目的父项目 ID。
     *
     * @param deleted   项目删除标记
     * @param authSql   项目权限 SQL
     * @param params    项目筛选条件
     * @param parentIds 当前页父项目 ID
     * @return 存在可见子项目的父项目 ID
     */
    List<String> getVisibleChildParentIds(@Param("deleted") Boolean deleted, @Param("authSql") String authSql,
                                          @Param("params") Map<String, Object> params,
                                          @Param("parentIds") List<String> parentIds);

    Integer getProjectListChildProjectSum(@Param("pId")String pId);

    List<String> getUserDepartment(@Param("uId")String uId);

    List<String> getChildProjectIds(@Param("pId")String pId);

    List<PreProjectResponse> getProjectListByPCompetentDepartmentName(@Param("pIds") Set<String> pIds);

    List<PreProjectResponse> getProjectContractChangeList(@Param("params") Map<String, Object> query,
                                                          @Param("page") IPage<PreProjectResponse> page);

    List<ConEcAndVisFeeModel> getProjectContractChangeListBypId(@Param("params") Map<String, Object> query,
                                                                @Param("page") IPage<ConEcAndVisFeeModel> page);


    List<String>  getProjectDivision(@Param("province") String province, @Param("city") String city);
}
