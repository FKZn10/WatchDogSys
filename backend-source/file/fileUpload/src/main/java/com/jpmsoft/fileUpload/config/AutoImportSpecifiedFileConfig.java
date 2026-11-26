package com.jpmsoft.fileUpload.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.fileUpload.pojo.dto.FormDataImpDTO;
import com.jpmsoft.fileUpload.pojo.entity.beijing.AutoImportFileRecord;
import com.jpmsoft.fileUpload.service.IAutoImportFileRecordService;
import com.jpmsoft.fileUpload.service.IFileUploadService;
import com.jpmsoft.fileUpload.utils.FileUtil;
import com.jpmsoft.fileUpload.utils.MD5File;
import org.apache.commons.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

/**
 * 北京219定制化自动扫描目录导入文件
 */
@ConditionalOnProperty(prefix = "auto-import-file", name = "enable", havingValue = "true")
@Configuration
public class AutoImportSpecifiedFileConfig {

    @Value("${auto-import-file.directory-path}")
    private String directoryPath;

    @Resource
    private IAutoImportFileRecordService autoImportFileRecordService;

    @Resource
    private IFileUploadService fileUploadService;

    /**
     * 定时任务，扫描目录，导入文件
     * 5分钟一次
     */
    @Scheduled(cron = "0 0/5 * * * ? ")
//    @Scheduled(cron = "0/10 * * * * ?")
    public void scanDirectoryPathToImportFile() {
        // 获取目录下的文件列表
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            // 目录不存在
            return;
        }
        File[] files = directory.listFiles();
        if (files == null || files.length < 1) {
            return;
        }
        for (File file : files) {
            String fileName = file.getName();
            AutoImportFileRecord autoImportFileRecord = autoImportFileRecordService.getOne(new LambdaQueryWrapper<AutoImportFileRecord>()
                    .eq(AutoImportFileRecord::getFileName, fileName));
            if (autoImportFileRecord != null && AutoImportFileRecord.HANDLE_SUCCESS.equals(autoImportFileRecord.getHandleSuccess())) {
                // 该文件已处理过，并且处理成功
                continue;
            }
            FormDataImpDTO formDataImpDTO = new FormDataImpDTO();
            formDataImpDTO.setTops(Collections.emptyList());
            LogUserInfoVo logUserInfoVo = new LogUserInfoVo();
            logUserInfoVo.setName("robot");
            logUserInfoVo.setId("robot");
            if (autoImportFileRecord == null){
                autoImportFileRecord = new AutoImportFileRecord();
                autoImportFileRecord.setUuid(UUID.randomUUID().toString());
                autoImportFileRecord.setFileName(fileName);
                autoImportFileRecord.setCreateTime(new Date());
            }
            autoImportFileRecord.setHandleTime(new Date());
            try {
                BaseResponse baseResponse = fileUploadService.importFormData(new MultipartFile[]{}, formDataImpDTO, logUserInfoVo);
                autoImportFileRecord.setHandleSuccess(Boolean.TRUE.equals(baseResponse.getSuccess()) ?
                        AutoImportFileRecord.HANDLE_SUCCESS : AutoImportFileRecord.HANDLE_FAILED);
                autoImportFileRecord.setHandleResult(baseResponse.getMessage());
            } catch (Exception exception) {
                autoImportFileRecord.setHandleSuccess(AutoImportFileRecord.HANDLE_FAILED);
                autoImportFileRecord.setHandleResult(exception.getMessage());
                exception.printStackTrace();
            }
            autoImportFileRecordService.saveOrUpdate(autoImportFileRecord);
        }
    }

}
