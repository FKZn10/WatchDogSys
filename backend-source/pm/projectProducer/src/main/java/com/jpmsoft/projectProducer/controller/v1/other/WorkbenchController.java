package com.jpmsoft.projectProducer.controller.v1.other;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConContractRequest;
import com.jpmsoft.common.model.request.Contract.ConEcRequest;
import com.jpmsoft.common.model.request.InvestmentControl.InvCompleteMonthRequest;
import com.jpmsoft.common.model.request.InvestmentControl.InvPlanYearRequest;
import com.jpmsoft.common.model.request.Qs.QsRecordRequest;
import com.jpmsoft.common.model.request.pay.ConPayRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.other.IWorkbenchService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/kb")
public class WorkbenchController {

    @Resource
    private IWorkbenchService workbenchService;

    @Resource
    private RedisUtils redisUtils;

    /**
     * 获取领导看板总览
     *
     * @return
     */
    @GetMapping("/getWorkbenchOverviewModel")
    public BaseResponse getWorkbenchOverviewModel(@RequestHeader String token) {
        log.info("getWorkbenchOverviewModel");
        return workbenchService.getWorkbenchOverviewModel(redisUtils.getTeId(token));
    }

    /**
     * 质量安全二级页面 顶部块状数值统计
     *
     * @param qsrCategory
     * @return
     */
    @GetMapping("/getWorkbenchDetailStatistics")
    public BaseResponse getWorkbenchDetailStatistics(@RequestParam Integer qsrCategory, @RequestHeader String token) {
        log.info("getWorkbenchDetailStatistics");
        return workbenchService.getWorkbenchDetailStatistics(qsrCategory,redisUtils.getTeId(token));
    }

    /**
     * 质量安全二级页面 统计点开后的详情
     *
     * @param request
     * @return
     */
    @PostMapping("/getWorkbenchDetailStatisticsList")
    public BaseResponse getWorkbenchDetailStatisticsList(@RequestBody QsRecordRequest request, @RequestHeader String token) {
        log.info("getWorkbenchDetailStatisticsList");
        return workbenchService.getWorkbenchDetailStatisticsList(request, redisUtils.getTeId(token));
    }

    /**
     * 获取质量安全最近七天数据
     *
     * @param qsrCategory
     * @return
     */
    @GetMapping("/getQsrLastSevenDaysStatistics")
    public BaseResponse getQsrLastSevenDaysStatistics(@RequestParam Integer qsrCategory, @RequestHeader String token) {
        log.info("getQsrLastSevenDaysStatistics");
        return workbenchService.getQsrLastSevenDaysStatistics(qsrCategory,redisUtils.getTeId(token));
    }

    /**
     * 统计最近一年质量安全月度数据
     *
     * @param qsrCategory
     * @return
     */
    @GetMapping("/getQsMonthlyStatisticsByYear")
    public BaseResponse getQsMonthlyStatisticsByYear(@RequestParam Integer qsrCategory, @RequestHeader String token) {
        log.info("getQsMonthlyStatisticsByYear");
        return workbenchService.getQsMonthlyStatisticsByYear(qsrCategory,redisUtils.getTeId(token));
    }

    /**
     * 统计年度季度月度质量安全
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/getQsrYearQuarterMonthStatistics")
    public BaseResponse getQsrYearQuarterMonthStatistics(@RequestBody QsRecordRequest request) {
        log.info("getQsrYearQuarterMonthStatistics");
        return workbenchService.getQsrYearQuarterMonthStatistics(request);
    }

    /**
     * 合同类型分组统计合同
     *
     * @return
     */
    @GetMapping("/getContractStatisticsGourpConType")
    public BaseResponse getContractStatisticsGroupConType(@RequestHeader String token) {
        log.info("getContractStatisticsGroupConType");
        return workbenchService.getContractStatisticsGroupConType(redisUtils.getTeId(token));
    }

