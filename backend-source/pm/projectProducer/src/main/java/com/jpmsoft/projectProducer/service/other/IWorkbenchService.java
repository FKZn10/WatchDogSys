package com.jpmsoft.projectProducer.service.other;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConContractRequest;
import com.jpmsoft.common.model.request.Contract.ConEcRequest;
import com.jpmsoft.common.model.request.InvestmentControl.InvCompleteMonthRequest;
import com.jpmsoft.common.model.request.InvestmentControl.InvPlanYearRequest;
import com.jpmsoft.common.model.request.Qs.QsRecordRequest;
import com.jpmsoft.common.model.request.pay.ConPayRequest;

public interface IWorkbenchService {

    /**
     * 获取领导看板总览
     *
     * @return
     */
    BaseResponse getWorkbenchOverviewModel(String teId);

    /**
     * 质量安全二级页面 顶部块状数值统计
     *
     * @param qsrCategory
     * @return
     */
    BaseResponse getWorkbenchDetailStatistics(Integer qsrCategory,String teId);

    /**
     * 质量安全二级页面 统计点开后的详情
     *
     * @param request
     * @return
     */
    BaseResponse getWorkbenchDetailStatisticsList(QsRecordRequest request, String teId);

    /**
     * 获取质量安全最近七天数据
     *
     * @param qsrCategory
     * @return
     */
    BaseResponse getQsrLastSevenDaysStatistics(Integer qsrCategory,String teId);

    /**
     * 获取质量安全月度数据
     *
     * @param qsrCategory
     * @return
     */
    BaseResponse getQsMonthlyStatisticsByYear(Integer qsrCategory, String teId);

    /**
     * 统计年度季度月度质量安全
     *
     * @param request
     * @return
     */
    BaseResponse getQsrYearQuarterMonthStatistics(QsRecordRequest request);


    /**
     * 合同类型分组统计合同
     *
     * @return
     */
    BaseResponse getContractStatisticsGroupConType(String teId);

    /**
     * 获取合同总览
     *
     * @return
     */
    BaseResponse getContractStatisticsOverview(String teId);

    /**
     * 获取合同总览列表
     *
     * @param request
     * @param teId
     * @return
     */
    BaseResponse getContractStatisticsOverviewList(ConContractRequest request, String teId);

    /**
     * 获取合同签订情况
     *
     * @return
     */
    BaseResponse getContractStatisticsSigning(String teId);

    /**
     * 招标方式统计合同
     *
     * @return
     */
    BaseResponse getContractStatisticsBiddingType(String teId);


    /**
     * 获取支付总览
     *
     * @return
     */
    BaseResponse getConPayStatisticsOverview(String teId);

    /**
     * 获取支付总览列表
     *
     * @param request
     * @param teId
     * @return
     */
    BaseResponse getConPayStatisticsOverviewList(ConPayRequest request, String teId);

    /**
     * 分组查询各部门当年完成支付情况
     *
     * @return
     */
    BaseResponse getConPayStatisticsGroupByDep(String teId);


    /**
     * 统计当年每月支付情况
     *
     * @return
     */
    BaseResponse getConPayStatisticsByMonth(String teId);


    /**
     * 项目支付年度排行榜
     *
     * @param size
     * @return
     */
    BaseResponse getConPayStatisticsRanking(Integer size,String teId);


    /**
     * 投资计划总览
     *
     * @return
     */
    BaseResponse getInvStatisticsOverview(String teId);

    /**
     * 获取年度投资计划统计总览列表
     *
     * @param request
     * @param teId
     * @return
     */
    BaseResponse getInvPlanYearStatisticsOverviewList(InvPlanYearRequest request, String teId);

    /**
     * 获取月度投资计划统计总览列表
     *
     * @param request
     * @param teId
     * @return
     */
    BaseResponse getInvMonthlyScheduleStatisticsOverviewList(InvPlanYearRequest request, String teId);

    /**
     * 获取月度投资完成统计总览列表
     *
     * @param request
     * @param teId
     * @return
     */
    BaseResponse getInvCompleteMonthStatisticsOverviewList(InvCompleteMonthRequest request, String teId);

    /**
     * 统计当年各组织机构每月计划完成投资
     *
     * @return
     */
    BaseResponse getInvStatisticsGroupDep(String teId);

    /**
     * 月度投资计划完成情况
     *
     * @return
     */
    BaseResponse getInvStatisticsByComplete(String teId);

    /**
     * 年度投资完成率排行榜
     *
     * @param size
     * @return
     */
    BaseResponse getInvStatisticsByRanking(Integer size,String teId);

    /**
     * 变更总览
     *
     * @return
     */
    BaseResponse getConEcStatisticsOverview(String teId);

    /**
     * 获取变更总览列表
     *
     * @param request
     * @param teId
     * @return
     */
    BaseResponse getConEcStatisticsOverviewList(ConEcRequest request, String teId);

    /**
     * 变更等级分布
     *
     * @return
     */
    BaseResponse getConEcStatisticsByGrade(String teId);

    /**
     * 合同变更情况
     *
     * @return
     */
    BaseResponse getConEcStatisticsChangeSituation(String teId);

    /**
     * 变更详情列表
     *
     * @param size
     * @return
     */
    BaseResponse getConEcStatisticsByRanking(Integer size,String teId);
}
