package com.jpmsoft.projectProducer.pojo.entity.secInfo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 答疑补遗
 */
@Data
@TableName("Sec_Answer_Question")
public class SecAnswerQuestion {
    @TableId(type = IdType.INPUT)
    private String aqId;
    //标段id
    private String secId;

    private String pId;
    //答疑澄清标题
    private String aqTitle;
    //对文件澄清与修改的主要内容
    private String aqContent;
    //变更澄清文件类型
    private String aqDocType;
    //变更澄清事项类别
    private String aqItemType;
    //其他文件
    private String aqOtherDoc;
    //是否更改公告时间
    private Integer aqNoticeTimeChange;
    private String aqCreateUid;
    private String aqCreateUname;
    private Date aqCreateTime;
    private Integer aqState;
    private Date aqStateTime;
    private String aqRemark;
    //问题澄清通知
    private String aqClarificationNotice;
    //问题的澄清
    private String aqClarification;
    //（踏勘现场）是否组织勘探现场
    private Integer aqSurveyWhether;
    //（踏勘现场）踏勘时间
    private Date aqSurveyTime;
    //（踏勘现场）踏勘地点
    private String aqSurveyPlace;
    //（踏勘现场）备注
    private String aqSurveyRemark;
    //（投标预备会）是否召开投标预备会
    private Integer aqMeetingWhether;
    //（投标预备会）召开时间
    private Date aqMeetingTime;
    //（投标预备会）召开地点
    private String aqMeetingPlace;
    //（投标预备会）备注
    private String aqMeetingRemark;
    //是否更改开标时间
    private Boolean aqIsChangeTime;
    //是否更改开标地点
    private Boolean aqIsChangeLocation;
    //开标时间
    private Date aqBidTime;
    //开标地点
    private String aqLocation;
    //延期截标(是:1/否:0)
    private Integer aqDelayStopBids;
    //是否有答疑
    private Boolean aqIsThere;
    //呈批事项
    private String aqApprovalMatters;
    private String teId;
    //质疑时间
    private Date aqTime;
    //质疑状态
    private String aqStatus;
    //答复时间
    private Date aqAnswerTime;
    //答复内容
    private String aqAnswerContent;
    //答复人姓名
    private String aqAnswerUname;
    //质疑附件
    private String aqFile;
    //回复附件
    private String aqAnswerFile;
    //最终回复内容
    private String aqFinalRes;

    // 计划开标时间
    private Date aqPlanOpenTime;
    //计划开标地点
    private String aqPlanOpenLocation;
    //答疑发布日期
    private Date aqPublishTime;

    //答疑发布网址
    private String aqPublishUrl;
    /*
     * 项目规模
     */
    private String projectCategory;
    /*
     * 发起单位
     */
    private String projectInitiatingUnit;
}