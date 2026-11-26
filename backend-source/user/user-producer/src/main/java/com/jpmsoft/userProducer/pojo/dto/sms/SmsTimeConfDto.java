package com.jpmsoft.userProducer.pojo.dto.sms;

import lombok.Data;

/**
 * 短信时效以及发送间隔配置
 * 基础配置-> smsCodePrescriptionInterval
 *
 * @author: 李文涛 aaron
 * @date: 2022/9/1 15:09
 */
@Data
public class SmsTimeConfDto {

    /**
     * 验证码时效 单位秒
     * 缺省60
     */
    private int prescription = 60;
    /**
     * 发送间隔 单位秒
     * 缺省60
     */
    private int interval = 60;
}
