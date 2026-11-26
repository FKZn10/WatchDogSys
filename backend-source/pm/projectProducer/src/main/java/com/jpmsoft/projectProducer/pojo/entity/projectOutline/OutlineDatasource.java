package com.jpmsoft.projectProducer.pojo.entity.projectOutline;

import lombok.Data;

import java.io.Serializable;

@Data
public class OutlineDatasource implements Serializable {
    private String id;
    private String odName;
    private String odType;
    private String odSource;
    private String odStatus;
    private String odRemark;
    private String odAddress;
    private String odMethod;
    private String odBody;
    private String odDataType;
    private String odAlias;
}
