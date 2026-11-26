package com.jpmsoft.jputils.sms;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.jputils.dto.HwSmsDto;
import com.jpmsoft.jputils.utils.OkHttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * SUBMAIL短信
 * https://www.mysubmail.com/
 *
 * @Author: 李文涛 aaron
 * @date: 2024/8/22 10:52
 */
public class SubMailSms {
    /**
     * submail 短信发送
     *
     * @param sms
     * @return
     */
    public static BaseResponse send(HwSmsDto sms) {
        //请求入参
        Map<String, String> data = new HashMap<>();
        data.put("appid", sms.getAccessKey());
        data.put("signature", sms.getKeySecret());
        data.put("to", sms.getPhoneNumber());
        data.put("content", sms.getContent());
        String res = OkHttpUtil.xwfuPost(sms.getUrl(), data, null);
        if (ValidateUtil.isEmpty(res)) return BaseResponse.fail();
        return BaseResponse.ok(res);
    }
}
