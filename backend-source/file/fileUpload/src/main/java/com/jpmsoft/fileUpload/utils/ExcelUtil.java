package com.jpmsoft.fileUpload.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.InvestmentControl.InvFinalAccountsApplyDtlDto;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvBudgetDeclareDtlModel;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvestmentEstimateBreakdownDetailsModel;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaProjectReportModel;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProApprovalDtlModel;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectDtlModel;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectRegisterDtlModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertModel;
import com.jpmsoft.common.model.requestAndResponse.designReview.DrBudgetDeclareDtlModel;
import com.jpmsoft.common.model.response.InvestmentControl.InvProjectPlanResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.constants.FileMsg;
import com.jpmsoft.fileUpload.easyexcel.EasyExcelData;
import com.jpmsoft.fileUpload.pojo.dto.*;
import com.jpmsoft.fileUpload.pojo.dto.easyexcel.GanttTaskFeedbackDTO;
import com.jpmsoft.fileUpload.pojo.dto.easyexcel.GeneralFormsUpDTO;
import com.jpmsoft.fileUpload.pojo.entity.InvProjectPlan;
import com.jpmsoft.fileUpload.pojo.entity.SysDictionary;
import com.jpmsoft.fileUpload.pojo.entity.SysLoggerInfo;
import com.jpmsoft.fileUpload.pojo.entity.SysTableColumn;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class ExcelUtil {

    /**
     * easyexcel导出数据到指定文件
     *
     * @param data
     * @param clazz
     * @param filePath
     * @param sheetName
     */
    public static void export2File(Collection<?> data, Class<?> clazz, String filePath, String sheetName) {
        Consumer<ExcelWriter> consumer = writer -> {
            writer.write(data, EasyExcel.writerSheet(sheetName).head(clazz).build());
        };
        EasyExcelData.export(filePath, consumer);
    }

    public static void export2OutputStream(Collection<?> data, Class<?> clazz, OutputStream outputStream, String sheetName) {
        Consumer<ExcelWriter> consumer = writer -> {
            writer.write(data, EasyExcel.writerSheet(sheetName).head(clazz).build());
        };
        EasyExcelData.exportOutputStream(outputStream, consumer);
    }

    public static void export2File(Collection<?> data, Class<?> clazz, String filePath, String sheetName, CellWriteHandler hanlder) {
        Consumer<ExcelWriter> consumer = writer -> {
            writer.write(data, EasyExcel.writerSheet(sheetName).registerWriteHandler(hanlder).head(clazz).build());
        };
        EasyExcelData.export(filePath, consumer);
    }


    /**
     * 政府投资项目计划excel
     *
     * @param filePath
     * @return
     */
    public static BaseResponse poiAnalysisExcel(String filePath) throws IOException {
        List<InvProjectPlanResponse> list = new ArrayList<>();
        InputStream is = new FileInputStream(new File(filePath));
        Workbook hssfWorkbook = null;
        if (filePath.endsWith("xlsx")) {
            hssfWorkbook = new XSSFWorkbook(is);//Excel 2007
        } else if (filePath.endsWith("xls")) {
            hssfWorkbook = new HSSFWorkbook(is);//Excel 2003
        }
        try {
            InvProjectPlanResponse model = null;
            // 循环工作表Sheet
            int quarter = DateUtil.getCurrentQuarter();
            for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
                Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
                if (hssfSheet == null) {
                    continue;
                }
                // 循环行Row
                for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                    Row hssfRow = hssfSheet.getRow(rowNum);
                    if (hssfRow != null) {
                        Integer index = 0;
                        try {
                            model = new InvProjectPlanResponse();
                            //预算提出单位
                            model.setPBuildModel(StringFormat(hssfRow.getCell(index += 1)));
                            if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            //项目名称
                            model.setPName(StringFormat(hssfRow.getCell(index += 1)));
                            if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            //项目编码
                            model.setPCode(StringFormat(hssfRow.getCell(index += 1)));
                            if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            //投资主体
                            model.setIppInvestor(StringFormat(hssfRow.getCell(index += 1)));
                            if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            //建设内容及规模
                            model.setIppBuildContent(StringFormat(hssfRow.getCell(index += 1)));
                            if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            //建设性质
                            model.setIppBuildNature(StringFormat(hssfRow.getCell(index += 1)));
                            if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            //建设年限
                            model.setIppBuildPeriod(ValidateUtil.isEmpty(StringFormat(hssfRow.getCell(index += 1))) ? null : Integer.parseInt(StringFormat(hssfRow.getCell(index))));
                            if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            //计划总投资
                            model.setIppPlanInv(new BigDecimal(cellIsEmpty(hssfRow.getCell(index += 1)) ? "0" : hssfRow.getCell(index).toString()));
                            if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            //上年底累计完成投资资金
                            model.setIppAccumulatedFunds(new BigDecimal(cellIsEmpty(hssfRow.getCell(index += 1)) ? "0" : hssfRow.getCell(index).toString()));
                            if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            //上年度建设进度或前期工作开展情况
                            model.setIppProgressLastYear(StringFormat(hssfRow.getCell(index += 1)));
                            if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            //当年计划投资
                            model.setIppAnnualReleaseFunds(new BigDecimal(cellIsEmpty(hssfRow.getCell(index += 1)) ? "0" : hssfRow.getCell(index).toString()));
                            if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            //当年形象进度要求
                            model.setIppNowYearProgress(StringFormat(hssfRow.getCell(index += 1)));
                            if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            //责任单位
                            model.setIppLiableCompany(StringFormat(hssfRow.getCell(index += 1)));
                            if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            //牵头责任人
                            model.setIppLiableMan(StringFormat(hssfRow.getCell(index += 1)));
                            if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            list.add(model);
                        } catch (Exception ex) {
                            is.close();
                            hssfWorkbook.close();
                            return new BaseResponse(MessageFormat.format(FileMsg.cellIsFail, rowNum, numCovertLetter(index + 1)));
                        }
                    }
                }
            }

            return new BaseResponse(true, "", list);
        } finally {
            is.close();
            hssfWorkbook.close();
        }
    }

    /**
     * 进度计划任务excel
     *
     * @param filePath
     * @return
     */
    public static List<GanttTasksDto> uploadGanttTasksExcel(String filePath) throws IOException {
        List<GanttTasksDto> list = new ArrayList<>();
        InputStream is = new FileInputStream(new File(filePath));
        Workbook hssfWorkbook = null;
        if (filePath.endsWith("xlsx")) {
            hssfWorkbook = new XSSFWorkbook(is);//Excel 2007
        } else if (filePath.endsWith("xls")) {
            hssfWorkbook = new HSSFWorkbook(is);//Excel 2003
        }
        try {
            GanttTasksDto task = null;
            List<String> againId = new ArrayList<>();
            List<GanttTasksDto> oneColList = new ArrayList<>();
            //处理断层场景（用户直接在excel上删除两个节点，工具到出会重置序号）
            List<GanttTasksDto> allList = new ArrayList<>();
            // 循环工作表Sheet 仅处理第一个sheet
            for (int numSheet = 0; numSheet < 1; numSheet++) {
                Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
                if (hssfSheet == null) {
                    continue;
                }
                // 循环行Row 从第三列开始
                for (int rowNum = 2; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                    Row hssfRow = hssfSheet.getRow(rowNum);
                    if (hssfRow != null) {
                        task = new GanttTasksDto();
                        //此时记录的是项目的编码
                        task.setTaskId(GenerateUUID.getUUID());
                        task.setTaskNumber(hssfRow.getCell(0).toString());
                        if (hssfRow.getCell(1).toString().equals("是")) task.setTaskState(1);
                        else task.setTaskState(0);
                        task.setTaskMode(hssfRow.getCell(2) == null ? null : hssfRow.getCell(2).toString());
                        task.setTaskText(hssfRow.getCell(3) == null ? null : hssfRow.getCell(3).toString());
                        String taskDuration = hssfRow.getCell(4).toString().trim().replace("个工作日", "").replace("?", "");
                        task.setTaskDuration(Float.valueOf(taskDuration));
                        task.setTaskStartDate(DateUtil.String2Date(hssfRow.getCell(5).toString(), "yyyy年mm月dd日 HH:mm"));
                        task.setTaskEndDate(DateUtil.String2Date(hssfRow.getCell(6).toString(), "yyyy年mm月dd日 HH:mm"));
                        task.setTaskPreTaskId(hssfRow.getCell(7) == null ? null : hssfRow.getCell(7).toString());
                        task.setTaskOutlineLevel(hssfRow.getCell(8) == null ? null : hssfRow.getCell(8).toString());
                        task.setTaskPriority("正常");
                        task.setTaskRemark(hssfRow.getCell(9) == null ? null : hssfRow.getCell(9).toString());
                        oneColList.add(task);
                        if (Integer.parseInt(hssfRow.getCell(0).toString()) - allList.size() > 1) {
                            for (int i = 0; i < Integer.parseInt(hssfRow.getCell(0).toString()) - allList.size() - 1; i++) {
                                GanttTasksDto task1 = new GanttTasksDto();
                                task1.setTaskNumber(Integer.parseInt(hssfRow.getCell(0).toString()) - i - 1 + "");
                                allList.add(task1);
                            }
                            allList.add(task);
                        } else {
                            allList.add(task);
                        }
                    }
                }
            }

            for (GanttTasksDto model : oneColList) {
                if (model.getTaskOutlineLevel() != null && model.getTaskOutlineLevel().equals("1")) {
                    model.setTaskParent("0");
                    againId = new ArrayList<>();
                    againId.add(model.getTaskId());
                } else {
                    for (int i = againId.size() - 1; i < Integer.parseInt(model.getTaskOutlineLevel()); i++) {
                        againId.add("占位符");
                    }
                    againId.set(Integer.parseInt(model.getTaskOutlineLevel()) - 1, model.getTaskId());
                    model.setTaskParent(againId.get(Integer.parseInt(model.getTaskOutlineLevel()) - 2));
                }
                String taskFront = model.getTaskPreTaskId();
                if (taskFront != null) {
                    String[] indexs = taskFront.split(",");
                    String preTaskId = "";
                    for (int i = 0; i < indexs.length; i++) {
                        String index = indexs[i];
                        if (!ValidateUtil.isEmpty(allList.get(Integer.parseInt(index) - 1).getTaskId())) {
                            preTaskId += allList.get(Integer.parseInt(index) - 1).getTaskId();
                            if (i != indexs.length - 1) preTaskId += ",";
                        }
                    }
                    model.setTaskPreTaskId(preTaskId);
                }
                list.add(model);
            }
            return list;
        }
        finally {
            is.close();
            hssfWorkbook.close();
        }
    }

    /**
     * 项目注册匡算清单excel
     *
     * @param filePath
     * @return
     */
    public static List<PreProjectRegisterDtlModel> uploadProjectRegisterDtlExcel(String filePath) throws IOException {
        List<PreProjectRegisterDtlModel> list = new ArrayList<>();
        InputStream is = new FileInputStream(new File(filePath));
        Workbook hssfWorkbook = null;
        if (filePath.endsWith("xlsx")) {
            hssfWorkbook = new XSSFWorkbook(is);//Excel 2007
        } else if (filePath.endsWith("xls")) {
            hssfWorkbook = new HSSFWorkbook(is);//Excel 2003
        }
        try {
            PreProjectRegisterDtlModel plan = null;
            // 循环工作表Sheet
            for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
                Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
                if (hssfSheet == null) {
                    continue;
                }
                // 循环行Row
                for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                    Row hssfRow = hssfSheet.getRow(rowNum);
                    if (hssfRow != null) {
                        plan = new PreProjectRegisterDtlModel();
                        //生成随机Id
                        plan.setPrdId(GenerateUUID.getUUID());
                        plan.setPrdNumber(ValidateUtil.isEmpty(hssfRow.getCell(0)) ? "" : hssfRow.getCell(0).toString());
                        //此时记录的是节点编码
                        plan.setPrdParenetId(hssfRow.getCell(1).toString());
                        plan.setPrdCode(ValidateUtil.isEmpty(hssfRow.getCell(2)) ? "" : hssfRow.getCell(2).toString());
                        plan.setPrdName(ValidateUtil.isEmpty(hssfRow.getCell(3)) ? "" : hssfRow.getCell(3).toString());
                        plan.setPrdUnit(ValidateUtil.isEmpty(hssfRow.getCell(4)) ? "" : hssfRow.getCell(4).toString());
                        plan.setPrdCount(new Float(ValidateUtil.isEmpty(hssfRow.getCell(5)) ? "0" : hssfRow.getCell(5).toString()));
                        plan.setPrdPrice(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(6)) ? "0" : hssfRow.getCell(6).toString()));
                        plan.setPrdTotal(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(7)) ? "0" : hssfRow.getCell(7).toString()).multiply(new BigDecimal(10000)));
                        plan.setPrdRemark(ValidateUtil.isEmpty(hssfRow.getCell(8)) ? "" : hssfRow.getCell(8).toString());
                        list.add(plan);
                    }
                }
            }
            //将节点编码替换成对应ID
            if (!CollectionUtils.isEmpty(list)) {
                List<PreProjectRegisterDtlModel> datas = new ArrayList<>();
                List<PreProjectRegisterDtlModel> headers = list.stream().filter(n -> !n.getPrdParenetId().contains("-")).collect(Collectors.toList());
                headers.forEach(header -> {
                    List<PreProjectRegisterDtlModel> child = new ArrayList<>();
                    String nodeCode = header.getPrdParenetId();
                    header.setPrdParenetId("0");
                    List<PreProjectRegisterDtlModel> childs = list.stream().filter(n -> n.getPrdParenetId().contains("-") && n.getPrdParenetId().substring(0, n.getPrdParenetId().lastIndexOf('-')).equals(nodeCode)).collect(Collectors.toList());
                    getProjectRegisterDtlChilds(child, childs, list, header);
                    header.setChildNodes(child);
                    datas.add(header);
                });
                return datas;
            }
            return list;
        } finally {
            is.close();
            hssfWorkbook.close();
        }
    }

    /**
     * 项目立项估算清单excel
     *
     * @param filePath
     * @return
     */
    public static List<PreProApprovalDtlModel> uploadPreProApprovalDtlExcel(String filePath) throws IOException {
        List<PreProApprovalDtlModel> list = new ArrayList<>();
        InputStream is = new FileInputStream(new File(filePath));
        Workbook hssfWorkbook = null;
        if (filePath.endsWith("xlsx")) {
            hssfWorkbook = new XSSFWorkbook(is);//Excel 2007
        } else if (filePath.endsWith("xls")) {
            hssfWorkbook = new HSSFWorkbook(is);//Excel 2003
        }
        try {
            PreProApprovalDtlModel plan = null;
            // 循环工作表Sheet
            for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
                Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
                if (hssfSheet == null) {
                    continue;
                }
                // 循环行Row
                for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                    Row hssfRow = hssfSheet.getRow(rowNum);
                    if (hssfRow != null) {
                        plan = new PreProApprovalDtlModel();
                        //生成随机Id
                        plan.setPadId(GenerateUUID.getUUID());
                        plan.setPadNumber(ValidateUtil.isEmpty(hssfRow.getCell(0)) ? "" : hssfRow.getCell(0).toString());
                        //此时记录的是节点编码
                        plan.setPadParenetId(hssfRow.getCell(1).toString());
                        plan.setPadCode(ValidateUtil.isEmpty(hssfRow.getCell(2)) ? "" : hssfRow.getCell(2).toString());
                        plan.setPadName(ValidateUtil.isEmpty(hssfRow.getCell(3)) ? "" : hssfRow.getCell(3).toString());
                        plan.setPadUnit(ValidateUtil.isEmpty(hssfRow.getCell(4)) ? "" : hssfRow.getCell(4).toString());
                        plan.setPadCount(new Float(ValidateUtil.isEmpty(hssfRow.getCell(5)) ? "0" : hssfRow.getCell(5).toString()));
                        plan.setPadPrice(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(6)) ? "0" : hssfRow.getCell(6).toString()));
                        plan.setPadTotal(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(7)) ? "0" : hssfRow.getCell(7).toString()).multiply(new BigDecimal(10000)));
                        plan.setPadRemark(ValidateUtil.isEmpty(hssfRow.getCell(8)) ? "" : hssfRow.getCell(8).toString());
                        list.add(plan);
                    }
                }
            }
            //将节点编码替换成对应ID
            if (!CollectionUtils.isEmpty(list)) {
                List<PreProApprovalDtlModel> datas = new ArrayList<>();
                List<PreProApprovalDtlModel> headers = list.stream().filter(n -> !n.getPadParenetId().contains("-")).collect(Collectors.toList());
                headers.forEach(header -> {
                    List<PreProApprovalDtlModel> child = new ArrayList<>();
                    String nodeCode = header.getPadParenetId();
                    header.setPadParenetId("0");
                    List<PreProApprovalDtlModel> childs = list.stream().filter(n -> n.getPadParenetId().contains("-") && n.getPadParenetId().substring(0, n.getPadParenetId().lastIndexOf('-')).equals(nodeCode)).collect(Collectors.toList());
                    getPreProApprovalDtlChilds(child, childs, list, header);
                    header.setChildNodes(child);
                    datas.add(header);
                });
                return datas;
            }
            return list;
        } finally {
            is.close();
            hssfWorkbook.close();
        }
    }


    /**
     * 项目注册概算清单excel
     *
     * @param filePath
     * @return
     */
    public static List<PreProjectDtlModel> uploadPreProjectDtlExcel(String filePath) throws IOException {
        List<PreProjectDtlModel> list = new ArrayList<>();
        InputStream is = new FileInputStream(new File(filePath));
        Workbook hssfWorkbook = null;
        if (filePath.endsWith("xlsx")) {
            hssfWorkbook = new XSSFWorkbook(is);//Excel 2007
        } else if (filePath.endsWith("xls")) {
            hssfWorkbook = new HSSFWorkbook(is);//Excel 2003
        }
        try {
            PreProjectDtlModel plan = null;
            // 循环工作表Sheet
            for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
                Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
                if (hssfSheet == null) {
                    continue;
                }
                // 循环行Row
                for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                    Row hssfRow = hssfSheet.getRow(rowNum);
                    if (hssfRow != null) {
                        plan = new PreProjectDtlModel();
                        //生成随机Id
                        plan.setPpdId(GenerateUUID.getUUID());
                        plan.setPpdNumber(ValidateUtil.isEmpty(hssfRow.getCell(0)) ? "" : hssfRow.getCell(0).toString());
                        //此时记录的是节点编码
                        plan.setPpdParenetId(hssfRow.getCell(1).toString());
                        plan.setPpdCode(ValidateUtil.isEmpty(hssfRow.getCell(2)) ? "" : hssfRow.getCell(2).toString());
                        plan.setPpdName(ValidateUtil.isEmpty(hssfRow.getCell(3)) ? "" : hssfRow.getCell(3).toString());
                        plan.setPpdUnit(ValidateUtil.isEmpty(hssfRow.getCell(4)) ? "" : hssfRow.getCell(4).toString());
                        plan.setPpdCount(new Float(ValidateUtil.isEmpty(hssfRow.getCell(5)) ? "0" : hssfRow.getCell(5).toString()));
                        plan.setPpdPrice(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(6)) ? "0" : hssfRow.getCell(6).toString()));
                        plan.setPpdTotal(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(7)) ? "0" : hssfRow.getCell(7).toString()).multiply(new BigDecimal(10000)));
                        plan.setPpdRemark(ValidateUtil.isEmpty(hssfRow.getCell(8)) ? "" : hssfRow.getCell(8).toString());
                        list.add(plan);
                    }
                }
            }
            //将节点编码替换成对应ID
            if (!CollectionUtils.isEmpty(list)) {
                List<PreProjectDtlModel> datas = new ArrayList<>();
                List<PreProjectDtlModel> headers = list.stream().filter(n -> !n.getPpdParenetId().contains("-")).collect(Collectors.toList());
                headers.forEach(header -> {
                    List<PreProjectDtlModel> child = new ArrayList<>();
                    String nodeCode = header.getPpdParenetId();
                    header.setPpdParenetId("0");
                    List<PreProjectDtlModel> childs = list.stream().filter(n -> n.getPpdParenetId().contains("-") && n.getPpdParenetId().substring(0, n.getPpdParenetId().lastIndexOf('-')).equals(nodeCode)).collect(Collectors.toList());
                    getPreProjectDtlChilds(child, childs, list, header);
                    header.setChildNodes(child);
                    datas.add(header);
                });
                return datas;
            }
            return list;
        } finally {
            is.close();
            hssfWorkbook.close();
        }
    }

