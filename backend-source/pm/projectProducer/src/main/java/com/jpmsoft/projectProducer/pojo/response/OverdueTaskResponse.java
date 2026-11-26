package com.jpmsoft.projectProducer.pojo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OverdueTaskResponse {
    @JsonProperty("pId")
    private String pId;
    @JsonProperty("pName")
    private String pName;
    private String taskText;
    private String gsSection;
    private String taskType;
    private Date planStart;
    private Date planEnd;
    private Date taskStartDate;
    private Date taskEndDate;
    private Date taskBeginTime;
    private Date taskFinishTime;
    private String taskImportance;
    private String createUname;
    private Date createTime;
    private String gtoExplanation;

    private String id;
    private String createUid;
    private Integer state;
    private Date stateTime;
    private String taskId;
}
