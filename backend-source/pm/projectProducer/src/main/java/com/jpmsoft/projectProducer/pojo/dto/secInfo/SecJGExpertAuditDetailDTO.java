package com.jpmsoft.projectProducer.pojo.dto.secInfo;

import lombok.Data;

import java.io.Serializable;


@Data
public class SecJGExpertAuditDetailDTO implements Serializable {

    private static final long serialVersionUID = 5279684875457766586L;
    private String id;
    private String exId;
    private String seaId;
}
