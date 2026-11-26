package com.jpmsoft.apigateway.filter;

import com.jpmsoft.apigateway.base.BaseResponse;
import com.jpmsoft.common.utils.JacksonUtils;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;

@Slf4j
public class SelfSendErrorFilter extends SendErrorFilter {

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        ZuulException e = (ZuulException) findZuulException(ctx.getThrowable()).getThrowable();
        ctx.remove("throwable");
        BaseResponse baseResponse = new BaseResponse("网关转发异常！");
        ctx.setResponseBody(JacksonUtils.toJSONString(baseResponse));
        ctx.setResponseStatusCode(200);
        return null;
    }
}
