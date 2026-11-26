package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 估算申报
 */
@Data
@TableName("Inv_Estimate_Declare")
public class InvEstimateDeclare {

    @TableId(type = IdType.INPUT)
    private String edId;
    private String pId;
    private BigDecimal edMoney;
    private String edRemark;
    private String edCreateUid;
    private String edCreateUname;
    private Date edCreateTime;
    private Integer edState;
    private Date edStateTime;
    private String teId;
    private String edUpdateUid;
    private String edUpdateUname;
}
