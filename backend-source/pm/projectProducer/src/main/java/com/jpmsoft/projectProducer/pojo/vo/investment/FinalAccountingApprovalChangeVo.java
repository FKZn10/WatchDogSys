package com.jpmsoft.projectProducer.pojo.vo.investment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class FinalAccountingApprovalChangeVo  extends BaseFileRequest {
    private String id;

//    @JsonProperty("pId")
//    private String pId;

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

    @JsonProperty("faaId")
    private String faaId;

    @JsonProperty("pName")
    private String pName;

    @JsonProperty("cName")
    private String cName;

//    @JsonProperty("cId")
//    private String cId;
}
