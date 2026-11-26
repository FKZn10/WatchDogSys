package com.jpmsoft.userProducer.pojo.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginSelfVo {
    /**
     * 我方用户标识
     * 账号/手机/uMdmNo等等 对应Sys_User表字段
     * 缺省 uAccount
     */
    private String currentKey = "uAccount";
    /**
     * 对方用户标识
     * 账号/手机/uMdmNo等等  对应Sys_User表字段
     * 缺省 uAccount
     */
    private String thirdKey = "uAccount";
    /**
     * 目标API地址
     */
    private String targetApiUrl;

    //渠道标识
    private String channelType;
    //平台标识
    private String platform;

}
