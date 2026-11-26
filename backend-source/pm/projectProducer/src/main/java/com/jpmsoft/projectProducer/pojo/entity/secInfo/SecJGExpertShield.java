package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 专家抽取屏蔽表 （建管）
 *
 */
@Data
@TableName("Sec_JG_Expert_Shield")
public class SecJGExpertShield {

    @TableId(type = IdType.INPUT)
    private String id;
    //专家抽取表id
    private String srId;
    //专家表id
    private String exId;

}
