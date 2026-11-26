package com.jpmsoft.projectProducer.pojo.dto.fundPlan;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/17 15:39
 */
@Data
public class ProjectFundCollectionDetailsDto extends BaseModeRequest implements Serializable {

    private static final long serialVersionUID = 1554093701245449012L;

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
    //主管单位
    @JsonProperty("pConstructionUnitName")
    private String pConstructionUnitName;
    //主管部门
    @JsonProperty("pCompetentDepartmentName")
    private String pCompetentDepartmentName;
    //付款金额
    private BigDecimal fcdMoney;
    //付款明细批次
    private int fcdBatchNo;
    //本期拨付金额
    private BigDecimal faDemand;
    //拨付批次
    private int faBatchNo;
    //结余金额
    private BigDecimal balanceAmt;
    /**
     * 支付结余
     */
    private BigDecimal payBalanceAmt;
    /**
     * 合同支付次数
     */
    private int conPayNo;
    /**
     * 【支付】本期申请支付金额统计值
     */
    private BigDecimal payApply;
}
