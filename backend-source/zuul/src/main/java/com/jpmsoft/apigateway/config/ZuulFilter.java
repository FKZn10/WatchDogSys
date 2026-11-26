package com.jpmsoft.apigateway.config;

import com.jpmsoft.apigateway.filter.*;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZuulFilter {

    @Bean
    public AccessFilter accessFilter() {
        return new AccessFilter();
    }

    @Bean
    public NoticeXssFilter noticeXssFilter() {
        return new NoticeXssFilter();
    }

    @Bean
    public SendErrorFilter sndErrorFilter() {
        return new SelfSendErrorFilter();
    }

    @Bean
    public ReturnFilter returnFilter() {
        return new ReturnFilter();
    }

    /**
     * 注册针对所有路由text/event-stream响应的逐块刷新过滤器。
     *
     * @return SSE事件流刷新过滤器
     */
    @Bean
    public SseFlushFilter sseFlushFilter() {
        return new SseFlushFilter();
    }
}
