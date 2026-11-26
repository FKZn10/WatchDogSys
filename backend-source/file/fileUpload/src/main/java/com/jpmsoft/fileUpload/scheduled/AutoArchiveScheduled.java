package com.jpmsoft.fileUpload.scheduled;

import com.jpmsoft.fileUpload.service.IArchiveService;
import com.jpmsoft.fileUpload.service.ISysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 自动归档
 * Created By:李文涛 aaron
 * Created Time:2022/6/19 15:01
 */
@Slf4j
@Component
public class AutoArchiveScheduled {

    @Resource
    private IArchiveService archiveService;

    @Resource
    private ISysConfigService sysConfigService;

    /**
     * 自动归档
     */
    @Scheduled(cron = "${platform.parameters.scheduling.autoArchive:-}")
    public void autoArchive() {
        if (!autoArchiveEnabled()) {
            log.info("自动归档开关已关闭，跳过本次执行");
            return;
        }
        log.info("定时自动归档");
        try {
            archiveService.autoArchive();
            archiveService.autoArchiveProCpRprFileInputCode();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("自动归档异常！" + ex.getMessage(),ex);
        }
    }

    @Scheduled(cron = "${platform.parameters.scheduling.autoXzArchive:-}")
    public void autoXzArchive() {
        if (!autoArchiveEnabled()) {
            log.info("自动归档开关已关闭，跳过本次执行");
            return;
        }
        log.info("定时自动归档<行政附件>");
        try {
            archiveService.autoXzArchive();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("自动归档<行政附件>异常！" + ex.getMessage(),ex);
        }
    }

    @Scheduled(cron = "${platform.parameters.scheduling.autoJmArchive:-}")
    public void autoJmArchive() {
        if (!autoArchiveEnabled()) {
            log.info("自动归档开关已关闭，跳过本次执行");
            return;
        }
        archiveService.autoArchiveJimuReport(50);
    }

    /**
     * 自动归档开关
     * sys_config 配置项 autoArchiveSwitch：
     * Con_Value = "0" 表示关闭定时自动归档；未配置或其他值默认开启（触发）
     */
    private boolean autoArchiveEnabled() {
        String sw = sysConfigService.getSysConfByCode("autoArchiveSwitch");
        return !"0".equals(sw);
    }
}
