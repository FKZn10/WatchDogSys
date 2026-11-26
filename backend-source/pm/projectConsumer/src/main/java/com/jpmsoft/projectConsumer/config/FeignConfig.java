package com.jpmsoft.projectConsumer.config;

import com.jpmsoft.common.utils.JacksonUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * feign调用默认传递token请求头头
 *
 * @Author: 李文涛 aaron
 * @date: 2023/7/7 10:16
 */
@Slf4j
@Configuration
public class FeignConfig implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest httpServletRequest = this.getHttpServletRequest();
        log.info("RequestTemplate.headers:" + JacksonUtils.toJSONString(requestTemplate.headers()));
        if (null != httpServletRequest) {
            Map<String, String> headers = getHeaders(httpServletRequest);
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                if (entry.getKey().equals("token"))//全量设置报错too many bytes written
                    requestTemplate.header(entry.getKey(), entry.getValue());
            }
        }
    }

    private HttpServletRequest getHttpServletRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        if (enumeration != null) {
            while (enumeration.hasMoreElements()) {
                String key = enumeration.nextElement();
                String value = request.getHeader(key);
                map.put(key, value);
            }
        }
        return map;
    }

}
