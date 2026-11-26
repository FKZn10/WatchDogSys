package com.jpmsoft.projectProducer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.model.request.InvestmentControl.InvCompleteMonthRequest;
import com.jpmsoft.common.model.request.InvestmentControl.InvCompleteMonthSearchRequest;
import com.jpmsoft.common.model.request.InvestmentControl.InvMonthVersionRequest;
import com.jpmsoft.common.model.response.InvestmentControl.GetCreateFieldDataRequest;
import com.jpmsoft.common.model.response.InvestmentControl.InvCompleteMonthResponse;
import com.jpmsoft.common.model.response.InvestmentControl.TransferStatusUpdateRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.config.CommonResponse;
import com.jpmsoft.projectProducer.config.PageResult;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvCompleteMonth;
import com.jpmsoft.projectProducer.service.investment.IInvCompleteMonthService;
import com.jpmsoft.projectProducer.service.investment.IInvMonthVersionService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("/v1/invCompleteMonth")
public class InvCompleteMonthController {
    @Resource
    private IInvCompleteMonthService invCompleteMonthService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private IInvMonthVersionService invMonthVersionService;


    @TenantId
    @PostMapping("/createOrUpdateInvCompleteMonth")
    public BaseResponse createOrUpdateInvCompleteMonth(@RequestBody InvCompleteMonthRequest request) {
        log.info("createOrUpdateInvCompleteMonth");
        return invCompleteMonthService.createOrUpdateInvCompleteMonth(request);
    }

    @TenantId
    @PostMapping("/createOrUpdateInvCompleteMonthXz")
    public BaseResponse createOrUpdateInvCompleteMonthXz(@RequestBody InvCompleteMonthRequest request) {
        log.info("createOrUpdateInvCompleteMonthXz");
        return invCompleteMonthService.createOrUpdateInvCompleteMonthXz(request);
    }

    @TenantId
    @PostMapping("/getInvCompleteMonthList")
    public BaseResponse getInvCompleteMonthList(@RequestBody InvCompleteMonthRequest request, @RequestHeader String token) {
        log.info("getInvCompleteMonthList");
        return invCompleteMonthService.getInvCompleteMonthList(request, redisUtils.getUid(token));
    }

    @PostMapping("/getInvCompleteMonthSearchList")
    public CommonResponse<PageResult<InvCompleteMonthResponse>> getInvCompleteMonthSearchList(@RequestBody InvCompleteMonthSearchRequest request, @RequestHeader String token) {
        return CommonResponse.ok(invCompleteMonthService.getInvCompleteMonthSearchList(request, redisUtils.getUid(token)));
    }

    @PostMapping("/getInvSearchTableList")
    public CommonResponse<List<ClassInfoAnnotation>> getInvSearchTableList() {
        return CommonResponse.ok(invCompleteMonthService.getInvSearchTableList());
    }

