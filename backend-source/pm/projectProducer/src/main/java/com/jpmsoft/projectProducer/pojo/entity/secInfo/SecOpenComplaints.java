package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

/**
 * 开评标质疑投诉处理
 *
 * @author: 李文涛 aaron
 * @date: 2022/11/11 10:19
 */
@Data
@TableName("Sec_Open_Complaints")
public class SecOpenComplaints extends BaseEntity {

    private String secId;
    //是否有质疑投诉
    private Integer socQuestion;
    //投诉质疑人
    private String socQuestioner;
    //投诉质疑人联系方式
    private String socQuestionerTelephone;
    //质疑投诉问题
    private String socQuestionQuestion;
    //质疑投诉问题回复人
    private String socRespondent;
    //质疑投诉问题回复内容
    private String socReplyContent;
    //备注
    private String socRemark;
    /*
     * 项目规模
     */
    private String projectCategory;
    /*
     * 发起单位
     */
    private String projectInitiatingUnit;
    private String projectOwner;
    private String teId;
}