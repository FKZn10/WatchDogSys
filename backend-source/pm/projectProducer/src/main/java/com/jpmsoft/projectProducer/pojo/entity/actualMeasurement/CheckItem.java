package com.jpmsoft.projectProducer.pojo.entity.actualMeasurement;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("check_item")
public class CheckItem {

    /**
     * 检查项ID
     */
    @TableId(value = "Ci_Id", type = IdType.INPUT)
    private String ciId;

    /**
     * 创建用户ID
     */
    @TableField("Create_Uid")
    private String createUid;

    /**
     * 创建用户姓名
     */
    @TableField("Create_Uname")
    private String createUname;

    /**
     * 创建时间
     */
    @TableField("Create_Time")
    private Date createTime;

    /**
     * 状态
     */
    @TableField("State")
    private Integer state;

    /**
     * 审批时间
     */
    @TableField("State_Time")
    private Date stateTime;

    /**
     * 检查项名称
     */
    @TableField("Ci_Name")
    private String ciName;

    /**
     * 检查项编码
     */
    @TableField("Ci_Code")
    private String ciCode;

    /**
     * 检查项类型
     */
    @TableField("Ci_Type")
    private String ciType;

    /**
     * 测量工具
     */
    @TableField("Ci_tool")
    private String ciTool;

    /**
     * 检查项表达式
     */
    @TableField("Ci_Expection")
    private String ciExpection;

    /**
     * 计算点公式
     */
    @TableField("Ci_Compute_Expection")
    private String ciComputeExpection;

    /**
     * 指标说明
     */
    @TableField("Ci_Indicator")
    private String ciIndicator;

    /**
     * 检查项描述
     */
    @TableField("Ci_describe")
    private String ciDescribe;

    /**
     * 测量方法和数据记录
     */
    @TableField("Ci_Method_Data")
    private String ciMethodData;

    /**
     * 节点类型  目录 01 检查项 02
     */
    @TableField("Ci_Node_Type")
    private String ciNodeType;

    /**
     * 父级
     */
    @TableField("Ci_Parent")
    private String ciParent;

    /**
     * 排序
     */
    @TableField("Ci_Order")
    private Integer ciOrder;

    /**
     * 图片附件
     */
    @TableField("ci_file")
    private String ciFile;

    /**
     * 租户id
     */
    @TableField("te_Id")
    private String teId;

    /**
     * 动态代码ID
     */
    @TableField("dynamic_Id")
    private String dynamicId;

    /**
     * 是否使用动态代码
     */
    @TableField("is_Use_Dynamic")
    private Integer isUseDynamic;

}
