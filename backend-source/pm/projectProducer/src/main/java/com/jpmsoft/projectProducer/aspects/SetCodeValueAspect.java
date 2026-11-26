package com.jpmsoft.projectProducer.aspects;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.utils.ClazzUtil;
import org.apache.http.HttpStatus;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

@Component
@Aspect
public class SetCodeValueAspect {

    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private ISysConfigService sysConfigService;


    /**
     * 获取指定方法
     *
     * @param objectClass
     * @param fieldName
     * @param method
     * @return
     */
    public static Method getMethod(Class<?> objectClass, String fieldName, String method) {
        try {
            Class<?>[] parameterTypes = new Class<?>[1];
            Field field = objectClass.getDeclaredField(fieldName);
            parameterTypes[0] = field.getType();
            return objectClass.getMethod(method, parameterTypes);
        } catch (NoSuchFieldException | NoSuchMethodException e) {
            throw new JPMException("Reflect error!");
        }
    }

    /**
     * 根据注解设置编码值
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("@annotation(com.jpmsoft.projectProducer.annotations.CodeGeneration)")
    public Object dosetFeildValue(ProceedingJoinPoint pjp) throws Throwable {
        //region 前置操作 生成编码并赋值到入参中
        //获取入参
        Object[] args = pjp.getArgs();
        //操作结果集
        //获取到注解 然后通过反射执行 方法
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        CodeGeneration cg = signature.getMethod().getAnnotation(CodeGeneration.class);
        //是否开启自动编码
        boolean openCode = false;
        Object codeArg = null;
        for (Object arg : args) {
            Class clazz = arg.getClass();
            if (clazz == cg.beanClass()) {
                List<Field> fieldList = ClazzUtil.getAllFields(clazz);
                Field field = ClazzUtil.getField(fieldList, cg.codeParam());
                Field key = ClazzUtil.getField(fieldList, cg.key());
                field.setAccessible(true);
                key.setAccessible(true);
                //String oldCode = (String) field.get(arg);
                String paimaryKey = (String) key.get(arg);
                boolean flag = true;
                if (cg.codeRule().equals("ConContract") && sysConfigService.endProcessGenerateCode())
                    flag = false;
                if (flag) {
                    //临时逻辑  合同根据配置在流程结束后生成编码
                    if (ValidateUtil.isEmpty(paimaryKey) && apiUtilsService.sequenceRestEnable(cg.codeRule())) {
                        openCode = true;
                        codeArg = arg;
                        String value = apiUtilsService.generateCodeByTCode(cg.codeRule(), arg);
                        Method method = getMethod(clazz, cg.codeParam(), cg.method());
                        if (!ValidateUtil.isEmpty(value))
                            method.invoke(arg, value);
                    }
                }
            }
        }
        //endregion
        Object res = pjp.proceed();
        //region 后置操作 更新编码自增序号
        if (openCode) {
            if (res instanceof BaseResponse) {
                BaseResponse result = (BaseResponse) res;
                if (result.getSuccess() && result.getStatusCode() == HttpStatus.SC_OK) {
                    BaseResponse refersh = apiUtilsService.refershAutoNo(cg.codeRule(), codeArg);
                }
            }
        }
        //endregion
        return res;
    }

}
