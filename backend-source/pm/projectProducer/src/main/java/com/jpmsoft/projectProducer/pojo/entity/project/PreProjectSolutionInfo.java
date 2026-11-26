package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("pre_project_solution_info")
public class PreProjectSolutionInfo implements Serializable {


    private static final long serialVersionUID = 548033384771352602L;
    /**
     *
     */
    private String id;

    /**
     * 项目ID
     */
    private String projectId;
    /**
     * 信息类型
     */
    private String infoType;

    /**
     * 指标ID
     */
    private String indicatorId;

    /**
     * 指标版本ID
     */
    private String indicatorVersionId;

    /**
     * 应用名称
     */

    private String applicationId;
    private String applicationName;

    /**
     * 使用量
     */
    private BigDecimal used;

    /**
     * 可用量
     */
    private BigDecimal available;

    /**
     * 分数
     */
    private Double score;

    /**
     * 合计
     */
    private BigDecimal sum;

    /**
     * 占比
     */
    private BigDecimal rate;

    /**
     * SYS_FileAnnex表fileRelationId
     */
    private String supportingDocument;

    /**
     * 使用时间
     */
    private Date useTime;

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
}