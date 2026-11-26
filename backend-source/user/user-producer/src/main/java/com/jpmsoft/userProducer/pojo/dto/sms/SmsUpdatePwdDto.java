package com.jpmsoft.userProducer.pojo.dto.sms;

import com.jpmsoft.userProducer.constants.BaseMsg;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 短信验证码修改用户密码
 *
 * @author: 李文涛 aaron
 * @date: 2022/9/1 17:22
 */
@Data
public class SmsUpdatePwdDto implements Serializable {
    @NotBlank(message = BaseMsg.notActPwd)
    private String account;
    @NotBlank(message = BaseMsg.authCodeIsEmpty)
    private String code;
    @NotBlank(message = BaseMsg.authCodePhoneIsEmpty)
    private String phoneNumber;
    @NotBlank(message = BaseMsg.notActPwd)
    private String newPwd;
}
