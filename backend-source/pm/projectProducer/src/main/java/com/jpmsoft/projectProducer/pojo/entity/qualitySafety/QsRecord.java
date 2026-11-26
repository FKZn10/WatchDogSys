package com.jpmsoft.projectProducer.pojo.entity.qualitySafety;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 质量安全检查记录
 */
@Data
@TableName("Qs_Record")
public class QsRecord {

    @TableId(type = IdType.INPUT)
    private String qsrId;

    /**
     * Qs_Type表主键id
     */
    private String qstId;

    /**
     * 项目ID
     */
    private String pId;

    /**
     * 合同ID
     */
    private String conId;

    /***
     * 质量安全检查类型：JPMsoft_ZLAQJCLX
     * 01：日常检查
     * 02：例行检查
     * 03：第三方检查
     * 04：督导抽查
     * 05：专项检查
     */
    private String qsrType;

    /**
     * 检查位置
     */
    private String qsrPosition;

    /**
     * 问题描述
     */
    private String qsrDes;

    /**
     * 问题性质：JPMsoft_WTXZ
     * 01：整体
     * 02：部分
     * 03：局部
     * 04：个别
     */
    private String qsrProblemNature;

    /**
     * 整改要求
     */
    private String qsrRectifyRequire;

    /**
     * 检查时间
     */
    private Date qsrTime;

    /**
     * 问题数量
     */
    private Integer qsrQuestionNum;

    /**
     * 要求整改时间
     */
    private Date qsrRectifyRequireTime;
    /**
     * 实际整改时间
     */
    private Date qsrRectifyActualTime;
    /**
     * 整改责任人ID
     */
    private String qsrRectifyUid;

    /**
     * 整改责任人姓名
     */
    private String qsrRectifyUame;

    /**
     * 整改督办人ID
     */
    private String qsrSuperviseUid;

    /**
     * 整改督办人姓名
     */
    private String qsrSuperviseUname;

    /**
     * 是否短信通知
     * 0：否
     * 1：是
     */
    private Integer qsrShortMessage;

    /**
     * 整改前照片
     */
    private String qsrPic;

    /**
     * 整改后照片
     */
    private String qsrRectifyPic;
    private String qsrCreateUid;
    private String qsrCreateUname;
    private Date qsrCreateTime;
    //修改人
    private String qsrUpdateUid;
    //修改时间
    private Date qsrUpdateTime;
    /**
     * 审批状态
     */
    private Integer qsrState;

    /**
     * qsrState_Time
     */
    private Date qsrStateTime;

    /**
     * 类型
     * 0：质量
     * 1：安全
     */
    private Integer qsrCategory;

    /**
     * 实际整改人（文本输入）
     */
    private String qsrRectifyRealUname;

    /**
     * 整改回复
     */
    private String qsrRectifyReply;

    /**
     * 整改完成时间
     */
    private Date qsrRectifyFinishTime;

    /**
     * 巡查对象
     */
    private String qsrPatrolObject;
    /**
     * 巡检任务ID
     */
    private String ptId;
    /**
     * 问题类型（监理或施工）
     */
    private String qsrProblemType;
    /**
     * 责任单位
     */
    private String qsrDutyCpy;
    /**
     * 整改状态 JPMsoft_ZGZT
     * 待整改	01
     * 整改中	02
     * 整改完成	03
     */
    private String qsrRectifyStatus;
    /**
     * 隐患等级
     */
    private String qsrHazardLevel;
    private String teId;
    //所在部位
    private String qsrLocation;
    //原因类型(可能导致事故类型)
    private String qsrReasonType;
    //安全隐患形成原因
    private String qsrFormation;

    private String qsrCid;
    private String qsrDid;
    private String qsrCnature;
    private String qsrFzCid;
    private Boolean qsrHf;
    private Boolean qsrHfHg;
    private String qsrXdUid;

    /**
     * 业务表id: 风险评价表id
     */
    private String bizId;

    private String qsrCcUser;

    private String qsrCcUserName;

    /**
     * 待办推送状态 01未推送  02已推送
     */
    private String qsrToDoStatus;
}
