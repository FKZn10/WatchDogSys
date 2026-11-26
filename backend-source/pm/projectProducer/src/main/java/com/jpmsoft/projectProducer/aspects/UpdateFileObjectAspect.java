package com.jpmsoft.projectProducer.aspects;

import com.jpmsoft.common.utils.RequestBean;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.UpdateFileObject;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class UpdateFileObjectAspect {


    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Pointcut("@annotation(com.jpmsoft.projectProducer.annotations.UpdateFileObject)")
    public void webUpdateFileObjectAspect() {

    }


    /**
     * 环绕通知
     *
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = "webUpdateFileObjectAspect()")
    public Object recodeWeb(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object result = null;

//        long start = System.currentTimeMillis();

        // 执行方法的真实调用
           result = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());

//        long end = System.currentTimeMillis();

        // 请求该接口花费的时间
        // 获取当前请求的request对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        // 获取方法
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        // 获取类的名称
        String targetClassName = proceedingJoinPoint.getTarget().getClass().getName();
        //获取方法
        Method method = signature.getMethod();
        // 获取LogAnnotation
        UpdateFileObject annotation = method.getAnnotation(UpdateFileObject.class);
        Map<String,String> methodParameter = getMethodParameter(method, proceedingJoinPoint.getArgs());
        //图片处理
        if (ValidateUtil.isNotEmpty(methodParameter.get("objectId")) && ValidateUtil.isNotEmpty(methodParameter.get("fileRelationId"))) {
            String rteFileIds ="";
            if (ValidateUtil.isNotEmpty( methodParameter.get("rteFileIds"))){
                rteFileIds=methodParameter.get("rteFileIds");
            }
            sysFileAnnexService.refreshRelationFile(Arrays.asList(methodParameter.get("fileRelationId")), Arrays.asList(rteFileIds), methodParameter.get("objectId"));
        }
        return result;
    }

    /**
     * 获取方法的执行参数
     *
     * @return {"key_参数的名称":"value_参数的值"}
     */
    private Map getMethodParameter(Method method, Object[] args) {
        Map<String, String> methodParametersWithValues = new HashMap<>();
        LocalVariableTableParameterNameDiscoverer localVariableTableParameterNameDiscoverer =
                new LocalVariableTableParameterNameDiscoverer();
        // 方法的形参名称
        String[] parameterNames = localVariableTableParameterNameDiscoverer.getParameterNames(method);
        for (int i = 0; i < parameterNames.length; i++) {
            if (parameterNames[i].equals("requestBean")) {
                RequestBean requestBean = (RequestBean) args[i];
                Map<String, String> map = (HashMap) requestBean.getInfo();
                if (ValidateUtil.isNotEmpty(map.get("fileRelationId"))) {
                    methodParametersWithValues.put("fileRelationId", map.get("fileRelationId"));
                }
                if (ValidateUtil.isNotEmpty(map.get("rteFileIds"))) {
                    methodParametersWithValues.put("rteFileIds", map.get("rteFileIds"));
                }
                if (ValidateUtil.isNotEmpty(map.get("objectId"))) {
                    methodParametersWithValues.put("objectId", map.get("objectId"));
                }
                return methodParametersWithValues;
            }
        }
        return methodParametersWithValues;
    }
}
