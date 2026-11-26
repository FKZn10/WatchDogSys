package com.jpmsoft.apigateway.filter;


import com.jpmsoft.apigateway.base.BaseResponse;
import com.jpmsoft.apigateway.base.HttpResMsgEnum;
import com.jpmsoft.apigateway.filter.whiteList.FileWhiteList;
import com.jpmsoft.apigateway.filter.whiteList.NoLogWhiteList;
import com.jpmsoft.apigateway.pojo.SysInterface;
import com.jpmsoft.apigateway.rabbitMQ.Sender;
import com.jpmsoft.apigateway.utils.RedisUtils;
import com.jpmsoft.apigateway.utils.TxtUtil;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class ReturnFilter extends ZuulFilter {

    @Autowired
    private Sender sender;
    @Autowired
    private RedisUtils redisUtils;

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
        return "post";
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
     * 不记入操作日志的接口
     * 打开Swagger页面的接口
     * 操作日志相关的接口
     */
    private final static List<String> url_list = Arrays.asList("/api/user", "/pm/pm", "/jpbpm/bpm", "/fileServer/file",
            "v1/SystemManage/GetLoggerInfoList", "v1/SystemManage/DeleteLoggerInfo", "v1/SystemManage/CreateLoggerInfo", "/api/v1/OrganizationManage/checkUserLoginTimeOut",
            "/baseInfo/getCockpitDataBySec", "/baseInfo/getCockpitDataByProject", "/baseInfo/getProjectSecImgById");

//    /**
//     * 相关接口不记录响应参数
//     */
//    private final static List<String> not_params = Arrays.asList("/get", "/GET", "/Get", "/routes");

    /**
     * 没有返回体的接口
     * 例如第三方登录重定向
     */
    private final static List<String> not_res = Arrays.asList("/dock/login/thirdLogin", "/jimureport/jmreport/", "/gmsso/cas", "/jpmDock/sso");

    /**
     * 过滤方法名为get开头的接口 不插入日志表
     */
    @Value("${rabbit.sendMessage.filterGet}")
    private Boolean filterGet;
    private final static List<String> filterGetList = TxtUtil.readTxtFile("InterfaceFilter/notInlog.txt", "UTF-8");


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
            Long startTime = (long) ctx.getCurrentContext().get("startTime");
            String token = request.getHeader("token");
            HttpServletResponse response = ctx.getResponse();
            //error路由  内部转发错误导致
            String reqUrl = request.getRequestURL().toString();
            Boolean flag = NoLogWhiteList.contains(reqUrl);
            Boolean haveRes = checkUrlList(not_res, reqUrl);
            Boolean getReq = filterGet ? checkUrlList(filterGetList, reqUrl) : true;
            if (!flag && haveRes && getReq&&interfaceEnableLog(reqUrl)) {
                //region 获取req body
                String data = "{}";
                if (!FileWhiteList.contains(reqUrl)) {//文件上传下载不记录请求参数
                    if ("POST".equalsIgnoreCase(request.getMethod())) {
                        data = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
                    } else if ("GET".equalsIgnoreCase(request.getMethod())) {
                        Map<String, Object> map = new HashMap<>();
                        String key, name;
                        Enumeration<String> enumeration = request.getParameterNames();
                        while (enumeration.hasMoreElements()) {
                            key = enumeration.nextElement();
                            name = request.getParameter(key);
                            map.put(key, name);
                        }
                        data = JacksonUtils.toJSONString(map);
                    }
                }
                //endregion

                String userIpAd = getIPAddress(request);
                String addr = "127.0.0.1";//获得本机IP(部分项目服务器不允许获取本机IP)
                log.info("请求方式 {} URL {} 耗时 {}", request.getMethod(), request.getRequestURL().toString(), (System.currentTimeMillis() - startTime));
                log.info("响应结果 {} 请求参数 {} 响应参数 {} IP {} 服务器IP {}", response.getStatus(), data, "", userIpAd, addr);
                Long currentTime = System.currentTimeMillis();
                Map<String, Object> mqData = new HashMap<>();
                mqData.put("method", request.getMethod());
                mqData.put("url", reqUrl);
                mqData.put("requestTime", startTime);
                mqData.put("responseTime", currentTime);
                mqData.put("time", (currentTime - startTime));
                mqData.put("status", response.getStatus());
                mqData.put("requestData", data);
                mqData.put("userIp", userIpAd);
                mqData.put("serverIp", addr);
                mqData.put("userId", redisUtils.get(request.getHeader("token")));
                mqData.put("actionEnd",actionEnd(token));

                //region 获取res body
//                if (!FileWhiteList.contains(reqUrl)) {
//                    String body = "{}";
//                    InputStream stream = RequestContext.getCurrentContext().getResponseDataStream();
//                    if (ValidateUtil.isNotNull(stream)) {
//                        byte[] bytes;
//                        if (RequestContext.getCurrentContext().getResponseGZipped()) {
//                            GZIPInputStream gzipInputStream = new GZIPInputStream(stream);
//                            bytes = StreamUtils.copyToByteArray(gzipInputStream);
//                        } else {
//                            bytes = StreamUtils.copyToByteArray(stream);
//                        }
//                        body = new String(bytes, "UTF8");
//                    } else
//                        body = RequestContext.getCurrentContext().getResponseBody();
//                    RequestContext.getCurrentContext().setResponseBody(body);
//                    mqData.put("L_Content", body);
//                }
                //endregion
                sender.send(JacksonUtils.toJSONString(mqData));
            }

            if (haveRes) {
                if (response.getStatus() != HttpStatus.SC_OK && response.getStatus() != HttpStatus.SC_PARTIAL_CONTENT) {
                    //拦截所有非200响应码 请求统一返回
                    log.error(HttpResMsgEnum.getMsg(response.getStatus()));
                    BaseResponse baseResponse = new BaseResponse(HttpResMsgEnum.getMsg(response.getStatus()));
                    ctx.setResponseBody(JacksonUtils.toJSONString(baseResponse));
                    ctx.setResponseStatusCode(200);
                    ctx.setSendZuulResponse(false);
                }
            }
            //response.setHeader("Cache-Control", "no-cache");
        } catch (Exception e) {
            log.error("网关响应失败！");
            e.printStackTrace();
            routeBlocking(ctx);
        }
        log.info("响应拦截结束时间");
        return null;
    }

    /**
     * 根据token判断当前操作用户为那个端在操作
     * @param token
     * @return
     */
    private String  actionEnd(String token)
    {
        if (ValidateUtil.isNotEmpty(token)) {
            String uId = redisUtils.get(token) + "";
            if (ValidateUtil.isNotEmpty(uId)) {
                Object webToken = redisUtils.get(uId);
                if (webToken != null && webToken.equals(token)) return "web";
                Object appOldToken = redisUtils.get(uId + "app");
                //app端token
                if (appOldToken != null && appOldToken.equals(token)) return "app";
                Object backOldToken = redisUtils.get(uId + "back");
                //后台管理端token
                if (backOldToken != null && backOldToken.equals(token)) return "web-admin";
            }
        }
        return "unknown";
    }

    private Boolean checkUrlList(List<String> list, String url) {
        boolean isFile = true;
        for (String str : list) {
            if (url.indexOf(str) != -1) {
                isFile = false;
                break;
            }
        }
        return isFile;
    }

    // 这里我们通过RequestContext.setSendZuulResponse(false)让zuul过来请求，不对其进行路由
    // 然后通过RequestContext.setResponseStatusCode()设置了返回的错误码
    private void routeBlocking(RequestContext ctx) {
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(200);
        ctx.setResponseBody(JacksonUtils.toJSONString(new BaseResponse("网关响应失败！")));
    }

    private static String getIPAddress(HttpServletRequest request) {
        String ip = null;    //X-Forwarded-For：Squid 服务代理
        String ipAddresses = request.getHeader("X-Forwarded-For");
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {        //Proxy-Client-IP：apache 服务代理
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {        //WL-Proxy-Client-IP：weblogic 服务代理
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {        //HTTP_CLIENT_IP：有些代理服务器
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {        //X-Real-IP：nginx服务代理
            ipAddresses = request.getHeader("X-Real-IP");
        }    //有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (ipAddresses != null && ipAddresses.length() != 0) {
            ip = ipAddresses.split(",")[0];
        }    //还是不能获取到，最后再通过request.getRemoteAddr();获取
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 接口配置配置了禁用日志或启用日志
     * @param url
     * @return
     */
    private boolean interfaceEnableLog(String url)
    {
        Object json=redisUtils.get("sys_interface_list");
        if (ValidateUtil.isNotNull(json))
        {
            List<SysInterface> ifs=JacksonUtils.parseArray(json.toString(),SysInterface.class);
            if (!CollectionUtils.isEmpty(ifs))
            {
                url=xgFiter(url.replaceAll("\\\\","/"));
                String finalUrl = url;
                List<SysInterface> fs=ifs.stream().filter(f->
                        ValidateUtil.isNotEmpty(f.getIfRecord())
                                &&ValidateUtil.isNotEmpty(f.getIfUrl())
                                && finalUrl.contains(f.getIfUrl())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(fs))
                {
                    if (fs.stream().findFirst().get().getIfRecord().equalsIgnoreCase("true"))
                        return true;
                    else return false;
                }
            }
        }
        return false;
    }

    private String xgFiter(String url)
    {
        url=url.replaceAll("//","/");
        if (url.contains("//")) xgFiter(url);
        return url;
    }
}

