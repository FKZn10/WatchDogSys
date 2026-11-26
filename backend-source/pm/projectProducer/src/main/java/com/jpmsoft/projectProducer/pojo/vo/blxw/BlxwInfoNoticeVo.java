package com.jpmsoft.projectProducer.pojo.vo.blxw;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
public class BlxwInfoNoticeVo extends BlxwInfoVo{
    private String id;
    //编号
    private String blxwNumber;
    //项目名称
    private String blxwProjectId;
    private String blxwProjectName;
    //违规标段
    private String blxwSectionId;
    private String blxwSectionName;
    //违规合同
    private String blxwContractId;
    private String blxwContractName;
    //开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date blxwStartTime;
    //结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date blxwEndTime;
    //责任单位
    private String blxwResponsibleUnitId;
    private String blxwResponsibleUnitName;
    //责任单位负责人
    private String blxwResponsibleUnitLeaderId;
    private String blxwResponsibleUnitLeaderName;
    //约谈时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date blxwTalkTime;
    //约谈地点
    private String blxwTalkPlace;
    //不良行为
    private String blxwTypeIds;
    private String blxwTypeNames;
    //不良行为等级
    private String blxwLevel;
    //扣分情况
    private Double blxwDeductScore;
    //拟处理意见
    private String blxwMeasures;
    //处理方式
    private String blxwHandleType;
    //整改时限
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date blxwRectifyTime;
    //具体要求
    private String blxwRectifyRequire;
    //处理状态 0 未处理 1 已处理
    private Integer blxwState;
    private String blxwSupportingDocument;
    // 是否历史记录
    private Boolean blxwIsHistory;
    private String closeStatus;
    private String supportingDocument;
    //班子会议题附件
    private String supportingAnnex;
    private Integer state;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date stateTime;
    private String createUid;
    private String createUname;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    private  String sourceId;
    private  String sourceCode;
    private  String noticeInfoCode;
    private  String noticeInfoContent;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date noticeInfoTime;
    // 是否历史记录
    private Boolean isHistory;
}