package com.jpmsoft.userProducer.utils;


import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.pojo.vo.LogUserInfoVo;
import org.apache.http.HttpStatus;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 */
@Component
public final class RedisUtils {

    @Resource
    private RedisTemplate redisTemplate;

    // =============================common============================

    /**
     * 租户缓存Key
     *
     * @param key  原始KEY0
     * @param teId 租户ID
     * @return
     */
    public String tenantKey(String key, String teId) {
        if (ValidateUtil.isNotEmpty(teId)) key = teId + "_" + key;
        return key;
    }

    /**
     * 获取用户ID
     *
     * @param token
     * @return
     */
    public String getUid(String token) {
        Object userId = get(token);
        if (userId == null)
            throw new JPMException(HttpStatus.SC_UNAUTHORIZED, BaseMsg.tokenInvalid);
        return userId.toString();
    }

    public LogUserInfoVo getUserInfoByToken(String token) {
        Object userId = get(token);
        if (userId == null)
            throw new JPMException(HttpStatus.SC_UNAUTHORIZED, BaseMsg.tokenInvalid);
        Object user = get("userLoginInfo_" + userId);
        if (user == null)
            throw new JPMException(HttpStatus.SC_UNAUTHORIZED, BaseMsg.tokenInvalid);
        return JacksonUtils.parseObject(user.toString(), LogUserInfoVo.class);
    }


    public LogUserInfoVo getUserInfoByUid(String uId) {
        Object user = get("userLoginInfo_" + uId);
        if (user == null)
            throw new JPMException(HttpStatus.SC_UNAUTHORIZED, BaseMsg.tokenInvalid);
        return JacksonUtils.parseObject(user.toString(), LogUserInfoVo.class);
    }

    /**
     * 开启租户模式且teId有值
     *
     * @param token
     * @return
     */
    public String getTeId(String token) {
        if (ValidateUtil.isEmpty(token)) return null;
        LogUserInfoVo user = getUserInfoByToken(token);
        if (user != null && user.isEnableTenant())
            return user.getTeId();
        return null;
    }

    public String getTeIdByUid(String uid) {
        if (ValidateUtil.isEmpty(uid)) return null;
        LogUserInfoVo user = getUserInfoByUid(uid);
        if (user != null && user.isEnableTenant())
            return user.getTeId();
        return null;
    }

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

    public Boolean zsetSet(String key, Object value, long score) {
        try {
            return redisTemplate.opsForZSet().add(key, value, score);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Long zsetCount(String key) {
        try {
            return redisTemplate.opsForZSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    public Long zsetRemove(String key, Long minScore, Long maxScore) {
        try {
            return redisTemplate.opsForZSet().removeRangeByScore(key, minScore, maxScore);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    public String getReqToken() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        return request.getHeader("token");
    }
}
