package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 专家暂停申请
 *
 * @Author: 李文涛 aaron
 * @date: 2023/8/1 9:46
 */
@Data
@TableName("Sec_Expert_Pause")
public class SecExpertPause extends BaseEntity {

    private String exId;
    //暂停开始时间
    private Date sepStartTime;
    //暂停结束时间
    private Date sepEndTime;
    //总天数
    private Integer sepDays;
    //申请时间
    private Date sepTime;
    //原因说明
    private String sepReason;
    private String teId;

    /**
     * 当年请假天数
     */
    private Integer sepDaysYear;

    /**
     * 当年是否已禁用
     */
    private String sepDisabledYear;
}