/*

    public static List<InvestmentEstimateBreakdownDetailsModel> uploadEstimateBreakdownExecel(String filePath) throws IOException {
        List<InvestmentEstimateBreakdownDetailsModel> list = new ArrayList<>();
        InputStream is = new FileInputStream(new File(filePath));
        Workbook workbook = null;
        if (filePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(is);
        } else if (filePath.endsWith("xls")) {
            workbook = new HSSFWorkbook(is);
        }
        try {
            Map<String, InvestmentEstimateBreakdownDetailsModel> nodeMap = new HashMap<>();
            List<InvestmentEstimateBreakdownDetailsModel> rootNodes = new ArrayList<>();
            Stack<String> stack = new Stack<>();
            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();

            // 存储每个父节点下的子节点序号映射
            // key: 父节点编码, value: 该父节点下已存在的子节点序号集合
            Map<String, Set<Integer>> parentSeqMap = new HashMap<>();

            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (row == null) continue;

                Cell nameCell = row.getCell(1);
                String nameValue = formatter.formatCellValue(nameCell).trim();
                if (nameCell == null || nameValue.isEmpty()) {
                    continue;
                }

                InvestmentEstimateBreakdownDetailsModel model = new InvestmentEstimateBreakdownDetailsModel();
                model.setId(GenerateUUID.getUUID());

                // A列: 节点序号
                Cell nodeCodeCell = row.getCell(0);
                String nodeCode = (nodeCodeCell != null) ?
                        formatter.formatCellValue(nodeCodeCell).trim() : "0";
                model.setLevelCode(nodeCode);
                model.setIsbdFeeTypeName(nameValue);

                // C列: 金额（万元），需转换为元
                Cell amountCell = row.getCell(2);
                double amount = 0.0;
                if (amountCell != null) {
                    if (amountCell.getCellType() == CellType.NUMERIC) {
                        amount = amountCell.getNumericCellValue() * 10000;
                    } else {
                        String amountStr = amountCell.toString().trim();
                        if (!amountStr.isEmpty()) {
                            try {
                                amount = Double.parseDouble(amountStr) * 10000;
                            } catch (NumberFormatException e) {
                                throw new RuntimeException("第" + (rowNum+1) + "行【" + nameValue + "】: 金额格式错误。");
                            }
                        }
                    }
                }
                model.setIsbdAmount(amount);

                // D列: 工程量
                Cell quantityCell = row.getCell(3);
                String quantityStr = "";
                if (quantityCell != null) {
                    if (quantityCell.getCellType() == CellType.NUMERIC) {
                        quantityStr = String.valueOf(quantityCell.getNumericCellValue());
                    } else {
                        quantityStr = quantityCell.toString().trim();
                    }
                }
                model.setWorkMeasure(quantityStr);

                // E列: 单位
                Cell unitCell = row.getCell(4);
                model.setUnit((unitCell != null) ? formatter.formatCellValue(unitCell).trim() : "");

                // F列: 单价（元）
                Cell priceCell = row.getCell(5);
                String priceStr = "";
                if (priceCell != null) {
                    if (priceCell.getCellType() == CellType.NUMERIC) {
                        priceStr = String.valueOf(priceCell.getNumericCellValue());
                    } else {
                        priceStr = formatter.formatCellValue(priceCell).trim();
                    }
                }
                model.setUnitPrice(priceStr);

                // 计算节点深度
                int depth = calculateDepth(nodeCode);

                // 弹出栈中深度 >= 当前深度的节点
                while (!stack.isEmpty() && calculateDepth(stack.peek()) >= depth) {
                    stack.pop();
                }

                // 设置父节点
                String parentCode = "";
                if (stack.isEmpty()) {
                    model.setParentId("零");
                    rootNodes.add(model);
                } else {
                    parentCode = stack.peek();
                    InvestmentEstimateBreakdownDetailsModel parent = nodeMap.get(parentCode);
                    if (parent != null) {
                        model.setParentId(parent.getId());
                    } else {
                        model.setParentId("零");
                        rootNodes.add(model);
                    }
                }

                // === 新增: 序号连续性检查 ===
                if (!nodeCode.isEmpty() && !isChineseNumber(nodeCode)) {
                    // 解析当前节点的序号值
                    int seqValue = parseSequenceValue(nodeCode);

                    if (!parentCode.isEmpty()) {
                        // 获取父节点下的子节点序号集合
                        Set<Integer> seqSet = parentSeqMap.computeIfAbsent(parentCode, k -> new HashSet<>());

                        // 检查序号是否连续
                        if (!seqSet.isEmpty()) {
                            int expected = Collections.max(seqSet) + 1;
                            if (seqValue != expected) {
                                String parentName = "";
                                if (nodeMap.containsKey(parentCode)) {
                                    parentName = nodeMap.get(parentCode).getIsbdFeeTypeName();
                                }
                                // 修改点：在错误信息中添加当前行的B列内容（nameValue）
                                throw new RuntimeException("第" + (rowNum+1) + "行【" + nameValue + "】: 序号不连续。");
                            }
                        }

                        // 添加当前序号到父节点集合
                        seqSet.add(seqValue);
                    }
                }

                // 将当前节点压栈
                stack.push(nodeCode);
                nodeMap.put(nodeCode, model);
                list.add(model);
            }

            // 构建层级结构
            Map<String, InvestmentEstimateBreakdownDetailsModel> idMap = new HashMap<>();
            for (InvestmentEstimateBreakdownDetailsModel node : list) {
                idMap.put(node.getId(), node);
            }

            for (InvestmentEstimateBreakdownDetailsModel node : list) {
                if ("零".equals(node.getParentId())) continue;

                InvestmentEstimateBreakdownDetailsModel parent = idMap.get(node.getParentId());
                if (parent != null) {
                    if (parent.getChildNodes() == null) {
                        parent.setChildNodes(new ArrayList<>());
                    }
                    parent.getChildNodes().add(node);
                }
            }

            // 从叶子节点向上汇总金额
            Map<String, Double> amountMap = new HashMap<>();
            for (int i = list.size() - 1; i >= 0; i--) {
                InvestmentEstimateBreakdownDetailsModel node = list.get(i);
                String nodeId = node.getId();

                // 如果有子节点，计算子节点金额总和
                if (node.getChildNodes() != null && !node.getChildNodes().isEmpty()) {
                    double sum = 0.0;
                    for (InvestmentEstimateBreakdownDetailsModel child : node.getChildNodes()) {
                        sum += amountMap.getOrDefault(child.getId(), child.getIsbdAmount());
                    }
                    // 用子节点总和覆盖当前节点金额
                    amountMap.put(nodeId, sum);
                    node.setIsbdAmount(sum);
                } else {
                    // 叶子节点保持原金额
                    amountMap.put(nodeId, node.getIsbdAmount());
                }
            }

            // 汇总根节点金额（工程总投资）
            double totalAmount = 0.0;
            for (InvestmentEstimateBreakdownDetailsModel root : rootNodes) {
                // 跳过"工程总投资"节点本身
                if (!"工程总投资".equals(root.getIsbdFeeTypeName())) {
                    totalAmount += amountMap.getOrDefault(root.getId(), root.getIsbdAmount());
                }
            }

            // 设置工程总投资金额
            for (InvestmentEstimateBreakdownDetailsModel root : rootNodes) {
                if ("工程总投资".equals(root.getIsbdFeeTypeName())) {
                    root.setIsbdAmount(totalAmount);
                    break;
                }
            }

            return rootNodes;
        } finally {
            if (workbook != null) workbook.close();
            is.close();
        }
    }

// ===== 辅助方法 =====

    // 计算节点深度
    private static int calculateDepth(String nodeCode) {
        if (nodeCode == null || nodeCode.isEmpty()) {
            return 0;
        }
        if (isChineseNumber(nodeCode)) {
            return 0;
        }
        int dotCount = nodeCode.length() - nodeCode.replace(".", "").length();
        return dotCount + 1;
    }

    // 判断是否是汉字序号
    private static boolean isChineseNumber(String str) {
        return str.matches("^[一二三四五六七八九十]+$");
    }

    // 解析序号值
    private static int parseSequenceValue(String nodeCode) {
        if (nodeCode.contains(".")) {
            // 处理带小数点的序号（如1.2.3）
            String lastPart = nodeCode.substring(nodeCode.lastIndexOf('.') + 1);
            try {
                return Integer.parseInt(lastPart);
            } catch (NumberFormatException e) {
                throw new RuntimeException("无效的序号格式: " + nodeCode);
            }
        } else {
            // 处理单级序号（如1, 2, 3）
            try {
                return Integer.parseInt(nodeCode);
            } catch (NumberFormatException e) {
                throw new RuntimeException("无效的序号格式: " + nodeCode);
            }
        }
    }*/
/*

    public static List<InvestmentEstimateBreakdownDetailsModel> uploadEstimateBreakdownExecel(String filePath) throws IOException {
        List<InvestmentEstimateBreakdownDetailsModel> list = new ArrayList<>();
        InputStream is = new FileInputStream(new File(filePath));
        Workbook workbook = null;
        if (filePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(is);
        } else if (filePath.endsWith("xls")) {
            workbook = new HSSFWorkbook(is);
        }
        try {
            Map<String, InvestmentEstimateBreakdownDetailsModel> nodeMap = new HashMap<>();
            List<InvestmentEstimateBreakdownDetailsModel> rootNodes = new ArrayList<>();
            Stack<String> stack = new Stack<>();
            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();

            // 存储每个父节点下的子节点序号映射
            Map<String, Set<Integer>> parentSeqMap = new HashMap<>();

            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (row == null) continue;

                Cell nameCell = row.getCell(1);
                String nameValue = formatter.formatCellValue(nameCell).trim();
                if (nameCell == null || nameValue.isEmpty()) {
                    continue;
                }

                InvestmentEstimateBreakdownDetailsModel model = new InvestmentEstimateBreakdownDetailsModel();
                model.setId(GenerateUUID.getUUID());

                // A列: 节点序号
                Cell nodeCodeCell = row.getCell(0);
                String nodeCode = (nodeCodeCell != null) ?
                        formatter.formatCellValue(nodeCodeCell).trim() : "0";
                model.setLevelCode(nodeCode);
                model.setIsbdFeeTypeName(nameValue);

                // ===== 新增：工程量与单价计算功能 =====
                double calculatedAmount = 0.0;
                boolean useCalculatedAmount = false;

                // D列: 工程量
                Cell quantityCell = row.getCell(3);
                double quantityValue = 0.0;
                String quantityStr = "";
                if (quantityCell != null) {
                    if (quantityCell.getCellType() == CellType.NUMERIC) {
                        quantityValue = quantityCell.getNumericCellValue();
                        quantityStr = String.valueOf(quantityValue);
                    } else {
                        quantityStr = quantityCell.toString().trim();
                        if (!quantityStr.isEmpty()) {
                            try {
                                quantityValue = Double.parseDouble(quantityStr);
                            } catch (NumberFormatException e) {
                                // 保留原字符串值
                            }
                        }
                    }
                }
                model.setWorkMeasure(quantityStr);

                // F列: 单价（元）
                Cell priceCell = row.getCell(5);
                double priceValue = 0.0;
                String priceStr = "";
                if (priceCell != null) {
                    if (priceCell.getCellType() == CellType.NUMERIC) {
                        priceValue = priceCell.getNumericCellValue();
                        priceStr = String.valueOf(priceValue);
                    } else {
                        priceStr = formatter.formatCellValue(priceCell).trim();
                        if (!priceStr.isEmpty()) {
                            try {
                                priceValue = Double.parseDouble(priceStr);
                            } catch (NumberFormatException e) {
                                // 保留原字符串值
                            }
                        }
                    }
                }
                model.setUnitPrice(priceStr);

                // E列: 单位
                Cell unitCell = row.getCell(4);
                model.setUnit((unitCell != null) ? formatter.formatCellValue(unitCell).trim() : "");

                // 如果工程量和单价都有效，则计算金额
                if (quantityValue > 0 && priceValue > 0) {
                    calculatedAmount = quantityValue * priceValue; // 单位：元
                    useCalculatedAmount = true;
                }

                // C列: 金额处理逻辑
                double amount = 0.0;
                Cell amountCell = row.getCell(2);
                if (useCalculatedAmount) {
                    // 使用工程量×单价计算的金额
                    amount = calculatedAmount;
                } else {
                    // 使用原有的金额处理逻辑
                    if (amountCell != null) {
                        if (amountCell.getCellType() == CellType.NUMERIC) {
                            amount = amountCell.getNumericCellValue() * 10000;
                        } else {
                            String amountStr = amountCell.toString().trim();
                            if (!amountStr.isEmpty()) {
                                try {
                                    amount = Double.parseDouble(amountStr) * 10000;
                                } catch (NumberFormatException e) {
                                    throw new RuntimeException("第" + (rowNum+1) + "行【" + nameValue + "】: 数据格式错误。");
                                }
                            }
                        }
                    }
                }
                model.setIsbdAmount(amount);

                // 计算节点深度
                int depth = calculateDepth(nodeCode);

                // 弹出栈中深度 >= 当前深度的节点
                while (!stack.isEmpty() && calculateDepth(stack.peek()) >= depth) {
                    stack.pop();
                }

                // 设置父节点
                String parentCode = "";
                if (stack.isEmpty()) {
                    model.setParentId("零");
                    rootNodes.add(model);
                } else {
                    parentCode = stack.peek();
                    InvestmentEstimateBreakdownDetailsModel parent = nodeMap.get(parentCode);
                    if (parent != null) {
                        model.setParentId(parent.getId());
                    } else {
                        model.setParentId("零");
                        rootNodes.add(model);
                    }
                }

                // 序号连续性检查
                if (!nodeCode.isEmpty() && !isChineseNumber(nodeCode)) {
                    // 解析当前节点的序号值
                    int seqValue = parseSequenceValue(nodeCode);

                    if (!parentCode.isEmpty()) {
                        // 获取父节点下的子节点序号集合
                        Set<Integer> seqSet = parentSeqMap.computeIfAbsent(parentCode, k -> new HashSet<>());

                        // 检查序号是否连续
                        if (!seqSet.isEmpty()) {
                            int expected = Collections.max(seqSet) + 1;
                            if (seqValue != expected) {
                                String parentName = "";
                                if (nodeMap.containsKey(parentCode)) {
                                    parentName = nodeMap.get(parentCode).getIsbdFeeTypeName();
                                }
                                throw new RuntimeException("第" + (rowNum+1) + "行【" + nameValue + "】: 序号不连续。");
                            }
                        }

                        // 添加当前序号到父节点集合
                        seqSet.add(seqValue);
                    }
                }

                // 将当前节点压栈
                stack.push(nodeCode);
                nodeMap.put(nodeCode, model);
                list.add(model);
            }

            // 构建层级结构
            Map<String, InvestmentEstimateBreakdownDetailsModel> idMap = new HashMap<>();
            for (InvestmentEstimateBreakdownDetailsModel node : list) {
                idMap.put(node.getId(), node);
            }

            for (InvestmentEstimateBreakdownDetailsModel node : list) {
                if ("零".equals(node.getParentId())) continue;

                InvestmentEstimateBreakdownDetailsModel parent = idMap.get(node.getParentId());
                if (parent != null) {
                    if (parent.getChildNodes() == null) {
                        parent.setChildNodes(new ArrayList<>());
                    }
                    parent.getChildNodes().add(node);
                }
            }

            // 从叶子节点向上汇总金额
            Map<String, Double> amountMap = new HashMap<>();
            for (int i = list.size() - 1; i >= 0; i--) {
                InvestmentEstimateBreakdownDetailsModel node = list.get(i);
                String nodeId = node.getId();

                // 如果有子节点，计算子节点金额总和
                if (node.getChildNodes() != null && !node.getChildNodes().isEmpty()) {
                    double sum = 0.0;
                    for (InvestmentEstimateBreakdownDetailsModel child : node.getChildNodes()) {
                        sum += amountMap.getOrDefault(child.getId(), child.getIsbdAmount());
                    }
                    // 用子节点总和覆盖当前节点金额
                    amountMap.put(nodeId, sum);
                    node.setIsbdAmount(sum);
                } else {
                    // 叶子节点保持原金额
                    amountMap.put(nodeId, node.getIsbdAmount());
                }
            }

            // 汇总根节点金额（工程总投资）
            double totalAmount = 0.0;
            for (InvestmentEstimateBreakdownDetailsModel root : rootNodes) {
                // 跳过"工程总投资"节点本身
                if (!"工程总投资".equals(root.getIsbdFeeTypeName())) {
                    totalAmount += amountMap.getOrDefault(root.getId(), root.getIsbdAmount());
                }
            }

            // 设置工程总投资金额
            for (InvestmentEstimateBreakdownDetailsModel root : rootNodes) {
                if ("工程总投资".equals(root.getIsbdFeeTypeName())) {
                    root.setIsbdAmount(totalAmount);
                    break;
                }
            }

            return rootNodes;
        } finally {
            if (workbook != null) workbook.close();
            is.close();
        }
    }

// ===== 辅助方法 =====

    // 计算节点深度
    private static int calculateDepth(String nodeCode) {
        if (nodeCode == null || nodeCode.isEmpty()) {
            return 0;
        }
        if (isChineseNumber(nodeCode)) {
            return 0;
        }
        int dotCount = nodeCode.length() - nodeCode.replace(".", "").length();
        return dotCount + 1;
    }

    // 判断是否是汉字序号
    private static boolean isChineseNumber(String str) {
        return str.matches("^[一二三四五六七八九十]+$");
    }

    // 解析序号值
    private static int parseSequenceValue(String nodeCode) {
        if (nodeCode.contains(".")) {
            // 处理带小数点的序号（如1.2.3）
            String lastPart = nodeCode.substring(nodeCode.lastIndexOf('.') + 1);
            try {
                return Integer.parseInt(lastPart);
            } catch (NumberFormatException e) {
                throw new RuntimeException("无效的序号格式: " + nodeCode);
            }
        } else {
            // 处理单级序号（如1, 2, 3）
            try {
                return Integer.parseInt(nodeCode);
            } catch (NumberFormatException e) {
                throw new RuntimeException("无效的序号格式: " + nodeCode);
            }
        }
    }
*/
    public static Map<String, Object> uploadEstimateBreakdownExecel(String filePath) throws IOException {
        List<InvestmentEstimateBreakdownDetailsModel> list = new ArrayList<>();
        InputStream is = new FileInputStream(new File(filePath));
        Workbook workbook = null;
        if (filePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(is);
        } else if (filePath.endsWith("xls")) {
            workbook = new HSSFWorkbook(is);
        }
        try {
            String msg = "";
            Map<String, InvestmentEstimateBreakdownDetailsModel> nodeMap = new HashMap<>();
            List<InvestmentEstimateBreakdownDetailsModel> rootNodes = new ArrayList<>();
            Stack<String> stack = new Stack<>();
            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();

            // 存储每个父节点下的子节点序号映射（使用父节点ID作为key）
            Map<String, Set<Integer>> parentSeqMap = new HashMap<>();

            // 读取现有字典值
            List<DictDto> dictList = DictionaryUtils.getDictList("JPMsoft_DW");
            Map<String, String> dictMap = dictList.stream()
                    .collect(Collectors.toMap(DictDto::getName, DictDto::getCode));

            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (row == null) continue;

                Cell nameCell = row.getCell(1);
                String nameValue = formatter.formatCellValue(nameCell).trim();
                if (nameCell == null || nameValue.isEmpty()) {
                    continue;
                }

                InvestmentEstimateBreakdownDetailsModel model = new InvestmentEstimateBreakdownDetailsModel();
                model.setId(GenerateUUID.getUUID());

                // A列: 节点序号
                Cell nodeCodeCell = row.getCell(0);
                String nodeCode = (nodeCodeCell != null) ?
                        formatter.formatCellValue(nodeCodeCell).trim() : "";
                model.setLevelCode(nodeCode.isEmpty() ? "0" : nodeCode);
                model.setIsbdFeeTypeName(nameValue);

                // ==== 工程量处理 (D列) ====
                BigDecimal quantityValue = null;
                Cell quantityCell = row.getCell(3);
                if (quantityCell != null) {
                    String quantityStr = formatter.formatCellValue(quantityCell).trim();
                    if (!quantityStr.isEmpty()) {
                        try {
                            quantityValue = new BigDecimal(quantityStr);
                        } catch (NumberFormatException e) {
                            throw new RuntimeException("第" + (rowNum + 1) + "行【" + nameValue + "】: 工程量格式错误。");
                        }
                    }
                }
                model.setWorkMeasure(quantityValue);

                // ==== 金额处理 (C列) ====
                BigDecimal amount = BigDecimal.ZERO;
                Cell amountCell = row.getCell(2);
                if (amountCell != null) {
                    String amountStr = formatter.formatCellValue(amountCell).trim();
                    if (!amountStr.isEmpty()) {
                        try {
                            amount = new BigDecimal(amountStr).multiply(BigDecimal.valueOf(10000));
                        } catch (NumberFormatException e) {
                            throw new RuntimeException("第" + (rowNum+1) + "行【" + nameValue + "】: 金额格式错误。");
                        }
                    }
                }
                model.setIsbdAmount(amount);

                // ==== 单位处理 (E列) ====
                Cell unitCell = row.getCell(4);
                if (unitCell != null) {
                    String unitStr = formatter.formatCellValue(unitCell).trim();
                    model.setUnit(unitStandardHandle(unitStr, dictMap));
                } else {
                    model.setUnit("");
                }
                if ("无法识别".equalsIgnoreCase(model.getUnit())) {
                    msg = "存在无法识别的单位，请手动选择！";
                    model.setUnit("");
                }

                // 初始化单价为空字符串
                model.setUnitPrice("");

                // 计算节点深度
                int depth = calculateDepth(nodeCode);

                // 弹出栈中深度 >= 当前深度的节点
                while (!stack.isEmpty() && calculateDepth(stack.peek()) >= depth) {
                    stack.pop();
                }

                // 设置父节点
                String parentCode = "";
                InvestmentEstimateBreakdownDetailsModel parent = null; // 提升parent变量作用域
                if (stack.isEmpty()) {
                    model.setParentId("零");
                    rootNodes.add(model);
                } else {
                    parentCode = stack.peek();
                    parent = nodeMap.get(parentCode); // 直接获取父节点对象
                    if (parent != null) {
                        model.setParentId(parent.getId());
                    } else {
                        model.setParentId("零");
                        rootNodes.add(model);
                    }
                }

                // 序号连续性检查
                if (!nodeCode.isEmpty() && !isChineseNumber(nodeCode)) {
                    // 解析当前节点的序号值
                    int seqValue = parseSequenceValue(nodeCode);

                    // 使用父节点ID作为key（如果有父节点）
                    if (parent != null) {
                        String parentKey = parent.getId(); // 使用父节点ID作为唯一标识
                        Set<Integer> seqSet = parentSeqMap.computeIfAbsent(parentKey, k -> new HashSet<>());

                        // 检查序号是否连续
                        if (!seqSet.isEmpty()) {
                            int expected = Collections.max(seqSet) + 1;
                            if (seqValue != expected) {
                                throw new RuntimeException("第" + (rowNum+1) + "行【" + nameValue + "】: 序号不连续。");
                            }
                        }
                        seqSet.add(seqValue);
                    }
                }

                // 将当前节点压栈
                stack.push(nodeCode);
                nodeMap.put(nodeCode, model);
                list.add(model);
            }


            // 构建层级结构
            Map<String, InvestmentEstimateBreakdownDetailsModel> idMap = new HashMap<>();
            for (InvestmentEstimateBreakdownDetailsModel node : list) {
                idMap.put(node.getId(), node);
            }

            for (InvestmentEstimateBreakdownDetailsModel node : list) {
                if ("零".equals(node.getParentId())) continue;

                InvestmentEstimateBreakdownDetailsModel parent = idMap.get(node.getParentId());
                if (parent != null) {
                    if (parent.getChildNodes() == null) {
                        parent.setChildNodes(new ArrayList<>());
                    }
                    parent.getChildNodes().add(node);
                }
            }

            // 从叶子节点向上汇总金额（单位：元）
            Map<String, BigDecimal> amountMap = new HashMap<>();
            for (int i = list.size() - 1; i >= 0; i--) {
                InvestmentEstimateBreakdownDetailsModel node = list.get(i);
                String nodeId = node.getId();

                // 如果有子节点，计算子节点金额总和
                if (node.getChildNodes() != null && !node.getChildNodes().isEmpty()) {
                    BigDecimal sum = BigDecimal.ZERO;
                    for (InvestmentEstimateBreakdownDetailsModel child : node.getChildNodes()) {
                        sum = sum.add(amountMap.getOrDefault(child.getId(), child.getIsbdAmount()));
                    }
                    // 用子节点总和覆盖当前节点金额
                    amountMap.put(nodeId, sum);
                    node.setIsbdAmount(sum);
                } else {
                    // 叶子节点保持原金额
                    amountMap.put(nodeId, node.getIsbdAmount());
                }
            }

            // ===== 新增：为所有节点计算单价（仅当工程量不为空时） =====
            for (InvestmentEstimateBreakdownDetailsModel node : list) {
                BigDecimal quantity = node.getWorkMeasure();
                BigDecimal amount = node.getIsbdAmount();

                if (ValidateUtil.isNotNull(quantity) && quantity.compareTo(BigDecimal.ZERO) > 0) {
                    BigDecimal calculatedPrice = amount.divide(quantity, 2, RoundingMode.HALF_UP);
                    node.setUnitPrice(calculatedPrice.toPlainString());
                } else {
                    // node.setUnitPrice(amount.setScale(2, RoundingMode.HALF_UP).toPlainString());
                    node.setUnitPrice("");
                }
            }
            // 汇总根节点金额（工程总投资，单位：元）
            BigDecimal totalAmount = BigDecimal.ZERO;
            for (InvestmentEstimateBreakdownDetailsModel root : rootNodes) {
                // 跳过"工程总投资"节点本身
                if (!"工程总投资".equals(root.getIsbdFeeTypeName())) {
                    BigDecimal rootAmount = amountMap.getOrDefault(root.getId(), root.getIsbdAmount());
                    totalAmount = totalAmount.add(rootAmount == null ? BigDecimal.ZERO : rootAmount);
                }
            }

            // 设置工程总投资金额（单位：元）
            for (InvestmentEstimateBreakdownDetailsModel root : rootNodes) {
                if ("工程总投资".equals(root.getIsbdFeeTypeName())) {
                    root.setIsbdAmount(totalAmount);
                    break;
                }
            }

            // 计算工程总投资单价（金额/工程量）
            for (InvestmentEstimateBreakdownDetailsModel root : rootNodes) {
                if ("工程总投资".equals(root.getIsbdFeeTypeName())) {
                    BigDecimal quantity = root.getWorkMeasure();
                    if (ValidateUtil.isNotNull(quantity) && quantity.compareTo(BigDecimal.ZERO) > 0) {
                        BigDecimal price = totalAmount.divide(quantity, 2, RoundingMode.HALF_UP);
                        root.setUnitPrice(price.toPlainString());
                    } else {
                        // root.setUnitPrice(totalAmount.setScale(2, RoundingMode.HALF_UP).toPlainString());
                        root.setUnitPrice("");
                    }
                    break;
                }
            }

            // 结果处理
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("rootNodes", rootNodes);
            resultMap.put("msg", ValidateUtil.isEmpty(msg) ? "上传成功！" : msg);
            return resultMap;
        } finally {
            if (workbook != null) workbook.close();
            is.close();
        }
    }

