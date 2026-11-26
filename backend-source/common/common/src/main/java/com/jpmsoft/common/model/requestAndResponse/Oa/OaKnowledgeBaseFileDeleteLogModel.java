package com.jpmsoft.common.model.requestAndResponse.Oa;

import lombok.Data;

import java.util.Date;

/**
 * @author yb.Sun
 * @date 2024/10/24 10:17
 */
@Data
public class OaKnowledgeBaseFileDeleteLogModel extends OaKnowledgeBaseFileModel{
    private String id;
    private String deleteFunctionCode;
    private String deleteSourceId;
    private String deleteReason;
    private Date deleteTime;
    private String deleteUser;
    private String deleteUserID;
    private Integer deleteState;
    private Date deleteStateTime;
}
