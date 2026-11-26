package com.jpmsoft.gmgws.utils;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 统计用户在线
 * 用户上线 离线 统计
 * Created By:李文涛 aaron
 * Created Time:2022/6/15 9:08
 */
@Component
public class UserStatisticsUtil {

    private static final String ONLINE_USER = "online_user";

    @Resource
    private RedisUtils redisUtils;

    //region 在线用户统计相关
    public void userOnline(String userId) {
        redisUtils.zsetSet(ONLINE_USER, userId, System.currentTimeMillis());
    }

    public Long userOnlineCount() {
        return redisUtils.zsetCount(ONLINE_USER);
    }

    public Long userOfflineClean(Integer second) {
        Date dateTime = new Date();
        dateTime = DateUtil.DateChangeSecond(dateTime, second);
        return redisUtils.zsetRemove(ONLINE_USER, 0L, dateTime.getTime());
    }
    //endregion
}
