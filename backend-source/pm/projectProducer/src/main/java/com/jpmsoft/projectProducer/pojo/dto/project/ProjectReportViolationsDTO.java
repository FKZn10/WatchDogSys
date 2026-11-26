package com.jpmsoft.projectProducer.pojo.dto.project;


import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.FileFields;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("Project_Report_Violations")
public class ProjectReportViolationsDTO extends BaseFileRequest {

    @ProcessFields(type = ProcessFieldEnum.id)
    private String prvId;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String prvCreateUid;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date prvCreateTime;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String prvCreateName;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer prvState;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date prvStateTime;


    @JsonProperty("pId")
    private String pId;
    private String prvCompany;
    private String prvQuestion;
    private String prvRemark;
    @FileFields(index = 1)
    private String prvPhoto;
    @FileFields(index = 2)
    private String prvIdCardImg;
    private String prvPhone;

    @FileFields(index = 1, type = "prvPhotoIds")
    private String prvPhotoIds;
    @FileFields(index = 2, type = "prvIdCardImgIds")
    private String prvIdCardImgIds;

    private String smsCode;
    @JsonProperty("pName")
    private String pName;
}
