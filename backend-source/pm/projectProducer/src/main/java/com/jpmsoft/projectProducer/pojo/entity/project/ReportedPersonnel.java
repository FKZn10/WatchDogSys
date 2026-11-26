package com.jpmsoft.projectProducer.pojo.entity.project;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 项目人员报备
 */
@Data
@TableName("Reported_Personnel")
public class ReportedPersonnel {

    @TableId(type = IdType.INPUT)
    private String rpId;
    private String pId;
    private String rpType;
    private String rpRemark;
    private String rpCreateUid;
    private String rpCreateUname;
    private Date rpCreateTime;
    private Integer rpState;
    private Date rpStateTime;
    private String teId;
    private String rpChangeType;

    //region 北林人员变更
    /**
     * 会议结果是否同意变更
     */
    private String rpMeetingAgree;
    /**
     * 变更前用户
     */
    private String rpOriginalUser;
    /**
     * 变更前岗位
     */
    private String rpOriginalPosition;
    /**
     * 变更后用户
     */
    private String rpChangeUser;
    /**
     * 变更后岗位
     */
    private String rpChangePosition;
    //endregion

    private String rpCode;
    @TableField(value = "rp_apply_cid")
    private String rpApplyCId;
    private Date rpApplyDate;
    private String rpReason;
    private String rpOriginalScope;
    private String rpChangeCertificate;
    private String rpChangeExp;
    private String rpChangeScope;
    private Date rpChangeDate;
    private String rpIsFine;
    private Date rpMeetingDate;
    private String rpDId;
    private String rpChangeDId;
    private String rpChangeFile;
    private String rpOriginalFile;
    private String rpFineMoeny;
    private String rpFineBasis;
    private String rpOriginalUserName;
    private String rpChangeUserName;
    private String conId;
    private String rpApplicant;
    private String rpJobType;
    private String rpCertNumber;
    private String rpContact;
    private String rpCardFile;
    private String rpUnitId;

    /**
     * 申请人id，关联sys_user，用于申报通过后向申请人发送待办
     */
    @TableField(value = "rpApplicantId")
    private String rpApplicantId;
}
