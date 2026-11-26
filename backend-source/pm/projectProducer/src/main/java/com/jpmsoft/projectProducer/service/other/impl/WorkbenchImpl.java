package com.jpmsoft.projectProducer.service.other.impl;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConContractRequest;
import com.jpmsoft.common.model.request.Contract.ConEcRequest;
import com.jpmsoft.common.model.request.InvestmentControl.InvCompleteMonthRequest;
import com.jpmsoft.common.model.request.InvestmentControl.InvPlanYearRequest;
import com.jpmsoft.common.model.request.Qs.QsRecordRequest;
import com.jpmsoft.common.model.request.pay.ConPayRequest;
import com.jpmsoft.projectProducer.service.contract.IConContractService;
import com.jpmsoft.projectProducer.service.contract.IConEcService;
import com.jpmsoft.projectProducer.service.contract.IConPayService;
import com.jpmsoft.projectProducer.service.investment.IInvCompleteMonthService;
import com.jpmsoft.projectProducer.service.investment.IInvMonthlyScheduleService;
import com.jpmsoft.projectProducer.service.investment.IInvPlanYearService;
import com.jpmsoft.projectProducer.service.other.IWorkbenchService;
import com.jpmsoft.projectProducer.service.project.IPreProjectService;
import com.jpmsoft.projectProducer.service.qualitySafety.IQsRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WorkbenchImpl implements IWorkbenchService {

    @Resource
    private IPreProjectService preProjectService;
    @Resource
    private IQsRecordService qsRecordService;
    @Resource
    private IConContractService conContractService;
    @Resource
    private IConPayService conPayService;
    @Resource
    private IInvPlanYearService invPlanYearService;
    @Resource
    private IConEcService conEcService;
    @Resource
    private IInvMonthlyScheduleService invMonthlyScheduleService;
    @Resource
    private IInvCompleteMonthService invCompleteMonthService;


    @Override
    public BaseResponse getWorkbenchOverviewModel(String teId) {
        return BaseResponse.ok(preProjectService.getWorkbenchOverviewModel(teId));
    }

    @Override
    public BaseResponse getWorkbenchDetailStatistics(Integer qsrCategory,String teId) {
        return BaseResponse.ok(qsRecordService.getWorkbenchDetailStatistics(qsrCategory,teId));
    }

    /**
     * 质量安全二级页面 统计点开后的详情
     *
     * @param request
     * @param teId
     * @return
     */
    @Override
    public BaseResponse getWorkbenchDetailStatisticsList(QsRecordRequest request, String teId) {
        return qsRecordService.getWorkbenchDetailStatisticsList(request, teId);
    }

    @Override
    public BaseResponse getQsrLastSevenDaysStatistics(Integer qsrCategory,String teId) {
        return qsRecordService.getLastSevenDaysStatistics(qsrCategory,teId);
    }

    @Override
    public BaseResponse getQsMonthlyStatisticsByYear(Integer qsrCategory, String teId) {
        return qsRecordService.getQsMonthlyStatisticsByYear(qsrCategory,teId);
    }

    @Override
    public BaseResponse getQsrYearQuarterMonthStatistics(QsRecordRequest request) {
        return qsRecordService.getYearQuarterMonthStatistics(request);
    }

    @Override
    public BaseResponse getContractStatisticsGroupConType(String teId) {
        return conContractService.getContractStatisticsGroupConType(teId);
    }

    @Override
    public BaseResponse getContractStatisticsOverview(String teId) {
        return conContractService.getContractStatisticsOverview(teId);
    }

    /**
     * 获取合同总览列表
     *
     * @param request
     * @param teId
     * @return
     */
    @Override
    public BaseResponse getContractStatisticsOverviewList(ConContractRequest request, String teId) {
        return conContractService.getContractStatisticsOverviewList(request, teId);
    }

    @Override
    public BaseResponse getContractStatisticsSigning(String teId) {
        return conContractService.getContractStatisticsSigning(teId);
    }

    @Override
    public BaseResponse getContractStatisticsBiddingType(String teId) {
        return conContractService.getContractStatisticsBiddingType(teId);
    }

    @Override
    public BaseResponse getConPayStatisticsOverview(String teId) {
        return conPayService.getConPayStatisticsOverview(teId);
    }

    /**
     * 获取支付总览列表
     *
     * @param request
     * @param teId
     * @return
     */
    @Override
    public BaseResponse getConPayStatisticsOverviewList(ConPayRequest request, String teId) {
        return conPayService.getConPayStatisticsOverviewList(request, teId);
    }

    @Override
    public BaseResponse getConPayStatisticsGroupByDep(String teId) {
        return conPayService.getConPayStatisticsGroupByDep(teId);
    }

    @Override
    public BaseResponse getConPayStatisticsByMonth(String teId) {
        return conPayService.getConPayStatisticsByMonth(teId);
    }

    @Override
    public BaseResponse getConPayStatisticsRanking(Integer size,String teId) {
        return conPayService.getConPayStatisticsRanking(size,teId);
    }

    @Override
    public BaseResponse getInvStatisticsOverview(String teId) {
        return invPlanYearService.getInvStatisticsOverview(teId);
    }

    /**
     * 获取年度投资计划统计总览列表
     *
     * @param request
     * @param teId
     * @return
     */
    @Override
    public BaseResponse getInvPlanYearStatisticsOverviewList(InvPlanYearRequest request, String teId) {
        return invPlanYearService.getInvPlanYearStatisticsOverviewList(request, teId);
    }

    /**
     * 获取月度投资计划统计总览列表
     *
     * @param request
     * @param teId
     * @return
     */
    @Override
    public BaseResponse getInvMonthlyScheduleStatisticsOverviewList(InvPlanYearRequest request, String teId) {
        return invMonthlyScheduleService.getInvMonthlyScheduleStatisticsOverviewList(request, teId);
    }

    /**
     * 获取月度投资完成统计总览列表
     *
     * @param request
     * @param teId
     * @return
     */
    @Override
    public BaseResponse getInvCompleteMonthStatisticsOverviewList(InvCompleteMonthRequest request, String teId) {
        return invCompleteMonthService.getInvCompleteMonthStatisticsOverviewList(request, teId);
    }

    @Override
    public BaseResponse getInvStatisticsGroupDep(String teId) {
        return invPlanYearService.getInvStatisticsGroupDep(teId);
    }

    @Override
    public BaseResponse getInvStatisticsByComplete(String teId) {
        return invPlanYearService.getInvStatisticsByComplete(teId);
    }

    @Override
    public BaseResponse getInvStatisticsByRanking(Integer size,String teId) {
        return invPlanYearService.getInvStatisticsByRanking(size,teId);
    }

    @Override
    public BaseResponse getConEcStatisticsOverview(String teId) {
        return conEcService.getConEcStatisticsOverview(teId);
    }

    /**
     * 获取变更总览列表
     *
     * @param request
     * @param teId
     * @return
     */
    @Override
    public BaseResponse getConEcStatisticsOverviewList(ConEcRequest request, String teId) {
        return conEcService.getConEcStatisticsOverviewList(request, teId);
    }

    @Override
    public BaseResponse getConEcStatisticsByGrade(String teId) {
        return conEcService.getConEcStatisticsByGrade(teId);
    }

    @Override
    public BaseResponse getConEcStatisticsChangeSituation(String teId) {
        return conEcService.getConEcStatisticsChangeSituation(teId);
    }

    @Override
    public BaseResponse getConEcStatisticsByRanking(Integer size,String teId) {
        return conEcService.getConEcStatisticsByRanking(size,teId);
    }
}
