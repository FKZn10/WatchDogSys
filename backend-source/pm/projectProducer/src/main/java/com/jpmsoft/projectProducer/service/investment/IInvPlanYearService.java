package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.model.request.InvestmentControl.InvCompleteMonthRequest;
import com.jpmsoft.common.model.request.InvestmentControl.InvPlanYearRequest;
import com.jpmsoft.common.model.request.InvestmentControl.InvPlanYearSearchRequest;
import com.jpmsoft.common.model.requestAndResponse.Project.ProInvInfoModel;
import com.jpmsoft.common.model.response.InvestmentControl.InvPlanYearResponse;
import com.jpmsoft.projectProducer.config.PageResult;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvPlanYear;
import com.jpmsoft.projectProducer.pojo.vo.project.ProjectInvStatisticsVo;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

import java.util.List;

public interface IInvPlanYearService extends IService<InvPlanYear> {

    BaseResponse createOrUpdateInvPlanYear(InvPlanYearRequest request);

    BaseResponse getInvPlanYearList(InvPlanYearRequest request, String uId);

    PageResult<InvPlanYearResponse> getInvPlanYearSearchList(InvPlanYearSearchRequest request, String uId);

    List<ClassInfoAnnotation> getSearchTableList();

    BaseResponse getInvPlanYearByIpvId(String ipvId);

    BaseResponse getInvPlanYearByPIdAndYear(InvPlanYearRequest request);

    BaseResponse canToCreateInvPlanYear(InvPlanYearRequest request);

    BaseResponse getInvPlanYearByPDYear(InvPlanYearRequest request);


    BaseResponse getInvStatisticsOverview(String teId);

    BaseResponse getInvPlanYearStatisticsOverviewList(InvPlanYearRequest request, String teId);

    BaseResponse getInvStatisticsGroupDep(String teId);

    BaseResponse getInvStatisticsByComplete(String teId);

    BaseResponse getInvStatisticsByRanking(Integer size,String teId);

    ProInvInfoModel getInvInfoByPidAndTimeYear(String pId, Integer start, Integer end);

    List<InvPlanYear> getInvPlanYearByYearAndPids(List<String> pIds, Integer year);

    /**
     * 查询项目年度投资统计
     *
     * @param year
     * @return
     */
    ProjectInvStatisticsVo getInvPlanYearInvStatistics(Integer year);


    BaseResponse createOrUpdateInvPlanYearXz(InvPlanYearRequest request);

    BaseResponse getInvPlanYearByPDYearXz(InvPlanYearRequest request);

    BaseResponse updatePreState(String pid, String preState,String icmId);

    BaseResponse getInvPlanYearByIpvIdXz(String ipvId);
    

    BaseResponse determineAnnualPlan(String pId);

    BaseResponse determinemonthlyPlan(String pId, Integer year, Integer month);

    BaseResponse getPlanOverview(String pName, String strYear, Integer page, Integer size, String pCompetentDepartment, String pSecType, String projectState);

    BaseResponse writeBackMonthPlan(String ipvId,String uid);

    BaseResponse getMonthplanClassInfo(InvCompleteMonthRequest request,  LogUserInfoVo user);

    BaseResponse getMonthplanList(InvCompleteMonthRequest request, LogUserInfoVo user);

    BaseResponse syncToInvPlanYearYw(String ipvId);
}
