package com.jpmsoft.projectProducer.pojo.entity.supervision;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 工程交底
 */
@Data
@TableName("Con_Sup_Disclosure")
public class ConSupDisclosure {

    @TableId(type = IdType.INPUT)
    private String disId;
    private String pId;
    private String conId;
    private String disUnitId;
    private String disUnitDId;
    private String disType;
    private Date disDate;
    private String disContent;
    private String disFile;
    private String disCreateUid;
    private String disCreateUname;
    private Date disCreateTime;
    private Integer disState;
    private Date disStateTime;
    private String teId;
}
