package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.FileFields;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 专家考核评分
 *
 * @Author: 李文涛 aaron
 * @date: 2023/8/1 9:49
 */
@Data
@TableName("Sec_Expert_Check")
public class SecExpertCheck extends BaseEntity {

    private String exId;
    @JsonProperty("pId")
    private String pId;

    @JsonProperty("pName")
    private String pName;
    private String secId;

    private String secName;

    // 专家选取记录表id  评审类型  考核人  就是这个表的创建人  考核时间  创建时间
    private String srId;

    // 评审类型
    private String seMode;
    // 考核人
    private String examineUid;

    private String examineUname;
    //评标日期
    private Date ecAssessTime;
    //考核评分日期
    private Date ecTime;
    private String teId;


    private String seRemark;

    private String expertCheckFile;
    private String expertCheckFileIds;

    private String status;

    // 甲方评委满分情况说明
    private String clientReviewerFullScoreExplanation;
}
