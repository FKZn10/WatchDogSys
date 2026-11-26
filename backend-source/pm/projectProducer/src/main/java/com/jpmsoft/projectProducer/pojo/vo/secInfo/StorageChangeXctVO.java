package com.jpmsoft.projectProducer.pojo.vo.secInfo;

import lombok.Data;

import java.util.Date;

@Data
public class StorageChangeXctVO {

    private String id;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String scCode;
    private Integer expertCount;
}
