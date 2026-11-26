package com.jpmsoft.projectConsumer.controller.v1.investment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.model.request.InvestmentControl.InvCompleteMonthRequest;
import com.jpmsoft.common.model.request.InvestmentControl.InvCompleteMonthSearchRequest;
import com.jpmsoft.common.model.request.InvestmentControl.InvMonthVersionRequest;
import com.jpmsoft.common.model.response.InvestmentControl.GetCreateFieldDataRequest;
import com.jpmsoft.common.model.response.InvestmentControl.InvCompleteMonthResponse;
import com.jpmsoft.common.model.response.InvestmentControl.TransferStatusUpdateRequest;
import com.jpmsoft.projectConsumer.config.CommonResponse;
import com.jpmsoft.projectConsumer.config.PageResult;
import com.jpmsoft.projectConsumer.feign.InvFeign;
import feign.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;


@Slf4j
@RestController
@Api(tags = "月完成情况", value = "月完成情况")
@RequestMapping("/inv/v1/invCompleteMonth")
public class InvCompleteMonthController {
    @Autowired
    private InvFeign invFeign;

    @ApiOperation(value = "新增或修改月完成情况")
    @PostMapping("/createOrUpdateInvCompleteMonth")
    public BaseResponse createOrUpdateInvCompleteMonth(@RequestBody InvCompleteMonthRequest request) {
        log.info("createOrUpdateInvCompleteMonth");
        return invFeign.createOrUpdateInvCompleteMonth(request);
    }

    @ApiOperation(value = "新增或修改月完成情况-西藏")
    @PostMapping("/createOrUpdateInvCompleteMonthXz")
    public BaseResponse createOrUpdateInvCompleteMonthXz(@RequestBody InvCompleteMonthRequest request) {
        log.info("createOrUpdateInvCompleteMonth");
        return invFeign.createOrUpdateInvCompleteMonthXz(request);
    }


    @ApiOperation(value = "分页月完成情况")
    @PostMapping("/getInvCompleteMonthList")
    public BaseResponse getInvCompleteMonthList(@RequestBody InvCompleteMonthRequest request, @RequestHeader String token) {
        return invFeign.getInvCompleteMonthList(request, token);
    }

    @PostMapping("/getInvCompleteMonthSearchList")
    public CommonResponse<PageResult<InvCompleteMonthResponse>> getInvCompleteMonthSearchList(@RequestBody InvCompleteMonthSearchRequest request, @RequestHeader String token) {
        return invFeign.getInvCompleteMonthSearchList(request, token);
    }

    @PostMapping("/getInvSearchTableList")
    public CommonResponse<List<ClassInfoAnnotation>> getInvSearchTableList() {
        return invFeign.getInvSearchTableList();
    }

    @ApiOperation(value = "ID查询月完成情况")
    @GetMapping("/getInvCompleteMonthByIcmId")
    public BaseResponse getInvCompleteMonthByIcmId(@RequestParam String icmId, @RequestHeader String token) {
        log.info("getInvCompleteMonthByIcmId");
        return invFeign.getInvCompleteMonthByIcmId(icmId, token);
    }

    @ApiOperation(value = "是否可以创建月完成情况 true可以")
    @PostMapping("/canToCreateInvCompleteMonth")
    public BaseResponse canToCreateInvCompleteMonth(@RequestBody InvCompleteMonthRequest request) {
        log.info("canToCreateInvCompleteMonth");
        return invFeign.canToCreateInvCompleteMonth(request);
    }

    @ApiOperation(value = "统计导出完成情况")
    @PostMapping("/exportInvCompleteMonthStatistics")
    public BaseResponse exportInvCompleteMonthStatistics(@RequestBody InvCompleteMonthRequest request) {
        log.info("exportInvCompleteMonthStatistics");
        return invFeign.exportInvCompleteMonthStatistics(request);
    }

    @ApiOperation(value = "修改单据流转状态")
    @PostMapping("/updateTransferStatus")
    public BaseResponse updateTransferStatus(@RequestBody TransferStatusUpdateRequest request, @RequestHeader String token) {
        return invFeign.updateTransferStatus(request, token);
    }

    @ApiOperation(value = "获取资金计划创建初始值")
    @PostMapping("/getCreateFieldData")
    public BaseResponse getCreateFieldData(@RequestBody GetCreateFieldDataRequest request) {
        return invFeign.getCreateFieldData(request);
    }

    @ApiOperation(value = "获取上一个月的数据")
    @PostMapping("/getNearCompleteMonthData")
    public BaseResponse getNearCompleteMonthData(@RequestBody GetCreateFieldDataRequest request) {
        return invFeign.getNearCompleteMonthData(request);
    }

    @ApiOperation(value = "查询项目指定年份从其实月份到指定月份IcmMonthlyInvestment汇总值")
    @GetMapping("/getMonthlyInvestmentSum")
    public BaseResponse getMonthlyInvestmentSum(@RequestParam String pid, @RequestParam String year, @RequestParam Integer month) {
        return invFeign.getMonthlyInvestmentSum(pid, year, month);
    }

