package com.jpmsoft.projectProducer.pojo.entity.oa;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 考勤地点
 */
@Data
@TableName("OA_Attendance_Location")
public class OaAttendanceLocation {

    @TableId(type = IdType.INPUT)
    private String alId;
    private String agId;
    private String alName;
    private Integer alScope;
    private String alLongitude;
    private String alLatitude;
    private String alRemark;
    private String alCreateUid;
    private String alCreateUname;
    private Date alCreateTime;
    private Integer alState;
    private String teId;
}
