package com.jpmsoft.projectProducer.pojo.entity.oa;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 请假申请
 */
@Data
@TableName("OA_Attendance_Leave")
public class OaAttendanceLeave {

    @TableId(type = IdType.INPUT)
    private String alId;
    private String alType;
    private Date alStartTime;
    private Date alEndTime;
    private Float alHours;
    private String alUid;
    private String alUname;
    private String alReason;
    private String alRemark;
    private String alCreateUid;
    private String alCreateUname;
    private Date alCreateTime;
    private Integer alState;
    private Date alStateTime;

    /**
     * 实际请假结束时间
     */
    private Date alActualEndTime;
    private String teId;
}