    /**
     * 获取合同总览
     *
     * @return
     */
    @GetMapping("/getContractStatisticsOverview")
    public BaseResponse getContractStatisticsOverview(@RequestHeader String token) {
        log.info("getContractStatisticsOverview");
        return workbenchService.getContractStatisticsOverview(redisUtils.getTeId(token));
    }

    /**
     * 获取合同总览列表
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/getContractStatisticsOverviewList")
    public BaseResponse getContractStatisticsOverviewList(@RequestBody ConContractRequest request, @RequestHeader String token) {
        log.info("getContractStatisticsOverviewList");
        return workbenchService.getContractStatisticsOverviewList(request, redisUtils.getTeId(token));
    }

    /**
     * 获取合同签订情况
     *
     * @return
     */
    @GetMapping("/getContractStatisticsSigning")
    public BaseResponse getContractStatisticsSigning(@RequestHeader String token) {
        log.info("getContractStatisticsSigning");
        return workbenchService.getContractStatisticsSigning(redisUtils.getTeId(token));
    }

    /**
     * 招标方式统计合同
     *
     * @return
     */
    @GetMapping("/getContractStatisticsBiddingType")
    public BaseResponse getContractStatisticsBiddingType(@RequestHeader String token) {
        log.info("getContractStatisticsBiddingType");
        return workbenchService.getContractStatisticsBiddingType(redisUtils.getTeId(token));
    }

    /**
     * 获取支付总览
     *
     * @return
     */
    @GetMapping("/getConPayStatisticsOverview")
    public BaseResponse getConPayStatisticsOverview(@RequestHeader String token) {
        log.info("getConPayStatisticsOverview");
        return workbenchService.getConPayStatisticsOverview(redisUtils.getTeId(token));
    }

    /**
     * 获取支付总览列表
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/getConPayStatisticsOverviewList")
    public BaseResponse getConPayStatisticsOverviewList(@RequestBody ConPayRequest request, @RequestHeader String token) {
        log.info("getConPayStatisticsOverviewList");
        return workbenchService.getConPayStatisticsOverviewList(request, redisUtils.getTeId(token));
    }

    /**
     * 分组查询各部门当年完成支付情况
     *
     * @return
     */
    @GetMapping("/getConPayStatisticsGroupByDep")
    public BaseResponse getConPayStatisticsGroupByDep(@RequestHeader String token) {
        log.info("getConPayStatisticsGroupByDep");
        return workbenchService.getConPayStatisticsGroupByDep(redisUtils.getTeId(token));
    }

    /**
     * 统计当年每月支付情况
     *
     * @return
     */
    @GetMapping("/getConPayStatisticsByMonth")
    public BaseResponse getConPayStatisticsByMonth(@RequestHeader String token) {
        log.info("getConPayStatisticsByMonth");
        return workbenchService.getConPayStatisticsByMonth(redisUtils.getTeId(token));
    }

    /**
     * 项目支付年度排行榜
     *
     * @param size
     * @return
     */
    @GetMapping("/getConPayStatisticsRanking")
    public BaseResponse getConPayStatisticsRanking(@RequestParam Integer size,@RequestHeader String token) {
        log.info("getConPayStatisticsRanking");
        return workbenchService.getConPayStatisticsRanking(size,redisUtils.getTeId(token));
    }

    /**
     * 投资计划总览
     *
     * @return
     */
    @GetMapping("/getInvStatisticsOverview")
    public BaseResponse getInvStatisticsOverview(@RequestHeader String token) {
        log.info("getInvStatisticsOverview");
        return workbenchService.getInvStatisticsOverview(redisUtils.getTeId(token));
    }

