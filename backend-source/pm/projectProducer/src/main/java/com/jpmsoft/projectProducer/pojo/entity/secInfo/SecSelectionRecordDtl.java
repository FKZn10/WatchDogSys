package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 专家选取记录详情
 */
@Data
@TableName("Sec_Selection_Record_Dtl")
public class SecSelectionRecordDtl {
    @TableId(type = IdType.INPUT)
    private String srdId;
    // 专家id
    private String exId;
    // 抽取取记录id
    private String srId;
    //参与会议/咨询次数
    private Integer srdCount;
    /**
     * 选取类型(抽取|自选)
     */
    private String srdType;
    //是否需要回避
    private Integer srdAvoid;
    //是否按时参加
    private Integer srdAttend;
    //是否请假
    private Integer srdVacation;
    //抽取时间
    private Date srdTime;
    //确认时间
    private Date srdConfirmTime;
    //技术类专家申请专业详情
    private String srdEsType;
    // 考核评分表 id
    private String ecId;
}
