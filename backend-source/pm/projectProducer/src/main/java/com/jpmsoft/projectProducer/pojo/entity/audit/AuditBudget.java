package com.jpmsoft.projectProducer.pojo.entity.audit;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 预算审计
 */
@Data
@TableName("Audit_Budget")
public class AuditBudget {
    @TableId(type = IdType.INPUT)
    private String abId;
    private String pId;
    private String secId;
    private BigDecimal abAmount;
    private String abCreateCid;
    private String abCreateUid;
    private String abCreateUname;
    private Date abCreateTime;
    private Integer abState;
    private Date abStateTime;
    private String abContactName;
    private String abContactTel;
    private String abFileApproval;
    private String abFileEstimate;
    private String abFileBidDoc;
    private String abFileDesign;
    private String abFileOther;
    private String abRemark;
    private String abAuditResult;
    private String teId;
}
