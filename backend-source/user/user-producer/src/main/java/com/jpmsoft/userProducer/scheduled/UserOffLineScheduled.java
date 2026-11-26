package com.jpmsoft.userProducer.scheduled;

import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.service.ISysConfigEntityService;
import com.jpmsoft.userProducer.utils.UserStatisticsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 用户离线定时检测
 * Created By:李文涛 aaron
 * Created Time:2022/6/15 9:23
 */
@Slf4j
@Component
public class UserOffLineScheduled {

    @Resource
    private UserStatisticsUtil userStatisticsUtil;
    @Resource
    private ISysConfigEntityService sysConfigEntityService;


    @Scheduled(cron = "${platform.parameters.scheduling.userOffLine:0 0/1 * * * ?}")
    public void userOffLine() {
        log.info("用户离线定时检测");
        //web定时心跳发送配置 秒
        String str = sysConfigEntityService.getSysConfigByCode("tokenPollTime");
        Integer second = ValidateUtil.isEmpty(str) ? -60 : (~(Integer.parseInt(str) - 1));
        Long num = userStatisticsUtil.userOfflineClean(second);
        log.info("清除过期用户数量:" + num);
    }
}
