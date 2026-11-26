package com.jpmsoft.jpbpm.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 问题反馈
 */
@Data
@TableName("OA_Problem_Feedback")
public class OaProblemFeedback {
    @TableId(type = IdType.INPUT)
    private String pmfId;
    private String pmdId;
    private String pmfContent;
    private String pmfUid;
    private String pmfUname;
    private Date pmfTime;
    private String pmfCreateUid;
    private String pmfCreateUname;
    private Date pmfCreateTime;
    private Integer pmfState;
    private Date pmfStateTime;
    private String pmfCode;
    private Integer pmfStatus;

}
