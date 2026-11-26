package com.jpmsoft.projectProducer.pojo.entity.audit;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 结算审计
 */
@Data
@TableName("Audit_Settlement")
public class AuditSettlement {

    @TableId(type = IdType.INPUT)
    private String asId;
    private String pId;
    private String secId;
    private String conId;
    private BigDecimal asSettlementPrice;
    private String asCreateCid;
    private String asContactName;
    private String asContactTel;
    private String asCreateUid;
    private String asCreateUname;
    private Date asCreateTime;
    private Integer asState;
    private Date asStateTime;
    private String asFileBidDoc;
    private String asFileTenderDoc;
    private String asFileContract;
    private String asFileBidWin;
    private String asFileCompletion;
    private String asFileStatement;
    private String asFileCalculation;
    private String asFileOther;
    private String asRemark;
    private Date asStartDate;
    private Date asEndDate;
    private String asAuditResult;
    private String asType;
    private Integer asNeedMoney;
    private String asAssetFile;
    private String teId;

    private BigDecimal asConMoneyTotal;
    private BigDecimal asReviewTheAmount;
    private Date asDateOfReferral;
    private BigDecimal asAuditedAmount;
    private Date asDateOfApproval;
    private BigDecimal asDeductionAmount;
    private Float asReductionRate;
    private String asAuditUnit;
    private String asContractor;
}
