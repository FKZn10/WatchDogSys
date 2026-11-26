package com.jpmsoft.projectProducer.pojo.entity.fundPlan;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 进度申报详情
 * @Author: 李文涛 aaron
 * @date: 2024/9/24 19:51
 */
@Data
@TableName("Progress_Declaration_Dtl")
public class ProgressDeclarationDtl {

    @TableId(type = IdType.INPUT)
    private String id;
    private String pdId;
    private String pId;
    private String conId;
    private BigDecimal pddProgress;
    private String pddFile;
    private BigDecimal pddMoney;
    private String pddRemark;
    private String cppId;
    private Date pddJgDate;

    private String pddPayType;

    private String pddMoneyContant;

    private Integer pddThisNode;

    private String pddContant;

    private String pddProgressNode;

    private BigDecimal pddStoichiometric;

    private String pddScheduleType;


}
