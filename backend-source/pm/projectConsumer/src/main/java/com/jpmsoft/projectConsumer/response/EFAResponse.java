package com.jpmsoft.projectConsumer.response;

import lombok.Data;

@Data
public class EFAResponse {
    private String msg_signature;
    private String timestamp;
    private String nonce;
    private String echostr;
}
