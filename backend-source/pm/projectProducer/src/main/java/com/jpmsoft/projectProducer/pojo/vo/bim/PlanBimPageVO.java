package com.jpmsoft.projectProducer.pojo.vo.bim;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 进度计划
 */
@Data
public class PlanBimPageVO implements Serializable {

    private static final long serialVersionUID = 1013016645140323001L;

    /**
     * 计划id
     */
    private String planId;

    /**
     * 计划名称
     */
    private String planName;
    private String pId;
    private String conId;
    private String planDId;

    /**
     * 计划类型
     */
    private String planType;

    /**
     * 计划开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date planStart;

    /**
     * 计划结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date planEnd;
    private String planCreateUid;

    /**
     * 创建人
     */
    private String planCreateUname;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date planCreateTime;
    private Integer planState;
    private String planRemark;
    private String planGantt;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date planStateTime;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date planActualStart;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date planActualEnd;
    private Integer planEnableState;
    private String planEndUid;
    private String planEndUname;
    private Integer planFinishState;

    private String secId;
    private String planPmUid;
    private String planPmUname;
    private String planApmUid;
    private String planApmUname;
    private String planCueUid;
    private String planCueUname;
    private String planSeUid;
    private String planSeUname;
    private String planPartyBId;
    private String planLeadUid;
    private String planLeadUname;
    private String teId;
}
