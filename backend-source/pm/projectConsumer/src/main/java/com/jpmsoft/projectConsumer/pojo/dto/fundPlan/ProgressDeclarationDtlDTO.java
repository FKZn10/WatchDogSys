package com.jpmsoft.projectConsumer.pojo.dto.fundPlan;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 进度申报详情
 * @Author: 李文涛 aaron
 * @date: 2024/9/24 19:51
 */
@Data
public class ProgressDeclarationDtlDTO {

    private String id;
    @JsonProperty("pId")
    private String pId;
    private String conId;
    private BigDecimal pddProgress;
    private String pddFile;
    private BigDecimal pddMoney;
    private String pddRemark;
    private String cppId;
    private Date pddJgDate;

    private String pddFileIds;
    @JsonProperty("pName")
    private String pName;
    private String conName;
    private String cppClause;
    private String conCode;
    private BigDecimal conMoneyTotal;


    private String pddMoneyContant;

    private Integer pddThisNode;

    private String pddContant;

    private String pddProgressNode;

    private BigDecimal pddStoichiometric;

    private String pddScheduleType;

    private String pddPayType;
}
