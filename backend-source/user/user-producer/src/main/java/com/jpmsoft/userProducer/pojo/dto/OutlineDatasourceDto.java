package com.jpmsoft.userProducer.pojo.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class OutlineDatasourceDto extends BaseModeRequest implements Serializable {

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
    private String createUid;
    private String createUname;
    private Date createTime;
    private Date updateTime;
    private String odAlias;

    private String oplId;
    private String oslId;

    @JsonProperty("tCodeList")
    private List<String> tCodeList;
}
