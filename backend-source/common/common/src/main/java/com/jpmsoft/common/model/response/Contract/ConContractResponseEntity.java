package com.jpmsoft.common.model.response.Contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 合同
 */
@Data
public class ConContractResponseEntity {
    @JsonProperty("pName")
    private String pName;//项目名称
    private String conName;//合同名称
    private String conCode;//合同编号
    private String pCampus;//校区
    private String pType;//项目类型
    private String conType;//合同类型
    private BigDecimal conMoneyTotal;//合同金额
    private String partyBName;//乙方单位
    private Date conDate;//合同签订时间
    private Integer conTotalDuration;//总工期
    private String fsCostCode;//项目经费号
    private Date conStartDate;//开工时间
    private Date completeDate;//验收时间
    private Date endTime;//结算时间
    private BigDecimal auditReport;//结算金额
    private String conCreateUserId;
    private String conCreateUserName;
    private Date conCreateTime;
    private String conApprovalState;
    private Date conApprovalTime;
}
