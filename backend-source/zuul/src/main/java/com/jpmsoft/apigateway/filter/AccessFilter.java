package com.jpmsoft.apigateway.filter;


import com.jpmsoft.apigateway.base.BaseResponse;
import com.jpmsoft.apigateway.utils.RedisUtils;
import com.jpmsoft.apigateway.utils.TxtUtil;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class AccessFilter extends ZuulFilter {

    @Autowired
    private RedisUtils redisUtils;

    public final static String tokenInvalid = "访问令牌失效，请重新登录！";

    /**
     * 不需要token令牌的接口
     */
    private final static List<String> untoken_list = TxtUtil.readTxtFile("InterfaceFilter/untoken_list.txt", "UTF-8");

    /**
     * 未强制修改密码仅能访问的接口
     */
    private final static List<String> only_interface = Arrays.asList("/v1/OrganizationManage/updateUesrPassword", "/v1/OrganizationManage/updateUserPwd"
            , "/v1/smsSend/sendCheckSms", "/v1/SystemManage/smsCheck");

    /**
     * 不记最后操作时间的接口
     * 可能是前端轮询调用
     */
    private final static List<String> Not_Action = TxtUtil.readTxtFile("InterfaceFilter/Not_Action.txt", "UTF-8");
    /**
     * create接口名以外的需要防抖的接口
     */
    private final static List<String> preventResub = TxtUtil.readTxtFile("InterfaceFilter/resubmit.txt", "UTF-8");

    /**
     * 防抖接口白名单
     */
    private final static List<String> resubWhitelist = TxtUtil.readTxtFile("InterfaceFilter/resubWhitelist.txt", "UTF-8");

    /**
     * filterType 返回过滤器类型
     * 他决定了过滤器在请求的哪个生命周期中执行。这里定义为pre，代表会在请求被路由前执行。
     * pre:请求执行之前filter
     * route: 处理请求，进行路由
     * post: 请求处理完成后执行的filter
     * error:出现错误时执行的filter
     *
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * filterOrder 返回过滤器的执行顺序
     * 当请求在一个阶段有多个过滤器是，需要根据该方法的返回值来依次执行
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * shouldFilter 判断该过滤器是否需要被执行
     * 这里直接返回true，表示该过滤器对所有请求都会生效。
     * 实际运用中我们可以利用该函数指定过滤器的有效范围
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器的具体逻辑
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        try {
            HttpServletRequest request = ctx.getRequest();
            ctx.getResponse().setContentType("text/html;charset=UTF-8");
            ctx.getCurrentContext().set("startTime", System.currentTimeMillis());
            String token = request.getHeader("token");
            String reqUrl = request.getRequestURL().toString();
            log.info("当前请求的Origin域:{}", request.getHeader("Origin"));
            log.info("当前请求url:{}", reqUrl);
            //校验token
            if (checkArrayList(untoken_list, reqUrl)) {
                log.info("当前请求token:{}", token);
                //具体逻辑视需求而定
                if (StringUtils.isEmpty(token)) {
                    checkToken(ctx, HttpStatus.SC_UNAUTHORIZED, tokenInvalid);
                    return null;
                } else {
                    if (!GenerateUUID.validToken(token)) {
                        checkToken(ctx, HttpStatus.SC_UNAUTHORIZED, tokenInvalid);
                        return null;
                    }
                    if (redisUtils.get(token) == null) {
                        checkToken(ctx, HttpStatus.SC_UNAUTHORIZED, tokenInvalid);
                        return null;
                    }
                }
                //更新用户最后操作时间。用于用户超时计算
                if (checkArrayList(Not_Action, reqUrl)) {
                    redisUtils.set("lastActionTime_" + token, date2String(new Date()));
                }
                //验证用户是否首次登录
                Object firstLogin = redisUtils.get("firstLogin_" + token);
                LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
                if (firstLogin == null || user == null) {
                    checkToken(ctx, HttpStatus.SC_UNAUTHORIZED, tokenInvalid);
                    return null;
                }
                if (Integer.parseInt(firstLogin.toString()) == 1 //待强制修改密码
                ||(ValidateUtil.isNotNull(user.getUCheckSms()) && user.getUCheckSms() == 1) //需要执行短信验证
                ) {
                    if (checkArrayList(only_interface, reqUrl)) {
                        checkToken(ctx, "无权操作！");
                        return null;
                    }
                }
                //region接口防抖
                String[] reqs = reqUrl.split("/");
                String method = reqs[reqs.length - 1];
                if ((method.toLowerCase(Locale.ENGLISH).startsWith("create") || !checkArrayList(preventResub, method)) && checkArrayList(resubWhitelist, reqUrl)) {
                    //所有create接口
                    Object req = redisUtils.get(token + method);
                    if (req != null) {
                        checkToken(ctx, "请勿重复操作！");
                        return null;
                    } else {
                        //同一token 5秒内无法调用同一个create接口
                        redisUtils.set(token + method, "lock", 5);
                    }
                }
                //endregion
            }
            //文件上传等无校验入参
            //Boolean checkParams = FileWhiteList.contains(reqUrl);
            //过滤特殊符号
//            if (!checkParams)
//                filterSymbol(ctx, request);
            filterListSort(ctx, request);
        } catch (Exception e) {
            e.printStackTrace();
            routeBlocking(ctx);
            ctx.setResponseBody(JacksonUtils.toJSONString(new BaseResponse("网关路由转化异常！")));
            log.warn("网关路由转化异常");
        }
        log.info("放行");
        return null;
    }

    /**
     * 对排序字段sortField过滤
     *
     * @param ctx
     * @param request
     */
    private void filterListSort(RequestContext ctx, HttpServletRequest request) {
        String req = request.getMethod();
        try {
            if (req.equalsIgnoreCase("post")) {
                String reqUrl = request.getRequestURL().toString();
                String[] reqs = reqUrl.split("/");
                String method = reqs[reqs.length - 1].toUpperCase();
                if (method.startsWith("GET") && method.endsWith("LIST")) { //post提交的get***list接口
                    // 获取请求的输入流
                    InputStream in = request.getInputStream();
                    String body = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
                    // 如果body为空初始化为空json
                    if (StringUtils.isEmpty(body)) {
                        body = "{}";
                    }
                    if (!StringUtils.isEmpty(body)) {
                        Map data = JacksonUtils.parseObject(body, Map.class);
                        String sortFiled = data.containsKey("sortFiled") ? data.get("sortFiled") + "" : null;
                        String sortSideFiled = data.containsKey("sortSideFiled") ? data.get("sortSideFiled") + "" : null;
                        if (ValidateUtil.isNotEmpty(sortFiled) || ValidateUtil.isNotEmpty(sortSideFiled)) {
                            if (regTszf(sortFiled)) data.put("sortFiled", null);
                            if (regTszf(sortSideFiled)) data.put("sortSideFiled", null);
                            final byte[] reqBodyBytes = JacksonUtils.toJSONString(data).getBytes();
                            // 重写上下文的HttpServletRequestWrapper
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
                        }
                    }
                }
            }
        } catch (Exception ex) {

        }
    }


    private boolean regTszf(String value) {
        if (ValidateUtil.isEmpty(value)) return true;
        String regEx = ".*[\\s`~!@#$%^&*()+=Ø|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\\\]+.*";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(value);
        return m.matches();
    }

    /**
     * 过滤入参 将特殊符号单引号替换成空字符
     *
     * @param ctx
     * @param request
     */
    private void filterSymbol(RequestContext ctx, HttpServletRequest request) {
        try {
            String method = request.getMethod();
            String reqUrl = request.getRequestURL().toString();
            //过滤入参符号
            if ("GET".equals(method)) {
                // 获取请求参数name
                Map<String, List<String>> reqParams = ctx.getRequestQueryParams();
                if (reqParams != null) {
                    String str = JacksonUtils.toJSONString(reqParams);
                    if (str.contains("'")) {
//                        str = str.replaceAll("'", URLEncoder.encode("'", "UTF-8"));
                        str = str.replaceAll("'", "");
                        Map<String, List<String>> newParams = JacksonUtils.parseObject(str, Map.class);
                        log.info("GET接口的请求地址:" + reqUrl + " 替换后参数： " + str);
                        ctx.setRequestQueryParams(newParams);
                    }
                }
            }// post和put需重写HttpServletRequestWrapper
            else if ("POST".equals(method)) {
                // 获取请求的输入流
                InputStream in = request.getInputStream();
                String body = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
                // 如果body为空初始化为空json
                if (StringUtils.isEmpty(body)) {
                    body = "{}";
                }
                if (!StringUtils.isEmpty(body)) {
                    if (body.contains("'")) {
                        body = body.replaceAll("'", "");
                        // 转化成json
                        log.info("POST接口请求地址:" + reqUrl + " 参数： " + body);
                        final byte[] reqBodyBytes = body.getBytes();
                        // 重写上下文的HttpServletRequestWrapper
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
                    }
                }
            }

        } catch (Exception ex) {

        }
    }

    private void checkToken(RequestContext ctx, String msg) {
        routeBlocking(ctx);
        BaseResponse res = new BaseResponse(msg);
        ctx.setResponseBody(JacksonUtils.toJSONString(res));
    }

    private void checkToken(RequestContext ctx, int httpCode, String msg) {
        routeBlocking(ctx);
        ctx.setResponseBody(JacksonUtils.toJSONString(BaseResponse.fail(httpCode, msg)));
    }


    // 这里我们通过RequestContext.setSendZuulResponse(false)让zuul过来请求，不对其进行路由
    // 然后通过RequestContext.setResponseStatusCode()设置了返回的错误码
    private void routeBlocking(RequestContext ctx) {
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(200);
    }

    private Boolean checkArrayList(List<String> list, String url) {
        boolean isFile = true;
        for (String str : list) {
            if (url.contains(str)) {
                isFile = false;
                break;
            }
        }
        return isFile;
    }

    private String date2String(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    @Deprecated
    private Boolean checkUrlList(List<String> list, String url) {
        Boolean isFile = true;
        for (String str : list) {
            if (url.indexOf(str) != -1) {
                isFile = false;
                break;
            }
        }
        return isFile;
    }

}

