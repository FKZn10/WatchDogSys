package com.jpmsoft.projectProducer.pojo.entity.qualitySafety;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 安全责任岗位
 */
@Data
@TableName("safe_responsibility_post")
public class SafeResponsibilityPost {
    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    private String id;

    /**
     * 安全责任体系ID
     */
    private String systemId;

    /**
     * 责任层级ID
     */
    private String levelId;

    /**
     * 岗位名称
     */
    private String postName;

    /**
     * 岗位总体说明
     */
    private String postDescription;

    /**
     * 日常履职要求
     */
    private String dailyRequirement;

    /**
     * 管控标准
     */
    private String controlStandard;

    /**
     * 考核依据
     */
    private String assessmentBasis;

    /**
     * 追责场景
     */
    private String accountabilityScenario;

    /**
     * 排序号
     */
    private Integer sortNo;

    /**
     * 状态：0停用，1启用
     */
    private Boolean status;

    /**
     * 创建人ID
     */
    private String createUid;

    /**
     * 创建人姓名
     */
    private String createUname;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人ID
     */
    private String updateUid;

    /**
     * 修改人姓名
     */
    private String updateUname;

    /**
     * 修改时间
     */
    private Date updateTime;
}