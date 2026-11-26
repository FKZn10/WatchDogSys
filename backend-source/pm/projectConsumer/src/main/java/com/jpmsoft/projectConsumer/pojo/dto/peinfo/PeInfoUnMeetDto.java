package com.jpmsoft.projectConsumer.pojo.dto.peinfo;

import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.io.Serializable;

@Data
public class PeInfoUnMeetDto extends BaseFileRequest implements Serializable {

    private static final long serialVersionUID = -7992326228395118833L;

    private String peConId;
    private String peProjectId;
    private Integer peYear;
    private Integer peQuarter;
    private String peType;
    private String peCompanyId;
    private String peConType;
    private String peStage;
    private String peUnitId;
    private String peJoin;
    private String peState;
    private String peCompetentDepartment;
    private Integer pecFlowState;
    private String eliminateLastMeeting;
    private String whetherPage;
}
