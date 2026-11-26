package com.jpmsoft.projectProducer.pojo.dto.secInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/1/5 9:15
 */
@Data
public class SecExpertAuditDetailDTO implements Serializable {

    private static final long serialVersionUID = 5279684875457766586L;
    private String id;
    private String exId;
    private String seaId;
    private String keyExpert;
    private String exChangeStatus;
}
