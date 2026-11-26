package com.jpmsoft.projectConsumer.pojo.dto.secInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.FileFields;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecSelectionRecordDtlModel;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 专家考核评分
 *
 * @Author: 李文涛 aaron
 * @date: 2023/8/1 9:49
 */
@Data
public class SecExpertCheckDTO extends BaseFileRequest {

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

    @FileFields(index = 1)
    private String expertCheckFile;
    @FileFields(index = 1,type = "fileIds")
    private String expertCheckFileIds;

    private String exId;
    @JsonProperty("pId")
    private String pId;
    private String secId;
    private Date ecAssessTime;
    private Date ecTime;

    private String exName;
    private String secName;
    @JsonProperty("pName")
    private String pName;
    @JsonProperty("pCode")
    private String pCode;
    // 专家选取记录表Id
    private String srId;

    private String seMode;

    private String examineUid;

    private String seRemark;

    private String examineUname;


    private String clientReviewerFullScoreExplanation;

    private List<SecExpertCheckDtlDTO> secExpertCheckDtls;

    // 专家信息  含  考核评分
    private List<SecSelectionRecordDtlModel> secSelectionRecordDtls;



}
