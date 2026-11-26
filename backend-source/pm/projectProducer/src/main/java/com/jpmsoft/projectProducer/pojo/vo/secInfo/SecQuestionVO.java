package com.jpmsoft.projectProducer.pojo.vo.secInfo;

import com.jpmsoft.common.model.BaseFileResponse;
import com.jpmsoft.common.model.response.SecInfo.SecProjectResponse;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 质询
 *
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:35
 */
@Data
public class SecQuestionVO extends BaseFileResponse {
    private String sqId;
    private String secId;
    private String sqTenderee;
    private String sqBiddingType;
    private String sqScope;
    private Boolean sqWhether;
    private String sqResult;
    private String sqRemark;
    private String createUname;
    private String createUid;
    private Date createTime;
    private Integer state;
    private Date stateTime;

    private String secName;
    private String tendereeName;
    private List<SecProjectResponse> secProjects;
    private List<SecDefenseReviewVO> secDefenseReviews;
    private List<SecDefenseSupervisionVO> secDefenseSupervisions;
}
