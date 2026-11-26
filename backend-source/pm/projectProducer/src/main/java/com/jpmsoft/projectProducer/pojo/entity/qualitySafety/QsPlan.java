package com.jpmsoft.projectProducer.pojo.entity.qualitySafety;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 质量安全检查计划
 */
@Data
@TableName("Qs_Plan")
public class QsPlan {

    @TableId(type = IdType.INPUT)
    private String qspId;

    /**
     * 计划编号
     */
    private String qspCode;

    /**
     * 项目类型
     */
    private String qspProjectType;

    /**
     * 计划类别
     */
    private Integer qspType;

    /**
     * 巡检类型
     */
    private String qspCategory;

    /**
     * 巡检开始时间
     */
    private Date qspStartTime;

    /**
     * 巡检结束时间
     */
    private Date qspEndTime;

    /**
     * 计划名称
     */
    private String qspName;
    private Integer qspOrder;

    /**
     * 巡检内容说明
     */
    private String qspContent;
    private String qspCreateUid;
    private String qspCreateUname;
    private Date qspCreateTime;

    /**
     * 审批状态
     */
    private Integer qspState;

    /**
     * 审批时间
     */
    private Date qspStateTime;
    private String teId;


    private String qspCid;
    private String qspDid;
    private String qspCnature;
    private String qspSbUid;
    private String qspSbDid;
    private String qspSbCid;
    private Date qspSbTime;
    private String qspAttr;
    /**
     *回复状态：1-已回复;2-未回复
     */
    private Integer replyState;
}
