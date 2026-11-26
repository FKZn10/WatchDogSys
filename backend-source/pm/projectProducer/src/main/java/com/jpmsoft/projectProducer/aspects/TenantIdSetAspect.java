package com.jpmsoft.projectProducer.aspects;

import com.jpmsoft.common.model.BasePageEntity;
import com.jpmsoft.common.utils.ClazzUtil;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/6/26 9:12
 */
@Component
@Aspect
public class TenantIdSetAspect {

    @Resource
    private RedisUtils redisUtils;
    //赋值的字段名
    private final static String fieldName = "teId";
    //赋值的方法名
    private final static String setMethod = "setTeId";

    @Around("@annotation(com.jpmsoft.projectProducer.annotations.TenantId)")
    public Object doSetTeId(ProceedingJoinPoint pjp) throws Throwable {
        //region 前置操作
        //region 赋值租户Id
        //入参信息
        Object[] args = pjp.getArgs();
        //获取用户登录信息
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String token = request.getHeader("token");
        if (ValidateUtil.isNotEmpty(token)) {
            LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
            if (user.isEnableTenant()) {
                for (Object arg : args) {
                    if (arg instanceof BasePageEntity) {
                        Class<?> clazz = arg.getClass();
                        boolean setSuccess = false;
                        //字段可能继承于父级
                        while (clazz != null && !setSuccess) {
                            Method method = ClazzUtil.getMethod(clazz, fieldName, setMethod);
                            if (method != null) {
                                method.invoke(arg, user.getTeId());
                                setSuccess = true;
                            }
                            clazz = clazz.getSuperclass();
                        }
                    }
                }
            }
        }
        //endregion
        //endregion
        Object res = pjp.proceed();
        return res;
    }
}
