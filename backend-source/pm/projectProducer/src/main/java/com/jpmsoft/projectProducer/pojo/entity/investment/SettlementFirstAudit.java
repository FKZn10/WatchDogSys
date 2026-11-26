package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("Settlement_First_Audit")
public class SettlementFirstAudit {
    @TableId(type = IdType.INPUT)
    private String id;
    private String projectId;
    private String contractId ;
    private BigDecimal sfaContractPrice;
    private String sfaSupervisionUnit;
    private BigDecimal sfaSupervisionUnitPrice;
    private String sfaCostConsultantUnit;
    private BigDecimal sfaCostConsultantUnitPrice;
    private BigDecimal sfaSettlementFirstPrice;
    private BigDecimal sfaCorePrice;
    private String sfaSettlementFirstDecisionDoc;
    private String sfaSettlementDocUrl;
    private Integer delFlag;
    private Integer status;
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
    private String remark;
}
