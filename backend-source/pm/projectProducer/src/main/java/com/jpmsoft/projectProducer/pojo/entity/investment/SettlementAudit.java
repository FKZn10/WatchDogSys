package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("Settlement_Audit")
public class SettlementAudit implements Serializable {
    private static final long serialVersionUID = -5293500241447865493L;

    @TableId(type = IdType.INPUT)
    private String id;
    private String projectId;
    private String projectName;
    private Integer auditContractNum;
    private BigDecimal auditContractMoney;
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
    private Integer delFlag;
    private Integer status;
}
