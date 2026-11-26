package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目立项资金来源
 */
@Data
@TableName("Pro_Funding_Source")
public class ProFundingSource {

    @TableId(type = IdType.INPUT)
    private String fsId;
    private String paId;
    private String fsCode;
    private Date fsCreateTime;
    private Float fsScale;
    private BigDecimal fsMoney;
    private String fsRemark;
    private Integer fsSort;

}
