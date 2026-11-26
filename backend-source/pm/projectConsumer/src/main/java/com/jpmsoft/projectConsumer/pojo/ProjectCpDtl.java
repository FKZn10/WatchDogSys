package com.jpmsoft.projectConsumer.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * 项目建设程序详情
 * cpdCplId 和 cpdParentId组成上下级关系
 */
@Data
@TableName("Project_Cp_Dtl")
public class ProjectCpDtl {

    @TableId(type = IdType.INPUT)
    private String cpdId;
    private String cpId;
    private String cpdName;
    private String cpdCode;
    private String cpdParentId;
    private String cpdType;
    private Integer cpdDays;
    private String cpdProId;
    private String cpdAdministration;
    private String cpdAdministrationUrl;
    private String cpdRemark;
    private String cpdHtml;
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date cpdPlanStartTime;
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date cpdPlanEndTime;
    private String cpdPersonId;
    private String cpdPersonName;
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date cpdStartTime;
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date cpdEndTime;
    private Integer cpdState;
    private String cpdDoc;
    private String cpdCreateUid;
    private String cpdCreateUname;
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date cpdCreateTime;
    private Integer cpdSort;
    private String cpdCplId;
    private String cpdAdministrationUrlH;
    private String tCode;
    private String cpdFileName;
    private String cpdFileCode;
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date cpdReplyTime;
    private Integer cpdStatus;
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date cpdCerEndTime;
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date stateTime;
    private String teId;
    private String cpdOtherFile;

    private String cpdFileType;

    private String gsId;
    private String taskImportance;
}
