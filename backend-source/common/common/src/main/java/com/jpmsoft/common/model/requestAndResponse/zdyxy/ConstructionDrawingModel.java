package com.jpmsoft.common.model.requestAndResponse.zdyxy;

import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ConstructionDrawingModel extends BaseFileDto {

    private static final long serialVersionUID = -976074710229839001L;
    /**
     * 主键
     */
    private String id;

    private String constructionDrawingName;
    /**
     *
     */
    private String constructionDrawingCode;
    /**
     * 设计合同附件上传
     */
    private String designContractAttachmentFile;

    private String designContractAttachmentFileId;
    /**
     * 施工图附件上传
     */
    private String constructionDrawingAttachmentFile;
    private String constructionDrawingAttachmentFileId;

    /**
     * 施工图附件最终版
     */
    private String constructionDrawingFinalVersionFile;
    private String constructionDrawingFinalVersionFileId;

    /**
     * 发起单位
     */
    private String initiatingUnit;
    private String initiatingUnitName;

    /**
     * 发起人
     */
    private String initiatingPerson;
    private String initiatingPersonName;

    /**
     * 发起时间
     */
    private Date initiatingTime;

    /**
     * 发起人联系方式
     */
    private String initiatingPersonPhone;
    /**
     * 备注
     */
    private String remark;
    private String context;

    /**
     *
     * 是否选择 0：否 1：是
     */
    private Integer isSelect;
    private List<Date> createTimeRange;
}
