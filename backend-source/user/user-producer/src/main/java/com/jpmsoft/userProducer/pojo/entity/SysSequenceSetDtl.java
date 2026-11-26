package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 编码规则配置详情
 */
@Data
@TableName("SYS_Sequence_Set_Dtl")
public class SysSequenceSetDtl {

    @TableId(type = IdType.INPUT)
    private String sdId;
    private String sId;
    private Integer sdRuleOrder;
    private String sdRuleType;
    private String sdRuleValue;
    private String sdPaddingSide;
    private Integer sdPaddingWidth;
    private Character sdPaddingChar;
    private String sdCreateUid;
    private String sdCreateUname;
    private Date sdCreateTime;
    private String sdUpdateUid;
    private String sdUpdateUname;
    private Date sdUpdateTime;
    //常量是否拼接
    private String sdTableField;
    private String sdFieldValue;
    private String sdTableKey;
}
