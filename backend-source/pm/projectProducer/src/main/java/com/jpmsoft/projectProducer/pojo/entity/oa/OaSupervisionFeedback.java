package com.jpmsoft.projectProducer.pojo.entity.oa;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 整改反馈记录
 */
@Data
@TableName("OA_Supervision_Feedback")
public class OaSupervisionFeedback {
    @TableId(type = IdType.INPUT)
    private String sfId;
    private String stId;
    private String sfRemark;
    private Date sfTime;
    private String sfCreateUid;
    private String sfCreateUname;
    private Date sfCreateTime;
    private Integer sfState;
    private Date sfStateTime;
    private String sfDId;
    private String sfDPerson;
    private String sfDPhone;
    private String sfEvaluate;
    private String sfSituation;
    private String sfProblem;
    private String sfMeasures;
    private String sfRectification;
    private String teId;
}
