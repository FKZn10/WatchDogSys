package com.jpmsoft.projectProducer.pojo.vo.qualitySafety;

import com.jpmsoft.common.model.ProjectCommonRes;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class QsRecordVo extends ProjectCommonRes implements Serializable {

    private String qsrId;

    /**
     * Qs_Type表主键id
     */
    private String qstId;

    /**
     * Qs_Type表名称
     */
    private String qstName;

    /**
     * 项目ID
     */
    private String pId;

    /**
     * 编号
     */
    private String ptCode;

    /**
     * 问题描述
     */
    private String qsrDes;

    /**
     * 问题类型
     */
    private String ptType;

    /**
     * 评估时间（检查日期）
     */
    private Date ptEvaluationTime;

    /**
     * 评估组成员ID（多个）
     */
    private String ptGroupMember;

    /**
     * 评估组成员姓名（文本输入）
     */
    private String ptGroupMemberName;

    /**
     * 问题数量
     */
    private Integer recordCount;

}
