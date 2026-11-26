package com.jpmsoft.projectConsumer.controller.v1.other;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConContractRequest;
import com.jpmsoft.common.model.request.Contract.ConEcRequest;
import com.jpmsoft.common.model.request.InvestmentControl.InvCompleteMonthRequest;
import com.jpmsoft.common.model.request.InvestmentControl.InvPlanYearRequest;
import com.jpmsoft.common.model.request.Qs.QsRecordRequest;
import com.jpmsoft.common.model.request.pay.ConPayRequest;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "看板", value = "看板")
@RequestMapping("/v1/kb")
public class WorkbenchController {
    @Autowired
    private BaseFeign baseFeign;

    /**
     * 获取领导看板总览
     *
     * @return
     */
    @ApiOperation(value = "获取领导看板总览")
    @GetMapping("/getWorkbenchOverviewModel")
    public BaseResponse getWorkbenchOverviewModel() {
        log.info("getWorkbenchOverviewModel");
        return baseFeign.getWorkbenchOverviewModel();
    }

    /**
     * 质量安全二级页面 顶部块状数值统计
     *
     * @param qsrCategory
     * @return
     */
    @ApiOperation(value = "质量安全二级页面 顶部块状数值统计")
    @GetMapping("/getWorkbenchDetailStatistics")
    public BaseResponse getWorkbenchDetailStatistics(@RequestParam Integer qsrCategory) {
        log.info("getWorkbenchDetailStatistics");
        return baseFeign.getWorkbenchDetailStatistics(qsrCategory);
    }

    /**
     * 质量安全二级页面 统计点开后的详情
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation("质量安全二级页面 统计点开后的详情")
    @PostMapping("/getWorkbenchDetailStatisticsList")
    public BaseResponse getWorkbenchDetailStatisticsList(@RequestBody QsRecordRequest request, @RequestHeader String token) {
        log.info("getWorkbenchDetailStatisticsList");
        return baseFeign.getWorkbenchDetailStatisticsList(request, token);
    }

    /**
     * 获取质量安全最近七天数据
     *
     * @param qsrCategory
     * @return
     */
    @ApiOperation(value = "获取质量安全最近七天新增关闭数据")
    @GetMapping("/getQsrLastSevenDaysStatistics")
    public BaseResponse getQsrLastSevenDaysStatistics(@RequestParam Integer qsrCategory) {
        log.info("getQsrLastSevenDaysStatistics");
        return baseFeign.getQsrLastSevenDaysStatistics(qsrCategory);
    }

    @GetMapping("/getQsMonthlyStatisticsByYear")
    public BaseResponse getQsMonthlyStatisticsByYear(@RequestParam Integer qsrCategory, @RequestHeader String token) {
        log.info("getQsMonthlyStatisticsByYear");
        return baseFeign.getQsMonthlyStatisticsByYear(qsrCategory,token);
    }

    /**
     * 统计年度季度月度质量安全
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "统计年度季度月度质量安全")
    @PostMapping("/getQsrYearQuarterMonthStatistics")
    public BaseResponse getQsrYearQuarterMonthStatistics(@RequestBody QsRecordRequest request) {
        log.info("getQsrYearQuarterMonthStatistics");
        return baseFeign.getQsrYearQuarterMonthStatistics(request);
    }


    /**
     * 合同类型分组统计合同
     *
     * @return
     */
    @ApiOperation(value = "合同类型分组统计合同")
    @GetMapping("/getContractStatisticsGourpConType")
    public BaseResponse getContractStatisticsGourpConType() {
        log.info("getContractStatisticsGourpConType");
        return baseFeign.getContractStatisticsGourpConType();
    }

    /**
     * 获取合同总览
     *
     * @return
     */
    @ApiOperation(value = "获取合同总览")
    @GetMapping("/getContractStatisticsOverview")
    public BaseResponse getContractStatisticsOverview() {
        log.info("getContractStatisticsOverview");
        return baseFeign.getContractStatisticsOverview();
    }

