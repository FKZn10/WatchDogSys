package com.jpmsoft.jputils.sms;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.jputils.constants.ApiMsg;
import com.jpmsoft.jputils.dto.SxSmsDto;
import com.jpmsoft.jputils.utils.SHA512Utils;
import com.jpmsoft.jputils.utils.UnicodeUtils;
import com.jpmsoft.jputils.vo.SxResVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * 苏相短信
 *
 * @author: 李文涛 aaron
 * @date: 2022/9/6 10:31
 */
@Slf4j
public class SxSms {

    public static BaseResponse sendSms(SxSmsDto sms) {
        try {
            Map<String, Object> constrMap = setConstr(sms);
            String constr = JacksonUtils.toJSONString(constrMap);
            //jackson转json会将unicode的但斜杠\转换成双斜杠\
            constr = constr.replaceAll("\\\\\\\\", "\\\\");
            PostMethod postMethod = new PostMethod(sms.getSscUrl());
            postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            //参数设置，需要注意的就是里边不能传NULL，要传空字符串
            NameValuePair[] data = {
                    new NameValuePair("zone", checkEmpty(sms.getSscRegion())),
                    new NameValuePair("nonce", checkEmpty(sms.getNonce())),
                    new NameValuePair("timestamp", checkEmpty(sms.getTimestamp())),
                    new NameValuePair("constr", checkEmpty(constr)),
                    new NameValuePair("signature", checkEmpty(getSign(constr, sms.getNonce(), sms.getTimestamp(), sms.getSscRegion(), sms.getSalt())))
            };
            postMethod.setRequestBody(data);
            HttpClient client = new HttpClient();
            int response = client.executeMethod(postMethod); // 执行POST方法
            if (response == HttpStatus.SC_OK) {
                String result = postMethod.getResponseBodyAsString();
                SxResVo res = JacksonUtils.parseObject(result, SxResVo.class);
                if (res.getStatus().equals("succeed"))
                    return BaseResponse.ok();
                else return BaseResponse.fail(ApiMsg.sendSmsError, res);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("苏相短信发送失败：" + ex.getMessage());
        }
        return BaseResponse.fail(ApiMsg.sendSmsError);
    }

    /**
     * SHA512生成signature
     *
     * @param constr
     * @param nonce
     * @param timestamp
     * @param zone
     * @param salt
     * @return
     */
    private static String getSign(String constr, String nonce, String timestamp, String zone, String salt) {
        StringBuilder sb = new StringBuilder();
        sb.append(constr).append(nonce).append(timestamp).append(zone).append(salt);
        return SHA512Utils.SHA512(sb.toString());
    }

    /**
     * constr主体
     *
     * @param sms
     * @return
     */
    private static Map<String, Object> setConstr(SxSmsDto sms) {
        Map<String, Object> constrMap = new HashMap<>();
        constrMap.put("signName", UnicodeUtils.gbEncoding(sms.getSscSign()));
        constrMap.put("templateId", sms.getSscTempCode());
        constrMap.put("phoneNumbers", sms.getPhoneNumber());
        constrMap.put("templateParam", JacksonUtils.parseObject(sms.getTempParam(), Map.class));
        return constrMap;
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
}
