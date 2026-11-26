package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 招采计划概算金额清单表
 */
@Data
@TableName("Sec_Procurement_Plan_Amount")
public class SecProcurementPlanAmount {
    @TableId(type = IdType.INPUT)
    private String ppaId;

    //招采计划id
    private String ppId;

    //概算申报主表id
    private String bdId;

    //概算申报清单id
    private String bddId;

    //科目名称
    private String bddName;

    //序号
    private String bddNumber;

    //造价金额
    private BigDecimal bddAmount;

    //选择金额
    private BigDecimal selectAmount;

    //选择比例
    private Float selectProportion;


}
