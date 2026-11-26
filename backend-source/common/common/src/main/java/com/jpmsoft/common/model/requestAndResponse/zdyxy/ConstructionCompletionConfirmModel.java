package com.jpmsoft.common.model.requestAndResponse.zdyxy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class ConstructionCompletionConfirmModel extends BaseFileDto {
    private static final long serialVersionUID = -2805173584823262058L;
    /**
     * 主键
     */
    private String id;

    /**
     * 报修Id
     */
    private String mrId;
    private String mrSerialNumber;
    @JsonProperty(value = "pName")
    private String pName;
    private BigDecimal mrReportAmountPre;
    /**
     * 工程地点
     */
    private String constructionSite;
    private String confirmSerialNumber;
    /**
     * 建设单位
     */
    private String constructionUnit;
    private String constructionUnitName;

    /**
     * 开工时间
     */
    private Date constructionStartTime;

    /**
     * 施工单位
     */
    private String constructionContractor;
    private String constructionContractorName;

    /**
     * 审计单位
     */
    private String auditUnit;
    private String auditUnitName;

    /**
     * 竣工时间
     */
    private Date constructionCompletionTime;

    /**
     * 验收时间
     */
    private Date acceptanceTime;

    /**
     * 主要工作内容及工作量
     */
    private String mainWorkContent;

    /**
     * 工程形象照片
     */
    private String constructionImage;
    private String constructionImageIds;

    /**
     * 竣工图纸
     */
    private String constructionDrawing;
    private String constructionDrawingIds;

    /**
     * 审计单位意见
     */
    private String auditUnitOpinion;

    /**
     * 建设单位意见
     */
    private String constructionUnitOpinion;
    /**
     *
     * 是否选择 0：否 1：是
     */
    private Integer isSelect;
    private List<Date> createTimeRange;

    private String mrCreateUid;
    private String mrCreateUname;
}