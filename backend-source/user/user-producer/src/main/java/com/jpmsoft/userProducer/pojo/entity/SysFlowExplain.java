package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 审批常用语
 */
@Data
@TableName("SYS_FlowExplain")
public class SysFlowExplain {

    @TableId(type = IdType.INPUT)
    private String feId;
    @TableField(value = "FE_MemberID")
    private String feMemberId;
    @TableField(value = "FE_SolutionID")
    private String feSolutionId;
    private String feContent;
    private Date feTime;
    private Integer feOrder;
}
