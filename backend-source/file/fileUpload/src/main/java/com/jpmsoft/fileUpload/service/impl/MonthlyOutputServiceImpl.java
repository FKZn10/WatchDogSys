package com.jpmsoft.fileUpload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.response.Contract.ConContractResponse;
import com.jpmsoft.common.model.response.FileTemplateTree;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.feign.PmFeign;
import com.jpmsoft.fileUpload.mapper.MonthlyOutputMapper;
import com.jpmsoft.fileUpload.pojo.entity.MonthlyOutput;
import com.jpmsoft.fileUpload.pojo.entity.PreProject;
import com.jpmsoft.fileUpload.pojo.entity.SysFileTemplate;
import com.jpmsoft.fileUpload.service.*;
import com.jpmsoft.fileUpload.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

// todo 向sys_file 插入数据时，没有 tId title code sectionId 刷新或新增表单附件 传递的字段有误

@Slf4j
@Service
public class MonthlyOutputServiceImpl extends ServiceImpl<MonthlyOutputMapper, MonthlyOutput> implements IMonthlyOutputService {

//    @Resource
//    private IInvPlanYearService invPlanYearService;
//    @Resource
//    private IInvMonthlyScheduleService invMonthlyScheduleService;

    @Resource
    private IPreProjectService projectService;

    @Resource
    private ISysFileService sysFileService;

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private PmFeign pmFeign;

    @Resource
    private ISysFileTemplateService sysFileTemplateService;

    @Resource
    private IMonthlyOutputService monthlyOutputService;


//    @Override
//    public BaseResponse changeMonthlyOutputByInvId(String ipvId) {
//        LambdaQueryWrapper<InvPlanYear> invPlanYearLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        invPlanYearLambdaQueryWrapper.eq(InvPlanYear::getIpvId, ipvId);
//        invPlanYearLambdaQueryWrapper.eq(InvPlanYear::getIpvState, 7);
//        List<InvPlanYear> invplanYears = invPlanYearService.list(invPlanYearLambdaQueryWrapper);
//        if (ValidateUtil.isEmpty(invplanYears)) {
//            return BaseResponse.fail("没有符合条件的年度计划");
//        }
//        InvPlanYear invPlanYear = invplanYears.get(0);
//        LambdaQueryWrapper<InvMonthlySchedule> invMonthlyScheduleLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        invMonthlyScheduleLambdaQueryWrapper.eq(InvMonthlySchedule::getIpvId, ipvId);
//        List<InvMonthlySchedule> invMonthlySchedules = invMonthlyScheduleService.list(invMonthlyScheduleLambdaQueryWrapper);
//
//        if (ValidateUtil.isEmpty(invMonthlySchedules)) {
//            return BaseResponse.fail("没有符合条件的月度计划");
//        }
//
//        List<MonthlyOutput> monthlyOutputList = new ArrayList<>();
//
//        for (InvMonthlySchedule invMonthlySchedule : invMonthlySchedules) {
//            MonthlyOutput monthlyOutput = new MonthlyOutput();
//            monthlyOutput.setId(UUID.randomUUID().toString());
//            monthlyOutput.setPId(invPlanYear.getPId());
//            monthlyOutput.setConId(invPlanYear.getConId());
//            monthlyOutput.setMoYear(invPlanYear.getIpvDeclareYear());
//            monthlyOutput.setMoMonth(String.valueOf(invMonthlySchedule.getImsMonth()));
//            monthlyOutput.setMoJhMoney(invMonthlySchedule.getImsPlannedInv());
//            monthlyOutput.setMoMoney(invMonthlySchedule.getImsPlannedInv());
//            monthlyOutputList.add(monthlyOutput);
//        }
//        this.saveBatch(monthlyOutputList);
//        return BaseResponse.ok(monthlyOutputList,monthlyOutputList.size());
//    }

    private static List<String> cellRefereceList = Arrays.asList("A2","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R");

