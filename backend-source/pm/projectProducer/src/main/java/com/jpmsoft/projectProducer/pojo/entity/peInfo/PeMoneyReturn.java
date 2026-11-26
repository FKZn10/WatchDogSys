package com.jpmsoft.projectProducer.pojo.entity.peInfo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 履约保证金退还
 *
 * @Author: 李文涛 aaron
 * @date: 2024/3/25 18:40
 */
@Data
@TableName("Pe_Money_Return")
public class PeMoneyReturn extends BaseEntity {
    private String conId;
    private String pId;
    private BigDecimal mrConAmt;
    private String mrType;
    private String mrContent;
    private BigDecimal mrAmt;
    private String mrReason;
    private String mrConstruction;
    private String mrRemark;
}
