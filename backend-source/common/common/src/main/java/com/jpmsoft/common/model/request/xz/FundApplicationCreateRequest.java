package com.jpmsoft.common.model.request.xz;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2024/8/29 16:08
 * @Description:
 * @Version: 1.0
 */
@Data
public class FundApplicationCreateRequest implements Serializable {

    private static final long serialVersionUID = 4111717895908413856L;

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
    private String fileIds;

    @JsonProperty("tId")
    private String tId;

    @JsonProperty("tCode")
    private String tCode;

    private String createId;

    private String createName;

    private List<ProjectApplicationCreateRequest> details;
}
