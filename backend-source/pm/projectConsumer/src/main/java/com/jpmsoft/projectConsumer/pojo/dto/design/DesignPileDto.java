package com.jpmsoft.projectConsumer.pojo.dto.design;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 桩基检测
 * dto临时目录
 * Created By:李文涛 aaron
 * Created Time:2022/6/22 13:55
 */
@Data
public class DesignPileDto extends BaseFileRequest implements Serializable {

    private static final long serialVersionUID = -532164700350561470L;

    private String dpId;
    @JsonProperty("pId")
    private String pId;
    private String conId;
    private String secId;
    private String dpSecName;
    private String dpType;
    private String dpPm;
    private Date dpStartTime;
    private Date dpEndTime;
    private String dpContent;
    private Integer state;
    private Date stateTime;
    private String dpCreateUid;
    private String dpCreateUname;
    private Date dpCreateTime;

    @JsonProperty("pName")
    private String pName;
    @JsonProperty("pCode")
    private String pCode;
    private String conName;
    private String conCode;
}
