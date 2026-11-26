package com.jpmsoft.projectProducer.pojo.vo.secInfo;

import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecJGExpertModel;
import lombok.Data;

import java.io.Serializable;


@Data
public class SecJGExpertAuditDetailVO extends SecJGExpertModel implements Serializable {

    private static final long serialVersionUID = 4354475679805672682L;
    private String id;
    private String exId;
    private String seaId;
}
