package com.jpmsoft.projectProducer.pojo.entity.blxw;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("Blxw_Info")
public class BlxwInfo extends BaseEntity implements Serializable {
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
    private Date blxwStartTime;
    //结束时间
    private Date blxwEndTime;
    //责任单位
    private String blxwResponsibleUnitId;
    //责任单位负责人
    private String blxwResponsibleUnitLeaderId;
    //约谈时间
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
    private Date blxwRectifyTime;
    //具体要求
    private String blxwRectifyRequire;
    private String supportingDocument;
    //处理状态
    private Integer blxwState;
    private String closeStatus;
    //班子会议题附件
    private String supportingAnnex;
    // 是否历史记录
    private Boolean isHistory;
    private String blxwContent;
    //合同中单位类型
    private String blxwContractUnitType;
    //应用项目类别
    private String blxwProjectType;
}