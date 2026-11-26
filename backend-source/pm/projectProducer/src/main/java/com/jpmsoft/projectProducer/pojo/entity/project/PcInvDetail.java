package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目变更-项目投资详情
 */
@Data
@TableName("Pc_Inv_Detail")
public class PcInvDetail {

    @TableId(type = IdType.INPUT)
    private String fsId;
    private String pcId;
    private String fsCode;
    private Date fsCreateTime;
    private Float fsScale;
    private BigDecimal fsMoney;
    private String fsRemark;
    private Integer fsSort;

}
