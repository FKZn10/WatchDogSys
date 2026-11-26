package com.jpmsoft.projectConsumer.pojo.dto.secInfo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 专家考核内容
 *
 * @Author: 李文涛 aaron
 * @date: 2023/8/1 9:50
 */
@Data
public class SecExpertCheckDtlDTO {

    private String id;
    private String ecId;
    private String secdContent;
    // 1 是  2 否
    private Integer secdResult;
    private BigDecimal secdScore;
    private Integer secdSort;
}
