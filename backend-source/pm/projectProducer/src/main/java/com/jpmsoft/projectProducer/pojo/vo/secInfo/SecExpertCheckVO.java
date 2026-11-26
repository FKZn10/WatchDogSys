package com.jpmsoft.projectProducer.pojo.vo.secInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileResponse;
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
public class SecExpertCheckVO extends BaseFileResponse {

    private String id;
    private Integer state;
    private Date stateTime;
    private String createUid;
    private String createUname;
    private Date createTime;
    private String exId;
    @JsonProperty("pId")
    private String pId;
    private String secId;
    private Date ecAssessTime;
    private Date ecTime;

    private String exName;
    private String exIdType;
    private String exIdNumber;
    private String secName;
    @JsonProperty("pName")
    private String pName;
    @JsonProperty("pCode")
    private String pCode;
    private String seMode;
    private String examineUid;

    private String examineUname;

    private List<SecExpertCheckDtlVO> secExpertCheckDtls;

    private String seRemark;


    private String expertCheckFile;
    private String expertCheckFileIds;

    private String clientReviewerFullScoreExplanation;

    private List<SecSelectionRecordDtlModel> secSelectionRecordDtls;
}
