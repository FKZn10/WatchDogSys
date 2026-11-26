package com.jpmsoft.userProducer.pojo.dto.sms;

import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.pojo.dto.UserDto;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/30 14:13
 */
@Data
public class UserSmsLoginDto extends UserDto {
    //web 或者 app
    @NotBlank(message = BaseMsg.loginTypeIsNull)
    @Pattern(regexp = "web|app", message = BaseMsg.uknowLoginType)
    private String loginType;
    @NotBlank(message = BaseMsg.authCodePhoneIsEmpty)
    @Pattern(regexp = "^[1]\\d{10}$", message = BaseMsg.phoneNumIsError)
    private String phoneNumber;
    @NotBlank(message = BaseMsg.authCodeIsEmpty)
    private String code;
}
