package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 指挥部架构
 */
@Data
@TableName("Pre_Command_Post")
public class PreCommandPost {

    @TableId(type = IdType.INPUT)
    private String comId;
    private String pId;
    private String comOverview;
    private BigDecimal comInvestment;
    private String comCreateCid;
    private String comCreateUid;
    private String comCreateUname;
    private Date comCreateTime;
    private Integer comState;
    private Date comStateTime;
    private String teId;
}
