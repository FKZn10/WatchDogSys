package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目经费号
 *
 * @author: 李文涛 aaron
 * @date: 2022/7/20 14:47
 */
@Data
@TableName("Pro_Fund_No")
public class ProFundNo {
    @TableId(type = IdType.INPUT)
    private String id;
    @JsonProperty("pId")
    private String pId;
    private String fnCode;
    private BigDecimal fnMoney;
    private String fnRemark;
    private Integer fnSort;
    private Date createTime;
}
