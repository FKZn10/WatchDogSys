package com.jpmsoft.projectProducer.pojo.entity.fund;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 资金计划明细-概算关联表
 */
@Data
@TableName("Group_Fund_Plan_Detail_Subject")
public class GroupFundPlanDetailSubject {
    @TableId(type = IdType.INPUT)
    private String id;

    /**
     * 资金计划id
     */
    private String gfpId;

    /**
     * 资金计划明细id
     */
    private String gfpdId;

    /**
     * 概算科目id（ibbdId）
     */
    private String ibbId;

    /**
     * 估算科目id（iebdId）
     */
    private String iebId;
}