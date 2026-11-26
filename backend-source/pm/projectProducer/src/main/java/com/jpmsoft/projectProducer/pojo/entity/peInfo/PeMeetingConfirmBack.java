package com.jpmsoft.projectProducer.pojo.entity.peInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 上会确认退回记录
 */
@Data
@TableName("Pe_MeetingConfirm_Back")
public class PeMeetingConfirmBack {

    @TableId(type = IdType.INPUT)
    private String backId;
    private String peId;
    private String backUid;
    private String backUname;
    private Date backCreateTime;
    private String backType;
    private String backReason;
}
