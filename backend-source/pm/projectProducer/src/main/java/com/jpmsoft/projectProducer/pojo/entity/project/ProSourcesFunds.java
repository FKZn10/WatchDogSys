package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目资金来源
 */
@Data
@TableName("Pro_Sources_Funds")
public class ProSourcesFunds {

    @TableId(type = IdType.INPUT)
    private String fsId;
    @JsonProperty("pId")
    private String pId;
    private String fsCode;
    private Date fsCreateTime;
    private Float fsScale;
    private BigDecimal fsMoney;
    private String fsRemark;
    private Integer fsSort;
    private  String fsCostCode;
}
