package com.jpmsoft.projectProducer.pojo.dto.investment;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OperationRecordDTO {
    private String id;
    private String recordId;
    private Date orTime;
    private String orOperator;
    private String orOperatorName;
    private String orType;
    private String orStatusOld;
    private String orStatusNow;
    private String orRemarks;
    private List<String> roles;
}