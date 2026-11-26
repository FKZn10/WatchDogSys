package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目建设计划资金来源
 *
 * @author: 李文涛 aaron
 * @date: 2022/11/7 15:37
 */
@Data
@TableName("Pro_Construction_Sources_Funds")
public class ProConstructionSourcesFunds {

    @TableId(type = IdType.INPUT)
    private String fsId;
    private String cplId;
    private String fsCode;
    private Date fsCreateTime;
    private Float fsScale;
    private BigDecimal fsMoney;
    private String fsRemark;
    private Integer fsSort;
}
