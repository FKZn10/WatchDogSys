package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 结算二审
 *
 * @author: 李文涛 aaron
 * @date: 2022/10/21 9:57
 */
@Data
@TableName("Inv_Settlement_Second")
public class InvSettlementSecond extends BaseEntity {
    private String saId;
    @JsonProperty("pId")
    private String pId;
    private String conId;
    private Boolean ssHistory;
    private BigDecimal ssApprovedAmt;
    private String ssApprovedCom;
    private String ssApprovedFile;
    private String teId;
}
