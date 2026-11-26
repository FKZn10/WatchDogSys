package com.jpmsoft.userProducer.pojo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 考勤组
 */
@Data
@TableName("OA_Attendance_Group")
public class OaAttendanceGroup {

    @TableId(type = IdType.INPUT)
    private String agId;
    private String agName;
    private String pId;
    private Integer agScope;
    private String agLongitude;
    private String agLatitude;
    private String agRemark;
    private String agCreateUid;
    private String agCreateUname;
    private Date agCreateTime;
    private Integer agState;
}
