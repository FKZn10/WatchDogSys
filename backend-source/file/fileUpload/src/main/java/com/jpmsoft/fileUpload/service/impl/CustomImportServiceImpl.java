package com.jpmsoft.fileUpload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.common.model.requestAndResponse.Project.ProvPilotEnterpriseImportError;
import com.jpmsoft.common.model.requestAndResponse.Project.ProvPilotEnterpriseImportRequest;
import com.jpmsoft.common.model.requestAndResponse.Project.ProvPilotEnterpriseImportResult;
import com.jpmsoft.common.model.requestAndResponse.Project.ProvPilotEnterpriseImportRow;
import com.jpmsoft.common.model.requestAndResponse.Project.ProvPilotProjectImportError;
import com.jpmsoft.common.model.requestAndResponse.Project.ProvPilotProjectImportRequest;
import com.jpmsoft.common.model.requestAndResponse.Project.ProvPilotProjectImportResult;
import com.jpmsoft.common.model.requestAndResponse.Project.ProvPilotProjectImportRow;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.constants.FileMsg;
import com.jpmsoft.fileUpload.mapper.*;
import com.jpmsoft.fileUpload.pojo.dto.UploadDto;
import com.jpmsoft.fileUpload.pojo.dto.beijing.*;
import com.jpmsoft.fileUpload.pojo.entity.*;
import com.jpmsoft.fileUpload.pojo.entity.beijing.*;
import com.jpmsoft.fileUpload.pojo.vo.UploadResVo;
import com.jpmsoft.fileUpload.service.CustomImportService;
import com.jpmsoft.fileUpload.service.ISysDictionaryService;
import com.jpmsoft.fileUpload.service.ISysConfigService;
import com.jpmsoft.fileUpload.service.ISysFileService;
import com.jpmsoft.fileUpload.service.ISysFileAnnexService;
import com.jpmsoft.fileUpload.service.ISysFileTemplateService;
import com.jpmsoft.fileUpload.service.ISysImpLogService;
import com.jpmsoft.fileUpload.utils.FileUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
public class CustomImportServiceImpl implements CustomImportService {

    @Resource
    private ISysConfigService sysConfigService;

    @Resource
    private ISysImpLogService sysImpLogService;

    @Resource
    private ISysDictionaryService sysDictionaryService;

    @Resource
    private ISysFileService sysFileService;

    @Resource
    private ISysFileTemplateService sysFileTemplateService;

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Resource
    private SysFileAnnexMapper sysFileAnnexMapper;

    @Resource
    private PreProjectMapper preProjectMapper;

    @Resource
    private MaterialDeviceEnterInspectionMapper materialDeviceEnterInspectionMapper;

    @Resource
    private MaterialDeviceEnterInspectionChildMapper materialDeviceEnterInspectionChildMapper;

    @Resource
    MaterialDeviceSamplingInspectionMapper materialDeviceSamplingInspectionMapper;

    @Resource
    private MaterialDeviceTestReportMapper materialDeviceTestReportMapper;

    @Resource
    private SubpackageContractMapper subpackageContractMapper;

    @Resource
    private BranchProjectQualityAcceptMapper branchProjectQualityAcceptMapper;

    @Resource
    private BranchProjectQualityAcceptChildMapper branchProjectQualityAcceptChildMapper;

    @Resource
    private SubItemEngineeringQualityAcceptanceMapper subItemEngineeringQualityAcceptanceMapper;

    @Resource
    private SubItemEngineeringQualityAcceptanceChildMapper subItemEngineeringQualityAcceptanceChildMapper;

    @Resource
    private CheckGroupQualityAcceptanceMapper checkGroupQualityAcceptanceMapper;

    @Resource
    private CheckGroupQualityAcceptanceChildMapper checkGroupQualityAcceptanceChildMapper;

    @Resource
    private ComplaintLawsuitImportMapper complaintLawsuitImportMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private com.jpmsoft.fileUpload.feign.PmFeign pmFeign;

    @Resource
    private com.jpmsoft.fileUpload.service.IFileUploadService fileUploadService;

    @Resource
    private com.jpmsoft.fileUpload.utils.RedisUtils redisUtils;

    private static final String COMPLAINT_SHEET_NAME = "各平台投诉";

    /**
     * 省试点项目导入源文件的文件编码
     */
    private static final String PROV_PILOT_FILE_CODE = "ProvPilotProjectImport";

    /**
     * 省试点项目 导入列：字段 key -> Excel 列字母（B~F 等）
     */
    private static final Map<String, String> PROV_PILOT_COLUMN_MAP = new HashMap<>();

    static {
        PROV_PILOT_COLUMN_MAP.put("annualYear", "B");
        PROV_PILOT_COLUMN_MAP.put("batch", "C");
        PROV_PILOT_COLUMN_MAP.put("projectName", "D");
        PROV_PILOT_COLUMN_MAP.put("projectType", "E");
        PROV_PILOT_COLUMN_MAP.put("district", "F");
        PROV_PILOT_COLUMN_MAP.put("declareUnit", "G");
    }

    /**
     * 省试点企业 导入列：字段 key -> Excel 列字母（B~F）
     */
    private static final Map<String, String> PROV_PILOT_ENTERPRISE_COLUMN_MAP = new HashMap<>();

    static {
        PROV_PILOT_ENTERPRISE_COLUMN_MAP.put("annualYear", "B");
        PROV_PILOT_ENTERPRISE_COLUMN_MAP.put("batch", "C");
        PROV_PILOT_ENTERPRISE_COLUMN_MAP.put("enterpriseName", "D");
        PROV_PILOT_ENTERPRISE_COLUMN_MAP.put("enterpriseType", "E");
        PROV_PILOT_ENTERPRISE_COLUMN_MAP.put("district", "F");
    }

