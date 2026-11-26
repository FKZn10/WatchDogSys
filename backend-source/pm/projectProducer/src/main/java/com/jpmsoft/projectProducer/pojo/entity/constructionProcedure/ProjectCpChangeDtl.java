package com.jpmsoft.projectProducer.pojo.entity.constructionProcedure;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 项目建设程序详情
 * cpdCplId 和 cpdParentId组成上下级关系
 */
@Data
@TableName("Project_Cp_Change_Dtl")
public class ProjectCpChangeDtl {

    @TableId(type = IdType.INPUT)
    private String id;
    private String cpId;
    private String cpdId;
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
    private Date cpdPlanStartTime;
    private Date cpdPlanEndTime;
    private String cpdPersonId;
    private String cpdPersonName;
    private Date cpdStartTime;
    private Date cpdEndTime;
    private Integer cpdState;
    private String cpdDoc;
    private String cpdCreateUid;
    private String cpdCreateUname;
    private Date cpdCreateTime;
    private Integer cpdSort;
    private String cpdCplId;
    private String cpdAdministrationUrlH;
    private String tCode;
    private String cpdFileName;
    private String cpdFileCode;
    private Date cpdReplyTime;
    private Integer cpdStatus;
    private Date cpdCerEndTime;
    private Date stateTime;
    private String teId;
}
