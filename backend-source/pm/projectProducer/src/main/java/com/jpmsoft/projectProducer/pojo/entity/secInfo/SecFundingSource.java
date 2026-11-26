package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目资金来源
 */
@Data
@TableName("Sec_Funding_Source")
public class SecFundingSource {

    @TableId(type = IdType.INPUT)
    private String sfsId;
    private String sfsForeignId;
    private String sfsCode;
    private Date sfsCreateTime;
    private Float sfsScale;
    private BigDecimal sfsMoney;
    private String sfsRemark;
    private Integer sfsSort;
}