// ===== 辅助方法 =====

    // 计算节点深度
    private static int calculateDepth(String nodeCode) {
        if (nodeCode == null || nodeCode.isEmpty()) {
            return 0;
        }
        if (isChineseNumber(nodeCode)) {
            return 0;
        }
        int dotCount = nodeCode.length() - nodeCode.replace(".", "").length();
        return dotCount + 1;
    }

    // 判断是否是汉字序号
    private static boolean isChineseNumber(String str) {
        return str.matches("^[一二三四五六七八九十]+$");
    }

    // 解析序号值
    private static int parseSequenceValue(String nodeCode) {
        if (nodeCode.contains(".")) {
            // 处理带小数点的序号（如1.2.3）
            String lastPart = nodeCode.substring(nodeCode.lastIndexOf('.') + 1);
            try {
                return Integer.parseInt(lastPart);
            } catch (NumberFormatException e) {
                throw new RuntimeException("无效的序号格式: " + nodeCode);
            }
        } else {
            // 处理单级序号（如1, 2, 3）
            try {
                return Integer.parseInt(nodeCode);
            } catch (NumberFormatException e) {
                throw new RuntimeException("无效的序号格式: " + nodeCode);
            }
        }
    }

    private static String unitStandardHandle(String unit, Map<String, String> dictMap) {
        if (unit == null || unit.isEmpty()) {
            return unit;
        }
        // unit = unit.replaceFirst("^\\s*元/\\s*", "");
        String lowerUnit = unit.toLowerCase(Locale.ROOT)
                .replace("\u200C", "")
                .replace("\u200B", "")
                .trim();

        // 已存在dictMap中的单位直接返回对应code
        if (dictMap.containsKey(unit)) {
            return dictMap.get(unit);
        }

        if (unit.contains("千米") || unit.contains("公里") || lowerUnit.contains("km")) {
            return "km";
        } else if (unit.contains("平方米") || unit.contains("平米") || unit.contains("㎡") || lowerUnit.contains("m2") || lowerUnit.contains("m²")) {
            return "m²";
        } else if (unit.contains("立方米") || unit.contains("方") || unit.contains("m³") || lowerUnit.contains("m3")) {
            return "m³";
        } else if ((unit.contains("米") || lowerUnit.matches("(^|.*/)m$")) && !unit.contains("千米") && !lowerUnit.contains("km") && !unit.contains("平方米") && !unit.contains("平米") && !lowerUnit.contains("m2") && !unit.contains("立方米") && !lowerUnit.contains("m3")) {
            return "m";
        } else if (unit.contains("项")) {
            return "项";
        } else if (unit.contains("部")) {
            return "部";
        } else if (lowerUnit.contains("kva")) {
            return "KVA";
        } else if (unit.contains("个")) {
            return "个";
        } else if (unit.contains("张")) {
            return "张";
        } else if (unit.contains("株")) {
            return "株";
        } else if (unit.contains("丛")) {
            return "丛";
        } else if (unit.contains("处")) {
            return "处";
        } else if (unit.contains("千克") || unit.contains("公斤") || lowerUnit.contains("kg")) {
            return "kg";
        } else if (unit.contains("套")) {
            return "套";
        } else if (unit.contains("组")) {
            return "组";
        } else if (unit.contains("台")) {
            return "台";
        } else if (unit.contains("系统")) {
            return "系统";
        } else if (unit.contains("吨") || lowerUnit.contains("t")) {
            return "吨";
        } else {
            return "无法识别";
        }
    }

    /**
     * 概算申报清单excel
     *
     * @param filePath
     * @return
     */
    public static List<InvBudgetDeclareDtlModel> uploadBudgetDeclareExcel(String filePath) throws IOException {
        List<InvBudgetDeclareDtlModel> list = new ArrayList<>();
        InputStream is = new FileInputStream(new File(filePath));
        Workbook hssfWorkbook = null;
        if (filePath.endsWith("xlsx")) {
            hssfWorkbook = new XSSFWorkbook(is);//Excel 2007
        } else if (filePath.endsWith("xls")) {
            hssfWorkbook = new HSSFWorkbook(is);//Excel 2003
        }
        try {
            InvBudgetDeclareDtlModel plan = null;
            // 循环工作表Sheet
            for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
                Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
                if (hssfSheet == null) {
                    continue;
                }
                // 循环行Row
                for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                    Row hssfRow = hssfSheet.getRow(rowNum);
                    if (hssfRow != null) {
                        plan = new InvBudgetDeclareDtlModel();
                        //生成随机Id
                        plan.setBddId(GenerateUUID.getUUID());
                        plan.setBddNumber(ValidateUtil.isEmpty(hssfRow.getCell(0)) ? "" : hssfRow.getCell(0).toString());
                        //此时记录的是节点编码
                        plan.setBddParentId(hssfRow.getCell(1).toString());
                        plan.setBddCode(ValidateUtil.isEmpty(hssfRow.getCell(2)) ? "" : hssfRow.getCell(2).toString());
                        plan.setBddName(ValidateUtil.isEmpty(hssfRow.getCell(3)) ? "" : hssfRow.getCell(3).toString());
                        plan.setBddUnit(ValidateUtil.isEmpty(hssfRow.getCell(4)) ? "" : hssfRow.getCell(4).toString());
                        plan.setBddCount(new Float(ValidateUtil.isEmpty(hssfRow.getCell(5)) ? "0" : hssfRow.getCell(5).toString()));
                        plan.setBddPrice(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(6)) ? "0" : hssfRow.getCell(6).toString()));
                        plan.setBddTotal(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(7)) ? "0" : hssfRow.getCell(7).toString()).multiply(new BigDecimal(10000)));
                        plan.setBddRemark(ValidateUtil.isEmpty(hssfRow.getCell(8)) ? "" : hssfRow.getCell(8).toString());
                        list.add(plan);
                    }
                }
            }
            //将节点编码替换成对应ID
            if (!CollectionUtils.isEmpty(list)) {
                List<InvBudgetDeclareDtlModel> datas = new ArrayList<>();
                List<InvBudgetDeclareDtlModel> headers = list.stream().filter(n -> !n.getBddParentId().contains("-")).collect(Collectors.toList());
                headers.forEach(header -> {
                    List<InvBudgetDeclareDtlModel> child = new ArrayList<>();
                    String nodeCode = header.getBddParentId();
                    header.setBddParentId("0");
                    List<InvBudgetDeclareDtlModel> childs = list.stream().filter(n -> n.getBddParentId().contains("-") && n.getBddParentId().substring(0, n.getBddParentId().lastIndexOf('-')).equals(nodeCode)).collect(Collectors.toList());
                    getBudgetDeclareDtlChilds(child, childs, list, header);
                    header.setChildNodes(child);
                    datas.add(header);
                });
                return datas;
            }
            return list;
        }finally {
            is.close();
            hssfWorkbook.close();
        }
    }

    /**
     * 初设评审：概算申报清单excel
     * @param filePath
     * @return
     */
    public static List<DrBudgetDeclareDtlModel> uploadDrBudgetDeclareExcel(String filePath) throws IOException {
        List<DrBudgetDeclareDtlModel> list = new ArrayList<>();
        InputStream is = new FileInputStream(new File(filePath));
        Workbook hssfWorkbook = null;
        try {
            if (filePath.endsWith("xlsx")) {
                hssfWorkbook = new XSSFWorkbook(is);//Excel 2007
            } else if (filePath.endsWith("xls")) {
                hssfWorkbook = new HSSFWorkbook(is);//Excel 2003
            }
            DrBudgetDeclareDtlModel plan = null;
            Integer order = 1;
            // 循环工作表Sheet
            for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
                Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
                if (hssfSheet == null) {
                    continue;
                }
                // 循环行Row
                for (int rowNum = 4; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                    Row hssfRow = hssfSheet.getRow(rowNum);
                    if (hssfRow != null) {
                        plan = new DrBudgetDeclareDtlModel();
                        //生成随机Id
                        plan.setId(IdWorker.getIdStr());
                        plan.setBddNumber(ValidateUtil.isEmpty(hssfRow.getCell(0)) ? "" : hssfRow.getCell(0).toString());
                        plan.setBddName(ValidateUtil.isEmpty(hssfRow.getCell(1)) ? "" : hssfRow.getCell(1).toString());
                        plan.setBddConstructionCost(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(2)) ? "0" : hssfRow.getCell(2).toString()));
                        plan.setBddDeviceCost(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(3)) ? "0" : hssfRow.getCell(3).toString()));
                        plan.setBddInstallCost(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(4)) ? "0" : hssfRow.getCell(4).toString()));
                        plan.setBddOtherCost(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(5)) ? "0" : hssfRow.getCell(5).toString()));
                        plan.setBddTotalCost(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(6)) ? "0" : hssfRow.getCell(6).toString()));
                        plan.setBddUnit(ValidateUtil.isEmpty(hssfRow.getCell(7)) ? "" : hssfRow.getCell(7).toString());
                        plan.setBddCount(new Float(ValidateUtil.isEmpty(hssfRow.getCell(8)) ? "0" : hssfRow.getCell(8).toString()));
                        plan.setBddUnitCost(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(9)) ? "0" : hssfRow.getCell(9).toString()));
                        plan.setBddOrder(order++);
                        list.add(plan);
                    }
                }
            }
        } finally {
            is.close();
            if (hssfWorkbook!=null)
                hssfWorkbook.close();
        }
        return list;
    }


    /**
     * 信概算申报清单excel
     *
     * @param filePath
     * @return
     */
    public static Map<String,Object> uploadNewBudgetDeclareExcel(String filePath) throws IOException {
        Map<String,Object> map=new HashMap<>();
        String result="1";
        String message="";
        List<InvBudgetDeclareDtlModel> list = new ArrayList<>();
        InputStream is = new FileInputStream(new File(filePath));
        Workbook hssfWorkbook = null;
        if (filePath.endsWith("xlsx")) {
            hssfWorkbook = new XSSFWorkbook(is);//Excel 2007
        } else if (filePath.endsWith("xls")) {
            hssfWorkbook = new HSSFWorkbook(is);//Excel 2003
        }
        try {
            InvBudgetDeclareDtlModel plan = null;
            // 循环工作表Sheet
            int sheetNum = hssfWorkbook.getNumberOfSheets();
            for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
                Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
                if (hssfSheet == null) {
                    continue;
                }
                // 循环行Row
                int hssfRowNum = hssfSheet.getLastRowNum();
                for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                    Row hssfRow = hssfSheet.getRow(rowNum);
                    if (hssfRow != null) {
                        plan = new InvBudgetDeclareDtlModel();
                        //生成随机Id
                        plan.setBddId(GenerateUUID.getUUID());
                        plan.setBddNumber(ValidateUtil.isEmpty(hssfRow.getCell(0)) ? "" : hssfRow.getCell(0).toString());
                        //此时记录的是节点编码
//                        plan.setBddParentId(hssfRow.getCell(1).toString());
                        String parentId = ValidateUtil.isEmpty(hssfRow.getCell(1)) ? "" : hssfRow.getCell(1).toString();
                        if (parentId.matches("\\d+\\.0")) {
                            parentId = parentId.substring(0, parentId.length() - 2);
                        }
                        plan.setBddParentId(parentId);
                        plan.setBddCode(ValidateUtil.isEmpty(hssfRow.getCell(2)) ? "" : hssfRow.getCell(2).toString());
                        plan.setBddName(ValidateUtil.isEmpty(hssfRow.getCell(3)) ? "" : hssfRow.getCell(3).toString());
                        plan.setBddUnit(ValidateUtil.isEmpty(hssfRow.getCell(4)) ? "" : hssfRow.getCell(4).toString());
                        plan.setBddCount(new Float(ValidateUtil.isEmpty(hssfRow.getCell(5)) ? "0" : hssfRow.getCell(5).toString()));
                        plan.setBddPrice(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(6)) ? "0" : hssfRow.getCell(6).toString()));
                        plan.setBddTotal(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(7)) ? "0" : hssfRow.getCell(7).toString()).multiply(new BigDecimal(10000)));
                        plan.setBddBuildFee(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(8)) ? "0" : hssfRow.getCell(8).toString()).multiply(new BigDecimal(10000)));
                        plan.setBddInstallFee(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(9)) ? "0" : hssfRow.getCell(9).toString()).multiply(new BigDecimal(10000)));
                        plan.setBddDeviceFee(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(10)) ? "0" : hssfRow.getCell(10).toString()).multiply(new BigDecimal(10000)));
                        plan.setBddOtherFee(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(11)) ? "0" : hssfRow.getCell(11).toString()).multiply(new BigDecimal(10000)));
                        plan.setBddRemark(ValidateUtil.isEmpty(hssfRow.getCell(12)) ? "" : hssfRow.getCell(12).toString());
                        BigDecimal totalAmount=(plan.getBddBuildFee().add(plan.getBddDeviceFee()).add(plan.getBddInstallFee()).add(plan.getBddOtherFee()));
                        if(totalAmount!=plan.getBddTotal())
                        {
                            result="-1";
                            if("".equals(message))
                                message="第"+rowNum+"行数据各细数据累加之和不为投资总额,是否使用系统自动计算或重新导入";
                            plan.setBddTotal(totalAmount);
                            BigDecimal count=new BigDecimal(plan.getBddCount());
                            if(ValidateUtil.isNotNull(count)&&!count.equals(BigDecimal.ZERO)) {
                                BigDecimal Price = totalAmount.divide(count, 2);
                                plan.setBddPrice(Price);
                            }
                        }
                        list.add(plan);
                    }
                }
            }

            List<InvBudgetDeclareDtlModel> datas = new ArrayList<>();
            //将节点编码替换成对应ID
            if (!CollectionUtils.isEmpty(list)) {
                List<InvBudgetDeclareDtlModel> headers = list.stream().filter(n -> !n.getBddParentId().contains("-")).collect(Collectors.toList());
                headers.forEach(header -> {
                    List<InvBudgetDeclareDtlModel> child = new ArrayList<>();
                    String nodeCode = header.getBddParentId();
                    header.setBddParentId("0");
                    List<InvBudgetDeclareDtlModel> childs = list.stream().filter(n -> n.getBddParentId().contains("-") && n.getBddParentId().substring(0, n.getBddParentId().lastIndexOf('-')).equals(nodeCode)).collect(Collectors.toList());
                    getBudgetDeclareDtlChilds(child, childs, list, header);
                    header.setChildNodes(child);
                    if(ValidateUtil.isNotEmpty(child)) {
                        header.setBddTotal(child.stream().map(InvBudgetDeclareDtlModel::getBddTotal).reduce(BigDecimal.ZERO, NumberUtil::add));
                        header.setBddBuildFee(child.stream().map(InvBudgetDeclareDtlModel::getBddBuildFee).reduce(BigDecimal.ZERO, NumberUtil::add));
                        header.setBddInstallFee(child.stream().map(InvBudgetDeclareDtlModel::getBddInstallFee).reduce(BigDecimal.ZERO, NumberUtil::add));
                        header.setBddDeviceFee(child.stream().map(InvBudgetDeclareDtlModel::getBddDeviceFee).reduce(BigDecimal.ZERO, NumberUtil::add));
                        header.setBddOtherFee(child.stream().map(InvBudgetDeclareDtlModel::getBddOtherFee).reduce(BigDecimal.ZERO, NumberUtil::add));
                    }
                    datas.add(header);
                });
            }
            map.put("result",result);
            map.put("message",message);
            map.put("datas",datas);
            return map;
        } finally {
            is.close();
            hssfWorkbook.close();
        }
    }


    public static List<InvFinalAccountsApplyDtlDto> uploadFinalAccountsApplyExcel(String filePath) throws IOException {
        List<InvFinalAccountsApplyDtlDto> list = new ArrayList<>();
        InputStream is = new FileInputStream(new File(filePath));
        Workbook hssfWorkbook = null;
        if (filePath.endsWith("xlsx")) {
            hssfWorkbook = new XSSFWorkbook(is);//Excel 2007
        } else if (filePath.endsWith("xls")) {
            hssfWorkbook = new HSSFWorkbook(is);//Excel 2003
        }
        try {
            InvFinalAccountsApplyDtlDto plan = null;
            // 循环工作表Sheet
            for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
                Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
                if (hssfSheet == null) {
                    continue;
                }
                // 循环行Row
                for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                    Row hssfRow = hssfSheet.getRow(rowNum);
                    if (hssfRow != null) {
                        plan = new InvFinalAccountsApplyDtlDto();
                        //生成随机Id
                        plan.setFadId(GenerateUUID.getUUID());
                        plan.setFadNumber(ValidateUtil.isEmpty(hssfRow.getCell(0)) ? "" : hssfRow.getCell(0).toString());
                        //此时记录的是节点编码
                        plan.setFadParentId(hssfRow.getCell(1).toString());
                        plan.setFadCode(ValidateUtil.isEmpty(hssfRow.getCell(2)) ? "" : hssfRow.getCell(2).toString());
                        plan.setFadName(ValidateUtil.isEmpty(hssfRow.getCell(3)) ? "" : hssfRow.getCell(3).toString());
                        plan.setFadUnit(ValidateUtil.isEmpty(hssfRow.getCell(4)) ? "" : hssfRow.getCell(4).toString());
                        plan.setFadCount(new Float(ValidateUtil.isEmpty(hssfRow.getCell(5)) ? "0" : hssfRow.getCell(5).toString()));
                        plan.setFadPrice(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(6)) ? "0" : hssfRow.getCell(6).toString()));
                        plan.setFadTotal(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(7)) ? "0" : hssfRow.getCell(7).toString()).multiply(new BigDecimal(10000)));
                        plan.setFadRemark(ValidateUtil.isEmpty(hssfRow.getCell(8)) ? "" : hssfRow.getCell(8).toString());
                        list.add(plan);
                    }
                }
            }
            //将节点编码替换成对应ID
            if (!CollectionUtils.isEmpty(list)) {
                List<InvFinalAccountsApplyDtlDto> datas = new ArrayList<>();
                List<InvFinalAccountsApplyDtlDto> headers = list.stream().filter(n -> !n.getFadParentId().contains("-")).collect(Collectors.toList());
                headers.forEach(header -> {
                    List<InvFinalAccountsApplyDtlDto> child = new ArrayList<>();
                    String nodeCode = header.getFadParentId();
                    header.setFadParentId("0");
                    List<InvFinalAccountsApplyDtlDto> childs = list.stream().filter(n -> n.getFadParentId().contains("-") && n.getFadParentId().substring(0, n.getFadParentId().lastIndexOf('-')).equals(nodeCode)).collect(Collectors.toList());
                    getFinalAccountsApplyDtlChilds(child, childs, list, header);
                    header.setChildNodes(child);
                    datas.add(header);
                });
                return datas;
            }
            return list;
        } finally {
            is.close();
            hssfWorkbook.close();
        }
    }


    public static List<InvProjectPlan> uploadInvProPlanOneExcel(String filePath) throws IOException {
        List<InvProjectPlan> list = new ArrayList<>();
        InputStream is = new FileInputStream(new File(filePath));
        Workbook hssfWorkbook = null;
        if (filePath.endsWith("xlsx")) {
            hssfWorkbook = new XSSFWorkbook(is);//Excel 2007
        } else if (filePath.endsWith("xls")) {
            hssfWorkbook = new HSSFWorkbook(is);//Excel 2003
        }
        try {
            InvProjectPlan plan = null;
            // 循环工作表Sheet
            for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
                Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
                if (hssfSheet == null) {
                    continue;
                }
                // 循环行Row
                for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                    Row hssfRow = hssfSheet.getRow(rowNum);
                    if (hssfRow != null) {
                        plan = new InvProjectPlan();
                        //生成随机Id
                        plan.setIppId(GenerateUUID.getUUID());
                        //此时记录的是项目编码
                        plan.setPId(StringFormat(hssfRow.getCell(2)));
                        plan.setIppYear(StringFormat(hssfRow.getCell(3)));
                        plan.setIppDocNumber(ValidateUtil.isEmpty(hssfRow.getCell(4)) ? "" : StringFormat(hssfRow.getCell(4)));
                        plan.setIppReleaseFunds(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(5)) ? "0" : hssfRow.getCell(5).toString()));
                        list.add(plan);
                    }
                }
            }
            return list;
        }
        finally {
            is.close();
            hssfWorkbook.close();
        }
    }


    /**
     * 工程周报
     *
     * @param filePath
     * @param type
     * @return
     */
    public static List<OaProjectReportModel> uploadOaProReportExcelWeek(String filePath, Integer type) throws IOException {
        List<OaProjectReportModel> list = new ArrayList<>();
        InputStream is = new FileInputStream(new File(filePath));
        Workbook hssfWorkbook = null;
        if (filePath.endsWith("xlsx")) {
            hssfWorkbook = new XSSFWorkbook(is);//Excel 2007
        } else if (filePath.endsWith("xls")) {
            hssfWorkbook = new HSSFWorkbook(is);//Excel 2003
        }
        try {
            OaProjectReportModel model = null;
            // 循环工作表Sheet
            for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
                Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
                if (hssfSheet == null) {
                    continue;
                }
                // 循环行Row
                for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                    Row hssfRow = hssfSheet.getRow(rowNum);
                    if (hssfRow != null) {
                        int index = 0;
                        model = new OaProjectReportModel();
                        //此时记录的是项目编码
                        model.setpId(StringFormat(hssfRow.getCell(1)).trim());
                        model.setPrConstruction(ValidateUtil.isEmpty(hssfRow.getCell(2)) ? "" : StringFormat(hssfRow.getCell(2)));
                        model.setPrSafeProduction(ValidateUtil.isEmpty(hssfRow.getCell(3)) ? "" : StringFormat(hssfRow.getCell(3)));
                        model.setPrInspection(ValidateUtil.isEmpty(hssfRow.getCell(4)) ? "" : StringFormat(hssfRow.getCell(4)));
                        index = 5;
                        model.setPrProUnder(ValidateUtil.isEmpty(hssfRow.getCell(index)) ? "" : StringFormat(hssfRow.getCell(index)));
                        model.setPrProcedures(ValidateUtil.isEmpty(hssfRow.getCell(index + 1)) ? "" : StringFormat(hssfRow.getCell(index + 1)));
                        model.setPrImgProgress(ValidateUtil.isEmpty(hssfRow.getCell(index + 2)) ? "" : StringFormat(hssfRow.getCell(index + 2)));
                        model.setPrCompleteInv(MathUtil.DecimalMultiplicationInt(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(index + 3)) ? "0" : hssfRow.getCell(index + 3).toString()), 10000));
                        model.setPrYearInv(MathUtil.DecimalMultiplicationInt(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(index + 4)) ? "0" : hssfRow.getCell(index + 4).toString()), 10000));
                        model.setPrInvRate(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(index + 5)) ? "0" : hssfRow.getCell(index + 5).toString()));
                        model.setPrMonthInv(MathUtil.DecimalMultiplicationInt(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(index + 6)) ? "0" : hssfRow.getCell(index + 6).toString()), 10000));
                        model.setPrMonthInvRate(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(index + 7)) ? "0" : hssfRow.getCell(index + 7).toString()));
                        if (!ValidateUtil.isEmpty(hssfRow.getCell(index + 8))) {
                            if (StringFormat(hssfRow.getCell(index + 8)).equals("是")) model.setPrWhetherLag(true);
                            else model.setPrWhetherLag(false);
                        }
                        model.setPrReasonLag(ValidateUtil.isEmpty(hssfRow.getCell(index + 9)) ? "" : StringFormat(hssfRow.getCell(index + 9)));
                        model.setPrSuggest(ValidateUtil.isEmpty(hssfRow.getCell(index + 10)) ? "" : StringFormat(hssfRow.getCell(index + 10)));
                        list.add(model);
                    }
                }
            }
            return list;
        } finally {
            is.close();
            hssfWorkbook.close();
        }
    }

    /**
     * 工程月报
     *
     * @param filePath
     * @param urbanPlan 是否解析城建计划
     * @return
     */
    public static BaseResponse uploadOaProReportExcelMonth(String filePath, boolean urbanPlan, List<String> cons, List<String> under) throws IOException {
        List<OaProjectReportModel> list = new ArrayList<>();
        InputStream is = new FileInputStream(new File(filePath));
        Workbook hssfWorkbook = null;
        if (filePath.endsWith("xlsx")) {
            hssfWorkbook = new XSSFWorkbook(is);//Excel 2007
        } else if (filePath.endsWith("xls")) {
            hssfWorkbook = new HSSFWorkbook(is);//Excel 2003
        }
        try {
            OaProjectReportModel model = null;
            // 循环工作表Sheet
            int quarter = DateUtil.getCurrentQuarter();
            for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
                Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
                if (hssfSheet == null) {
                    continue;
                }
                // 循环行Row
                for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                    Row hssfRow = hssfSheet.getRow(rowNum);
                    if (hssfRow != null) {
                        Integer index = 0;
                        try {
                            model = new OaProjectReportModel();
                            //此时记录的是项目编码
                            model.setpId(StringFormat(hssfRow.getCell(index += 1)));
                            if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            model.setpId(model.getpId().trim());
                            model.setpCompetentDepartmentName(StringFormat(hssfRow.getCell(index += 1)));
                            if (urbanPlan) {
                                model.setPrStartWork(StringFormat(hssfRow.getCell(index += 1)));
                                model.setPrInitiationPlan(getInt(hssfRow.getCell(index += 1)));
                                if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                                model.setPrResponsibleDep(getStr(hssfRow.getCell(index += 1)));
                                if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                                model.setPrResponsibleLeader(getStr(hssfRow.getCell(index += 1)));
                                if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                                model.setPrProjectInvestment(MathUtil.DecimalMultiplicationInt(getDec(hssfRow.getCell(index += 1)), 10000));
                                if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                                model.setPrInvestmentType(getStr(hssfRow.getCell(index += 1)));
                                if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                                model.setPrIsBudget(getStr(hssfRow.getCell(index += 1)));
                                if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                                model.setPrBudgetInvestment(MathUtil.DecimalMultiplicationInt(getDec(hssfRow.getCell(index += 1)), 10000));
                                if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                                model.setPrLastYearRate(MathUtil.DecimalMultiplicationInt(getDec(hssfRow.getCell(index += 1)), 10000));
                                if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                                model.setPrIsStartWork(getStr(hssfRow.getCell(index += 1)));
                                if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                                model.setPrIsFinish(getStr(hssfRow.getCell(index += 1)));
                                if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                                model.setPrMainContent(getStr(hssfRow.getCell(index += 1)));
                                if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                                model.setPrAdjustmentType(getStr(hssfRow.getCell(index += 1)));
                                if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                                model.setPrAdjustmentTime(TimeFormat(hssfRow.getCell(index += 1), "yyyy-MM-dd"));
                                if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            }
                            model.setPrDoneLastYear(MathUtil.DecimalMultiplicationInt(new BigDecimal(cellIsEmpty(hssfRow.getCell(index += 1)) ? "0" : hssfRow.getCell(index).toString()), 10000));
                            if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            model.setPrPlannedInvestment(MathUtil.DecimalMultiplicationInt(new BigDecimal(cellIsEmpty(hssfRow.getCell(index += 1)) ? "0" : hssfRow.getCell(index).toString()), 10000));
                            if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            model.setPrTImgProgress(cellIsEmpty(hssfRow.getCell(index += 1)) ? "" : StringFormat(hssfRow.getCell(index)));
                            if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            //region 各季度形象进度 计完成当年投资额
                            if (!urbanPlan) {
                                model.setPrProgressQuarterOne(cellIsEmpty(hssfRow.getCell(index += 1)) ? "" : StringFormat(hssfRow.getCell(index)));
                                if (checkQuarter(hssfRow, index, quarter, 1)) return returnBaseRes(rowNum, index);
                                model.setPrInvQuarterOne(MathUtil.DecimalMultiplicationInt(new BigDecimal(cellIsEmpty(hssfRow.getCell(index += 1)) ? "0" : hssfRow.getCell(index).toString()), 10000));
                                if (checkQuarter(hssfRow, index, quarter, 1)) return returnBaseRes(rowNum, index);
                                model.setPrProgressQuarterTwo(cellIsEmpty(hssfRow.getCell(index += 1)) ? "" : StringFormat(hssfRow.getCell(index)));
                                if (checkQuarter(hssfRow, index, quarter, 2)) return returnBaseRes(rowNum, index);
                                model.setPrInvQuarterTwo(MathUtil.DecimalMultiplicationInt(new BigDecimal(cellIsEmpty(hssfRow.getCell(index += 1)) ? "0" : hssfRow.getCell(index).toString()), 10000));
                                if (checkQuarter(hssfRow, index, quarter, 2)) return returnBaseRes(rowNum, index);
                                model.setPrProgressQuarterThree(cellIsEmpty(hssfRow.getCell(index += 1)) ? "" : StringFormat(hssfRow.getCell(index)));
                                if (checkQuarter(hssfRow, index, quarter, 3)) return returnBaseRes(rowNum, index);
                                model.setPrInvQuarterThree(MathUtil.DecimalMultiplicationInt(new BigDecimal(cellIsEmpty(hssfRow.getCell(index += 1)) ? "0" : hssfRow.getCell(index).toString()), 10000));
                                if (checkQuarter(hssfRow, index, quarter, 3)) return returnBaseRes(rowNum, index);
                                model.setPrProgressQuarterFour(cellIsEmpty(hssfRow.getCell(index += 1)) ? "" : StringFormat(hssfRow.getCell(index)));
                                if (checkQuarter(hssfRow, index, quarter, 4)) return returnBaseRes(rowNum, index);
                                model.setPrInvQuarterFour(MathUtil.DecimalMultiplicationInt(new BigDecimal(cellIsEmpty(hssfRow.getCell(index += 1)) ? "0" : hssfRow.getCell(index).toString()), 10000));
                                if (checkQuarter(hssfRow, index, quarter, 4)) return returnBaseRes(rowNum, index);
                            }
                            //endregion
                            //region 城建计划
                            if (urbanPlan) {
                                model.setPrUrbanPlan(getStr(hssfRow.getCell(index += 1)));
                                checkDictionary(under, model.getPrUrbanPlan(), rowNum, index, "JPMsoft_UNDER");
                                model.setPrInvPlan(getStr(hssfRow.getCell(index += 1)));
                                model.setPrSocialProject(getStr(hssfRow.getCell(index += 1)));
                                model.setPrPractical(getStr(hssfRow.getCell(index += 1)));
                                model.setPrRefine(getStr(hssfRow.getCell(index += 1)));
                                model.setPrKeyWork(getStr(hssfRow.getCell(index += 1)));
                                model.setPrKeyTask(getStr(hssfRow.getCell(index += 1)));
                                model.setPrEconomicDeve(getStr(hssfRow.getCell(index += 1)));
                                model.setPrBrokenRoad(getStr(hssfRow.getCell(index += 1)));
                                model.setPrLivableCity(getStr(hssfRow.getCell(index += 1)));
                                model.setPrUrbanAction(getStr(hssfRow.getCell(index += 1)));
                                model.setPrRefuseClass(getStr(hssfRow.getCell(index += 1)));
                                model.setPrParking(getStr(hssfRow.getCell(index += 1)));
                                model.setPrDistrictConst(getStr(hssfRow.getCell(index += 1)));
                                model.setPrRemark(getStr(hssfRow.getCell(index += 1)));
                                model.setPrDutyMain(getStr(hssfRow.getCell(index += 1)));
                            }
                            //endregion
                            model.setPrProUnder(cellIsEmpty(hssfRow.getCell(index += 1)) ? "" : StringFormat(hssfRow.getCell(index)));
                            if (urbanPlan) {
                                checkDictionary(cons, model.getPrProUnder(), rowNum, index, "JPMsoft_PRCONSSTATE");
                            } else {
                                if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            }
                            model.setPrProcedures(cellIsEmpty(hssfRow.getCell(index += 1)) ? "" : StringFormat(hssfRow.getCell(index)));
                            if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            if (urbanPlan)
                                model.setPrNumberPresent(cellIsEmpty(hssfRow.getCell(index += 1)) ? 0 : Integer.parseInt(StringFormat(hssfRow.getCell(index))));
                            model.setPrImgProgress(cellIsEmpty(hssfRow.getCell(index += 1)) ? "" : StringFormat(hssfRow.getCell(index)));
                            if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            model.setPrCompleteInv(MathUtil.DecimalMultiplicationInt(getDec(hssfRow.getCell(index += 1)), 10000));
                            if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            model.setPrYearInv(MathUtil.DecimalMultiplicationInt(getDec(hssfRow.getCell(index += 1)), 10000));
                            if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            model.setPrInvRate(getDec(hssfRow.getCell(index += 1)));
                            if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            model.setPrMonthInv(MathUtil.DecimalMultiplicationInt(getDec(hssfRow.getCell(index += 1)), 10000));
                            if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            model.setPrMonthInvRate(getDec(hssfRow.getCell(index += 1)));
                            if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            index += 1;
                            if (!cellIsEmpty(hssfRow.getCell(index))) {
                                if (StringFormat(hssfRow.getCell(index)).equals("是")) model.setPrWhetherLag(true);
                                else model.setPrWhetherLag(false);
                            }
                            model.setPrReasonLag(cellIsEmpty(hssfRow.getCell(index += 1)) ? "" : StringFormat(hssfRow.getCell(index)));
                            if (model.getPrWhetherLag() == true && cellIsEmpty(hssfRow.getCell(index)))
                                return new BaseResponse(MessageFormat.format(FileMsg.cellIsEmpty, rowNum, numCovertLetter(index + 1)));
                            model.setPrSuggest(cellIsEmpty(hssfRow.getCell(index += 1)) ? "" : StringFormat(hssfRow.getCell(index)));
                            if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            list.add(model);
                        } catch (Exception ex) {
                            is.close();
                            hssfWorkbook.close();
                            return BaseResponse.fail(MessageFormat.format(FileMsg.cellIsFail, rowNum, numCovertLetter(index + 1)));
                        }
                    }
                }
            }
            return BaseResponse.ok(list);
        } catch (IOException ex) {
            ex.printStackTrace();
            log.error(ex.getMessage(),ex);
            return BaseResponse.fail(FileMsg.fileParsingFail);
        }finally {
            is.close();
            hssfWorkbook.close();
        }
    }

    /**
     * 专家库
     *
     * @param filePath
     * @return
     */
    public static BaseResponse uploadSecExpertExcel(String filePath) throws IOException {
        List<SecExpertModel> list = new ArrayList<>();
        InputStream is = new FileInputStream(new File(filePath));
        Workbook hssfWorkbook = null;
        if (filePath.endsWith("xlsx")) {
            hssfWorkbook = new XSSFWorkbook(is);//Excel 2007
        } else if (filePath.endsWith("xls")) {
            hssfWorkbook = new HSSFWorkbook(is);//Excel 2003
        }
        try {
            SecExpertModel model = null;
            // 循环工作表Sheet
            int quarter = DateUtil.getCurrentQuarter();
            for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
                Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
                if (hssfSheet == null) {
                    continue;
                }
                // 循环行Row
                for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                    Row hssfRow = hssfSheet.getRow(rowNum);
                    if (hssfRow != null) {
                        Integer index = 0;
                        try {
                            model = new SecExpertModel();
                            //姓名
                            model.setExName(StringFormat(hssfRow.getCell(index += 1)));
                            if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            //性别
                            model.setExSex(StringFormat(hssfRow.getCell(index += 1)));
                            if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            //出生日期
                            model.setExBirthday(TimeFormat(hssfRow.getCell(index += 1), "yyyy-MM-dd"));
                            if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            //政治面貌
                            model.setExPolitics(StringFormat(hssfRow.getCell(index += 1)));
                            if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            //民族
                            model.setExFamily(StringFormat(hssfRow.getCell(index += 1)));
                            if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            //专家类型
                            model.setExCategory(StringFormat(hssfRow.getCell(index += 1)));
                            if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            //专家类别
                            model.setExType(StringFormat(hssfRow.getCell(index += 1)));
                            if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            //学历学位
                            model.setExEducation(StringFormat(hssfRow.getCell(index += 1)));
                            if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            //联系方式
                            model.setExCellphoneNumber1(StringFormat(hssfRow.getCell(index += 1)));
                            if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            //身份证号
                            model.setExIdNumber(IntFormat(hssfRow.getCell(index += 1)));
                            if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            //单位地址
                            model.setExWorkAddress(StringFormat(hssfRow.getCell(index += 1)));
                            if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            //现任职务
                            model.setExPost(StringFormat(hssfRow.getCell(index += 1)));
                            if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            //从事专业
                            model.setExProfessionalCategory(StringFormat(hssfRow.getCell(index += 1)));
                            if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            //从事专业起始年月
                            model.setExStartDate(TimeFormat(hssfRow.getCell(index += 1), "yyyy-MM-dd"));
                            if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            //专业技术职称
                            model.setExTitle(StringFormat(hssfRow.getCell(index += 1)));
                            //获得技术职称时间
                            model.setExTilteDate(TimeFormat(hssfRow.getCell(index += 1), "yyyy-MM-dd"));
                            //职称证书编号
                            model.setExTitleNumber(StringFormat(hssfRow.getCell(index += 1)));
                            //职业资格
                            model.setExPracticeQualification(StringFormat(hssfRow.getCell(index += 1)));
                            //取得执业资格时间
                            model.setExQualification(StringFormat(hssfRow.getCell(index += 1)));
                            //执业证书编号
                            model.setExPracticeCode(StringFormat(hssfRow.getCell(index += 1)));
                            //主要工作经历
                            model.setExMainWork(StringFormat(hssfRow.getCell(index += 1)));
                            if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            //专业学术成果
                            model.setExPersonalResult(StringFormat(hssfRow.getCell(index += 1)));
                            if (cellIsEmpty(hssfRow.getCell(index))) return returnBaseRes(rowNum, index);
                            list.add(model);
                        } catch (Exception ex) {
                            is.close();
                            hssfWorkbook.close();
                            return new BaseResponse(MessageFormat.format(FileMsg.cellIsFail, rowNum, numCovertLetter(index + 1)));
                        }
                    }
                }
            }
            return new BaseResponse(true, "", list);
        } catch (IOException ex) {
            ex.printStackTrace();
            log.error(ex.getMessage(),ex);
            return BaseResponse.fail(FileMsg.fileParsingFail);
        }finally {
            is.close();
            hssfWorkbook.close();
        }
    }

    /**
     * 导入资金计划下达 光明
     *
     * @param filePath
     * @return
     * @throws Exception
     */
    public static List<GmInvProPlanDto> importGmInvProPlan(String filePath) throws Exception {
        List<GmInvProPlanDto> list = new ArrayList<>();
        InputStream is = new FileInputStream(new File(filePath));
        Workbook hssfWorkbook = null;
        if (filePath.endsWith("xlsx")) {
            hssfWorkbook = new XSSFWorkbook(is);//Excel 2007
        } else if (filePath.endsWith("xls")) {
            hssfWorkbook = new HSSFWorkbook(is);//Excel 2003
        }
        GmInvProPlanDto model = null;
        // 循环工作表Sheet
        int quarter = DateUtil.getCurrentQuarter();
        try {
            for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
                Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
                if (hssfSheet == null) {
                    continue;
                }
                // 循环行Row
                for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                    Row hssfRow = hssfSheet.getRow(rowNum);
                    if (hssfRow != null) {
                        Integer index = 0;
                        model = new GmInvProPlanDto();
                        //项目名称
                        model.setPName(StringFormat(hssfRow.getCell(index += 1)));
                        if (cellIsEmpty(hssfRow.getCell(index))) throw new JPMException(cellEmptyMsg(rowNum, index));
                        //项目编码
                        model.setPCode(StringFormat(hssfRow.getCell(index += 1)));
                        //预算项目名称
                        model.setIppBudgetItemName(StringFormat(hssfRow.getCell(index += 1)));
                        if (cellIsEmpty(hssfRow.getCell(index))) throw new JPMException(cellEmptyMsg(rowNum, index));
                        //国家发改委项目编码
                        model.setIppNdrcCode(StringFormat(hssfRow.getCell(index += 1)));
                        if (cellIsEmpty(hssfRow.getCell(index))) throw new JPMException(cellEmptyMsg(rowNum, index));
                        //年度
                        model.setIppYear(StringFormat(hssfRow.getCell(index += 1)));
                        if (cellIsEmpty(hssfRow.getCell(index))) throw new JPMException(cellEmptyMsg(rowNum, index));
                        //本次计划下达文号
                        model.setIppDocNumber(StringFormat(hssfRow.getCell(index += 1)));
                        if (cellIsEmpty(hssfRow.getCell(index))) throw new JPMException(cellEmptyMsg(rowNum, index));
                        //原计划下达文号
                        model.setIppPlanDocNo(StringFormat(hssfRow.getCell(index += 1)));
                        //资金来源
                        model.setIppSourceFunds(StringFormat(hssfRow.getCell(index += 1)));
                        if (cellIsEmpty(hssfRow.getCell(index))) throw new JPMException(cellEmptyMsg(rowNum, index));
                        //下达类型
                        model.setIppReleaseType(StringFormat(hssfRow.getCell(index += 1)));
                        if (cellIsEmpty(hssfRow.getCell(index))) throw new JPMException(cellEmptyMsg(rowNum, index));
                        //本次下达金额
                        model.setIppReleaseFunds(StringFormat(hssfRow.getCell(index += 1)));
                        if (cellIsEmpty(hssfRow.getCell(index))) throw new JPMException(cellEmptyMsg(rowNum, index));
                        //备注
                        model.setIppRemarks(StringFormat(hssfRow.getCell(index += 1)));
                        list.add(model);
                    }
                }
            }
        } finally {
            is.close();
            hssfWorkbook.close();
        }
        return list;
    }
    /**
     * 导入工程量清单
     *
     * @param filePath 文件路径
     * @param read     数据读取方式 index:列顺序 name:列名称
     */
    public static List<ConBoqDtlDto> importConBopDtl(String filePath, String read) throws Exception {
        List<ConBoqDtlDto> list = new ArrayList<>();
        InputStream is = new FileInputStream(new File(filePath));
        Workbook hssfWorkbook = null;
        if (filePath.endsWith("xlsx")) {
            hssfWorkbook = new XSSFWorkbook(is);//Excel 2007
        } else if (filePath.endsWith("xls")) {
            hssfWorkbook = new HSSFWorkbook(is);//Excel 2003
        }
        try {

        ConBoqDtlDto data = null;
        // 循环工作表Sheet
        int quarter = DateUtil.getCurrentQuarter();
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            List<String> tbTops = new ArrayList<>();
            int parentIndex = 0;
            // 循环行Row
            for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                Row hssfRow = hssfSheet.getRow(rowNum);
                Integer index = 0;
                if (rowNum == 0) { //读取表头
                    short maxColIx = hssfRow.getLastCellNum();
                    for (short colIx = 0; colIx < maxColIx; colIx++) {
                        Cell cell = hssfRow.getCell(colIx);
                        if (cell == null) continue;
                        tbTops.add(StringFormat(cell));
                    }
                }

                if (hssfRow != null && rowNum > 0) {
                    if (read.equalsIgnoreCase("index"))  //以列顺序方式读取数据
                    {
                        data = new ConBoqDtlDto();
                        //清单类别
                        data.setBoqdType(StringFormat(hssfRow.getCell(index)));
                        //上级编码
                        data.setBoqdParentCode(getRealStringValue(hssfRow.getCell(index += 1)));
                        if (!containsNum(data.getBoqdParentCode())) {
                            parentIndex += 1;
                            data.setBoqdParentCode(parentIndex + "");
                        } else data.setBoqdParentCode(parentIndex + "." + data.getBoqdParentCode());
                        //项目编码
                        data.setBoqdCode(StringFormat(hssfRow.getCell(index += 1)));
//                        if (cellIsEmpty(hssfRow.getCell(index))) throw new JPMException(cellEmptyMsg(rowNum, index));
                        //项目名称
                        data.setBoqdName(StringFormat(hssfRow.getCell(index += 1)));
//                        if (cellIsEmpty(hssfRow.getCell(index))) throw new JPMException(cellEmptyMsg(rowNum, index));
                        //项目特征
                        data.setBoqdFeature(StringFormat(hssfRow.getCell(index += 1)));
//                        //工程内容
//                        data.setBoqdContent(StringFormat(hssfRow.getCell(index += 1)));
                        //计量单位
                        data.setBoqdUnit(StringFormat(hssfRow.getCell(index += 1)));
                        //工程量
                        data.setBoqdAmount(FloatFormat(hssfRow.getCell(index += 1)));
                        //综合单价
                        data.setBoqdPrice(new BigDecimal(cellIsEmpty(hssfRow.getCell(index += 1)) ? "0" : hssfRow.getCell(index).toString()));
                        //总价
                        data.setBoqdTotalPrice(new BigDecimal(cellIsEmpty(hssfRow.getCell(index += 1)) ? "0" : hssfRow.getCell(index).toString()));
                        //措施费比率
                        data.setBoqdRate(FloatFormat(hssfRow.getCell(index += 1)));
                        //规费比率
                        data.setBoqdFeesRatio(FloatFormat(hssfRow.getCell(index += 1)));
                        //其他项目比率
                        data.setBoqdOtherRatio(FloatFormat(hssfRow.getCell(index += 1)));
                        //税金比率
                        data.setBoqdTaxesRatio(FloatFormat(hssfRow.getCell(index += 1)));
                        //计算公式
                        data.setBoqdFormula(StringFormat(hssfRow.getCell(index += 1)));
                        //备注
                        data.setBoqdRemark(StringFormat(hssfRow.getCell(index += 1)));
                        //所属概算
                        data.setBddName(StringFormat(hssfRow.getCell(index += 1)));
                        data.setBoqdName(ValidateUtil.isEmpty(data.getBoqdName()) ? data.getBoqdCode() : data.getBoqdName());
                        list.add(data);
                    } else if (read.equalsIgnoreCase("name")) //以列名方式读取数据
                    {
                        data = new ConBoqDtlDto();
                        //项目编码
//                        if (cellIsEmpty(getCellByIndex(hssfRow, tbTops.indexOf("项目编码"))))
//                            throw new JPMException("项目编码列不能为空！");
                        data.setBoqdCode(StringFormat(getCellByIndex(hssfRow, tbTops.indexOf("项目编码"))));
                        //上级编码
                        data.setBoqdParentCode(getRealStringValue(getCellByIndex(hssfRow, tbTops.indexOf("序号"))));
                        if (!containsNum(data.getBoqdParentCode())) {
                            parentIndex += 1;
                            data.setBoqdParentCode(parentIndex + "");
                        } else data.setBoqdParentCode(parentIndex + "." + data.getBoqdParentCode());
                        //清单类别
                        data.setBoqdType(StringFormat(getCellByIndex(hssfRow, tbTops.indexOf("清单类别"))));
                        //项目名称
//                        if (cellIsEmpty(getCellByIndex(hssfRow, tbTops.indexOf("项目名称"))))
//                            throw new JPMException("项目名称列不能为空！");
                        data.setBoqdName(StringFormat(getCellByIndex(hssfRow, tbTops.indexOf("项目名称"))));
                        //项目特征
                        data.setBoqdFeature(StringFormat(getCellByIndex(hssfRow, tbTops.indexOf("项目特征"))));
                        //工程内容
                        data.setBoqdContent(StringFormat(getCellByIndex(hssfRow, tbTops.indexOf("工程内容"))));
                        //计量单位
                        data.setBoqdUnit(StringFormat(getCellByIndex(hssfRow, tbTops.indexOf("计量单位"))));
                        //工程量
                        data.setBoqdAmount(FloatFormat(getCellByIndex(hssfRow, tbTops.indexOf("工程量"))));
                        //综合单价
                        data.setBoqdPrice(new BigDecimal(cellIsEmpty(getCellByIndex(hssfRow, tbTops.indexOf("综合单价(元)"))) ? "0" : getCellByIndex(hssfRow, tbTops.indexOf("综合单价(元)")).toString()));
                        //总价
                        data.setBoqdTotalPrice(new BigDecimal(cellIsEmpty(getCellByIndex(hssfRow, tbTops.indexOf("总价(元)"))) ? "0" : getCellByIndex(hssfRow, tbTops.indexOf("总价(元)")).toString()));
                        //措施费比率
                        data.setBoqdRate(FloatFormat(getCellByIndex(hssfRow, tbTops.indexOf("措施费率(%)"))));
                        //规费比率
                        data.setBoqdFeesRatio(FloatFormat(getCellByIndex(hssfRow, tbTops.indexOf("规费费率(%)"))));
                        //其他项目比率
                        data.setBoqdOtherRatio(FloatFormat(getCellByIndex(hssfRow, tbTops.indexOf("其他项目费率(%)"))));
                        //税金比率
                        data.setBoqdTaxesRatio(FloatFormat(getCellByIndex(hssfRow, tbTops.indexOf("税金费率(%)"))));
                        //计算公式
                        data.setBoqdFormula(StringFormat(getCellByIndex(hssfRow, tbTops.indexOf("计算公式"))));
                        //备注
                        data.setBoqdRemark(StringFormat(getCellByIndex(hssfRow, tbTops.indexOf("备注"))));
                        //所属概算
                        data.setBddName(StringFormat(getCellByIndex(hssfRow, tbTops.indexOf("所属概算清单"))));
                        data.setBoqdName(ValidateUtil.isEmpty(data.getBoqdName()) ? data.getBoqdCode() : data.getBoqdName());
                        list.add(data);
                    }
                }
            }
        }
        }finally {
            is.close();
            hssfWorkbook.close();
        }
        return list;
    }

    private static Cell getCellByIndex(Row hssfRow, int index) {
        if (index < 0) return null;
        return hssfRow.getCell(index);
    }

    public static List<ConMeasureRecordDto> importConMeasureRecord(String filePath) throws Exception {
        List<ConMeasureRecordDto> list = new ArrayList<>();
        InputStream is = new FileInputStream(new File(filePath));
        Workbook hssfWorkbook = null;
        if (filePath.endsWith("xlsx")) {
            hssfWorkbook = new XSSFWorkbook(is);//Excel 2007
        } else if (filePath.endsWith("xls")) {
            hssfWorkbook = new HSSFWorkbook(is);//Excel 2003
        }
        try {
        ConMeasureRecordDto data = null;
        // 循环工作表Sheet
        int quarter = DateUtil.getCurrentQuarter();
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // 循环行Row
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                Row hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                    Integer index = 0;
                    try {
                        data = new ConMeasureRecordDto();
                        //计量单编号
                        data.setMearCode(StringFormat(hssfRow.getCell(index)));
                        if (cellIsEmpty(hssfRow.getCell(index))) throw new JPMException(cellEmptyMsg(rowNum, index));
                        //清单号
                        data.setMearBoqdCode(StringFormat(hssfRow.getCell(index += 1)));
                        if (cellIsEmpty(hssfRow.getCell(index))) throw new JPMException(cellEmptyMsg(rowNum, index));
                        //图号及部位
                        data.setMearPosition(StringFormat(hssfRow.getCell(index += 1)));
                        if (cellIsEmpty(hssfRow.getCell(index))) throw new JPMException(cellEmptyMsg(rowNum, index));
                        //本期申报量
                        String mearDeclareAmount = StringFormat(hssfRow.getCell(index += 1));
                        data.setMearDeclareAmount(ValidateUtil.isEmpty(mearDeclareAmount) ? 0F : Float.valueOf(mearDeclareAmount));
                        if (cellIsEmpty(hssfRow.getCell(index))) throw new JPMException(cellEmptyMsg(rowNum, index));
                        //计价要素
                        data.setMearPriceElement(StringFormat(hssfRow.getCell(index += 1)));
                        if (cellIsEmpty(hssfRow.getCell(index))) throw new JPMException(cellEmptyMsg(rowNum, index));
                        //草图、几何尺寸及计算式
                        data.setMearRemark(StringFormat(hssfRow.getCell(index += 1)));
                        //计量员
                        data.setMearGauger(StringFormat(hssfRow.getCell(index += 1)));
                        if (cellIsEmpty(hssfRow.getCell(index))) throw new JPMException(cellEmptyMsg(rowNum, index));
                        //项目经理
                        data.setMearContractorPm(StringFormat(hssfRow.getCell(index += 1)));
                        if (cellIsEmpty(hssfRow.getCell(index))) throw new JPMException(cellEmptyMsg(rowNum, index));
                        //计量时间
                        data.setMearVerbTime(TimeFormat(hssfRow.getCell(index += 1), "yyyy-MM-dd"));
                        if (cellIsEmpty(hssfRow.getCell(index))) throw new JPMException(cellEmptyMsg(rowNum, index));
                        list.add(data);
                    } catch (JPMException ex) {
                        is.close();
                        hssfWorkbook.close();
                        throw ex;
                    } catch (Exception ex) {
                        is.close();
                        hssfWorkbook.close();
                        throw new JPMException(String.format(FileMsg.unknownFormat, rowNum, numCovertLetter(index + 1)));
                    }
                }
            }
        }        }
