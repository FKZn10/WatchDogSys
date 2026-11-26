package com.jpmsoft.projectProducer.pojo.vo.secInfo;

import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/1/5 9:15
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SecExpertAuditDetailVO extends SecExpertModel implements Serializable {

    private static final long serialVersionUID = 4354475679805672682L;
    private String id;
    private String exId;
    private String seaId;
    private String keyExpert;
}
