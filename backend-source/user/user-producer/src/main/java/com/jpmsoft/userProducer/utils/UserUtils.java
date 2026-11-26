package com.jpmsoft.userProducer.utils;

import com.jpmsoft.userProducer.pojo.vo.LogUserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户信息工具类
 */
@Component
public class UserUtils {
    @Autowired
    private RedisUtils redisUtils;

    private static UserUtils userUtils;

    /**
     * 初始化静态工具依赖。
     *
     */
    @PostConstruct
    public void init() {
        userUtils = this;
    }

    /**
     * 获取用户信息
     *
     * @return 获取用户信息结果
     */
    public static LogUserInfoVo getUserInfo() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("token");
        return userUtils.redisUtils.getUserInfoByToken(token);
    }

    /**
     * 获取token
     *
     * @return 获取token结果
     */
    public static String getToken() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        return request.getHeader("token");
    }
}