package com.jpmsoft.common.model.requestAndResponse;

import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ResumeWorkModel extends BaseFileDto {
    private String id;
    private String projectId;
    private String unitId;
    private String isMeeting;
    private String isMeetingWorkDoc;
    private String isPlan;
    private String isPlanWorkDoc;
    private String isEmployeeMeeting;
    private String isEmployeeMeetingWorkDoc;
    private String isEducation;
    private String isEducationWorkDoc;
    private String isDispatch;
    private String isDispatchWorkDoc;
    private String isCheck;
    private String isCheckWorkDoc;
    private String isSign;
    private String isSignWorkDoc;
    private String isReport;
    private String isReportWorkDoc;
}