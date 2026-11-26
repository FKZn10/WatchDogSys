package com.jpmsoft.fileUpload.scheduled;

import com.jpmsoft.fileUpload.service.ISysFileAnnexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 自动删除临时文件
 * 删除上传未保存表单的文件(白天用户使用时不允许执行该任务，防止用户未保存文件被删除)
 * 删除已上传oss的文件
 *
 * @Author: 李文涛 aaron
 * @date: 2023/11/6 9:16
 */
@Slf4j
@Component
public class DeleteTemporaryFileScheduled {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Scheduled(cron = "${platform.parameters.scheduling.delTemporaryFile:-}")
    public void deleteTemporaryFile() {
        try {
            log.info("定时任务->删除上传未保存表单的文件");
            sysFileAnnexService.deleteUnSaveFile();
            log.info("定时任务->删除已上传OSS的文件");
            sysFileAnnexService.deleteOssLocalFile();
        } catch (Exception ex) {
            log.error("定时任务自动删除文件执行异常! message:" + ex.getMessage(),ex);
            ex.printStackTrace();
        }
    }
}
