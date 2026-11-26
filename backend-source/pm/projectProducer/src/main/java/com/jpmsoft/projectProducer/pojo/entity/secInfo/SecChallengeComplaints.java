package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 中标质疑投诉处理
 */
@Data
@TableName("Sec_Challenge_Complaints")
public class SecChallengeComplaints {

    @TableId(type = IdType.INPUT)
    private String sccId;
    private String secId;
    //是否有质疑投诉
    private Integer sccQuestion;
    //投诉质疑人
    private String sccQuestioner;
    //投诉质疑人联系方式
    private String sccQuestionerTelephone;
    //质疑投诉问题
    private String sccQuestionQuestion;
    //质疑投诉问题回复人
    private String sccRespondent;
    //质疑投诉问题回复内容
    private String sccReplyContent;
    private String sccRemarks;
    private String sccCreateUid;
    private String sccCreateUname;
    private Date sccCreateTime;
    private Integer sccState;
    private Date sccStateTime;
    private String teId;
    //中标质疑文件
    private String sccHandleDoc;
    //开评标结果是否有更新
    private Integer sccResultUpdate;
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