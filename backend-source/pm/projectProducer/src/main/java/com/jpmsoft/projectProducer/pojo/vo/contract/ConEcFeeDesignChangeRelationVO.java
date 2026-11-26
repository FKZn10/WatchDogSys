package com.jpmsoft.projectProducer.pojo.vo.contract;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 变更费用申报可关联的多合同设计变更。
 */
@Data
public class ConEcFeeDesignChangeRelationVO {

    private String uuid;
    private String projectId;
    private String projectName;
    private String changeName;
    private String changeCode;
    private String ecChangeReasonType;
    private String reason;
    private String content;
    private String remark;
    private BigDecimal applyAmount;
    private String createUid;
    private String createUname;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private Integer state;
    private String ecChangeType;
    private Integer engineerChangeType;
}
