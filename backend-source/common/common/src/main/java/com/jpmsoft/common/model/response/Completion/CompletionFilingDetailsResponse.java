package com.jpmsoft.common.model.response.Completion;

import lombok.Data;

@Data
public class CompletionFilingDetailsResponse {

    /**
     * 主键 id
     */
    private String id;

    /**
     * 主表 id
     */
    private String cfId;

    /**
     * 文件名
     */
    private String cfdName;

    /**
     * fileId
     */
    private String cfdFile;

    private String cfdFileIds;
}
