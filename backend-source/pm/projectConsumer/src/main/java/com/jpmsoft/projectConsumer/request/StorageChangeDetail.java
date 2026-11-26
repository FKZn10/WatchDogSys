package com.jpmsoft.projectConsumer.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StorageChangeDetail {
    private String id;
    private String scId; // 关联storage_change表的id
    private String exApplicantId;
    private String exName;
    private String exId;
    private String idNumber;
    @JsonProperty("dId")
    private String dId;
    @JsonProperty("dName")
    private String dName;
    private String exPracticeCode;
    private String exTitle;
    private String changeType;
}
