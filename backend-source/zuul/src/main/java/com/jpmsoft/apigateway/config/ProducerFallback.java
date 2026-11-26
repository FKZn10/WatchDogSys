package com.jpmsoft.apigateway.config;

import com.jpmsoft.apigateway.base.BaseResponse;
import com.jpmsoft.common.utils.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 服务熔断降级
 * Created By:李文涛 aaron
 * Created Time:2022/6/15 14:55
 */
@Slf4j
@Component
public class ProducerFallback implements FallbackProvider {


    /**
     * getRoute方法的返回值就是要监听的挂掉的微服务的名字
     * 如果需要所有服务都走这个熔断回退，则return "*" 或 return null
     *
     * @return
     */
    @Override
    public String getRoute() {
        return "*";
    }

    /**
     * 当服务无法执行的时候，返回托底信息
     *
     * @param route
     * @param cause
     * @return
     */
    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {

            /**
             * 设置响应头信息
             * @return
             */
            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                return httpHeaders;
            }

            /**
             * 返回托底数据
             * @return
             * @throws IOException
             */
            @Override
            public InputStream getBody() throws IOException {
                BaseResponse res = new BaseResponse(route + " 服务不可用，请联系管理员！");
                return new ByteArrayInputStream(JacksonUtils.toJSONString(res).getBytes());
            }

            /**
             * 返回相应的状态码：
             *  zuul转请求失败了，但是客户端向zuul发送请求是成功的，
             *  所以不应该把404、500等问题抛给客户端
             * @return
             * @throws IOException
             */
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            /**
             * ClientHttpResponse的fallback的状态码
             * @return
             * @throws IOException
             */
            @Override
            public int getRawStatusCode() throws IOException {
                return this.getStatusCode().value();
            }

            /**
             *ClientHttpResponse的fallback的状态码
             * @return
             * @throws IOException
             */
            @Override
            public String getStatusText() throws IOException {
                return this.getStatusCode().getReasonPhrase();
            }

            /**
             * Close this response, freeing any resources created.
             */
            @Override
            public void close() {

            }
        };
    }
}
