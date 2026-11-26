package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 合同支付现场检测关联表
 *
 * @Author: 李文涛 aaron
 * @date: 2023/10/30 17:29
 */
@Data
@TableName("Con_Pay_Witness")
public class ConPayWitness {

    @TableId(type = IdType.INPUT)
    private String id;
    private String payId;
    private String wiId;
    private Integer pwSort;
}
