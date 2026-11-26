package com.jpmsoft.projectConsumer.pojo.vo.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class FinalAccountingApprovalVo   extends BaseFileRequest {
    private String id;

    @JsonProperty("pId")
    private String pId;
    @JsonProperty("pName")
    private String pName;

    @JsonProperty("cName")
    private String cName;

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
    private List<String> pNameList;
    private List<Integer> stateList;
    private List<BigDecimal> faaApprovalInvestmentList;
    private List<String> createUnameList;

}
