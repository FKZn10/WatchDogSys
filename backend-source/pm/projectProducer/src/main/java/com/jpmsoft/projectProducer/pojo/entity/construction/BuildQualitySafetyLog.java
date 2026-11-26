package com.jpmsoft.projectProducer.pojo.entity.construction;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 施工质量安全日志
 *
 * @author: 李文涛 aaron
 * @date: 2022/07/08 10:07
 * @TableName Build_Quality_Safety_Log
 */
@TableName(value = "Build_Quality_Safety_Log")
@Data
public class BuildQualitySafetyLog implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    @TableId(value = "id")
    private String id;
    /**
     *
     */
    @TableField(value = "P_Id")
    private String pId;
    /**
     *
     */
    @TableField(value = "Sec_Id")
    private String secId;
    /**
     *
     */
    @TableField(value = "Bqsl_Date")
    private Date bqslDate;
    /**
     *
     */
    @TableField(value = "Create_Uid")
    private String createUid;
    /**
     *
     */
    @TableField(value = "Create_Uname")
    private String createUname;
    /**
     *
     */
    @TableField(value = "Create_Time")
    private Date createTime;
    /**
     *
     */
    @TableField(value = "State")
    private Integer state;
    /**
     *
     */
    @TableField(value = "State_Time")
    private Date stateTime;
    /**
     *
     */
    @TableField(value = "Bqsl_Remarks")
    private String bqslRemarks;
    private String teId;
}