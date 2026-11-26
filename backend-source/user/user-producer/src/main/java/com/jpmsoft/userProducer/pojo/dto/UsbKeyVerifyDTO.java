package com.jpmsoft.userProducer.pojo.dto;

import lombok.Data;

@Data
public class UsbKeyVerifyDTO {
    /**
     *
     */
    private String publicKey;
    /**
     * 明文
     */
    private String plaintext;
    /**
     * 签名
     */
    private String signature;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 设备类型
     * 预留拓展接口兼容不同设备
     */
    private String keyType;
}
