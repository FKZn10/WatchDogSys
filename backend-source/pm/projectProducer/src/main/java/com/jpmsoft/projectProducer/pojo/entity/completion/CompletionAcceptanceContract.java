package com.jpmsoft.projectProducer.pojo.entity.completion;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 工程验收——合同清单信息记录表
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/18 9:34
 */
@Data
@TableName("Completion_Acceptance_Contract")
public class CompletionAcceptanceContract {

    @TableId(type = IdType.INPUT)
    private String cacId;
    private String caId;
    private String conId;
    private BigDecimal cacSettlementMoney;
    private String cacRemarks;
}
