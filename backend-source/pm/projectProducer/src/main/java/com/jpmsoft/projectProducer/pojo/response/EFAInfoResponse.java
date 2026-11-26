package com.jpmsoft.projectProducer.pojo.response;

import lombok.Data;

@Data
public class EFAInfoResponse {
    private String payApplyId;
    private String oper;
    private String msg;
    private EFAPayInfoResponse payInfo;
}
