package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 资格预审情况报告
 */
@Data
@TableName("Sec_Prequalification_Report")
public class SecPrequalificationReport {
    @TableId(type = IdType.INPUT)
    private String id;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String pId;
    private String secId;
    private Date sprJudgeDate;
    private String sprStyle;
    private String sprRemark;
}