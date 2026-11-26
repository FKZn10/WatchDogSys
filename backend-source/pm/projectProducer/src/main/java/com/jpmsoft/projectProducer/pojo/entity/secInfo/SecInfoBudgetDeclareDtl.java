package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 标段概算清单
 */
@Data
@TableName("Sec_Info_Budget_Declare_Dtl")
public class SecInfoBudgetDeclareDtl {

    @TableId(type = IdType.INPUT)
    private String sibdId;

    //标段id
    private String secId;

    //项目id
    private String pId;

    //招采计划id
    private String ppId;

    //概算主表
    private String bdId;

    //概算科目表
    private String bddId;

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
