package com.jpmsoft.projectProducer.pojo.request;

import lombok.Data;

@Data
public class AccessTokenRequest {
    private String appId;
    private String timeStamp;
    private String signature;
}
