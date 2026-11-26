package com.jpmsoft.projectProducer.pojo.response;

import lombok.Data;

@Data
public class AccessTokenData {
    private String validTime;
	private String accessToken;
	private String appId;
}
