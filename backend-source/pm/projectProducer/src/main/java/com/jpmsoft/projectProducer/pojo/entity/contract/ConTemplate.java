package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 合同范本.
 *
 * @author: 李文涛 aaron
 * @date: 2022/07/08 10:07
 * @TableName Con_Template
 */
@TableName(value = "Con_Template")
@Data
public class ConTemplate implements Serializable {
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
    @TableField(value = "cont_Initiate")
    private String contInitiate;
    /**
     *
     */
    @TableField(value = "cont_Name")
    private String contName;
    /**
     *
     */
    @TableField(value = "cont_Version")
    private String contVersion;
    /**
     *
     */
    @TableField(value = "P_Id")
    private String pId;
    /**
     *
     */
    @TableField(value = "Con_Id")
    private String conId;
    /**
     *
     */
    @TableField(value = "Con_Type")
    private String conType;
    /**
     *
     */
    @TableField(value = "cont_Department_Id")
    private String contDepartmentId;
    /**
     *
     */
    @TableField(value = "cont_File")
    private String contFile;
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
    @TableField(value = "State_Time")
    private Date stateTime;
    /**
     *
     */
    @TableField(value = "State")
    private Integer state;
    /**
     *
     */
    @TableField(value = "cont_Enable")
    private Integer contEnable;
    /**
     *
     */
    @TableField(value = "cont_Code")
    private String contCode;
    /**
     *
     */
    @TableField(value = "cont_Description")
    private String contDescription;
    private String teId;

    private String contMarKey;
    private String contNew;
}