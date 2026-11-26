package com.jpmsoft.projectProducer.pojo.response;

import lombok.Data;

@Data
public class EFAResponse {
    private String msg_signature;
    private String timestamp;
    private String nonce;
    private String echostr;
}
