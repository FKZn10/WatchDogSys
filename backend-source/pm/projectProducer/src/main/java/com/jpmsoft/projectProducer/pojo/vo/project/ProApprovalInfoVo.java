package com.jpmsoft.projectProducer.pojo.vo.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.poi.hpsf.Decimal;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 查询项目批复信息
 *
 * @author: 李文涛 aaron
 * @date: 2022/7/12 14:39
 */
@Data
public class ProApprovalInfoVo {

    @JsonProperty("pApplyTilte")
    private String pApplyTilte;
    @JsonProperty("pApprBudgetCode")
    private String pApprBudgetCode;
    @JsonProperty("pApprBudgetDate")
    private Date pApprBudgetDate;
    @JsonProperty("pCommApprDoc")
    private String pCommApprDoc;

    @JsonProperty("pApproveBudget")
    private BigDecimal pApproveBudget;
}
