package com.jpmsoft.projectProducer.pojo.vo.fundPlan;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/17 15:39
 */
@Data
public class ProjectFundCollectionDetailsVo implements Serializable {

    private static final long serialVersionUID = 997203895711811587L;
    @JsonProperty("pId")
    private String pId;
    //项目名
    @JsonProperty("pName")
    private String pName;
    @JsonProperty("pUseUnit")
    private String pUseUnit;
    @JsonProperty("pBuildModel")
    private String pBuildModel;
    @JsonProperty("pType")
    private String pType;
    @JsonProperty("pImpCompany")
    private String pImpCompany;
    //主管单位
    @JsonProperty("pConstructionUnitName")
    private String pConstructionUnitName;
    //主管部门
    @JsonProperty("pCompetentDepartmentName")
    private String pCompetentDepartmentName;
    //付款金额
    private BigDecimal fcdMoney = BigDecimal.ZERO;
    //付款明细批次
    private int fcdBatchNo;
    //本期拨付金额
    private BigDecimal faDemand = BigDecimal.ZERO;
    //拨付批次
    private int faBatchNo;
    //结余金额
    private BigDecimal balanceAmt = BigDecimal.ZERO;
    /**
     * 支付结余
     */
    private BigDecimal payBalanceAmt = BigDecimal.ZERO;
    /**
     * 合同支付次数
     */
    private int conPayBatchNo;
    /**
     * 【支付】本期申请支付金额统计值
     */
    private BigDecimal payApply = BigDecimal.ZERO;
}
