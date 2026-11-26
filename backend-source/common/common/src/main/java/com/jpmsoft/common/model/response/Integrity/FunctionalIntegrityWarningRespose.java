package com.jpmsoft.common.model.response.Integrity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * 功能预警流程记录
 */
@Data
public class FunctionalIntegrityWarningRespose {
    private String id;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String triggerField;
    private String triggerFieldValue;
    @Deprecated
    @JsonProperty("tCode")
    private String tCode;
    private String warningForm;
    private String situationDescription;
    private String relatedDocuments;
    private String auditResults;
    private String wriId;
    private String businessCode;
}
