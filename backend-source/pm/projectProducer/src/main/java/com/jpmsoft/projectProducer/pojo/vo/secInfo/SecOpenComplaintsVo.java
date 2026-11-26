package com.jpmsoft.projectProducer.pojo.vo.secInfo;

import com.jpmsoft.common.model.BaseFileResponse;
import com.jpmsoft.common.model.response.SecInfo.SecProjectResponse;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 开评标质疑投诉处理
 *
 * @Author: 李文涛 aaron
 * @date: 2022/11/28 13:51
 */
@Data
public class SecOpenComplaintsVo extends BaseFileResponse {

    private static final long serialVersionUID = 1743525561624068001L;
    private String id;
    private String createUname;
    private String createUid;
    private Date createTime;
    private Integer state;
    private Date stateTime;

    private String secId;
    private Integer socQuestion;
    private String socQuestioner;
    private String socQuestionerTelephone;
    private String socQuestionQuestion;
    private String socRespondent;
    private String socReplyContent;
    private String socRemark;

    private String secName;
    private String secCode;
    private String secType;
    private List<SecProjectResponse> secProjects;
    /*
     * 项目规模
     */
    private String projectCategory;
    /*
     * 发起单位
     */
    private String projectInitiatingUnit;
    private String projectOwner;
}