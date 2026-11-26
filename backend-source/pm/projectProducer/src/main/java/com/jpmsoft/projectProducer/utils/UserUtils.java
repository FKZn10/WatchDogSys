package com.jpmsoft.projectProducer.utils;

import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
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

    @PostConstruct
    public void init() {
        userUtils = this;
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public static LogUserInfoVo getUserInfo() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("token");
        return userUtils.redisUtils.getUserInfoByToken(token);
    }
}
