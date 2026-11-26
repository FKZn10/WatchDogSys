package com.jpmsoft.jpbpm.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("SYS_FlowRule")
public class FlowRule {

    @TableId(type = IdType.INPUT)
    private String ruleid;
    private String rulename;
    private String ruletype;
    private String rulecondition;
    private String rulesystype;
    private String ruleflowid;
    private String rulebeginactid;
    private String ruleendactid;
    private String teId;
}
