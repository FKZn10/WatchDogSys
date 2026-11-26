package com.jpmsoft.projectProducer.pojo.entity.supervision;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 监理规划及细则
 */
@Data
@TableName("Con_Sup_Plan")
public class ConSupPlan {

    @TableId(type = IdType.INPUT)
    private String spId;
    private String pId;
    private String conId;
    private String spTitle;
    private String spContent;
    private String spRemark;
    private String spFile;
    private String spImplementFile;
    private String spCreateUid;
    private String spCreateUname;
    private Date spCreateTime;
    private Integer spState;
    private Date spStateTime;
    private String teId;
    /**
     * 类型：01：旧，02：监理现场用表的监理规划
     */
    private String spType;
}
