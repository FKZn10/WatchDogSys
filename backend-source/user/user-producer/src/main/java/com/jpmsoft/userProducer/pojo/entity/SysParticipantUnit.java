package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 项目参建单位
 */
@Data
@TableName("SYS_Participant_Unit")
public class SysParticipantUnit {
    @TableId(type = IdType.INPUT)
    public String puId;
    public String pId;
    public String cId;
    public String cType;
    public Date puCreateTime;
    public String puCreateUid;
    public String puCreateUname;
    public String puRemark;
    public Integer puOrder;
}