    /**
     * 获取合同总览列表
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation(value = "获取合同总览列表")
    @PostMapping("/getContractStatisticsOverviewList")
    public BaseResponse getContractStatisticsOverviewList(@RequestBody ConContractRequest request, @RequestHeader String token) {
        log.info("getContractStatisticsOverviewList");
        return baseFeign.getContractStatisticsOverviewList(request, token);
    }

    @ApiOperation(value = "获取合同签订情况")
    @GetMapping("/getContractStatisticsSigning")
    public BaseResponse getContractStatisticsSigning() {
        log.info("getContractStatisticsSigning");
        return baseFeign.getContractStatisticsSigning();
    }

    @ApiOperation(value = "招标方式统计合同")
    @GetMapping("/getContractStatisticsBiddingType")
    public BaseResponse getContractStatisticsBiddingType() {
        log.info("getContractStatisticsBiddingType");
        return baseFeign.getContractStatisticsBiddingType();
    }


    @ApiOperation(value = "获取支付总览")
    @GetMapping("/getConPayStatisticsOverview")
    public BaseResponse getConPayStatisticsOverview() {
        log.info("getConPayStatisticsOverview");
        return baseFeign.getConPayStatisticsOverview();
    }

    /**
     * 获取支付总览列表
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation("获取支付总览列表")
    @PostMapping("/getConPayStatisticsOverviewList")
    public BaseResponse getConPayStatisticsOverviewList(@RequestBody ConPayRequest request, @RequestHeader String token) {
        log.info("getConPayStatisticsOverviewList");
        return baseFeign.getConPayStatisticsOverviewList(request, token);
    }

    @ApiOperation(value = "分组查询各部门当年完成支付情况")
    @GetMapping("/getConPayStatisticsGroupByDep")
    public BaseResponse getConPayStatisticsGroupByDep() {
        log.info("getConPayStatisticsGroupByDep");
        return baseFeign.getConPayStatisticsGroupByDep();
    }

    @ApiOperation(value = "统计当年每月支付情况")
    @GetMapping("/getConPayStatisticsByMonth")
    public BaseResponse getConPayStatisticsByMonth() {
        log.info("getConPayStatisticsByMonth");
        return baseFeign.getConPayStatisticsByMonth();
    }

    @ApiOperation(value = "项目支付年度排行榜")
    @GetMapping("/getConPayStatisticsRanking")
    public BaseResponse getConPayStatisticsRanking(@RequestParam Integer size) {
        log.info("getConPayStatisticsRanking");
        return baseFeign.getConPayStatisticsRanking(size);
    }

    /**
     * 投资计划总览
     *
     * @return
     */
    @ApiOperation(value = "统计当年每月支付情况")
    @GetMapping("/getInvStatisticsOverview")
    public BaseResponse getInvStatisticsOverview() {
        log.info("getInvStatisticsOverview");
        return baseFeign.getInvStatisticsOverview();
    }

    /**
     * 获取支付总览列表
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation("获取年度投资计划统计总览列表")
    @PostMapping("/getInvPlanYearStatisticsOverviewList")
    public BaseResponse getInvPlanYearStatisticsOverviewList(@RequestBody InvPlanYearRequest request, @RequestHeader String token) {
        log.info("getInvPlanYearStatisticsOverviewList");
        return baseFeign.getInvPlanYearStatisticsOverviewList(request, token);
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
        return baseFeign.getInvMonthlyScheduleStatisticsOverviewList(request, token);
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
        return baseFeign.getInvCompleteMonthStatisticsOverviewList(request, token);
    }

    @ApiOperation(value = "统计当年各组织机构每月计划完成投资")
    @GetMapping("/getInvStatisticsGroupDep")
    public BaseResponse getInvStatisticsGroupDep() {
        log.info("getInvStatisticsGroupDep");
        return baseFeign.getInvStatisticsGroupDep();
    }

    @ApiOperation(value = "月度投资计划完成情况")
    @GetMapping("/getInvStatisticsByComplete")
    public BaseResponse getInvStatisticsByComplete() {
        log.info("getInvStatisticsByComplete");
        return baseFeign.getInvStatisticsByComplete();
    }

    @ApiOperation(value = "年度投资完成率排行榜")
    @GetMapping("/getInvStatisticsByRanking")
    public BaseResponse getInvStatisticsByRanking(@RequestParam Integer size) {
        log.info("getInvStatisticsByRanking");
        return baseFeign.getInvStatisticsByRanking(size);
    }


    /**
     * 变更总览
     *
     * @return
     */
    @ApiOperation(value = "变更总览")
    @GetMapping("/getConEcStatisticsOverview")
    public BaseResponse getConEcStatisticsOverview() {
        log.info("getConEcStatisticsOverview");
        return baseFeign.getConEcStatisticsOverview();
    }

    /**
     * 获取变更总览列表
     *
     * @param request
     * @param token
     * @return
     */
    @ApiOperation("获取变更总览列表")
    @PostMapping("/getConEcStatisticsOverviewList")
    public BaseResponse getConEcStatisticsOverviewList(@RequestBody ConEcRequest request, @RequestHeader String token) {
        log.info("getConEcStatisticsOverviewList");
        return baseFeign.getConEcStatisticsOverviewList(request, token);
    }

    /**
     * 变更等级分布
     *
     * @return
     */
    @ApiOperation(value = "变更等级分布")
    @GetMapping("/getConEcStatisticsByGrade")
    public BaseResponse getConEcStatisticsByGrade() {
        log.info("getConEcStatisticsByGrade");
        return baseFeign.getConEcStatisticsByGrade();
    }

    /**
     * 合同变更情况
     *
     * @return
     */
    @ApiOperation(value = "合同变更情况")
    @GetMapping("/getConEcStatisticsChangeSituation")
    public BaseResponse getConEcStatisticsChangeSituation() {
        log.info("getConEcStatisticsChangeSituation");
        return baseFeign.getConEcStatisticsChangeSituation();
    }

    /**
     * 变更详情列表
     *
     * @param size
     * @return
     */
    @ApiOperation(value = "变更详情列表")
    @GetMapping("/getConEcStatisticsByRanking")
    public BaseResponse getConEcStatisticsByRanking(@RequestParam Integer size) {
        log.info("getConEcStatisticsByRanking");
        return baseFeign.getConEcStatisticsByRanking(size);
    }
}
