package com.jpmsoft.apigateway.filter;

import com.netflix.util.Pair;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.MediaType;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

/**
 * 在默认响应转发前包装所有路由的SSE输出流，每次写入立即刷新，避免事件滞留在网关缓冲区。
 * 上游读取、响应头转发及资源释放继续由默认SendResponseFilter负责。
 */
public class SseFlushFilter extends ZuulFilter {

    /**
     * 在上游响应已建立的post阶段处理输出流。
     *
     * @return post过滤阶段
     */
    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    /**
     * 排在默认响应发送过滤器之前，保留前面的响应检查逻辑。
     *
     * @return 默认响应发送顺序减一
     */
    @Override
    public int filterOrder() {
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER - 1;
    }

    /**
     * 根据上游Content-Type处理所有text/event-stream响应，不限制请求路径。
     *
     * @return 当前响应是否需要立即刷新
     */
    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        if (context.getThrowable() != null || context.getResponse() == null) {
            return false;
        }
        for (Pair<String, String> header : context.getZuulResponseHeaders()) {
            if ("Content-Type".equalsIgnoreCase(header.first()) && header.second() != null) {
                String contentType = header.second().split(";", 2)[0].trim();
                return MediaType.TEXT_EVENT_STREAM_VALUE.equalsIgnoreCase(contentType);
            }
        }
        return false;
    }

    /**
     * 包装响应而不读取上游流，让默认转发过滤器写入每一块数据时立即flush。
     *
     * @return 无额外过滤结果
     */
    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletResponse response = context.getResponse();
        if (!(response instanceof FlushingResponse)) {
            context.setResponse(new FlushingResponse(response));
        }
        return null;
    }

    /**
     * 仅包装二进制输出流，其余Servlet响应行为原样委托。
     */
    private static final class FlushingResponse extends HttpServletResponseWrapper {
        private ServletOutputStream output;

        /**
         * 保留原响应的状态码、头部和容器生命周期。
         *
         * @param response 原网关响应
         */
        private FlushingResponse(HttpServletResponse response) {
            super(response);
        }

        /**
         * 首次访问时包装原输出流，后续访问返回同一实例。
         *
         * @return 写入后立即刷新的输出流
         * @throws IOException 原响应无法获取输出流
         */
        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            if (output == null) {
                output = new FlushingOutputStream(super.getOutputStream());
            }
            return output;
        }
    }

    /**
     * 每次写入后刷新原输出流，并委托Servlet异步写入及关闭行为。
     */
    private static final class FlushingOutputStream extends ServletOutputStream {
        private final ServletOutputStream delegate;

        /**
         * 保存原输出流。
         *
         * @param delegate 原Servlet输出流
         */
        private FlushingOutputStream(ServletOutputStream delegate) {
            this.delegate = delegate;
        }

        /**
         * 写入单字节后立即刷新。
         *
         * @param value 字节值
         * @throws IOException 客户端断开或写入失败
         */
        @Override
        public void write(int value) throws IOException {
            delegate.write(value);
            delegate.flush();
        }

        /**
         * 写入当前数据块后立即刷新，不等待上游流结束。
         *
         * @param bytes  当前数据块
         * @param offset 起始偏移
         * @param length 字节数
         * @throws IOException 客户端断开或写入失败
         */
        @Override
        public void write(byte[] bytes, int offset, int length) throws IOException {
            delegate.write(bytes, offset, length);
            delegate.flush();
        }

        /**
         * 委托原输出流刷新。
         *
         * @throws IOException 刷新失败
         */
        @Override
        public void flush() throws IOException {
            delegate.flush();
        }

        /**
         * 委托原输出流关闭，保留容器原有行为。
         *
         * @throws IOException 关闭失败
         */
        @Override
        public void close() throws IOException {
            delegate.close();
        }

        /**
         * 返回原输出流的可写状态。
         *
         * @return 是否可继续写入
         */
        @Override
        public boolean isReady() {
            return delegate.isReady();
        }

        /**
         * 委托原输出流注册非阻塞写入监听器。
         *
         * @param listener 写入监听器
         */
        @Override
        public void setWriteListener(WriteListener listener) {
            delegate.setWriteListener(listener);
        }
    }
}
