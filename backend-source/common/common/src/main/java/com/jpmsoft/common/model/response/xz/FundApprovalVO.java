package com.jpmsoft.common.model.response.xz;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2024/8/29 16:08
 * @Description:
 * @Version: 1.0
 */
@Data
public class FundApprovalVO implements Serializable {

    private static final long serialVersionUID = 4111717855908414856L;

    private String id;

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

    /**
     * 附件
     */
    private String fileRelationId;

    /**
     * 数量
     */
    private long totalNum;

    /**
     * 总投资估算(单位：元)
     */
    private BigDecimal totalBudget;


    private String approveStatus;

    private String createId;

    private String createName;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createTime;

    private List<ProjectApplicationVO> details;
}
