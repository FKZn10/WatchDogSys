package com.jpmsoft.userProducer.pojo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * ai规则配置
 */
@Data
@TableName("ai_rule_configuration")
public class AiRuleConfiguration {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String type;
    private String prompt;
    private String status;
    private String createUid;
    private String createUname;
    private Date createTime;
}
