package com.jpmsoft.projectConsumer.pojo.dto.secInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.util.List;

/**
 * 开评标质疑投诉处理
 *
 * @Author: 李文涛 aaron
 * @date: 2022/11/28 13:51
 */
@Data
public class SecOpenComplaintsDto extends BaseFileDto {

    private static final long serialVersionUID = 1743525561624068001L;
    private String secId;
    private Integer socQuestion;
    private String socQuestioner;
    private String socQuestionerTelephone;
    private String socQuestionQuestion;
    private String socRespondent;
    private String socReplyContent;
    private String socRemark;

    @JsonProperty("pName")
    private String pName;
    private String secName;
    private String secBiddingType;
    private List<String> createTimes;
}
