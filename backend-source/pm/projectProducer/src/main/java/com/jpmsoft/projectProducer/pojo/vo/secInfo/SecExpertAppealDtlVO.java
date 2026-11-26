package com.jpmsoft.projectProducer.pojo.vo.secInfo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 专家考核申诉内容
 *
 * @Author: 李文涛 aaron
 * @date: 2023/8/1 9:53
 */
@Data
public class SecExpertAppealDtlVO {

    private String id;
    private String seaId;
    private String seadContent;
    private BigDecimal seadScore;
    private String seadReason;
    private Integer seadSort;
    private Boolean seadIsAgree;
    private String secdId;
}
