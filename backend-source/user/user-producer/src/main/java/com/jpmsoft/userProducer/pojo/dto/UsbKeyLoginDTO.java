package com.jpmsoft.userProducer.pojo.dto;

import lombok.Data;

@Data
public class UsbKeyLoginDTO {

    /**
     * 设备中读取的用户名
     */
    private String account;
    /**
     * 设备唯一标识
     */
    private String keyId;
    private String passWord;
    private String authCode;
}
