package com.jpmsoft.projectProducer.pojo.entity.oa;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 变更会议通知
 *
 * @Author: 李文涛 aaron
 * @date: 2024/10/29 13:58
 */
@Data
@TableName("Change_Meeting_Notification")
public class ChangeMeetingNotification extends BaseEntity {

    private String cmnName;
    private String cmnType;
    private String cmnIsRoom;
    private Date cmnStartTime;
    private Date cmnEndTime;
    private String cmnLocations;
    private String cmnRoom;
    private String cmnSigner;
    private String cmnIssuingTechnology;
    private String cmnPerson;
    private String cmnPhone;
    private String cmnInternalParticipants;
    private String cmnExternalParticipants;
    private String cmnMainContent;
    private String cmnRemark;
    private String cmnBriefingContent;
    private String cmnBriefingAnnex;
    /**
     * 会议锁定状态
     * 1 未锁定 2 锁定  3 审核解锁
     */
    private Integer cmnCsState;
}
