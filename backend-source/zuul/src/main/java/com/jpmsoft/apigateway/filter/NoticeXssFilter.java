package com.jpmsoft.apigateway.filter;

import com.jpmsoft.common.utils.JacksonUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 通知公告创建接口 XSS 过滤过滤器
 * <p>
 * 针对 /pm/OA/Notice/createOrUpdateOaNotice 与 /pm/OA/Notice/createOaNotice
 * 两个落库接口，在网关层对 nContent（正文）做白名单过滤、对 nTitle（标题）去除全部 HTML 标签，
 * 防止存储型 XSS 数据入库。
 * <p>
 * 说明：本过滤器不依赖第三方库，纯正则过滤，作为网关层的纵深防御（服务端另有 WAF 兜底）。
 *
 * @author apigateway
 */
@Slf4j
public class
NoticeXssFilter extends ZuulFilter {

    /**
     * 需要过滤的接口（网关 context-path 之后的路径）
     */
    private static final String CREATE_OR_UPDATE_PATH = "/pm/OA/Notice/createOrUpdateOaNotice";
    private static final String CREATE_PATH = "/pm/OA/Notice/createOaNotice";

    /**
     * 危险标签（连同标签内容一并去除）：script/style/iframe/object/embed/link/meta/frame/frameset
     */
    private static final Pattern DANGEROUS_TAG_WITH_CONTENT = Pattern.compile(
            "(?is)<(script|style|iframe|object|embed|link|meta|frame|frameset|applet|base|basefont)[^>]*>.*?</\\1>");

    /**
     * 危险自闭合/单标签（无闭合对）
     */
    private static final Pattern DANGEROUS_TAG_SINGLE = Pattern.compile(
            "(?i)<(script|style|iframe|object|embed|link|meta|frame|frameset|applet|base|basefont)[^>]*/?>");

    /**
     * 事件属性 on*（如 onclick、onerror、onload 等）
     */
    private static final Pattern EVENT_ATTRIBUTE = Pattern.compile(
            "(?i)\\son\\w+\\s*=\\s*(\"[^\"]*\"|'[^']*'|[^\\s>]+)");

    /**
     * javascript: 协议（href/src/action 等属性，含带引号/不带引号两种写法）
     */
    private static final Pattern JAVASCRIPT_PROTOCOL = Pattern.compile(
            "(?i)(href|src|action)\\s*=\\s*(\"[^\"]*javascript:[^\"]*\"|'[^']*javascript:[^']*'|[^\\s>]*javascript:[^\\s>]*)");

    /**
     * 标题去标签：匹配任意 HTML 标签
     */
    private static final Pattern ALL_TAG = Pattern.compile("(?is)<[^>]*>");

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        // 排在 AccessFilter(0) 之后执行
        return 5;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        if (ctx == null) {
            return false;
        }
        HttpServletRequest request = ctx.getRequest();
        if (request == null) {
            return false;
        }
        if (!"POST".equalsIgnoreCase(request.getMethod())) {
            return false;
        }
        String uri = request.getRequestURI();
        // 网关可能带 context-path（如 /apis），用 endsWith 兼容「带 /apis 前缀」与「不带前缀」两种形式
        return uri != null && (uri.endsWith(CREATE_OR_UPDATE_PATH) || uri.endsWith(CREATE_PATH));
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        try {
            InputStream in = request.getInputStream();
            String body = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
            if (body == null || body.trim().isEmpty()) {
                return null;
            }
            String filtered = filterBody(body);
            if (filtered == null || filtered.equals(body)) {
                return null;
            }
            final byte[] reqBodyBytes = filtered.getBytes(Charset.forName("UTF-8"));
            // 重写上下文的 HttpServletRequestWrapper
            ctx.setRequest(new HttpServletRequestWrapper(request) {
                @Override
                public ServletInputStream getInputStream() throws IOException {
                    return new ServletInputStreamWrapper(reqBodyBytes);
                }

                @Override
                public int getContentLength() {
                    return reqBodyBytes.length;
                }

                @Override
                public long getContentLengthLong() {
                    return reqBodyBytes.length;
                }
            });
            log.info("通知公告XSS过滤完成，接口: {}", request.getRequestURI());
        } catch (Exception e) {
            // 过滤失败不影响正常请求，原样放行
            log.warn("通知公告XSS过滤异常，原样放行: {}", e.getMessage());
        }
        return null;
    }

    /**
     * 对请求体做 XSS 过滤，支持单对象 JSON 与数组 JSON（批量接口）
     *
     * @param body 原始请求体
     * @return 过滤后的请求体；无法解析时返回 null 表示不做处理
     */
    private String filterBody(String body) {
        String trimmed = body.trim();
        // 批量接口：JSON 数组
        if (trimmed.startsWith("[")) {
            List<Map<String, Object>> list = JacksonUtils.parseObject(trimmed, List.class);
            if (list == null) {
                return null;
            }
            boolean changed = false;
            for (Map<String, Object> item : list) {
                if (item == null) {
                    continue;
                }
                changed |= sanitizeMap(item);
            }
            if (!changed) {
                return null;
            }
            return JacksonUtils.toJSONString(list);
        }
        // 单条接口：JSON 对象
        if (trimmed.startsWith("{")) {
            Map<String, Object> map = JacksonUtils.parseObject(trimmed, Map.class);
            if (map == null) {
                return null;
            }
            if (!sanitizeMap(map)) {
                return null;
            }
            return JacksonUtils.toJSONString(map);
        }
        return null;
    }

    /**
     * 对 Map 中的 nContent / nTitle 字段做过滤
     *
     * @param map 请求体解析出的 Map
     * @return 是否有字段被修改
     */
    private boolean sanitizeMap(Map<String, Object> map) {
        boolean changed = false;
        if (map.containsKey("nContent")) {
            Object raw = map.get("nContent");
            if (raw instanceof String) {
                String filtered = filterContent((String) raw);
                if (!filtered.equals(raw)) {
                    map.put("nContent", filtered);
                    changed = true;
                }
            }
        }
        if (map.containsKey("nTitle")) {
            Object raw = map.get("nTitle");
            if (raw instanceof String) {
                String cleaned = cleanTitle((String) raw);
                if (!cleaned.equals(raw)) {
                    map.put("nTitle", cleaned);
                    changed = true;
                }
            }
        }
        return changed;
    }

    /**
     * 正文富文本白名单过滤：
     * 1. 去除危险标签（script/iframe/object/embed 等，连同内容）；
     * 2. 去除所有 on* 事件属性；
     * 3. 去除 javascript: 协议。
     * 保留 <p>/<b>/<h1>/<img src> 等安全标签。
     *
     * @param content 原始正文
     * @return 过滤后的正文
     */
    private String filterContent(String content) {
        if (content == null || content.isEmpty()) {
            return content;
        }
        String result = content;
        // 1. 去除危险标签（含内容）
        result = DANGEROUS_TAG_WITH_CONTENT.matcher(result).replaceAll("");
        // 2. 去除危险单标签
        result = DANGEROUS_TAG_SINGLE.matcher(result).replaceAll("");
        // 3. 去除事件属性
        result = EVENT_ATTRIBUTE.matcher(result).replaceAll("");
        // 4. 去除 javascript: 协议
        result = JAVASCRIPT_PROTOCOL.matcher(result).replaceAll("$1=\"#\"");
        return result;
    }

    /**
     * 标题去全部 HTML 标签，仅保留纯文本
     *
     * @param title 原始标题
     * @return 纯文本标题
     */
    private String cleanTitle(String title) {
        if (title == null || title.isEmpty()) {
            return title;
        }
        return ALL_TAG.matcher(title).replaceAll("");
    }
}
