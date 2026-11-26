package com.jpmsoft.projectProducer.pojo.entity.oa;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 工作日设置
 */
@Data
@TableName("OA_Attendance_Weekday")
public class OaAttendanceWeekday {

    @TableId(type = IdType.INPUT)
    private String awId;
    private String agId;
    private String awWeek;
    private String awStartAm;
    private String awEndAm;
    private String awStartPm;
    private String awEndPm;
    private Float awHours;
    private Integer awOrder;
    private String awRemark;
    private String teId;
}
