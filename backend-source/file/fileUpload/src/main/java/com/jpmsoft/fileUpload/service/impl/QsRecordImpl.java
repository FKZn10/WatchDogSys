package com.jpmsoft.fileUpload.service.impl;

import cn.hutool.core.date.DatePattern;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.Qs.QsRecordDutyDTO;
import com.jpmsoft.common.model.request.Qs.QualitySafetyStatisticFowYWRequest;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.constants.FileMsg;
import com.jpmsoft.fileUpload.feign.PmFeign;
import com.jpmsoft.fileUpload.feign.UserFeign;
import com.jpmsoft.fileUpload.mapper.QsRecordMapper;
import com.jpmsoft.fileUpload.pojo.dto.*;
import com.jpmsoft.fileUpload.pojo.entity.QsRecord;
import com.jpmsoft.fileUpload.pojo.entity.SysDictionary;
import com.jpmsoft.fileUpload.pojo.entity.SysFileAnnex;
import com.jpmsoft.fileUpload.pojo.vo.QsPlanTaskExportVo;
import com.jpmsoft.fileUpload.pojo.vo.QsPlanTaskVo;
import com.jpmsoft.fileUpload.pojo.vo.QsRecordVo;
import com.jpmsoft.fileUpload.pojo.vo.QualitySafetyStatisticFowYWVO;
import com.jpmsoft.fileUpload.service.IQsRecordService;
import com.jpmsoft.fileUpload.service.ISysConfigService;
import com.jpmsoft.fileUpload.service.ISysDictionaryService;
import com.jpmsoft.fileUpload.service.ISysFileAnnexService;
import com.jpmsoft.fileUpload.utils.DateUtil;
import com.jpmsoft.fileUpload.utils.FileUtil;
import com.jpmsoft.fileUpload.utils.MathUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/2/4 11:21
 */
@Service
@Slf4j
public class QsRecordImpl extends ServiceImpl<QsRecordMapper, QsRecord> implements IQsRecordService {

    @Resource
    private ISysDictionaryService sysDictionaryService;
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private PmFeign pmFeign;
    @Value("${file.upload.dirPath}")
    private String dirPath;

    @Resource
    private UserFeign userFeign;


    /**
     * 头部
     */
    private final static List<String> tops = Arrays.asList("序号", "项目", "编码", "检查日期"
            , "可能导致事故原因", "安全隐患形成原因", "问题描述", "所在部位"
            , "整改前图片", "责任单位", "责任人", "整改期限"
            , "整改描述", "整改后图片");


    @Override
    public List<QsRecordExcelDTO> selectExcelData(QsRecordExcelDTO request) {
        if (request == null) return null;
        Map<String, Object> params = new HashMap<>();
        if (!CollectionUtils.isEmpty(request.getPlanTaskIds()))
            params.put("planTaskIds", request.getPlanTaskIds());
        if (!CollectionUtils.isEmpty(request.getQsrIds()))
            params.put("qsrIds", request.getQsrIds());
        return baseMapper.selectExcelData(params);
    }

