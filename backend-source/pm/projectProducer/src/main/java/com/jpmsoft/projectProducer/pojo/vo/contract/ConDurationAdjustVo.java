package com.jpmsoft.projectProducer.pojo.vo.contract;

import com.jpmsoft.common.model.ProjectCommonRes;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/11 10:21
 */
@Data
public class ConDurationAdjustVo extends ProjectCommonRes implements Serializable {

    private static final long serialVersionUID = 3933261572546299697L;
    private String id;
    private String createUname;
    private String createUid;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String daReason;
    private String conId;
    private String daSupervisor;
    private Float daAdjust;
    private String daRmark;
    private String daType;
    private String conBId;

    private String conBName;
    private String conName;
    private BigDecimal conMoneyTotal;
    private String conPartyAId;
    private String conPartyBId;
    private String conPartyAName;
    private String conPartyBName;
    private String daSupervisorName;

}
