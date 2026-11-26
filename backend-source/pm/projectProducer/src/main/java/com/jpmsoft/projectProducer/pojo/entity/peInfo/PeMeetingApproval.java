package com.jpmsoft.projectProducer.pojo.entity.peInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 考核评分上会呈批
 * Created By:李文涛 aaron
 * Created Time:2022/6/16 10:05
 */
@Data
@TableName("Pe_Meeting_Approval")
public class PeMeetingApproval {

    @TableId(type = IdType.INPUT)
    private String maId;
    private String maTitle;
    private String maAssessmentType;
    private String maYear;
    private String maQuarter;
    private String maMinutesMeeting;
    private String maRemarks;
    private String maCreateUname;
    private String maCreateUid;
    private Date maCreateTime;
    private Integer state;
    private Date stateTime;
    private String teId;
}
