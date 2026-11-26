package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 招标代理专家抽取记录详情（建管）
 */
@Data
@TableName("Sec_JG_Agency_Selection_Record_Dtl")
public class SecJGAgencySelectionRecordDtl {
    @TableId(type = IdType.INPUT)
    private String id;
    private String exId;
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
}
