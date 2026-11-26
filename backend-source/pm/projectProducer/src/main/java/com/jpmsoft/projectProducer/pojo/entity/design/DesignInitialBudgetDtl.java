package com.jpmsoft.projectProducer.pojo.entity.design;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 初步设计（概算清单）
 */
@Data
@TableName("Design_Initial_Budget_Dtl")
public class DesignInitialBudgetDtl {

    @TableId(type = IdType.INPUT)
    private String dibdId;

    //初步设计表id
    private String diId;

    //概算主表id
    private String bdId;

    //概算明细id
    private String bddId;

    //项目及费用名称
    private String bddName;

    //合价（投资）
    private BigDecimal bddTotal;


}
