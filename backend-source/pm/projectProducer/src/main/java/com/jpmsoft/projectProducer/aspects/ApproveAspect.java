package com.jpmsoft.projectProducer.aspects;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.RequestBean;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.Approve;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
@Order(1)
public class ApproveAspect {


    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Pointcut("@annotation(com.jpmsoft.projectProducer.annotations.Approve)")
    public void webApproveAspect() {

    }


    /**
     * 环绕通知
     *
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @AfterReturning(value = "webApproveAspect()", returning = "v")
    public Object recodeWebAspect(JoinPoint proceedingJoinPoint, Object v) throws Throwable {
        BaseResponse baseResponse = (BaseResponse) v;
        Object result = null;

        SysFile sysFilePojo = new SysFile();
//        long start = System.currentTimeMillis();

        // 执行方法的真实调用
        //    result = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());

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
        Approve annotation = method.getAnnotation(Approve.class);
        SysFile methodParameter = getMethodParameter(sysFilePojo, method, proceedingJoinPoint.getArgs());
        String data = (String) baseResponse.getData();
        sysFilePojo.setFileid(data);
        sysFilePojo.setFiletitle(annotation.title());
        sysFilePojo.setFilecode(annotation.fileCode());
        sysFilePojo.setFileflowstatus(annotation.status());
        sysFilePojo.setFilecreateuser(methodParameter.getFilecreateuser());
        sysFilePojo.setFilecreateuserName(methodParameter.getFilecreateuserName());
        sysFilePojo.setFilecreatedate(new Date());
        if (ValidateUtil.isNotEmpty(data)){
            sysFileService.save(sysFilePojo);
            //图片处理
            if (ValidateUtil.isNotEmpty(sysFilePojo.getFileRelationId())) {
                sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(sysFilePojo.getFileRelationId()), sysFilePojo.getFileid());
            }
        }
        return result;
    }

    /**
     * 获取方法的执行参数
     *
     * @return {"key_参数的名称":"value_参数的值"}
     */
    private SysFile getMethodParameter(SysFile sysFilePojo, Method method, Object[] args) {
        Map<String, Object> methodParametersWithValues = new HashMap<>();
        LocalVariableTableParameterNameDiscoverer localVariableTableParameterNameDiscoverer =
                new LocalVariableTableParameterNameDiscoverer();
        // 方法的形参名称
        String[] parameterNames = localVariableTableParameterNameDiscoverer.getParameterNames(method);
        for (int i = 0; i < parameterNames.length; i++) {
            if (parameterNames[i].equals("requestBean")) {
                RequestBean requestBean = (RequestBean) args[i];
                Map<String, String> map = (HashMap) requestBean.getInfo();
                SysFile sysFilePojo1 = new SysFile();
//                sysFilePojo.setFileflowstatus(map.get("approveStatus"));
                sysFilePojo.setFilecreateuser(map.get("createId"));
                sysFilePojo.setFilecreateuserName(map.get("createName"));
                if (ValidateUtil.isNotEmpty(map.get("fileRelationId"))){
                    sysFilePojo.setFileRelationId(map.get("fileRelationId"));
                }
                sysFilePojo.setFilecreatedate(new Date());
                return sysFilePojo1;
            }

        }
        return null;
    }

    //   return methodParametersWithValues;

}
