package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 中标成交单位
 *
 * @author: 李文涛 aaron
 * @date: 2022/7/25 10:43
 */
@Data
@TableName("Sec_Publicity_Cpy")
public class SecPublicityCpy {

    @TableId(type = IdType.INPUT)
    private String id;
    //中标结果公示表id
    private String prId;
    //中标成交单位
    private String spcUnit;
    //中标成交金额
    private BigDecimal spcAmt;
    private Date createTime;
    private String spcDescribe;

}
