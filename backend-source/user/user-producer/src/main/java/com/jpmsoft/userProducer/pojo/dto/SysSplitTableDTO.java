package com.jpmsoft.userProducer.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/1/3 14:16
 */
@Data
public class SysSplitTableDTO extends BaseModeRequest implements Serializable {

    private static final long serialVersionUID = -7813273629450046161L;

    public String id;
    @JsonProperty("tCode")
    public String tCode;
    public String stTable;
    public String stName;
    public String stRemark;
    public String stCode;
    public Date createTime;
    public String createUid;
    public String createUname;
}
