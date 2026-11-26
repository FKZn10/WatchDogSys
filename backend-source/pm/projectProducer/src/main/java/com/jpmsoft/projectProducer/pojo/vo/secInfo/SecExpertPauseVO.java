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
public class SecExpertPauseVO extends BaseFileResponse {


    private String id;
    private String exId;
    private Date sepStartTime;
    private Date sepEndTime;
    private Integer sepDays;
    private Date sepTime;
    private String sepReason;
    private Integer state;
    private Date stateTime;
    private String createUid;
    private String createUname;
    private Date createTime;

    private String exName;
    private String exIdType;
    private String exIdNumber;
    /**
     * 当年是否已禁用
     */
    private String sepDisabledYear;
    private String exPlaintextNum;
    private Integer exAge;
    @JsonProperty("cName")
    private String cName;
    @JsonProperty("cAbbName")
    private String cAbbName;
    @JsonProperty("cNameHistory")
    private String cNameHistory;

    private Integer sepDaysYear;

    private Integer year;
}
