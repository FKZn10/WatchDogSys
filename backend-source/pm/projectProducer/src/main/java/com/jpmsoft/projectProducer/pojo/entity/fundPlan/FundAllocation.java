package com.jpmsoft.projectProducer.pojo.entity.fundPlan;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 资金拨付
 * @Author: 李文涛 aaron
 * @date: 2024/9/24 19:00
 */
@Data
@TableName("Fund_Allocation")
public class FundAllocation extends BaseEntity {


    private String   faAffiliatedUnit;
    private Date faDisbursementTime;
    private BigDecimal  faThisAmountAllocated;
    private BigDecimal  faThisDemand;
    private BigDecimal  faThisActualAmount;
    private BigDecimal  faResidualAmount;
    private String  faDistributionResult;
    private String  faRemark;
}
