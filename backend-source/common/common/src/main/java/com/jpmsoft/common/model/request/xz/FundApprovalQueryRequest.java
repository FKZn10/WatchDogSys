package com.jpmsoft.common.model.request.xz;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2024/7/17 10:50
 * @Description:
 * @Version: 1.0
 */
@Data
public class FundApprovalQueryRequest implements Serializable {

    private static final long serialVersionUID = -1591264997818262196L;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 计划年度
     */
    private String planYear;

    /**
     * 申报批次(JPMSoft_ZJSBPC)
     */
    private String batchNum;

    private String createId;

    private String approveStatus;

    private Integer pageNum;

    private Integer pageSize;
}
