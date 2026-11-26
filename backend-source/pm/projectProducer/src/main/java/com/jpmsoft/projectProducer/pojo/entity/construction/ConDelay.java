package com.jpmsoft.projectProducer.pojo.entity.construction;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 工程延期报审
 */
@Data
@TableName("Con_Delay")
public class ConDelay {

    @TableId(type = IdType.INPUT)
    private String deId;
    private String deTitle;
    private String deReason;
    private Integer deDays;
    private String deRemark;
    private String deCreateUid;
    private String deCreateUname;
    private Date deCreateTime;
    private Integer deState;
    private Date deStateTime;
    private String pId;
    private String teId;
}
