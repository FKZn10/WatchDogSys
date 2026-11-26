package com.jpmsoft.projectProducer.utils;


import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.BpmMsg;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
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

    public String getReqToken() {
        String token = null;
        try {
            HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            token = request.getHeader("token");
        } catch (Exception ex) {
            System.out.println("可能非用户请求，获取不到token！");
        }
        return token;
    }

    public LogUserInfoVo userInfo() {
        //获取用户登录信息
        String token = getReqToken();
        if (ValidateUtil.isNotEmpty(token))
            return getUserInfoByToken(token);
        return null;
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

    public LogUserInfoVo getUserInfoByUid(String uId) {
        Object user = get("userLoginInfo_" + uId);
        if (user == null)
            throw new JPMException(HttpStatus.SC_UNAUTHORIZED, ResMsg.tokenInvalid);
        return JacksonUtils.parseObject(user.toString(), LogUserInfoVo.class);
    }


    /**
     * 获取token对应用户ID
     *
     * @param token
     * @return
     */
    public String getUid(String token) {
        Object userId = get(token);
        if (userId == null)
            throw new JPMException(HttpStatus.SC_UNAUTHORIZED, ResMsg.tokenInvalid);
        return userId.toString();
    }

    /**
     * 是否显示作废数据
     * 本人创建的数据不受此配置项影响，会全部显示
     * 0：不显示，缺省或其他：显示
     *
     * @return
     */
    public int showCancelData() {
        int config = 1;
        Object showCancelData = get("Config_showCancelData");
        if (!Objects.isNull(showCancelData)) {
            config = Integer.parseInt(showCancelData.toString());
        }
        return config;
    }

    public LogUserInfoVo getUserInfoByToken(String token) {
        Object userId = get(token);
        if (userId == null)
            throw new JPMException(HttpStatus.SC_UNAUTHORIZED, ResMsg.tokenInvalid);
        Object user = get("userLoginInfo_" + userId);
        if (user == null)
            throw new JPMException(HttpStatus.SC_UNAUTHORIZED, ResMsg.tokenInvalid);
        return JacksonUtils.parseObject(user.toString(), LogUserInfoVo.class);
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

    public String tenantKey(String key, String teId) {
        if (ValidateUtil.isNotEmpty(teId)) key = teId + "_" + key;
        return key;
    }

    public String getCustomerId(String token) {
        if (ValidateUtil.isEmpty(token)) {
            return "All";
        }
        Object userId = get(token);
        if (userId == null)
            throw new JPMException(HttpStatus.SC_UNAUTHORIZED, BpmMsg.tokenInvalid);
        Object user = get("userLoginInfo_" + userId);
        if (user == null)
            throw new JPMException(HttpStatus.SC_UNAUTHORIZED, BpmMsg.tokenInvalid);
        com.jpmsoft.common.model.response.LogUserInfoVo obj = JacksonUtils.parseObject(user.toString(), com.jpmsoft.common.model.response.LogUserInfoVo.class);
        if (obj.isEnableTenant()) {
            if (ValidateUtil.isEmpty(obj.getTeId())) {
                return "All";
            } else {
                return obj.getTeId();
            }
        } else {
            return "All";
        }
    }
}
