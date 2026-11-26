package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 城建月度计划执行状态流转记录
 */
@Data
@TableName("operation_Record")
public class OperationRecord {

    @TableId(type = IdType.INPUT)
    private String id;
    private String recordId;
    private Date orTime;
    private String orOperator;
    private String orOperatorName;
    private String orType;
    private String orStatusOld;
    private String orStatusNow;
    private String orRemarks;

}
