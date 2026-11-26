package com.jpmsoft.common.model.request.Completion;

import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

@Data
public class CompletionFilingDetailsRequest  extends BaseFileRequest {

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
