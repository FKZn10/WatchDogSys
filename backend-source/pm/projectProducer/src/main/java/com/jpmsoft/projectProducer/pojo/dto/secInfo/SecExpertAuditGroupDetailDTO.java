package com.jpmsoft.projectProducer.pojo.dto.secInfo;

import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertModel;
import lombok.Data;

@Data
public class SecExpertAuditGroupDetailDTO extends SecExpertModel {
    private String id;
    private String exId;
    private String seaId;
    private String keyExpert;
}
