package com.jpmsoft.userProducer.pojo.dto;

import lombok.Data;

@Data
public class AuditLogSenderDTO {

    private String id;
    private String content;
    private String action;
}
