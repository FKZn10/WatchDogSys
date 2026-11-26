package com.jpmsoft.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
@Data
public class TaskProcessPrintResponse {
    private String tpUser;
    private String tpExplain;
    private String tpResult;
    private String actName;
    private String uSignature;
    private Date tpDoTime;

    @JsonProperty("uName")
    private String uName;

    private String imgUrl;
}
