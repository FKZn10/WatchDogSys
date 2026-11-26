package com.jpmsoft.common.model.requestAndResponse.Project;

import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class PreProjectSolutionInfoModel extends BaseFileDto {
    private static final long serialVersionUID = -1317080549367052350L;
    /**
     * 项目ID
     */
    private String projectId;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 信息类型
     */
    private String infoType;
    /**
     * 信息类型名称
     */
    private String infoTypeName;

    /**
     * 指标ID
     */
    private String indicatorId;

    /**
     * 指标版本ID
     */
    private String indicatorVersionId;
    private String indicatorName;

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
     * 数量
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
    private String supportingDocumentIds;
    /**
     * 使用时间
     */
    private Date useTime;
    private String remark;
}
