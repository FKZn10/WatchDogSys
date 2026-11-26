package com.jpmsoft.projectProducer.pojo.dto.fundPlan;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 资金拨付
 * @Author: 李文涛 aaron
 * @date: 2024/9/24 19:00
 */
@Data
public class FundAllocationDTO extends BaseEntity {

    private String id;
    private Integer state;
    private Date stateTime;
    private String createUid;
    private String createUname;
    private Date createTime;
    private String   faAffiliatedUnit;
    private Date faDisbursementTime;
    private BigDecimal  faThisAmountAllocated;
    private BigDecimal  faThisDemand;
    private BigDecimal  faThisActualAmount;
    private BigDecimal  faResidualAmount;
    private String  faDistributionResult;
    private String  faRemark;

    private String   faAffiliatedUnitName;
    private List<FundAllocationDtlDTO> fundAllocationDtls;
}
