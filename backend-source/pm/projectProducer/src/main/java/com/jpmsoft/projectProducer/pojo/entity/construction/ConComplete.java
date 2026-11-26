package com.jpmsoft.projectProducer.pojo.entity.construction;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 完工申请
 */
@Data
@TableName("Con_Complete")
public class ConComplete {

    @TableId(type = IdType.INPUT)
    private String coId;
    private String pId;
    private String coTitle;
    private Date coStart;
    private Date coEnd;
    private String coRemark;
    private String coCreateUid;
    private String coCreateUname;
    private Date coCreateTime;
    private Integer coState;
    private Date coStateTime;
    private String teId;
}
