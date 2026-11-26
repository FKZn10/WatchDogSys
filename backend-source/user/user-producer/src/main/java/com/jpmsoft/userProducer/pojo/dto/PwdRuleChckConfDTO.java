package com.jpmsoft.userProducer.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 密码校验规则配置
 *
 * @Author: 李文涛 aaron
 * @date: 2023/5/26 13:35
 */
@Data
public class PwdRuleChckConfDTO implements Serializable {

    private static final long serialVersionUID = -425269348936034028L;
    /**
     * 密码最小长度
     */
    private int minLen = 8;
    /**
     * 密码最大长度
     */
    private int maxLen = 16;
    /**
     * 密码种类数
     * 大写 小写 数值 符号
     */
    private int charType = 4;
    /**
     * 校验密码是否包含账户名 开启则校验
     */
    private boolean containsAccount = true;
    /**
     * 校验密码是否包含用户名 开启则校验
     */
    private boolean containsName = true;

    /**
     * 校验密码是否包含三个及以上连续的数值、字符、键盘连续按键
     */
    private boolean continuous = true;
    /**
     * 是否校验密码库
     */
    private boolean checkLib = true;
}
