package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 专家抽取屏蔽表
 *
 * @author: 李文涛 aaron
 * @date: 2022/10/10 14:01
 */
@Data
@TableName("Sec_Expert_Shield")
public class SecExpertShield {

    @TableId(type = IdType.INPUT)
    private String id;
    private String srId;
    private String exId;

}
