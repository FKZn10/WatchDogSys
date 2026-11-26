package com.jpmsoft.jputils.sms;

import com.cloud.api.sdk.*;
import com.cloud.api.sdk.client.RequestClient;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.jputils.constants.ApiMsg;
import com.jpmsoft.jputils.dto.CrcSmsDto;
import com.jpmsoft.jputils.vo.CrcSmsVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpStatus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 华润短信发送
 *
 * @Author: 李文涛 aaron
 * @date: 2022/12/16 14:25
 */
@Slf4j
public class CrcSms {

    /**
     * 华润短信发送（单条）
     * 短信配置 accessKey对应华润appId应用id KeySecret对应华润s-crc-token应用令牌
     *
     * @param sms
     * @return
     */
    @Deprecated
    public static BaseResponse sendSms(CrcSmsDto sms) {
        try {
            PostMethod postMethod = new PostMethod(sms.getUrl());
            postMethod.setRequestHeader("Content-Type", "application/json");
            postMethod.addRequestHeader("s-crc-api-id", "crc.jvku.smscommsend");
            postMethod.addRequestHeader("s-crc-app-id", sms.getAccessKey());
            postMethod.addRequestHeader("s-crc-token", sms.getKeySecret());
            postMethod.addRequestHeader("s-crc-stage", "PRD");
            //参数设置，需要注意的就是里边不能传NULL，要传空字符串
            NameValuePair[] data = {
                    new NameValuePair("appId", checkEmpty(sms.getAccessKey())),
                    new NameValuePair("signCode", checkEmpty(sms.getSignName())),
                    new NameValuePair("templateCode", checkEmpty(sms.getTmpCode())),
                    new NameValuePair("phoneNumber", checkEmpty(sms.getPhoneNumber())),
                    new NameValuePair("msg_tplparams2", checkEmpty(sms.getContent())),
                    new NameValuePair("tenantId", checkEmpty(sms.getTenantId())),
                    new NameValuePair("submiter", checkEmpty(sms.getSubmiter()))
            };
            postMethod.setRequestBody(data);
            HttpClient client = new HttpClient();
            int response = client.executeMethod(postMethod); // 执行POST方法
            if (response == HttpStatus.SC_OK) {
                String result = postMethod.getResponseBodyAsString();
                CrcSmsVo res = JacksonUtils.parseObject(result, CrcSmsVo.class);
                if (res != null && ValidateUtil.isNotNull(res.getCode()) && res.getCode() == 200)
                    return BaseResponse.ok();
                else return BaseResponse.fail(ApiMsg.sendSmsError, res);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("华润短信发送失败：" + ex.getMessage());
        }
        return BaseResponse.fail(ApiMsg.sendSmsError);
    }

    /**
     * 校验字符是否为空 空则返回空字符
     *
     * @param str
     * @return
     */
    private static String checkEmpty(String str) {
        if (ValidateUtil.isEmpty(str)) return "";
        return str;
    }


    public static BaseResponse send(CrcSmsDto sms) {
        RequestHeader header = new RequestHeader(sms.getAccessKey(), sms.getKeySecret(), "crc.jvku.smscommsend", RequestEnvironment.PRD);
        header.addHeader("Content-Type", "application/json");
        //传入json格式的服务请求报文
        Map<String, Object> params = new HashMap<>();
        params.put("appId", checkEmpty(sms.getAccessKey()));
        params.put("signCode", checkEmpty(sms.getSignName()));
        params.put("templateCode", checkEmpty(sms.getTmpCode()));
        params.put("phoneNumber", checkEmpty(sms.getPhoneNumber()));
        if (ValidateUtil.isNotEmpty(sms.getContent()))
            params.put("msg_tplparams2", JacksonUtils.parseObject(sms.getContent(), Map.class));
        params.put("tenantId", checkEmpty(sms.getTenantId()));
        params.put("submiter", checkEmpty(sms.getSubmiter()));
        RequestParameter requestParameter = new RequestParameter("/", JacksonUtils.toJSONString(params));
        try {
            HttpResponseBody body = RequestClient.call(sms.getUrl(), RequestMethod.POST, header, requestParameter, sms.getKeySecret(), "");
            CrcSmsVo res = JacksonUtils.parseObject(body.getBody(), CrcSmsVo.class);
            if (res != null && ValidateUtil.isNotNull(res.getCode()) && res.getCode() == 200)
                return BaseResponse.ok(res);
            else return BaseResponse.fail(ApiMsg.sendSmsError, res);
        } catch (IOException ex) {
            return BaseResponse.fail(ApiMsg.sendSmsError, null);
        }
    }

}
