package com.jpmsoft.common.model.request.Oa;

import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author tianhelin
 * @date 2025/6/5
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OaKnowledgeBaseFileChangeRequest extends BaseFileRequest {
    private String id;
    private String kbfId;
    private String kbId;
    private String kbfName;
    private String kbfCreateUid;
    private String kbfCreateUname;
    private Date kbfCreateTime;
    private String kbfType;
    private Double kbfSize;
    private Integer kbfState;
    private String kbfCode;
    private String kbfPermission;
    private String kbfTitle;
    private String kbfSummary;
    private String kbfContent;
    private String kbfTag;
    private String kbfCover;
    private String kbfVersionNumber;
    private String kbfFile;
    private String approveStatus;
    private Date approveTime;
    private Date kbfPublishDate;
    private Integer status;
    private Date statusTime;
    private Date kbfPrintTime;
    private String executionStatus;
    private Boolean groupFiling;
    private Date effectiveDate;
    private Date expiringDate;
    private String effectiveDateType;

    private String kbfFileIds;
    private String kbfCoverIds;

    // 发文单位
    private String partyAsUnitName;
}
