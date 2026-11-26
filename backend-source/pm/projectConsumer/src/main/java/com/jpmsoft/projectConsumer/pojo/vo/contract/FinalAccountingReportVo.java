package com.jpmsoft.projectConsumer.pojo.vo.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class FinalAccountingReportVo    extends BaseFileRequest {

    private String id;

    @JsonProperty("pId")
    private String pId;

    @JsonProperty("pName")
    private String pName;

    @JsonProperty("cName")
    private String cName;

    private BigDecimal approvedBudgetAmount;  //概算批复金额

    private BigDecimal amountSubmitted;  //送审金额

    private String createUname;
    private String createUid;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private Integer status;

    private List<FinalAccountingReportDetailVo> detailList;
    private List<String> pNameList;
    private List<Integer> stateList;
    private List<BigDecimal> approvedBudgetAmountList;
    private List<BigDecimal> auditContractMoneyList;
    private List<String> createUnameList;

    private List<String> cNameList;
}
