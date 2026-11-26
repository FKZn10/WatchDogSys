package com.jpmsoft.projectProducer.pojo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PreProjectOrgStructnResponse {

    @JsonProperty("pId")
    private String pId;

    @JsonProperty("pName")
    private String pName;

    private String pCanYanApplyUnitName;  //可研申报单位

    private String pCanYanApprovalUnitName;  //可研审批单位

    private String pUseUnitName;  //使用单位
}
