package com.jpmsoft.projectProducer.pojo.vo.secInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileResponse;
import lombok.Data;

import java.util.Date;

/**
 * 专家请假申请
 *
 * @Author: 李文涛 aaron
 * @date: 2023/8/1 9:46
 */
@Data
public class SecExpertVacationVO extends BaseFileResponse {


    private String id;
    private String exId;
    private Date sevStartTime;
    private Date sevEndTime;
    private Integer sevDays;
    private Date sevTime;
    private String sevReason;
    private Integer state;
    private Date stateTime;
    private String createUid;
    private String createUname;
    private Date createTime;

    private String exName;
    private String exIdType;
    private String exIdNumber;
    @JsonProperty("pId")
    private String pId;
    private String secId;
    private String srId;
    @JsonProperty("pName")
    private String  pName;
    @JsonProperty("pCode")
    private String  pCode;
    private String  secName;
    private String  secCode;
}
