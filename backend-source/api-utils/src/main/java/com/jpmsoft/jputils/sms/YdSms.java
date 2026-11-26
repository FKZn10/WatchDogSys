package com.jpmsoft.jputils.sms;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.jputils.constants.ApiMsg;
import com.jpmsoft.jputils.dto.ChinaYdSmsDTO;
import com.mascloud.sdkclient.Client;
import lombok.extern.slf4j.Slf4j;


/**
 * 中国移动短信
 * wudz
 */
@Slf4j
public class YdSms {
    public static BaseResponse send(ChinaYdSmsDTO sms){
        Client client = Client.getInstance();
        // 登录地址需另外提供
        boolean isLoggedin = client.login(sms.getUrl(), sms.getUserName(), sms.getUserPwd(), sms.getEcName());
        if(!isLoggedin){
            return BaseResponse.fail(ApiMsg.sendSmsError);  //短信登录验证失败
        }
        // 普通短信   手机号  短信内容  扩展码  优先级  签名编码  消息批次号 是否需要上行
//        int rt = client.sendDSMS(new String[]{sms.getPhone()}, sms.getContent(), "", 1, sms.getSign(), null, true);
        int rt = client.sendDSMS(new String[]{sms.getPhone()}, sms.getContent(), "", 1, sms.getSign(), null, true);
        log.info("移动短信结果状态:{}",rt);

        return rt == 1 ? BaseResponse.ok() :BaseResponse.fail(ApiMsg.sendSmsError);
    }

}
