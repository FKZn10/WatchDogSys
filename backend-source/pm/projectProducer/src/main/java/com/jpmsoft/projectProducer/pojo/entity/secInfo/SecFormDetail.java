package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 考核评价表详细
 *
 * @Author: 李文涛 aaron
 * @date: 2023/8/7 16:56
 */
@Data
@TableName("Sec_Form_Detail")
public class SecFormDetail {

    @TableId(type = IdType.INPUT)
    private String fdId;
    private String fId;
    private String fdType;
    private String fdContent;
    private BigDecimal fdScore;
    private Integer fdOrder;
    private String fdCode;
    private String fdState;
    private String fdCalculationRule;
    private String fdStandard;
    private String fdRemark;
    private String teId;
}
