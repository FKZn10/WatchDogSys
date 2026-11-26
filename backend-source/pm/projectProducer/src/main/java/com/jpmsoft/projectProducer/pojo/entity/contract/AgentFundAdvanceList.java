package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 代建合同实拨
 */
@Data
@TableName("Agent_Fund_Advance_List")
public class AgentFundAdvanceList {
    @TableId(type = IdType.INPUT)
    private String afalId;
    private String afaId;
    private String afalConId;
    private BigDecimal afalLastTotalMoney;
    private BigDecimal afalThisMoney;
    private BigDecimal afalSurplusMoney;
    private String afalFile;
}
