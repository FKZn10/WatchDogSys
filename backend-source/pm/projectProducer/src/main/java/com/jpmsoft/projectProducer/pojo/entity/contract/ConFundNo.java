package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 合同项目经费号
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/15 14:32
 */
@Data
@TableName("Con_Fund_No")
public class ConFundNo {

    @TableId(type = IdType.INPUT)
    private String id;
    private String payId;
    private String fnCode;
    private BigDecimal fnMoney;
    private String fnRemark;
    private Integer fnSort;
    private Date createTime;
    private String fnFundCode;
}
