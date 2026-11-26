package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * 标段询价调研明细。
 */
@Data
@TableName("quotation_request")
public class QuotationRequest {

    /**
     * 主键。
     */
    @TableId(type = IdType.INPUT)
    private String id;

    /**
     * 所属标段ID。
     */
    private String secId;

    /**
     * 创建人ID。
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String createUid;

    /**
     * 创建人姓名。
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String createUname;

    /**
     * 创建时间。
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date createTime;

    /**
     * 状态。
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer state;

    /**
     * 状态时间。
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date stateTime;

    /**
     * 供应商名称。
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String supplierName;

    /**
     * 联系人。
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String qrContactPerson;

    /**
     * 联系电话。
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String qrContactPhone;

    /**
     * 调研人。
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String qrResearcher;

    /**
     * 调研方式。
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String qrResearchMethod;

    /**
     * 调研结果。
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String qrResearchResult;
}
