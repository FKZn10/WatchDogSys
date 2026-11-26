package com.jpmsoft.projectProducer.pojo.entity.oa;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 考勤记录
 */
@Data
@TableName("Oa_Attendance")
public class OaAttendance {

    @TableId(type = IdType.INPUT)
    private String aId;
    private String agId;
    private String aType;
    private String aUid;
    private String aUname;
    private String cId;
    private Date aTime;
    private String aPic;
    private Date aCreateTime;
    private String aLongitude;
    private String aLatitude;
    private String aWeek;
    private String aStartAm;
    private String aEndAm;
    private String aStartPm;
    private String aEndPm;
    private Float aHours;
    private String aResult;
    private String aWay;
    private Integer aIsFieid;
    private String aRemark;
    private String teId;
}
