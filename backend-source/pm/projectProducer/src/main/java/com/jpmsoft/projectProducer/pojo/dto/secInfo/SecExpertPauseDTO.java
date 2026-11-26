package com.jpmsoft.projectProducer.pojo.dto.secInfo;

import com.jpmsoft.common.annotations.ClassInfo;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 专家请假申请
 *
 * @Author: 李文涛 aaron
 * @date: 2023/8/1 9:46
 */
@Data
public class SecExpertPauseDTO extends BaseFileRequest {

    @ProcessFields(type = ProcessFieldEnum.id)
    private String id;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer state;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date stateTime;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String createUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String createUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date createTime;

    private String exName;
    private String exId;
    private Date sepStartTime;
    private Date sepEndTime;
    private Integer sepDays;
    private Date sepTime;
    private String sepReason;

    private Integer sepDaysYear;
    @ClassInfo(title = "申请停用时长", type = "string", value = "sepDaysYears", dbValue = " mn.Sep_Days_Year ", no = 3)
    private List<String> sepDaysYears;

    private String exApplicantId;

    /**
     * 当年是否已禁用
     */
    private String sepDisabledYear;

    private Integer year;
    @ClassInfo(title = "年度", type = "string", value = "years", dbValue = " ex.year ", no = 1)
    private List<String> years;
    @ClassInfo(title = "姓名", type = "string", value = "exNames", dbValue = " ex.Ex_Name ", no = 2)
    private List<String> exNames;
    @ClassInfo(title = "流程状态", type = "dictionary", value = "states", dbValue = "mn.state",dictionary = "JPMsoft_LCZT", no = 7)
    private List<String> states;
    @ClassInfo(title = "开始时间", type = "date", value = "sepStartTimes", dbValue = " mn.Sep_Start_Time ", no = 4)
    private List<String> sepStartTimes;
    @ClassInfo(title = "结束时间", type = "date", value = "sepEndTimes", dbValue = " mn.Sep_End_Time ", no = 5)
    private List<String> sepEndTimes;
    @ClassInfo(title = "创建时间", type = "date", value = "createTimes", dbValue = " mn.Create_Time ", no = 6)
    private List<String> createTimes;
}
