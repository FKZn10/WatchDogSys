package com.jpmsoft.projectProducer.pojo.entity.actualMeasurement;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("dynamics_code")
public class DynamicsCode {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String dcName;
    private String dcCode;
    private String className;
    private String methodName;
    private String dcDescription;
    private String status;
    private String createUid;
    private String createUname;
    private Date createTime;
    /**
     * 种类
     */
    private String dcCategory;
    /**
     * 类型
     */
    private String dcType;


}
