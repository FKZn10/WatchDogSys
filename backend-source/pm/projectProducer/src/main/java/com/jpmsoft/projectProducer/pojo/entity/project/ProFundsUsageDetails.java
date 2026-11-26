package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 资金使用计划明细
 *
 * @author: 李文涛 aaron
 * @date: 2022/11/7 15:42
 */
@Data
@TableName("Pro_Funds_Usage_Details")
public class ProFundsUsageDetails {

    @TableId(type = IdType.INPUT)
    private String id;
    private String cplId;
    private String fudYear;
    private BigDecimal fudInvComplated;
    private BigDecimal fudInvDemand;
    private String fudRemark;
    private Integer fudSort;

}
