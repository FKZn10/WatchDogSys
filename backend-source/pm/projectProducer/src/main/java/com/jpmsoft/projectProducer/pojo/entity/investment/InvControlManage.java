package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 投资控制
 */
@Data
@TableName("Inv_Control_Manage")
public class InvControlManage {

    @TableId(type = IdType.INPUT)
    private String icmId;
    private String pId;
    private String pName;
    private String icmYear;
    private String icmLegalPerson;
    private String icmLegalPersonName;
    private String icmLegalPersonCid;
    private String icmGenerationUnit;
    private String icmPlanType;
    private String icmMajorType;
    private String icmSourceFunds;
    private BigDecimal icmInvestmentEstimation;
    private BigDecimal icmCompleteYnvestmentY;
    private BigDecimal icmInvestmentFundsY;
    private Date icmOpenDate;
    private Date icmEndDate;
    private String icmPlanSource;
    private BigDecimal icmLength;
    private BigDecimal icmWidth;
    private BigDecimal icmMeasureArea;
    private String icmCreateUid;
    private String icmCreateUname;
    private Date icmCreateTime;
    private Integer icmState;
    private Date icmStateTime;
    private String icmPName;
    private String teId;
}
