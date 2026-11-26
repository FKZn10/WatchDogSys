package com.jpmsoft.userProducer.pojo.dto;

import lombok.Data;

@Data
public class SelfLoginExchangeDTO {

    //来源系统校验值
    private String sourceValue;
    //当前系统需要核对的字段名
    private String checkKey;
    //渠道标识
    private String channelType;
    //平台标识
    private String platform;
}
