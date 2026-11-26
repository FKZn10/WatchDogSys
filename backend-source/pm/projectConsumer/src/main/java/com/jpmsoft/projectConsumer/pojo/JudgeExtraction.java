package com.jpmsoft.projectConsumer.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JudgeExtraction {
    private String id;
    private String pId;
    private String pName;
    private Date bidTime;
    private String bidLocation;
    private String cId;
    private String cName;
    private String contactPerson;
    private String contactPhone;
    private Integer insideCount;
    private String avoidUnit;
    private String remark;
    private String attachment;
    private String attachmentIds;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
}