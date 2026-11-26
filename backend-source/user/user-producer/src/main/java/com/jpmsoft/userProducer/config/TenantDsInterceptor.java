package com.jpmsoft.userProducer.config;

//import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
//import com.jpmsoft.common.utils.ValidateUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;

/**
 * 根据header标识切换数据源
 * @Author: 李文涛 aaron
 * @date: 2023/2/27 9:27
 */
//@Slf4j
//@Component
public class TenantDsInterceptor
//        implements HandlerInterceptor
        {
//
//    /**
//     * 在请求处理前调用
//     * @param request
//     * @param response
//     * @param handler
//     * @return
//     * @throws Exception
//     */
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String requestURI = request.getRequestURI();
//        log.info("经过多数据源Interceptor,当前路径是{}", requestURI);
//        String tenant = request.getHeader("tenant");
//        //如果tenantId为空，则使用默认数据源
//        if (ValidateUtil.isNotEmpty(tenant)){
//            log.info("拿到的租户编码{}", tenant);
//            DynamicDataSourceContextHolder.push(tenant);
//        }
//        return true;
//    }
//
//    /**
//     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
//     * @param request
//     * @param response
//     * @param handler
//     * @param modelAndView
//     * @throws Exception
//     */
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//
//    }
//
//    /**
//     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
//     * @param request
//     * @param response
//     * @param handler
//     * @param ex
//     * @throws Exception
//     */
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        //清空当前线程数据源
//        DynamicDataSourceContextHolder.clear();
//    }
}
