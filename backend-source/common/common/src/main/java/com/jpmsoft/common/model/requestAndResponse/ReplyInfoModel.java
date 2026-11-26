package com.jpmsoft.common.model.requestAndResponse;

import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

@Data
public class ReplyInfoModel extends BaseFileDto {
    private  String sourceId;
    private  String sourceCode;
    private  String projectId;
    private  String conId;
    private  String riCode;
    private  String riTitle;
    private  String riContent;
    private  String fileRelationId;
}
