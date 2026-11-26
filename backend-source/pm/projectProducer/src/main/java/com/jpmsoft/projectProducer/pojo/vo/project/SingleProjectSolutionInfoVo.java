package com.jpmsoft.projectProducer.pojo.vo.project;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class SingleProjectSolutionInfoVo {
    private String projectName;
    /**
     * 功能编码:company contract member material plan quality safety
     */
    private String functionCode;

    /**
     * 数量
     */
    private BigDecimal sum;

    /**
     * 功能地址
     */
    private String url;
}
