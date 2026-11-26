package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("Settlement_First_Audit_Change_Log")
public class SettlementFirstAuditChangeLog {
    private String id;
    private String sfaId;
    private Date changeTime;
    private String projectId;
    private String contractId;
    private BigDecimal sfaContractPrice;
    private String sfaSupervisionUnit;
    private BigDecimal sfaSupervisionUnitPrice;
    private String sfaCostConsultantUnit;
    private BigDecimal sfaCostConsultantUnitPrice;
    private BigDecimal sfaSettlementFirstPrice;
    private BigDecimal sfaCorePrice;
    private BigDecimal sfaContractPriceOld;
    private String sfaSupervisionUnitOld;
    private BigDecimal sfaSupervisionUnitPriceOld;
    private String sfaCostConsultantUnitOld;
    private BigDecimal sfaCostConsultantUnitPriceOld;
    private BigDecimal sfaSettlementFirstPriceOld;
    private BigDecimal sfaCorePriceOld;
    private String sfaSettlementFirstDecisionDoc;
    private String sfaSettlementDocUrl;
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
