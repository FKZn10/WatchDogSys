package com.jpmsoft.projectProducer.pojo.entity.supervision;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;


/**
 * 违规处罚
 */
@Data
@TableName("Punishes")
public class Punish {
    @TableId(type = IdType.INPUT)
    private String id;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer State;
    private Date stateTime;
    private String pId;
    private String conId;
    private String cId;
    private String illegalBehavior;
    private String punitiveMeasures;
    private String punishType;
    private String remark;
    private Date punishStartTime;
    private Date punishEndTime;

}
