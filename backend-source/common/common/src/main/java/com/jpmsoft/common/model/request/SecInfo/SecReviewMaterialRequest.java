package com.jpmsoft.common.model.request.SecInfo;

import lombok.Data;

@Data
public class SecReviewMaterialRequest {

    private String rmId;
    /**
     * 来源类型
     */
    private String sourceType;
    /**
     * 业务表id
     */
    private String businessId;
    /**
     * 资料名称
     */
    private String rmName;
    /**
     * 数量
     */
    private Integer rmNum;
    /**
     * 是否有原件  1：有 0：否
     */
    private Integer rmOriginal;
    /**
     * 是否有复印件 1：有 0：否
     */
    private Integer rmCopy;
    /**
     * 是否有电子版 1：有 0：否
     */
    private Integer rmElectronic;

    /**
     * 原件附件
     */
    private String rmOriginalRelationId;

    /**
     * 原件附件fileids
     */
    private String rmOriginalFileIds;

    /**
     * 复印件附件
     */
    private String rmCopyRelationId;

    /**
     * 复印件附件fileids
     */
    private String rmCopyFileIds;

    /**
     * 电子版附件
     */
    private String rmElectronicRelationId;

    /**
     * 电子版附件fileids
     */
    private String rmElectronicFileIds;

    private String rmRemark;

}
