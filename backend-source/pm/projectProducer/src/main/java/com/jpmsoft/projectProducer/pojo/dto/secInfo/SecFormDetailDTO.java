package com.jpmsoft.projectProducer.pojo.dto.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 考核评价表详细
 *
 * @Author: 李文涛 aaron
 * @date: 2023/8/7 16:56
 */
@Data
public class SecFormDetailDTO extends BaseModeRequest implements Serializable {

    private static final long serialVersionUID = -2025132223008816452L;

    @TableId(type = IdType.INPUT)
    private String fdId;
    @JsonProperty("fId")
    private String fId;
    private String fdType;
    private String fdContent;
    private BigDecimal fdScore;
    private Integer fdOrder;
    private String fdCode;
    private String fdState;
    private String fdCalculationRule;
    private String fdStandard;
    private String fdRemark;
}
