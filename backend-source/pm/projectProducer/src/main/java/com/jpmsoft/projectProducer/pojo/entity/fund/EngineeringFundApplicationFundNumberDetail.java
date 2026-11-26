package com.jpmsoft.projectProducer.pojo.entity.fund;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/***
 * 工程用款申请文号占用明细
 */
@Data
@TableName("Engineering_Fund_Application_Fund_Number_Detail")
public class EngineeringFundApplicationFundNumberDetail {
    private String id;
    private String efaId;
    private String fnId;
    private BigDecimal efaOccupancyAmount;
}
