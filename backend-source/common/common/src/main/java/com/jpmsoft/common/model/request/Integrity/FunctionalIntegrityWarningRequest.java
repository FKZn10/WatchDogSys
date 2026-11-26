package com.jpmsoft.common.model.request.Integrity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BasePageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 功能预警流程记录
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FunctionalIntegrityWarningRequest extends BasePageEntity {
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
