package com.jpmsoft.fileUpload.controller;

import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.constants.FileMsg;
import com.jpmsoft.fileUpload.pojo.dto.QsRecordExcelDTO;
import com.jpmsoft.fileUpload.pojo.dto.WordTempRenderingDTO;
import com.jpmsoft.fileUpload.pojo.entity.SysFileAnnex;
import com.jpmsoft.fileUpload.service.*;
import com.jpmsoft.fileUpload.utils.AsposeUtil;
import com.jpmsoft.fileUpload.utils.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/10/10 11:04
 */
@Slf4j
@RestController
@Api(tags = "模板word导出", value = "模板word导出")
@RequestMapping("/wordTemp")
public class WordTempFillController {

    @Resource
    private IWordTempFillService wordTempFillService;
    @Resource
    private IQsRecordService qsRecordService;
    @Resource
    private IFileUploadService fileUploadService;
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Value("${file.upload.dirPath}")
    private String dirPath;


    @ApiOperation(value = "导出质量安全检查结果Excel")
    @PostMapping("/exportQsRecordExcel")
    public void exportQsRecordExcel(HttpServletResponse res, @RequestBody QsRecordExcelDTO request) {
        qsRecordService.exportQsRecordExcel(res, request);
    }

    @ApiOperation(value = "导出安全检查结果通知单word")
    @GetMapping("/exportSecureNoticeWord")
    public void exportSecureNoticeWord(HttpServletResponse response, @RequestParam String id) {
        wordTempFillService.exportQsPlanTaskWord(response, id, "inspectionNoticeWordTemp");
    }

    @ApiOperation(value = "导出安全检查结果回复单word")
    @GetMapping("/exportSecureReplyWord")
    public void exportSecureReplyWord(HttpServletResponse response, @RequestParam String id) {
        wordTempFillService.exportQsPlanTaskWord(response, id, "rectificationNoticeWordTmp");
    }

    @ApiOperation(value = "导出质量检查结果通知单word")
    @GetMapping("/exportQualityNoticeWord")
    public void exportQualityNoticeWord(HttpServletResponse response, @RequestParam String id) {
        wordTempFillService.exportQsPlanTaskWord(response, id, "qualityNoticeWordTemp");
    }

    @ApiOperation(value = "导出质量检查结果回复单word")
    @GetMapping("/exportQualityReplyWord")
    public void exportQualityReplyWord(HttpServletResponse response, @RequestParam String id) {
        wordTempFillService.exportQsPlanTaskWord(response, id, "qualityNoticeWordTmp");
    }


    @ApiOperation(value = "word模板渲染")
    @PostMapping("/wordTempRenderingByConf")
    public void wordTempRendering(HttpServletResponse response, @RequestBody WordTempRenderingDTO data) {
        String root = sysConfigService.getRootDirectory();
        String archive = sysConfigService.getArchiveDirectory();
        String relationId = sysConfigService.getSysConfByCode(data.getFileConfCode());
        if (ValidateUtil.isAnObjEmpty(relationId, archive, root)) throw new JPMException(FileMsg.missSysConfig);
        SysFileAnnex file = sysFileAnnexService.getOneFileAnnexByRelationId(sysConfigService.getSysConfByCode(data.getFileConfCode()));
        String tempPath = file.getFileLocation() == 0 ? root : archive;
        tempPath += ("/" + file.getFilePath());
        String[] tmp = tempPath.split("\\.");
        String suffix = "." + tmp[tmp.length - 1];
        String path = FileUtil.temporaryPath(root, suffix);
        if (CollectionUtils.isEmpty(data.getFileIds()))
            AsposeUtil.tempExportWord(tempPath, path, data.getData(), "s", null, data.getImgWidth(), data.getImgHeight());
        else
            AsposeUtil.tempExportWord(tempPath, path, data.getData(), "s", getImgs(sysFileAnnexService.listByIds(data.getFileIds()), root, archive), data.getImgWidth(), data.getImgHeight());
        if (ValidateUtil.isNotEmpty(data.getPdf())) {
            String pdfPath = path.replace(suffix, ".pdf");
            String fontsDirectory = sysConfigService.getSysConfByCode("fileFontsDirectory");
            AsposeUtil.wordToPdf(path, pdfPath, fontsDirectory);
            FileUtil.downFileAndDelFile(response, pdfPath, file.getFileName().replace(suffix, ".pdf"));
            return;
        }
        FileUtil.downFileAndDelFile(response, path, file.getFileName());
    }

    @ApiOperation(value = "word模板渲染")
    @PostMapping("/wordTempRendering")
    public void wordTempRendering(HttpServletResponse response, @RequestParam MultipartFile file, WordTempRenderingDTO data) {
        String root = sysConfigService.getRootDirectory();
        String archive = sysConfigService.getArchiveDirectory();
        File f = fileUploadService.temporaryStorageFile(root, file, ".*\\.(doc|docx)$", FileMsg.onlyDoc, null);
        String tempPath = f.getPath();
        String[] tmp = tempPath.split("\\.");
        String suffix = "." + tmp[tmp.length - 1];
        String path = FileUtil.temporaryPath(root, suffix);
        if (CollectionUtils.isEmpty(data.getFileIds()))
            AsposeUtil.tempExportWord(tempPath, path, data.getData(), "s", null, data.getImgWidth(), data.getImgHeight());
        else
            AsposeUtil.tempExportWord(tempPath, path, data.getData(), "s", getImgs(sysFileAnnexService.listByIds(data.getFileIds()), root, archive), data.getImgWidth(), data.getImgHeight());
        f.delete();
        FileUtil.downFileAndDelFile(response, path, f.getName());
    }

    private Map<String, String> getImgs(List<SysFileAnnex> files, String root, String archive) {
        if (CollectionUtils.isEmpty(files)) return null;
        Map<String, String> imgs = new HashMap<>();
        files.forEach(f -> {
            imgs.put(f.getFileId(), f.getFileLocation() == 1 ? archive + "/" + f.getFilePath() : root + "/" + f.getFilePath());
        });
        return imgs;
    }
}
