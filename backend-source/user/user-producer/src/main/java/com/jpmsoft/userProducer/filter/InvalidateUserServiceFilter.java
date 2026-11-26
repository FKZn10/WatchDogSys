package com.jpmsoft.userProducer.filter;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@ConditionalOnProperty(prefix = "user-service", name = "invalid", havingValue = "true")
@Component
public class InvalidateUserServiceFilter extends OncePerRequestFilter {

    private String startTimeStr = "2024-11-1";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Date date = new Date();
        DateTime startTime = DateUtil.parse(startTimeStr, "yyyy-MM-dd");
        int validDays = 15;
        if (DateUtil.dayOfYear(date) - DateUtil.dayOfYear(startTime) > validDays) {
            response.sendError(200, "用户服务不可用");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
