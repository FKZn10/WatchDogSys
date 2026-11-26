package com.jpmsoft.projectProducer.pojo.entity.other;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("znpj_indicator_info_type_config")
public class ZNPJIndicatorInfoTypeConfig implements Serializable {

    private static final long serialVersionUID = -1466236277740884333L;
    /**
     *
     */
    private String id;

    /**
     * 指标类型编码
     */
    private String typeCode;

    /**
     * 指标类型名称
     */
    private String typeName;

    /**
     * 旧指标类型编码
     */
    private String oldTypeCode;

    /**
     * 指标类型描述
     */
    private String typeDesc;

    /**
     * 指标ID
     */
    private String indicatorId;

    /**
     * 指标版本ID
     */
    private String indicatorVersionId;

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
    private LocalDateTime createTime;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 状态时间
     */
    private LocalDateTime stateTime;

    /**
     * 备注
     */
    private String remark;
}