finally {

        is.close();
        hssfWorkbook.close();
        }

        return list;
    }


    /**
     * 数据库中的时间类型
     * 全大写
     */
    private final static List<String> timeCodes = Arrays.asList("DATE", "DATETIME", "TIMESTAMP");

    public static GeneralFormsUpDTO readCompressedExcel(File[] excels, List<SysTableColumn> allColumns, FormDataImpDTO request) throws Exception {
        List<Map<String, String>> strData = new ArrayList<>();
        List<Map<String, String>> fileData = new ArrayList<>();
        Map<String, String> dictionaryMp = new HashMap<>();
        Map<String, String> keySql = new HashMap<>();
        List<String> otherFiles = new ArrayList<>();
        String projectKey = null;
        if (excels != null && excels.length > 0) {
            List<String> columns = allColumns.stream().map(SysTableColumn::getColumnCode).map(String::toUpperCase).collect(Collectors.toList());
            columns.add("FILEARCHIVEDIREXLPZ");
            columns.add("FILERELATIONID");
            List<String> times = allColumns.stream().filter(f -> ValidateUtil.isNotEmpty(f.getColumnType()) && timeCodes.contains(f.getColumnType().toUpperCase(Locale.ENGLISH))).map(SysTableColumn::getColumnCode).map(String::toUpperCase).collect(Collectors.toList());
            int topIndex = request == null ? 0 : request.getTopIndex();//表头列是第几行 缺省0
            for (File excel : excels) {
                String filePath = excel.getPath();
                InputStream is = new FileInputStream(excel);
                Workbook hssfWorkbook = null;
                if (filePath.endsWith("xlsx")) {
                    hssfWorkbook = new XSSFWorkbook(is);//Excel 2007
                } else if (filePath.endsWith("xls")) {
                    hssfWorkbook = new HSSFWorkbook(is);//Excel 2003
                }
                try {

                for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
                    Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
                    if (hssfSheet == null) {
                        continue;
                    }
                    // 循环行Row
                    int rankNum = 0;//列数量
                    List<String> keys = new ArrayList<>();
                    List<String> notNullKeys = new ArrayList<>();
                    for (int rowNum = topIndex; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                        Row hssfRow = hssfSheet.getRow(rowNum);
                        if (hssfRow != null) {
                            if (rowNum == topIndex) {//表头行
                                boolean flag = true;
                                while (flag) {
                                    Cell cell = hssfRow.getCell(rankNum);
                                    String formula = cell != null && cell.getCellType() == CellType.FORMULA ? cell.getCellFormula() : null; // 获取公式字符串
                                    String rankName = StringFormat(cell);
                                    if (!ValidateUtil.isAllEmpty(rankName, formula)) {
                                        String key;
                                        if (request != null && !CollectionUtils.isEmpty(request.getTops())) {
                                            if (request.getTops().size() > rankNum) {
                                                //入参传入表头列集合则按表头入参信息去列字段
                                                key = request.getTops().get(rankNum);
                                            } else {
                                                flag = false;
                                                continue;
                                            }
                                        } else
                                            key = ValidateUtil.isEmpty(getFormulaKey(formula)) ? null : getFormulaKey(formula).toUpperCase(Locale.ENGLISH);
                                        if (ValidateUtil.isNotEmpty(key) && columns.contains(key)) {
                                            keys.add(key);
                                            //验证必填字段
                                            List<SysTableColumn> noNulls = allColumns.stream().filter(f -> f.getColumnCode().equalsIgnoreCase(key) && ValidateUtil.isNotEmpty(f.getColumnNull())&&f.getColumnNull().equals("是")).collect(Collectors.toList());
                                            if (!CollectionUtils.isEmpty(noNulls)) notNullKeys.add(key);
                                            //字典类型字段
                                            List<SysTableColumn> dicColumn = allColumns.stream().filter(f -> f.getColumnCode().equalsIgnoreCase(key) && ValidateUtil.isNotNull(f.getFilterType()) && f.getFilterType() == 3).collect(Collectors.toList());
                                            if (!CollectionUtils.isEmpty(dicColumn))
                                                dictionaryMp.put(key, dicColumn.stream().findFirst().get().getAssocTableCode());
                                            //链表字段
                                            List<SysTableColumn> tableColumn = allColumns.stream().filter(f -> f.getColumnCode().equalsIgnoreCase(key) && ValidateUtil.isNotNull(f.getFilterType()) && f.getFilterType() == 2).collect(Collectors.toList());
                                            if (!CollectionUtils.isEmpty(tableColumn)) {
                                                SysTableColumn tc = tableColumn.stream().findFirst().get();
                                                //存储好SQL
                                                keySql.put(key, "select " + tc.getAssocColumnPk() + " as id," + tc.getAssocColumnCode() + " as acCode " + " from " + tc.getAssocTableCode() + " where " + tc.getAssocColumnCode() + " in ");
                                                for (SysTableColumn d : tableColumn) {
                                                    if (d.getAssocTableCode().equalsIgnoreCase("Pre_Project")) {
                                                        projectKey = d.columnCode.toUpperCase(Locale.ENGLISH);
                                                    }
                                                }
                                            }
                                            log.info("列:" + rankNum + " 公式:" + formula + " key:" + getFormulaKey(formula));
                                        } else keys.add(null);
                                        rankNum += 1;
                                    } else flag = false;
                                }
                            } else {
                                Map<String, String> entity = new HashMap<>();
                                Map<String, String> files = new HashMap<>();

                                for (int i = 0; i < hssfRow.getLastCellNum(); i++) {
                                    if (i < rankNum) {
                                        if (ValidateUtil.isNotEmpty(keys.get(i))) {
                                            Cell cell = hssfRow.getCell(i);
                                            Hyperlink link = cell != null ? cell.getHyperlink() : null;
                                            if (link != null)//附件超链接
                                                files.put(keys.get(i), link.getAddress());
                                            else//其他文本
                                            {
                                                if (!CollectionUtils.isEmpty(times) && times.contains(keys.get(i))) {
                                                    //时间格式
                                                    entity.put(keys.get(i), cellTime2Str(cell, "yyyy-MM-dd HH:mm:ss"));
                                                } else entity.put(keys.get(i), StringFormat(cell));
                                            }
                                            if (notNullKeys.contains(keys.get(i))&&ValidateUtil.isEmpty(files.get(keys.get(i)))
                                            &&ValidateUtil.isEmpty(entity.get(keys.get(i))))
                                                throw new JPMException(MessageFormat.format(FileMsg.cellIsEmpty, rowNum + 1, ExcelUtil.numCovertLetter(i+1)));
                                        }
                                    } else { //额外通用附件
                                        Cell cell = hssfRow.getCell(i);
                                        Hyperlink link = cell != null ? cell.getHyperlink() : null;
                                        if (link != null)//附件超链接
                                            otherFiles.add(link.getAddress());
                                    }
                                }
                                strData.add(entity);
                                fileData.add(files);
                            }
                        }
                    }
                }
                }finally {
                is.close();
                hssfWorkbook.close();
                }
            }
        }
        return new GeneralFormsUpDTO().setOtherFiles(otherFiles).setFileData(fileData).setStrData(strData).setDictionaryMp(dictionaryMp).setKeySql(keySql).setProjectKey(projectKey);
    }


    public static void exportFormData(List<Map> data, List<SysTableColumn> columns, List<String> fileCodes, String dir, String tCode, String tName
            , List<SysDictionary> dics, List<String> dicColumns, List<Map> projects, List<Map> contracts) throws IOException {
        //编码顺序
        List<String> codes = new ArrayList<>();
        //头部公式
        List<String> tops = new ArrayList<>();
        //非公式索引
        List<Integer> noFmIndex = new ArrayList<>();
        List<Map<Integer, String>> project = new ArrayList<>();
        List<Map<Integer, String>> contract = new ArrayList<>();
        for (SysTableColumn column : columns) {
            if (ValidateUtil.isEmpty(column.getColumnPk()) || !column.getColumnPk().equals("1")) {
                String name = ValidateUtil.isNotEmpty(column.getColumnName()) ? column.getColumnName() : column.getColumnCode();
                if (ValidateUtil.isNotNull(column.getFilterType()) && column.getFilterType() == 2) {
                    if (ValidateUtil.isNotEmpty(column.getAssocShowCode())) {
                        codes.add(column.getAssocShowCode());
                        //额外导出字段不参与解析 导出时不用使用公式导出
                        tops.add(ValidateUtil.isEmpty(column.getAssocShowName()) ? column.getAssocShowCode() : column.getAssocShowName());
                        noFmIndex.add(tops.size() - 1);
                        Map<Integer, String> im1 = new HashMap<>();
                        im1.put(tops.size() - 1, column.getAssocShowCode().toUpperCase(Locale.ENGLISH));
                        Map<Integer, String> im2 = new HashMap<>();
                        im2.put(tops.size(), column.getAssocColumnCode().toUpperCase(Locale.ENGLISH));
                        if (column.getAssocTableCode().equalsIgnoreCase("Pre_Project")) {
                            project.add(im1);
                            project.add(im2);
                        } else if (column.getAssocTableCode().equalsIgnoreCase("Con_Contract")) {
                            contract.add(im1);
                            contract.add(im2);
                        }
                    } else {
                        Map<Integer, String> im2 = new HashMap<>();
                        im2.put(tops.size(), column.getAssocColumnCode().toUpperCase(Locale.ENGLISH));
                        if (column.getAssocTableCode().equalsIgnoreCase("Pre_Project"))
                            project.add(im2);
                        else if (column.getAssocTableCode().equalsIgnoreCase("Con_Contract"))
                            contract.add(im2);
                    }
                    codes.add(column.getAssocColumnCode());
                } else codes.add(column.getColumnCode());
                tops.add("IF(ISNUMBER(SEARCH(\"" + column.getColumnCode() + "\",\"" + column.getColumnCode() + "\")),\"" + name + "\",\"" + column.getColumnCode() + "\")");
            }
        }
        List<String> times = columns.stream().filter(f -> ValidateUtil.isNotEmpty(f.getColumnType()) && timeCodes.contains(f.getColumnType().toUpperCase(Locale.ENGLISH))).map(SysTableColumn::getColumnCode).collect(Collectors.toList());
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 创建工作表
        XSSFSheet sheet = workbook.createSheet("Sheet1");
        CellStyle cs = workbook.createCellStyle();
        cs.setDataFormat(workbook.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
        // 创建第一行
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < tops.size(); i++) {
            Cell cell = headerRow.createCell(i);
            if (noFmIndex.contains(i))
                cell.setCellValue(tops.get(i));
            else
                cell.setCellFormula(tops.get(i));
            sheet.setColumnWidth(i, 2 * 256 * 8);
        }
        if (!CollectionUtils.isEmpty(data)) {
            for (int i = 0; i < data.size(); i++) {
                Map map = data.get(i);
                Row row = sheet.createRow(i + 1);
                for (int j = 0; j < codes.size(); j++) {
                    Cell cell = row.createCell(j);
                    Object obj = map.get(codes.get(j));
                    if (fileCodes.contains(codes.get(j))) {
                        if (ValidateUtil.isNotNull(obj)) {
                            cell.setCellValue(obj.toString());
                            Hyperlink hp = workbook.getCreationHelper().createHyperlink(HyperlinkType.FILE);
                            hp.setAddress("./" + obj.toString().replaceAll("\\\\", "/"));
                            cell.setHyperlink(hp);
                        }
                    } else {
                        if (!CollectionUtils.isEmpty(times) && times.contains(codes.get(j))) {
                            cell.setCellValue(DateUtil.String2Date(ValidateUtil.isNotNull(obj) ? obj.toString() : null, "yyyy-MM-dd HH:mm:ss"));
                            cell.setCellStyle(cs);
                        } else cell.setCellValue(ValidateUtil.isNotNull(obj) ? obj.toString() : null);
                    }
                }
            }
        }
        //region 字典设置下拉选择框
        for (int j = 0; j < codes.size(); j++) {
            if (dicColumns.contains(codes.get(j))) {
                int finalJ = j;
                List<SysTableColumn> dColumns = columns.stream().filter(f -> f.getColumnCode().equals(codes.get(finalJ))).collect(Collectors.toList());
                String dic = dColumns.stream().findFirst().get().getAssocTableCode();
                if (ValidateUtil.isNotEmpty(dic)) {
                    List<SysDictionary> oneDic = dics.stream().filter(f -> f.getParentCode().equals(dic)).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(oneDic)) {
                        List<String> disStr = oneDic.stream().map(SysDictionary::getDName).collect(Collectors.toList());
                        addValidationData(sheet, disStr.toArray(new String[disStr.size()]), 1, CollectionUtils.isEmpty(data) ? 0 : data.size(), j, j);
                    }
                }
            }
//            if (!CollectionUtils.isEmpty(data))
//                //自动调整列宽
//                sheet.autoSizeColumn(j);
        }
        //项目下拉选择
        if (!CollectionUtils.isEmpty(projects) && !CollectionUtils.isEmpty(project)) {
            project.forEach(p -> {
                p.forEach((k, v) -> {
                    List<String> res = getListValues(projects, v);
                    if (!CollectionUtils.isEmpty(res))
                        addValidationData(sheet, res.toArray(new String[res.size()]), 1, CollectionUtils.isEmpty(data) ? 0 : data.size(), k, k);
                });
            });
        }
        List<String> conName = new ArrayList<>();
        //合同下拉选择
        if (!CollectionUtils.isEmpty(contracts) && !CollectionUtils.isEmpty(contract)) {
            contract.forEach(p -> {
                p.forEach((k, v) -> {
                    List<String> res = getListValues(contracts, v);
                    if (!CollectionUtils.isEmpty(res)) {
                        addValidationData(sheet, res.toArray(new String[res.size()]), 1, CollectionUtils.isEmpty(data) ? 0 : data.size(), k, k);
                        conName.add(res.get(0));
                    }
                });
            });
        }
        //endregion
        String xlsxName = ValidateUtil.isEmpty(tName) ? tCode + ".xlsx" : tName + "(" + tCode + ").xlsx";
        if (!CollectionUtils.isEmpty(conName)) xlsxName = conName.get(0) + "-" + xlsxName;
        String excel = dir + "/" + xlsxName;
        try (FileOutputStream outputStream = new FileOutputStream(excel)) {
            workbook.write(outputStream);
        }
    }

    public static void exportArchivementsExcel(List<ArchievementsDTO> news, String excelPath) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 创建工作表
        XSSFSheet sheet = workbook.createSheet("Sheet1");
        // 创建第一行
        Row headerRow = sheet.createRow(0);
        Cell cell1 = headerRow.createCell(0);
        cell1.setCellValue("项目名称");
        Cell cell2 = headerRow.createCell(1);
        cell2.setCellValue("建设程序名称");
        Cell cell3 = headerRow.createCell(2);
        cell3.setCellValue("成果文件");
        for (int i = 0; i < news.size(); i++) {
            ArchievementsDTO a = news.get(i);
            Row row = sheet.createRow(i + 1);
            Cell c1 = row.createCell(0);
            c1.setCellValue(a.getPName());
            Cell c2 = row.createCell(1);
            c2.setCellValue(a.getCpdName());
            Cell c3 = row.createCell(2);
            c3.setCellValue(a.getPath());
            Hyperlink hp = workbook.getCreationHelper().createHyperlink(HyperlinkType.FILE);
            hp.setAddress("./" + a.getPath());
            c3.setHyperlink(hp);
        }
        sheet.setColumnWidth(0, 2 * 256 * 8);
        sheet.setColumnWidth(1, 2 * 256 * 8);
        sheet.setColumnWidth(2, 2 * 256 * 8);
        try (FileOutputStream outputStream = new FileOutputStream(excelPath)) {
            workbook.write(outputStream);
        }
    }

    public static List<GanttTaskFeedbackDTO> importGtZip(File excel) throws Exception {
        List<GanttTaskFeedbackDTO> list = new ArrayList<>();
        InputStream is = new FileInputStream(excel);
        Workbook hssfWorkbook = null;
        if (excel.getPath().endsWith("xlsx")) {
            hssfWorkbook = new XSSFWorkbook(is);//Excel 2007
        } else if (excel.getPath().endsWith("xls")) {
            hssfWorkbook = new HSSFWorkbook(is);//Excel 2003
        }
        GanttTaskFeedbackDTO data = null;
        // 循环工作表Sheet
        int quarter = DateUtil.getCurrentQuarter();
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // 循环行Row
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                Row hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                    Integer index = 0;
                    try {
                        data = new GanttTaskFeedbackDTO();
                        //任务编码
                        data.setTaskId(StringFormat(hssfRow.getCell(index)));
                        if (cellIsEmpty(hssfRow.getCell(index))) throw new JPMException(cellEmptyMsg(rowNum, index));
                        //计划名称
                        data.setPlanName(StringFormat(hssfRow.getCell(index += 1)));
                        //所属项目
                        data.setPName(StringFormat(hssfRow.getCell(index += 1)));
                        //上级节点名称
                        data.setTaskParentText(StringFormat(hssfRow.getCell(index += 1)));
                        //节点名称
                        data.setTaskText(StringFormat(hssfRow.getCell(index += 1)));
                        data.setTaskStartDate(TimeFormat(hssfRow.getCell(index += 1), "yyyy-MM-dd"));
                        data.setTaskEndDate(TimeFormat(hssfRow.getCell(index += 1), "yyyy-MM-dd"));
                        data.setTaskBeginTime(TimeFormat(hssfRow.getCell(index += 1), "yyyy-MM-dd"));
                        data.setTaskFinishTime(TimeFormat(hssfRow.getCell(index += 1), "yyyy-MM-dd"));
                        //优先级
                        data.setTaskPriority(StringFormat(hssfRow.getCell(index += 1)));
                        //要求成果
                        data.setTaskDoc(StringFormat(hssfRow.getCell(index += 1)));
                        String taskDuration = StringFormat(hssfRow.getCell(index += 1));
                        data.setTaskDuration(ValidateUtil.isEmpty(taskDuration) ? 0F : Float.parseFloat(taskDuration));
                        String taskProgress = StringFormat(hssfRow.getCell(index += 1));
                        data.setTaskProgress(ValidateUtil.isEmpty(taskProgress) ? 0F : Float.parseFloat(taskProgress));
                        String gtpRate = StringFormat(hssfRow.getCell(index += 1));
                        data.setGtpRate(ValidateUtil.isEmpty(gtpRate) ? 0F : Float.parseFloat(gtpRate));
                        //填报日期
                        data.setGtpDate(TimeFormat(hssfRow.getCell(index += 1), "yyyy-MM-dd"));
                        //形象进度填报
                        data.setGtpImageProgress(StringFormat(hssfRow.getCell(index += 1)));
                        //责任人
                        data.setTaskUname(StringFormat(hssfRow.getCell(index += 1)));
                        List<String> files = new ArrayList<>();
                        for (int i = index + 1; i < hssfRow.getLastCellNum(); i++) {
                            Cell cell = hssfRow.getCell(i);
                            Hyperlink link = cell != null ? cell.getHyperlink() : null;
                            if (link != null)//附件超链接
                                files.add(link.getAddress());
                        }
                        data.setGtpScenePhotos(JacksonUtils.toJSONString(files));
                        list.add(data);
                    } catch (JPMException ex) {
                        is.close();
                        hssfWorkbook.close();
                        throw ex;
                    } catch (Exception ex) {
                        is.close();
                        hssfWorkbook.close();
                        throw new JPMException(String.format(FileMsg.unknownFormat, rowNum, numCovertLetter(index + 1)));
                    }
                }
            }
        }
        is.close();
        hssfWorkbook.close();
        return list;
    }

    /**
     * 获取公式中的key
     *
     * @param formula 公式
     * @return
     */
    private static String getFormulaKey(String formula) {
        if (ValidateUtil.isEmpty(formula)) return null;
        String keyStr = formula.split(",")[3];
        return keyStr.substring(keyStr.indexOf("\"") + 1, keyStr.lastIndexOf("\""));
    }

    private static BaseResponse returnBaseRes(int row, int index) {
        return new BaseResponse(cellEmptyMsg(row, index));
    }

    private static String cellEmptyMsg(int row, int index) {
        return MessageFormat.format(FileMsg.cellIsEmpty, row, numCovertLetter(index + 1));
    }

    /**
     * 工程报告导入 根据季度判断当前是否必填
     *
     * @return
     */
    private static Boolean checkQuarter(Row hssfRow, int index, int quarter, int cquater) {
        if (quarter >= cquater && cellIsEmpty(hssfRow.getCell(index))) return true;
        return false;
    }

    private static void getBudgetDeclareDtlChilds(List<InvBudgetDeclareDtlModel> child, List<InvBudgetDeclareDtlModel> childs, List<InvBudgetDeclareDtlModel> list, InvBudgetDeclareDtlModel data) {
        if (childs != null && childs.size() > 0) {
            childs.forEach(header -> {
                List<InvBudgetDeclareDtlModel> childNode = new ArrayList<>();
                String nodeCode = header.getBddParentId();
                header.setBddParentId(data.getBddId());
                List<InvBudgetDeclareDtlModel> childNodes = list.stream().filter(n -> n.getBddParentId().contains("-") && n.getBddParentId().substring(0, n.getBddParentId().lastIndexOf('-')).equals(nodeCode)).collect(Collectors.toList());
                getBudgetDeclareDtlChilds(childNode, childNodes, list, header);
                if(ValidateUtil.isNotEmpty(childNode)) {
                    header.setBddTotal(childNode.stream().map(InvBudgetDeclareDtlModel::getBddTotal).reduce(BigDecimal.ZERO, NumberUtil::add));
                    header.setBddBuildFee(childNode.stream().map(InvBudgetDeclareDtlModel::getBddBuildFee).reduce(BigDecimal.ZERO, NumberUtil::add));
                    header.setBddInstallFee(childNode.stream().map(InvBudgetDeclareDtlModel::getBddInstallFee).reduce(BigDecimal.ZERO, NumberUtil::add));
                    header.setBddDeviceFee(childNode.stream().map(InvBudgetDeclareDtlModel::getBddDeviceFee).reduce(BigDecimal.ZERO, NumberUtil::add));
                    header.setBddOtherFee(childNode.stream().map(InvBudgetDeclareDtlModel::getBddOtherFee).reduce(BigDecimal.ZERO, NumberUtil::add));
                }
                header.setChildNodes(childNode);
                child.add(header);
            });
        }
    }

    private static void getProjectRegisterDtlChilds(List<PreProjectRegisterDtlModel> child, List<PreProjectRegisterDtlModel> childs, List<PreProjectRegisterDtlModel> list, PreProjectRegisterDtlModel data) {
        if (childs != null && childs.size() > 0) {
            childs.forEach(header -> {
                List<PreProjectRegisterDtlModel> childNode = new ArrayList<>();
                String nodeCode = header.getPrdParenetId();
                header.setPrdParenetId(data.getPrdId());
                List<PreProjectRegisterDtlModel> childNodes = list.stream().filter(n -> n.getPrdParenetId().contains("-") && n.getPrdParenetId().substring(0, n.getPrdParenetId().lastIndexOf('-')).equals(nodeCode)).collect(Collectors.toList());
                getProjectRegisterDtlChilds(childNode, childNodes, list, header);
                header.setChildNodes(childNode);
                child.add(header);
            });
        }
    }

    private static void getPreProApprovalDtlChilds(List<PreProApprovalDtlModel> child, List<PreProApprovalDtlModel> childs, List<PreProApprovalDtlModel> list, PreProApprovalDtlModel data) {
        if (childs != null && childs.size() > 0) {
            childs.forEach(header -> {
                List<PreProApprovalDtlModel> childNode = new ArrayList<>();
                String nodeCode = header.getPadParenetId();
                header.setPadParenetId(data.getPadId());
                List<PreProApprovalDtlModel> childNodes = list.stream().filter(n -> n.getPadParenetId().contains("-") && n.getPadParenetId().substring(0, n.getPadParenetId().lastIndexOf('-')).equals(nodeCode)).collect(Collectors.toList());
                getPreProApprovalDtlChilds(childNode, childNodes, list, header);
                header.setChildNodes(childNode);
                child.add(header);
            });
        }
    }

    private static void getPreProjectDtlChilds(List<PreProjectDtlModel> child, List<PreProjectDtlModel> childs, List<PreProjectDtlModel> list, PreProjectDtlModel data) {
        if (childs != null && childs.size() > 0) {
            childs.forEach(header -> {
                List<PreProjectDtlModel> childNode = new ArrayList<>();
                String nodeCode = header.getPpdParenetId();
                header.setPpdParenetId(data.getPpdId());
                List<PreProjectDtlModel> childNodes = list.stream().filter(n -> n.getPpdParenetId().contains("-") && n.getPpdParenetId().substring(0, n.getPpdParenetId().lastIndexOf('-')).equals(nodeCode)).collect(Collectors.toList());
                getPreProjectDtlChilds(childNode, childNodes, list, header);
                header.setChildNodes(childNode);
                child.add(header);
            });
        }
    }





    private static void getFinalAccountsApplyDtlChilds(List<InvFinalAccountsApplyDtlDto> child, List<InvFinalAccountsApplyDtlDto> childs, List<InvFinalAccountsApplyDtlDto> list, InvFinalAccountsApplyDtlDto data) {
        if (childs != null && childs.size() > 0) {
            childs.forEach(header -> {
                List<InvFinalAccountsApplyDtlDto> childNode = new ArrayList<>();
                String nodeCode = header.getFadParentId();
                header.setFadParentId(data.getFadId());
                List<InvFinalAccountsApplyDtlDto> childNodes = list.stream().filter(n -> n.getFadParentId().contains("-") && n.getFadParentId().substring(0, n.getFadParentId().lastIndexOf('-')).equals(nodeCode)).collect(Collectors.toList());
                getFinalAccountsApplyDtlChilds(childNode, childNodes, list, header);
                header.setChildNodes(childNode);
                child.add(header);
            });
        }
    }


    public static String getRealStringValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        } else {
            final CellType cellType = cell.getCellType();
            if (CellType.NUMERIC.equals(cellType)) {
                cellValue = realStringValueOfDouble(cell.getNumericCellValue());
            } else {
                cellValue = new DataFormatter().formatCellValue(cell);
            }
            return cellValue;
        }
    }

    public static String realStringValueOfDouble(Double d) {
        String doubleStr = d.toString();
        if (doubleStr.contains(".") && doubleStr.endsWith(".0")) //整数被自动补0
            return doubleStr.substring(0, doubleStr.length() - 2);
        else return doubleStr;
    }

    /**
     * 下拉框
     */
    public static void addValidationData(XSSFSheet sheet, String[] data, int firstRow, int lastRow, int firstCol, int lastCol) {
        if (lastRow == 0) lastRow = 101;
        XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);
        // 下拉框可选的数据，作为约束
        XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(data);
        // 设置为下拉框的范围
        CellRangeAddressList addressList = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
        // 创建 DataValidation 对象
        XSSFDataValidation validation = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
        validation.setShowErrorBox(true);
        // 作用于指定工作表
        sheet.addValidationData(validation);
    }

    /**
     * 处理导入小数点
     */
    private static String StringFormat(Cell cell) {
        if (ValidateUtil.isNull(cell)) return null;
//        cell.setCellType(CellType.STRING);
        DataFormatter dataFormatter = new DataFormatter();
        return dataFormatter.formatCellValue(cell);
    }

    private static Float FloatFormat(Cell cell) {
        return Float.valueOf(cellIsEmpty(cell) ? "0" : cell.toString());
    }

    private static Date TimeFormat(Cell cell, String format) {
        if (ValidateUtil.isNull(cell)) return null;
        if (cell.getLocalDateTimeCellValue() == null) return null;
        Date date = DateUtil.String2Date(cell.getLocalDateTimeCellValue().toString().replaceAll("T", " "), format);
        return date;
    }

    private static String cellTime2Str(Cell cell, String format) {
        if (ValidateUtil.isNull(cell)) return null;
        if (cell.getLocalDateTimeCellValue() == null) return null;
        LocalDateTime localDateTime = cell.getLocalDateTimeCellValue();
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return DateUtil.Date2String(date, format);
    }

    /**
     * 处理导入身份证成科学计数
     *
     * @param cell
     * @return
     */
    private static String IntFormat(Cell cell) {
        if (ValidateUtil.isNull(cell)) return null;
        if (cell.getCellType() == CellType.NUMERIC) {
            DecimalFormat df = new DecimalFormat("0");
            return df.format(cell.getNumericCellValue());
        }
        return cell.toString();
    }


    /**
     * 导出接口日志到Excel
     *
     * @param sheet       页数
     * @param excelWriter
     * @param datas       数据
     */
    public static Boolean exportInterfaceLog2Excel(int sheet, ExcelWriter excelWriter, List<SysLoggerInfo> datas) {
        boolean flag = true;
        try {
            WriteSheet writeSheet = EasyExcel.writerSheet(sheet, "sheet" + sheet).build();
            excelWriter.write(datas, writeSheet);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("迭代删除接口日志,生产日志Excel失败！" + ex.getMessage(),ex);
            flag = false;
        }
        return flag;
    }

    /**
     * 判断单元格是否为空
     *
     * @param cell
     * @return
     */
    public static boolean cellIsEmpty(Cell cell) {
        if (ValidateUtil.isNull(cell)) return Boolean.TRUE;
        String str = new DataFormatter().formatCellValue(cell);
        return str == null || "".equals(str + "");
    }

    private static String getStr(Cell cell) {
        return cellIsEmpty(cell) ? "" : StringFormat(cell);
    }

    private static BigDecimal getDec(Cell cell) {
        return new BigDecimal(cellIsEmpty(cell) ? "0" : StringFormat(cell));
    }

    private static Integer getInt(Cell cell) {
        return ValidateUtil.isEmpty(StringFormat(cell)) ? null : Integer.parseInt(StringFormat(cell));
    }

    /**
     * 根据列数计算列号
     *
     * @param num 列数
     * @return
     */
    public static String numCovertLetter(int num) {
        if (num <= 0) {
            return num + "";
        }
        String str = "";
        while (num > 0) {
            int res = num % 26;
            if (res == 0) {
                res = 26;
            }
            str = (char) (res + 64) + str;
            num = (num - res) / 26;
        }
        return str;
    }

    /**
     * 判断是否包含数值
     *
     * @param str
     * @return
     */
    private static boolean containsNum(String str) {
        Pattern pattern = Pattern.compile(".*[0-9].*");
        return pattern.matcher(str).matches();
    }


    private static void checkDictionary(List<String> cons, String value, int row, int index, String dicCode) {
        if (cons != null && !CollectionUtils.isEmpty(cons)) {
            if (ValidateUtil.isNotEmpty(value) && !cons.contains(value))
                throw new JPMException(String.format(FileMsg.unknownFormat, row, numCovertLetter(index + 1)));
        } else throw new JPMException(String.format(FileMsg.notFoundDictionary, dicCode));
    }

    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
    public static final int NUM_ROWS_PER_THREAD = 500;

    public static void tet(String inputFilePath, String outputDirPath, String name) throws Exception {
        long startTime = System.currentTimeMillis();
        String newPath = outputDirPath + "/" + name;
        //将分段生成的excel放入统一目录用于后期合并
        String outputDir = outputDirPath + "/" + GenerateUUID.getUUID();
        File dir = new File(outputDir);
        if (!dir.exists() || !dir.isDirectory()) dir.mkdirs();
        int numRows = getNumRows(inputFilePath); // 获取工作表中的总行数（这里需要实现获取行数的逻辑）
        int startRow = 0; // 第一个线程处理的起始行号
        int totalParts = (int) Math.ceil((double) numRows / NUM_ROWS_PER_THREAD); // 计算需要的部分数
        List<Future<File>> futures = new ArrayList<>(); // 用于存储每个线程的结果的列表
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS); // 创建固定大小的线程池
        try (FileInputStream fis = new FileInputStream(inputFilePath)) {
            Workbook workbook = new HSSFWorkbook(fis); // 读取原始的XLS文件作为二进制输入流
            Sheet sheet = workbook.getSheetAt(0); // 获取第一个工作表（根据需要调整索引）
            for (int part = 0; part < totalParts; part++) {
                int statr = startRow;
                int threadEndRow = (part + 1) * NUM_ROWS_PER_THREAD; // 计算当前部分结束的行号（如果超出总行数则取总行数）
                futures.add((Future<File>) executorService.submit(() -> {
                    try (OutputStream os = new FileOutputStream(outputDir + "/output_" + threadEndRow + ".xlsx")) { // 输出到新文件（这里需要根据实际需求调整）
                        Workbook clonedWorkbook = new XSSFWorkbook(); // 克隆工作簿以支持流式写入（不修改原始工作簿）
                        Sheet clonedSheet = clonedWorkbook.createSheet("sheet1");
                        for (int rowNum = statr, cNum = 0; rowNum < threadEndRow; rowNum++, cNum++) {
                            Row sourceRow = sheet.getRow(rowNum); // 获取源行
                            if (sourceRow != null) {
                                Row clonedRow = clonedSheet.createRow(cNum); // 在克隆的工作表中创建行（不会影响原始工作表）
                                copyRow(sourceRow, clonedRow); // 复制行数据（这里需要根据实际情况实现）
                            }
                        }
                        clonedWorkbook.write(os); // 将克隆的工作簿写入输出文件（不修改原始工作簿）
                        clonedWorkbook.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                })); // 将任务提交给线程池执行，并返回一个Future对象，表示异步计算的结果。注意：在上述示例中，您需要根据实际使用的POI库版本和数据类型处理单元格的值。
                startRow = threadEndRow;
            }
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                executorService.shutdown(); // 关闭线程池
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    executorService.shutdownNow(); // 如果线程池未在给定的时间内关闭，则立即停止所有正在执行的活动任务并返回等待执行的任务列表。这可能导致任务未开始执行或未完成执行。
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("多线程执行分段读取执行完成！");
                long tdTime = System.currentTimeMillis();
                long tdTimeElapsed = tdTime - startTime; // 计算耗时
                System.out.println("耗时: " + tdTimeElapsed / 1000 + "秒");
                XlsxMerge.merge(dir, newPath);
                long endTime = System.currentTimeMillis();
                long timeElapsed = endTime - tdTime; // 计算耗时
                System.out.println("合并xlsx完成！");
                System.out.println("耗时: " + timeElapsed / 1000 + "秒");
            }
        }
    }

    //合并文件
    private static void mergeTempFiles(File dir, String outputPath) throws IOException {
        List<File> tempFiles = Arrays.asList(dir.listFiles());
        Workbook outputWorkbook = new XSSFWorkbook(); // 创建新的XLSX工作簿对象
        Sheet outputSheet = outputWorkbook.createSheet("Sheet1"); // 在输出工作簿中创建新的工作表
        int rowNum = 0;
        for (File tempFile : tempFiles) {
            Workbook tempWorkbook = WorkbookFactory.create(tempFile); // 读取临时文件的工作簿
            Sheet tempSheet = tempWorkbook.getSheetAt(0); // 获取第一个工作表的数据
            for (Row row : tempSheet) { // 遍历行数据
                Row outputRow = outputSheet.createRow(rowNum++); // 在输出工作表中创建对应行的数据
                for (Cell cell : row) { // 遍历单元格数据
                    Cell outputCell = outputRow.createCell(cell.getColumnIndex()); // 在输出行中创建对应单元格的数据
                    copyCell(cell, outputCell); // 将数据复制到输出单元格中
                }
            }
            tempWorkbook.close();
        }
        try (FileOutputStream outputStream = new FileOutputStream(outputPath)) {
            outputWorkbook.write(outputStream); // 将输出工作簿写入最终文件
        } finally {
            outputWorkbook.close();
            FileUtil.deleteFiles(dir, true);
        }
    }

    // 计算总行数
    private static int getNumRows(String inputFilePath) throws IOException {
        // 读取工作簿和工作表，这里需要根据实际情况进行调整
        Workbook workbook = new HSSFWorkbook(new FileInputStream(inputFilePath));
        Sheet sheet = workbook.getSheetAt(0);
        return sheet.getLastRowNum() + 1; // 返回总行数
    }

    // 辅助方法：复制行数据（根据实际情况实现）
    private static void copyRow(Row sourceRow, Row clonedRow) {
        for (int i = sourceRow.getFirstCellNum(); i <= sourceRow.getLastCellNum(); i++) {
            Cell sourceCell = sourceRow.getCell(i);
            if (sourceCell != null) {
                Cell clonedCell = clonedRow.createCell(i);
                copyCell(sourceCell, clonedCell);
            }
        }
    }

    // 辅助方法：复制单元格数据（根据实际情况实现）
    private static void copyCell(Cell inputCell, Cell outputCell) {
//        outputCell.setCellType(inputCell.getCellType());
        switch (inputCell.getCellType()) {
            case STRING:
                outputCell.setCellValue(inputCell.getStringCellValue());
                break;
            case NUMERIC:
                outputCell.setCellValue(inputCell.getNumericCellValue());
                break;
            case BOOLEAN:
                outputCell.setCellValue(inputCell.getBooleanCellValue());
                break;
            case FORMULA:
                outputCell.setCellFormula(inputCell.getCellFormula());
                break;
            default:
                outputCell.setCellValue(inputCell.getStringCellValue());
                break;
        }
    }


    private static List<String> getListValues(List<Map> data, String key) {
        List<String> res = new ArrayList<>();
        data.forEach(d -> {
            if (d.containsKey(key) && d.get(key) != null) res.add(d.get(key).toString());
        });
        return res;
    }


    public static List<InvBudgetDeclareDtlModel> uploadBudgetDeclareExcelBl(String url) throws IOException {
        List<InvBudgetDeclareDtlModel> list = new ArrayList<>();
        InputStream is = Files.newInputStream(new File(url).toPath());
        Workbook hssfWorkbook = null;
        if (url.endsWith("xlsx")) {
            hssfWorkbook = new XSSFWorkbook(is);//Excel 2007
        } else if (url.endsWith("xls")) {
            hssfWorkbook = new HSSFWorkbook(is);//Excel 2003
        }
        try {
            InvBudgetDeclareDtlModel plan = null;
            // 循环工作表Sheet
            if (hssfWorkbook != null) {
                Map<String, InvBudgetDeclareDtlModel> nodeMap = new HashMap<>();
                for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
                    Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
                    if (hssfSheet == null) {
                        continue;
                    }
                    String sheetName =hssfSheet.getSheetName();
                    if (StrUtil.isNotEmpty(sheetName)){
                        log.info("sheetName:{}",sheetName);
                        switch (sheetName)
                        {
                            case "总概算表": getTotalEstimate(hssfSheet,list);break;
                            case "其他费":getOtherEstimate(hssfSheet,list);break;
                            case "综合概算表 (西南地块)  ":
                            case "综合概算表（南区宿舍地块、北区宿舍地块、垃圾转运站地块）":getCompositeEstimate(hssfSheet,list);break;
                            default:break;
                        }
                    }
                }
            }
            log.info("list:{}",JacksonUtils.toJSONString(list));
            return list;
        }finally {
            is.close();
            if (hssfWorkbook != null) {
                hssfWorkbook.close();
            }
        }
    }

    private static void getOtherEstimate(Sheet hssfSheet, List<InvBudgetDeclareDtlModel> list) {
        List<InvBudgetDeclareDtlModel> list2 = new ArrayList<>();
        for (int rowNum = 4; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
            Row hssfRow = hssfSheet.getRow(rowNum);
            if (hssfRow != null) {
                InvBudgetDeclareDtlModel plan = new InvBudgetDeclareDtlModel();
                plan.setBddId(GenerateUUID.getUUID());
                InvBudgetDeclareDtlModel parentModel = list.stream().filter(d -> "二".equals(d.getBddNumber()))
                        .findFirst().orElse(null);
                plan.setParentId(parentModel != null ? parentModel.getBddId() : null);
                plan.setBddName(ValidateUtil.isEmpty(hssfRow.getCell(1)) ? "" : hssfRow.getCell(1).toString());
                if (ValidateUtil.isEmpty(plan.getBddName())){
                    continue;
                }
                String s= ValidateUtil.isEmpty(hssfRow.getCell(0)) ? "" : hssfRow.getCell(0).toString();
                BigDecimal sDouble = new BigDecimal(s).setScale(1, RoundingMode.HALF_UP);
                plan.setBddNumber(String.valueOf(sDouble));
                //log.info("plan.getBddName():{}:{}:{}", plan.getBddName(), rowNum, hssfRow.getCell(2).toString());
                plan.setBddTotal(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(3)) ? "0" : hssfRow.getCell(3).toString()));
                plan.setBddPrice(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(4)) ? "0" : hssfRow.getCell(4).toString()).setScale(6, RoundingMode.HALF_UP));
                plan.setBddRemark(ValidateUtil.isEmpty(hssfRow.getCell(5)) ? "" : hssfRow.getCell(5).toString());
                list2.add(plan);
            }
        }
        for (InvBudgetDeclareDtlModel plan : list2){
            if (plan.getBddNumber().contains(".")){
                String parentNumber = plan.getBddNumber().substring(0, plan.getBddNumber().indexOf("."));
                String childNumber = plan.getBddNumber().substring(plan.getBddNumber().indexOf(".") + 1);
                parentNumber+=".0";
                if (!childNumber.equals("0")){
                   String finalParentNumber = parentNumber;
                   InvBudgetDeclareDtlModel parentModel = list2.stream().filter(d -> finalParentNumber
                           .equals(d.getBddNumber())).findFirst().orElse(null);
                   plan.setBddParentId(parentModel != null ? parentModel.getBddId() : null);
                }
            }
        }
        for (InvBudgetDeclareDtlModel plan : list2) {
            List<InvBudgetDeclareDtlModel> childNodes = list2.stream().filter(d -> plan.getBddId()
                    .equals(d.getBddParentId())).collect(Collectors.toList());
            plan.setChildNodes(childNodes);
        }
        list2=list2.stream().filter(d -> ValidateUtil.isEmpty(d.getBddParentId())).collect(Collectors.toList());
        log.info("其他费:{}",JacksonUtils.toJSONString(list2));
        List<InvBudgetDeclareDtlModel> finalList = list2;
        list.stream().filter(d -> "二".equals(d.getBddNumber()))
                .findFirst().ifPresent(parentModel -> parentModel.setChildNodes(finalList));
    }

    private static void getCompositeEstimate(Sheet hssfSheet, List<InvBudgetDeclareDtlModel> list) {
        String sheetName =hssfSheet.getSheetName();
        List<String> parentNodes = new ArrayList<>();
        parentNodes.add("一");
        parentNodes.add("二");
        parentNodes.add("三");
        List<String> childNodes = getStringList();
        List<String> childNode2s = getStrings();
        if ("综合概算表 (西南地块)  ".equals(sheetName)){
            List<InvBudgetDeclareDtlModel> list2 = new ArrayList<>();
            for (int rowNum = 5; rowNum <= hssfSheet.getLastRowNum(); rowNum++){
                Row hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                    InvBudgetDeclareDtlModel plan = new InvBudgetDeclareDtlModel();
                    plan.setBddId(GenerateUUID.getUUID());
                    plan.setRowNum(rowNum);
                    plan.setBddNumber(ValidateUtil.isEmpty(hssfRow.getCell(0)) ? "" : hssfRow.getCell(0).toString());
                    plan.setBddName(ValidateUtil.isEmpty(hssfRow.getCell(1)) ? "" : hssfRow.getCell(1).toString());
                    if (ValidateUtil.isEmpty(plan.getBddName())){
                        continue;
                    }
                    //log.info("plan.getBddName():{}:{}:{}", plan.getBddName(), rowNum, (ValidateUtil.isEmpty(hssfRow.getCell(2)) ? "0" : Double.toString(hssfRow.getCell(2).getNumericCellValue())));
                    plan.setBddBuildFee(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(2)) ? "0" : Double.toString(hssfRow.getCell(2).getNumericCellValue())).setScale(6, RoundingMode.HALF_UP));
                    plan.setBddInstallFee(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(3)) ? "0" : Double.toString(hssfRow.getCell(3).getNumericCellValue())).setScale(6, RoundingMode.HALF_UP));
                    plan.setBddDeviceFee(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(4)) ? "0" : Double.toString(hssfRow.getCell(4).getNumericCellValue())).setScale(6, RoundingMode.HALF_UP));
                    plan.setBddTotal(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(5)) ? "0" : Double.toString(hssfRow.getCell(5).getNumericCellValue())).setScale(6, RoundingMode.HALF_UP));
                    plan.setBddPrice(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(6)) ? "0" : Double.toString(hssfRow.getCell(6).getNumericCellValue())).setScale(6, RoundingMode.HALF_UP));
                    plan.setBddUnit(ValidateUtil.isEmpty(hssfRow.getCell(7)) ? "" : hssfRow.getCell(7).toString());
                    plan.setBddRemark(ValidateUtil.isEmpty(hssfRow.getCell(8)) ? "" : String.valueOf(new BigDecimal(Double.toString(hssfRow.getCell(8).getNumericCellValue())).setScale(6, RoundingMode.HALF_UP)));
                    list2.add(plan);
                }
            }
            String parentOldId=getParentIdByName("西南宿舍地块",list);
            log.info("parentOldId:{}",parentOldId);
            //log.info("list2:{}",JacksonUtils.toJSONString(list2));
            for (int i = 0; i < list2.size(); i++){
                InvBudgetDeclareDtlModel plan = list2.get(i);
                if (parentNodes.contains(plan.getBddNumber())){
                    plan.setBddParentId(parentOldId);
                }else if (childNodes.contains(plan.getBddNumber())){
                    List<InvBudgetDeclareDtlModel> listChild= list2.stream().filter(n -> n.getRowNum()!=null && n.getRowNum() < plan.getRowNum()
                            && parentNodes.contains(n.getBddNumber())).collect(Collectors.toList());
                    listChild.stream().max(Comparator.comparingInt(InvBudgetDeclareDtlModel::getRowNum)).ifPresent(parentPlan -> plan.setBddParentId(parentPlan.getBddId()));
                }else if (childNode2s.contains(plan.getBddNumber())){
                    List<InvBudgetDeclareDtlModel> listChild= list2.stream().filter(n -> n.getRowNum()!=null && n.getRowNum() < plan.getRowNum()
                            && childNodes.contains(n.getBddNumber())).collect(Collectors.toList());
                    listChild.stream().max(Comparator.comparingInt(InvBudgetDeclareDtlModel::getRowNum)).ifPresent(parentPlan -> plan.setBddParentId(parentPlan.getBddId()));
                }
            }
            for (int i = 0; i < list2.size(); i++){
                InvBudgetDeclareDtlModel plan = list2.get(i);
                plan.setChildNodes(list2.stream().filter(n->plan.getBddId()
                        .equals(n.getBddParentId())).collect(Collectors.toList()));
            }
            Integer index = null;
            for (InvBudgetDeclareDtlModel plan : list2) {
                if (plan.getBddNumber().contains(".")) {
                    String childNumber = plan.getBddNumber().substring(plan.getBddNumber().indexOf(".") + 1);

                    if (!childNumber.equals("0")) {
                        if (childNumber.equals("1")){
                            index =plan.getRowNum() - 1;
                            plan.setBddParentId(getParentIdByNumber(plan.getBddNumber().substring(0, plan.getBddNumber().lastIndexOf(".")) + ".0",
                                    list2, plan.getRowNum() - 1));
                        }else {
                            plan.setBddParentId(getParentIdByNumber(plan.getBddNumber().substring(0, plan.getBddNumber().lastIndexOf(".")) + ".0",
                                    list2, index));
                        }
                    }
                }
            }
            for (int i = 0; i < list2.size(); i++){
                InvBudgetDeclareDtlModel plan = list2.get(i);
                plan.setChildNodes(list2.stream().filter(n->plan.getBddId()
                        .equals(n.getBddParentId())).collect(Collectors.toList()));
            }
            list2=list2.stream().filter(d -> ValidateUtil.isEmpty(d.getBddParentId())
                    ||(ValidateUtil.isNotEmpty(d.getBddParentId()))&& parentOldId.equals(d.getBddParentId())).collect(Collectors.toList());
            log.info("西南宿舍地块:{}",JacksonUtils.toJSONString(list2));
            List<InvBudgetDeclareDtlModel> finalList = list2;
            setInvBudgetDeclareDtlModel(list, finalList,"西南宿舍地块");
        }else if ("综合概算表（南区宿舍地块、北区宿舍地块、垃圾转运站地块）".equals(sheetName)){
            String parentOldId=getParentIdByName("南区宿舍地块、北区宿舍地块、垃圾转运站地块",list);
            List<InvBudgetDeclareDtlModel> list2 = new ArrayList<>();
            for (int rowNum = 5; rowNum <= hssfSheet.getLastRowNum(); rowNum++){
                Row hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                    InvBudgetDeclareDtlModel plan = new InvBudgetDeclareDtlModel();
                    plan.setBddId(GenerateUUID.getUUID());
                    plan.setRowNum(rowNum);
                    plan.setBddNumber(ValidateUtil.isEmpty(hssfRow.getCell(0)) ? "" : hssfRow.getCell(0).toString());
                    plan.setBddName(ValidateUtil.isEmpty(hssfRow.getCell(1)) ? "" : hssfRow.getCell(1).toString());
                    if (ValidateUtil.isEmpty(plan.getBddName())){
                        continue;
                    }
                    plan.setBddBuildFee(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(2)) ? "0" : hssfRow.getCell(2).toString()).setScale(6, RoundingMode.HALF_UP));
                    plan.setBddInstallFee(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(3)) ? "0" : hssfRow.getCell(3).toString()).setScale(6, RoundingMode.HALF_UP));
                    plan.setBddDeviceFee(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(4)) ? "0" : hssfRow.getCell(4).toString()).setScale(6, RoundingMode.HALF_UP));
                    plan.setBddTotal(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(5)) ? "0" : hssfRow.getCell(5).toString()).setScale(6, RoundingMode.HALF_UP));
                    plan.setBddPrice(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(6)) ? "0" : hssfRow.getCell(6).toString()).setScale(6, RoundingMode.HALF_UP));
                    plan.setBddUnit(ValidateUtil.isEmpty(hssfRow.getCell(7)) ? "" : hssfRow.getCell(7).toString());
                    plan.setBddRemark(ValidateUtil.isEmpty(hssfRow.getCell(8)) ? "" : String.valueOf(new BigDecimal(Double.toString(hssfRow.getCell(8).getNumericCellValue())).setScale(6, RoundingMode.HALF_UP)));
                    list2.add(plan);
                }
            }
            for (int i = 0; i < list2.size(); i++){
                InvBudgetDeclareDtlModel plan = list2.get(i);
                if (parentNodes.contains(plan.getBddNumber())){
                    plan.setBddParentId(parentOldId);
                }else if (childNodes.contains(plan.getBddNumber())){
                    List<InvBudgetDeclareDtlModel> listChild= list2.stream().filter(n -> n.getRowNum()!=null && n.getRowNum() < plan.getRowNum()
                            && parentNodes.contains(n.getBddNumber())).collect(Collectors.toList());
                    listChild.stream().max(Comparator.comparingInt(InvBudgetDeclareDtlModel::getRowNum)).ifPresent(parentPlan -> plan.setBddParentId(parentPlan.getBddId()));
                }else if (childNode2s.contains(plan.getBddNumber())){
                   List<InvBudgetDeclareDtlModel> listChild= list2.stream().filter(n -> n.getRowNum()!=null && n.getRowNum() < plan.getRowNum()
                                    && childNodes.contains(n.getBddNumber())).collect(Collectors.toList());
                   listChild.stream().max(Comparator.comparingInt(InvBudgetDeclareDtlModel::getRowNum)).ifPresent(parentPlan -> plan.setBddParentId(parentPlan.getBddId()));
                }
            }
            for (int i = 0; i < list2.size(); i++){
                InvBudgetDeclareDtlModel plan = list2.get(i);
                plan.setChildNodes(list2.stream().filter(n->plan.getBddId()
                        .equals(n.getBddParentId())).collect(Collectors.toList()));
            }
            Integer index = null;
            for (InvBudgetDeclareDtlModel plan : list2) {
                if (plan.getBddNumber().contains(".")) {
                    String childNumber = plan.getBddNumber().substring(plan.getBddNumber().indexOf(".") + 1);
                    if (!childNumber.equals("0")) {
                        if (childNumber.equals("1")){
                            index =plan.getRowNum() - 1;
                            plan.setBddParentId(getParentIdByNumber(plan.getBddNumber().substring(0, plan.getBddNumber().lastIndexOf(".")) + ".0",
                                    list2, plan.getRowNum() - 1));
                        }else {
                            plan.setBddParentId(getParentIdByNumber(plan.getBddNumber().substring(0, plan.getBddNumber().lastIndexOf(".")) + ".0",
                                    list2, index));
                        }
                    }
                }
            }
            for (int i = 0; i < list2.size(); i++){
                InvBudgetDeclareDtlModel plan = list2.get(i);
                plan.setChildNodes(list2.stream().filter(n->plan.getBddId()
                        .equals(n.getBddParentId())).collect(Collectors.toList()));
            }
            list2=list2.stream().filter(d -> ValidateUtil.isEmpty(d.getBddParentId())
                    ||(ValidateUtil.isNotEmpty(d.getBddParentId()))&& parentOldId.equals(d.getBddParentId())).collect(Collectors.toList());
            log.info("综合概算表（南区宿舍地块、北区宿舍地块、垃圾转运站地块）:{}",JacksonUtils.toJSONString(list2));
            List<InvBudgetDeclareDtlModel> finalList = list2;
            setInvBudgetDeclareDtlModel(list, finalList,"南区宿舍地块、北区宿舍地块、垃圾转运站地块");
        }
    }

    private static List<String> getStringList() {
        List<String> childNodes = new ArrayList<>();
        childNodes.add("（一）");
        childNodes.add("（二）");
        childNodes.add("（三）");
        childNodes.add("（四）");
        childNodes.add("（五）");
        childNodes.add("（六）");
        childNodes.add("（七）");
        childNodes.add("（八）");
        childNodes.add("（九）");
        childNodes.add("（十）");
        childNodes.add("（十一）");
        childNodes.add("（十二）");
        childNodes.add("（十三）");
        childNodes.add("（十四）");
        childNodes.add("（十五）");
        childNodes.add("（十六）");
        childNodes.add("（十七）");
        childNodes.add("（十八）");
        return childNodes;
    }

    private static String getParentIdByName(String name, List<InvBudgetDeclareDtlModel> list) {
        String parentId = "";
        for (InvBudgetDeclareDtlModel plan : list){
            if (CollectionUtil.isNotEmpty(plan.getChildNodes())){
                parentId= getParentIdByName(name,plan.getChildNodes());
            }else {
                if (name.equals(plan.getBddName())){
                    parentId= plan.getBddId();
                }
            }
        }
        return parentId;
    }

    private static String getParentIdByNumber(String name, List<InvBudgetDeclareDtlModel> list,Integer row) {
        String parentId = null;
        log.info("getParentIdByNumber name:{};row:{}",name,row);
        for (InvBudgetDeclareDtlModel plan : list){
            if (CollectionUtil.isNotEmpty(plan.getChildNodes())){
                parentId= getParentIdByNumber(name,plan.getChildNodes(),row);
            }else {
                //log.info("getParentIdByNumber plan.getBddNumber:{};plan.getRowNum:{}",plan.getBddNumber(),plan.getRowNum());
                if (name.equals(plan.getBddNumber())&&plan.getRowNum()>=row){
                    parentId= plan.getBddId();
                }
            }
        }
        log.info("getParentIdByNumber parentId:{}",parentId);
        return parentId;
    }

    private static List<String> getStrings() {
        List<String> childNode2s = new ArrayList<>();
        childNode2s.add("1.0");
        childNode2s.add("2.0");
        childNode2s.add("3.0");
        childNode2s.add("4.0");
        childNode2s.add("5.0");
        childNode2s.add("6.0");
        childNode2s.add("7.0");
        childNode2s.add("8.0");
        childNode2s.add("9.0");
        childNode2s.add("10.0");
        childNode2s.add("11.0");
        childNode2s.add("12.0");
        childNode2s.add("13.0");
        childNode2s.add("14.0");
        childNode2s.add("15.0");
        childNode2s.add("16.0");
        childNode2s.add("17.0");
        childNode2s.add("18.0");
        childNode2s.add("19.0");
        childNode2s.add("20.0");
        return childNode2s;
    }

    private static void setInvBudgetDeclareDtlModel(List<InvBudgetDeclareDtlModel> list, List<InvBudgetDeclareDtlModel> finalList,String str) {
        for (InvBudgetDeclareDtlModel plan : list){
            if (CollectionUtil.isNotEmpty(plan.getChildNodes())){
                setInvBudgetDeclareDtlChildModel(plan.getChildNodes(), finalList,str);
            }else {
                if (str.equals(plan.getBddName())){
                    plan.setChildNodes(finalList);
                }
            }
        }
    }

    private static void setInvBudgetDeclareDtlChildModel(List<InvBudgetDeclareDtlModel> childNodes, List<InvBudgetDeclareDtlModel> finalList, String str) {
        for (InvBudgetDeclareDtlModel plan : childNodes){
            if (CollectionUtil.isNotEmpty(plan.getChildNodes())){
                setInvBudgetDeclareDtlChildModel(plan.getChildNodes(), finalList,str);
            }else {
                if (str.equals(plan.getBddName())){
                    plan.setChildNodes(finalList);
                }
            }
        }
    }

    //解析总表信息
    private static void getTotalEstimate(Sheet hssfSheet, List<InvBudgetDeclareDtlModel> list) {
        List<InvBudgetDeclareDtlModel> list1 = new ArrayList<>();
        for (int rowNum = 4; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
            Row hssfRow = hssfSheet.getRow(rowNum);
            if (hssfRow != null) {
                InvBudgetDeclareDtlModel plan = new InvBudgetDeclareDtlModel();
                plan.setBddId(GenerateUUID.getUUID());
                plan.setRowNum(rowNum);
                plan.setBddNumber(ValidateUtil.isEmpty(hssfRow.getCell(0)) ? "" : hssfRow.getCell(0).toString());
                plan.setBddName(ValidateUtil.isEmpty(hssfRow.getCell(1)) ? "" : hssfRow.getCell(1).toString());
                if (ValidateUtil.isEmpty(plan.getBddName())){
                    continue;
                }
                plan.setBddBuildFee(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(2)) ? "0" : hssfRow.getCell(2).toString()).setScale(6, RoundingMode.HALF_UP));
                plan.setBddInstallFee(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(3)) ? "0" : hssfRow.getCell(3).toString()).setScale(6, RoundingMode.HALF_UP));
                plan.setBddDeviceFee(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(4)) ? "0" : hssfRow.getCell(4).toString()).setScale(6, RoundingMode.HALF_UP));
                plan.setBddOtherFee(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(5)) ? "0" : hssfRow.getCell(5).toString()).setScale(6, RoundingMode.HALF_UP));
                plan.setReserveCost(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(6)) ? "0" : hssfRow.getCell(6).toString()).setScale(6, RoundingMode.HALF_UP));
                plan.setBddTotal(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(7)) ? "0" : hssfRow.getCell(7).toString()).setScale(6, RoundingMode.HALF_UP));
                plan.setBddPrice(new BigDecimal(ValidateUtil.isEmpty(hssfRow.getCell(8)) ? "0" : hssfRow.getCell(8).toString()).setScale(6, RoundingMode.HALF_UP));
                plan.setBddCount(new Float(ValidateUtil.isEmpty(hssfRow.getCell(9)) ? "0" : hssfRow.getCell(9).toString()));
                list1.add(plan);
            }
        }
        log.info("总概算表:{}", JacksonUtils.toJSONString(list1));
        //获取父节点
        for (int i = 0; i < list1.size(); i++){
            InvBudgetDeclareDtlModel plan = list1.get(i);
            List<String> parentNodes = new ArrayList<>();
            parentNodes.add("一");
            parentNodes.add("二");
            parentNodes.add("三");
            List<String> childNodes = new ArrayList<>();
            childNodes.add("1.0");
            childNodes.add("2.0");
            childNodes.add("3.0");
            if (parentNodes.contains(plan.getBddNumber())){
                plan.setBddParentId("0");
            }else if (childNodes.contains(plan.getBddNumber())){
                list1.stream().filter(n -> n.getRowNum() < plan.getRowNum()
                                && parentNodes.contains(n.getBddNumber()))
                        .findFirst().ifPresent(parentPlan -> plan.setBddParentId(parentPlan.getBddId()));
            }
        }
        //构建树形结构
        for (int i = 0; i < list1.size(); i++){
            InvBudgetDeclareDtlModel plan = list1.get(i);
            if ("0".equals(plan.getBddParentId())){
                plan.setChildNodes(list1.stream().filter(n->plan.getBddId()
                        .equals(n.getBddParentId())).collect(Collectors.toList()));
                list.add(plan);
            }
        }
    }
}
