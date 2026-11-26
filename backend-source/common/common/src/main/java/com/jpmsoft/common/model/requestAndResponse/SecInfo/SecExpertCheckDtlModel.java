package com.jpmsoft.common.model.requestAndResponse.SecInfo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 专家考核内容
 *
 * @Author: 李文涛 aaron
 * @date: 2023/8/1 9:50
 */
@Data
public class SecExpertCheckDtlModel {

    private String id;
    private String ecId;
    private String secdContent;

    // 中间表id
    private String ecdId;

    private Integer selected;

    private String scdId;

    // 1 是 扣分 2 否
    private Integer secdResult;
    private BigDecimal secdScore;
    private Integer secdSort;

    private String parentId;

    // 满分值
    private BigDecimal fullScore;

    private List<SecExpertCheckDtlModel> subItems;

}
