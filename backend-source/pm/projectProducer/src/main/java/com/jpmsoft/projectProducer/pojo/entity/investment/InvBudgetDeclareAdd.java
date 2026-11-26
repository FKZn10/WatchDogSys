package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 概算追加
 *
 * @Author: 李文涛 aaron
 * @date: 2024/3/29 15:12
 */
@Data
@TableName("Inv_Budget_Declare_Add")
public class InvBudgetDeclareAdd extends BaseEntity {

    private BigDecimal bdaMoney;
    private String bdaRemark;
    private String pId;
    private String teId;
    private BigDecimal bdaProBudget;
}