    // todo 这个 MonthlyOutput 是主表需要向 sys_file 插入数据
    @Override
    public BaseResponse importMonthlyOutput(MultipartFile file,String token) {
        if (file.isEmpty()) {
            return BaseResponse.fail("文件为空");
        }

        if (ValidateUtil.isEmpty(token)) {
            return BaseResponse.fail("token为空");
        }
        LogUserInfoVo userInfo = redisUtils.getUserInfoByToken(token);
        if (userInfo == null){
            return BaseResponse.fail("用户信息为空");
        }

        log.info(" userInfo : " + userInfo);
        String year = null;
        try (InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0); // 获取第一个工作表

            String cellReference = cellRefereceList.get(0); // 单元格引用
            CellReference cellRef = new CellReference(cellReference);
            Row row = sheet.getRow(cellRef.getRow());
            if (row != null) {
                Cell cell = row.getCell(cellRef.getCol());
                if (cell != null) {
                    String cellValue = getCellValue(cell);
                    year = cellValue.replaceAll("\\D+", ""); // 只保留数字
                }
            }
            if (ValidateUtil.isEmpty(year)) {
                return BaseResponse.fail("未解析出计划年份");
            }
            log.info(" 年份为 ： " + year);

            int startRow = 3; // 第4行（索引为3）
            int startCol = 1; // B 列
            int endCol = 17;  // R 列

            List<Map<String , String>> allRowData = new ArrayList<>();

            for (int rowIndex = startRow; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row sheetRow = sheet.getRow(rowIndex);

                if (sheetRow == null || isRowEmpty(sheetRow,startCol,endCol)) {
                    continue;
                }

                Map<String , String> rowData = readRowCells(sheetRow,startCol,endCol);
                log.info("rowData : " + rowData);
                allRowData.add(rowData);
            }
            try {
                monthlyOutputService.insertData(year,allRowData,userInfo,token);
            } catch (RuntimeException e) {
                return BaseResponse.fail(e.getMessage());
            }


        } catch (IOException e) {
            e.printStackTrace(); // 记录异常信息
            return BaseResponse.fail("文件读取失败: " + e.getMessage());
        }