    /**
     * 获取年度计划总览列表
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/getInvPlanYearStatisticsOverviewList")
    public BaseResponse getInvPlanYearStatisticsOverviewList(@RequestBody InvPlanYearRequest request, @RequestHeader String token) {
        log.info("getInvPlanYearStatisticsOverviewList");
        return workbenchService.getInvPlanYearStatisticsOverviewList(request, redisUtils.getTeId(token));
    }

    /**
     * 获取月度计划总览列表
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/getInvMonthlyScheduleStatisticsOverviewList")
    public BaseResponse getInvMonthlyScheduleStatisticsOverviewList(@RequestBody InvPlanYearRequest request, @RequestHeader String token) {
        log.info("getInvMonthlyScheduleStatisticsOverviewList");
        return workbenchService.getInvMonthlyScheduleStatisticsOverviewList(request, redisUtils.getTeId(token));
    }

    /**
     * 获取完成月度计划总览列表
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/getInvCompleteMonthStatisticsOverviewList")
    public BaseResponse getInvCompleteMonthStatisticsOverviewList(@RequestBody InvCompleteMonthRequest request, @RequestHeader String token) {
        log.info("getInvCompleteMonthStatisticsOverviewList");
        return workbenchService.getInvCompleteMonthStatisticsOverviewList(request, redisUtils.getTeId(token));
    }

    /**
     * 统计当年各组织机构每月计划完成投资
     *
     * @return
     */
    @GetMapping("/getInvStatisticsGroupDep")
    public BaseResponse getInvStatisticsGroupDep(@RequestHeader String token) {
        log.info("getInvStatisticsGroupDep");
        return workbenchService.getInvStatisticsGroupDep(redisUtils.getTeId(token));
    }

    /**
     * 月度投资计划完成情况
     *
     * @return
     */
    @GetMapping("/getInvStatisticsByComplete")
    public BaseResponse getInvStatisticsByComplete(@RequestHeader String token) {
        log.info("getInvStatisticsByComplete");
        return workbenchService.getInvStatisticsByComplete(redisUtils.getTeId(token));
    }

    /**
     * 年度投资完成率排行榜
     *
     * @param size
     * @return
     */
    @GetMapping("/getInvStatisticsByRanking")
    public BaseResponse getInvStatisticsByRanking(@RequestParam Integer size,@RequestHeader String token) {
        log.info("getInvStatisticsByRanking");
        return workbenchService.getInvStatisticsByRanking(size,redisUtils.getTeId(token));
    }

    /**
     * 变更总览
     *
     * @return
     */
    @GetMapping("/getConEcStatisticsOverview")
    public BaseResponse getConEcStatisticsOverview(@RequestHeader String token) {
        log.info("getConEcStatisticsOverview");
        return workbenchService.getConEcStatisticsOverview(redisUtils.getTeId(token));
    }

    /**
     * 获取变更总览列表
     *
     * @param request
     * @param token
     * @return
     */
    @PostMapping("/getConEcStatisticsOverviewList")
    public BaseResponse getConEcStatisticsOverviewList(@RequestBody ConEcRequest request, @RequestHeader String token) {
        log.info("getConEcStatisticsOverviewList");
        return workbenchService.getConEcStatisticsOverviewList(request, redisUtils.getTeId(token));
    }

    /**
     * 变更等级分布
     *
     * @return
     */
    @GetMapping("/getConEcStatisticsByGrade")
    public BaseResponse getConEcStatisticsByGrade(@RequestHeader String token) {
        log.info("getConEcStatisticsByGrade");
        return workbenchService.getConEcStatisticsByGrade(redisUtils.getTeId(token));
    }

    /**
     * 合同变更情况
     *
     * @return
     */
    @GetMapping("/getConEcStatisticsChangeSituation")
    public BaseResponse getConEcStatisticsChangeSituation(@RequestHeader String token) {
        log.info("getConEcStatisticsChangeSituation");
        return workbenchService.getConEcStatisticsChangeSituation(redisUtils.getTeId(token));
    }

    /**
     * 变更详情列表
     *
     * @param size
     * @return
     */
    @GetMapping("/getConEcStatisticsByRanking")
    public BaseResponse getConEcStatisticsByRanking(@RequestParam Integer size,@RequestHeader String token) {
        log.info("getConEcStatisticsByRanking");
        return workbenchService.getConEcStatisticsByRanking(size,redisUtils.getTeId(token));
    }
}