    @PostMapping("/invCompleteMonthListExportExcel")
    public void invCompleteMonthListExportExcel(HttpServletResponse response,
                                                @RequestBody InvCompleteMonthRequest request, @RequestHeader String token) {
        try {
            Workbook workbook = invCompleteMonthService.invCompleteMonthListExportExcel(request, redisUtils.getUid(token));
            String fileName = URLEncoder.encode("项目主管单位经办人资金计划统计分析报表.xls", "UTF-8");
            // 告诉浏览器用什么软件可以打开此文件
            response.setHeader("content-Type", "application/vnd.ms-excel");
            // 下载文件的默认名称
            response.setHeader("Content-Disposition", fileName);
            // 编码
            response.setCharacterEncoding("UTF-8");
            // 多sheet
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (IOException e) {
            log.error("项目主管单位经办人资金计划统计分析报表导出EXCEL导出出错{}", e.getMessage(), e);
        }
    }

    @GetMapping("/getInvCompleteMonthByIcmId")
    public BaseResponse getInvCompleteMonthByIcmId(@RequestParam String icmId) {
        log.info("getInvCompleteMonthByIcmId");
        return invCompleteMonthService.getInvCompleteMonthByIcmId(icmId);
    }

    @PostMapping("/canToCreateInvCompleteMonth")
    public BaseResponse canToCreateInvCompleteMonth(@RequestBody InvCompleteMonthRequest request) {
        log.info("canToCreateInvCompleteMonth");
        return invCompleteMonthService.canToCreateInvCompleteMonth(request);
    }

    @TenantId
    @PostMapping("/exportInvCompleteMonthStatistics")
    public BaseResponse exportInvCompleteMonthStatistics(@RequestBody InvCompleteMonthRequest request) {
        log.info("exportInvCompleteMonthStatistics");
        return invCompleteMonthService.exportInvCompleteMonthStatistics(request);
    }

    @PostMapping("/updateTransferStatus")
    public BaseResponse updateTransferStatus(@RequestBody TransferStatusUpdateRequest request, @RequestHeader String token) {
        return invCompleteMonthService.updateTransferStatus(request, token);
    }

    @PostMapping("/getCreateFieldData")
    public BaseResponse getCreateFieldData(@RequestBody GetCreateFieldDataRequest request) {
        return invCompleteMonthService.getCreateFieldData(request);
    }

    @PostMapping("/getNearCompleteMonthData")
    public BaseResponse getNearCompleteMonthData(@RequestBody GetCreateFieldDataRequest request) {
        return invCompleteMonthService.getNearCompleteMonthData(request);
    }

    @GetMapping("/getMonthlyInvestmentSum")
    public BaseResponse getMonthlyInvestmentSum(@RequestParam String pid, @RequestParam String year, @RequestParam Integer month) {
        return invCompleteMonthService.getMonthlyInvestmentSum(pid, year, month);
    }

    @PostMapping("/countInvCompleteMonth")
    public BaseResponse countInvCompleteMonth(@RequestBody InvCompleteMonthRequest request, @RequestHeader String token) {
        log.info("countInvCompleteMonth");
        return invCompleteMonthService.countInvCompleteMonth(request, redisUtils.getUid(token));
    }

    @PostMapping("/countInvCompleteMonthForAll")
    public BaseResponse countInvCompleteMonthForAll(@RequestBody InvCompleteMonthRequest request, @RequestHeader String token) {
        log.info("countInvCompleteMonthForAll");
        return invCompleteMonthService.countInvCompleteMonthForAll(request, redisUtils.getUid(token));
    }

    @PostMapping("/countInvCompleteMonthExportExcel")
    public void countInvCompleteMonthExportExcel(HttpServletResponse response, @RequestBody InvCompleteMonthRequest request,
                                                 @RequestHeader String token) {

        try {
            Workbook workbook = invCompleteMonthService.countInvCompleteMonthExportExcel(request, redisUtils.getUid(token));
            ;
            String fileName = URLEncoder.encode("项目主管单位经办人资金计划统计分析报表.xls", "UTF-8");
            // 告诉浏览器用什么软件可以打开此文件
            response.setHeader("content-Type", "application/vnd.ms-excel");
            // 下载文件的默认名称
            response.setHeader("Content-Disposition", fileName);
            // 编码
            response.setCharacterEncoding("UTF-8");
            // 多sheet
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (IOException e) {
            log.error("项目主管单位经办人资金计划统计分析报表导出EXCEL导出出错{}", e.getMessage(), e);
        }
    }

    @PostMapping("/countInvCompleteMonthForAllExportExcel")
    public void countInvCompleteMonthForAllExportExcel(HttpServletResponse response, @RequestBody InvCompleteMonthRequest request,
                                                       @RequestHeader String token) {

        try {
            Workbook workbook = invCompleteMonthService.countInvCompleteMonthForAllExportExcel(request, redisUtils.getUid(token));
            ;
            String fileName = URLEncoder.encode("集团工程部经办人资金计划统计分析报表.xls", "UTF-8");
            // 告诉浏览器用什么软件可以打开此文件
            response.setHeader("content-Type", "application/vnd.ms-excel");
            // 下载文件的默认名称
            response.setHeader("Content-Disposition", fileName);
            // 编码
            response.setCharacterEncoding("UTF-8");
            // 多sheet
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (IOException e) {
            log.error("集团工程部经办人资金计划统计分析报表导出EXCEL导出出错{}", e.getMessage(), e);
        }
    }

    /**
     * 批量完成后续的月完成情况
     *
     * @param request
     * @return
     */
    @PostMapping("/completeSubsequentInvCompleteMonth")
    public BaseResponse completeSubsequentInvCompleteMonth(@RequestBody TransferStatusUpdateRequest request) {
        log.info("completeSubsequentInvCompleteMonth");
        return invCompleteMonthService.completeSubsequentInvCompleteMonth(request);
    }

    /**
     * 刷新所有的年度投资计划数据和完成百分比
     *
     * @return
     */
    @GetMapping("/refreshAllIcmAnnualInvestment")
    public BaseResponse refreshAllIcmAnnualInvestment() {
        log.info("refreshAllIcmAnnualInvestment");
        return invCompleteMonthService.refreshAllIcmAnnualInvestment();
    }

    /**
     * 根据id获取版本信息
     *
     * @param id
     * @return
     */
    @GetMapping("/getInvMonthVersionById")
    public BaseResponse getInvMonthVersionById(@RequestParam String id) {
        log.info("getInvMonthVersionById");
        return invMonthVersionService.getInvMonthVersionById(id);
    }

    /**
     * 处理版本
     *
     * @param request
     * @return
     */
    @PostMapping("/createInvMonthVersion")
    public BaseResponse createInvMonthVersion(@RequestBody InvMonthVersionRequest request) {
        log.info("createInvMonthVersion");
        return invMonthVersionService.createInvMonthVersion(request);
    }

    /**
     * 初始化所有的版本数据
     *
     * @return
     */
    @GetMapping("/initInvMonthVersion")
    public BaseResponse initInvMonthVersion() {
        log.info("initInvMonthVersion");
        List<String> ids = invCompleteMonthService.list().stream().map(InvCompleteMonth::getIcmId).collect(Collectors.toList());
        invMonthVersionService.saveInvMonthVersionByIcmIds(ids);
        return BaseResponse.ok();
    }
}
