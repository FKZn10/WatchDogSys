package com.jpmsoft.fileUpload.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.aspose.words.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fasterxml.jackson.core.type.TypeReference;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.export.request.GanttPlanExportRequest;
import com.jpmsoft.common.model.export.response.GanttTasksProcessModel;
import com.jpmsoft.common.model.request.Contract.ConContractRequest;
import com.jpmsoft.common.model.request.FilerecordRequest;
import com.jpmsoft.common.model.request.InvestmentControl.InvFinalAccountsApplyDtlDto;
import com.jpmsoft.common.model.requestAndResponse.InnerFileUploadAppointDTO;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvBudgetDeclareDtlModel;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvestmentEstimateBreakdownDetailsModel;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaProjectReportModel;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProApprovalDtlModel;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectDtlModel;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectRegisterDtlModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertModel;
import com.jpmsoft.common.model.requestAndResponse.designReview.DrBudgetDeclareDtlModel;
import com.jpmsoft.common.model.response.Contract.ConContractResponse;
import com.jpmsoft.common.model.response.InvestmentControl.InvProjectPlanResponse;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.common.model.response.Oa.OaNoticeResponse;
import com.jpmsoft.common.model.response.Plan.GanttPlanResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.constants.FileMsg;
import com.jpmsoft.fileUpload.easyexcel.EasyExcelData;
import com.jpmsoft.fileUpload.enums.FileSourceEnum;
import com.jpmsoft.fileUpload.feign.BpmFeign;
import com.jpmsoft.fileUpload.feign.OaFeign;
import com.jpmsoft.fileUpload.feign.PmFeign;
import com.jpmsoft.fileUpload.mapper.ArchiveMapper;
import com.jpmsoft.fileUpload.mapper.SysFileAnnexMapper;
import com.jpmsoft.fileUpload.oss.crc.CrcOssUtil;
import com.jpmsoft.fileUpload.pojo.TInformationFilling;
import com.jpmsoft.fileUpload.pojo.dto.*;
import com.jpmsoft.fileUpload.pojo.dto.easyexcel.CjOaProjectReportDto;
import com.jpmsoft.fileUpload.pojo.dto.easyexcel.GeneralFormsUpDTO;
import com.jpmsoft.fileUpload.pojo.dto.easyexcel.InvInfrastructureMonthDtlDto;
import com.jpmsoft.fileUpload.pojo.entity.*;
import com.jpmsoft.fileUpload.pojo.vo.*;
import com.jpmsoft.fileUpload.service.*;
import com.jpmsoft.fileUpload.utils.*;
import com.jpmsoft.fileUpload.utils.DateUtil;
import dm.jdbc.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FileUploadImpl implements IFileUploadService {
    private static final String DOCX = ".docx";
    private static final String OA_NOTICE_CONTENT_PLACEHOLDER = "${OA_NOTICE_N_CONTENT_HTML}";
    private static final Set<String> REQUIRED_ESTIMATE_FEE_TYPES =
            Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
                    "工程费用",
                    "工程建设其他费用",
                    "预备费"
            )));

    //用于日志打印
    Logger logger = LoggerFactory.getLogger(FileUploadImpl.class);

    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISysFileDownloadService sysFileDownloadService;
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private IPreProjectService preProjectService;
    @Resource
    private IInvProjectPlanService invProjectPlanService;
    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private ISecExpertService secExpertService;
    @Resource
    private ISecSelectionRecordService secSelectionRecordService;
    @Resource
    private ISysDictionaryService sysDictionaryService;
    @Resource
    private IArchiveFileService archiveFileService;
    @Resource
    private ISysDownloadLogService downloadLogService;
    @Resource
    private ISysFileTemplateService sysFileTemplateService;
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysTableColumnService sysTableColumnService;
    @Resource
    private IWatermarkService watermarkService;
    @Resource
    private SysFileAnnexMapper sysFileAnnexMapper;
    @Resource
    private ITInformationFillingService tInformationFillingService;
    @Resource
    private PmFeign pmFeign;
    @Resource
    private OaFeign oaFeign;
    @Resource
    private BpmFeign bpmFeign;
    @Resource
    private ISysFileTemplatePrintService sysFileTemplatePrintService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ArchiveMapper archiveMapper;

    @Value("${file.upload.dirPath}")
    private String dirPath;

    @Value("${file.upload.zipTemporary}")
    private String zipTemporary;

    @Override
    public BaseResponse removeFileByIds(String fileIds) {
        if (ValidateUtil.isEmpty(fileIds)) return BaseResponse.ok();
        logger.info("删除本地文件！");
        String root = sysConfigService.getRootDirectory();
        String archive = sysConfigService.getArchiveDirectory();
        if (ValidateUtil.isAnObjEmpty(root, archive)) return BaseResponse.fail(FileMsg.missSysConfig);
        List<SysFileAnnex> entitys = sysFileAnnexService.getFileListByFileIds(fileIds);
        OssClientParamsDto conf = sysConfigService.getOssParams();
        for (SysFileAnnex entity : entitys) {
            if (entity != null) {
                if (entity.isFileNotRemove())
                    return BaseResponse.fail(String.format(FileMsg.fileNotRemove, entity.getFileName()));
                String filePath;
                String ossKey = null;
                if (entity.getFileLocation() == 0) {
                    filePath = root + "/" + entity.getFilePath();
                    if (conf != null) ossKey = conf.getOssRootDir() + "/" + entity.getFilePath();
                } else {
                    filePath = archive + "/" + entity.getFilePath();
                    if (conf != null) ossKey = conf.getOssArchiveDir() + "/" + entity.getFilePath();
                }
                File files = new File(filePath);
                if (files.exists()) {
                    if (ValidateUtil.isNotEmpty(entity.getFileSourceType()) && entity.getFileSourceType().equals(FileSourceEnum.SystemFile.getIndex())) {
                        //系统关联类型 未确认上传之前的文件地址指向的是源文件
                        if (entity.getUploadStatus() == 1) {
                            files.delete();
                            if (entity.isFileUpOss()) CrcOssUtil.deleteFile(conf, ossKey);
                        }
                    } else {
                        files.delete();
                        if (entity.isFileUpOss()) CrcOssUtil.deleteFile(conf, ossKey);
                    }
                }
                sysFileAnnexService.removeById(entity.getFileId());
            }
        }
        return new BaseResponse(true, "删除成功");
    }

    @Override
    public String breakpointDownload(String fileIds) {
        String root = sysConfigService.getRootDirectory();
        String archive = sysConfigService.getArchiveDirectory();
        if (root == null || archive == null) throw new JPMException(FileMsg.missSysConfig);
        SysFileDownload downloadPojo = sysFileDownloadService.getSysFileDownload(fileIds);
        String downId = GenerateUUID.getUUID();
        if (downloadPojo != null) {
            //已存在文件或者压缩包
            downId = downloadPojo.getDownId();
        } else {
            //不存在文件或者压缩包
            String[] files = fileIds.split(",");
            SysFileDownload pojo = new SysFileDownload();
            pojo.setFileIds(fileIds);
            pojo.setDownCreateTime(new Date());
            pojo.setDownId(downId);
            OssClientParamsDto param = sysConfigService.getOssParams();
            if (files.length == 1) {
                //单文件使用当前文件即可
                SysFileAnnex entity = sysFileAnnexService.getById(fileIds);
                //判断本地文件是否存在，不存在则尝试从oss中拉取
                CrcOssUtil.downLocalNotExistFile(param, entity.getFilePath(), entity.getFileLocation() == 0 ? root : archive, entity.getFileLocation());
                pojo.setFilePath(entity.getFilePath());
            } else {
                //多文件
                //需要压缩处理
                File[] srcFiles = new File[files.length];
                for (int i = 0; i < files.length; i++) {
                    SysFileAnnex entity = sysFileAnnexService.getById(files[i]);
                    if (entity.getFileLocation() == 0) srcFiles[i] = new File(root + entity.getFilePath());
                        //归档目录文件
                    else if (entity.getFileLocation() == 1) srcFiles[i] = new File(archive + entity.getFilePath());
                    //判断本地文件是否存在，不存在则尝试从oss中拉取
                    CrcOssUtil.downLocalNotExistFile(param, entity.getFilePath(), entity.getFileLocation() == 0 ? root : archive, entity.getFileLocation());
                }
                String zipPath = zipTemporary + "/" + GenerateUUID.getUUID() + ".zip";
                File zipFile = new File(root + "/" + zipPath);
                try {
                    File zipDir = new File(root + "/" + zipTemporary + "/");
                    if (!zipDir.isDirectory() || !zipDir.exists()) zipDir.mkdirs();
                    zipFile.createNewFile();
                } catch (IOException io) {
                    throw new JPMException(FileMsg.createZipFail);
                }
                FileUtil.zipFiles(srcFiles, zipFile);
                pojo.setFilePath(zipPath);
            }
            sysFileDownloadService.insertSysFileDownload(pojo);
        }
        return "/jpm/downloadFile/" + downId;
    }

    @Override
    public List<DownloadUrlDto> downloadAloneBath(List<ProFileAnnex> files) {
        if (CollectionUtils.isEmpty(files)) return null;
        Map<String, String> configs = sysConfigService.getSysValueByCodes(Arrays.asList("rootDirectory", "archiveDirectory", "downloadUrl"));
        List<String> fileIds = files.stream().map(SysFileAnnex::getFileId).collect(Collectors.toList());
        List<SysFileDownload> downloads = sysFileDownloadService.getSysFileDownloadBath(fileIds);
        List<DownloadUrlDto> datas = new ArrayList<>();
        List<SysFileDownload> downs = new ArrayList<>();
        Date now = new Date();
        fileIds.forEach(fileId -> {
            DownloadUrlDto data = new DownloadUrlDto();
            data.setFileIds(fileId);
            List<SysFileDownload> download = downloads.stream().filter(n -> n.getFileIds().equals(fileId)).collect(Collectors.toList());
            String downId;
            if (ValidateUtil.isNotEmpty(download)) {
                downId = download.stream().findFirst().get().getDownId();
            } else {
                SysFileDownload down = new SysFileDownload();
                down.setFileIds(fileId);
                down.setDownCreateTime(now);
                down.setDownId(GenerateUUID.getUUID());
                ProFileAnnex entity = files.stream().filter(n -> n.getFileId().equals(fileId)).findFirst().get();
                down.setFilePath(entity.getFilePath());
                downs.add(down);
                downId = down.getDownId();
            }
            data.setUrl(configs.get("downloadUrl") + "/jpm/downloadFile/" + downId);
            data.setNoHostUrl("/fileServer/jpm/downloadFile/" + downId);
            data.setOpenUrl(configs.get("downloadUrl") + "/jpm/downloadAloneOpenFile/" + fileId);
            datas.add(data);
        });
        if (ValidateUtil.isNotEmpty(downs)) sysFileDownloadService.saveBatch(downs);
        return datas;
    }


    @Override
    public void downloadFile(HttpServletRequest request, HttpServletResponse response, String downId, LogUserInfoVo user, Boolean down) throws Exception {
        String root = sysConfigService.getRootDirectory();
        String archive = sysConfigService.getArchiveDirectory();
        if (root == null || archive == null) throw new JPMException(FileMsg.missSysConfig);
        SysFileDownload downloadPojo = sysFileDownloadService.getById(downId);
        if (downloadPojo == null) throw new JPMException(HttpStatus.SC_NO_CONTENT, FileMsg.noneDownFile);
        String fileName = null;
        String[] files = downloadPojo.getFileIds().split(",");
        if (files.length == 1) {//单文件
            SysFileAnnex file = sysFileAnnexService.getById(files[0]);
            if (file != null) fileName = file.getFileName();
            //归档的文件
            if (file.getFileLocation() == 1) root = archive;
            //判断本地文件是否存在，不存在则尝试从oss中拉取
            CrcOssUtil.downLocalNotExistFile(sysConfigService.getOssParams(), file.getFilePath(), root, file.getFileLocation());
        }
        FileUtil.downloadExistsFile(request, response, new File(root + "/" + downloadPojo.getFilePath()), fileName);
        downloadLogService.create(user, downloadPojo.getFileIds(), down);
    }


    @Override
    public void downloadAloneFile(HttpServletRequest request, HttpServletResponse response, String downId, LogUserInfoVo user, Boolean down) throws Exception {
        String root = sysConfigService.getRootDirectory();
        if (root == null) throw new JPMException(FileMsg.missSysConfig);
        SysFileAnnex file = sysFileAnnexService.getById(downId);
        if (file == null) throw new JPMException(HttpStatus.SC_NO_CONTENT, FileMsg.noneDownFile);
        //判断本地文件是否存在，不存在则尝试从oss中拉取
        CrcOssUtil.downLocalNotExistFile(sysConfigService.getOssParams(), file.getFilePath(), root, file.getFileLocation());
        FileUtil.downloadExistsFile(request, response, new File(root + "/" + file.getFilePath()), file.getFileName());
        downloadLogService.create(user, file, down);
    }

    @Override
    public void downloadArchivesFile(HttpServletRequest request, HttpServletResponse response, String downId, LogUserInfoVo user, Boolean down) throws Exception {
        String archive = sysConfigService.getArchiveDirectory();
        if (archive == null) throw new JPMException(FileMsg.missSysConfig);
        SysFileAnnex file = sysFileAnnexService.getById(downId);
        if (file == null) throw new JPMException(HttpStatus.SC_NO_CONTENT, FileMsg.downFail);
        //判断本地文件是否存在，不存在则尝试从oss中拉取
        CrcOssUtil.downLocalNotExistFile(sysConfigService.getOssParams(), file.getFilePath(), archive, file.getFileLocation());
        FileUtil.downloadExistsFile(request, response, new File(archive + "/" + file.getFilePath()), file.getFileName());
        downloadLogService.create(user, file, down);
    }

    @Override
    public void doc2Pdf2View(HttpServletRequest request, HttpServletResponse response, String watermarkId, String downId, LogUserInfoVo user) throws Exception {
        String archive = sysConfigService.getArchiveDirectory();
        String root = sysConfigService.getRootDirectory();
        String fontsDirectory = sysConfigService.getSysConfByCode("fileFontsDirectory");
        if (archive == null || root == null) throw new JPMException(FileMsg.missSysConfig);
        SysFileAnnex entity = sysFileAnnexService.getById(downId);
        if (entity == null) throw new JPMException(FileMsg.noneFile);
        String filePath = "";
        if (entity.getFileLocation() == 0) filePath = root + "/" + entity.getFilePath();
        else filePath = archive + "/" + entity.getFilePath();
        CrcOssUtil.downLocalNotExistFile(sysConfigService.getOssParams(), entity.getFilePath(), entity.getFileLocation() == 0 ? root : archive, entity.getFileLocation());
        //临时存储地址PDF
        String temFileDir = root + "/" + dirPath + "/JPBPMTemporaryFolder/";
        File fileDir = new File(temFileDir);
        if (!fileDir.exists()) fileDir.mkdir();
        String temFile = temFileDir + GenerateUUID.getUUID() + ".PDF";
        WatermarkControlDto wc = sysConfigService.getWatermarkControlConf();
        if (ValidateUtil.isNotEmpty(watermarkId)) {
            AsposeUtil.wordToPdf(filePath, temFile, watermarkService.getByWatermarkId(root, watermarkId), fontsDirectory, wc, user);
        } else
            AsposeUtil.wordToPdf(filePath, temFile, fontsDirectory, wc, user, sysUserService.getById(user.getId()));
//        Document doc = new Document(filePath);
//        doc.saveToFile(temFile, FileFormat.PDF);
        File pdfFile = new File(temFile);
        FileUtil.downloadExistsFile(request, response, pdfFile, "");
        pdfFile.delete();
    }

    @Override
    public void watermarkDown(HttpServletRequest request, HttpServletResponse response, String downId, String waterId, LogUserInfoVo user) throws Exception {
        String archive = sysConfigService.getArchiveDirectory();
        String root = sysConfigService.getRootDirectory();
        String fontsDirectory = sysConfigService.getSysConfByCode("fileFontsDirectory");
        if (archive == null || root == null) throw new JPMException(FileMsg.missSysConfig);
        SysFileAnnex entity = sysFileAnnexService.getById(downId);
        if (entity == null) throw new JPMException(FileMsg.noneFile);
        if (entity.getFileSuffix().equalsIgnoreCase("doc")
                || entity.getFileSuffix().equalsIgnoreCase("docx")
                || entity.getFileSuffix().equalsIgnoreCase("pdf")) {
            String filePath = "";
            if (entity.getFileLocation() == 0) filePath = root + "/" + entity.getFilePath();
            else filePath = archive + "/" + entity.getFilePath();
            CrcOssUtil.downLocalNotExistFile(sysConfigService.getOssParams(), entity.getFilePath(), entity.getFileLocation() == 0 ? root : archive, entity.getFileLocation());
            //临时存储地址PDF
            String temFileDir = root + "/" + dirPath + "/JPBPMTemporaryFolder/";
            File fileDir = new File(temFileDir);
            if (!fileDir.exists()) fileDir.mkdir();
            String temFile = temFileDir + GenerateUUID.getUUID() + "." + entity.getFileSuffix();
            WatermarkControlDto wc = sysConfigService.getWatermarkControlConf();
            File pdfFile = new File(temFile);
            try {
                if (entity.getFileSuffix().equalsIgnoreCase("pdf"))
                    AsposeUtil.pdfWatermark(filePath, temFile, watermarkService.getByWatermarkId(root, waterId, true), fontsDirectory, wc, user);
                else
                    AsposeUtil.wordWatermark(filePath, temFile, watermarkService.getByWatermarkId(root, waterId), fontsDirectory, wc, user);
                FileUtil.downloadExistsFile(request, response, pdfFile, "");
            } finally {
                pdfFile.delete();
                fileDir.delete();
            }
        } else throw new JPMException("仅支持pdf、doc、docx附件！");
    }

    @Override
    public void downloadOpenFile(HttpServletRequest request, HttpServletResponse response, String downId) throws Exception {
        String root = sysConfigService.getRootDirectory();
        if (root == null) throw new JPMException(FileMsg.missSysConfig);
        SysFileAnnex entity = sysFileAnnexService.getOneFileAnnexByRelationId(downId);
        if (entity == null) throw new JPMException(HttpStatus.SC_NO_CONTENT, FileMsg.noneDownFile);
        //判断本地文件是否存在，不存在则尝试从oss中拉取
        CrcOssUtil.downLocalNotExistFile(sysConfigService.getOssParams(), entity.getFilePath(), root, entity.getFileLocation());
        FileUtil.downloadExistsFile(request, response, new File(root + "/" + entity.getFilePath()), entity.getFileName());
    }


    @Override
    public void batchDownloadByEntityIds(HttpServletRequest request, HttpServletResponse response, FormBatchDownDto dto, LogUserInfoVo user) {
        List<SysFileAnnex> datas = sysFileAnnexService.getFileByEntityIds(dto.getEntityIds());
        batchDownloadByBath(request, response, datas);
        downloadLogService.create(user, datas);
    }

    @Override
    public void batchDownloadByFileIds(HttpServletRequest request, HttpServletResponse response, List<String> ids, LogUserInfoVo user) {
        if (!CollectionUtils.isEmpty(ids)) {
            List<SysFileAnnex> datas = sysFileAnnexService.getFileListByIds(ids);
            batchDownloadByBath(request, response, datas);
            downloadLogService.create(user, datas);
        } else throw new JPMException(FileMsg.noReqParam);
    }

    /**
     * 文件批量zip下载
     *
     * @param request
     * @param response
     * @param datas
     */
    public void batchDownloadByBath(HttpServletRequest request, HttpServletResponse response, List<SysFileAnnex> datas) {
        if (CollectionUtils.isEmpty(datas)) throw new JPMException(FileMsg.noneDownFile);
        String root = sysConfigService.getRootDirectory();
        String archive = sysConfigService.getArchiveDirectory();
        try {
            //判断本地文件是否存在，不存在则尝试从oss中拉取
            OssClientParamsDto param = sysConfigService.getOssParams();
            if (param != null) {
                datas.forEach(data -> {
                    CrcOssUtil.downLocalNotExistFile(param, data.getFilePath(), root, data.getFileLocation());
                });
            }
            FileUtil.batchDownload(request, response, root, archive, datas);
        } catch (IOException ex) {
            throw new JPMException(FileMsg.batchDownFail);
        }
    }


    //region 上传解析 导出附件
    @Override
    public BaseResponse uploadGanttPlanXml(HttpServletRequest request, MultipartFile file) {
        String url = null;
        String root = sysConfigService.getRootDirectory();
        if (root == null) return BaseResponse.fail(FileMsg.missSysConfig);
        String fileName = file.getOriginalFilename();
        if (ValidateUtil.isEmpty(fileName)) return BaseResponse.fail(FileMsg.unSupportedFormat);
        if (!fileName.matches(".*\\.(xml)$")) return BaseResponse.fail(FileMsg.onlyXml);
        String[] name = fileName.split("\\.");
        String fileSuffix = name[name.length - 1];
        try {
            //临时存储
            String realAddres = upload(file, fileSuffix, GenerateUUID.getUUID(), "JPBPMTemporaryFolder", root);
            File tfFile = new File(root + "/" + realAddres);
            if (!tfFile.exists() || !tfFile.isFile()) return new BaseResponse(FileMsg.fileParsingFail);
            url = root + "/" + realAddres;
            GanttPlanResponse plan = Dom4jXml.xml2Object(tfFile);
            tfFile.delete();
            return new BaseResponse(true, "", plan);
        } catch (Exception ex) {
            if (ValidateUtil.isEmpty(url)) {
                File tfFile = new File(url);
                tfFile.delete();
            }
            ex.printStackTrace();

            return new BaseResponse(FileMsg.fileParsingFail);
        }
    }

    @Override
    public BaseResponse uploadGanttPlan(MultipartFile file, HttpServletRequest request) {
        File tfFile = temporaryStorageFile(file);
        String url = tfFile.getPath();
        String fileName = file.getOriginalFilename();
        try {
            if (fileName.toLowerCase(Locale.ENGLISH).matches(".*\\.(xls|xlsx)$")) {
                //上传的excel
                List<GanttTasksDto> datas = ExcelUtil.uploadGanttTasksExcel(url);
                if (datas == null) return new BaseResponse(FileMsg.fileParsingFail);
                List<GanttTasksDto> models = datas.stream().filter(n -> n.getTaskParent().equals("0")).collect(Collectors.toList());
                getGanttTaskChild(models, datas);
                return BaseResponse.ok(models);
            } else {//上传的mpp
                return BaseResponse.ok(MpxjUtil.readInputStream(url, sysDictionaryService.getDictionaryByParentCode("JPMsoft_GTLINKTYPE")));
            }
        } catch (Exception ex) {
            if (tfFile.exists() && tfFile.isFile()) tfFile.delete();
            ex.printStackTrace();
            return new BaseResponse(FileMsg.fileParsingFail);
        }
//
//        try {
//            //临时存储
//            String realAddres = upload(file, fileSuffix, GenerateUUID.getUUID(), "JPBPMTemporaryFolder", root);
//            File tfFile = new File(root + "/" + realAddres);
//            if (!tfFile.exists() || !tfFile.isFile())
//                return new BaseResponse(FileMsg.fileParsingFail);
//            url = root + "/" + realAddres;
//            if (fileName.toLowerCase(Locale.ENGLISH).matches(".*\\.(xls|xlsx)$")) {
//                //上传的excel
//                List<GanttTasksDto> datas = ExcelUtil.uploadGanttTasksExcel(url);
//                if (datas == null)
//                    return new BaseResponse(FileMsg.fileParsingFail);
//                List<GanttTasksDto> models = datas.stream().filter(n -> n.getTaskParent().equals("0")).collect(Collectors.toList());
//                getGanttTaskChild(models, datas);
//                return new BaseResponse(true, "", models);
//            } else {//上传的mpp
//                return new BaseResponse(true, "", MpxjUtil.readInputStream(url));
//            }
//        } catch (Exception ex) {
//            if (ValidateUtil.isEmpty(url)) {
//                File tfFile = new File(url);
//                tfFile.delete();
//            }
//            ex.printStackTrace();
//
//            return new BaseResponse(FileMsg.fileParsingFail);
//        }
    }

    @Override
    public BaseResponse uploadInvPlanExcel(HttpServletRequest request, MultipartFile file) {
        File tfFile = temporaryStorageFile(file);
        String url = tfFile.getPath();
        try {
            BaseResponse res = ExcelUtil.poiAnalysisExcel(url);
            if (res.getSuccess() == false) return res;
            List<InvProjectPlanResponse> datas = JacksonUtils.parseArray(JacksonUtils.toJSONString(res.getData()), InvProjectPlanResponse.class);
            if (datas == null) return new BaseResponse(FileMsg.fileParsingFail);
            tfFile.delete();
            return BaseResponse.ok(datas);
        } catch (Exception ex) {
            if (tfFile.exists() && tfFile.isFile()) tfFile.delete();
            ex.printStackTrace();
            return new BaseResponse(FileMsg.fileParsingFail);
        }
    }

    @Override
    public BaseResponse uploadBudgetDeclareExcel(HttpServletRequest request, MultipartFile file) {
        File tfFile = temporaryStorageFile(file);
        String url = tfFile.getPath();
        try {
            List<InvBudgetDeclareDtlModel> datas = ExcelUtil.uploadBudgetDeclareExcel(url);
            if (datas == null) return new BaseResponse(FileMsg.fileParsingFail);
            tfFile.delete();
            return BaseResponse.ok(datas);
        } catch (Exception ex) {
            if (tfFile.exists() && tfFile.isFile()) tfFile.delete();
            ex.printStackTrace();
            return new BaseResponse(FileMsg.fileParsingFail);
        }
    }

    @Override
    public BaseResponse uploadEstimateBreakdownExecel(HttpServletRequest request, MultipartFile file) {
        File tfFile = temporaryStorageFile(file);
        String url = tfFile.getPath();
        try {
            Map<String, Object> map = ExcelUtil.uploadEstimateBreakdownExecel(url);
            List<InvestmentEstimateBreakdownDetailsModel> datas = (List<InvestmentEstimateBreakdownDetailsModel>) map.get("rootNodes");
            String msg = map.get("msg").toString();
            if (datas == null) return new BaseResponse(FileMsg.fileParsingFail);
            tfFile.delete();
            // 校验模板有效性
            if (ValidateUtil.isEmpty(datas)) {
                return BaseResponse.fail("导入文件结构与模板不符！");
            }

            String firstTypeName = datas.get(0).getIsbdFeeTypeName();
            Set<String> actualTypeNames = datas.stream()
                    .map(InvestmentEstimateBreakdownDetailsModel::getIsbdFeeTypeName)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            if (ValidateUtil.isEmpty(firstTypeName)
                    || !"工程总投资".equals(firstTypeName)
                    || !actualTypeNames.containsAll(REQUIRED_ESTIMATE_FEE_TYPES)) {
                return BaseResponse.fail("导入文件结构与模板不符！");
            }
            return BaseResponse.ok(msg, datas);
        } catch (Exception ex) {
            if (tfFile.exists() && tfFile.isFile()) tfFile.delete();
            ex.printStackTrace();
            return new BaseResponse(ex.getMessage());
        }
    }

    /**
     * 下载估算分解子表execl模板
     *
     * @param response
     */
    @Override
    public void downloadEstimateBreakdownExecelTemplate(HttpServletResponse response) {
        String fileName = "估（概）算模板表.xlsx";
        // 读取配置文件拿到文件的relationId
        String relationId = sysConfigService.getSysConfByCode("EstimationTemplate");
        if (ValidateUtil.isEmpty(relationId)) {
            throw new JPMException(FileMsg.missSysConfig);
        }

        String root = sysConfigService.getRootDirectory();
        if (ValidateUtil.isEmpty(root)) {
            throw new JPMException(FileMsg.missSysConfig);
        }

        SysFileAnnex entity = sysFileAnnexService.getOneFileAnnexByRelationId(relationId);
        if (entity == null) {
            throw new JPMException(HttpStatus.SC_NO_CONTENT, FileMsg.noneDownFile);
        }

        // 判断本地文件是否存在，不存在则尝试从oss中拉取
        CrcOssUtil.downLocalNotExistFile(
                sysConfigService.getOssParams(),
                entity.getFilePath(),
                root,
                entity.getFileLocation()
        );

        File template;
        try {
            template = File.createTempFile("estimateBreakdownExecelTemplate", ".xlsx");
        } catch (IOException e) {
            log.error("创建临时文件失败", e);
            throw new JPMException(FileMsg.fileParsingFail);
        }

        // 把源文件复制到临时文件
        Path sourcePath = Paths.get(root, entity.getFilePath());
        try (InputStream in = Files.newInputStream(sourcePath);
             OutputStream out = Files.newOutputStream(template.toPath())) {
            byte[] buffer = new byte[8192];
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
        } catch (IOException e) {
            log.error("复制预算分解模板失败", e);
            throw new JPMException(FileMsg.fileParsingFail);
        }

        // 读取字典
        List<DictDto> dictList = DictionaryUtils.getDictList("JPMsoft_DW");
        if (CollectionUtils.isEmpty(dictList)) {
            // 字典为空，直接返回原模板
            FileUtil.downFileAndDelFile(response, template.getPath(), fileName);
            return;
        }

        String[] options = dictList.stream()
                .map(DictDto::getCode)
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .filter(s -> !"其他".equalsIgnoreCase(s))
                .distinct()
                .toArray(String[]::new);

        if (options.length == 0) {
            // 过滤后无可用选项，直接返回原模板
            FileUtil.downFileAndDelFile(response, template.getPath(), fileName);
            return;
        }

        try (FileInputStream fis = new FileInputStream(template);
             XSSFWorkbook workbook = (XSSFWorkbook) WorkbookFactory.create(fis)) {

            XSSFSheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                throw new JPMException("模板sheet不存在");
            }

            // 清空当前sheet上的数据验证
            clearAllDataValidations(sheet);

            // 重新添加 E2:E13 的下拉
            CellRangeAddressList addressList = new CellRangeAddressList(1, 12, 4, 4);
            DataValidationHelper helper = sheet.getDataValidationHelper();
            DataValidationConstraint constraint = helper.createExplicitListConstraint(options);
            DataValidation validation = helper.createValidation(constraint, addressList);

            validation.setSuppressDropDownArrow(true);
            validation.setShowErrorBox(true);
            validation.setEmptyCellAllowed(true);

            sheet.addValidationData(validation);

            try (FileOutputStream fos = new FileOutputStream(template)) {
                workbook.write(fos);
                fos.flush();
            }

        } catch (Exception e) {
            log.error("处理预算分解模板下拉失败", e);
            throw new JPMException(FileMsg.fileParsingFail);
        }

        // 下载文件
        FileUtil.downFileAndDelFile(response, template.getPath(), fileName);
    }

    /**
     * 彻底清空sheet上所有数据验证
     */
    private void clearAllDataValidations(XSSFSheet sheet) {
        if (sheet == null) {
            return;
        }

        List<? extends DataValidation> validations = sheet.getDataValidations();
        if (validations != null) {
            validations.clear();
        }

        if (sheet.getCTWorksheet().isSetDataValidations()) {
            sheet.getCTWorksheet().unsetDataValidations();
        }
    }

    @Override
    public BaseResponse uploadDrBudgetDeclareExcel(HttpServletRequest request, MultipartFile file) {
        File tfFile = temporaryStorageFile(file);
        String url = tfFile.getPath();
        try {
            List<DrBudgetDeclareDtlModel> datas = ExcelUtil.uploadDrBudgetDeclareExcel(url);
            if (datas == null) return new BaseResponse(FileMsg.fileParsingFail);
            tfFile.delete();
            return BaseResponse.ok(datas);
        } catch (Exception ex) {
            if (tfFile.exists() && tfFile.isFile()) tfFile.delete();
            ex.printStackTrace();
            return new BaseResponse(FileMsg.fileParsingFail);
        }
    }

    @Override
    public BaseResponse uploadNewBudgetDeclareExcel(HttpServletRequest request, MultipartFile file) {
        File tfFile = temporaryStorageFile(file);
        String url = tfFile.getPath();
        try {
            Map<String, Object> result = ExcelUtil.uploadNewBudgetDeclareExcel(url);
            List<InvBudgetDeclareDtlModel> datas = (List<InvBudgetDeclareDtlModel>) result.get("datas");
            if (datas == null) return new BaseResponse(FileMsg.fileParsingFail);
            tfFile.delete();
            return BaseResponse.ok(result);
        } catch (Exception ex) {
            if (tfFile.exists() && tfFile.isFile()) tfFile.delete();
            ex.printStackTrace();
            return new BaseResponse(FileMsg.fileParsingFail);
        }
    }

    @Override
    public BaseResponse uploadProjectRegisterDtlExcel(HttpServletRequest request, MultipartFile file) throws Exception {
        File tfFile = temporaryStorageFile(file);
        String url = tfFile.getPath();
        try {
            List<PreProjectRegisterDtlModel> datas = ExcelUtil.uploadProjectRegisterDtlExcel(url);
            if (datas == null) return new BaseResponse(FileMsg.fileParsingFail);
            tfFile.delete();
            return BaseResponse.ok(datas);
        } catch (Exception ex) {
            if (tfFile.exists() && tfFile.isFile()) tfFile.delete();
            ex.printStackTrace();
            return new BaseResponse(FileMsg.fileParsingFail);
        }
    }

    @Override
    public BaseResponse uploadPreProApprovalDtlExcel(HttpServletRequest request, MultipartFile file) throws Exception {
        File tfFile = temporaryStorageFile(file);
        String url = tfFile.getPath();
        try {
            List<PreProApprovalDtlModel> datas = ExcelUtil.uploadPreProApprovalDtlExcel(url);
            if (datas == null) return new BaseResponse(FileMsg.fileParsingFail);
            tfFile.delete();
            return BaseResponse.ok(datas);
        } catch (Exception ex) {
            if (tfFile.exists() && tfFile.isFile()) tfFile.delete();
            ex.printStackTrace();
            return new BaseResponse(FileMsg.fileParsingFail);
        }
    }

    @Override
    public BaseResponse uploadPreProjectDtlExcel(HttpServletRequest request, MultipartFile file) throws Exception {
        File tfFile = temporaryStorageFile(file);
        String url = tfFile.getPath();
        try {
            List<PreProjectDtlModel> datas = ExcelUtil.uploadPreProjectDtlExcel(url);
            if (datas == null) return new BaseResponse(FileMsg.fileParsingFail);
            tfFile.delete();
            return BaseResponse.ok(datas);
        } catch (Exception ex) {
            if (tfFile.exists() && tfFile.isFile()) tfFile.delete();
            ex.printStackTrace();
            return new BaseResponse(FileMsg.fileParsingFail);
        }
    }


    @Override
    public BaseResponse uploadFinalAccountsApplyExcel(HttpServletRequest request, MultipartFile file) throws Exception {
        File tfFile = temporaryStorageFile(file);
        String url = tfFile.getPath();
        try {
            List<InvFinalAccountsApplyDtlDto> datas = ExcelUtil.uploadFinalAccountsApplyExcel(url);
            if (datas == null) return new BaseResponse(FileMsg.fileParsingFail);
            tfFile.delete();
            return BaseResponse.ok(datas);
        } catch (Exception ex) {
            if (tfFile.exists() && tfFile.isFile()) tfFile.delete();
            ex.printStackTrace();
            return new BaseResponse(FileMsg.fileParsingFail);
        }
    }

    @Override
    public BaseResponse uploadAndCompressPdf(HttpServletRequest request, MultipartFile file) {
        try {
            byte[] byteArr = file.getBytes();
            InputStream inputStream = new ByteArrayInputStream(byteArr);
            ItextPdfUtil.manipulatePdf(inputStream, "D:/123456.pdf");
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return null;
    }

    @Override
    public BaseResponse uploadInvProPlanExcel(HttpServletRequest request, MultipartFile file, String uId) {
        File tfFile = temporaryStorageFile(file);
        String url = tfFile.getPath();
        try {
            //临时存储
            List<InvProjectPlan> datas = ExcelUtil.uploadInvProPlanOneExcel(url);
            if (datas == null) return new BaseResponse(FileMsg.fileParsingFail);
            tfFile.delete();
            return invProjectPlanService.importInvProjectPlanByExcel(datas, uId);
        } catch (Exception ex) {
            if (tfFile.exists() && tfFile.isFile()) tfFile.delete();
            ex.printStackTrace();
            return new BaseResponse(FileMsg.fileParsingFail);
        }
    }


    @Override
    public BaseResponse uploadCjOaProReportExcel(HttpServletRequest request, MultipartFile file, String uId) {
//        File tfFile = temporaryStorageFile(file);
//        String url = tfFile.getPath();
        try {
//            List<OaProjectReportModel> datas = null;
//            BaseResponse res = ExcelUtil.uploadOaProReportExcelMonth(url, true, sysDictionaryService.getCodeList("JPMsoft_PRCONSSTATE")
//                    , sysDictionaryService.getCodeList("JPMsoft_UNDER"));
//            if (res.getSuccess() == false) return res;
//            datas = JacksonUtils.parseArray(JacksonUtils.toJSONString(res.getData()), OaProjectReportModel.class);
//            if (datas == null)
//                return new BaseResponse(FileMsg.fileParsingFail);
//            tfFile.delete();
            List<CjOaProjectReportDto> cjDtos = new EasyExcelData<CjOaProjectReportDto>().getExcelData(file, CjOaProjectReportDto.class, 1);
            if (CollectionUtils.isEmpty(cjDtos)) return new BaseResponse(FileMsg.fileParsingFail);
            List<OaProjectReportModel> datas = new ArrayList<>();
            for (int i = 0; i < cjDtos.size(); i++) {
                OaProjectReportModel model = new OaProjectReportModel();
                BeanUtils.copyProperties(cjDtos.get(i), model);
                datas.add(model);
                if (model.getPrWhetherLag() && ValidateUtil.isEmpty(model.getPrReasonLag()))
                    return new BaseResponse(MessageFormat.format(FileMsg.cellIsEmpty, i + 1, ExcelUtil.numCovertLetter(51)));
            }
            return preProjectService.assignOaProReportPid(datas, uId, 3);
        } catch (ExcelDataConvertException ex) {
            throw new JPMException(String.format(FileMsg.unknownFormat, ex.getRowIndex(), ExcelUtil.numCovertLetter(ex.getColumnIndex() + 1)));
        } catch (JPMException jp) {
            throw jp;
        } catch (Exception ex) {
//            if (tfFile.exists() && tfFile.isFile()) {
//                tfFile.delete();
//            }
            ex.printStackTrace();
            return new BaseResponse(FileMsg.fileParsingFail);
        }
    }

    @Override
    @Deprecated
    public BaseResponse uploadOaProReportExcel(HttpServletRequest request, Integer type, MultipartFile file, String uId) {
        File tfFile = temporaryStorageFile(file);
        String url = tfFile.getPath();
        try {
            List<OaProjectReportModel> datas = null;
            if (type == 1) {
                datas = ExcelUtil.uploadOaProReportExcelWeek(url, type);
            } else {
                BaseResponse res = ExcelUtil.uploadOaProReportExcelMonth(url, false, null, null);
                if (res.getSuccess() == false) return res;
                datas = JacksonUtils.parseArray(JacksonUtils.toJSONString(res.getData()), OaProjectReportModel.class);
            }
            if (datas == null) return new BaseResponse(FileMsg.fileParsingFail);
            tfFile.delete();
            return preProjectService.assignOaProReportPid(datas, uId, type);
        } catch (Exception ex) {
            if (tfFile.exists() && tfFile.isFile()) {
                tfFile.delete();
            }
            ex.printStackTrace();
            return new BaseResponse(FileMsg.fileParsingFail);
        }
    }

    @Override
    public BaseResponse uploadSecExpertExcel(HttpServletRequest request, MultipartFile file, String uId) {
        File tfFile = temporaryStorageFile(file);
        String url = tfFile.getPath();
        try {
            //临时存储
            BaseResponse res = ExcelUtil.uploadSecExpertExcel(url);
            if (res.getSuccess() == false) return res;
            List<SecExpertModel> datas = JacksonUtils.parseArray(JacksonUtils.toJSONString(res.getData()), SecExpertModel.class);
            return secExpertService.importSecExpert(datas, uId, sysUserService.getUserNameByUId(uId));
        } catch (Exception ex) {
            if (tfFile.exists() && tfFile.isFile()) tfFile.delete();
            ex.printStackTrace();
            return new BaseResponse(FileMsg.fileParsingFail);
        }
    }

    @Override
    public void exportGanttTasksProcessWord(HttpServletRequest request, HttpServletResponse response, GanttPlanExportRequest req, String uId) {
        String root = sysConfigService.getRootDirectory();
        String title = sysConfigService.getSysConfByCode("ganttTaskWordTitle");
        String dNameStr = sysConfigService.getSysConfByCode("scheduleDepartmentsequence");
        List<String> dNameList = new ArrayList<>();
        if (!StringUtil.isEmpty(dNameStr)) {
            try {
                dNameList = JacksonUtils.parseArray(dNameStr, String.class);
            } catch (Exception ex) {
            }
        }
        if (ValidateUtil.isAnObjEmpty(root, title)) throw new JPMException(FileMsg.missSysConfig);
        BaseResponse res = apiUtilsService.getGanttTasksProcessExport(req);
        String actionUser = "";
        if (!ValidateUtil.isEmpty(uId)) {
            SysUser user = sysUserService.getById(uId);
            if (user != null) actionUser = user.getuName();
        }
        if (res != null) {
            Object data = res.getData();
            if (data != null) {
                List<GanttTasksProcessModel> models = JacksonUtils.parseArray(JacksonUtils.toJSONString(data), GanttTasksProcessModel.class);
                if (models != null && models.size() > 0) {
                    List<String> photosRelations = models.stream().filter(n -> !ValidateUtil.isEmpty(n.getdName())).map(GanttTasksProcessModel::getGtpScenePhotos).distinct().collect(Collectors.toList());
                    List<SysFileAnnex> photos = sysFileAnnexService.getFilePojoByFileRelationIds(photosRelations);
                    String docName = GenerateUUID.getUUID() + DOCX;
                    String docPath = root + "/" + zipTemporary + "/" + docName;
                    AsposeUtil.exportGanttTasksProcessWord(docPath, models, root, photos, title, actionUser, dNameList);
                    File doc = new File(docPath);
                    if (doc.exists() && doc.isFile()) FileUtil.download(docPath, response, docName);
                }
            }
        }
    }

    @Override
    public void exportSecExpertSelectionRecordWord(HttpServletRequest request, HttpServletResponse response, String srId) {
        String root = sysConfigService.getRootDirectory();
        String title = sysConfigService.getSysConfByCode("expertSelectionRecordTitle");
        if (ValidateUtil.isAnObjEmpty(root, title)) throw new JPMException(FileMsg.missSysConfig);
        SecSelectionRecord entity = secSelectionRecordService.getById(srId);
        if (ValidateUtil.isEmpty(root)) throw new JPMException(FileMsg.noneFile);
        List<SecExpert> eps = secExpertService.getSecExpertBySrId(srId);
        if (ValidateUtil.isEmpty(eps)) throw new JPMException(FileMsg.noneExportRecord);
        List<File> allDocFile = new ArrayList<>();
        String guid = GenerateUUID.getUUID();
        for (SecExpert ep : eps) {
            String docName = ep.getExName() + DOCX;
            String docPath = root + "/JPBPMTemporaryFolder/" + guid + "/" + docName;
            AsposeUtil.exportSecExpertSelectionRecordWord(ep, entity, docPath, title);
            allDocFile.add(new File(docPath));
        }
        String zipName = GenerateUUID.getUUID() + ".zip";
        File zipFile = new File(root + "/" + zipTemporary + "/" + zipName);
        try {
            File zipDir = new File(root + "/" + zipTemporary + "/");
            if (!zipDir.isDirectory() || !zipDir.exists()) zipDir.mkdirs();
            zipFile.createNewFile();
        } catch (IOException io) {
            throw new JPMException(FileMsg.createZipFail);
        }
        FileUtil.zipFiles(allDocFile.toArray(new File[allDocFile.size()]), zipFile);
        if (zipFile.exists() && zipFile.isFile()) {
            FileUtil.download(zipFile.getPath(), response, zipName);
            zipFile.delete();
        }
        //删除doc
        for (File file : allDocFile) {
            if (file.exists() && file.isFile()) file.delete();
        }
    }

    @Override
    public void exportExpertSelectionRecordWordOffline(HttpServletRequest request, HttpServletResponse response, String srId) {
        String root = sysConfigService.getRootDirectory();
        String title = sysConfigService.getSysConfByCode("expertSelectionRecordTitle");
        if (ValidateUtil.isAnObjEmpty(root, title)) throw new JPMException(FileMsg.missSysConfig);
        SecSelectionRecord entity = secSelectionRecordService.getById(srId);
        if (ValidateUtil.isEmpty(root)) throw new JPMException(FileMsg.noneExportRecord);
        List<SecExpert> eps = secExpertService.getSecExpertBySrId(srId);
        if (ValidateUtil.isEmpty(eps)) throw new JPMException(FileMsg.noneExportRecord);
        String guid = GenerateUUID.getUUID();
        String docName = title + DOCX;
        String docPath = root + "/JPBPMTemporaryFolder/" + guid + "/" + docName;
        AsposeUtil.exportExpertSelectionRecordWordOffline(eps, entity, docPath, title, sysDictionaryService.getDictionaryByParentCode("JPMsoft_ZJLB"));
        FileUtil.download(docPath, response, docName);
        File file = new File(docPath);
        if (file.exists() && file.isFile()) file.delete();
    }

    @Override
    public BaseResponse importGmInvProjectPlan(HttpServletRequest request, HttpServletResponse response, MultipartFile file) throws Exception {
        String root = sysConfigService.getRootDirectory();
        String path = saveExcelFile(root, file);
        List<GmInvProPlanDto> datas = ExcelUtil.importGmInvProPlan(path);
        return invProjectPlanService.importGmInvProPlan(datas);
    }

    @Override
    public BaseResponse importConBoqDtl(HttpServletRequest request, HttpServletResponse response, MultipartFile file, String pId, String read) throws Exception {
        String root = sysConfigService.getRootDirectory();
        String path = saveExcelFile(root, file);
        List<ConBoqDtlDto> dtls = ExcelUtil.importConBopDtl(path, read);
        if (ValidateUtil.isNotEmpty(dtls)) {
            //region 返回格式
            List<ConBoqDtlVo> datas = new ArrayList<>();
            //概算清单
            List<InvBudgetDeclareDtlDto> invBds = apiUtilsService.getInvBudgetDeclareDtlListByPid(pId);
            List<SysDictionary> dictionarys = sysDictionaryService.getDictionaryByParentCode("JPMsoft_QDLB");
            //if (ValidateUtil.isEmpty(invBds)) return BaseResponse.fail(FileMsg.notFoundInvBudgetDeclareDtl);
            dtls.forEach(dtl -> {
                //if (ValidateUtil.isEmpty(invB))
                //    throw new JPMException(String.format(FileMsg.notFoundBudgetDeclareDtlCode, dtl.getBoqdCode()));
                ConBoqDtlVo data = new ConBoqDtlVo();
                BeanUtils.copyProperties(dtl, data);
                data.setBoqdId(GenerateUUID.getUUID());
                if (!CollectionUtils.isEmpty(dictionarys)) {
                    List<SysDictionary> types = dictionarys.stream().filter(n -> n.getDName().equals(dtl.getBoqdType())).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(types)) data.setBoqdType(types.stream().findFirst().get().getDCode());
                    else data.setBoqdType(null);
                } else data.setBoqdType(null);
                if (CollectionUtils.isEmpty(invBds) && ValidateUtil.isNotEmpty(dtl.getBddName()))
                    throw new JPMException(FileMsg.notFoundInvBudgetDeclareDtl);
                if (!CollectionUtils.isEmpty(invBds) && ValidateUtil.isNotEmpty(dtl.getBddName())) {
                    List<InvBudgetDeclareDtlDto> invBq = invBds.stream().filter(n -> n.getBddName().equals(dtl.getBddName())).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(invBq)) {
                        InvBudgetDeclareDtlDto inv = invBq.stream().findFirst().get();
                        data.setBoqdBddId(inv.getBddId());
                        data.setBddName(inv.getBddName());
                    } else {
                        throw new JPMException(String.format(FileMsg.notFoundBudgetDeclareDtlName, dtl.getBddName()));
                    }
                }
                datas.add(data);
            });
            //endregion
            //region 组树
            if (!CollectionUtils.isEmpty(datas)) {
                for (ConBoqDtlVo data : datas) {
                    if (ValidateUtil.isEmpty(data.getBoqdParentCode()) || !data.getBoqdParentCode().contains("."))
                        continue;
                    else {
                        String parent = data.getBoqdParentCode().substring(0, data.getBoqdParentCode().lastIndexOf("."));
                        List<ConBoqDtlVo> parents = datas.stream().filter(n -> ValidateUtil.isNotEmpty(n.getBoqdParentCode()) && n.getBoqdParentCode().equals(parent)).collect(Collectors.toList());
                        if (CollectionUtils.isEmpty(parents))
                            throw new JPMException(String.format(FileMsg.notFoundBoqParent, data.getBoqdParentCode().substring(2), data.getBoqdName()));
                    }
                }
                //根级节点
                List<ConBoqDtlVo> rootTrees = datas.stream().filter(n -> ValidateUtil.isEmpty(n.getBoqdParentCode()) || !n.getBoqdParentCode().contains(".")).collect(Collectors.toList());
                //组装树
                List<ConBoqDtlVo> trees = new ArrayList<>();
                int sort = 0;
                for (ConBoqDtlVo rootTree : rootTrees) {
                    rootTree.setBoqdParentId("0");
                    rootTree.setBoqdSort(sort += 1);
                    rootTree.setBoqdId(GenerateUUID.getUUID());
                    List<ConBoqDtlVo> childNodes = new ArrayList<>();
                    setChildNodes(datas, childNodes, rootTree.getBoqdParentCode(), rootTree.getBoqdId());
                    rootTree.setChildNodes(childNodes);
                    trees.add(rootTree);
                }
                return BaseResponse.ok(trees);
            }
            //endregion
            return BaseResponse.ok(datas);
        }
        return BaseResponse.ok(dtls);
    }

    private void setChildNodes(List<ConBoqDtlVo> datas, List<ConBoqDtlVo> childNodes, String parentCode, String parentId) {
        String rex = parentCode + ".";
        List<ConBoqDtlVo> childVos = datas.stream().filter(n -> ValidateUtil.isNotEmpty(n.getBoqdParentCode()) && n.getBoqdParentCode().length() > rex.length() && !n.getBoqdParentCode().replaceFirst(rex, "").contains(".")).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(childVos)) {
            int sort = 0;
            for (ConBoqDtlVo c : childVos) {
                ConBoqDtlVo child = new ConBoqDtlVo();
                BeanUtils.copyProperties(c, child);
                String code = child.getBoqdParentCode();
                child.setBoqdParentId(parentId);
                child.setBoqdSort(sort += 1);
                child.setBoqdId(GenerateUUID.getUUID());
                List<ConBoqDtlVo> childs = new ArrayList<>();
                setChildNodes(datas, childs, code, child.getBoqdId());
                child.setChildNodes(childs);
                childNodes.add(child);
            }
        }
    }

    @Override
    public BaseResponse importConMeasureRecord(HttpServletRequest request, HttpServletResponse response, MultipartFile file, String conId) throws Exception {
        String root = sysConfigService.getRootDirectory();
        String path = saveExcelFile(root, file);
        List<ConMeasureRecordDto> records = ExcelUtil.importConMeasureRecord(path);
        if (ValidateUtil.isNotEmpty(records)) {
            List<ConMeasureRecordVo> datas = new ArrayList<>();
            //合同工程清单
            List<ConBoqDtlDto> boqs = apiUtilsService.getConBoqDtlByConId(conId);
            //计量单
            List<ConMearDeclareDto> declares = apiUtilsService.getConMearDeclareByConId(conId);
            if (ValidateUtil.isEmpty(boqs)) return BaseResponse.fail(FileMsg.notFoundBoqDtl);
            records.forEach(record -> {
                List<ConBoqDtlDto> boq = boqs.stream().filter(n -> n.getBoqdCode().equals(record.getMearBoqdCode())).collect(Collectors.toList());
                if (ValidateUtil.isEmpty(boq))
                    throw new JPMException(String.format(FileMsg.notFoundBoqDtlCode, record.getMearBoqdCode()));
                ConMeasureRecordVo data = new ConMeasureRecordVo();
                BeanUtils.copyProperties(record, data);
                ConBoqDtlDto dtl = boq.stream().findFirst().get();
                data.setMearBoqdUnit(dtl.getBoqdUnit());
                data.setMearBoqdName(dtl.getBoqdName());
                data.setMearBoqdAmount(dtl.getBoqdAmount());
                data.setBoqdPrice(dtl.getBoqdPrice());
                data.setBoqdTotalPrice(dtl.getBoqdTotalPrice());
                data.setBoqdId(dtl.getBoqdId());
                data.setMearDeclaredTotal(MathUtil.floatMultiplyDecm(data.getMearDeclareAmount(), data.getBoqdPrice()));
                //措施费比率
                data.setMearRate(dtl.getBoqdRate());
                //规费比率
                data.setMearFeesRatio(dtl.getBoqdFeesRatio());
                //其他项目比率
                data.setMearOtherRatio(dtl.getBoqdOtherRatio());
                //税金比率
                data.setMearTaxesRatio(dtl.getBoqdTaxesRatio());
                //计算公式
                data.setMearFormula(dtl.getBoqdFormula());
                data.setBoqdParentId(dtl.getBoqdParentId());
                data.setBoqdParentName(dtl.getBoqdParentName());
                data.setBoqdType(dtl.getBoqdType());
                List<ConMearDeclareDto> declare = null;
                if (!CollectionUtils.isEmpty(declares))
                    declare = declares.stream().filter(n -> ValidateUtil.isNotEmpty(n.getMearBoqdCode()) && n.getMearBoqdCode().equals(record.getMearBoqdCode())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(declare)) {
                    ConMearDeclareDto cmd = declare.stream().findFirst().get();
                    data.setMearUpperAmount(cmd.getMearCumulativeAmount());
                    data.setMearUpperTotal(cmd.getMearCumulativeTotal());
                } else {
                    data.setMearUpperAmount(0F);
                    data.setMearUpperTotal(BigDecimal.ZERO);
                }
                data.setMearCumulativeAmount(MathUtil.floatAddFloat(data.getMearUpperAmount(), data.getMearDeclareAmount()));
                data.setMearCumulativeTotal(data.getMearUpperTotal().add(data.getMearDeclaredTotal()));
                datas.add(data);
            });
            return BaseResponse.ok(datas);
        }
        return BaseResponse.ok(records);
    }

    @Override
    public BaseResponse importInfrastructureMonth(HttpServletRequest request, HttpServletResponse response, MultipartFile file) {
        List<InvInfrastructureMonthDtlDto> datas = new EasyExcelData<InvInfrastructureMonthDtlDto>().getExcelData(file, InvInfrastructureMonthDtlDto.class, 6);
        if (ValidateUtil.isNotEmpty(datas)) {
            List<String> pCodes = datas.stream().map(InvInfrastructureMonthDtlDto::getPId).collect(Collectors.toList());
            List<PreProject> projects = preProjectService.getPreProjectByCodes(pCodes);
            List<InvInfrastructureMonthDtlVo> results = new ArrayList<>();
            int i = 1;
            for (InvInfrastructureMonthDtlDto data : datas) {
                List<PreProject> pros = projects.stream().filter(n -> n.getpCode().equals(data.getPId())).collect(Collectors.toList());
                if (ValidateUtil.isNotEmpty(pros)) {
                    PreProject pro = pros.stream().findFirst().get();
                    InvInfrastructureMonthDtlVo result = new InvInfrastructureMonthDtlVo();
                    BeanUtils.copyProperties(data, result);
                    result.setId(GenerateUUID.getUUID());
                    result.setPId(pro.getpId());
                    result.setPName(pro.getpName());
                    result.setPCode(pro.getpCode());
                    result.setImdSort(i);
                    i += 1;
                    results.add(result);
                }
            }
            return BaseResponse.ok(results);
        } else return BaseResponse.fail(FileMsg.excelNotData);
    }

    @Override
    public BaseResponse uploadBoqdXml(HttpServletRequest request, MultipartFile file) {
        File tfFile = temporaryStorageFile(file);
        String url = tfFile.getPath();
        try {
            List<ConBoqDtlXmlTreeDto> plan = BoqDtlXml.analysisBoqDtlXml(tfFile);
            tfFile.delete();
            return new BaseResponse(true, "", plan);
        } catch (Exception ex) {
            if (tfFile.exists() && tfFile.isFile()) tfFile.delete();
            ex.printStackTrace();
            return new BaseResponse(FileMsg.fileParsingFail);
        }
    }

    @Resource
    private ISysImpLogService sysImpLogService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse importFormData(MultipartFile[] upFiles, FormDataImpDTO request, LogUserInfoVo user) {
        String root = sysConfigService.getRootDirectory();
        String arDir = sysConfigService.getArchiveDirectory();
        Boolean deleteImportZip = sysConfigService.deleteImportZip();
        List<String> decPaths = new LinkedList<>();
        List<File> tfFiles = new LinkedList<>();
        List<String> fjPaths = new LinkedList<>();
        if (ValidateUtil.isAnObjEmpty(root, arDir)) return BaseResponse.fail(FileMsg.missSysConfig);
        if (upFiles == null || upFiles.length < 1) return BaseResponse.fail(FileMsg.noneFile);
        try {
            for (MultipartFile file : upFiles) {
                File tfFile = temporaryStorageFile(root, file, ".*\\.(zip)$", FileMsg.onlyZip, "universalImport");
                String decPath = root + "/JPBPMTemporaryFolder/decompression/" + GenerateUUID.getUUID();
                FileUtil.decompression(tfFile.getPath(), decPath);
                tfFiles.add(tfFile);
                decPaths.add(decPath);
                if (deleteImportZip) //通用导入配置了删除源文件
                    tfFile.delete();//解压完成直接删除压缩包
                File[] excels = FileUtil.findExcel(decPath);
                if (excels == null || excels.length < 1) {
                    sysImpLogService.create(user, tfFile, FileMsg.notFoundExcel, file.getOriginalFilename(), deleteImportZip);
                    FileUtil.deleteFiles(new File(decPath), true);//删除解压的目录
                    return BaseResponse.fail(FileMsg.notFoundExcel);
                }
                try {
                    for (File excel : excels) {
                        String excelName = excel.getName();
                        String tCode = excelName.split("\\.")[0];
                        if (tCode.contains("(")) tCode = tCode.substring(tCode.indexOf("(") + 1, tCode.indexOf(")"));
                        try {
                            SysFileTemplate temp = sysFileTemplateService.getByCode(tCode);
                            if (ValidateUtil.isNull(temp)) {
                                sysImpLogService.create(user, tfFile, FileMsg.notFoundTemp, file.getOriginalFilename(), deleteImportZip);
                                return BaseResponse.fail(String.format(FileMsg.notFoundTemp, tCode));
                            }
                            List<SysTableColumn> columns = sysTableColumnService.getColumnListByTableCode(temp.gettTable(), "imp");
                            if (CollectionUtils.isEmpty(columns)) {
                                sysImpLogService.create(user, tfFile, FileMsg.tableColumnDeletion, file.getOriginalFilename(), deleteImportZip);
                                return BaseResponse.fail(FileMsg.tableColumnDeletion);
                            }
                            GeneralFormsUpDTO data = ExcelUtil.readCompressedExcel(new File[]{excel}, columns, request);
                            if (CollectionUtils.isEmpty(data.getStrData())) {
                                sysImpLogService.create(user, tfFile, FileMsg.notSeeColumn, file.getOriginalFilename(), deleteImportZip);
                                return BaseResponse.fail(FileMsg.notSeeColumn);
                            }
                            String pk = temp.gettPrimaryKey().toUpperCase(Locale.ENGLISH);
                            //region 附件数据,拷贝转移附件、生成附件数据
                            List<SysFileAnnex> files = new ArrayList<>();
                            List<Map<String, String>> fileData = data.getFileData();
                            List<Map<String, String>> strData = data.getStrData();
                            List<FormDataImpArchiveDTO> archives = new ArrayList<>();
                            if (!CollectionUtils.isEmpty(fileData)) {
                                String path = FileUtil.path(dirPath, tCode, user.getId(), root);
                                for (int i = 0; i < fileData.size(); i++) {
                                    Map<String, String> f = fileData.get(i);
                                    Map<String, String> d = strData.get(i);
                                    String entityId = GenerateUUID.getUUID();
                                    d.put(pk, entityId);
                                    FormDataImpArchiveDTO fa = new FormDataImpArchiveDTO();
                                    fa.setAdCode(d.get("FILEARCHIVEDIREXLPZ"));
                                    fa.setPId(d.get(data.getProjectKey()));
                                    for (Map.Entry<String, String> entry : f.entrySet()) {
                                        String key = entry.getKey();
                                        List<String> fileValues = new ArrayList<>();
                                        List<SysFileAnnex> arfs = new ArrayList<>();
                                        if (ValidateUtil.isNotEmpty(entry.getValue())) fileValues.add(entry.getValue());
                                        if (key.equalsIgnoreCase("fileRelationId") && !CollectionUtils.isEmpty(data.getOtherFiles()))
                                            fileValues.addAll(data.getOtherFiles());
                                        if (!CollectionUtils.isEmpty(fileValues)) {
                                            String relationId = GenerateUUID.getUUID();
                                            d.put(key, relationId);
                                            for (String value : fileValues) {
                                                if (ValidateUtil.isNotEmpty(value)) {
                                                    value = value.replaceAll("\\\\", "/");
                                                    String fileName = value.substring(value.lastIndexOf("/") + 1);
                                                    String[] name = fileName.split("\\.");
                                                    String fileSuffix = name[name.length - 1];
                                                    String newName = GenerateUUID.getUUID() + "." + fileSuffix;
                                                    String source = decPath + "/" + value;
                                                    FileUtil.moveTo(source, root + "/" + path, newName);
                                                    fjPaths.add(root + "/" + path + "/" + newName);
                                                    SysFileAnnex fe = sysFileAnnexService.entity(root, path, 0, fileName, newName, fileSuffix, key, entityId, relationId, user, temp);
                                                    files.add(fe);
                                                    arfs.add(fe);
                                                }
                                            }
                                        }
                                        fa.setFiles(arfs);
                                        archives.add(fa);
                                    }
                                    strData.set(i, d);
                                }
                            }
                            //endregion
                            //region 将字典项转成编码，将链表字段转成ID
                            //字典项转成编码
                            //字段名对应字典信息
                            Map<String, List<SysDictionary>> dics = new HashMap<>();
                            if (data.getDictionaryMp() != null) {
                                List<String> codes = new ArrayList<>(data.getDictionaryMp().values());
                                List<SysDictionary> allDic = sysDictionaryService.getDictionaryByParentCodes(codes);
                                if (!CollectionUtils.isEmpty(allDic)) {
                                    data.getDictionaryMp().forEach((k, v) -> {
                                        dics.put(k, allDic.stream().filter(f -> f.getParentCode().equals(v)).collect(Collectors.toList()));
                                    });
                                }
                            }
                            Map<String, Map<String, String>> tbs = new HashMap<>();
                            if (data.getKeySql() != null) {
                                data.getKeySql().forEach((k, v) -> {
                                    List<String> codes = strData.stream().filter(f -> f.containsKey(k)).map(d -> d.get(k)).collect(Collectors.toList());
                                    tbs.put(k, sysFileService.selectByImportSQL(v, codes));
                                });
                            }
                            for (int i = 0; i < strData.size(); i++) {
                                Map<String, String> sd = strData.get(i);
                                sd.forEach((k, v) -> {
                                    //替换字典值
                                    if (dics.size() > 0 && dics.containsKey(k)) {
                                        if (ValidateUtil.isNotEmpty(v)) {
                                            List<SysDictionary> dic = dics.get(k);
                                            List<String> dNames = Arrays.asList(v.split(","));
                                            List<SysDictionary> d = dic.stream().filter(f -> dNames.contains(f.getDName())).collect(Collectors.toList());
                                            if (CollectionUtils.isEmpty(d) || d.size() != dNames.size())
                                                throw new JPMException(String.format(FileMsg.unKnowDic, v));
                                            else
                                                sd.put(k, d.stream().map(SysDictionary::getDCode).collect(Collectors.joining(",")));
                                        }
                                    }
                                    //替换表主键
                                    if (tbs.size() > 0 && tbs.containsKey(k)) {
                                        if (ValidateUtil.isNotEmpty(v)) {
//                                        if (!tbs.containsKey(k) || !tbs.get(k).containsKey(v))
//                                            throw new JPMException(String.format(FileMsg.unKnowLinked, v));
                                            if (tbs.containsKey(k) && ValidateUtil.isNotNull(tbs.get(k)) && tbs.get(k).containsKey(v)) {
                                                sd.put(k, tbs.get(k).get(v));
                                                //将归档记录的主键文本替换成主键ID
                                                if (!CollectionUtils.isEmpty(archives) && k.equalsIgnoreCase(data.getProjectKey())) {
                                                    archives.forEach(archive -> {
                                                        if (!CollectionUtils.isEmpty(archive.getFiles()) &&
                                                                tbs.get(k).containsKey(archive.getPId())) {
                                                            archive.setPId(tbs.get(k).get(v));
                                                        }
                                                    });
                                                }
                                            } else
                                                throw new JPMException(String.format(FileMsg.unKnowLinked, v));
                                        }
                                    }
                                });
                                strData.set(i, sd);
                            }
                            //endregion
                            //region 校验唯一值
                            List<SysTableColumn> onlyCms = columns.stream().filter(f -> ValidateUtil.isNotNull(f.getColumnOnly()) && f.getColumnOnly()).collect(Collectors.toList());
                            List<Map> olnyData = sysFileService.selectOnlyColumn(onlyCms, temp);
                            if (!CollectionUtils.isEmpty(olnyData)) {
                                for (SysTableColumn o : onlyCms) {
                                    List<Map> ods = olnyData.stream().filter(f -> f.containsKey(o.getColumnCode())).collect(Collectors.toList());
                                    if (!CollectionUtils.isEmpty(ods)) {
                                        List<Object> d = ods.stream().map(s -> s.get(o.getColumnCode())).collect(Collectors.toList());
                                        for (Map<String, String> sd : strData) {
                                            String code = o.getColumnCode().toUpperCase(Locale.ENGLISH);
                                            if (sd.containsKey(code) && ValidateUtil.isNotEmpty(sd.get(code))) {
                                                if (d.contains(sd.get(code)))
                                                    throw new JPMException(String.format(FileMsg.onlyNotIns, sd.get(code)));
                                                else d.add(sd.get(code));
                                            }
                                        }
                                    }
                                }
                            } else {//库内没有数据仅新数据
                                if (!CollectionUtils.isEmpty(onlyCms)) {
                                    for (SysTableColumn o : onlyCms) {
                                        String code = o.getColumnCode().toUpperCase(Locale.ENGLISH);
                                        List<String> d = new ArrayList<>();
                                        for (Map<String, String> sd : strData) {
                                            if (sd.containsKey(code) && ValidateUtil.isNotEmpty(sd.get(code))) {
                                                if (d.contains(sd.get(code)))
                                                    throw new JPMException(String.format(FileMsg.onlyNotIns, sd.get(code)));
                                                else d.add(sd.get(code));
                                            }
                                        }
                                    }
                                }
                            }
                            //endregion
                            //region 组装业务表单SQL以及file表
                            List<String> formSql = new ArrayList<>();
                            List<SysFile> sysFiles = new ArrayList<>();
                            for (Map<String, String> d : strData) {
                                if (!d.containsKey(pk)) d.put(pk, GenerateUUID.getUUID());
                                d.put(temp.gettStateKey().toUpperCase(Locale.ENGLISH), ApprovalStatusEnum.Already_Over.getIndex().toString());
                                StringBuilder sb = new StringBuilder();
                                sb.append("insert into ");
                                StringBuilder keys = new StringBuilder();
                                keys.append(" (");
                                StringBuilder values = new StringBuilder();
                                values.append(" VALUES(");
                                boolean start = true;
                                for (Map.Entry<String, String> entry : d.entrySet()) {
                                    //文档附件
                                    if (!entry.getKey().equalsIgnoreCase("FILERELATIONID") &&
                                            !entry.getKey().equalsIgnoreCase("fileArchiveDirExlPz")) {
                                        if (start) {
                                            keys.append(entry.getKey());
                                            if (ValidateUtil.isNotEmpty(entry.getValue())) {
                                                values.append("'").append(entry.getValue()).append("'");
                                            } else values.append("NULL");
                                            start = false;
                                        } else {
                                            keys.append(",").append(entry.getKey());
                                            if (ValidateUtil.isNotEmpty(entry.getValue())) {
                                                values.append(",").append("'").append(entry.getValue()).append("'");
                                            } else values.append(",").append("NULL");
                                        }
                                    }
                                }
                                keys.append(")");
                                values.append(");");
                                formSql.add(sb.append(temp.gettTable()).append(keys).append(values).toString());
                                sysFiles.add(sysFileService.file(d, temp, user, data.getProjectKey()));
                            }
                            //endregion
                            if (!CollectionUtils.isEmpty(files)) sysFileAnnexService.saveBatch(files);
                            if (!CollectionUtils.isEmpty(formSql)) sysFileService.inertSql(formSql);
                            if (!CollectionUtils.isEmpty(sysFiles)) sysFileService.saveBatch(sysFiles);
                            archiveFileService.formImpArchive(archives, user, root, arDir);
                        } catch (JPMException jp) {
                            sysImpLogService.create(user, tfFile, jp.getMsg(), file.getOriginalFilename(), deleteImportZip);
                            return BaseResponse.fail(jp.getCode(), "[" + excelName + "]" + jp.getMsg());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            sysImpLogService.create(user, tfFile, ex.getMessage(), file.getOriginalFilename(), deleteImportZip);
                            return BaseResponse.fail("[" + excelName + "]" + FileMsg.fileParsingFail);
                        }
                    }
                } finally {
                    FileUtil.deleteFiles(new File(decPath), true);
                }
                sysImpLogService.create(user, tfFile, FileMsg.actSuccess, file.getOriginalFilename(), deleteImportZip);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("============事务发生异常,回滚附件================");
            for (String fj : fjPaths) {
                FileUtil.deleteFiles(new File(fj), true);
            }
        } finally {
            for (String decPath : decPaths) {
                FileUtil.deleteFiles(new File(decPath), true);
            }
            if (deleteImportZip) {
                for (File tfFile : tfFiles) {
                    tfFile.delete();
                }
            }
        }

        return BaseResponse.ok();
    }

    @Override
    public void exportFormData(HttpServletResponse response, FormDataExpDTO req, LogUserInfoVo user) {
        if (CollectionUtils.isEmpty(req.getTCodes()) && CollectionUtils.isEmpty(req.getTableCodes()))
            throw new JPMException(FileMsg.notFoundTCode);
        if (CollectionUtils.isEmpty(req.getProjectIds()) || CollectionUtils.isEmpty(req.getContractIds()))
            throw new JPMException(FileMsg.notSelectProCon);
        if (req.getProjectIds().size() != req.getContractIds().size())
            throw new JPMException(FileMsg.proConDy);
        boolean isTCode = true;
        if (CollectionUtils.isEmpty(req.getTCodes())) {
            req.setTCodes(req.getTableCodes());
            isTCode = false;
        }
        //临时根目录
        String root = sysConfigService.getRootDirectory();
        String archive = sysConfigService.getArchiveDirectory();
        if (ValidateUtil.isAllEmpty(root, archive))
            throw new JPMException(FileMsg.missSysConfig);
        String tmpDir = root + "/JPBPMTemporaryFolder/decompression/";
        String decPath = tmpDir + GenerateUUID.getUUID();
        //文件根目录
        String fileDir = decPath + "/Files";
        File dir = new File(decPath);
        dir.mkdirs();
        File fDir = new File(fileDir);
        fDir.mkdirs();
        try {
            for (String tCode : req.getTCodes()) {
                for (int i = 0; i < req.getProjectIds().size(); i++) {
                    String tableCode = tCode;
                    String tName = null;
                    if (ValidateUtil.isNotEmpty(tCode) && isTCode) {
                        SysFileTemplate temp = sysFileTemplateService.getByCode(tCode);
                        if (ValidateUtil.isNull(temp))
                            throw new JPMException(String.format(FileMsg.notFoundTemp, tCode));
                        tableCode = temp.gettTable();
                        tName = temp.gettName();
                    }
                    List<SysTableColumn> columns = sysTableColumnService.getColumnListByTableCode(tableCode, "other");
                    if (CollectionUtils.isEmpty(columns)) throw new JPMException(FileMsg.tableColumnDeletion);
                    //表单数据
                    List<Map> data = req.isOnlyTemp() ? null : sysFileService.selectByTableColumn(columns, tableCode, req);
                    columns = columns.stream().filter(f -> ValidateUtil.isNotNull(f.getColumnExp()) && f.getColumnExp()).collect(Collectors.toList());
                    if (CollectionUtils.isEmpty(columns)) throw new JPMException(FileMsg.tableColumnDeletion);
                    List<Map> projects = preProjectService.getPreProjectByIds(columns, Arrays.asList(req.getProjectIds().get(i)));
                    List<Map> contracts = preProjectService.getContractByIds(columns, Arrays.asList(req.getContractIds().get(i)));
                    //通用附件
                    columns.add(new SysTableColumn().setColumnCode("fileArchiveDirExlPz").setColumnFile(Boolean.FALSE).setColumnName("附件归档目录编码"));
                    columns.add(new SysTableColumn().setColumnCode("fileRelationId").setColumnFile(Boolean.TRUE).setColumnName("通用附件"));
                    List<String> fileCodes = null;
                    List<String> dicCodes = columns.stream().filter(f -> ValidateUtil.isNotNull(f.getFilterType()) && f.getFilterType() == 3).map(SysTableColumn::getAssocTableCode).collect(Collectors.toList());
                    List<SysDictionary> dics = sysDictionaryService.getDictionaryByParentCodes(dicCodes);
                    List<String> dicColumns = columns.stream().filter(f -> ValidateUtil.isNotNull(f.getFilterType()) && f.getFilterType() == 3).map(SysTableColumn::getColumnCode).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(data)) {
                        //region 字典code转字典名称
                        if (!CollectionUtils.isEmpty(dicColumns)) {
                            List<SysDictionary> finalDics = dics;
                            List<String> finalDicColumns = dicColumns;
                            List<SysTableColumn> finalColumns = columns;
                            data.forEach(m -> {
                                m.forEach((k, v) -> {
                                    if (finalDicColumns.contains(k) && ValidateUtil.isNotEmpty(v.toString())) {
                                        List<String> codes = Arrays.asList(v.toString().split(","));
                                        List<SysTableColumn> dColumns = finalColumns.stream().filter(f -> f.getColumnCode().equals(k)).collect(Collectors.toList());
                                        String dic = dColumns.stream().findFirst().get().getAssocTableCode();
                                        List<SysDictionary> dicNames = finalDics.stream().filter(f -> f.getParentCode().equals(dic) && codes.contains(f.getDCode())).collect(Collectors.toList());
                                        if (!CollectionUtils.isEmpty(dicNames)) {
                                            m.put(k, dicNames.stream().map(SysDictionary::getDName).collect(Collectors.joining(",")));
                                        } else m.put(k, null);
                                    }
                                });
                            });
                        }
                        //endregion
                        //region 附件处理
                        List<String> relationIds = new ArrayList<>();
                        fileCodes = columns.stream().filter(f -> ValidateUtil.isNotNull(f.getColumnFile()) && f.getColumnFile()).map(SysTableColumn::getColumnCode).collect(Collectors.toList());
                        List<String> finalFileCodes = fileCodes;
                        data.forEach(m -> {
                            m.forEach((k, v) -> {
                                        if (finalFileCodes.contains(k)) relationIds.add(v.toString());
                                    }
                            );
                        });
                        List<SysFileAnnex> allFiles = sysFileAnnexService.getFilePojoByFileRelationIds(relationIds);
                        List<SysFileAnnex> files = new ArrayList<>();
                        //排除无效文件
                        if (!CollectionUtils.isEmpty(allFiles)) {
                            for (SysFileAnnex f : allFiles) {
                                String path = root;
                                if (f.getFileLocation() == 1) path = archive;
                                File file = new File(path + "/" + f.getFilePath());
                                if (file.exists() && file.isFile()) {
                                    //附件路径调整成完整的绝对路径
                                    f.setFilePath(file.getAbsolutePath());
                                    files.add(f);
                                }
                            }
                        }
                        data.forEach(m -> {
                            m.forEach((k, v) -> {
                                if (finalFileCodes.contains(k)) {
                                    List<SysFileAnnex> fs = files.stream().filter(f -> f.getFileRelationId().equals(v.toString())).collect(Collectors.toList());
                                    if (!CollectionUtils.isEmpty(fs)) {
                                        if (fs.size() > 1) {
                                            String zipName = GenerateUUID.getUUID() + ".zip";
                                            FileUtil.zipFiles(fs.stream().map(f -> {
                                                return new File(f.getFilePath());
                                            }).toArray(File[]::new), new File(fileDir + "/" + zipName));
                                            m.put(k, "Files\\\\" + zipName);
                                        } else {
                                            String name = GenerateUUID.getUUID() + "." + fs.get(0).getFileSuffix();
                                            FileUtil.moveTo(fs.get(0).getFilePath(), fileDir, name);
                                            m.put(k, "Files\\\\" + name);
                                        }
                                    } else m.put(k, null);
                                }
                            });
                        });
                        //endregion
                    }
                    try {
                        ExcelUtil.exportFormData(data, columns, fileCodes, decPath, tCode, tName, dics, dicColumns, projects, contracts);
                    } catch (IOException exception) {
                        throw new JPMException(FileMsg.expError);
                    }
                }
            }
            String downZip = tmpDir + GenerateUUID.getUUID() + ".zip";
            FileUtil.zip(decPath, downZip);
            FileUtil.downFileAndDelFile(response, downZip, GenerateUUID.getUUID() + ".zip");
        } finally {
            FileUtil.deleteFiles(new File(decPath), true);
        }
    }

    @Override
    public File temporaryStorageXml(MultipartFile file) {
        return temporaryStorageFile(file, ".*\\.(xml)$", FileMsg.onlyXml);
    }

    //endregion
    @Override
    public BaseResponse innerAppointUpload(MultipartFile file, InnerFileUploadAppointDTO dto) {
        String root = sysConfigService.getRootDirectory();
        String archive = sysConfigService.getArchiveDirectory();
        if (ValidateUtil.isAnObjEmpty(archive, root)) throw new JPMException(FileMsg.missSysConfig);
        String path = dto.getPath();
        if (dto.isArchiveDir()) path = archive + "/" + path;
        else path = root + "/" + path;
        File nf = new File(path);
        try {
            file.transferTo(nf);
        } catch (IOException ex) {
            log.error("method:innerAppointUpload 文件保存失败！", ex);
            ex.printStackTrace();
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse uploadOss(MultipartFile file, HttpServletRequest request) {
        String url = null;
        String root = sysConfigService.getRootDirectory();
        if (root == null) return BaseResponse.fail(FileMsg.missSysConfig);
        String fileName = file.getOriginalFilename();
        if (ValidateUtil.isEmpty(fileName)) return BaseResponse.fail(FileMsg.unSupportedFormat);
        String[] name = fileName.split("\\.");
        String fileSuffix = name[name.length - 1];
        try {
            //临时存储
            String realAddres = upload(file, fileSuffix, GenerateUUID.getUUID(), "JPBPMTemporaryFolder", root);
            OssClientParamsDto params = sysConfigService.getOssParams();
            String key = CrcOssUtil.upload(params, root + realAddres, params.getOssRootDir() + realAddres.substring(0, realAddres.lastIndexOf("/") + 1));
            logger.info("大文件OSS上传地址！" + key);
        } catch (Exception ex) {

        }

        return null;
    }

    @Override
    public BaseResponse uploadFile(MultipartFile[] files, UploadDto req, String updateType, int status, String teId) {
        return uploadFile(files, req, updateType, status, teId, null, false);
    }

    @Override
    public BaseResponse uploadImage2Pdf(MultipartFile[] files, UploadDto req, String updateType, int status, String teId) {
        return uploadFile(files, req, updateType, status, teId, null, true);
    }

    public BaseResponse uploadFile(MultipartFile[] files, UploadDto req, String updateType, int status, String teId, String regex,
                                   boolean img2Pdf) {
        logger.info("上传文件！");
        try {
            String root = null;
            String downloadUrl = sysConfigService.getDownloadUrl();
            int fileLoation = 0;
            if (updateType.equals("root")) root = sysConfigService.getRootDirectory();
            else if (updateType.equals("archive")) {
                root = sysConfigService.getArchiveDirectory();
                fileLoation = 1;
            }
            logger.info("文件根路径地址" + root);
            if (root == null || ValidateUtil.isEmpty(downloadUrl)) return BaseResponse.fail(FileMsg.missSysConfig);
            String[] fileNames = req.getFileNames();
            String uName = sysUserService.getUserNameByUId(req.getuId());
            List<UploadResVo> data = new ArrayList<>();
            for (int i = 0; i < files.length; i++) {
                MultipartFile file = files[i];

                String fileName = file.getOriginalFilename();
                if (fileNames != null && fileNames.length > 0 && files.length == fileNames.length)
                    fileName = fileNames[i];
                if (ValidateUtil.isEmpty(regex)) {
                    if (!checkFileType(fileName)) return BaseResponse.fail(FileMsg.unSupportedFormat);
                } else {
                    if (!checkFileType(fileName, regex)) return BaseResponse.fail(FileMsg.unSupportedFormat);
                }

                String[] name = fileName.split("\\.");
                String fileSuffix = name[name.length - 1];
                String realAddres = upload(file, fileSuffix, req.getuId(), req.getCode(), root);
                //region 校验文件MD5 去重
                String path = root + "/" + realAddres;
                if (img2Pdf) {//走图片上传并转PDF
                    AsposeUtil.imgToPdf(path, path.replace("." + fileSuffix, ".pdf"));
                    path = path.replace("." + fileSuffix, ".pdf");
                }
                File saveFile = new File(path);
                String fileMd5 = MD5File.getFileMD5Str(saveFile);
                SysFileAnnex md5File = sysFileAnnexService.checkFileMd5(fileMd5, fileLoation);
                //if (md5File != null) {
                //    saveFile.delete();
                //    return BaseResponse.fail(String.format(FileMsg.fileAlreadyUp, fileName), md5File.getFileId());
                //}
                //endregion
                logger.info("文件路径地址" + realAddres);
                SysFileAnnex sysFileAnnexPojo = new SysFileAnnex();
                sysFileAnnexPojo.setFileMd5Code(fileMd5);
                sysFileAnnexPojo.setFileCreateUId(req.getuId());
                sysFileAnnexPojo.setFileCreateUName(uName);
                sysFileAnnexPojo.setFileUploadTime(new Date());
                sysFileAnnexPojo.setFileSuffix(fileSuffix);
                sysFileAnnexPojo.setFileSize(FileUtil.getRemoteFileSize(root + "/" + realAddres));
                sysFileAnnexPojo.setFileRelationId(req.getFileRelationId());
                sysFileAnnexPojo.setFilePath(realAddres);
                sysFileAnnexPojo.setFileName(fileName);
                sysFileAnnexPojo.setFileCode(req.getCode());
                sysFileAnnexPojo.setTableName(req.getTableName());
                sysFileAnnexPojo.setTableCode(req.getTableCode());
                sysFileAnnexPojo.setColumnName(req.getColumnName());
                sysFileAnnexPojo.setColumnCode(req.getColumnCode());
                sysFileAnnexPojo.setFileId(GenerateUUID.getUUID());
                sysFileAnnexPojo.setFileInputCode(req.getFileInputCode());
                sysFileAnnexPojo.setFileInputName(req.getFileInputName());
                sysFileAnnexPojo.setAdId(req.getAdId());
                sysFileAnnexPojo.setFileNotRemove(req.isFileNotRemove());
                if (ValidateUtil.isNotEmpty(req.getFileInputTime()))
                    sysFileAnnexPojo.setFileInputTime(DateUtil.String2Date(req.getFileInputTime(), "yyyy-MM-dd"));
                sysFileAnnexPojo.setFileInputVersion(req.getFileInputVersion());
                sysFileAnnexPojo.setArchive(fileLoation);
                sysFileAnnexPojo.setFileLocation(fileLoation);
                sysFileAnnexPojo.setFileReplace(0);
                sysFileAnnexPojo.setFileEntityId(req.getFileId());
                sysFileAnnexPojo.setTeId(teId);
                sysFileAnnexService.insert(sysFileAnnexPojo, status);
                UploadResVo upRes = new UploadResVo();
                String url = breakpointDownload(sysFileAnnexPojo.getFileId());
                upRes.setId(sysFileAnnexPojo.getFileId()).setUrl(downloadUrl + url).setNoHostUrl("/fileServer" + url).setFileName(fileName).setFileSuffix(fileSuffix).setFileSize(sysFileAnnexPojo.getFileSize()).setFileInputCode(sysFileAnnexPojo.getFileInputCode()).setFileInputName(sysFileAnnexPojo.getFileInputName()).setFileInputTime(sysFileAnnexPojo.getFileInputTime()).setFileInputVersion(sysFileAnnexPojo.getFileInputVersion()).setAdId(sysFileAnnexPojo.getAdId()).setAdLink(archiveFileService.getAdLinkByAdId(sysFileAnnexPojo.getAdId()));
                if (md5File != null) upRes.setDuplicateFile(true);
                data.add(upRes);
            }
            return BaseResponse.ok(FileMsg.uploadSuccess, data);
        } catch (Exception e) {
            logger.error("上传文件错误: {}", e.getMessage(), e);
            return BaseResponse.fail(FileMsg.fileUpError + " " + e.getMessage());
        }
    }

    @Override
    public BaseResponse openUpload(MultipartFile[] files, UploadDto req, String updateType, int status) {
        return uploadFile(files, req, updateType, status, null, sysConfigService.getSysConfByCode("openFileUploadXzRegex"), false);
    }

    @Override
    public BaseResponse chunkUpload(MultipartFile file, ChunkUploadDto req, String teId) {
        logger.info("断点续传上传文件！fileIdentifier: {}, chunkIndex: {}/{}",
                req.getFileIdentifier(), req.getChunkIndex(), req.getTotalChunks());
        try {
            String root = null;
            String downloadUrl = sysConfigService.getDownloadUrl();
            int fileLocation = 0;

            if ("root".equals(req.getUpdateType())) {
                root = sysConfigService.getRootDirectory();
            } else if ("archive".equals(req.getUpdateType())) {
                root = sysConfigService.getArchiveDirectory();
                fileLocation = 1;
            }

            logger.info("文件根路径地址: {}", root);
            if (root == null || ValidateUtil.isEmpty(downloadUrl)) {
                return BaseResponse.fail(FileMsg.missSysConfig);
            }

            String fileName = req.getFileName();
            if (!checkFileType(fileName)) {
                return BaseResponse.fail(FileMsg.unSupportedFormat);
            }

            String[] name = fileName.split("\\.");
            String fileSuffix = name[name.length - 1];
            String uName = sysUserService.getUserNameByUId(req.getUId());

            // 创建分片临时目录
            String chunkTempDir = root + "/JPBPMTemporaryFolder/chunks/" + req.getFileIdentifier();
            File chunkDir = new File(chunkTempDir);
            if (!chunkDir.exists()) {
                chunkDir.mkdirs();
            }

            // 保存当前分片
            String chunkPath = chunkTempDir + "/" + req.getChunkIndex() + ".part";
            File chunkFile = new File(chunkPath);
            file.transferTo(chunkFile);

            logger.info("分片 {} 上传成功，路径: {}", req.getChunkIndex(), chunkPath);

            // 检查是否所有分片都已上传
            File[] uploadedChunks = chunkDir.listFiles((dir, n) -> n.endsWith(".part"));
            int uploadedCount = uploadedChunks != null ? uploadedChunks.length : 0;

            // 如果还有分片未上传，返回中间状态
            if (uploadedCount < req.getTotalChunks()) {
                ChunkUploadResVo resVo = new ChunkUploadResVo();
                resVo.setUploadStatus("chunking")
                        .setUploadedChunks(uploadedCount)
                        .setTotalChunks(req.getTotalChunks())
                        .setFileName(fileName);
                return BaseResponse.ok("分片上传中", resVo);
            }

            // 所有分片已上传完成，开始合并
            logger.info("所有分片上传完成，开始合并文件");
            String mergedFilePath = mergeChunks(req, root, fileSuffix);

            // 计算合并后文件的MD5
            File mergedFile = new File(root + "/" + mergedFilePath);
            String fileMd5 = MD5File.getFileMD5Str(mergedFile);

            // 检查文件MD5去重
            SysFileAnnex md5File = sysFileAnnexService.checkFileMd5(fileMd5, fileLocation);

            // 创建文件记录
            SysFileAnnex sysFileAnnexPojo = new SysFileAnnex();
            sysFileAnnexPojo.setFileMd5Code(fileMd5);
            sysFileAnnexPojo.setFileCreateUId(req.getUId());
            sysFileAnnexPojo.setFileCreateUName(uName);
            sysFileAnnexPojo.setFileUploadTime(new Date());
            sysFileAnnexPojo.setFileSuffix(fileSuffix);
            sysFileAnnexPojo.setFileSize(mergedFile.length());
            sysFileAnnexPojo.setFileRelationId(req.getFileRelationId());
            sysFileAnnexPojo.setFilePath(mergedFilePath);
            sysFileAnnexPojo.setFileName(fileName);
            sysFileAnnexPojo.setFileCode(req.getCode());
            sysFileAnnexPojo.setTableName(req.getTableName());
            sysFileAnnexPojo.setTableCode(req.getTableCode());
            sysFileAnnexPojo.setColumnName(req.getColumnName());
            sysFileAnnexPojo.setColumnCode(req.getColumnCode());
            sysFileAnnexPojo.setFileId(GenerateUUID.getUUID());
            sysFileAnnexPojo.setFileInputCode(req.getFileInputCode());
            sysFileAnnexPojo.setFileInputName(req.getFileInputName());
            sysFileAnnexPojo.setAdId(req.getAdId());
            sysFileAnnexPojo.setFileNotRemove(req.isFileNotRemove());
            if (ValidateUtil.isNotEmpty(req.getFileInputTime())) {
                sysFileAnnexPojo.setFileInputTime(DateUtil.String2Date(req.getFileInputTime(), "yyyy-MM-dd"));
            }
            sysFileAnnexPojo.setFileInputVersion(req.getFileInputVersion());
            sysFileAnnexPojo.setArchive(fileLocation);
            sysFileAnnexPojo.setFileLocation(fileLocation);
            sysFileAnnexPojo.setFileReplace(0);
            sysFileAnnexPojo.setFileEntityId(req.getFileId());
            sysFileAnnexPojo.setTeId(teId);

            sysFileAnnexService.insert(sysFileAnnexPojo, req.getStatus());

            // 清理分片临时目录
            FileUtil.deleteFiles(chunkDir, true);

            // 构建返回结果
            ChunkUploadResVo upRes = new ChunkUploadResVo();
            String url = breakpointDownload(sysFileAnnexPojo.getFileId());
            upRes.setFileId(sysFileAnnexPojo.getFileId())
                    .setUrl(downloadUrl + url)
                    .setNoHostUrl("/fileServer" + url)
                    .setFileName(fileName)
                    .setFileSuffix(fileSuffix)
                    .setFileSize(sysFileAnnexPojo.getFileSize())
                    .setFileInputCode(sysFileAnnexPojo.getFileInputCode())
                    .setFileInputName(sysFileAnnexPojo.getFileInputName())
                    .setAdId(sysFileAnnexPojo.getAdId())
                    .setAdLink(archiveFileService.getAdLinkByAdId(sysFileAnnexPojo.getAdId()))
                    .setUploadStatus("merged")
                    .setUploadedChunks(req.getTotalChunks())
                    .setTotalChunks(req.getTotalChunks());

            if (md5File != null) {
                upRes.setDuplicateFile(true);
            }

            logger.info("文件合并完成，文件ID: {}", sysFileAnnexPojo.getFileId());
            return BaseResponse.ok(FileMsg.uploadSuccess, upRes);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("断点续传上传文件错误", e);
            return BaseResponse.fail(FileMsg.fileUpError);
        }
    }

    /**
     * 合并分片文件
     *
     * @param req        上传请求参数
     * @param root       根目录
     * @param fileSuffix 文件后缀
     * @return 合并后的文件相对路径
     */
    private String mergeChunks(ChunkUploadDto req, String root, String fileSuffix) throws IOException {
        String dateDir = new SimpleDateFormat("yyyy/MM").format(new Date());
        String filePath = dirPath + req.getCode() + "/" + req.getUId() + "/" + dateDir;
        String fileDirPath = root + "/" + filePath;
        File fileDir = new File(fileDirPath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        String fileName = GenerateUUID.getUUID();
        String mergedFilePath = fileDirPath + "/" + fileName + "." + fileSuffix;
        File mergedFile = new File(mergedFilePath);

        // 创建合并文件
        if (!mergedFile.exists()) {
            mergedFile.createNewFile();
        }

        // 按顺序合并分片
        try (FileOutputStream fos = new FileOutputStream(mergedFile)) {
            String chunkTempDir = root + "/JPBPMTemporaryFolder/chunks/" + req.getFileIdentifier();
            for (int i = 0; i < req.getTotalChunks(); i++) {
                String chunkPath = chunkTempDir + "/" + i + ".part";
                File chunkFile = new File(chunkPath);
                if (chunkFile.exists()) {
                    try (FileInputStream fis = new FileInputStream(chunkFile)) {
                        byte[] buffer = new byte[8192];
                        int bytesRead;
                        while ((bytesRead = fis.read(buffer)) != -1) {
                            fos.write(buffer, 0, bytesRead);
                        }
                    }
                } else {
                    throw new IOException("分片文件不存在: " + chunkPath);
                }
            }
            fos.flush();
        }

        logger.info("文件合并成功: {}", mergedFilePath);
        return filePath + "/" + fileName + "." + fileSuffix;
    }

    @Override
    public void uploadReplace(String oldId, LogUserInfoVo userLogin, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        String root = sysConfigService.getRootDirectory();
        if (root == null) throw new JPMException(FileMsg.missSysConfig);
        logger.info("文件根路径地址" + root);
        SysFileAnnex oldFile = sysFileAnnexService.getById(oldId);
        if (oldFile == null) throw new JPMException(FileMsg.noneReplaceFile);
        PrintWriter out = response.getWriter();
        Part part = request.getPart("docfile");
        String fileName = part.getSubmittedFileName() == null ? null : URLDecoder.decode(part.getSubmittedFileName(), "UTF-8");
        if (!checkFileType(fileName)) throw new JPMException(FileMsg.unSupportedFormat);
        try {
            oldFile.setFileReplace(1);
            SysFileAnnex newFile = new SysFileAnnex();
            BeanUtils.copyProperties(oldFile, newFile);
            oldFile.setFileId(GenerateUUID.getUUID());
            oldFile.setTeId(userLogin.getTeId());
            sysFileAnnexService.insert(oldFile, 0);
            //老文件移动至新ID
            newFile.setFileReplace(0);
            SysUser user = sysUserService.getById(userLogin.getId());
            String[] name = fileName.split("\\.");
            String fileSuffix = name[name.length - 1];
            String realAddres = upload(part, fileSuffix, userLogin.getId(), newFile.getFileCode(), root, response);
            OssClientParamsDto params = sysConfigService.getOssParams();
            if (params != null) {
                String key = CrcOssUtil.upload(params, root + realAddres, params.getOssRootDir() + realAddres.substring(0, realAddres.lastIndexOf("/") + 1));
                if (ValidateUtil.isNotEmpty(key)) newFile.setFileUpOss(Boolean.TRUE);
            } else newFile.setFileUpOss(Boolean.FALSE);
            newFile.setFileSuffix(fileSuffix);
            newFile.setFileCreateUId(userLogin.getId());
            if (user != null) newFile.setFileCreateUName(user.getuName());
            newFile.setFileSize(part.getSize());
            newFile.setFilePath(realAddres);
            newFile.setFileUploadTime(new Date());
            newFile.setTeId(userLogin.getTeId());
            //新文件继续持有老的ID
            sysFileAnnexService.updateById(newFile);
            //删除FileDlown相关记录
            sysFileDownloadService.deleteFileDownLoadByFileId(newFile.getFileId());
            out.print("||&");
            out.print("ok");
            out.print("&||");
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error(ex.getMessage());
            out.print("||&");
            out.print("error");
            out.print("&||");
        }
    }

    @Override
    public BaseResponse uploadRTEFile(MultipartFile[] files, UploadDto req, String teId) {
        logger.info("上传文件！");
        try {
            String root = sysConfigService.getRootDirectory();
            String downloadUrl = sysConfigService.getDownloadUrl();
            logger.info("文件根路径地址" + root);
            if (root == null || downloadUrl == null) throw new JPMException(FileMsg.missSysConfig);
            String uName = sysUserService.getUserNameByUId(req.getuId());
            List<Map<String, String>> data = new ArrayList<>();
            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                if (!checkFileType(fileName)) return BaseResponse.fail(FileMsg.unSupportedFormat);

                String[] name = fileName.split("\\.");
                String fileSuffix = name[name.length - 1];
                String realAddres = upload(file, fileSuffix, req.getuId(), req.getCode(), root);
                logger.info("文件路径地址" + realAddres);
                SysFileAnnex sysFileAnnexPojo = new SysFileAnnex();
                sysFileAnnexPojo.setFileCreateUId(req.getuId());
                sysFileAnnexPojo.setFileCreateUName(uName);
                sysFileAnnexPojo.setFileUploadTime(new Date());
                sysFileAnnexPojo.setFileSuffix(fileSuffix);
                sysFileAnnexPojo.setFileSize(FileUtil.getRemoteFileSize(root + "/" + realAddres));
                sysFileAnnexPojo.setFileRelationId(req.getFileRelationId());
                sysFileAnnexPojo.setFilePath(realAddres);
                sysFileAnnexPojo.setFileName(fileName);
                sysFileAnnexPojo.setFileCode(req.getCode());
                sysFileAnnexPojo.setTableName(req.getCode());
                sysFileAnnexPojo.setTableCode(req.getTableCode());
                sysFileAnnexPojo.setColumnName(req.getColumnName());
                sysFileAnnexPojo.setColumnCode(req.getColumnCode());
                sysFileAnnexPojo.setFileNotRemove(req.isFileNotRemove());
                sysFileAnnexPojo.setFileId(GenerateUUID.getUUID());
                sysFileAnnexPojo.setArchive(0);
                sysFileAnnexPojo.setFileLocation(0);
                sysFileAnnexPojo.setFileReplace(0);
                sysFileAnnexPojo.setTeId(teId);
                sysFileAnnexService.insert(sysFileAnnexPojo, 0);
                Map<String, String> map = new HashMap<>();
                map.put("id", sysFileAnnexPojo.getFileId());
                map.put("url", downloadUrl + "/jpm/downloadAloneFile/" + sysFileAnnexPojo.getFileId());
                map.put("noHostUrl", "/fileServer/jpm/downloadAloneFile/" + sysFileAnnexPojo.getFileId());
                map.put("fileName", fileName);
                map.put("fileSuffix", fileSuffix);
                map.put("fileSize", sysFileAnnexPojo.getFileSize() + "");
                data.add(map);
            }
            return new BaseResponse(true, "上传成功", data);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("上传文件错误");
            return BaseResponse.fail(FileMsg.fileUpError);
        }
    }


    /**
     * 普通文件上传保存
     *
     * @param uploadFile
     * @param fileSuffix
     * @param uId
     * @param code
     * @param root
     * @return
     * @throws Exception
     */
    private String upload(MultipartFile uploadFile, String fileSuffix, String uId, String code, String root) throws Exception {
        String dateDir = new SimpleDateFormat("yyyy/MM").format(new Date());
        String filePath = dirPath + code + "/" + uId + "/" + dateDir;
        String fileDirPath = root + "/" + filePath;
        File fileDir = new File(root, filePath);//创建文件目录 新
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        String fileName = GenerateUUID.getUUID();
        //实现文件上传   dirPath/功能编码/用户id/年月/
        String realUrlPath = fileDirPath + "/" + fileName + "." + fileSuffix;
        File realFilePath = new File(realUrlPath);
        uploadFile.transferTo(realFilePath);
        logger.info("文件上传 → ok");
        filePath = filePath + "/" + fileName + "." + fileSuffix;
        return filePath;
    }


    /**
     * 重载 -》 上传保存在线编辑文件
     *
     * @param uploadFile
     * @param fileSuffix
     * @param uId
     * @param code
     * @param root
     * @param response
     * @return
     * @throws Exception
     */
    private String upload(Part uploadFile, String fileSuffix, String uId, String code, String root, HttpServletResponse response) throws Exception {
        String dateDir = new SimpleDateFormat("yyyy/MM").format(new Date());
        String filePath = dirPath + code + "/" + uId + "/" + dateDir;
        String fileDirPath = root + "/" + filePath;
        File fileDir = new File(fileDirPath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        String fileName = GenerateUUID.getUUID();
        //实现文件上传   dirPath/功能编码/用户id/年月/
        String realUrlPath = fileDirPath + "/" + fileName + "." + fileSuffix;
        FileUtil.saveWebOfficeFile(realUrlPath, uploadFile, response);
        logger.debug("文件上传 → ok");
        filePath = filePath + "/" + fileName + "." + fileSuffix;
        return filePath;
    }

    private void getGanttTaskChild(List<GanttTasksDto> models, List<GanttTasksDto> datas) {
        for (GanttTasksDto model : models) {
            List<GanttTasksDto> child = datas.stream().filter(n -> n.getTaskParent().equals(model.getTaskId())).collect(Collectors.toList());
            getGanttTaskChild(child, datas);
            model.setChildNodes(child);
        }
    }


    /**
     * 校验文件名后缀 限制上传
     *
     * @param fileName 文件名
     * @return
     */
    @Override
    public Boolean checkFileType(String fileName) {
        return checkFileType(fileName, sysConfigService.getSysConfByCode("fileUploadXzRegex"));
    }

    @Override
    public Boolean checkFileType(String fileName, String regex) {
        if (ValidateUtil.isEmpty(fileName)) return false;
        if (!fileName.contains("."))//无后缀名文件
            return false;
        if (ValidateUtil.isEmpty(regex)) regex = ".*\\.(exe|php|py|java|jar|dll|bat|sh|com|sys|htm|html|jsp)$";
        if (fileName.toLowerCase(Locale.ENGLISH).matches(regex)) return false;
        return true;
    }

    @Override
    public void achievementPackageDownload(List<ArchievementsDTO> request, HttpServletResponse response) {
        List<String> fileIds = request.stream().map(ArchievementsDTO::getAnnexId).collect(Collectors.toList());
        List<SysFileAnnex> files = sysFileAnnexService.listByIds(fileIds);
        if (!CollectionUtils.isEmpty(files)) {
            String root = sysConfigService.getRootDirectory();
            String archive = sysConfigService.getArchiveDirectory();
            if (ValidateUtil.isAllEmpty(root, archive)) throw new JPMException(FileMsg.missSysConfig);
            String decPath = root + "/JPBPMTemporaryFolder/decompression/" + GenerateUUID.getUUID();
            File dec = new File(decPath);
            if (!dec.exists()) dec.mkdirs();
            String filePath = decPath + "/files";
            File fileDir = new File(filePath);
            if (!fileDir.exists()) fileDir.mkdirs();
            List<String> pNames = request.stream().map(ArchievementsDTO::getPName).distinct().collect(Collectors.toList());
            Map<String, String> idMap = new HashMap<>();
            //项目目录
            for (String pName : pNames) {
                String id = GenerateUUID.getUUID();
                File projDir = new File(filePath + "/" + id);
                projDir.mkdirs();
                idMap.put(pName, id);
            }
            //region 迁移文件到目录
            List<ArchievementsDTO> news = new ArrayList<>();
            for (ArchievementsDTO ar : request) {
                List<SysFileAnnex> aFiles = files.stream().filter(f -> f.getFileId().equals(ar.getAnnexId())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(aFiles)) {
                    SysFileAnnex fileAnnex = aFiles.stream().findFirst().get();
                    ar.setProDirId(idMap.get(ar.getPName()));
                    String proDir = filePath + "/" + ar.getProDirId();
                    File cpdDir = new File(proDir + "/" + ar.getCpdName());
                    if (!cpdDir.exists() || !cpdDir.isDirectory()) cpdDir.mkdirs();
                    File f = new File(cpdDir.getPath() + "/" + ar.getFileName());
                    String to = f.getPath();
                    String ml = ar.getFileName();
                    if (f.exists() && f.isFile()) {
                        ml = GenerateUUID.getUUID();
                        File dir = new File(cpdDir.getPath() + "/" + ml);
                        dir.mkdirs();
                        ml = ml + "/" + ar.getFileName();
                        to = dir.getPath() + "/" + ar.getFileName();
                    }
                    String from = fileAnnex.getFileLocation() == 0 ? root + "/" + fileAnnex.getFilePath() : archive + "/" + fileAnnex.getFilePath();
                    File source = new File(from);
                    if (source.exists() && source.isFile()) {
                        FileUtil.copy(from, to);
                        ar.setPath("files/" + ar.getProDirId() + "/" + ar.getCpdName() + "/" + ml);
                        news.add(ar);
                    }
                }

            }
            //endregion
            String excel = decPath + "/文件目录说明.xlsx";
            try {
                ExcelUtil.exportArchivementsExcel(news, excel);
                String zipPath = root + "/JPBPMTemporaryFolder/decompression/" + GenerateUUID.getUUID() + ".zip";
                FileUtil.zip(decPath, zipPath);
                FileUtil.downFileAndDelFile(response, zipPath, "成果文件" + DateUtil.Date2String(new Date(), "yyyyMMddHHmm") + ".zip");
            } catch (Exception ex) {
                ex.printStackTrace();
                log.error(ex.getMessage(), ex);
                throw new JPMException(FileMsg.expError);
            } finally {
                FileUtil.deleteFiles(dec, true);
            }
        } else throw new JPMException(FileMsg.noneFile);
    }

    @Override
    public BaseResponse uploadBudgetDeclareExcelBl(HttpServletRequest request, MultipartFile file) {
        File tfFile = temporaryStorageFile(file);
        String url = tfFile.getPath();
        try {
            List<InvBudgetDeclareDtlModel> datas = ExcelUtil.uploadBudgetDeclareExcelBl(url);
            if (datas == null) return new BaseResponse(FileMsg.fileParsingFail);
            tfFile.delete();
            return BaseResponse.ok(datas);
        } catch (Exception ex) {
            if (tfFile.exists() && tfFile.isFile()) tfFile.delete();
            ex.printStackTrace();
            return new BaseResponse(FileMsg.fileParsingFail);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse importProjectBusinessMonthlyProgress(HttpServletRequest request, MultipartFile file) {
        List<TInformationFilling> results = new ArrayList<>();
        List<String> errorMessages = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream()) {
            // ===== 读取 Excel 数据，表头 3 行，从第 4 行开始是数据 =====
            List<TInformationFilling> datas = EasyExcel.read(inputStream)
                    .head(TInformationFilling.class)
                    .sheet(0)
                    .headRowNumber(4) // 前三行表头，从第4行开始是数据
                    .doReadSync();

            if (datas == null || datas.isEmpty()) {
                return BaseResponse.fail(FileMsg.excelNotData);
            }

            int rowNum = 3; // 表头行数
            for (TInformationFilling data : datas) {
                rowNum++;

                String projectName = data.getName();
                String year = data.getReportingYear();
                String month = data.getReportingMonth();

                // ===== 项目名为空，表示后面没有数据，停止处理 =====
                if (ValidateUtil.isEmpty(projectName)) {
                    break;
                }

                // ===== 校验项目是否存在 =====
                List<PreProject> projects = preProjectService.list(
                        new LambdaQueryWrapper<PreProject>().eq(PreProject::getpName, projectName)
                );

                if (projects == null || projects.isEmpty()) {
                    errorMessages.add("第 " + rowNum + " 行：项目名称【" + projectName + "】不存在");
                    continue;
                } else if (projects.size() > 1) {
                    errorMessages.add("第 " + rowNum + " 行：项目名称【" + projectName + "】重复，请检查数据");
                    continue;
                }

                // 取唯一项目
                PreProject project = projects.get(0);


                // ===== 校验是否已存在该年月数据 =====
                boolean exists = tInformationFillingService.count(
                        new LambdaQueryWrapper<TInformationFilling>()
                                .eq(TInformationFilling::getPId, project.getpId())
                                .eq(TInformationFilling::getReportingYear, year)
                                .eq(TInformationFilling::getReportingMonth, month)
                ) > 0;
                if (exists) {
                    errorMessages.add("第 " + rowNum + " 行：项目【" + projectName + "】" + year + "年" + month + "月 数据已存在");
                    continue;
                }

                // ===== 封装附加属性 =====
                String uid = GenerateUUID.getUUID();
                data.setId(uid);
                data.setUuid(uid);
                data.setPId(project.getpId());
                data.setCreateTime(new Date());
                data.setState(ApprovalStatusEnum.Already_Over.getIndex() + "");
                data.setStateTime(new Date());
                data.setCreateUname("admin");

                results.add(data);
            }

            // ===== 校验失败 → 回滚事务 =====
            if (!errorMessages.isEmpty()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return BaseResponse.fail("导入失败", errorMessages);
            }

            // ===== 保存业务表数据 =====
            if (!results.isEmpty()) {
                tInformationFillingService.saveBatch(results);

                // ===== 同步到 sys_file 表 =====
                String tCode = "MonthlyProgress";
                String templateId = getTemplateIdByCode(tCode);

                List<String> ids = results.stream().map(TInformationFilling::getId).collect(Collectors.toList());
                List<String> pIds = results.stream().map(TInformationFilling::getPId).collect(Collectors.toList());
                List<String> createUnames = results.stream().map(TInformationFilling::getCreateUname).collect(Collectors.toList());
                List<String> tIds = Collections.nCopies(results.size(), templateId);

                sysFileService.batchSaveFile(
                        ids, tIds, "7",
                        null, createUnames, null,
                        pIds, null, null,
                        "create", null, null,
                        tCode, null
                );
            }

            return BaseResponse.ok("成功导入 " + results.size() + " 条数据");

        } catch (IOException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return BaseResponse.fail("文件读取失败: " + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<byte[]> getFileWithoutFileAnnex(UploadDto request, HttpServletResponse response) {
        try {
            String root = sysConfigService.getRootDirectory();
            List<FileAnnexInfoWithFileVo> archives = sysFileAnnexMapper.getFileWithoutFileAnnex();
            List<FileAnnexInfoWithFileVo> archivesWithoutFileAnnex = new ArrayList<>();
            if (CollectionUtil.isNotEmpty(archives)) {
                ExecutorService executor = Executors.newFixedThreadPool(
                        Math.min(archives.size(), Runtime.getRuntime().availableProcessors())
                );
                try {
                    List<CompletableFuture<Void>> futures = new ArrayList<>();

                    for (FileAnnexInfoWithFileVo archive : archives) {
                        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                            if (archive != null && archive.getFilePath() != null) {
                                try {
                                    String filePath = archive.getFilePath();
                                    // 路径安全检查
                                    File file = new File(root, filePath);
                                    if (!file.exists() && StrUtil.isNotEmpty(archive.getCode())) {
                                        synchronized (archivesWithoutFileAnnex) {
                                            archivesWithoutFileAnnex.add(archive);
                                        }
                                    }
                                } catch (SecurityException e) {
                                    logger.warn("Security exception when checking file: " + archive.getFilePath(), e);
                                }
                            }
                        }, executor);

                        futures.add(future);
                    }

                    // 等待所有任务完成
                    CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
                } finally {
                    executor.shutdown();
                }
            }
            log.info("开始处理无附件文件：{}", archivesWithoutFileAnnex.size());
            //log.info("无附件文件：{}", JacksonUtils.toJSONString(archivesWithoutFileAnnex));
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            WriteCellStyle headStyle = new WriteCellStyle();
//            headStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
            WriteFont headFont = new WriteFont();
            headFont.setFontHeightInPoints((short) 12);
            headFont.setFontName("Arial");
            headStyle.setWriteFont(headFont);
            WriteCellStyle contentStyle = new WriteCellStyle();
//            contentStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
            WriteFont contentFont = new WriteFont();
            contentFont.setFontHeightInPoints((short) 10);
            contentFont.setFontName("Arial");
            contentStyle.setWriteFont(contentFont);
            HorizontalCellStyleStrategy styleStrategy = new HorizontalCellStyleStrategy(headStyle, contentStyle);
            EasyExcel.write(out, FileAnnexInfoWithFileVo.class)
                    .registerWriteHandler(styleStrategy)
                    .registerWriteHandler(getHorizontalCellStyleStrategy((short) 12))
                    .sheet("fileAnnexInfoWithFileVo")
                    .doWrite(archivesWithoutFileAnnex);
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=xmbs.xlsx");
            response.getOutputStream().write(out.toByteArray());
            response.getOutputStream().flush();
            response.getOutputStream().close();
            return new ResponseEntity<>(out.toByteArray(), org.springframework.http.HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //return BaseResponse.ok(archivesWithoutFileAnnex);
    }

    @Override
    public BaseResponse uploadFileById(MultipartFile files, UploadDtoWithNoBlank req) {
        log.info("开始上传文件：{}", req.getFileId());
        if (files == null) {
            return BaseResponse.fail("请选择文件");
        }
        if (StrUtil.isEmpty(req.getFileId())) {
            return BaseResponse.fail("文件id为空");
        }
        SysFileAnnex fileAnnex = sysFileAnnexMapper.selectById(req.getFileId());
        if (fileAnnex != null) {
            String root = sysConfigService.getRootDirectory();
            String realFilePath = root + fileAnnex.getFilePath();
            log.info("开始上传文件：{}", realFilePath);
            try {
                String realFilePathDir = realFilePath.substring(0, realFilePath.lastIndexOf("/"));
                String fileSuffix = realFilePath.substring(realFilePath.lastIndexOf(".") + 1);
                log.info("文件后缀名:{}  源文件后缀名:{}", fileSuffix, fileAnnex.getFileSuffix());
                if (!fileSuffix.equals(fileAnnex.getFileSuffix())) {
                    return BaseResponse.fail("文件后缀名错误，请检查");
                }
                File realFileDir = new File(realFilePathDir);
                if (!realFileDir.exists()) {
                    realFileDir.mkdirs();
                }
                File realFile = new File(realFilePath);
                if (!realFile.exists()) {
                    realFile.createNewFile();
                }
                files.transferTo(realFile);
            } catch (IOException e) {
                log.error("文件上传失败: {}", e.getMessage());
                return BaseResponse.fail("文件上传失败: " + e.getMessage());
            }
            log.info("文件上传 → ok");
        }
        return BaseResponse.ok();
    }

    @Override
    public ResponseEntity<byte[]> getFiles(UploadDto request, HttpServletResponse response) throws IOException {
        String root = sysConfigService.getRootDirectory();
        List<FileAnnexInfoWithFileVo> archives = sysFileAnnexMapper.getFileWithoutFileAnnex();
        log.info("开始处理无附件文件：{}", archives.size());
        List<FileAnnexInfoWithFileVo> archivesWithoutFileAnnex = new ArrayList<>();

        if (CollectionUtil.isNotEmpty(archives)) {
            ExecutorService executor = Executors.newFixedThreadPool(Math.min(archives.size(), Runtime.getRuntime().availableProcessors()));
            try {
                List<CompletableFuture<Void>> futures = new ArrayList<>();
                for (FileAnnexInfoWithFileVo archive : archives) {
                    CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                        if (archive != null && archive.getFilePath() != null) {
                            try {
                                String filePath = archive.getFilePath();
                                // 路径安全检查
                                File file = new File(root, filePath);
                                if (!file.exists()) {
                                    synchronized (archivesWithoutFileAnnex) {
                                        archivesWithoutFileAnnex.add(archive);
                                    }
                                }
                            } catch (SecurityException e) {
                                logger.warn("Security exception when checking file: {}", archive.getFilePath(), e);
                            }
                        }
                    }, executor);
                    futures.add(future);
                }
                // 等待所有任务完成
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
            } finally {
                executor.shutdown();
            }
        }
        List<File> fileList = new ArrayList<>();
        File folder = new File("D:\\upload\\file");
        scanFolder(fileList, folder);
        List<String> fileNameList = fileList.stream().map(File::getName).distinct().collect(Collectors.toList());
        List<FileAnnexInfoWithFileVo> archivesWithoutFileAnnex2 = archivesWithoutFileAnnex.stream()
                .filter(archive -> fileNameList.contains(archive.getFileName())).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(archivesWithoutFileAnnex2)) {
            log.info("开始导出文件：{}", JacksonUtils.toJSONString(archivesWithoutFileAnnex2));
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
            EasyExcel.write(out, FileAnnexInfoWithFileVo.class)
                    .registerWriteHandler(styleStrategy)
                    .registerWriteHandler(getHorizontalCellStyleStrategy((short) 12))
                    .sheet("fileAnnexInfoWithFileVo")
                    .doWrite(archivesWithoutFileAnnex2);
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=archivesWithoutFileAnnex2.xlsx");
            response.getOutputStream().write(out.toByteArray());
            response.getOutputStream().flush();
            response.getOutputStream().close();
            return new ResponseEntity<>(out.toByteArray(), org.springframework.http.HttpStatus.OK);
        } else {
            log.info("没有需要处理的文件");
            return new ResponseEntity<>(org.springframework.http.HttpStatus.OK);
        }
    }


    @Override
    public BaseResponse importChangeMeasureApproval(MultipartFile file) {

        try (InputStream inputStream = file.getInputStream()) {
            List<ConEcFeeRecordExcelModel> list = EasyExcel.read(inputStream)
                    .head(ConEcFeeRecordExcelModel.class)
                    .headRowNumber(2)
                    .sheet()
                    .doReadSync();

            List<ConEcFeeRecord> records = new ArrayList<>();

            for (ConEcFeeRecordExcelModel model : list) {
                ConEcFeeRecord record = new ConEcFeeRecord();

                record.setMearOrder(model.getMearOrder());
                record.setMearBoqdName(model.getMearBoqdName());
                record.setMearBoqdUnit(model.getMearBoqdUnit());

                // BigDecimal 无需转换，直接赋值即可
                record.setMearDeclareAmount(model.getMearDeclareAmount() == null ? null : model.getMearDeclareAmount().floatValue());
                record.setMearUpperAmount(model.getMearUpperAmount() == null ? null : model.getMearUpperAmount().floatValue());
                record.setMearConPrice(model.getMearConPrice() == null ? null : model.getMearConPrice().floatValue());
                record.setMearConTotal(model.getMearConTotal());

                if (model.getMearDeclareAmount() != null
                        && model.getMearConPrice() != null) {
                    BigDecimal total = model.getMearDeclareAmount()
                            .multiply(model.getMearConPrice())
                            .setScale(2, RoundingMode.HALF_UP);
                    record.setMearConTotal(total);
                }

                records.add(record);
            }

            return BaseResponse.ok(records, records.size());
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.fail("Excel 导入失败：" + e.getMessage());
        }
    }


    public static void scanFolder(List<File> fileList, File folder) {

        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isDirectory()) {
                    scanFolder(fileList, file); // 如果是目录，则递归调用
                } else {
                    fileList.add(file);
                    log.info("file found: {}", file.getName());
                }
            }
        } else {
            //log.info("folder does not exist or is not a directory");
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

    /**
     * 根据模板编码获取模板ID
     */
    private String getTemplateIdByCode(String tCode) {
        SysFileTemplate template = sysFileTemplateService.getOne(
                new LambdaQueryWrapper<SysFileTemplate>()
                        .eq(SysFileTemplate::gettCode, tCode)
                        .last("LIMIT 1"));
        return template != null ? template.gettId() : null;
    }


    /**
     * 保存文件到临时文件夹 返回文件路径
     * 主要使用场景：上传解析excel
     *
     * @param root 文件根路径
     * @param file 文件
     * @return 文件临时保存位置
     * @throws Exception
     */
    private String saveExcelFile(String root, MultipartFile file) throws Exception {
        String url = null;
        if (root == null) throw new JPMException(FileMsg.missSysConfig);
        String fileName = file.getOriginalFilename();
        if (ValidateUtil.isEmpty(fileName)) throw new JPMException(FileMsg.unSupportedFormat);
        if (!fileName.matches(".*\\.(xls|xlsx)$")) throw new JPMException(FileMsg.onlyExcel);
        String[] name = fileName.split("\\.");
        String fileSuffix = name[name.length - 1];
        //临时存储
        String realAddres = upload(file, fileSuffix, GenerateUUID.getUUID(), "JPBPMTemporaryFolder", root);
        url = root + "/" + realAddres;
        File tfFile = new File(url);
        if (!tfFile.exists() || !tfFile.isFile()) throw new JPMException(FileMsg.saveFail);
        return url;
    }


    /**
     * 临时存储excel
     *
     * @param file
     * @return
     */
    private File temporaryStorageFile(MultipartFile file) {
        return temporaryStorageFile(file, ".*\\.(xls|xlsx|xml|mpp)$", FileMsg.onlyExcelOrMpp);
    }

    private File temporaryStorageFile(MultipartFile file, String regex, String msg) {
        String root = sysConfigService.getRootDirectory();
        return temporaryStorageFile(root, file, regex, msg);
    }

    private File temporaryStorageFile(String root, MultipartFile file, String regex, String msg) {
        return temporaryStorageFile(root, file, regex, msg, null);
    }

    @Override
    public File temporaryStorageFile(String root, MultipartFile file, String regex, String msg, String code) {
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

    @Override
    public void exportContractFiles(HttpServletResponse response, ConContractRequest request, String token) {
        try {
            String root = sysConfigService.getRootDirectory();
            String archive = sysConfigService.getArchiveDirectory();
            OssClientParamsDto ossParams = sysConfigService.getOssParams();

            // 获取合同信息
            request.setConApprovalState(ApprovalStatusEnum.Already_Over.getIndex());
            List<ConContractResponse> contracts = pmFeign.exportContractFiles(request);
            if (ValidateUtil.isEmpty(contracts)) {
                log.error("获取合同信息失败或不存在符合条件的合同");
                return;
            }
            // 构建所有文件关联id
            Set<String> allRelationIds = new HashSet<>();
            contracts.forEach(contract -> {
                if (ValidateUtil.isNotEmpty(contract.getConBasisFile())) {
                    allRelationIds.add(contract.getConBasisFile());
                }
                if (ValidateUtil.isNotEmpty(contract.getConScan())) {
                    allRelationIds.add(contract.getConScan());
                }
                if (ValidateUtil.isNotEmpty(contract.getConWord())) {
                    allRelationIds.add(contract.getConWord());
                }
                if (ValidateUtil.isNotEmpty(contract.getFileRelationId())) {
                    allRelationIds.add(contract.getFileRelationId());
                }
            });

            // 一次性查询所有文件
            List<SysFileAnnex> allFiles = sysFileAnnexService.getFileByRelationIds(new ArrayList<>(allRelationIds));
            if (CollectionUtil.isNotEmpty(allFiles)) {
                for (SysFileAnnex annex : allFiles) {
                    try {
                        String targetRoot = (annex.getFileLocation() != null && annex.getFileLocation() == 1) ? archive : root;
                        CrcOssUtil.downLocalNotExistFile(ossParams, annex.getFilePath(), targetRoot, ValidateUtil.isNull(annex.getFileLocation()) ? 0 : annex.getFileLocation());
                    } catch (Exception e) {
                        log.warn("导出合同文件-同步OSS文件到本地失败: filename={}, path={}", annex.getFileName(), annex.getFilePath());
                    }
                }
            }

            Map<String, List<SysFileAnnex>> relationFileMap = allFiles.stream()
                    .collect(Collectors.groupingBy(SysFileAnnex::getFileRelationId));

            // 构建路径 合同名 → 附件
            Map<String, SysFileAnnex> pathFileMap = new HashMap<>();
            contracts.forEach(contract -> {
                String path = contract.getConName();
                List<SysFileAnnex> currentAllFiles = new ArrayList<>();
                List<SysFileAnnex> files1 = relationFileMap.getOrDefault(contract.getFileRelationId(), new ArrayList<>());
                List<SysFileAnnex> files2 = relationFileMap.getOrDefault(contract.getConBasisFile(), new ArrayList<>());
                List<SysFileAnnex> files3 = relationFileMap.getOrDefault(contract.getConScan(), new ArrayList<>());
                List<SysFileAnnex> files4 = relationFileMap.getOrDefault(contract.getConWord(), new ArrayList<>());
                currentAllFiles.addAll(files1);
                currentAllFiles.addAll(files2);
                currentAllFiles.addAll(files3);
                currentAllFiles.addAll(files4);
                for (SysFileAnnex file : currentAllFiles) {
                    String finalPath = path + "/" + file.getFileName();
                    int index = 1;
                    while (pathFileMap.containsKey(finalPath)) {
                        finalPath = path + "/" + appendFileNameIndex(file.getFileName(), index);
                        index++;
                    }
                    pathFileMap.put(finalPath, file);
                }
            });

            if (pathFileMap.isEmpty()) {
                return;
            }

            FileUtil.downloadZipWithPathMapping(response, "合同文件.zip", pathFileMap, root, archive);
            log.info(String.valueOf(response.getStatus()));
        } catch (IOException e) {
            log.error("合同文件下载失败IO异常: {}", e.getMessage(), e);
        } catch (Exception e) {
            log.error("合同文件下载失败其他异常: {}", e.getMessage(), e);
        }
    }

    private String appendFileNameIndex(String fileName, int index) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex == -1) {
            return fileName + "(" + index + ")";
        } else {
            String namePart = fileName.substring(0, dotIndex);
            String extPart = fileName.substring(dotIndex);
            return namePart + "(" + index + ")" + extPart;
        }
    }

    @Override
    public void getOaNoticeReport(HttpServletResponse response, String id, String type, String token) {
        String exportType = ValidateUtil.isEmpty(type) ? "pdf" : type;

        // 获取数据
        OaNoticeResponse entity = getOaNoticeOrThrow(id);
        // 获取模板文件
        File templateFile = getOaNoticeTemplateFile();

        // 处理数据，转换字典等
        prepareOaNoticeReportData(entity);

        // 构建文件
        String nContentHtml = buildOaNoticeContentHtml(entity.getnContent(), token);
        Map<String, Object> dataSource = buildOaNoticeDataSource(entity);
        HashMap[] approvalRecordArray = buildOaNoticeApprovalRecords(entity.getnId(), token);
        File wordFile = createTempFileOrThrow("oa_notice_", DOCX, "文件创建失败！");

        // 导出文件
        AsposeUtil.tempExportWord(
                templateFile.getPath(),
                wordFile.getPath(),
                new Object[]{dataSource, approvalRecordArray},
                new String[]{"s", "approvalRecords"},
                null,
                null,
                null
        );
        renderOaNoticeContentHtml(wordFile.getPath(), OA_NOTICE_CONTENT_PLACEHOLDER, nContentHtml);

        downloadOaNoticeReport(response, wordFile, exportType);
    }

    @Override
    public BaseResponse jimuReportGenerate(String fileId) {
        JimuExportAutoArchiveDTO conf = sysConfigService.getJimuExportArchive();
        if (conf == null)
            return BaseResponse.fail("缺少积木报表导出PDF请求地址配置！");
        if (ValidateUtil.isEmpty(conf.getExportPdfUrl()))
            return BaseResponse.fail("缺少积木报表导出PDF请求地址配置！");
        String root = sysConfigService.getRootDirectory();
        root = root.endsWith("/") ? new StringBuilder(root).deleteCharAt(root.length() - 1).toString() : root;
        SysFile file = sysFileService.getById(fileId);
        if (file != null) {
            List<SysFileTemplatePrint> prints = sysFileTemplatePrintService.getJiMuPrint(Arrays.asList(file.gettId()));
            if (!CollectionUtils.isEmpty(prints)) {
                //文件信息
                List<SysFileAnnex> fileAnnexes = new ArrayList<>();
                OssClientParamsDto ossConf = sysConfigService.getOssParams();
                Date now = new Date();
                List<SysFileTemplatePrint> pts = prints.stream().filter(n -> n.getTId().equals(file.gettId())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(pts)) {
                    //虚拟访问令牌
                    Object rd = redisUtils.get(file.getFilecreateuser());
                    log.info("虚拟令牌,{}", rd);
                    String token = ValidateUtil.isNull(rd) ? GenerateUUID.getToekn() : rd.toString();
                    if (ValidateUtil.isNull(rd) || !token.equals(rd.toString()) || ValidateUtil.isEmpty(redisUtils.get(token))) {
                        redisUtils.set(file.getFilecreateuser(), token);
                        redisUtils.set(token, file.getFilecreateuser());
                        getLogInfo(sysUserService.getById(file.getFilecreateuser()), token);
                        log.info("需要生成虚拟令牌,{}", token);
                    }
                    String relationId = ValidateUtil.isNotEmpty(file.getFileRelationId()) ? file.getFileRelationId() : GenerateUUID.getUUID();
                    for (SysFileTemplatePrint pt : pts) {
                        JMExportDTO param = new JMExportDTO();
                        param.setExcelConfigId(getExcelConfigId(pt.getTpApiUrl()));
                        JMExportDTO.QueryParam qm = new JMExportDTO.QueryParam();
                        qm.setToken(token);
                        qm.setId(file.getFileid());
                        param.setQueryParam(qm);
                        //region 生成文件信息fileAnnex,同时生成实体文件
                        SysFileAnnex annex = new SysFileAnnex();
                        annex.setFileId(GenerateUUID.getUUID());
                        annex.setFileRelationId(relationId);
                        annex.setTeId(file.getTeId());
                        annex.setUploadStatus(1);
                        annex.setFileEntityId(file.getFileid());
                        annex.setFileCreateUId(file.getFilecreateuser());
                        annex.setFileCreateUName(file.getFilecreateusername());
                        annex.setFileName(pt.getTpName() + ".pdf");
                        annex.setFilePath("/jimuReport/" + file.gettCode() + "/" + GenerateUUID.getUUID() + ".pdf");
                        annex.setFileSuffix("pdf");
                        annex.setFileLocation(0);
                        annex.setFileReplace(0);
                        annex.setArchive(0);
                        annex.setFileSourceType("01");
                        annex.setFileUpOss(Boolean.FALSE);
                        annex.setFileJimuReport(Boolean.TRUE);
                        annex.setFileUploadTime(now);
                        String path = root + annex.getFilePath();
                        //通过jimu报表生成PDF保存到指定位置
                        path = FileUtil.httpPost2File(conf.getExportPdfUrl(), JacksonUtils.toJSONString(param), path, token);
                        if (ValidateUtil.isEmpty(path))
                            continue;
                        File f = new File(path);
                        if (f.exists() && f.isFile()) {
                            annex.setFileSize(f.length());
                            annex.setFileMd5Code(MD5File.getFileMD5Str(f));
                            fileAnnexes.add(annex);
                            file.setFileJmArchive(Boolean.TRUE);
                        }
                    }
                }
                if (!CollectionUtils.isEmpty(fileAnnexes)) {
                    String finalRoot = root;
                    fileAnnexes.forEach(f -> {
                        String ossDir = f.getFilePath().substring(0, f.getFilePath().lastIndexOf("/") + 1);
                        //文件赋值后上传到OSS归档目录
                        if (ossConf != null && ossConf.isEnableOss()) {
                            String key = CrcOssUtil.upload(ossConf, finalRoot + f.getFilePath(), ossConf.getOssArchiveDir() + ossDir);
                            if (ValidateUtil.isNotEmpty(key)) f.setFileUpOss(Boolean.TRUE);
                        }
                    });
                    sysFileAnnexService.saveBatch(fileAnnexes);
                }
            }
        }
        return BaseResponse.ok();
    }

    /**
     * 获取发文管理数据
     *
     * @param id
     * @return
     */
    private OaNoticeResponse getOaNoticeOrThrow(String id) {
        if (ValidateUtil.isEmpty(id)) {
            throw new JPMException("缺少请求参数！");
        }

        BaseResponse oaNoticeByNid = oaFeign.getOaNoticeByNid(id);
        if (!oaNoticeByNid.getSuccess() || ValidateUtil.isNull(oaNoticeByNid.getData())) {
            throw new JPMException("数据不存在！");
        }

        OaNoticeResponse entity = JacksonUtils.parseObject(
                JacksonUtils.toJSONString(oaNoticeByNid.getData()),
                OaNoticeResponse.class
        );

        if (ValidateUtil.isNull(entity)) {
            throw new JPMException("数据不存在！");
        }

        return entity;
    }

    /**
     * 获取发文管理模板文件
     *
     * @return
     */
    private File getOaNoticeTemplateFile() {
        String oaNoticeReport = sysConfigService.getSysConfByCode("oaNoticeReport");

        SysFileAnnex file = sysFileAnnexService.getOne(Wrappers.<SysFileAnnex>lambdaQuery()
                .eq(SysFileAnnex::getFileRelationId, oaNoticeReport)
                .last(" limit 1 ")
        );

        if (file == null) {
            throw new JPMException("模板文件不存在！");
        }

        String root = sysConfigService.getRootDirectory();
        String archive = sysConfigService.getArchiveDirectory();
        String basePath = file.getArchive() == 1 ? archive : root;

        CrcOssUtil.downLocalNotExistFile(
                sysConfigService.getOssParams(),
                file.getFilePath(),
                basePath,
                file.getArchive()
        );

        File sourceFile = new File(basePath + "/" + file.getFilePath());
        if (!sourceFile.exists()) {
            throw new JPMException("源文件不存在！");
        }

        return sourceFile;
    }

    /**
     * 准备发文管理报告数据
     *
     * @param entity
     */
    private void prepareOaNoticeReportData(OaNoticeResponse entity) {
        JSONArray jpMsoftBdlxNew = DictionaryUtils.dictList("JPMsoft_GGLX");
        entity.setnType(DictionaryUtils.processDictList(jpMsoftBdlxNew, entity.getnType()));
        entity.setcName(DateUtil.Date2String(entity.getnCreateTime(), "yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 构建发文管理报告内容HTML
     *
     * @param nContent 内容
     * @param token    令牌
     * @return HTML内容
     */
    private String buildOaNoticeContentHtml(String nContent, String token) {
        String html = replaceOaNoticeContentText(nContent, token);
        if (ValidateUtil.isEmpty(html)) {
            return "";
        }

        String downloadUrl = sysConfigService.getSysConfByCode("downloadUrl");
        if (ValidateUtil.isNotEmpty(downloadUrl) && ValidateUtil.isNotEmpty(token)) {
            html = AsposeUtil.convertImgToBase64(html, downloadUrl, token);
        } else {
            html = AsposeUtil.cleanImgHtml(html);
        }

        return setOaNoticeContentImageStyle(html);
    }

    /**
     * 构建发文管理报告数据源
     *
     * @param entity 发文管理实体
     * @return 数据源
     */
    private Map<String, Object> buildOaNoticeDataSource(OaNoticeResponse entity) {
        Map<String, Object> dataSource = JacksonUtils.parseObject(
                JacksonUtils.toJSONString(entity),
                new TypeReference<Map<String, Object>>() {
                }
        );

        if (ValidateUtil.isEmpty(dataSource)) {
            throw new JPMException("数据转换失败！");
        }

        dataSource.put("nContent", OA_NOTICE_CONTENT_PLACEHOLDER);
        return dataSource;
    }

    /**
     * 构建发文管理审批记录数据源
     *
     * @param noticeId 发文管理ID
     * @param token    令牌
     * @return 审批记录数据源
     */
    private HashMap[] buildOaNoticeApprovalRecords(String noticeId, String token) {
        FilerecordRequest request = new FilerecordRequest();
        request.setEntityid(noticeId);

        BaseResponse baseResponse = bpmFeign.getApproveList(request, token, null);
        if (!baseResponse.getSuccess()) {
            throw new JPMException("审批记录获取失败！");
        }

        List<Map<String, Object>> approvalRecords = parseOaNoticeApprovalRecords(baseResponse.getData());
        List<HashMap<String, Object>> approvalRecordMaps = new ArrayList<>();

        for (int i = 0; i < approvalRecords.size(); i++) {
            HashMap<String, Object> item = new HashMap<>(approvalRecords.get(i));
            item.put("index", i + 1);
            item.put("tpDotime", formatOaNoticeApprovalTime(item.get("tpDotime")));
            approvalRecordMaps.add(item);
        }

        return approvalRecordMaps.toArray(new HashMap[0]);
    }

    /**
     * 解析发文管理审批记录
     *
     * @param data 数据
     * @return 审批记录数据源
     */
    private List<Map<String, Object>> parseOaNoticeApprovalRecords(Object data) {
        if (ValidateUtil.isNull(data)) {
            return Collections.emptyList();
        }

        List<Map<String, Object>> approvalRecords = JacksonUtils.parseObject(
                JacksonUtils.toJSONString(data),
                new TypeReference<List<Map<String, Object>>>() {
                }
        );

        return approvalRecords == null ? Collections.emptyList() : approvalRecords;
    }

    /**
     * 下载发文管理报告
     *
     * @param response 响应
     * @param wordFile Word文件
     * @param type     文件类型
     */
    private void downloadOaNoticeReport(HttpServletResponse response, File wordFile, String type) {
        if ("pdf".equalsIgnoreCase(type)) {
            File pdfFile = createTempFileOrThrow("oa_notice_pdf", ".pdf", "PDF临时文件创建失败！");

            String fontsDirectory = sysConfigService.getSysConfByCode("fileFontsDirectory");
            AsposeUtil.wordToPdf(wordFile.getPath(), pdfFile.getPath(), fontsDirectory);

            wordFile.delete();
            FileUtil.downFileAndDelFile(response, pdfFile.getPath(), "发文管理.pdf");
            return;
        }

        FileUtil.downFileAndDelFile(response, wordFile.getPath(), "发文管理.docx");
    }

    /**
     * 创建临时文件，如果创建失败则抛出异常
     *
     * @param prefix       文件前缀
     * @param suffix       文件后缀
     * @param errorMessage 错误消息
     * @return 临时文件
     */
    private File createTempFileOrThrow(String prefix, String suffix, String errorMessage) {
        try {
            return File.createTempFile(prefix, suffix);
        } catch (IOException e) {
            throw new JPMException(errorMessage + e.getMessage());
        }
    }

    /**
     * 替换发文管理内容中的文本
     *
     * @param nContent 内容
     * @param token    令牌
     * @return 替换后的内容
     */
    private String replaceOaNoticeContentText(String nContent, String token) {
        if (ValidateUtil.isEmpty(nContent)) {
            return "";
        }

        String downloadUrl = sysConfigService.getSysConfByCode("downloadUrl");
        String html = nContent;

        html = html.replace("{6々啊是サ待liwΦ!~}", ValidateUtil.isEmpty(token) ? "" : token);
        html = html.replace("{2々w3我サ传额qasΦ!~}", ValidateUtil.isEmpty(downloadUrl) ? "" : downloadUrl + "/");

        return html;
    }

    /**
     * 设置发文管理内容中的图片样式
     *
     * @param html HTML内容
     * @return 处理后的HTML内容
     */
    private String setOaNoticeContentImageStyle(String html) {
        if (ValidateUtil.isEmpty(html) || !html.toLowerCase(Locale.ENGLISH).contains("<img")) {
            return html;
        }

        org.jsoup.nodes.Document doc = org.jsoup.Jsoup.parseBodyFragment(html);

        for (org.jsoup.nodes.Element table : doc.select("table")) {
            table.attr("style", appendHtmlStyle(table.attr("style"), "width:100%;table-layout:fixed;"));
        }

        for (org.jsoup.nodes.Element img : doc.select("img")) {
            img.removeAttr("width");
            img.removeAttr("height");
            img.attr("style", "width:50%;max-width:80%;height:auto;");
        }

        return doc.body().html();
    }

    /**
     * 追加HTML样式
     *
     * @param oldStyle    原有样式
     * @param appendStyle 要追加的样式
     * @return 处理后的样式
     */
    private String appendHtmlStyle(String oldStyle, String appendStyle) {
        if (ValidateUtil.isEmpty(oldStyle)) {
            return appendStyle;
        }

        return oldStyle.endsWith(";") ? oldStyle + appendStyle : oldStyle + ";" + appendStyle;
    }

    /**
     * 格式化发文管理审批时间
     *
     * @param time 时间对象
     * @return 格式化后的时间字符串
     */
    private String formatOaNoticeApprovalTime(Object time) {
        if (time == null) {
            return "";
        }

        if (time instanceof Date) {
            return DateUtil.Date2String((Date) time, "yyyy-MM-dd HH:mm:ss");
        }

        String value = time.toString();
        if (value.length() >= 19) {
            return value.substring(0, 19).replace("T", " ");
        }

        return value;
    }

    /**
     * 渲染发文管理正文的HTML
     *
     * @param wordPath    Word文件路径
     * @param placeholder 占位符
     * @param html        HTML内容
     */
    private void renderOaNoticeContentHtml(String wordPath, String placeholder, String html) {
        try {
            Document doc = new Document(wordPath);
            doc.getRange().replace(
                    Pattern.compile(Pattern.quote(placeholder)),
                    "",
                    new FindReplaceOptions(new InsertHtmlCallback(html))
            );
            doc.save(wordPath);
        } catch (Exception e) {
            throw new JPMException("正文HTML渲染失败！");
        }
    }

    /**
     * 插入HTML内容的回调类
     */
    private static class InsertHtmlCallback implements IReplacingCallback {
        private final String html;

        private InsertHtmlCallback(String html) {
            this.html = ValidateUtil.isEmpty(html) ? "" : html;
        }

        @Override
        public int replacing(ReplacingArgs e) throws Exception {
            Node node = e.getMatchNode();
            Document document = (Document) node.getDocument();
            DocumentBuilder builder = new DocumentBuilder(document);

            builder.moveTo(node);

            if (ValidateUtil.isNotEmpty(html)) {
                builder.insertHtml(html);
            }

            return ReplaceAction.REPLACE;
        }
    }

    private String getPid(String pId, String secId, String conId) {
        if (ValidateUtil.isNotEmpty(pId)) return pId;
        if (ValidateUtil.isNotEmpty(secId)) {
            pId = archiveMapper.getPidBySecId(secId);
            if (ValidateUtil.isNotEmpty(pId)) return pId;
        }
        if (ValidateUtil.isNotEmpty(conId)) {
            pId = archiveMapper.getPidByConId(conId);
            if (ValidateUtil.isNotEmpty(pId)) return pId;
        }
        return null;
    }

    private String getExcelConfigId(String apiUrl) {
        if (ValidateUtil.isEmpty(apiUrl)) return null;
        String[] strs = apiUrl.split("/");
        return strs[strs.length - 1];
    }

    private void getLogInfo(SysUser user, String token) {
        LogUserInfoVo uf = new LogUserInfoVo();
        uf.setId(user.getuId());
        uf.setCode(user.getuCode());
        uf.setUType(user.getuType());
        uf.setTeId(user.getTeId());
        uf.setUFirstLogin(0);
        redisUtils.set("firstLogin_" + token, 0);
        redisUtils.set("userLoginInfo_" + user.getuId(), JacksonUtils.toJSONString(uf));
    }
}
