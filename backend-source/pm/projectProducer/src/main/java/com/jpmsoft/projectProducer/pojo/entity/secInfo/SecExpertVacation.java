package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 专家请假申请
 *
 * @Author: 李文涛 aaron
 * @date: 2023/8/1 9:46
 */
@Data
@TableName("Sec_Expert_Vacation")
public class SecExpertVacation extends BaseEntity {

    private String exId;
    @JsonProperty("pId")
    private String pId;
    private String secId;
    //选取记录ID
    private String srId;
    //请假开始时间
    private Date sevStartTime;
    //请假结束时间
    private Date sevEndTime;
    //总天数
    private Integer sevDays;
    //申请时间
    private Date sevTime;
    //原因说明
    private String sevReason;
    private String teId;

}
