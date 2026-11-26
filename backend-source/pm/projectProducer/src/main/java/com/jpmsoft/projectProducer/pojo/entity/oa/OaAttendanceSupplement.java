package com.jpmsoft.projectProducer.pojo.entity.oa;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 补卡申请
 */
@Data
@TableName("OA_Attendance_Supplement")
public class OaAttendanceSupplement {

    @TableId(type = IdType.INPUT)
    private String asId;
    private String asUid;
    private String asUname;
    private Date asTime;
    private String asAbnormalState;
    private String asAbnormalTime;
    private String asRemark;
    private Date asCreateTime;
    private Integer asState;
    private Date asStateTime;
    private String teId;

}
