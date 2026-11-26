package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 匡算申报
 */
@Data
@TableName("Inv_Rough_Declare")
public class InvRoughDeclare {

    @TableId(type = IdType.INPUT)
    private String rdId;
    private String pId;
    private BigDecimal rdMoney;
    private String rdRemark;
    private String rdCreateUid;
    private String rdCreateUname;
    private Date rdCreateTime;
    private Integer rdState;
    private Date rdStateTime;
    private String teId;
    private String rdUpdateUid;
    private String rdUpdateUname;
}
