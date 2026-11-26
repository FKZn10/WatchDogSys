package com.jpmsoft.common.model.requestAndResponse.Project;

import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class PreProjectSolutionModel extends BaseFileDto {
    private static final long serialVersionUID = -745436560135322683L;
    private String id;
    private String versionId;
    private Integer lastest;
    private String projectId;
    private String projectName;
    private String projectRegisterInfoId;
    private String remark;
    private String declarer;
    private BigDecimal selfScore;
    private BigDecimal scoreReal;
    private Date declarationTime;
    private String auditStatus;
    private String auditComment;
    private ProjectRegisterInfoModel projectRegisterInfo;
    private String solutionFileIds;
    private String solutionFileId;
    private String solutionId;
    private List<PreProjectSolutionDetailsModel> preProjectSolutionDetails;
}