    final String dirPath = "/OpenUpload/";

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseResponse importData(MultipartFile[] files, LogUserInfoVo user) {
        String root = sysConfigService.getRootDirectory();
        if (files == null || files.length < 1) return BaseResponse.fail(FileMsg.noneFile);
        for (MultipartFile file : files) {
            File tfFile = temporaryStorageFile(root, file, ".*\\.(zip)$", FileMsg.onlyZip, "customImport");
            String decPath = root + "/JPBPMTemporaryFolder/decompression/" + GenerateUUID.getUUID();
            FileUtil.decompression(tfFile.getPath(), decPath);
            // tfFile.delete();//解压完成直接删除压缩包
            File[] excels = FileUtil.findExcel(decPath);
            if (excels == null || excels.length < 1) {
                sysImpLogService.create(user, tfFile, FileMsg.notFoundExcel, file.getOriginalFilename());
                return BaseResponse.fail(FileMsg.notFoundExcel);
            }
            try {
                for (File excel : excels) {
                    String excelName = excel.getName();
                    String tCode = excelName.split("\\.")[0];
                    if (tCode.contains("(")) tCode = tCode.substring(tCode.indexOf("(") + 1, tCode.indexOf(")"));
                    // 开始解析excel
                    if ("materialDeviceEnterInspection".equals(tCode)) {
                        // 材料设备进场验货导入
                        importMaterialDeviceEnterInspection(excel, user, decPath, tCode, root);
                    } else if ("materialDeviceSamplingInspection".equals(tCode)) {
                        // 材料设备抽检 数据导入
                        importMaterialDeviceSamplingInspection(excel, user, decPath, tCode, root);
                    } else if ("materialDeviceTestReport".equals(tCode)) {
                        // 材料设备检测报告 数据导入
                        importMaterialDeviceTestReport(excel, user, decPath, tCode, root);
                    } else if ("subpackageContract".equals(tCode)) {
                        // 分包合同 数据导入
                        importSubpackageContract(excel, user, decPath, tCode, root);
                    } else if ("branchProjectQualityAccept".equals(tCode)) {
                        // 分部工程质量验收 数据导入
                        importBranchProjectQualityAccept(excel, user, decPath, tCode, root);
                    } else if ("subItemEngineeringQualityAcceptance".equals(tCode)) {
                        // 分项工程质量验收 数据导入
                        importSubItemEngineeringQualityAcceptance(excel, user, decPath, tCode, root);
                    } else if ("checkGroupQualityAcceptance".equals(tCode)) {
                        // 检验批质量验收记录 数据导入
                        importCheckGroupQualityAcceptance(excel, user, decPath, tCode, root);
                    }
                }
                sysImpLogService.create(user, tfFile, FileMsg.actSuccess, file.getOriginalFilename());
            } finally {
                FileUtil.deleteFiles(new File(decPath), true);
            }
        }
        return BaseResponse.ok("");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseResponse importComplaintData(MultipartFile file, LogUserInfoVo user) {
        if (file == null) return BaseResponse.fail(FileMsg.noneFile);
        FileUtil.checkExcelFile(file);
        try (InputStream inputStream = file.getInputStream(); Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = getRequiredSheet(workbook, COMPLAINT_SHEET_NAME);
            // 获取案件来源字典
            List<SysDictionary> caseSourceDictionaries = sysDictionaryService.getDictionaryByParentCode("JPMsoft_AJLY");
            List<Complaint> complaints = parseComplaintSheet(sheet, user, caseSourceDictionaries);
            if (ValidateUtil.isEmpty(complaints)) return BaseResponse.fail(FileMsg.excelNotData);
            return BaseResponse.ok(saveComplaintImport(complaints, user));
        } catch (Exception e) {
            if (e instanceof JPMException) return BaseResponse.fail(e.getMessage());
            log.error("举报台账导入失败：{}", file.getOriginalFilename(), e);
            throw new JPMException(FileMsg.fileParsingFail);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseResponse importLawsuitData(MultipartFile file, LogUserInfoVo user) {
        if (file == null) return BaseResponse.fail(FileMsg.noneFile);
        FileUtil.checkExcelFile(file);
        try (InputStream inputStream = file.getInputStream(); Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            List<SysDictionary> unitTypeDictionaries = sysDictionaryService.getDictionaryByParentCode("JPMsoft_DWLX");
            List<Lawsuit> lawsuits = parseLawsuitSheet(sheet, user, unitTypeDictionaries);
            if (ValidateUtil.isEmpty(lawsuits)) return BaseResponse.fail(FileMsg.excelNotData);
            return BaseResponse.ok(saveLawsuitImport(lawsuits, user));
        } catch (Exception e) {
            if (e instanceof JPMException) return BaseResponse.fail(e.getMessage());
            log.error("诉讼台账导入失败：{}", file.getOriginalFilename(), e);
            throw new JPMException(FileMsg.fileParsingFail);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseResponse importProvPilotProject(MultipartFile file, LogUserInfoVo user, String token) {
        if (file == null) return BaseResponse.fail(FileMsg.noneFile);
        FileUtil.checkExcelFile(file);
        try (InputStream inputStream = file.getInputStream(); Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            ProvPilotProjectImportRequest request = parseProvPilotProjectSheet(sheet, user);
            request.setTeId(user.getTeId());
            if (ValidateUtil.isEmpty(request.getRows())) {
                return BaseResponse.fail(FileMsg.excelNotData);
            }
            // 真上传 Excel 源文件到 file-server，落 SYS_FileAnnex（upload_status=1），整批共用同一个 fileId
            String fileId = uploadProvPilotExcel(file, user, token);
            if (ValidateUtil.isEmpty(fileId)) {
                return BaseResponse.fail(FileMsg.fileUpError);
            }
            request.setFileId(fileId);
            BaseResponse res = pmFeign.importProvPilotProject(request, token);
            // 将 PP 返回的字段级错误拼成"第N行第X列"的明细（成功/失败都要拼，前端拿到 data 里的明细）
            if (res != null && res.getData() instanceof ProvPilotProjectImportResult) {
                ProvPilotProjectImportResult result = (ProvPilotProjectImportResult) res.getData();
                if (result.getErrors() != null) {
                    for (ProvPilotProjectImportError err : result.getErrors()) {
                        String col = PROV_PILOT_COLUMN_MAP.get(err.getFieldKey());
                        err.setMessage("第" + err.getRowNum() + "行" + (col == null ? "" : "第" + col + "列") + err.getMessage());
                    }
                }
            }
            return res;
        } catch (Exception e) {
            if (e instanceof JPMException) return BaseResponse.fail(e.getMessage());
            log.error("省试点项目导入失败：{}", file.getOriginalFilename(), e);
            throw new JPMException(FileMsg.fileParsingFail);
        }
    }

    /**
     * 上传省试点项目导入的 Excel 源文件到 file-server（落 SYS_FileAnnex，upload_status=1，已确认上传）。
     *
     * @return 上传后的附件 fileId（SYS_FileAnnex.fileId），整批共用
     */
    private String uploadProvPilotExcel(MultipartFile file, LogUserInfoVo user, String token) {
        UploadDto uploadDto = new UploadDto();
        uploadDto.setuId(user.getId());
        uploadDto.setCode(PROV_PILOT_FILE_CODE);
        // 整批共用一个关联号，fileId/entityId 亦复用之，便于按批次回溯
        String relationId = GenerateUUID.getUUID();
        uploadDto.setFileRelationId(relationId);
        uploadDto.setFileId(relationId);
        uploadDto.setFileNames(new String[]{file.getOriginalFilename()});
        BaseResponse up = fileUploadService.uploadFile(new MultipartFile[]{file}, uploadDto, "root", 1, redisUtils.getTeId(token));
        if (up == null || !up.isSuccess() || !(up.getData() instanceof List)) {
            log.error("省试点项目导入-上传源文件失败：{}", up == null ? "null" : up.getMessage());
            return null;
        }
        List<?> data = (List<?>) up.getData();
        if (data.isEmpty() || !(data.get(0) instanceof UploadResVo)) {
            return null;
        }
        return ((UploadResVo) data.get(0)).getId();
    }

    private ProvPilotProjectImportRequest parseProvPilotProjectSheet(Sheet sheet, LogUserInfoVo user) {
        ProvPilotProjectImportRequest request = new ProvPilotProjectImportRequest();
        List<ProvPilotProjectImportRow> rows = new ArrayList<>();
        // 正式模板：第1行标题(合并A1:G1)，第2行说明，第3行表头，数据从第4行(i=3)开始
        // 列：A=序号、B=年度、C=批次、D=项目名称、E=项目类型、F=所在区、G=申报单位
        for (int i = 3; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            // 跳过空行（B~G 全空）
            if (isBlankRow(row, 1, 6)) continue;
            ProvPilotProjectImportRow dataRow = new ProvPilotProjectImportRow();
            dataRow.setExcelRowNum(i + 1);
            dataRow.setColAnnualYear(getCellString(row, 1));
            dataRow.setColBatch(getCellString(row, 2));
            dataRow.setColProjectName(getCellString(row, 3));
            dataRow.setColProjectType(getCellString(row, 4));
            dataRow.setColDistrict(getCellString(row, 5));
            dataRow.setColDeclareUnit(getCellString(row, 6));
            rows.add(dataRow);
        }
        request.setRows(rows);
        request.setCreateUid(user.getId());
        request.setCreateUname(user.getName());
        return request;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseResponse importProvPilotEnterprise(MultipartFile file, LogUserInfoVo user, String token) {
        if (file == null) return BaseResponse.fail(FileMsg.noneFile);
        FileUtil.checkExcelFile(file);
        try (InputStream inputStream = file.getInputStream(); Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            ProvPilotEnterpriseImportRequest request = parseProvPilotEnterpriseSheet(sheet, user);
            request.setTeId(user.getTeId());
            if (ValidateUtil.isEmpty(request.getRows())) {
                return BaseResponse.fail(FileMsg.excelNotData);
            }
            BaseResponse res = pmFeign.importProvPilotEnterprise(request, token);
            // 拼"第N行第X列"明细（成功/失败都要拼）
            if (res != null && res.getData() instanceof ProvPilotEnterpriseImportResult) {
                ProvPilotEnterpriseImportResult result = (ProvPilotEnterpriseImportResult) res.getData();
                if (result.getErrors() != null) {
                    for (ProvPilotEnterpriseImportError err : result.getErrors()) {
                        String col = PROV_PILOT_ENTERPRISE_COLUMN_MAP.get(err.getFieldKey());
                        err.setMessage("第" + err.getRowNum() + "行" + (col == null ? "" : "第" + col + "列") + err.getMessage());
                    }
                }
            }
            return res;
        } catch (Exception e) {
            if (e instanceof JPMException) return BaseResponse.fail(e.getMessage());
            log.error("省试点企业导入失败：{}", file.getOriginalFilename(), e);
            throw new JPMException(FileMsg.fileParsingFail);
        }
    }

    private ProvPilotEnterpriseImportRequest parseProvPilotEnterpriseSheet(Sheet sheet, LogUserInfoVo user) {
        ProvPilotEnterpriseImportRequest request = new ProvPilotEnterpriseImportRequest();
        List<ProvPilotEnterpriseImportRow> rows = new ArrayList<>();
        // 模板：第1行标题、第2行说明、第3行表头，数据从第4行(i=3)开始；列 A=序号 B=年度 C=批次 D=企业名称 E=企业类型 F=所在区
        for (int i = 3; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (isBlankRow(row, 1, 5)) continue;
            ProvPilotEnterpriseImportRow dataRow = new ProvPilotEnterpriseImportRow();
            dataRow.setExcelRowNum(i + 1);
            dataRow.setColAnnualYear(getCellString(row, 1));
            dataRow.setColBatch(getCellString(row, 2));
            dataRow.setColEnterpriseName(getCellString(row, 3));
            dataRow.setColEnterpriseType(getCellString(row, 4));
            dataRow.setColDistrict(getCellString(row, 5));
            rows.add(dataRow);
        }
        request.setRows(rows);
        request.setCreateUid(user.getId());
        request.setCreateUname(user.getName());
        return request;
    }


    private Map<String, Integer> saveComplaintImport(List<Complaint> complaints, LogUserInfoVo user) {
        Map<String, Complaint> existingMap = getExistingComplaintMap(complaints);
        Map<String, Complaint> finalMap = new LinkedHashMap<>();
        Set<String> updateKeys = new HashSet<>();
        for (Complaint complaint : complaints) {
            String duplicateKey = getComplaintDuplicateKey(complaint);
            Complaint duplicate = existingMap.get(duplicateKey);
            if (duplicate != null) {
                complaint.setId(duplicate.getId());
                updateKeys.add(duplicateKey);
            } else if (finalMap.containsKey(duplicateKey)) {
                complaint.setId(finalMap.get(duplicateKey).getId());
            }
            finalMap.put(duplicateKey, complaint);
        }
        List<Complaint> insertList = new ArrayList<>();
        List<Complaint> updateList = new ArrayList<>();
        List<String> updateIds = new ArrayList<>();
        List<SysFile> sysFiles = new ArrayList<>();
        for (Map.Entry<String, Complaint> entry : finalMap.entrySet()) {
            Complaint complaint = entry.getValue();
            if (updateKeys.contains(entry.getKey())) {
                updateList.add(complaint);
                updateIds.add(complaint.getId());
            } else {
                insertList.add(complaint);
            }
            sysFiles.add(buildSysFile(complaint.getId(), complaint.getPreId(), user, complaint.getCreateTime(), "Complaint_Ledger", "举报表"));
        }
        if (!updateIds.isEmpty()) {
            complaintLawsuitImportMapper.deleteComplaintBatchIds(updateIds);
        }
        List<Complaint> saveList = new ArrayList<>();
        saveList.addAll(insertList);
        saveList.addAll(updateList);
        if (!saveList.isEmpty()) {
            complaintLawsuitImportMapper.insertComplaintBatch(saveList);
        }
        if (!sysFiles.isEmpty()) {
            sysFileService.saveOrUpdateBatch(sysFiles);
        }
        Map<String, Integer> result = new HashMap<>();
        result.put("insertCount", insertList.size());
        result.put("updateCount", updateList.size());
        return result;
    }

    /**
     * 诉讼重复条件，按单位、项目、收件时间
     */
    private Map<String, Integer> saveLawsuitImport(List<Lawsuit> lawsuits, LogUserInfoVo user) {
        Map<String, Lawsuit> existingMap = getExistingLawsuitMap(lawsuits);
        Map<String, Lawsuit> finalMap = new LinkedHashMap<>();
        Set<String> updateKeys = new HashSet<>();
        for (Lawsuit lawsuit : lawsuits) {
            String duplicateKey = getLawsuitDuplicateKey(lawsuit);
            Lawsuit duplicate = existingMap.get(duplicateKey);
            if (duplicate != null) {
                lawsuit.setId(duplicate.getId());
                updateKeys.add(duplicateKey);
            } else if (finalMap.containsKey(duplicateKey)) {
                lawsuit.setId(finalMap.get(duplicateKey).getId());
            }
            finalMap.put(duplicateKey, lawsuit);
        }
        List<Lawsuit> insertList = new ArrayList<>();
        List<Lawsuit> updateList = new ArrayList<>();
        List<String> updateIds = new ArrayList<>();
        List<SysFile> sysFiles = new ArrayList<>();
        for (Map.Entry<String, Lawsuit> entry : finalMap.entrySet()) {
            Lawsuit lawsuit = entry.getValue();
            if (updateKeys.contains(entry.getKey())) {
                updateList.add(lawsuit);
                updateIds.add(lawsuit.getId());
            } else {
                insertList.add(lawsuit);
            }
            sysFiles.add(buildSysFile(lawsuit.getId(), lawsuit.getPreId(), user, lawsuit.getCreateTime(), "Lawsuit_Ledger", "诉讼表"));
        }
        if (!updateIds.isEmpty()) {
            complaintLawsuitImportMapper.deleteLawsuitBatchIds(updateIds);
        }
        List<Lawsuit> saveList = new ArrayList<>();
        saveList.addAll(insertList);
        saveList.addAll(updateList);
        if (!saveList.isEmpty()) {
            complaintLawsuitImportMapper.insertLawsuitBatch(saveList);
        }
        if (!sysFiles.isEmpty()) {
            sysFileService.saveOrUpdateBatch(sysFiles);
        }
        Map<String, Integer> result = new HashMap<>();
        result.put("insertCount", insertList.size());
        result.put("updateCount", updateList.size());
        return result;
    }

    private Map<String, Complaint> getExistingComplaintMap(List<Complaint> complaints) {
        Set<String> preIds = new HashSet<>();
        Set<String> unitIds = new HashSet<>();
        for (Complaint complaint : complaints) {
            if (ValidateUtil.isNotEmpty(complaint.getPreId())) preIds.add(complaint.getPreId());
            if (ValidateUtil.isNotEmpty(complaint.getUnitId())) unitIds.add(complaint.getUnitId());
        }
        Map<String, Complaint> existingMap = new HashMap<>();
        List<Complaint> existingList = complaintLawsuitImportMapper.selectComplaintCandidates(preIds, unitIds);
        if (existingList != null) {
            for (Complaint existing : existingList) {
                existingMap.put(getComplaintDuplicateKey(existing), existing);
            }
        }
        return existingMap;
    }

    private Map<String, Lawsuit> getExistingLawsuitMap(List<Lawsuit> lawsuits) {
        Set<String> preIds = new HashSet<>();
        Set<String> unitIds = new HashSet<>();
        for (Lawsuit lawsuit : lawsuits) {
            if (ValidateUtil.isNotEmpty(lawsuit.getPreId())) preIds.add(lawsuit.getPreId());
            if (ValidateUtil.isNotEmpty(lawsuit.getUnitId())) unitIds.add(lawsuit.getUnitId());
        }
        Map<String, Lawsuit> existingMap = new HashMap<>();
        List<Lawsuit> existingList = complaintLawsuitImportMapper.selectLawsuitCandidates(preIds, unitIds);
        if (existingList != null) {
            for (Lawsuit existing : existingList) {
                existingMap.put(getLawsuitDuplicateKey(existing), existing);
            }
        }
        return existingMap;
    }

    /**
     * 举报重复条件，按项目、单位、举报人、电话、内容、举报时间、案件来源
     * （项目/单位为空时退化为原始名称参与比对）
     */
    private String getComplaintDuplicateKey(Complaint complaint) {
        return keyPart(ValidateUtil.isNotEmpty(complaint.getPreId()) ? complaint.getPreId() : complaint.getOriginalProjectName())
                + "|" + keyPart(ValidateUtil.isNotEmpty(complaint.getUnitId()) ? complaint.getUnitId() : complaint.getOriginalUnitName())
                + "|" + keyPart(complaint.getReporterName())
                + "|" + keyPart(complaint.getPhoneNumber())
                + "|" + keyPart(complaint.getComplaintContent())
                + "|" + keyPart(complaint.getComplaintTime())
                + "|" + keyPart(complaint.getCaseSource());
    }

    private String getLawsuitDuplicateKey(Lawsuit lawsuit) {
        return keyPart(ValidateUtil.isNotEmpty(lawsuit.getUnitId()) ? lawsuit.getUnitId() : lawsuit.getOriginalUnitName())
                + "|" + keyPart(ValidateUtil.isNotEmpty(lawsuit.getPreId()) ? lawsuit.getPreId() : lawsuit.getOriginalProjectName())
                + "|" + keyPart(lawsuit.getReceiveTime());
    }

    private String keyPart(Object value) {
        if (value == null) return "";
        if (value instanceof Date) return String.valueOf(((Date) value).getTime());
        return value.toString();
    }

    private List<Complaint> parseComplaintSheet(Sheet sheet, LogUserInfoVo user, List<SysDictionary> caseSourceDictionaries) {
        List<Complaint> complaints = new ArrayList<>();
        Date now = new Date();
        Map<String, SysUser> userMap = getUserMap(collectNormalizedColumnValues(sheet, 2, sheet.getLastRowNum(), 5));
        for (int i = 2; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (isBlankRow(row, 1, 13)) continue;
            String projectName = getCellString(row, 3);
            String unitName = getCellString(row, 4);
            String reporterName = getCellString(row, 5);
            RelationMatchResult relationMatch = matchProjectUnitContract(projectName, unitName);
            SysUser reporter = getUserByUserName(reporterName, userMap);
            Complaint complaint = new Complaint();
            complaint.setId(GenerateUUID.getUUID());
            complaint.setComplaintTime(getCellDate(row, 1));
            complaint.setReplyTime(getCellDate(row, 2));
            complaint.setPreId(relationMatch.getProjectId());
            complaint.setUnitId(relationMatch.getUnitId());
            complaint.setContractId(relationMatch.getContractId());
            complaint.setOriginalProjectName(projectName);
            complaint.setOriginalUnitName(unitName);
            fillMatchInfo(complaint, relationMatch);
            complaint.setReporterName(reporter == null ? reporterName : reporter.getuName());
            complaint.setReporterId(reporter == null ? null : reporter.getuId());
            complaint.setPhoneNumber(getCellString(row, 6));
            complaint.setCaseSource(getDictionaryCodeByFuzzy(caseSourceDictionaries, getCellString(row, 7)));
            complaint.setComplaintContent(getCellString(row, 8));
            complaint.setInvestigationResult(getCellString(row, 9));
            complaint.setWageCount(getCellIntegerOrNull(row, 10));
            complaint.setWageAmount(getCellBigDecimalOrNull(row, 11));
            complaint.setBlacklistStatus(normalizeYesNo(getCellString(row, 12)));
            complaint.setIdentityNumber(getCellString(row, 13));
            fillCreateInfo(complaint, user, now);
            complaints.add(complaint);
        }
        return complaints;
    }

    private Sheet getRequiredSheet(Workbook workbook, String sheetName) {
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            throw new JPMException("未找到工作表：" + sheetName);
        }
        return sheet;
    }

    private List<Lawsuit> parseLawsuitSheet(Sheet sheet, LogUserInfoVo user, List<SysDictionary> unitTypeDictionaries) {
        List<Lawsuit> lawsuits = new ArrayList<>();
        Date now = new Date();
        Map<String, SysUser> userMap = getUserMap(collectNormalizedColumnValues(sheet, 2, sheet.getLastRowNum(), 10));
        for (int i = 2; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (isBlankRow(row, 1, 11)) continue;
            String unitName = getCellString(row, 1);
            String projectName = getCellString(row, 2);
            String informedPerson = getCellString(row, 10);
            RelationMatchResult relationMatch = matchProjectUnitContract(projectName, unitName);
            PreProject project = ValidateUtil.isEmpty(relationMatch.getProjectId()) ? null : preProjectMapper.selectById(relationMatch.getProjectId());
            SysUser informedUser = getUserByUserName(informedPerson, userMap);
            Lawsuit lawsuit = new Lawsuit();
            lawsuit.setId(GenerateUUID.getUUID());
            lawsuit.setUnitId(relationMatch.getUnitId());
            lawsuit.setPreId(relationMatch.getProjectId());
            lawsuit.setContractId(relationMatch.getContractId());
            lawsuit.setOriginalProjectName(projectName);
            lawsuit.setOriginalUnitName(unitName);
            fillMatchInfo(lawsuit, relationMatch);
            lawsuit.setUnitType(getDictionaryCodeByFuzzy(unitTypeDictionaries, getCellString(row, 3)));
            lawsuit.setProjectCategory(project == null ? null : project.getpType());
            lawsuit.setReceiveTime(getCellDate(row, 5));
            lawsuit.setCaseClosed(normalizeYesNo(getCellString(row, 6)));
            lawsuit.setDirectInterest(normalizeYesNo(getCellString(row, 7)));
            lawsuit.setDisputeNegotiation(normalizeYesNo(getCellString(row, 8)));
            lawsuit.setCaseSummary(getCellString(row, 9));
            lawsuit.setInformedPerson(informedUser == null ? informedPerson : informedUser.getuName());
            lawsuit.setInformedPersonId(informedUser == null ? null : informedUser.getuId());
            lawsuit.setRemark(getCellString(row, 11));
            fillCreateInfo(lawsuit, user, now);
            lawsuits.add(lawsuit);
        }
        return lawsuits;
    }

    /**
     * 匹配项目、单位和合同关系（三级匹配策略）
     * <p>
     * 按照优先级依次尝试以下匹配策略：
     * 1. 通过项目名称精确匹配项目，再关联单位和合同
     * 2. 通过合同名称匹配合同，从合同反推项目和单位
     * 3. 仅通过单位名称匹配单位（无法确定项目和合同）
     * </p>
     *
     * @param originalProjectName 原始项目名称（来自Excel，可能包含空格、全角字符等）
     * @param originalUnitName    原始单位名称（来自Excel，可能包含空格、全角字符等）
     * @return RelationMatchResult 匹配结果对象，包含：
     *         - projectId: 匹配到的项目ID（未匹配则为null）
     *         - unitId: 匹配到的单位ID（未匹配则为null）
     *         - contractId: 匹配到的合同ID（未匹配则为null）
     *         - matchStatus: 匹配状态码（00-完全匹配, 01-缺单位/合同, 02-缺项目, 0102-都无法匹配）
     *         - needManualConfirm: 是否需要人工确认
     *         - missingType: 缺失类型（01-缺项目, 02-缺单位/合同, 0102-都缺失）
     *         - missingInfo: 详细的缺失信息描述
     */
    private RelationMatchResult matchProjectUnitContract(String originalProjectName, String originalUnitName) {
        RelationMatchResult result = new RelationMatchResult(originalProjectName, originalUnitName);
        String projectName = normalizeLookupName(originalProjectName);
        String unitName = normalizeLookupName(originalUnitName);
        List<PreProject> projectCandidates = selectProjectCandidates(projectName);
        List<UnitCandidate> unitCandidates = selectUnitCandidates(unitName);

        // 第一级匹配：通过项目名称唯一确定项目
        if (projectCandidates.size() == 1) {
            resolveWithConfirmedProject(result, projectCandidates.get(0).getpId(), unitCandidates);
            finishRelationMatch(result);
            return result;
        }

        // 第二级匹配：通过合同名称匹配合同
        List<ContractCandidate> contractCandidates = selectContractCandidatesByName(projectName);

        // 情况1：唯一合同，直接从合同获取项目和合同ID
        if (contractCandidates.size() == 1) {
            ContractCandidate contract = contractCandidates.get(0);
            result.setProjectId(contract.getProjectId());
            result.setContractId(contract.getConId());
            resolveUnitByCandidateContracts(result, unitCandidates, contractCandidates, true);
            finishRelationMatch(result);
            return result;
        }

        // 情况2：多个合同但属于同一项目，确定项目ID
        if (!contractCandidates.isEmpty() && isSameProject(contractCandidates)) {
            result.setProjectId(contractCandidates.get(0).getProjectId());
            resolveUnitByCandidateContracts(result, unitCandidates, contractCandidates, false);
            finishRelationMatch(result);
            return result;
        }

        // 情况3：多个合同且不同项目，仅通过合同交集匹配单位
        if (!contractCandidates.isEmpty()) {
            resolveUnitByCandidateContracts(result, unitCandidates, contractCandidates, false);
            finishRelationMatch(result);
            return result;
        }

        // 第三级匹配：仅通过单位名称匹配单位
        if (unitCandidates.size() == 1) {
            result.setUnitId(unitCandidates.get(0).getUnitId());
        }
        finishRelationMatch(result);
        return result;
    }

    private void resolveWithConfirmedProject(RelationMatchResult result, String projectId, List<UnitCandidate> unitCandidates) {
        result.setProjectId(projectId);
        List<ContractCandidate> projectContracts = selectContractsByProjectId(projectId);
        resolveUnitByCandidateContracts(result, unitCandidates, projectContracts, false);
    }

    private void resolveUnitByCandidateContracts(RelationMatchResult result, List<UnitCandidate> unitCandidates,
                                                 List<ContractCandidate> candidateContracts, boolean keepSingleContract) {
        if (unitCandidates.isEmpty()) return;
        if (unitCandidates.size() == 1) {
            UnitCandidate unit = unitCandidates.get(0);
            result.setUnitId(unit.getUnitId());
            List<ContractCandidate> intersection = intersectContracts(candidateContracts, selectContractsByUnitId(unit.getUnitId()));
            if (intersection.size() == 1) {
                fillRelationByContract(result, unit.getUnitId(), intersection.get(0));
            } else if (!keepSingleContract || intersection.isEmpty()) {
                result.setContractId(null);
            }
            return;
        }

        List<RelationMatchResult> matchedResults = new ArrayList<>();
        for (UnitCandidate unit : unitCandidates) {
            List<ContractCandidate> intersection = intersectContracts(candidateContracts, selectContractsByUnitId(unit.getUnitId()));
            if (intersection.size() == 1) {
                RelationMatchResult matched = new RelationMatchResult(result.getOriginalProjectName(), result.getOriginalUnitName());
                fillRelationByContract(matched, unit.getUnitId(), intersection.get(0));
                matchedResults.add(matched);
            }
        }
        if (matchedResults.size() == 1) {
            RelationMatchResult matched = matchedResults.get(0);
            result.setProjectId(matched.getProjectId());
            result.setUnitId(matched.getUnitId());
            result.setContractId(matched.getContractId());
        } else if (!keepSingleContract) {
            result.setContractId(null);
        }
    }

    private void finishRelationMatch(RelationMatchResult result) {
        if (ValidateUtil.isNotEmpty(result.getProjectId())
                && ValidateUtil.isNotEmpty(result.getUnitId())
                && ValidateUtil.isNotEmpty(result.getContractId())) {
            result.setMatchStatus("00");
            result.setNeedManualConfirm(false);
            result.setMissingType(null);
            result.setMissingInfo(null);
            return;
        }
        result.setNeedManualConfirm(true);
        if (ValidateUtil.isNotEmpty(result.getProjectId())) {
            result.setMatchStatus("01");
            result.setMissingType("02");
            result.setMissingInfo(buildMissingInfo(result, true, false));
            return;
        }
        if (ValidateUtil.isNotEmpty(result.getUnitId())) {
            result.setMatchStatus("02");
            result.setMissingType("01");
            result.setMissingInfo(buildMissingInfo(result, false, true));
            return;
        }
        result.setMatchStatus("0102");
        result.setMissingType("0102");
        result.setMissingInfo(buildMissingInfo(result, false, false));
    }

    private String buildMissingInfo(RelationMatchResult result, boolean projectMatched, boolean unitMatched) {
        StringBuilder info = new StringBuilder();
        if (!projectMatched) {
            if (ValidateUtil.isEmpty(result.getOriginalProjectName())) {
                info.append("项目名称为空；");
            } else {
                info.append("项目“").append(result.getOriginalProjectName()).append("”无法唯一确认；");
            }
        }
        if (!unitMatched) {
            if (ValidateUtil.isEmpty(result.getOriginalUnitName())) {
                info.append("单位名称为空；");
            } else {
                info.append("单位“").append(result.getOriginalUnitName()).append("”或合同关系无法唯一确认；");
            }
        }
        if (projectMatched && unitMatched && ValidateUtil.isEmpty(result.getContractId())) {
            info.append("项目与单位之间的合同关系无法唯一确认；");
        }
        return info.toString();
    }

    private void fillRelationByContract(RelationMatchResult result, String unitId, ContractCandidate contract) {
        result.setProjectId(contract.getProjectId());
        result.setUnitId(unitId);
        result.setContractId(contract.getConId());
    }

    private boolean isSameProject(List<ContractCandidate> contracts) {
        String projectId = null;
        for (ContractCandidate contract : contracts) {
            if (ValidateUtil.isEmpty(contract.getProjectId())) return false;
            if (projectId == null) {
                projectId = contract.getProjectId();
            } else if (!projectId.equals(contract.getProjectId())) {
                return false;
            }
        }
        return projectId != null;
    }

    private List<ContractCandidate> intersectContracts(List<ContractCandidate> left, List<ContractCandidate> right) {
        List<ContractCandidate> result = new ArrayList<>();
        if (left == null || right == null || left.isEmpty() || right.isEmpty()) return result;
        Set<String> rightIds = new HashSet<>();
        for (ContractCandidate contract : right) {
            if (ValidateUtil.isNotEmpty(contract.getConId())) rightIds.add(contract.getConId());
        }
        Set<String> added = new HashSet<>();
        for (ContractCandidate contract : left) {
            if (ValidateUtil.isNotEmpty(contract.getConId()) && rightIds.contains(contract.getConId()) && added.add(contract.getConId())) {
                result.add(contract);
            }
        }
        return result;
    }

    private List<PreProject> selectProjectCandidates(String projectName) {
        if (ValidateUtil.isEmpty(projectName)) return new ArrayList<>();
        List<PreProject> projects = preProjectMapper.selectProjectCandidatesByName(projectName);
        return projects == null ? new ArrayList<>() : deduplicateProjects(projects);
    }

    private List<PreProject> deduplicateProjects(List<PreProject> projects) {
        List<PreProject> result = new ArrayList<>();
        Set<String> ids = new HashSet<>();
        for (PreProject project : projects) {
            if (project != null && ValidateUtil.isNotEmpty(project.getpId()) && ids.add(project.getpId())) {
                result.add(project);
            }
        }
        return result;
    }

    private List<UnitCandidate> selectUnitCandidates(String unitName) {
        List<UnitCandidate> candidates = new ArrayList<>();
        if (ValidateUtil.isEmpty(unitName)) return candidates;
        List<Map> units = preProjectMapper.selectCompanyCandidatesByName(unitName);
        if (units == null) return candidates;
        Set<String> ids = new HashSet<>();
        for (Map unit : units) {
            Object cId = getMapValue(unit, "cId");
            if (cId != null && ids.add(cId.toString())) {
                candidates.add(new UnitCandidate(cId.toString()));
            }
        }
        return candidates;
    }

    private List<ContractCandidate> selectContractsByProjectId(String projectId) {
        if (ValidateUtil.isEmpty(projectId)) return new ArrayList<>();
        return toContractCandidates(preProjectMapper.selectContractsByProjectId(projectId));
    }

    private List<ContractCandidate> selectContractsByUnitId(String unitId) {
        if (ValidateUtil.isEmpty(unitId)) return new ArrayList<>();
        return toContractCandidates(preProjectMapper.selectContractsByPartyBId(unitId));
    }

    private List<ContractCandidate> selectContractCandidatesByName(String contractName) {
        if (ValidateUtil.isEmpty(contractName)) return new ArrayList<>();
        return toContractCandidates(preProjectMapper.selectContractsByName(contractName));
    }

    private List<ContractCandidate> toContractCandidates(List<Map> maps) {
        List<ContractCandidate> candidates = new ArrayList<>();
        if (maps == null) return candidates;
        Set<String> ids = new HashSet<>();
        for (Map map : maps) {
            Object conId = getMapValue(map, "conId");
            Object projectId = getMapValue(map, "projectId");
            if (conId != null && ids.add(conId.toString())) {
                candidates.add(new ContractCandidate(conId.toString(), projectId == null ? null : projectId.toString()));
            }
        }
        return candidates;
    }

    private void fillMatchInfo(Complaint complaint, RelationMatchResult relationMatch) {
        complaint.setMatchStatus(relationMatch.getMatchStatus());
        complaint.setNeedManualConfirm(relationMatch.getNeedManualConfirm());
        complaint.setMissingType(relationMatch.getMissingType());
        complaint.setMissingInfo(relationMatch.getMissingInfo());
    }

    private void fillMatchInfo(Lawsuit lawsuit, RelationMatchResult relationMatch) {
        lawsuit.setMatchStatus(relationMatch.getMatchStatus());
        lawsuit.setNeedManualConfirm(relationMatch.getNeedManualConfirm());
        lawsuit.setMissingType(relationMatch.getMissingType());
        lawsuit.setMissingInfo(relationMatch.getMissingInfo());
    }

    private static class RelationMatchResult {
        private final String originalProjectName;
        private final String originalUnitName;
        private String projectId;
        private String unitId;
        private String contractId;
        private String matchStatus;
        private Boolean needManualConfirm;
        private String missingType;
        private String missingInfo;

        RelationMatchResult(String originalProjectName, String originalUnitName) {
            this.originalProjectName = originalProjectName;
            this.originalUnitName = originalUnitName;
        }

        String getOriginalProjectName() {
            return originalProjectName;
        }

        String getOriginalUnitName() {
            return originalUnitName;
        }

        String getProjectId() {
            return projectId;
        }

        void setProjectId(String projectId) {
            this.projectId = projectId;
        }

        String getUnitId() {
            return unitId;
        }

        void setUnitId(String unitId) {
            this.unitId = unitId;
        }

        String getContractId() {
            return contractId;
        }

        void setContractId(String contractId) {
            this.contractId = contractId;
        }

        String getMatchStatus() {
            return matchStatus;
        }

        void setMatchStatus(String matchStatus) {
            this.matchStatus = matchStatus;
        }

        Boolean getNeedManualConfirm() {
            return needManualConfirm;
        }

        void setNeedManualConfirm(Boolean needManualConfirm) {
            this.needManualConfirm = needManualConfirm;
        }

        String getMissingType() {
            return missingType;
        }

        void setMissingType(String missingType) {
            this.missingType = missingType;
        }

        String getMissingInfo() {
            return missingInfo;
        }

        void setMissingInfo(String missingInfo) {
            this.missingInfo = missingInfo;
        }
    }

    private static class UnitCandidate {
        private final String unitId;

        UnitCandidate(String unitId) {
            this.unitId = unitId;
        }

        String getUnitId() {
            return unitId;
        }
    }

    private static class ContractCandidate {
        private final String conId;
        private final String projectId;

        ContractCandidate(String conId, String projectId) {
            this.conId = conId;
            this.projectId = projectId;
        }

        String getConId() {
            return conId;
        }

        String getProjectId() {
            return projectId;
        }
    }

    private void fillCreateInfo(Complaint complaint, LogUserInfoVo user, Date now) {
        complaint.setCreateUid(user.getId());
        complaint.setCreateUname(user.getName());
        complaint.setCreateTime(now);
        complaint.setState(7);
        complaint.setStateTime(now);
    }

    private void fillCreateInfo(Lawsuit lawsuit, LogUserInfoVo user, Date now) {
        lawsuit.setCreateUid(user.getId());
        lawsuit.setCreateUname(user.getName());
        lawsuit.setCreateTime(now);
        lawsuit.setState(7);
        lawsuit.setStateTime(now);
    }

    private boolean isBlankRow(Row row, int startIndex, int endIndex) {
        if (row == null) return true;
        for (int i = startIndex; i <= endIndex; i++) {
            if (ValidateUtil.isNotEmpty(getCellString(row, i))) return false;
        }
        return true;
    }

    private String getCellString(Row row, int index) {
        if (row == null) return "";
        return getCellString(row.getCell(index));
    }

    private String getCellString(Cell cell) {
        if (cell == null) return "";
        DataFormatter formatter = new DataFormatter();
        String value = formatter.formatCellValue(cell);
        return value == null ? "" : value.trim();
    }

    private Date getCellDate(Row row, int index) {
        if (row == null) return null;
        Cell cell = row.getCell(index);
        if (cell == null) return null;
        try {
            if (cell.getCellType() == org.apache.poi.ss.usermodel.CellType.NUMERIC
                    || cell.getCellType() == org.apache.poi.ss.usermodel.CellType.FORMULA) {
                return org.apache.poi.ss.usermodel.DateUtil.getJavaDate(cell.getNumericCellValue());
            }
        } catch (Exception ignored) {
        }
        return parseDate(getCellString(cell));
    }

    private Date parseDate(String value) {
        if (ValidateUtil.isEmpty(value)) return null;
        String text = value.trim()
                .replace("年", "-")
                .replace("月", "-")
                .replace("日", "")
                .replace("/", "-")
                .replace(".", "-");
        String[] patterns = {"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd", "yyyy-M-d"};
        for (String pattern : patterns) {
            try {
                return new SimpleDateFormat(pattern).parse(text);
            } catch (Exception ignored) {
            }
        }
        try {
            return org.apache.poi.ss.usermodel.DateUtil.getJavaDate(Double.parseDouble(text));
        } catch (Exception ignored) {
        }
        return null;
    }

    private Integer getCellIntegerOrNull(Row row, int index) {
        String value = getCellString(row, index);
        if (ValidateUtil.isEmpty(value)) return null;
        try {
            return new BigDecimal(normalizeNumber(value)).intValue();
        } catch (Exception e) {
            log.warn("举报台账导入数字解析失败，位置：{}，原始值：{}", getCellPosition(row, index), value);
            return null;
        }
    }

    private BigDecimal getCellBigDecimalOrNull(Row row, int index) {
        String value = getCellString(row, index);
        if (ValidateUtil.isEmpty(value)) return null;
        try {
            return new BigDecimal(normalizeNumber(value));
        } catch (Exception e) {
            log.warn("举报台账导入数字解析失败，位置：{}，原始值：{}", getCellPosition(row, index), value);
            return null;
        }
    }

    private String getCellPosition(Row row, int index) {
        int rowNum = row == null ? 0 : row.getRowNum() + 1;
        return "第" + rowNum + "行第" + toExcelColumnName(index) + "列";
    }

    private String toExcelColumnName(int zeroBasedIndex) {
        int column = zeroBasedIndex + 1;
        StringBuilder name = new StringBuilder();
        while (column > 0) {
            int remainder = (column - 1) % 26;
            name.insert(0, (char) ('A' + remainder));
            column = (column - 1) / 26;
        }
        return name.toString();
    }

    private String normalizeNumber(String value) {
        return value.replace(",", "")
                .replace("，", "")
                .replace("元", "")
                .trim();
    }

    private SysFile buildSysFile(String id, String pId, LogUserInfoVo user, Date createTime, String tCode, String title) {
        SysFileTemplate template = sysFileTemplateService.getByCode(tCode);
        SysFile sysFile = new SysFile();
        sysFile.setFileid(id);
        sysFile.setFiletitle(template == null ? title : template.gettName());
        sysFile.setFilecode(tCode);
        sysFile.settCode(tCode);
        if (template != null) {
            sysFile.settId(template.gettId());
        }
        sysFile.setFileflowstatus("7");
        sysFile.setFilecreateuser(user.getId());
        sysFile.setFilecreateusername(user.getName());
        sysFile.setFilecreatedate(createTime);
        sysFile.setFileproject(pId);
        sysFile.setFileRelationId(GenerateUUID.getUUID());
        sysFile.setTeId(user.getTeId());
        return sysFile;
    }

    private Set<String> collectNormalizedColumnValues(Sheet sheet, int startRow, int endRow, int columnIndex) {
        Set<String> values = new HashSet<>();
        for (int i = startRow; i <= endRow; i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;
            String value = normalizeLookupName(getCellString(row, columnIndex));
            if (ValidateUtil.isNotEmpty(value)) values.add(value);
        }
        return values;
    }

    private Map<String, PreProject> getProjectMap(Set<String> projectNames) {
        Map<String, PreProject> projectMap = new HashMap<>();
        if (projectNames == null || projectNames.isEmpty()) return projectMap;
        List<PreProject> projects = preProjectMapper.getProjectsByNamesIgnoreSpace(projectNames);
        if (projects != null) {
            for (PreProject project : projects) {
                String key = normalizeLookupName(project.getpName());
                if (ValidateUtil.isNotEmpty(key) && !projectMap.containsKey(key)) {
                    projectMap.put(key, project);
                }
            }
        }
        return projectMap;
    }

    private Map<String, String> getUnitIdMap(Set<String> unitNames) {
        Map<String, String> unitMap = new HashMap<>();
        if (unitNames == null || unitNames.isEmpty()) return unitMap;
        List<Map> units = preProjectMapper.getCompanyIdsByNamesIgnoreSpace(unitNames);
        if (units != null) {
            for (Map unit : units) {
                Object nameKey = getMapValue(unit, "nameKey");
                Object cId = getMapValue(unit, "cId");
                if (nameKey != null && cId != null) {
                    unitMap.put(nameKey.toString(), cId.toString());
                }
            }
        }
        return unitMap;
    }

    private Map<String, SysUser> getUserMap(Set<String> userNames) {
        Map<String, SysUser> userMap = new HashMap<>();
        if (userNames == null || userNames.isEmpty()) return userMap;
        List<SysUser> users = sysUserMapper.getUsersByNamesIgnoreSpace(userNames);
        if (users != null) {
            for (SysUser user : users) {
                String key = normalizeLookupName(user.getuName());
                if (ValidateUtil.isNotEmpty(key) && !userMap.containsKey(key)) {
                    userMap.put(key, user);
                }
            }
        }
        return userMap;
    }

    private Object getMapValue(Map map, String key) {
        if (map.containsKey(key)) return map.get(key);
        String upperKey = key.toUpperCase();
        if (map.containsKey(upperKey)) return map.get(upperKey);
        String lowerKey = key.toLowerCase();
        if (map.containsKey(lowerKey)) return map.get(lowerKey);
        return null;
    }

    private PreProject getRequiredProjectByProjectName(String projectName, int rowNumber, Map<String, PreProject> projectMap) {
        String normalizedProjectName = normalizeLookupName(projectName);
        if (ValidateUtil.isEmpty(normalizedProjectName)) {
            throw new JPMException(String.format("第%s行数据，项目不存在，请检查项目名称是否正确。", rowNumber));
        }
        PreProject project = projectMap.get(normalizedProjectName);
        if (project == null) {
            throw new JPMException(String.format("第%s行数据，项目不存在，请检查项目名称是否正确。", rowNumber));
        }
        return project;
    }

    private String getRequiredUnitIdByUnitName(String unitName, int rowNumber, Map<String, String> unitMap) {
        String normalizedUnitName = normalizeLookupName(unitName);
        if (ValidateUtil.isEmpty(normalizedUnitName)) {
            throw new JPMException(String.format("第%s行数据，单位不存在，请检查单位名称是否正确。", rowNumber));
        }
        String unitId = unitMap.get(normalizedUnitName);
        if (ValidateUtil.isEmpty(unitId)) {
            throw new JPMException(String.format("第%s行数据，单位不存在，请检查单位名称是否正确。", rowNumber));
        }
        return unitId;
    }

    private SysUser getUserByUserName(String userName, Map<String, SysUser> userMap) {
        String normalizedUserName = normalizeLookupName(userName);
        if (ValidateUtil.isEmpty(normalizedUserName)) return null;
        return userMap.get(normalizedUserName);
    }

    private String getDictionaryCodeByFuzzy(List<SysDictionary> dictionaries, String value) {
        String normalizedValue = normalizeLookupName(value);
        if (ValidateUtil.isEmpty(normalizedValue) || dictionaries == null || dictionaries.isEmpty()) return null;
        for (SysDictionary dictionary : dictionaries) {
            String dictionaryName = normalizeLookupName(dictionary.getDName());
            if (ValidateUtil.isNotEmpty(dictionaryName)
                    && (dictionaryName.contains(normalizedValue) || normalizedValue.contains(dictionaryName))) {
                return dictionary.getDCode();
            }
        }
        return null;
    }

    private String normalizeYesNo(String value) {
        String normalizedValue = normalizeLookupName(value);
        if ("是".equals(normalizedValue)) return "01";
        if ("否".equals(normalizedValue)) return "02";
        return null;
    }

    private String normalizeLookupName(String value) {
        if (value == null) return "";
        return value.replace(" ", "")
                .replace("　", "")
                .replace("(", "（")
                .replace(")", "）")
                .trim();
    }

    /**
     * 检验批质量验收记录 数据导入
     *
     * @param excel
     * @param user
     * @param decPath
     * @param tCode
     * @param root
     */
    private void importCheckGroupQualityAcceptance(File excel, LogUserInfoVo user, String decPath, String tCode, String root) {
        // 解析excel
        CheckGroupQualityAcceptanceDTO checkGroupQualityAcceptanceDTO = parseCheckGroupQualityAcceptanceDTO(excel);
        // 将dto转换为数据库实体对象
        CheckGroupQualityAcceptance checkGroupQualityAcceptance = new CheckGroupQualityAcceptance();
        BeanUtils.copyProperties(checkGroupQualityAcceptanceDTO, checkGroupQualityAcceptance);

        // 生成主表实体主键id
        String entityId = UUID.randomUUID().toString();
        checkGroupQualityAcceptance.setUuid(entityId);

        // 与关联表进行关联：通过名称获取到关联表中的id，如项目表通过项目名称获取到项目id
        fillRelationTableDataIdOfCheckGroupQualityAcceptance(checkGroupQualityAcceptanceDTO,
                checkGroupQualityAcceptance, decPath, user, tCode, root);

        List<CheckGroupQualityAcceptanceChild> childList = new ArrayList<>();
        for (CheckGroupQualityAcceptanceChildDTO checkGroupQualityAcceptanceChildDTO : checkGroupQualityAcceptanceDTO.getChildDTOList()) {
            CheckGroupQualityAcceptanceChild checkGroupQualityAcceptanceChild = new CheckGroupQualityAcceptanceChild();
            BeanUtils.copyProperties(checkGroupQualityAcceptanceChildDTO, checkGroupQualityAcceptanceChild);
            checkGroupQualityAcceptanceChild.setUuid(UUID.randomUUID().toString());
            checkGroupQualityAcceptanceChild.setCheckGroupQualityAcceptanceId(entityId);
            childList.add(checkGroupQualityAcceptanceChild);
        }
        checkGroupQualityAcceptance.setChildList(childList);

        // 保存数据到数据表
        checkGroupQualityAcceptanceMapper.insert(checkGroupQualityAcceptance);

        // 保存子表信息
        for (CheckGroupQualityAcceptanceChild checkGroupQualityAcceptanceChild : childList) {
            checkGroupQualityAcceptanceChildMapper.insert(checkGroupQualityAcceptanceChild);
        }

    }

    @SneakyThrows
    private CheckGroupQualityAcceptanceDTO parseCheckGroupQualityAcceptanceDTO(File excel) {
        Workbook book = null;
        if (excel.getName().endsWith("xlsx")) {
            book = new XSSFWorkbook(new FileInputStream(excel));//Excel 2007
        } else if (excel.getName().endsWith("xls")) {
            book = new HSSFWorkbook(new FileInputStream(excel));//Excel 2003
        }
        try {

        Sheet sheet = book.getSheetAt(0);

        // 标题
        String title = sheet.getRow(0).getCell(0).getStringCellValue();

        // 编号
        String code = sheet.getRow(0).getCell(9).getStringCellValue();

        // 项目名称
        String projectName = sheet.getRow(1).getCell(2).getStringCellValue();

        // 合同名称
        String contractName = sheet.getRow(2).getCell(2).getStringCellValue();

        // 单位（子单位）工程名称
        String unitEngineeringName = sheet.getRow(3).getCell(2).getStringCellValue();

        // 分部（子分部）工程名称
        String branchEngineeringName = sheet.getRow(3).getCell(6).getStringCellValue();

        // 分项工程名称
        String subItemEngineeringName = sheet.getRow(3).getCell(9).getStringCellValue();

        // 施工单位
        String constructionUnitName = sheet.getRow(4).getCell(2).getStringCellValue();

        // 项目负责人
        String projectDirectorName = sheet.getRow(4).getCell(6).getStringCellValue();

        // 检验批容量
        String checkGroupCapacity = sheet.getRow(4).getCell(9).getStringCellValue();

        // 分包单位
        String subpackageUnitName = sheet.getRow(5).getCell(2).getStringCellValue();

        // 分包单位项目负责人
        String subpackageUnitProjectDirectorName = sheet.getRow(5).getCell(6).getStringCellValue();

        // 检验批部位
        String checkGroupPart = sheet.getRow(5).getCell(9).getStringCellValue();

        // 施工依据
        String constructionBasis = sheet.getRow(6).getCell(2).getStringCellValue();

        // 验收依据
        String acceptanceBasis = sheet.getRow(6).getCell(6).getStringCellValue();

        // 施工单位检查结果
        String constructionUnitCheckResult = sheet.getRow(7).getCell(2).getStringCellValue();

        // 专业工长
        String professionalForemanName = sheet.getRow(7).getCell(5).getStringCellValue();

        // 项目专业质量检查员
        String projectProfessionalQualityCheckerName = sheet.getRow(7).getCell(7).getStringCellValue();

        // 项目专业质量检查员签字日期
        Date projectProfessionalQualityCheckerSignDate = sheet.getRow(7).getCell(9).getDateCellValue();

        // 监理单位验收结论
        String superviseUnitAcceptanceConclusion = sheet.getRow(8).getCell(3).getStringCellValue();

        // 专业监理工程师
        String professionalSuperviseEngineerName = sheet.getRow(8).getCell(7).getStringCellValue();

        // 专业监理工程师签字日期
        Date professionalSuperviseEngineerSignDate = sheet.getRow(8).getCell(9).getDateCellValue();

        // 附件
        String filesLink = sheet.getRow(9).getCell(3).getStringCellValue();

        List<CheckGroupQualityAcceptanceChildDTO> childDTOList = new ArrayList<>();

        for (int i = 11; i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            // 验收项目类型
            String acceptanceProjectType = row.getCell(1).getStringCellValue();

            // 验收项目
            String acceptanceProjectName = row.getCell(2).getStringCellValue();

            // 设计要求及规范规定
            String designRequirement = row.getCell(4).getStringCellValue();

            // 最小/实际抽样数
            String maximumSamplingNumber = row.getCell(5).getStringCellValue();

            // 检查记录
            String checkRecord = row.getCell(7).getStringCellValue();

            // 检查结果
            String checkResult = row.getCell(9).getStringCellValue();

            CheckGroupQualityAcceptanceChildDTO checkGroupQualityAcceptanceChildDTO = CheckGroupQualityAcceptanceChildDTO.builder()
                    .acceptanceProjectType(acceptanceProjectType)
                    .acceptanceProjectName(acceptanceProjectName)
                    .designRequirement(designRequirement)
                    .maximumSamplingNumber(maximumSamplingNumber)
                    .checkRecord(checkRecord)
                    .checkResult(checkResult)
                    .build();
            childDTOList.add(checkGroupQualityAcceptanceChildDTO);
        }

        CheckGroupQualityAcceptanceDTO checkGroupQualityAcceptanceDTO = CheckGroupQualityAcceptanceDTO.builder()
                .title(title)
                .code(code)
                .projectName(projectName)
                .contractName(contractName)
                .unitEngineeringName(unitEngineeringName)
                .branchEngineeringName(branchEngineeringName)
                .subItemEngineeringName(subItemEngineeringName)
                .constructionUnitName(constructionUnitName)
                .projectDirectorName(projectDirectorName)
                .checkGroupCapacity(checkGroupCapacity)
                .subpackageUnitName(subpackageUnitName)
                .subpackageUnitProjectDirectorName(subpackageUnitProjectDirectorName)
                .checkGroupPart(checkGroupPart)
                .constructionBasis(constructionBasis)
                .acceptanceBasis(acceptanceBasis)
                .constructionUnitCheckResult(constructionUnitCheckResult)
                .professionalForemanName(professionalForemanName)
                .projectProfessionalQualityCheckerName(projectProfessionalQualityCheckerName)
                .projectProfessionalQualityCheckerSignDate(projectProfessionalQualityCheckerSignDate)
                .superviseUnitAcceptanceConclusion(superviseUnitAcceptanceConclusion)
                .professionalSuperviseEngineerName(professionalSuperviseEngineerName)
                .professionalSuperviseEngineerSignDate(professionalSuperviseEngineerSignDate)
                .filesLink(filesLink)
                .childDTOList(childDTOList)
                .build();
            return checkGroupQualityAcceptanceDTO;
        } finally {
            book.close();
        }
    }

    private void fillRelationTableDataIdOfCheckGroupQualityAcceptance(CheckGroupQualityAcceptanceDTO checkGroupQualityAcceptanceDTO
            , CheckGroupQualityAcceptance checkGroupQualityAcceptance, String decPath, LogUserInfoVo user, String tCode, String root) {
        // 项目名称 -> 项目id
        checkGroupQualityAcceptance.setProjectId(getProjectIdByProjectName(checkGroupQualityAcceptanceDTO.getProjectName()));

        // 合同名称 -> 合同id
        checkGroupQualityAcceptance.setContractId(getContractIdByContractName(checkGroupQualityAcceptanceDTO.getContractName()));

        // 施工单位
        checkGroupQualityAcceptance.setConstructionUnitId(getUnitIdByUnitName(checkGroupQualityAcceptanceDTO.getConstructionUnitName()));

        // 分包单位
        checkGroupQualityAcceptance.setSubpackageUnitId(getUnitIdByUnitName(checkGroupQualityAcceptanceDTO.getSubpackageUnitName()));

        // 文件id
        String fileRelationId = UUID.randomUUID().toString();
        saveFileByFileLink(checkGroupQualityAcceptanceDTO.getFilesLink(), checkGroupQualityAcceptance.getUuid(), fileRelationId, decPath, user, tCode, root);
        checkGroupQualityAcceptance.setFilesRelationId(fileRelationId);
    }

    /**
     * 分项工程质量验收 数据导入
     *
     * @param excel
     * @param user
     * @param decPath
     * @param tCode
     * @param root
     */
    private void importSubItemEngineeringQualityAcceptance(File excel, LogUserInfoVo user, String decPath, String tCode, String root) {
        // 解析excel
        SubItemEngineeringQualityAcceptanceDTO subItemEngineeringQualityAcceptanceDTO = parseSubItemEngineeringQualityAcceptanceDTO(excel);

        // 将dto转换为数据库实体对象
        SubItemEngineeringQualityAcceptance subItemEngineeringQualityAcceptance = new SubItemEngineeringQualityAcceptance();
        BeanUtils.copyProperties(subItemEngineeringQualityAcceptanceDTO, subItemEngineeringQualityAcceptance);

        // 生成主表实体主键id
        String entityId = UUID.randomUUID().toString();
        subItemEngineeringQualityAcceptance.setUuid(entityId);

        // 与关联表进行关联：通过名称获取到关联表中的id，如项目表通过项目名称获取到项目id
        fillRelationTableDataIdOfSubItemEngineeringQualityAcceptance(subItemEngineeringQualityAcceptanceDTO,
                subItemEngineeringQualityAcceptance, decPath, user, tCode, root);

        List<SubItemEngineeringQualityAcceptanceChild> childList = new ArrayList<>();

        for (SubItemEngineeringQualityAcceptanceChildDTO subItemEngineeringQualityAcceptanceChildDTO : subItemEngineeringQualityAcceptanceDTO.getChildDTOList()) {
            SubItemEngineeringQualityAcceptanceChild subItemEngineeringQualityAcceptanceChild = new SubItemEngineeringQualityAcceptanceChild();
            BeanUtils.copyProperties(subItemEngineeringQualityAcceptanceChildDTO, subItemEngineeringQualityAcceptanceChild);
            subItemEngineeringQualityAcceptanceChild.setUuid(UUID.randomUUID().toString());
            subItemEngineeringQualityAcceptanceChild.setSubItemEngineeringQualityAcceptanceId(entityId);
            childList.add(subItemEngineeringQualityAcceptanceChild);
        }
        subItemEngineeringQualityAcceptance.setChildList(childList);

        // 保存数据到数据表
        subItemEngineeringQualityAcceptanceMapper.insert(subItemEngineeringQualityAcceptance);

        // 保存子表信息
        for (SubItemEngineeringQualityAcceptanceChild subItemEngineeringQualityAcceptanceChild : childList) {
            subItemEngineeringQualityAcceptanceChildMapper.insert(subItemEngineeringQualityAcceptanceChild);
        }
    }

    @SneakyThrows
    private SubItemEngineeringQualityAcceptanceDTO parseSubItemEngineeringQualityAcceptanceDTO(File excel) {
        Workbook book = null;
        if (excel.getName().endsWith("xlsx")) {
            book = new XSSFWorkbook(new FileInputStream(excel));//Excel 2007
        } else if (excel.getName().endsWith("xls")) {
            book = new HSSFWorkbook(new FileInputStream(excel));//Excel 2003
        }
        try {

        Sheet sheet = book.getSheetAt(0);

        // 标题
        String title = sheet.getRow(0).getCell(0).getStringCellValue();

        // 编号
        String code = sheet.getRow(0).getCell(8).getStringCellValue();

        // 项目名称
        String projectName = sheet.getRow(1).getCell(2).getStringCellValue();

        // 合同名称
        String contractName = sheet.getRow(2).getCell(2).getStringCellValue();

        // 单位（子单位）工程名称
        String unitEngineeringName = sheet.getRow(3).getCell(2).getStringCellValue();

        // 分部（子分部）工程名称
        String branchEngineeringName = sheet.getRow(3).getCell(6).getStringCellValue();

        // 分项工程工程量
        String subItemEngineeringEngineeringQuantity = sheet.getRow(4).getCell(2).getStringCellValue();

        // 检验批数量
        Double checkGroupNumber = sheet.getRow(4).getCell(6).getNumericCellValue();

        // 施工单位
        String constructionUnitName = sheet.getRow(5).getCell(2).getStringCellValue();

        // 项目负责人
        String projectDirectorName = sheet.getRow(5).getCell(6).getStringCellValue();

        // 项目技术负责人
        String projectTechDirectorName = sheet.getRow(5).getCell(8).getStringCellValue();

        // 分包单位
        String subpackageUnitName = sheet.getRow(6).getCell(2).getStringCellValue();

        // 分包单位项目负责人
        String subpackageUnitProjectDirectorName = sheet.getRow(6).getCell(6).getStringCellValue();

        // 分包内容
        String subpackageContent = sheet.getRow(6).getCell(8).getStringCellValue();

        // 说明
        String explanation = sheet.getRow(7).getCell(2).getStringCellValue();

        // 施工单位检查结果
        String constructionUnitCheckResult = sheet.getRow(8).getCell(2).getStringCellValue();

        // 项目专业技术负责人
        String projectProfessionalTechDirectorName = sheet.getRow(8).getCell(5).getStringCellValue();

        // 项目专业技术负责人签字日期
        Date projectProfessionalTechDirectorSignDate = sheet.getRow(8).getCell(8).getDateCellValue();

        // 监理单位验收结论
        String superviseUnitAcceptanceConclusion = sheet.getRow(9).getCell(2).getStringCellValue();

        // 专业监理工程师
        String professionalSupervisorName = sheet.getRow(9).getCell(5).getStringCellValue();

        // 专业监理工程师签字日期
        Date professionalSupervisorSignDate = sheet.getRow(9).getCell(8).getDateCellValue();

        // 附件上传
        String filesLink = sheet.getRow(10).getCell(2).getStringCellValue();

        List<SubItemEngineeringQualityAcceptanceChildDTO> childDTOList = new ArrayList<>();
        for (int i = 12; i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            // 检验批名称
            String checkGroupName = row.getCell(1).getStringCellValue();

            // 检验批容量
            Double checkGroupCapacity = row.getCell(3).getNumericCellValue();

            // 部位/区段
            String part = row.getCell(4).getStringCellValue();

            // 施工单位检查结果
            String constructionUnitCheckResultOfChild = row.getCell(5).getStringCellValue();

            // 监理单位验收结论
            String superviseUnitAcceptanceConclusionOfChild = row.getCell(7).getStringCellValue();

            SubItemEngineeringQualityAcceptanceChildDTO subItemEngineeringQualityAcceptanceChildDTO = SubItemEngineeringQualityAcceptanceChildDTO.builder()
                    .checkGroupName(checkGroupName)
                    .checkGroupCapacity(checkGroupCapacity)
                    .part(part)
                    .constructionUnitCheckResult(constructionUnitCheckResultOfChild)
                    .superviseUnitAcceptanceConclusion(superviseUnitAcceptanceConclusionOfChild)
                    .build();
            childDTOList.add(subItemEngineeringQualityAcceptanceChildDTO);
        }

        SubItemEngineeringQualityAcceptanceDTO subItemEngineeringQualityAcceptanceDTO = SubItemEngineeringQualityAcceptanceDTO.builder()
                .title(title)
                .code(code)
                .projectName(projectName)
                .contractName(contractName)
                .unitEngineeringName(unitEngineeringName)
                .branchEngineeringName(branchEngineeringName)
                .subItemEngineeringEngineeringQuantity(subItemEngineeringEngineeringQuantity)
                .checkGroupNumber(checkGroupNumber)
                .constructionUnitName(constructionUnitName)
                .projectDirectorName(projectDirectorName)
                .projectTechDirectorName(projectTechDirectorName)
                .subpackageUnitName(subpackageUnitName)
                .subpackageUnitProjectDirectorName(subpackageUnitProjectDirectorName)
                .subpackageContent(subpackageContent)
                .explanation(explanation)
                .constructionUnitCheckResult(constructionUnitCheckResult)
                .projectProfessionalTechDirectorName(projectProfessionalTechDirectorName)
                .projectProfessionalTechDirectorSignDate(projectProfessionalTechDirectorSignDate)
                .superviseUnitAcceptanceConclusion(superviseUnitAcceptanceConclusion)
                .professionalSupervisorName(professionalSupervisorName)
                .professionalSupervisorSignDate(professionalSupervisorSignDate)
                .filesLink(filesLink)
                .childDTOList(childDTOList)
                .build();
        return subItemEngineeringQualityAcceptanceDTO;
        }finally {
            book.close();
        }
    }

    private void fillRelationTableDataIdOfSubItemEngineeringQualityAcceptance(SubItemEngineeringQualityAcceptanceDTO subItemEngineeringQualityAcceptanceDTO
            , SubItemEngineeringQualityAcceptance subItemEngineeringQualityAcceptance, String decPath, LogUserInfoVo user, String tCode, String root) {
        // 项目名称 -> 项目id
        subItemEngineeringQualityAcceptance.setProjectId(getProjectIdByProjectName(subItemEngineeringQualityAcceptanceDTO.getProjectName()));

        // 合同名称 -> 合同id
        subItemEngineeringQualityAcceptance.setContractId(getContractIdByContractName(subItemEngineeringQualityAcceptanceDTO.getContractName()));

        // 施工单位
        subItemEngineeringQualityAcceptance.setConstructionUnitId(getUnitIdByUnitName(subItemEngineeringQualityAcceptanceDTO.getConstructionUnitName()));

        // 分包单位
        subItemEngineeringQualityAcceptance.setSubpackageUnitId(getUnitIdByUnitName(subItemEngineeringQualityAcceptanceDTO.getSubpackageUnitName()));

        // 文件id
        String fileRelationId = UUID.randomUUID().toString();
        saveFileByFileLink(subItemEngineeringQualityAcceptanceDTO.getFilesLink(), subItemEngineeringQualityAcceptance.getUuid(), fileRelationId, decPath, user, tCode, root);
        subItemEngineeringQualityAcceptance.setFilesRelationId(fileRelationId);
    }

    /**
     * 分部工程质量验收 数据导入
     *
     * @param excel
     * @param user
     * @param decPath
     * @param tCode
     * @param root
     */
    private void importBranchProjectQualityAccept(File excel, LogUserInfoVo user, String decPath, String tCode, String root) {
        // 解析excel
        BranchProjectQualityAcceptDTO branchProjectQualityAcceptDTO = parseBranchProjectQualityAcceptDTO(excel);

        // 将dto转换为数据库实体对象
        BranchProjectQualityAccept branchProjectQualityAccept = new BranchProjectQualityAccept();
        BeanUtils.copyProperties(branchProjectQualityAcceptDTO, branchProjectQualityAccept);

        // 生成主表实体主键id
        String entityId = UUID.randomUUID().toString();
        branchProjectQualityAccept.setUuid(entityId);

        // 与关联表进行关联：通过名称获取到关联表中的id，如项目表通过项目名称获取到项目id
        fillRelationTableDataIdOfBranchProjectQualityAccept(branchProjectQualityAcceptDTO
                , branchProjectQualityAccept, decPath, user, tCode, root);

        List<BranchProjectQualityAcceptChild> childList = new ArrayList<>();
        for (BranchProjectQualityAcceptChildDTO branchProjectQualityAcceptChildDTO : branchProjectQualityAcceptDTO.getChildDTOList()) {
            BranchProjectQualityAcceptChild branchProjectQualityAcceptChild = new BranchProjectQualityAcceptChild();
            BeanUtils.copyProperties(branchProjectQualityAcceptChildDTO, branchProjectQualityAcceptChild);
            branchProjectQualityAcceptChild.setUuid(UUID.randomUUID().toString());
            branchProjectQualityAcceptChild.setBranchProjectQualityAcceptId(entityId);
            childList.add(branchProjectQualityAcceptChild);
        }
        branchProjectQualityAccept.setChildList(childList);

        // 保存数据到数据表
        branchProjectQualityAcceptMapper.insert(branchProjectQualityAccept);

        // 保存子表信息
        if (childList != null && !childList.isEmpty()) {
            for (BranchProjectQualityAcceptChild branchProjectQualityAcceptChild : childList) {
                branchProjectQualityAcceptChildMapper.insert(branchProjectQualityAcceptChild);
            }
        }
    }

    @SneakyThrows
    private BranchProjectQualityAcceptDTO parseBranchProjectQualityAcceptDTO(File excel) {
        Workbook book = null;
        if (excel.getName().endsWith("xlsx")) {
            book = new XSSFWorkbook(new FileInputStream(excel));//Excel 2007
        } else if (excel.getName().endsWith("xls")) {
            book = new HSSFWorkbook(new FileInputStream(excel));//Excel 2003
        }
        Sheet sheet = book.getSheetAt(0);

        // 标题
        String title = sheet.getRow(0).getCell(0).getStringCellValue();

        // 编号
        String code = sheet.getRow(0).getCell(8).getStringCellValue();

        // 项目名称
        String projectName = sheet.getRow(1).getCell(2).getStringCellValue();

        // 合同名称
        String contractName = sheet.getRow(2).getCell(2).getStringCellValue();

        // 单位（子单位）工程名称
        String unitProjectName = sheet.getRow(3).getCell(2).getStringCellValue();

        // 子分部工程数量
        Double subEngineeringNumber = sheet.getRow(3).getCell(5).getNumericCellValue();

        // 分项工程数量
        Double subItemEngineeringNumber = sheet.getRow(3).getCell(8).getNumericCellValue();

        // 施工单位
        String constructionUnitName = sheet.getRow(4).getCell(2).getStringCellValue();

        // 项目负责人
        String projectDirectorName = sheet.getRow(4).getCell(4).getStringCellValue();

        // 技术（质量）负责人
        String techDirectorName = sheet.getRow(4).getCell(6).getStringCellValue();

        // 分包单位
        String subpackageUnitName = sheet.getRow(5).getCell(2).getStringCellValue();

        // 分包单位项目负责人
        String subpackageUnitProjectDirectorName = sheet.getRow(5).getCell(4).getStringCellValue();

        // 分包内容
        String subpackageContent = sheet.getRow(5).getCell(6).getStringCellValue();

        // 质量控制资料
        String qualityControlData = sheet.getRow(6).getCell(5).getStringCellValue();

        // 安全和功能检验结果
        String securityAndFunctionTestResult = sheet.getRow(7).getCell(5).getStringCellValue();

        // 观感质量检查结果
        String appearanceQualityCheckResult = sheet.getRow(8).getCell(5).getStringCellValue();

        // 综合验收结论
        String comprehensiveAcceptanceConclusion = sheet.getRow(9).getCell(1).getStringCellValue();

        // 附件上传
        String filesLink = sheet.getRow(16).getCell(1).getStringCellValue();

        // 施工单位项目负责人
        String constructionUnitProjectDirectorName = sheet.getRow(14).getCell(1).getStringCellValue();

        // 施工单位项目负责人签字日期
        Date constructionUnitProjectDirectorSignDate = sheet.getRow(15).getCell(1).getDateCellValue();

        // 勘察单位项目负责人
        String surveyUnitProjectDirectorName = sheet.getRow(14).getCell(3).getStringCellValue();

        // 勘察单位项目负责人签字日期
        Date surveyUnitProjectDirectorSignDate = sheet.getRow(15).getCell(3).getDateCellValue();

        // 设计单位项目负责人
        String designUnitProjectDirectorName = sheet.getRow(14).getCell(5).getStringCellValue();

        // 设计单位项目负责人签字日期
        Date designUnitProjectDirectorSignDate = sheet.getRow(15).getCell(5).getDateCellValue();

        // 监理单位总监理工程师
        String superviseUnitChiefSuperviseEngineerName = sheet.getRow(14).getCell(8).getStringCellValue();

        // 监理单位总监理工程师签字日期
        Date superviseUnitChiefSuperviseEngineerSignDate = sheet.getRow(15).getCell(8).getDateCellValue();

        List<BranchProjectQualityAcceptChildDTO> branchProjectQualityAcceptChildDTOList = new ArrayList<>();

        for (int i = 18; i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            // 子分部工程名称
            String subBranchEngineeringName = row.getCell(1).getStringCellValue();

            // 分项工程名称
            String subItemEngineeringName = row.getCell(2).getStringCellValue();

            // 检验批数量
            Double checkGroupNumber = row.getCell(4).getNumericCellValue();

            // 施工单位检查结果
            String constructionUnitCheckResult = row.getCell(5).getStringCellValue();

            // 监理单位验收结论
            String superviseUnitAcceptanceConclusion = row.getCell(8).getStringCellValue();

            BranchProjectQualityAcceptChildDTO branchProjectQualityAcceptChildDTO = BranchProjectQualityAcceptChildDTO.builder()
                    .subBranchEngineeringName(subBranchEngineeringName)
                    .subItemEngineeringName(subItemEngineeringName)
                    .checkGroupNumber(checkGroupNumber.intValue())
                    .constructionUnitCheckResult(constructionUnitCheckResult)
                    .superviseUnitAcceptanceConclusion(superviseUnitAcceptanceConclusion)
                    .build();
            branchProjectQualityAcceptChildDTOList.add(branchProjectQualityAcceptChildDTO);
        }


        try {
            book.close();
        } catch (IOException ignored) {
        }
        BranchProjectQualityAcceptDTO branchProjectQualityAcceptDTO = BranchProjectQualityAcceptDTO.builder()
                .title(title)
                .code(code)
                .projectName(projectName)
                .contractName(contractName)
                .unitProjectName(unitProjectName)
                .subEngineeringNumber(subEngineeringNumber)
                .subItemEngineeringNumber(subItemEngineeringNumber)
                .constructionUnitName(constructionUnitName)
                .projectDirectorName(projectDirectorName)
                .techDirectorName(techDirectorName)
                .subpackageUnitName(subpackageUnitName)
                .subpackageUnitProjectDirectorName(subpackageUnitProjectDirectorName)
                .subpackageContent(subpackageContent)
                .qualityControlData(qualityControlData)
                .securityAndFunctionTestResult(securityAndFunctionTestResult)
                .appearanceQualityCheckResult(appearanceQualityCheckResult)
                .comprehensiveAcceptanceConclusion(comprehensiveAcceptanceConclusion)
                .filesLink(filesLink)
                .constructionUnitProjectDirectorName(constructionUnitProjectDirectorName)
                .constructionUnitProjectDirectorSignDate(constructionUnitProjectDirectorSignDate)
                .surveyUnitProjectDirectorName(surveyUnitProjectDirectorName)
                .surveyUnitProjectDirectorSignDate(surveyUnitProjectDirectorSignDate)
                .designUnitProjectDirectorName(designUnitProjectDirectorName)
                .designUnitProjectDirectorSignDate(designUnitProjectDirectorSignDate)
                .superviseUnitChiefSuperviseEngineerName(superviseUnitChiefSuperviseEngineerName)
                .superviseUnitChiefSuperviseEngineerSignDate(superviseUnitChiefSuperviseEngineerSignDate)
                .childDTOList(branchProjectQualityAcceptChildDTOList)
                .build();
        return branchProjectQualityAcceptDTO;
    }

    private void fillRelationTableDataIdOfBranchProjectQualityAccept(BranchProjectQualityAcceptDTO branchProjectQualityAcceptDTO
            , BranchProjectQualityAccept branchProjectQualityAccept, String decPath, LogUserInfoVo user, String tCode, String root) {
        // 项目名称 -> 项目id
        branchProjectQualityAccept.setProjectId(getProjectIdByProjectName(branchProjectQualityAcceptDTO.getProjectName()));

        // 合同名称 -> 合同id
        branchProjectQualityAccept.setContractId(getContractIdByContractName(branchProjectQualityAcceptDTO.getContractName()));

        // 施工单位
        branchProjectQualityAccept.setConstructionUnitId(getUnitIdByUnitName(branchProjectQualityAcceptDTO.getConstructionUnitName()));

        // 文件id
        String fileRelationId = UUID.randomUUID().toString();
        saveFileByFileLink(branchProjectQualityAcceptDTO.getFilesLink(), branchProjectQualityAccept.getUuid(), fileRelationId, decPath, user, tCode, root);
        branchProjectQualityAccept.setFilesRelationId(fileRelationId);
    }

    /**
     * 分包合同 数据导入
     *
     * @param excel
     * @param user
     * @param decPath
     * @param tCode
     * @param root
     */
    private void importSubpackageContract(File excel, LogUserInfoVo user, String decPath, String tCode, String root) {
        // 解析excel
        SubpackageContractDTO subpackageContractDTO = parseSubpackageContractDTO(excel);

        // 将dto转换为数据库实体对象
        SubpackageContract subpackageContract = new SubpackageContract();
        BeanUtils.copyProperties(subpackageContractDTO, subpackageContract);

        // 生成主表实体主键id
        String entityId = UUID.randomUUID().toString();
        subpackageContract.setUuid(entityId);
        // 与关联表进行关联：通过名称获取到关联表中的id，如项目表通过项目名称获取到项目id
        fillRelationTableDataIdOfSubpackageContract(subpackageContractDTO, subpackageContract, decPath, user, tCode, root);
        // 保存数据到数据表
        subpackageContractMapper.insert(subpackageContract);
    }

    @SneakyThrows
    private SubpackageContractDTO parseSubpackageContractDTO(File excel) {
        Workbook book = null;
        if (excel.getName().endsWith("xlsx")) {
            book = new XSSFWorkbook(new FileInputStream(excel));//Excel 2007
        } else if (excel.getName().endsWith("xls")) {
            book = new HSSFWorkbook(new FileInputStream(excel));//Excel 2003
        }
        Sheet sheet = book.getSheetAt(0);

        // 项目名称
        String projectName = sheet.getRow(0).getCell(2).getStringCellValue();

        // 总包合同名称
        String mainContractName = sheet.getRow(1).getCell(2).getStringCellValue();

        // 总包合同编码
        String mainContractCode = sheet.getRow(1).getCell(6).getStringCellValue();

        // 分包合同名称
        String subpackageContractName = sheet.getRow(2).getCell(2).getStringCellValue();

        // 分包合同编码
        String subpackageContractCode = sheet.getRow(2).getCell(6).getStringCellValue();

        // 合同金额
        Double contractAmount = sheet.getRow(3).getCell(2).getNumericCellValue();

        // 甲方单位
        String partyAsUnitName = sheet.getRow(3).getCell(6).getStringCellValue();

        // 签订日期
        Date signDate = sheet.getRow(4).getCell(2).getDateCellValue();

        // 驻场负责人
        String residentDirectorName = sheet.getRow(4).getCell(6).getStringCellValue();

        // 联系电话
        String residentDirectorPhone = sheet.getRow(5).getCell(2).getStringCellValue();

        // 收款账户
        String acceptanceMoneyHolderName = sheet.getRow(5).getCell(6).getStringCellValue();

        // 收款账号
        String receiveAccount = sheet.getRow(6).getCell(2).getStringCellValue();

        // 合同类型
        String contractType = sheet.getRow(6).getCell(6).getStringCellValue();

        // 附件上传
        String filesLink = sheet.getRow(7).getCell(2).getStringCellValue();

        // 乙方单位名称
        String partyBsUnitName = sheet.getRow(9).getCell(2).getStringCellValue();

        // 乙方单位类型
        String partyBsUnitType = sheet.getRow(9).getCell(6).getStringCellValue();

        SubpackageContractDTO subpackageContractDTO = SubpackageContractDTO.builder()
                .projectName(projectName)
                .mainContractName(mainContractName)
                .mainContractCode(mainContractCode)
                .subpackageContractName(subpackageContractName)
                .subpackageContractCode(subpackageContractCode)
                .contractAmount(contractAmount)
                .partyAsUnitName(partyAsUnitName)
                .signDate(signDate)
                .residentDirectorName(residentDirectorName)
                .residentDirectorPhone(residentDirectorPhone)
                .acceptanceMoneyHolderName(acceptanceMoneyHolderName)
                .receiveAccount(receiveAccount)
                .contractType(contractType)
                .filesLink(filesLink)
                .partyBsUnitName(partyBsUnitName)
                .partyBsUnitType(partyBsUnitType)
                .build();

        try {
            book.close();
        } catch (IOException ignored) {
        }

        return subpackageContractDTO;
    }

    private void fillRelationTableDataIdOfSubpackageContract(SubpackageContractDTO subpackageContractDTO
            , SubpackageContract subpackageContract, String decPath
            , LogUserInfoVo user, String tCode, String root) {
        // 项目名称 -> 项目id
        subpackageContract.setProjectId(getProjectIdByProjectName(subpackageContractDTO.getProjectName()));

        // 总包合同名称 -> 合同id
        subpackageContract.setMainContractId(getContractIdByContractName(subpackageContractDTO.getMainContractName()));
        // 分包合同
        subpackageContract.setSubpackageContractId(getContractIdByContractName(subpackageContractDTO.getSubpackageContractName()));

        // 甲方单位
        subpackageContract.setPartyAsUnitId(getUnitIdByUnitName(subpackageContractDTO.getPartyAsUnitName()));

        // 乙方单位
        subpackageContract.setPartyBsUnitId(getUnitIdByUnitName(subpackageContractDTO.getPartyBsUnitName()));

        // 文件id
        String fileRelationId = UUID.randomUUID().toString();
        saveFileByFileLink(subpackageContractDTO.getFilesLink(), subpackageContract.getUuid(), fileRelationId, decPath, user, tCode, root);
        subpackageContract.setFilesRelationId(fileRelationId);
    }

    /**
     * 材料设备检测报告 数据导入
     *
     * @param excel
     * @param user
     * @param decPath
     * @param tCode
     * @param root
     */
    private void importMaterialDeviceTestReport(File excel, LogUserInfoVo user, String decPath, String tCode, String root) {
        // 解析excel
        MaterialDeviceTestReportDTO materialDeviceTestReportDTO = parseMaterialDeviceTestReportDTO(excel);
        // 将dto转换为数据库实体对象
        MaterialDeviceTestReport materialDeviceTestReport = new MaterialDeviceTestReport();
        BeanUtils.copyProperties(materialDeviceTestReportDTO, materialDeviceTestReport);

        // 生成主表实体主键id
        String entityId = UUID.randomUUID().toString();
        materialDeviceTestReport.setUuid(entityId);

        // 与关联表进行关联：通过名称获取到关联表中的id，如项目表通过项目名称获取到项目id
        fileRelationTableDataIdOfMaterialDeviceTestReport(materialDeviceTestReportDTO, materialDeviceTestReport
                , decPath, user, tCode, root);

        // 保存数据到数据表
        materialDeviceTestReportMapper.insert(materialDeviceTestReport);

    }

    @SneakyThrows
    private MaterialDeviceTestReportDTO parseMaterialDeviceTestReportDTO(File excel) {
        Workbook book = null;
        if (excel.getName().endsWith("xlsx")) {
            book = new XSSFWorkbook(new FileInputStream(excel));//Excel 2007
        } else if (excel.getName().endsWith("xls")) {
            book = new HSSFWorkbook(new FileInputStream(excel));//Excel 2003
        }
        Sheet sheet = book.getSheetAt(0);

        // 标题
        String title = sheet.getRow(0).getCell(0).getStringCellValue();

        // 资料编号
        String dataCode = sheet.getRow(0).getCell(5).getStringCellValue();

        // 试验编号
        String experimentCode = sheet.getRow(1).getCell(5).getStringCellValue();

        // 委托编号
        String entrustCode = sheet.getRow(2).getCell(5).getStringCellValue();

        // 项目名称
        String projectName = sheet.getRow(3).getCell(1).getStringCellValue();

        // 合同名称
        String contractName = sheet.getRow(4).getCell(1).getStringCellValue();

        // 使用部位
        String usePart = sheet.getRow(4).getCell(5).getStringCellValue();

        // 委托单位
        String entrustUnitName = sheet.getRow(5).getCell(1).getStringCellValue();

        // 委托人
        String entrustManName = sheet.getRow(5).getCell(5).getStringCellValue();

        // 施工单位
        String constructionUnitName = sheet.getRow(6).getCell(1).getStringCellValue();

        // 试样编号
        String sampleCode = sheet.getRow(6).getCell(5).getStringCellValue();

        // 见证人单位
        String witnessUnitName = sheet.getRow(7).getCell(1).getStringCellValue();

        // 见证人
        String witnessName = sheet.getRow(7).getCell(5).getStringCellValue();

        // 建设单位
        String buildUnitName = sheet.getRow(8).getCell(1).getStringCellValue();

        // 设计单位
        String designUnitName = sheet.getRow(8).getCell(5).getStringCellValue();

        // 监理单位
        String superviseUnitName = sheet.getRow(9).getCell(1).getStringCellValue();

        // 委托日期
        Date entrustDate = sheet.getRow(9).getCell(5).getDateCellValue();

        // 检测原因
        String testReason = sheet.getRow(10).getCell(1).getStringCellValue();

        // 检测日期
        Date testDate = sheet.getRow(10).getCell(5).getDateCellValue();

        // 检测项目及要求
        String testProjectAndRequirement = sheet.getRow(11).getCell(1).getStringCellValue();

        // 其他说明
        String otherExplain = sheet.getRow(12).getCell(1).getStringCellValue();

        // 检测结果
        String testResult = sheet.getRow(13).getCell(1).getStringCellValue();

        // 结论
        String conclusion = sheet.getRow(14).getCell(1).getStringCellValue();

        // 备注
        String remark = sheet.getRow(15).getCell(1).getStringCellValue();

        // 批准
        String ratify = sheet.getRow(16).getCell(1).getStringCellValue();

        // 审核
        String audit = sheet.getRow(16).getCell(3).getStringCellValue();

        // 主检
        String mainInspection = sheet.getRow(16).getCell(5).getStringCellValue();

        // 检测机构
        String testUnitName = sheet.getRow(17).getCell(1).getStringCellValue();

        // 报告日期
        Date reportDate = sheet.getRow(18).getCell(1).getDateCellValue();

        // 附件
        String file = sheet.getRow(19).getCell(1).getStringCellValue();

        MaterialDeviceTestReportDTO materialDeviceTestReportDTO = MaterialDeviceTestReportDTO.builder()
                .title(title)
                .dataCode(dataCode)
                .experimentCode(experimentCode)
                .entrustCode(entrustCode)
                .projectName(projectName)
                .contractName(contractName)
                .usePart(usePart)
                .entrustUnitName(entrustUnitName)
                .entrustManName(entrustManName)
                .constructionUnitName(constructionUnitName)
                .sampleCode(sampleCode)
                .witnessUnitName(witnessUnitName)
                .witnessName(witnessName)
                .buildUnitName(buildUnitName)
                .designUnitName(designUnitName)
                .superviseUnitName(superviseUnitName)
                .entrustDate(entrustDate)
                .testDate(testDate)
                .testProjectAndRequirement(testProjectAndRequirement)
                .testReason(testReason)
                .otherExplain(otherExplain)
                .testResult(testResult)
                .conclusion(conclusion)
                .remark(remark)
                .ratify(ratify)
                .audited(audit)
                .mainInspection(mainInspection)
                .testUnitName(testUnitName)
                .reportDate(reportDate)
                .filesLink(file)
                .build();

        try {
            book.close();
        } catch (IOException ignored) {
        }

        return materialDeviceTestReportDTO;
    }

    private void fileRelationTableDataIdOfMaterialDeviceTestReport(MaterialDeviceTestReportDTO materialDeviceTestReportDTO
            , MaterialDeviceTestReport materialDeviceTestReport
            , String decPath, LogUserInfoVo user, String tCode
            , String root) {
        // 项目名称 -> 项目id
        materialDeviceTestReport.setProjectId(getProjectIdByProjectName(materialDeviceTestReportDTO.getProjectName()));

        // 合同名称 -> 合同id
        materialDeviceTestReport.setContractId(getContractIdByContractName(materialDeviceTestReportDTO.getContractName()));

        // 施工单位
        materialDeviceTestReport.setConstructionUnitId(getUnitIdByUnitName(materialDeviceTestReportDTO.getConstructionUnitName()));

        // 建设单位
        materialDeviceTestReport.setBuildUnitId(getUnitIdByUnitName(materialDeviceTestReportDTO.getBuildUnitName()));

        // 设计单位
        materialDeviceTestReport.setDesignUnitId(getUnitIdByUnitName(materialDeviceTestReportDTO.getDesignUnitName()));

        // 监理单位
        materialDeviceTestReport.setSuperviseUnitId(getUnitIdByUnitName(materialDeviceTestReportDTO.getSuperviseUnitName()));

        // 文件id
        String fileRelationId = UUID.randomUUID().toString();
        saveFileByFileLink(materialDeviceTestReportDTO.getFilesLink(), materialDeviceTestReport.getUuid(), fileRelationId, decPath, user, tCode, root);
        materialDeviceTestReport.setFilesRelationId(fileRelationId);

    }

    /**
     * 材料设备抽检 数据导入
     *
     * @param excel
     * @param user
     * @param decPath
     * @param tCode
     * @param root
     */
    private void importMaterialDeviceSamplingInspection(File excel, LogUserInfoVo user, String decPath, String tCode, String root) {
        // 解析excel
        MaterialDeviceSamplingInspectionDTO materialDeviceSamplingInspectionDTO = parseMaterialDeviceSamplingInspection(excel);

        // 将dto转换为数据库实体对象
        MaterialDeviceSamplingInspection materialDeviceSamplingInspection = new MaterialDeviceSamplingInspection();
        BeanUtils.copyProperties(materialDeviceSamplingInspectionDTO, materialDeviceSamplingInspection);

        // 生成主表实体主键id
        String entityId = UUID.randomUUID().toString();
        materialDeviceSamplingInspection.setUuid(entityId);

        // 与关联表进行关联：通过名称获取到关联表中的id，如项目表通过项目名称获取到项目id
        fillRelationTableDataIdOfMaterialDeviceSamplingInspection(materialDeviceSamplingInspectionDTO, materialDeviceSamplingInspection
                , decPath, user, tCode, root);

        // 保存数据到数据表
        materialDeviceSamplingInspectionMapper.insert(materialDeviceSamplingInspection);

    }

    @SneakyThrows
    private MaterialDeviceSamplingInspectionDTO parseMaterialDeviceSamplingInspection(File excel) {
        Workbook book = null;
        if (excel.getName().endsWith("xlsx")) {
            book = new XSSFWorkbook(new FileInputStream(excel));//Excel 2007
        } else if (excel.getName().endsWith("xls")) {
            book = new HSSFWorkbook(new FileInputStream(excel));//Excel 2003
        }
        Sheet sheet = book.getSheetAt(0);
        // 标题
        String title = sheet.getRow(0).getCell(0).getStringCellValue();

        // 资料编码
        String code = sheet.getRow(0).getCell(4).getStringCellValue();

        // 项目名称
        String projectName = sheet.getRow(1).getCell(1).getStringCellValue();

        // 合同名称
        String contractName = sheet.getRow(2).getCell(1).getStringCellValue();

        // 试件名称
        String testPieceName = sheet.getRow(3).getCell(1).getStringCellValue();

        // 生产厂家
        String manufacturerName = sheet.getRow(3).getCell(4).getStringCellValue();
        ;

        // 试件品种
        String testPieceVariety = sheet.getRow(4).getCell(1).getStringCellValue();
        ;

        // 材料出厂编号
        String materialFactoryCode = sheet.getRow(4).getCell(4).getStringCellValue();
        ;

        // 试件规格型号
        String testPieceFlavorModel = sheet.getRow(5).getCell(1).getStringCellValue();
        ;

        // 材料进场时间
        Date materialEnterTime = sheet.getRow(5).getCell(4).getDateCellValue();
        ;

        // 材料进场数量
        Double materialEnterNumber = sheet.getRow(6).getCell(1).getNumericCellValue();
        ;

        // 代表数量
        Double representativeNumber = sheet.getRow(6).getCell(4).getNumericCellValue();
        ;

        // 试样编号
        String testPieceCode = sheet.getRow(7).getCell(1).getStringCellValue();
        ;

        // 取样组数
        Double samplingGroupNumber = sheet.getRow(7).getCell(4).getNumericCellValue();
        ;

        // 抽样时间
        Date samplingTime = sheet.getRow(8).getCell(1).getDateCellValue();
        ;

        // 取样地点
        String samplingLocation = sheet.getRow(8).getCell(4).getStringCellValue();
        ;

        // "使用部位（取样部位）"
        String samplingPart = sheet.getRow(9).getCell(1).getStringCellValue();
        ;

        // 检测项目（设计要求)
        String testProjectDesignRequirement = sheet.getRow(10).getCell(1).getStringCellValue();
        ;

        // 见证记录
        String witnessRecord = sheet.getRow(11).getCell(1).getStringCellValue();
        ;

        // 产品标准
        String productStandard = sheet.getRow(12).getCell(2).getStringCellValue();
        ;

        // 验收规范
        String acceptanceSpecification = sheet.getRow(13).getCell(2).getStringCellValue();
        ;

        // 设计要求
        String designRequirement = sheet.getRow(14).getCell(2).getStringCellValue();
        ;

        // 抽样人签字
        String samplerSignature = sheet.getRow(15).getCell(2).getStringCellValue();
        ;

        // 抽样人签字日期
        Date samplerSignatureDate = sheet.getRow(16).getCell(2).getDateCellValue();
        ;

        // 见证人签字
        String witnessSignature = sheet.getRow(15).getCell(5).getStringCellValue();
        ;

        // 见证人签字日期
        Date witnessSignatureDate = sheet.getRow(16).getCell(5).getDateCellValue();
        ;

        // 检测单位
        String testUnitName = sheet.getRow(17).getCell(2).getStringCellValue();
        ;

        // 送检时间
        Date inspectionSubmitTime = sheet.getRow(18).getCell(2).getDateCellValue();
        ;

        // 附件
        String filesLink = sheet.getRow(19).getCell(1).getStringCellValue();

        MaterialDeviceSamplingInspectionDTO materialDeviceSamplingInspectionDTO = MaterialDeviceSamplingInspectionDTO
                .builder()
                .title(title)
                .code(code)
                .projectName(projectName)
                .contractName(contractName)
                .testPieceName(testPieceName)
                .manufacturerName(manufacturerName)
                .testPieceVariety(testPieceVariety)
                .materialFactoryCode(materialFactoryCode)
                .testPieceFlavorModel(testPieceFlavorModel)
                .materialEnterTime(materialEnterTime)
                .materialEnterNumber(materialEnterNumber)
                .representativeNumber(representativeNumber)
                .testPieceCode(testPieceCode)
                .samplingGroupNumber(samplingGroupNumber)
                .samplingTime(samplingTime)
                .samplingLocation(samplingLocation)
                .samplingPart(samplingPart)
                .testProjectDesignRequirement(testProjectDesignRequirement)
                .witnessRecord(witnessRecord)
                .productStandard(productStandard)
                .acceptanceSpecification(acceptanceSpecification)
                .designRequirement(designRequirement)
                .samplerSignature(samplerSignature)
                .samplerSignatureDate(samplerSignatureDate)
                .witnessSignature(witnessSignature)
                .witnessSignatureDate(witnessSignatureDate)
                .testUnitName(testUnitName)
                .inspectionSubmitTime(inspectionSubmitTime)
                .filesLink(filesLink)
                .build();

        try {
            book.close();
        } catch (IOException ignored) {
        }
        return materialDeviceSamplingInspectionDTO;
    }

    private void fillRelationTableDataIdOfMaterialDeviceSamplingInspection(MaterialDeviceSamplingInspectionDTO materialDeviceSamplingInspectionDTO
            , MaterialDeviceSamplingInspection materialDeviceSamplingInspection
            , String decPath, LogUserInfoVo user, String tCode
            , String root) {
        // 项目名称 -> 项目id
        materialDeviceSamplingInspection.setProjectId(getProjectIdByProjectName(materialDeviceSamplingInspectionDTO.getProjectName()));

        // 合同名称 -> 合同id
        materialDeviceSamplingInspection.setContractId(getContractIdByContractName(materialDeviceSamplingInspectionDTO.getContractName()));

        // 文件id
        String fileRelationId = UUID.randomUUID().toString();
        saveFileByFileLink(materialDeviceSamplingInspectionDTO.getFilesLink(), materialDeviceSamplingInspection.getUuid(), fileRelationId, decPath, user, tCode, root);
        materialDeviceSamplingInspection.setFilesRelationId(fileRelationId);

    }

    /**
     * 材料设备进场验货数据导入
     *
     * @param excel
     * @param user
     * @param decPath
     * @param tCode
     * @param root
     */
    private void importMaterialDeviceEnterInspection(File excel, LogUserInfoVo user, String decPath, String tCode, String root) {
        // 解析excel
        MaterialDeviceEnterInspectionManageDTO materialDeviceEnterInspectionManageDTO = parseMaterialDeviceEnterInspectionManageDTO(excel);
        // 将dto转换为数据库实体对象
        MaterialDeviceEnterInspection materialDeviceEnterInspection = new MaterialDeviceEnterInspection();
        BeanUtils.copyProperties(materialDeviceEnterInspectionManageDTO, materialDeviceEnterInspection);

        // 生成主表实体主键id
        String entityId = UUID.randomUUID().toString();
        materialDeviceEnterInspection.setUuid(entityId);

        // 与关联表进行关联：通过名称获取到关联表中的id，如项目表通过项目名称获取到项目id
        fillRelationTableDataIdOfMaterialDeviceEnterInspection(materialDeviceEnterInspectionManageDTO
                , materialDeviceEnterInspection, decPath, user, tCode, root);


        List<MaterialDeviceEnterInspectionChild> childList = new ArrayList<>();
        List<MaterialDeviceEnterInspectionManageChildDTO> childDTOList = materialDeviceEnterInspectionManageDTO.getChildDTOList();
        if (childDTOList != null && !childDTOList.isEmpty()) {
            for (MaterialDeviceEnterInspectionManageChildDTO materialDeviceEnterInspectionManageChildDTO : childDTOList) {
                MaterialDeviceEnterInspectionChild materialDeviceEnterInspectionChild = new MaterialDeviceEnterInspectionChild();
                BeanUtils.copyProperties(materialDeviceEnterInspectionManageChildDTO, materialDeviceEnterInspectionChild);
                childList.add(materialDeviceEnterInspectionChild);
            }
        }
        // 给子表对象赋值主表id
        materialDeviceEnterInspection.setChildList(childList);
        for (MaterialDeviceEnterInspectionChild materialDeviceEnterInspectionChild : childList) {
            materialDeviceEnterInspectionChild.setUuid(UUID.randomUUID().toString());
            materialDeviceEnterInspectionChild.setMaterialDeviceEnterInspectionId(entityId);
        }

        // 保存数据到数据表
        materialDeviceEnterInspectionMapper.insert(materialDeviceEnterInspection);

        for (MaterialDeviceEnterInspectionChild materialDeviceEnterInspectionChild : childList) {
            // 保存子表信息
            materialDeviceEnterInspectionChildMapper.insert(materialDeviceEnterInspectionChild);
        }
    }

    private void fillRelationTableDataIdOfMaterialDeviceEnterInspection(MaterialDeviceEnterInspectionManageDTO materialDeviceEnterInspectionManageDTO
            , MaterialDeviceEnterInspection materialDeviceEnterInspection, String decPath, LogUserInfoVo user, String tCode
            , String root) {
        // 项目名称 -> 项目id
        materialDeviceEnterInspection.setProjectId(getProjectIdByProjectName(materialDeviceEnterInspectionManageDTO.getProjectName()));

        // 合同名称 -> 合同id
        materialDeviceEnterInspection.setContractId(getContractIdByContractName(materialDeviceEnterInspectionManageDTO.getContractName()));

        // 施工单位
        materialDeviceEnterInspection.setConstructionUnitId(getUnitIdByUnitName(materialDeviceEnterInspectionManageDTO.getConstructionUnitName()));

        // 分包单位
        materialDeviceEnterInspection.setSubPackageUnitId(getUnitIdByUnitName(materialDeviceEnterInspectionManageDTO.getSubPackageUnitName()));

        String fileRelationId = UUID.randomUUID().toString();
        saveFileByFileLink(materialDeviceEnterInspectionManageDTO.getFilesLink(), materialDeviceEnterInspection.getUuid(), fileRelationId, decPath, user, tCode, root);
        materialDeviceEnterInspection.setFilesRelationId(fileRelationId);
    }

    private void saveFileByFileLink(String filesLink, String entityId, String relationId, String decPath, LogUserInfoVo user, String tCode, String root) {
        try {
            String path = FileUtil.path(dirPath, tCode, user.getId(), root);
            // 通过文件链接上传文件并获取到该文件的relationId
            filesLink = filesLink.replaceAll("\\\\", "/");
            String fileName = filesLink.substring(filesLink.lastIndexOf("/") + 1);
            String[] name = fileName.split("\\.");
            String fileSuffix = name[name.length - 1];
            String newName = GenerateUUID.getUUID() + "." + fileSuffix;
            String source = decPath + "/" + filesLink;
            FileUtil.moveTo(source, root + "/" + path, newName);
            SysFileTemplate temp = new SysFileTemplate();
            SysFileAnnex sysFileAnnex = sysFileAnnexService.entity(root, path, 0, fileName, newName, fileSuffix, "filesRelationId", entityId, relationId, user, temp);
            sysFileAnnexMapper.insert(sysFileAnnex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getProjectIdByProjectName(String projectName) {
        if (ValidateUtil.isEmpty(projectName)) return null;
        PreProject preProject = preProjectMapper.selectOne(new LambdaQueryWrapper<PreProject>()
                .eq(PreProject::getpName, projectName)
                .orderByDesc(PreProject::getStateTime)
                .last("limit 1"));
        if (preProject == null) {
            return null;
        }
        return preProject.getpId();
    }

    private String getContractIdByContractName(String contractName) {
        String contractId = preProjectMapper.getContractIdByContractName(contractName);
        return contractId;
    }

    private String getUnitIdByUnitName(String unitName) {
        if (ValidateUtil.isEmpty(unitName)) return null;
        String companyId = preProjectMapper.getCompanyIdByCompanyName(unitName);
        if (companyId == null) {
            companyId = UUID.randomUUID().toString();
            preProjectMapper.insertCompany(companyId, unitName);
        }
        return companyId;
    }

    /**
     * 解析excel文件
     *
     * @param excel
     * @return
     */
    @SneakyThrows
    private static MaterialDeviceEnterInspectionManageDTO parseMaterialDeviceEnterInspectionManageDTO(File excel) {
        Workbook book = null;
        if (excel.getName().endsWith("xlsx")) {
            book = new XSSFWorkbook(new FileInputStream(excel));//Excel 2007
        } else if (excel.getName().endsWith("xls")) {
            book = new HSSFWorkbook(new FileInputStream(excel));//Excel 2003
        }
        Sheet sheet = book.getSheetAt(0);
        Row row = sheet.getRow(0);
        Cell cell = row.getCell(0);
        // 标题
        String title = cell.getStringCellValue();
        // 资料编号
        cell = row.getCell(6);
        String code = cell.getStringCellValue();

        // 项目名称
        row = sheet.getRow(1);
        String projectName = row.getCell(0).getStringCellValue();

        // 合同名称
        String contractName = sheet.getRow(2).getCell(2).getStringCellValue();

        // 进场日期
        Date enterDateStr = sheet.getRow(2).getCell(6).getDateCellValue();

        // 施工单位
        String constructionUnitName = sheet.getRow(3).getCell(2).getStringCellValue();

        // 分包单位
        String subPackageUnitName = sheet.getRow(3).getCell(6).getStringCellValue();

        // 施工单位检查意见
        String constructionUnitCheckOpinion = sheet.getRow(4).getCell(2).getStringCellValue();

        // 监理单位审查意见
        String superviseUnitExamineOpinion = sheet.getRow(5).getCell(2).getStringCellValue();

        // 施工单位材料验收负责人
        String constructionUnitMaterialDirectorName = sheet.getRow(7).getCell(1).getStringCellValue();

        // 分包单位材料验收负责人
        String subpackageUnitMaterialAcceptanceDirectorName = sheet.getRow(7).getCell(4).getStringCellValue();

        // 专业监理工程师
        String supervisorEngineerName = sheet.getRow(7).getCell(6).getStringCellValue();

        // 制表日期
        Date fillDate = sheet.getRow(8).getCell(2).getDateCellValue();

        // 附件
        String filesLink = sheet.getRow(8).getCell(6).getStringCellValue();

        // 子表
        // 名称、规格、进场数量、生产厂家、质量证明文件核查、外观检验结果、复验情况
        List<MaterialDeviceEnterInspectionManageChildDTO> childList = new ArrayList<>();
        for (int i = 10; i < sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            String name = row.getCell(1).getStringCellValue();
            String flavor = row.getCell(2).getStringCellValue();
            Double enterNumber = row.getCell(3).getNumericCellValue();
            String manufacturerName = row.getCell(4).getStringCellValue();
            String qualityCertificationFileVerification = row.getCell(5).getStringCellValue();
            String appearanceInspectionResult = row.getCell(6).getStringCellValue();
            String retestSituation = row.getCell(7).getStringCellValue();
            MaterialDeviceEnterInspectionManageChildDTO materialDeviceEnterInspectionManageChildDTO = MaterialDeviceEnterInspectionManageChildDTO.builder()
                    .name(name)
                    .flavor(flavor)
                    .enterNumber(enterNumber)
                    .manufacturerName(manufacturerName)
                    .qualityCertificationFileVerification(qualityCertificationFileVerification)
                    .appearanceInspectionResult(appearanceInspectionResult)
                    .retestSituation(retestSituation)
                    .build();
            childList.add(materialDeviceEnterInspectionManageChildDTO);
        }

        MaterialDeviceEnterInspectionManageDTO materialDeviceEnterInspectionManageDTO = MaterialDeviceEnterInspectionManageDTO.builder()
                .title(title)
                .code(code)
                .projectName(projectName)
                .contractName(contractName)
                .enterDateStr(enterDateStr)
                .constructionUnitName(constructionUnitName)
                .subPackageUnitName(subPackageUnitName)
                .title(title)
                .constructionUnitCheckOpinion(constructionUnitCheckOpinion)
                .superviseUnitExamineOpinion(superviseUnitExamineOpinion)
                .constructionUnitDirectorName(constructionUnitMaterialDirectorName)
                .subpackageUnitMaterialAcceptanceDirectorName(subpackageUnitMaterialAcceptanceDirectorName)
                .supervisorEngineerName(supervisorEngineerName)
                .fillDate(fillDate)
                .filesLink(filesLink)
                .childDTOList(childList)
                .build();

        try {
            book.close();
        } catch (IOException ignored) {
        }
        return materialDeviceEnterInspectionManageDTO;
    }

    private File temporaryStorageFile(String root, MultipartFile file, String regex, String msg, String code) {
        if (root == null) throw new JPMException(FileMsg.missSysConfig);
        if (ValidateUtil.isEmpty(code)) code = "JPBPMTemporaryFolder";
        msg = ValidateUtil.isEmpty(msg) ? FileMsg.unSupportedFormat : msg;
        String fileName = file.getOriginalFilename();
        if (ValidateUtil.isEmpty(fileName)) throw new JPMException(FileMsg.unSupportedFormat);
        if (!fileName.matches(regex)) throw new JPMException(msg);
        String[] name = fileName.split("\\.");
        String fileSuffix = name[name.length - 1];
        try {
            String realAddres = upload(file, fileSuffix, GenerateUUID.getUUID(), code, root);
            File tfFile = new File(root + "/" + realAddres);
            if (!tfFile.exists() || !tfFile.isFile()) throw new JPMException(FileMsg.saveFail);
            return tfFile;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new JPMException(FileMsg.saveFail);
        }
    }

    private String upload(MultipartFile uploadFile, String fileSuffix, String uId, String code, String root) throws Exception {
        String dateDir = new SimpleDateFormat("yyyy/MM").format(new Date());

        String filePath = dirPath + code + "/" + uId + "/" + dateDir;
        String fileDirPath = root + "/" + filePath;
        File fileDir = new File(fileDirPath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        String fileName = GenerateUUID.getUUID();
        // 实现文件上传   dirPath/功能编码/用户id/年月/
        String realUrlPath = fileDirPath + "/" + fileName + "." + fileSuffix;
        File realFilePath = new File(realUrlPath);
        uploadFile.transferTo(realFilePath);
        log.info("文件上传 → ok");
        filePath = filePath + "/" + fileName + "." + fileSuffix;
        return filePath;
    }
}