        return BaseResponse.ok();
    }

    /**
     * 通过解析的数据插入数据库
     */
    // todo 这个 MonthlyOutput 是主表需要向 sys_file 插入数据
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertData(String year, List<Map<String, String>> allRowData, LogUserInfoVo userInfo,String token) {
        if (userInfo == null) {
            throw new RuntimeException("用户信息为空");
        }

        if (ValidateUtil.isEmpty(allRowData)) {
            throw new RuntimeException("没有解析出数据");
        }

        String uId = userInfo.getId();
        String uName = userInfo.getName();

        // Step 1: 收集所有项目名和合同名
        Set<String> pNames = new HashSet<>();
        Set<String> conNames = new HashSet<>();

        for (Map<String, String> rowData : allRowData) {
            String pName = getValueFromMap(rowData, "B");
            String conName = getValueFromMap(rowData, "C");

            if (pName != null && !pName.trim().isEmpty()) {
                pNames.add(pName);
            }
            if (conName != null && !conName.trim().isEmpty()) {
                conNames.add(conName);
            }
        }

        // Step 2: 批量查询 PreProject 和 ConContract 的 ID
        Map<String, String> pNameToIdMap = new HashMap<>();
        if (!pNames.isEmpty()) {
            LambdaQueryWrapper<PreProject> pWrapper = new LambdaQueryWrapper<>();
            pWrapper.in(PreProject::getpName, pNames);
            List<PreProject> projects = projectService.list(pWrapper);
            pNameToIdMap = projects.stream()
                    .collect(Collectors.toMap(PreProject::getpName, PreProject::getpId));
        }

        for (String pName : pNames) {
            if (!pNameToIdMap.containsKey(pName)) {
                log.info("未找到项目名：" + pName);
                throw new RuntimeException("未找到项目名：" + pName);
            }
        }

        Map<String, String> conNameToIdMap = new HashMap<>();
        if (!conNames.isEmpty()) {
            List<ConContractResponse> contracts = pmFeign.getContractListByName(conNames,token);
            // 确保 contracts 不为 null
            if (contracts == null) {
                contracts = new ArrayList<>();
            }

            // 收集映射关系，处理重复键的情况
            conNameToIdMap = contracts.stream()
                    .collect(Collectors.toMap(
                            ConContractResponse::getConName,
                            ConContractResponse::getConId,
                            (existing, replacement) -> existing // 保留第一个
                    ));

            // 检查是否所有 conNames 都找到了对应 ID
            for (String conName : conNames) {
                if (!conNameToIdMap.containsKey(conName)) {
                    throw new RuntimeException("未找到合同名：" + conName);
                }
            }
        }
        // Step 3: 收集所有 year + pId + conId + month 的组合
        List<String> checkPIds = new ArrayList<>();
        List<String> checkConIds = new ArrayList<>();
        List<String> checkYears = new ArrayList<>();
        List<String> checkMonths = new ArrayList<>();

        for (Map<String, String> rowData : allRowData) {
            String pName = getValueFromMap(rowData, "B");
            String conName = getValueFromMap(rowData, "C");

            String pId = pNameToIdMap.get(pName);
            String conId = conNameToIdMap.get(conName);

            if (pId == null || conId == null) continue;

            for (int i = 1; i <= 12; i++) {
                checkPIds.add(pId);
                checkConIds.add(conId);
                checkYears.add(year);
                checkMonths.add(String.valueOf(i));
            }
        }

        // Step 4: 一次性查询数据库中已有的记录
        Map<String, MonthlyOutput> existingMap = new HashMap<>();
        if (!checkPIds.isEmpty()) {
            LambdaQueryWrapper<MonthlyOutput> existWrapper = new LambdaQueryWrapper<>();
            existWrapper.in(MonthlyOutput::getPId, checkPIds)
                    .in(MonthlyOutput::getConId, checkConIds)
                    .in(MonthlyOutput::getMoYear, checkYears)
                    .in(MonthlyOutput::getMoMonth, checkMonths);

            List<MonthlyOutput> existingList = this.list(existWrapper);

            // 构造 key -> output 映射
            existingMap = existingList.stream()
                    .collect(Collectors.toMap(
                            output -> output.getPId() + "-" + output.getConId() + "-" + output.getMoYear() + "-" + output.getMoMonth(),
                            Function.identity(),
                            (existing, replacement) -> existing
                    ));
        }

        // Step 5: 准备待插入/更新的列表
        List<MonthlyOutput> toInsertList = new ArrayList<>();
        List<MonthlyOutput> toUpdateList = new ArrayList<>();

        for (Map<String, String> rowData : allRowData) {
            String pName = getValueFromMap(rowData, "B");
            String conName = getValueFromMap(rowData, "C");

            String pId = pNameToIdMap.get(pName);
            String conId = conNameToIdMap.get(conName);

            if (pId == null || conId == null) {
                continue; // 跳过无效数据
            }

            for (int i = 1; i <= 12; i++) {
                String cellKey = cellRefereceList.get(i + 4); // F~Q列
                String moMoneyString = getValueFromMap(rowData, cellKey);
                BigDecimal moMoney = parseBigDecimal(moMoneyString);

                String key = pId + "-" + conId + "-" + year + "-" + i;

                MonthlyOutput monthlyOutput = new MonthlyOutput();
                monthlyOutput.setPId(pId);
                monthlyOutput.setConId(conId);
                monthlyOutput.setMoYear(year);
                monthlyOutput.setMoMonth(String.valueOf(i));
                if (moMoney != null) {
                    moMoney = moMoney.multiply(BigDecimal.valueOf(10000));
                    monthlyOutput.setMoMoney(moMoney);
                }

                // 设置公共字段
                monthlyOutput.setId(UUID.randomUUID().toString());
                monthlyOutput.setCreateUid(uId);
                monthlyOutput.setCreateUname(uName);
                monthlyOutput.setCreateTime(new Date());
                monthlyOutput.setState(7);
                monthlyOutput.setStateTime(new Date());

                if (existingMap.containsKey(key)) {
                    // 更新已有记录
                    MonthlyOutput existing = existingMap.get(key);
                    monthlyOutput.setId(existing.getId());
                    monthlyOutput.setCreateUid(existing.getCreateUid());
                    monthlyOutput.setCreateTime(existing.getCreateTime());
                    monthlyOutput.setState(existing.getState());
                    monthlyOutput.setStateTime(existing.getStateTime());
                    toUpdateList.add(monthlyOutput);
                } else {
                    // 插入新记录
                    toInsertList.add(monthlyOutput);
                }
            }
        }

        // Step 6: 批量插入或更新
        if (!toInsertList.isEmpty()) {
            log.info("批量插入数据：" + toInsertList + " 条数  : " + toInsertList.size());
            this.saveBatch(toInsertList);
            handleFileOperationsForInserts(toInsertList, userInfo);
        }

        if (!toUpdateList.isEmpty()) {
            log.info("批量更新数据：" + toUpdateList + " 条数  : " + toUpdateList.size());
            this.updateBatchById(toUpdateList);
            handleFileOperationsForUpdates(toUpdateList, userInfo);
        }
    }

    private static final String tCode = "MonthlyOutput";

    private void handleFileOperationsForInserts(List<MonthlyOutput> inserts, LogUserInfoVo userInfo) {
        LambdaQueryWrapper<SysFileTemplate> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysFileTemplate::gettCode, tCode);
        List<SysFileTemplate> sysFileTemplates = sysFileTemplateService.list(queryWrapper);
        SysFileTemplate fileTemplate = new SysFileTemplate();
        if (sysFileTemplates != null && sysFileTemplates.size() > 0 && sysFileTemplates.get(0) != null && sysFileTemplates.get(0).gettId() != null && !"".equals(sysFileTemplates.get(0).gettId()) && !"null".equals(sysFileTemplates.get(0).gettId())){
            fileTemplate = sysFileTemplates.get(0);
        }
        List<String> ids = new ArrayList<>();
        List<String> relationIds = new ArrayList<>();
        List<String> pIds = new ArrayList<>();
        List<String> createUids = new ArrayList<>();
        List<String> createUnames = new ArrayList<>();
        List<Date> createTimes = new ArrayList<>();
        List<String> conIds = new ArrayList<>();
        List<String> tIds = new ArrayList<>();
        String tId = fileTemplate.gettId();
        String tName = fileTemplate.gettName();
        for (MonthlyOutput output : inserts) {
            ids.add(output.getId());
            String relationId = UUID.randomUUID().toString();
            relationIds.add(relationId);
            pIds.add(output.getPId());
            createUids.add(output.getCreateUid());
            createUnames.add(output.getCreateUname());
            createTimes.add(output.getCreateTime());
            conIds.add(output.getConId());
            tIds.add(tId);
        }
        sysFileService.batchSaveFile(
                ids,
                tIds,
                "7",
                createUids,
                createUnames,
                createTimes,
                pIds,
                tName,
                null,
                "create",
                relationIds,
                null,
                tCode,
                conIds
        );



    }

    private void handleFileOperationsForUpdates(List<MonthlyOutput> updates, LogUserInfoVo userInfo) {
        LambdaQueryWrapper<SysFileTemplate> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysFileTemplate::gettCode, tCode);
        List<SysFileTemplate> sysFileTemplates = sysFileTemplateService.list(queryWrapper);
        SysFileTemplate fileTemplate = new SysFileTemplate();
        if (sysFileTemplates.isEmpty()){
            fileTemplate = sysFileTemplates.get(0);
        }
        String tId = fileTemplate.gettId();
        List<String> ids = new ArrayList<>();
        List<String> pIds = new ArrayList<>();
        List<String> conIds = new ArrayList<>();
        List<String> tIds = new ArrayList<>();
        for (MonthlyOutput output : updates) {
            ids.add(output.getId());
            pIds.add(output.getPId());
            conIds.add(output.getConId());
            tIds.add(tId);

//            sysFileAnnexService.refreshRelationFile(null, Arrays.asList(output.getId()), output.getId());
        }

        sysFileService.batchSaveFile(
                ids,
                tIds,
                "7",
                null,
                null,
                null,
                pIds,
                null,
                null,
                "update",
                null,
                null,
                tCode,
                conIds
        );

    }



    private BigDecimal parseBigDecimal(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null; // 空值返回null
        }
        try {
            BigDecimal decimal = new BigDecimal(value.trim());
            if (decimal.compareTo(BigDecimal.ZERO) == 0) {
                return null;
            }
            return decimal;
        } catch (NumberFormatException e) {
            // 解析数据出现问题
            return null;
        }
    }


    /**
     * 根据列字母（如 "B"）找到对应行中的值（自动拼接行号）
     */
    private String getValueFromMap(Map<String, String> rowData, String columnLetter) {
        for (Map.Entry<String, String> entry : rowData.entrySet()) {
            String cellRef = entry.getKey(); // 如 "B4", "C5"
            if (cellRef.startsWith(columnLetter)) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * 判断指定行在指定列范围内是否为空
     */
    private boolean isRowEmpty(Row row, int startCol, int endCol) {
        Cell firstCell = row.getCell(startCol); // 只看第一个单元格
        return firstCell == null || getCellValue(firstCell).trim().isEmpty();
    }



    private Map<String, String> readRowCells(Row row, int startCol, int endCol) {
        Map<String, String> cellData = new LinkedHashMap<>();

        for (int colIndex = startCol; colIndex <= endCol; colIndex++) {
            Cell cell = row.getCell(colIndex);
            String value = getCellValue(cell);

            // 获取列字母（B=1 -> 'B'）
            String columnLetter = CellReference.convertNumToColString(colIndex);
            String cellRef = columnLetter + (row.getRowNum() + 1); // 转换行为实际编号（如 3 -> 4）

            // 存入 Map，空值保留 null 或 ""
            cellData.put(cellRef, value.trim().isEmpty() ? null : value.trim());
        }

        return cellData;
    }


    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }


}
