package com.jpmsoft.projectProducer.pojo.entity.secInfo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 其它采购
 */
@Data
@TableName("Sec_OtherProcurement")
public class SecOtherProcurement {

    @TableId(type = IdType.INPUT)
    private String sopId;
    private String secId;
    //标题
    private String sopTitle;
    //编号
    private String sopCode;
    private String sopRemarks;
    //其它说明
    private String sopExplain;
    private String sopCreateUid;
    private String sopCreateUname;
    private Date sopCreateTime;
    private Integer sopState;
    private Date sopStateTime;
    //类型
    private String sopType;
    private BigDecimal sopBiddingAmount;
    private String sopPurchaseMethod;
    private String teId;
    //参评环节
    private String sopEvaluation;
}
