package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 支付报审清单
 * @Author: 李文涛 aaron
 * @date: 2024/10/17 13:47
 */
@Data
@TableName("Con_Pay_Measure")
public class ConPayMeasure {

    @TableId(type = IdType.INPUT)
    private String id;
    private String payId;
    private String meaId;
    private String conId;
}
