package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @TableName single_project_solution_info
 */
@TableName(value ="single_project_solution_info")
@Data
public class SingleProjectSolutionInfo implements Serializable {
    /**
     * 
     */
    @TableId
    private String id;

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 功能编码:company contract member material plan quality safety
     */
    private String functionCode;

    /**
     * 数量
     */
    private BigDecimal sum;

    /**
     * 功能地址
     */
    private String url;

    /**
     * 创建人ID
     */
    private String createUid;

    /**
     * 创建人名称
     */
    private String createUname;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 状态时间
     */
    private Date stateTime;

    /**
     * 备注
     */
    private String remark;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}