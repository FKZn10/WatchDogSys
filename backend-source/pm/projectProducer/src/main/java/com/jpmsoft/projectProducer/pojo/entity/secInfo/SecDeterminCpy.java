package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * (定标)中标单位
 *
 * @author: 李文涛 aaron
 * @date: 2022/7/25 10:57
 */
@Data
@TableName("Sec_Determin_Cpy")
public class SecDeterminCpy {

    @TableId(type = IdType.INPUT)
    private String id;
    private String drId;
    private String sdcUnit;
    private Date createTime;
}
