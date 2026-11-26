package com.jpmsoft.common.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * 文档模板扩展
 *
 * @author tianhelin
 * @date 2026/01/17
 */
@Data
public class SysFileTemplateDtlDTO {
    private String id;
    @JsonProperty("tId")
    private String tId;
    @JsonProperty("tdFlowPage")
    private String tdFlowPage;
    @JsonProperty("tdInfoPage")
    private String tdInfoPage;
    @JsonProperty("tdUpdatePage")
    private String tdUpdatePage;
    @JsonProperty("tdAppFlowpage")
    private String tdAppFlowpage;
    @JsonProperty("tdAppInfopage")
    private String tdAppInfopage;
    @JsonProperty("tdAppUpdatepage")
    private String tdAppUpdatepage;
    @JsonProperty("tdCode")
    private String tdCode;
    @JsonProperty("dId")
    private String dId;
    @JsonProperty("pId")
    private String pId;
    @JsonProperty("pName")
    private String pName;
    @JsonProperty("status")
    private Integer status;
    private Date createTime;
    private Date statusTime;
    private String formId;
    private String listSettings;
    private String otherSettings;
}
