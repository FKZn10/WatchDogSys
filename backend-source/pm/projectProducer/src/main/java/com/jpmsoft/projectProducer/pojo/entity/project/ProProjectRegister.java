package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目登记主表，业务字段允许在整表单修改时清空。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("Pro_Project_Register")
public class ProProjectRegister extends BaseEntity {

    /**
     * 项目名称。
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String projectName;

    /**
     * 项目代码。
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String projectCode;

    /**
     * 项目类型字典编码。
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String projectType;

    /**
     * 投资主体名称。
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String investorName;

    /**
     * 联系人。
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String contactName;

    /**
     * 联系电话。
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String contactPhone;

    /**
     * 计划开工日期。
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date planStartDate;

    /**
     * 计划竣工日期。
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date planEndDate;

    /**
     * 提交日期。
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date submitDate;

    /**
     * 项目地址。
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String projectAddress;

    /**
     * 经度。
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private BigDecimal longitude;

    /**
     * 纬度。
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private BigDecimal latitude;

    /**
     * 项目概况，最多300个字符。
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String projectOverview;

    /**
     * 总建筑面积，平方米。
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private BigDecimal totalBuildingArea;

    /**
     * 投资总额，万元。
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private BigDecimal totalInvestment;

    /**
     * 是否政府投资：0否，1是。
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer isGovernmentInvestment;

    /**
     * 批复文件附件分组ID。
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String approvalFile;

    /**
     * 项目效果图附件分组ID。
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String renderingFile;

    /**
     * 最后修改人ID。
     */
    private String updateUid;
    /**
     * 最后修改人姓名。
     */
    private String updateUname;
    /**
     * 最后修改时间。
     */
    private Date updateTime;
}
