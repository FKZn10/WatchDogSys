package com.jpmsoft.projectProducer.pojo.entity.oa;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 考勤人员
 */
@Data
@TableName("OA_Attendance_Member")
public class OaAttendanceMember {

    @TableId(type = IdType.INPUT)
    private String amId;
    private String agId;
    private String amUid;
    private String amUname;
    private String ppId;
    private String teId;
}