    @ApiOperation(value = "项目主管单位经办人资金计划统计分析报表")
    @PostMapping("/countInvCompleteMonth")
    public BaseResponse countInvCompleteMonth(@RequestBody InvCompleteMonthRequest request, @RequestHeader String token) {
        log.info("countInvCompleteMonth");
        return invFeign.countInvCompleteMonth(request, token);
    }

    @ApiOperation(value = "集团经办人资金计划统计分析报表")
    @PostMapping("/countInvCompleteMonthForAll")
    public BaseResponse countInvCompleteMonthForAll(@RequestBody InvCompleteMonthRequest request, @RequestHeader String token) {
        log.info("countInvCompleteMonthForAll");
        return invFeign.countInvCompleteMonthForAll(request, token);
    }

    @ApiOperation(value = "项目主管单位经办人资金计划统计分析报表导出EXCEL")
    @PostMapping("/countInvCompleteMonthExportExcel")
    public void countInvCompleteMonthExportExcel(HttpServletResponse response, @RequestBody InvCompleteMonthRequest request, @RequestHeader String token) {
        log.info("countInvCompleteMonthExportExcel");
        Response feignResponse = invFeign.countInvCompleteMonthExportExcel(request, token);
        Response.Body body = feignResponse.body();
        try (InputStream inputStream = body.asInputStream();
             OutputStream outputStream = response.getOutputStream()) {
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, feignResponse.headers().get(HttpHeaders.CONTENT_DISPOSITION).stream().findFirst().get());
            response.setContentType(feignResponse.headers().get(HttpHeaders.CONTENT_TYPE).stream().findFirst().get());
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            log.error("countInvCompleteMonthExportExcel  error");
        }
    }

    @ApiOperation(value = "集团工程部经办人资金计划统计分析报表导出EXCEL")
    @PostMapping("/countInvCompleteMonthForAllExportExcel")
    public void countInvCompleteMonthForAllExportExcel(HttpServletResponse response, @RequestBody InvCompleteMonthRequest request, @RequestHeader String token) {
        log.info("countInvCompleteMonthForAllExportExcel");
        Response feignResponse = invFeign.countInvCompleteMonthForAllExportExcel(request, token);
        Response.Body body = feignResponse.body();
        try (InputStream inputStream = body.asInputStream();
             OutputStream outputStream = response.getOutputStream()) {
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, feignResponse.headers().get(HttpHeaders.CONTENT_DISPOSITION).stream().findFirst().get());
            response.setContentType(feignResponse.headers().get(HttpHeaders.CONTENT_TYPE).stream().findFirst().get());
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            log.error("countInvCompleteMonthForAllExportExcel  error");
        }
    }

    @ApiOperation(value = "集团工程部经办人资金计划统计分析报表导出EXCEL")
    @PostMapping("/invCompleteMonthListExportExcel")
    public void invCompleteMonthListExportExcel(HttpServletResponse response, @RequestBody InvCompleteMonthRequest request, @RequestHeader String token) {
        log.info("invCompleteMonthListExportExcel");
        Response feignResponse = invFeign.invCompleteMonthListExportExcel(request, token);
        Response.Body body = feignResponse.body();
        try (InputStream inputStream = body.asInputStream();
             OutputStream outputStream = response.getOutputStream()) {
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, feignResponse.headers().get(HttpHeaders.CONTENT_DISPOSITION).stream().findFirst().get());
            response.setContentType(feignResponse.headers().get(HttpHeaders.CONTENT_TYPE).stream().findFirst().get());
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            log.error("invCompleteMonthListExportExcel  error");
        }
    }

    /**
     * 批量完成后续的月完成情况
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "批量完成后续的月完成情况")
    @PostMapping("/completeSubsequentInvCompleteMonth")
    public BaseResponse completeSubsequentInvCompleteMonth(@RequestBody TransferStatusUpdateRequest request) {
        log.info("completeSubsequentInvCompleteMonth");
        return invFeign.completeSubsequentInvCompleteMonth(request);
    }

    /**
     * 根据id获取版本信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id获取版本信息")
    @GetMapping("/getInvMonthVersionById")
    public BaseResponse getInvMonthVersionById(@RequestParam String id) {
        log.info("getInvMonthVersionById");
        return invFeign.getInvMonthVersionById(id);
    }

    /**
     * 处理版本
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "处理版本")
    @PostMapping("/createInvMonthVersion")
    public BaseResponse createInvMonthVersion(@RequestBody InvMonthVersionRequest request) {
        log.info("createInvMonthVersion");
        return invFeign.createInvMonthVersion(request);
    }

    /**
     * 初始化版本数据
     *
     * @return
     */
    @ApiOperation(value = "初始化版本数据")
    @GetMapping("/initInvMonthVersion")
    public BaseResponse initInvMonthVersion() {
        log.info("initInvMonthVersion");
        return invFeign.initInvMonthVersion();
    }
}
