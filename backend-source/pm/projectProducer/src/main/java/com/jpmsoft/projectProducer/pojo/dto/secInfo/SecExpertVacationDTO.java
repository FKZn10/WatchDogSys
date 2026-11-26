package com.jpmsoft.projectProducer.pojo.dto.secInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.util.Date;

/**
 * 专家请假申请
 *
 * @Author: 李文涛 aaron
 * @date: 2023/8/1 9:46
 */
@Data
public class SecExpertVacationDTO extends BaseFileRequest {

    @ProcessFields(type = ProcessFieldEnum.id)
    private String id;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer state;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date stateTime;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String createUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String createUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date createTime;

    private String exName;
    private String exId;
    private Date sevStartTime;
    private Date sevEndTime;
    private Integer sevDays;
    private Date sevTime;
    private String sevReason;
    @JsonProperty("pId")
    private String pId;
    private String secId;
    private String srId;
    @JsonProperty("pName")
    private String pName;
    @JsonProperty("pCode")
    private String pCode;
    private String secName;
    private String secCode;

}
