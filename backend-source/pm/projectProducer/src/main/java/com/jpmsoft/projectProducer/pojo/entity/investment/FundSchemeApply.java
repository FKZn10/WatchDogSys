package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 资金计划申请
 */
@Data
@TableName("Fund_Scheme_Apply")
public class FundSchemeApply {

    @TableId(type = IdType.INPUT)
    private String fsaId;
    private String pId;
    private BigDecimal fsaBudgetApproval;
    private BigDecimal fsaHaveApplied;
    private BigDecimal fsaCurrentApplied;
    private BigDecimal fsaRemaining;
    private String fsaApplyDepartment;
    private String fsaApplyBatch;
    private Date fsaApplyDate;
    private Date fsaUseDate;
    private String fsaCreateUid;
    private String fsaCreateUname;
    private Date fsaCreateTime;
    private Integer fsaState;
    private Date fsaStateTime;
    private String teId;
}
