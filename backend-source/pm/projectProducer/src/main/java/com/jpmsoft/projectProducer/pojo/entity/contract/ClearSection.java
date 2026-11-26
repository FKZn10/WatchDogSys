package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("BG_ClearOrder")
public class ClearSection {

    @TableId
    private String id;

    @TableField("CLO_PId")
    private String projectId;

    @TableField("CLO_ConId")
    private String contractId;

    private Integer state;

    private BigDecimal finalConfirmMoney;
}
