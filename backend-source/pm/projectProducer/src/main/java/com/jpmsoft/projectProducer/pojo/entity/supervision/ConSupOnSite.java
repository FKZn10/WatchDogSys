package com.jpmsoft.projectProducer.pojo.entity.supervision;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 旁站记录
 */
@Data
@TableName("Con_Sup_On_Site")
public class ConSupOnSite {

    @TableId(type = IdType.INPUT)
    private String osId;
    private String pId;
    private String conId;
    private Date osStartTime;
    private Date osEndTime;
    private String osContractorId;
    private String osKeyPart;
    private String osKeyPartExplain;
    private String osExplain;
    private String osCreateUid;
    private String osCreateUname;
    private Date osCreateTime;
    private Integer osState;
    private Date osStateTime;
    private String osCode;
    private String teId;
}
