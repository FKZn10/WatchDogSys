package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

//决算批复
@Data
@TableName("Final_Accounting_Approval_Change")
public class FinalAccountingApprovalChange {
    private String id;

    private String pId;

    private Date faaApprovalTime;  //批复日期

    private String faaApprovalUnit;  //决算审批单位
    private BigDecimal faaApprovalInvestment;  //决算投资金额

    private String faaApprovalAuditId;  //审核报告
    private String faaApprovalAuditIds;  //审核报告

    private String faaApprovalAccountsId;  //决算明细表
    private String faaApprovalAccountsIds;  //决算明细表

    private String faaApprovalFileId;  //批复文件
    private String faaApprovalFileIds;  //批复文件

    private String createUname;
    private String createUid;
    private Date createTime;
    private Integer state;
    private Date stateTime;

    private Integer status;

    private String faaId;
}
