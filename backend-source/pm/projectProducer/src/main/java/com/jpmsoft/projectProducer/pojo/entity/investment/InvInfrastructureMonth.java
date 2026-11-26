package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

/**
 * 基建投资月报
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/5 9:00
 */
@Data
@TableName("Inv_Infrastructure_Month")
public class InvInfrastructureMonth extends BaseEntity {
    private String imYear;
    private String imMon;
    private String imRemark;
    private String teId;
}
