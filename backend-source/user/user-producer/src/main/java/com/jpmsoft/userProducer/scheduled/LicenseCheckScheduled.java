package com.jpmsoft.userProducer.scheduled;

import com.jpmsoft.userProducer.utils.LicenseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 检查license 默认每天4点执行一次
 */
@Slf4j
@Component
public class LicenseCheckScheduled {

    @Resource
    private LicenseUtil licenseUtil;

    @Scheduled(cron = "0 0 4 * * ?")
    public void userOffLine() {
        log.info("License检测");
        //web定时心跳发送配置 秒
        licenseUtil.check();
    }
}