    @Override
    public void exportQsRecordExcel(HttpServletResponse res, QsRecordExcelDTO request) {
        List<QsRecordExcelDTO> data = selectExcelData(request);
        if (!CollectionUtils.isEmpty(data)) {
            String root = sysConfigService.getRootDirectory();
            String archive = sysConfigService.getArchiveDirectory();
            if (ValidateUtil.isAllEmpty(root, archive)) throw new JPMException(FileMsg.missSysConfig);
            List<SysDictionary> AQYHYY = sysDictionaryService.getDictionaryByParentCode("JPMsoft_AQYHYY");
            if (CollectionUtils.isEmpty(AQYHYY))
                throw new JPMException(String.format(FileMsg.notFoundDictionary, "JPMsoft_AQYHYY"));
            List<SysDictionary> ZLWTYYLX = sysDictionaryService.getDictionaryByParentCode("JPMsoft_ZLWTYYLX");
            if (CollectionUtils.isEmpty(ZLWTYYLX))
                throw new JPMException(String.format(FileMsg.notFoundDictionary, "JPMsoft_ZLWTYYLX"));
            List<SysDictionary> rectifyStatus = sysDictionaryService.getDictionaryByParentCode("JPMsoft_ZGZT");
            if (CollectionUtils.isEmpty(rectifyStatus))
                throw new JPMException(String.format(FileMsg.notFoundDictionary, "JPMsoft_ZGZT"));
            List<String> relationIds = new ArrayList<>();
            List<String> qsrIds = new ArrayList<>();
            data.forEach(d -> {
                if (ValidateUtil.isNotEmpty(d.getQsrPic())) relationIds.add(d.getQsrPic());
                if (ValidateUtil.isNotEmpty(d.getQsrRectifyPic())) relationIds.add(d.getQsrRectifyPic());
                qsrIds.add(d.getQsrId());
            });
            List<SysFileAnnex> files = sysFileAnnexService.getFileByRelationIds(relationIds);
            List<QsRecordDutyDTO> dutys = pmFeign.getQsRecordDutyByQsrIds(qsrIds);
            data.forEach(d -> {
                //替换字典编码为文本信息
                d.setQsrFormation(getDicString(AQYHYY, d.getPtType()));
                d.setQsrReasonType(getDicString(ZLWTYYLX, d.getPtMeasure()));
//                d.setQsrRectifyStatus(getDicString(rectifyStatus, d.getQsrRectifyStatus()));
                //替换图片
                if (!CollectionUtils.isEmpty(files)) {
                    d.setQsrPic(getFilePath(files, root, archive, d.getQsrPic()));
                    d.setQsrRectifyPic(getFilePath(files, root, archive, d.getQsrRectifyPic()));
                }
                List<QsRecordDutyDTO> qrd = dutys.stream().filter(f -> f.getQsrId().equals(d.getQsrId())).collect(Collectors.toList());
                d.setQsRecordDutys(qrd);
                if (!CollectionUtils.isEmpty(qrd)) {
                    StringBuilder cpyNames = new StringBuilder();
                    StringBuilder userNames = new StringBuilder();
                    for (QsRecordDutyDTO q : qrd) {
                        if (ValidateUtil.isNotEmpty(q.getQrdCpyName())) {
                            if (ValidateUtil.isEmpty(cpyNames))
                                cpyNames.append(q.getQrdCpyName());
                            else cpyNames.append(",").append(q.getQrdCpyName());
                        }
                        if (ValidateUtil.isNotEmpty(q.getQrdUserName())) {
                            if (ValidateUtil.isEmpty(userNames))
                                userNames.append(q.getQrdUserName());
                            else userNames.append(",").append(q.getQrdUserName());
                        }
                    }
                    d.setQrdUserNames(userNames.toString());
                    d.setQrdCpyNames(cpyNames.toString());
                }
            });
            try {
                String name = GenerateUUID.getUUID() + ".xlsx";
                String tempExcel = root + "/" + dirPath + "/JPBPMTemporaryFolder/" + name;
                exportExcel(data, tempExcel, root, dirPath);
                FileUtil.downFileAndDelFile(res, tempExcel, name);
            } catch (IOException exception) {
                exception.printStackTrace();
                log.error("导出excel失败！" + exception.getMessage(),exception);
            }
        }
    }
    @Override
    public ResponseEntity<byte[]> getQsPlanTaskListExport(QsPlanTaskDto request, String token, HttpServletResponse response) {
        try{
            BaseResponse qsPlanTaskListExport = pmFeign.getQsPlanTaskListExport(request, token);
            List<QsPlanTaskVo> models = JacksonUtils.parseArray(JacksonUtils.toJSONString(qsPlanTaskListExport.getData()), QsPlanTaskVo.class);
            SelectDto selectDto = new SelectDto();
            List<String> codes = new ArrayList<>();
            codes.add("JPMsoft_XJLX");  //检查类型
            codes.add("JPMsoft_LCZT"); //流程状态
            selectDto.setCodes(codes);
            BaseResponse dictionaryCodes = userFeign.getDictionaryTreeByCodes(selectDto);
            LinkedHashMap<String, List<LinkedHashMap<String, Object>>> originalData = (LinkedHashMap<String, List<LinkedHashMap<String, Object>>>) dictionaryCodes.getData();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            List<QsPlanTaskExportVo> data = models.parallelStream().map(m-> {
                QsPlanTaskExportVo vo  = new QsPlanTaskExportVo();
                BeanUtils.copyProperties(m,vo);
                vo.setStateName(getValueByTitleCli(originalData.get("JPMsoft_LCZT"),String.valueOf(vo.getState())));
                vo.setPtType(getValueByTitleCli(originalData.get("JPMsoft_XJLX"),String.valueOf(vo.getPtType())));
                vo.setPtEvaluationDate(cn.hutool.core.date.DateUtil.format(vo.getPtEvaluationTime(), DatePattern.NORM_DATE_FORMAT));
                vo.setQsReplyRatelv(MathUtil.DecimalMultiplicationInt(vo.getQsReplyRate(), 100) + "%");
                vo.setQsFinishRatelv(MathUtil.DecimalMultiplicationInt(vo.getQsFinishRate(), 100) + "%");
                vo.setPtRectifyActualTimeDate(vo.getPtRectifyActualTime() == null ? "" : cn.hutool.core.date.DateUtil.format(vo.getPtRectifyActualTime(), DatePattern.NORM_DATE_FORMAT));
                vo.setPtRectifyRequireTimeDate(vo.getPtRectifyRequireTime() == null ? "" : cn.hutool.core.date.DateUtil.format(vo.getPtRectifyRequireTime(), DatePattern.NORM_DATE_FORMAT));
                return  vo;
            }).collect(Collectors.toList());
            WriteCellStyle headStyle = new WriteCellStyle();
            WriteFont headFont = new WriteFont();
            headFont.setFontHeightInPoints((short) 12);
            headFont.setFontName("Arial");
            headStyle.setWriteFont(headFont);

            WriteCellStyle contentStyle = new WriteCellStyle();
            WriteFont contentFont = new WriteFont();
            contentFont.setFontHeightInPoints((short) 10);
            contentFont.setFontName("Arial");
            contentStyle.setWriteFont(contentFont);

            HorizontalCellStyleStrategy styleStrategy = new HorizontalCellStyleStrategy(headStyle, contentStyle);


            EasyExcel.write(out, QsPlanTaskExportVo.class)
                    .registerWriteHandler(styleStrategy)
                    .registerWriteHandler(getHorizontalCellStyleStrategy((short)12))
                    .sheet("QsPlanTaskExportVo")
                    .doWrite(data);

            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=zlaq.xlsx");

            response.getOutputStream().write(out.toByteArray());
            response.getOutputStream().flush();
            response.getOutputStream().close();

            return new ResponseEntity<>(out.toByteArray(), HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity<byte[]> getQualitySafetyListExport(QualitySafetyStatisticFowYWRequest request, HttpServletResponse response) {
        try{
        List<QualitySafetyStatisticFowYWVO> dataList = pmFeign.getQualitySafetyStatisticFowYWExport(request);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
            WriteCellStyle headStyle = new WriteCellStyle();
            WriteFont headFont = new WriteFont();
            headFont.setFontHeightInPoints((short) 12);
            headFont.setFontName("Arial");
            headStyle.setWriteFont(headFont);
            WriteCellStyle contentStyle = new WriteCellStyle();
            WriteFont contentFont = new WriteFont();
            contentFont.setFontHeightInPoints((short) 10);
            contentFont.setFontName("Arial");
            contentStyle.setWriteFont(contentFont);

            HorizontalCellStyleStrategy styleStrategy = new HorizontalCellStyleStrategy(headStyle, contentStyle);


            EasyExcel.write(out, QualitySafetyStatisticFowYWVO.class)
                    .registerWriteHandler(styleStrategy)
                    .registerWriteHandler(getHorizontalCellStyleStrategy((short)12))
                    .sheet("QualitySafetyStatisticFowYWVO")
                    .doWrite(dataList);

            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=wtmstj.xlsx");

            response.getOutputStream().write(out.toByteArray());
            response.getOutputStream().flush();
            response.getOutputStream().close();

            return new ResponseEntity<>(out.toByteArray(), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<byte[]> getProSafetyLedgerPageYWExport(ProSafetyLedgerDtlDTO request, String token,HttpServletResponse response) {
        try{
            List<ProSafetyLedgerDtlDTO> dataList = pmFeign.getProSafetyLedgerPageYWExportNew(request,token);
            AtomicInteger index = new AtomicInteger(1); // 初始化序号为 1
            SelectDto selectDto = new SelectDto();
            List<String> codes = new ArrayList<>();
            codes.add("JPMsoft_XMLB"); //专业类型
            codes.add("JPMsoft_XMStatus");  //工程进展
            codes.add("JPMsoft_XJLX"); //检查类型
            selectDto.setCodes(codes);
            BaseResponse dictionaryCodes = userFeign.getDictionaryTreeByCodes(selectDto);
            LinkedHashMap<String, List<LinkedHashMap<String, Object>>> originalData = (LinkedHashMap<String, List<LinkedHashMap<String, Object>>>) dictionaryCodes.getData();
           List<ProSafetyLedgerExportDTO> proList =  dataList.stream().map(e->{
                ProSafetyLedgerExportDTO dto = new ProSafetyLedgerExportDTO();
                BeanUtils.copyProperties(e,dto);
                dto.setIndex(index.getAndIncrement());
                if(ValidateUtil.isNotNull(dto.getPType())){
                    dto.setPType(getValueByTitleCli(originalData.get("JPMsoft_XMLB"),String.valueOf(dto.getPType())));
                }else{
                    dto.setPType("");
                }
               if(ValidateUtil.isNotNull(dto.getPProjectStatus())){
                   dto.setPProjectStatus(getValueByTitleCli(originalData.get("JPMsoft_XMStatus"),String.valueOf(dto.getPProjectStatus())));
               }else{
                   dto.setPProjectStatus("");
               }
//               if(ValidateUtil.isNotNull(dto.getPtType())){
//                   dto.setPtType(getValueByTitleCli(originalData.get("JPMsoft_XJLX"),String.valueOf(dto.getPtType())));
//               }else{
//                   dto.setPtType("");
//               }
               if(ValidateUtil.isNotNull(dto.getPInvTotal())){
                   dto.setPInvTotal(MathUtil.DecimalDivisionInt(dto.getPInvTotal(),10000));
               }
               if(ValidateUtil.isNotNull(dto.getConstructionConMoney())){
                   dto.setConstructionConMoney(MathUtil.DecimalDivisionInt(dto.getConstructionConMoney(),10000));
               }
               if(ValidateUtil.isNotNull(dto.getQsFinishRate())){
                   dto.setQsFinishRateStr(dto.getQsFinishRate().multiply(new BigDecimal(100)) + "%");
               }
               if(ValidateUtil.isNotNull(e.getQsFinishOverdueCount())){
                   dto.setQsFinishOverdueCountStr(String.valueOf(e.getQsFinishOverdueCount()));
               }
               if(ValidateUtil.isNotNull(e.getQsFinishOnTimeCount())){
                   dto.setQsFinishOnTimeCountStr(String.valueOf(e.getQsFinishOnTimeCount()));
               }
               if(ValidateUtil.isNotNull(e.getQsFinishOverdueRate())){
                   dto.setQsFinishOverdueRateStr(e.getQsFinishOverdueRate() + "%");
               }
               if(ValidateUtil.isNotNull(e.getQsFinishOnTimeRate())){
                   dto.setQsFinishOnTimeRateStr(e.getQsFinishOnTimeRate() + "%");
               }
               return dto;
            }).collect(Collectors.toList());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            WriteCellStyle headStyle = new WriteCellStyle();
            WriteFont headFont = new WriteFont();
            headFont.setFontHeightInPoints((short) 12);
            headFont.setFontName("Arial");
            headStyle.setWriteFont(headFont);

            WriteCellStyle contentStyle = new WriteCellStyle();
            WriteFont contentFont = new WriteFont();
            contentFont.setFontHeightInPoints((short) 10);
            contentFont.setFontName("Arial");
            contentStyle.setWriteFont(contentFont);

            HorizontalCellStyleStrategy styleStrategy = new HorizontalCellStyleStrategy(headStyle, contentStyle);


            EasyExcel.write(out, ProSafetyLedgerExportDTO.class)
                    .registerWriteHandler(styleStrategy)
                    .registerWriteHandler(getHorizontalCellStyleStrategy((short)12))
                    .sheet("ProSafetyLedgerExportDTO")
                    .doWrite(proList);

            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=xjtj.xlsx");

            response.getOutputStream().write(out.toByteArray());
            response.getOutputStream().flush();
            response.getOutputStream().close();

            return new ResponseEntity<>(out.toByteArray(), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<byte[]> getQsRecordYwExport(QsRecordVo request, HttpServletResponse response) {

        try{
            if(request.getPtEvaluationTimes() != null && request.getPtEvaluationTimes().size() > 0  ){
                if( request.getPtEvaluationTimes().get(0) != null && !request.getPtEvaluationTimes().get(0).equals("")){
                    request.setPtEvaluationTimeStr(request.getPtEvaluationTimes().get(0) );
                }
                if(request.getPtEvaluationTimes().get(1) != null  && !request.getPtEvaluationTimes().get(1).equals("")){
                    request.setPtEvaluationTimeEnd(request.getPtEvaluationTimes().get(1));
                }
            }
            List<QsRecordVo> dataList =  baseMapper.getQsRecordYwExport(request);
            AtomicInteger index = new AtomicInteger(1); // 初始化序号为 1
            SelectDto selectDto = new SelectDto();
            List<String> codes = new ArrayList<>();
            codes.add("JPMsoft_ZGZT");
            codes.add("JPMsoft_XJLX"); //检查类型
            codes.add("JPMsoft_LCZT"); //流程状态
            selectDto.setCodes(codes);
            BaseResponse dictionaryCodes = userFeign.getDictionaryTreeByCodes(selectDto);
            LinkedHashMap<String, List<LinkedHashMap<String, Object>>> originalData = (LinkedHashMap<String, List<LinkedHashMap<String, Object>>>) dictionaryCodes.getData();
            dataList.stream().forEach(e->{
                e.setIndex(index.getAndIncrement());
                if(ValidateUtil.isNotNull(e.getPtType())){
                    e.setPtType(getValueByTitleCli(originalData.get("JPMsoft_XJLX"),String.valueOf(e.getPtType())));
                }else{
                    e.setPtType("");
                }
                if(ValidateUtil.isNotNull(e.getState())){
                    e.setStateName(getValueByTitleCli(originalData.get("JPMsoft_LCZT"),String.valueOf(e.getState())));
                }else{
                    e.setStateName("");
                }
                if(ValidateUtil.isNotNull(e.getQsrRectifyStatus())){
                    e.setQsrRectifyStatuss(getValueByTitleCli(originalData.get("JPMsoft_ZGZT"),String.valueOf(e.getQsrRectifyStatus())));
                }else{
                    e.setQsrRectifyStatuss("");
                }
                String ptEvaluationTime =  cn.hutool.core.date.DateUtil.format(e.getPtEvaluationTime(),"yyyy-MM-dd");
                if(ValidateUtil.isNotEmpty(ptEvaluationTime)){
                    e.setPtEvaluationTimep(ptEvaluationTime);
                }else{
                    e.setPtEvaluationTimep("");
                }
                String qsrRectifyRequireTime =  cn.hutool.core.date.DateUtil.format(e.getQsrRectifyRequireTime(),"yyyy-MM-dd");
                if(ValidateUtil.isNotEmpty(qsrRectifyRequireTime)){
                    e.setQsrRectifyRequireTimep(qsrRectifyRequireTime);
                }else{
                    e.setQsrRectifyRequireTimep("");
                }
            });

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            WriteCellStyle headStyle = new WriteCellStyle();
            WriteFont headFont = new WriteFont();
            headFont.setFontHeightInPoints((short) 12);
            headFont.setFontName("Arial");
            headStyle.setWriteFont(headFont);

            WriteCellStyle contentStyle = new WriteCellStyle();
            WriteFont contentFont = new WriteFont();
            contentFont.setFontHeightInPoints((short) 10);
            contentFont.setFontName("Arial");
            contentStyle.setWriteFont(contentFont);

            HorizontalCellStyleStrategy styleStrategy = new HorizontalCellStyleStrategy(headStyle, contentStyle);


            EasyExcel.write(out, QsRecordVo.class)
                    .registerWriteHandler(styleStrategy)
                    .registerWriteHandler(getHorizontalCellStyleStrategy((short)12))
                    .sheet("QsRecordVo")
                    .doWrite(dataList);

            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=log.xlsx");

            response.getOutputStream().write(out.toByteArray());
            response.getOutputStream().flush();
            response.getOutputStream().close();

            return new ResponseEntity<>(out.toByteArray(), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String getFilePath(List<SysFileAnnex> files, String root, String archive, String relationId) {
        List<SysFileAnnex> file = files.stream().filter(f -> f.getFileRelationId().equals(relationId)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(file)) {
            SysFileAnnex f = file.stream().findFirst().get();
            String path = "";
            if (f.getFileLocation() == 0) path = root;
            else path = archive;
            if (path.endsWith("/") || f.getFilePath().startsWith("/")) return path + f.getFilePath();
            else return path + "/" + f.getFilePath();
        }
        return null;
    }

    private String getDicString(List<SysDictionary> dics, String value) {
        if (ValidateUtil.isNotEmpty(value)) {
            List<String> codes = Arrays.asList(value.split(","));
            List<SysDictionary> dic = dics.stream().filter(f -> codes.contains(f.getDCode())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(dic)) {
                List<String> data = dic.stream().map(SysDictionary::getDName).collect(Collectors.toList());
                return String.join(",", data);
            }
        }
        return null;
    }


    public static void exportExcel(List<QsRecordExcelDTO> data, String excelPath, String root, String dirPath) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 创建工作表
        XSSFSheet sheet = workbook.createSheet("Sheet1");
        // 创建第一行
        Row headerRow = sheet.createRow(0);
        // 创建字体对象
        Font font = workbook.createFont();
        font.setBold(true); // 加粗字体
        // 创建单元格样式对象，并将字体应用于单元格样式
        CellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderTop(BorderStyle.THIN); // 设置上边框样式
        style.setBorderBottom(BorderStyle.THIN); // 设置下边框样式
        style.setBorderLeft(BorderStyle.THIN); // 设置左边框样式
        style.setBorderRight(BorderStyle.THIN); // 设置右边框样式
        for (int i = 0; i < tops.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(tops.get(i));
            cell.setCellStyle(style);
            if (i != 8 && i != 13)//跳过图片设置宽度
                sheet.setColumnWidth(cell.getColumnIndex(), tops.get(i).length() * 6 * 256);
        }
        //sheet.autoSizeColumn(0);
        int num = 1;
        for (int i = 0; i < data.size(); i++) {
            QsRecordExcelDTO qs = data.get(i);
            XSSFRow row = sheet.createRow(i + 1);
            // 巡检任务
            cetCellWithValue(sheet, row, 0, num + "");//序号
            cetCellWithValue(sheet, row, 1, qs.getPName());//项目名称
            cetCellWithValue(sheet, row, 2, qs.getPtCode());//编码
            cetCellWithValue(sheet, row, 3, DateUtil.Date2String(qs.getPtEvaluationTime(), "yyyy-MM-dd"));//检查时间
            cetCellWithValue(sheet, row, 4, qs.getQsrReasonType());//可能导致事故原因
            cetCellWithValue(sheet, row, 5, qs.getQsrFormation());//安全隐患形成原因
            cetCellWithValue(sheet, row, 6, qs.getQsrDes());//问题描述
            cetCellWithValue(sheet, row, 7, qs.getQsrLocation());//所在部位
            setImage(workbook, sheet, row, 8, qs.getQsrPic(), root, dirPath);//问题图片
            cetCellWithValue(sheet, row, 9, qs.getQrdCpyNames());//责任单位
            cetCellWithValue(sheet, row, 10, qs.getQrdUserNames());//责任人
            cetCellWithValue(sheet, row, 11, DateUtil.Date2String(qs.getPtRectifyRequireTime(), "yyyy-MM-dd"));//要求整改完成时间
            cetCellWithValue(sheet, row, 12, qs.getQsrRectifyReply());//整改描述
            setImage(workbook, sheet, row, 13, qs.getQsrRectifyPic(), root, dirPath);//整改图片
            num += 1;
            // sheet.autoSizeColumn(i);
        }
        try (FileOutputStream outputStream = new FileOutputStream(excelPath)) {
            workbook.write(outputStream);
        }
    }

    public static HorizontalCellStyleStrategy getHorizontalCellStyleStrategy(Short fontHeightInPoints) {
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 设置边框
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        contentWriteCellStyle.setBorderTop(BorderStyle.NONE);

        // 配置字体
        WriteFont contentWriteFont = new WriteFont();
        // 字体
        contentWriteFont.setFontName("宋体");
        // 字体大小
        contentWriteFont.setFontHeightInPoints(fontHeightInPoints);
        // 设置加粗
        contentWriteFont.setBold(false);
        contentWriteCellStyle.setWriteFont(contentWriteFont);

        // 设置文字左右居中
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 设置文字上下居中
//        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.LEFT);
        // 设置 自动换行
        contentWriteCellStyle.setWrapped(true);

        // 样式策略
        return new HorizontalCellStyleStrategy(null, contentWriteCellStyle);
    }

    public static String getValueByTitleCli(List<LinkedHashMap<String, Object>> dataList, String title) {
        if (dataList != null) {
            for (LinkedHashMap<String, Object> item : dataList) {
                // 检查当前层级的 "value" 是否与要找的值相等
                if(ValidateUtil.isNotNull(title)){
                    if(ValidateUtil.isNotNull(item.get("value"))){
                        if (title.equals(item.get("value"))) {
                            return String.valueOf(item.get("title"));
                        }
                    }
                }
                // 如果当前项有 "children" 并且是 List 类型，则递归检查
                Object childrenObj = item.get("children");
                if (ValidateUtil.isNotNull(childrenObj) && childrenObj instanceof List) {
                    @SuppressWarnings("unchecked")
                    List<LinkedHashMap<String, Object>> children = (List<LinkedHashMap<String, Object>>) childrenObj;
                    // 递归查找子项
                    String result = getValueByTitleCli(children, title);
                    if (result != null && !result.equals(title)) {
                        return result;
                    }
                }
            }
        }
        return title;
    }



    private static void cetCellWithValue(XSSFSheet sheet, XSSFRow row, int index, String value) {
        XSSFCell cell = row.createCell(index);
        cell.setCellValue(value);
    }

    private static void setImage(XSSFWorkbook workbook, XSSFSheet sheet, XSSFRow row, int cellInx, String value, String root, String dirPath) throws IOException {
        if (ValidateUtil.isNotEmpty(value)) {
            String f = value.toLowerCase(Locale.ENGLISH);
            int pictureType = f.endsWith("jpg") || f.endsWith("jpeg") ? XSSFWorkbook.PICTURE_TYPE_JPEG : XSSFWorkbook.PICTURE_TYPE_PNG;
            XSSFCell cell = row.createCell(cellInx);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(" ");
            Double widthUnit = (26.65d * 256d);
            sheet.setColumnWidth(cell.getColumnIndex(), widthUnit.intValue());
            short heightUnit = 123;
            row.setHeightInPoints(heightUnit);
            File file = new File(value);
            File img = formatImg(file, root, dirPath);
            // 将图片保存到文件系统中的临时文件
            byte[] bytes = IOUtils.toByteArray(new FileInputStream(img));
            // 将图片添加到工作簿中作为字节数组的 Base64 编码字符串
            workbook.addPicture(bytes, pictureType);
            // 将图片插入到单元格中，设置图片的尺寸和位置等属性（可选）
            int pictureIdx = workbook.addPicture(bytes, pictureType);
            XSSFCreationHelper helper = workbook.getCreationHelper();
            XSSFDrawing drawing = sheet.createDrawingPatriarch();
            XSSFClientAnchor anchor = helper.createClientAnchor();
            anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
            anchor.setCol1(cell.getColumnIndex());
            anchor.setRow1(cell.getRowIndex());
            XSSFPicture pict = drawing.createPicture(anchor, pictureIdx);
//            // 计算单元格的长宽
//            double cellWidth = sheet.getColumnWidthInPixels(cell.getColumnIndex());
//            double cellHeight = cell.getRow().getHeightInPoints() / 72 * 90;
            // 计算需要的长宽比例的系数
            double a = 1.0019676788907863;
            double b = 0.9873853211009174;
            pict.resize(a, b); // 自动调整图片大小以适应单元格
            deleteImg(file, img);
        }
    }

    /**
     * 格式化图片大小
     *
     * @param img
     * @return
     * @throws IOException
     */
    private static File formatImg(File img, String root, String dirPath) throws IOException {
        BufferedImage image = ImageIO.read(img);
        // 指定我想要的长宽
        int width = image.getWidth();
        int height = image.getHeight();
        if (width == 581 && height == 436) return img;
        // 创建一个新的BufferedImage对象，指定调整后的宽度和高度
//        BufferedImage resizedImage = new BufferedImage(581, 436, BufferedImage.TYPE_INT_RGB);
//        Graphics2D g2d = resizedImage.createGraphics();
//        g2d.setComposite(AlphaComposite.Clear);
//        g2d.fillRect(0, 0, 581, 436);
//        g2d.dispose();
//        g2d = resizedImage.createGraphics();
//        // 将原始图片绘制到新的BufferedImage对象上，并调整大小
//        g2d.drawImage(image, 0, 0, 581, 436, null);
//        g2d.dispose();
        // 调整图片大小
        BufferedImage resizedImage = resizeImage(image, 581, 436);
        // 将调整后的图片保存到文件
        String tempExcel = root + "/" + dirPath + "/JPBPMTemporaryFolder/" + GenerateUUID.getUUID() + ".png";
        File outputfile = new File(tempExcel);
        ImageIO.write(resizedImage, "png", outputfile);
        return outputfile;
    }

    private static void deleteImg(File file, File img) throws IOException {
        BufferedImage image = ImageIO.read(file);
        // 指定我想要的长宽
        int width = image.getWidth();
        int height = image.getHeight();
        if (width != 581 || height != 436) img.delete();
    }

    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, originalImage.getType());
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(resultingImage, 0, 0, null);
        g2d.dispose();
        return outputImage;
    }



}