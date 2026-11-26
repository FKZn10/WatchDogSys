package com.jpmsoft.apigateway.utils;


import com.jpmsoft.apigateway.filter.AccessFilter;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.common.utils.JacksonUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 */
@Component
public final class RedisUtils {

    @Autowired
    private RedisTemplate redisTemplate;

    // =============================common============================

    /**
     * 删除缓存
     *
     * @param key 可以传一个值或多个
     */
    @SuppressWarnings("unchecked")
    public void delete(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    public LogUserInfoVo getUserInfoByToken(String token) {
        Object userId = get(token);
        if (userId == null)
            throw new JPMException(HttpStatus.SC_UNAUTHORIZED, AccessFilter.tokenInvalid);
        Object user = get("userLoginInfo_" + userId);
        if (user == null)
            throw new JPMException(HttpStatus.SC_UNAUTHORIZED, AccessFilter.tokenInvalid);
        return JacksonUtils.parseObject(user.toString(), LogUserInfoVo.class);
    }

    // ============================String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return 是否成功
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间（秒）
     * @return
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
