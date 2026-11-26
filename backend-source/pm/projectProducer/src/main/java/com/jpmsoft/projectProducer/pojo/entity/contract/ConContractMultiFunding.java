package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 多项目合同项目经费号
 *
 * @author tianhelin
 * @date 2025/9/8
 */
@Data
@TableName("Con_Contract_Multi_Funding")
public class ConContractMultiFunding {
    @TableId(type = IdType.INPUT)
    private String id;
    private String ccmId;
    private String fundingNo;
    private BigDecimal amount;
    private Integer orderNo;
}