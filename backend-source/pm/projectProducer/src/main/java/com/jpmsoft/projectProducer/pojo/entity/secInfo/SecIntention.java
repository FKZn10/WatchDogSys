package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 招标意向
 *
 * @Author: 李文涛 aaron
 * @date: 2024/3/20 16:11
 */
@Data
@TableName("Sec_Intention")
public class SecIntention extends BaseEntity {
    //编号
    private String siCode;
    //采购目录名称
    private String siCatalogName;
    //采购需求概况
    private String siOverview;
    //预算金额
    private BigDecimal siAmt;
    //预计采购日期
    private Date siTime;
    private String siRemark;
    private String secId;
}
