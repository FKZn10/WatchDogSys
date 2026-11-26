package com.jpmsoft.fileUpload.controller;


import com.jpmsoft.common.model.request.Contract.ConContractRequest;
import com.jpmsoft.common.model.request.Qs.QualitySafetyStatisticFowYWRequest;
import com.jpmsoft.common.model.request.project.PreProjectRequest;
import com.jpmsoft.common.model.requestAndResponse.Project.ProvPilotEnterpriseModel;
import com.jpmsoft.common.model.requestAndResponse.Project.ProvPilotProjectModel;
import com.jpmsoft.fileUpload.feign.PmFeign;
import com.jpmsoft.fileUpload.pojo.dto.ProSafetyLedgerDtlDTO;
import com.jpmsoft.fileUpload.pojo.dto.QsPlanTaskDto;
import com.jpmsoft.fileUpload.pojo.vo.QsRecordVo;
import com.jpmsoft.fileUpload.service.IPreProjectService;
import com.jpmsoft.fileUpload.service.IQsRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@Api(tags = "Excel导出", value = "Excel导出")
@RequestMapping("/preExcel")
public class PreAndConExcelController {


    @Autowired
    private IPreProjectService iPreProjectService;

    @Resource
    private IQsRecordService qsRecordService;
    @Resource
    private PmFeign pmFeign;


    @PostMapping("/getProjectListExcel")
    public ResponseEntity<byte[]> getProjectListExcel(@RequestBody PreProjectRequest request, @RequestHeader String token, HttpServletResponse response){
        return    iPreProjectService.getProjectListExcel(request,token,response);
    }

    @PostMapping("/getContractListExcel")
    public ResponseEntity<byte[]> getContractListExcel(@RequestBody ConContractRequest request, @RequestHeader String token, HttpServletResponse response){
        return    iPreProjectService.getContractListExcel(request,token,response);
    }

    @ApiOperation(value = "省试点项目导出Excel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "令牌", required = true, paramType = "header", dataType = "string")
    })
    @PostMapping("/getProvPilotProjectExcel")
    public ResponseEntity<byte[]> getProvPilotProjectExcel(@RequestBody ProvPilotProjectModel request, @RequestHeader String token, HttpServletResponse response){
        return  iPreProjectService.getProvPilotProjectExcel(request,token,response);
    }

    @ApiOperation(value = "省试点企业导出Excel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "令牌", required = true, paramType = "header", dataType = "string")
    })
    @PostMapping("/getProvPilotEnterpriseExcel")
    public ResponseEntity<byte[]> getProvPilotEnterpriseExcel(@RequestBody ProvPilotEnterpriseModel request, @RequestHeader String token, HttpServletResponse response){
        return  iPreProjectService.getProvPilotEnterpriseExcel(request,token,response);
    }

    /**
     * 质量安全检查导出
     * @param request
     * @param token
     * @param response
     * @return
     */
    @PostMapping("/getQsPlanTaskListExport")
    public ResponseEntity<byte[]> getQsPlanTaskListExport(@RequestBody QsPlanTaskDto request, @RequestHeader String token, HttpServletResponse response){
        return    qsRecordService.getQsPlanTaskListExport(request,token,response);
    }


    /**
     * 质量安全问题描述统计导出
     */
    @PostMapping("/getQualitySafetyListExport")
    public ResponseEntity<byte[]> getQualitySafetyListExport(@RequestBody QualitySafetyStatisticFowYWRequest request, HttpServletResponse response){
        return    qsRecordService.getQualitySafetyListExport(request,response);
    }


    @PostMapping("/getProSafetyLedgerPageYWExport")
    public ResponseEntity<byte[]> getProSafetyLedgerPageYWExport(@RequestBody ProSafetyLedgerDtlDTO request, @RequestHeader String token,HttpServletResponse response) {
        return qsRecordService.getProSafetyLedgerPageYWExport(request, token,response);
    }


    @PostMapping("/getQsRecordYwExport")
    public ResponseEntity<byte[]> getQsRecordYwExport(@RequestBody QsRecordVo request, HttpServletResponse response) {
        return qsRecordService.getQsRecordYwExport(request,response);
    }

    /**
     * 导出本周未填报的在建项目周进度清单。
     *
     * @return 项目名称、合同名称和填报人组成的Excel文件
     */
    @GetMapping("/exportMissingWeeklyProgressReport")
    public ResponseEntity<byte[]> exportMissingWeeklyProgressReport() {
        List<LinkedHashMap<String, Object>> missingReports = pmFeign.getMissingWeeklyProgressReportList();
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("本周未填报项目");
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);

            Row header = sheet.createRow(0);
            String[] headers = {"项目名称", "合同名称", "填报人"};
            for (int columnIndex = 0; columnIndex < headers.length; columnIndex++) {
                header.createCell(columnIndex).setCellValue(headers[columnIndex]);
                header.getCell(columnIndex).setCellStyle(headerStyle);
            }
            if (missingReports != null) {
                for (int rowIndex = 0; rowIndex < missingReports.size(); rowIndex++) {
                    LinkedHashMap<String, Object> report = missingReports.get(rowIndex);
                    Row row = sheet.createRow(rowIndex + 1);
                    row.createCell(0).setCellValue(Objects.toString(report.get("pname"), ""));
                    row.createCell(1).setCellValue(Objects.toString(report.get("conName"), ""));
                    row.createCell(2).setCellValue(Objects.toString(report.get("fillUname"), ""));
                }
            }
            sheet.setColumnWidth(0, 35 * 256);
            sheet.setColumnWidth(1, 40 * 256);
            sheet.setColumnWidth(2, 18 * 256);
            workbook.write(outputStream);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setContentType(MediaType.parseMediaType(
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            responseHeaders.set(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment;filename=" + URLEncoder.encode("本周未填报项目.xlsx", "UTF-8"));
            return new ResponseEntity<>(outputStream.toByteArray(), responseHeaders, HttpStatus.OK);
        } catch (IOException exception) {
            log.error("导出本周未填报项目失败", exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
