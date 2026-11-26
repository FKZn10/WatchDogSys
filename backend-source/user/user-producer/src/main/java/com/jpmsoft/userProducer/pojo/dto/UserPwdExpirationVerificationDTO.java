package com.jpmsoft.userProducer.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户密码期限校验配置（区分内外部用户）
 *
 * @Author: 李文涛 aaron
 * @date: 2023/5/16 14:05
 */
@Data
public class UserPwdExpirationVerificationDTO implements Serializable {
    /**
     * 密码天数限制 超出当前天数则直接禁用账户
     */
    private Integer outTimeDisabledDays;
    /**
     * 密码天数限制禁用账户对当前用户是否生效
     */
    private boolean outTimeDisabledSwitch;
}
