package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 资料请审单
 */
@Data
@TableName("Sec_Review_Material")
public class SecReviewMaterial implements Serializable {
    private static final long serialVersionUID = 901582471546580775L;

    @TableId(type = IdType.INPUT)
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
     * 是否有原件
     */
    private Integer rmOriginal;
    /**
     * 是否有复印件
     */
    private Integer rmCopy;
    /**
     * 是否有电子版
     */
    private Integer rmElectronic;
    /**
     * 原件附件
     */
    private String rmOriginalRelationId;
    /**
     * 复印件附件
     */
    private String rmCopyRelationId;
    /**
     * 电子版附件
     */
    private String rmElectronicRelationId;
    
    private String rmRemark;


}

