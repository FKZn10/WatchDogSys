package com.jpmsoft.projectConsumer.pojo.dto.blxw;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class BlxwInfoDto extends BaseFileDto {
    private String id;
    //编号
    private String blxwNumber;
    //项目名称
    private String blxwProjectId;
    //违规标段
    private String blxwSectionId;
    //违规合同
    private String blxwContractId;
    //开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date blxwStartTime;
    //结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date blxwEndTime;
    //责任单位
    private String blxwResponsibleUnitId;
    //责任单位负责人
    private String blxwResponsibleUnitLeaderId;
    //约谈时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date blxwTalkTime;
    //约谈地点
    private String blxwTalkPlace;
    //不良行为
    private String blxwTypeIds;
    //不良行为等级
    private String blxwLevel;
    //扣分情况
    private Double blxwDeductScore;
    //拟处理意见
    private String blxwMeasures;
    //处理方式
    private String blxwHandleType;
    //整改时限
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date blxwRectifyTime;
    //具体要求
    private String blxwRectifyRequire;
    // 是否历史记录
    private Boolean isHistory;
    //班子会议题附件
    private String supportingAnnex;
    private String supportingAnnexFileIds;
    private String supportingDocument;
    //证明文件ids
    private String supportingDocumentFileIds;
    //处理状态 0 未处理 1 已处理
    private Integer blxwState;
    private String blxwContent;
    //合同中单位类型
    private String blxwContractUnitType;
    //应用项目类别
    private String blxwProjectType;
    private Integer state;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date stateTime;
    private String createUid;
    private String createUname;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
}