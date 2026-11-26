package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 编码规则配置
 */
@Data
@TableName("SYS_Sequence_Set")
public class SysSequenceSet {
    @TableId(type = IdType.INPUT)
    private String sId;
    private String sName;
    private String tCode;
    private String sDelimiter;
    private String sReset;
    private Integer sStep;
    private Integer sNo;
    private String sCode;
    private String sCurrentReset;
    private String sCreateUid;
    private String sCreateUname;
    private Date sCreateTime;
    private String sUpdateUid;
    private String sUpdateUname;
    private Date sUpdateTime;
    private Boolean sEnable;
    private String teId;
}
