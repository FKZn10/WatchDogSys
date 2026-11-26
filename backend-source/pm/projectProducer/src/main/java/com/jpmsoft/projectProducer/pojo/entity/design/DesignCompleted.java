package com.jpmsoft.projectProducer.pojo.entity.design;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 竣工图设计
 */
@Data
@TableName("Design_Completed")
public class DesignCompleted {

    @TableId(type = IdType.INPUT)
    private String dcId;
    //项目ID
    private String pId;
    //标段ID
    private String secId;
    //专业类别
    private String dcCategory;
    //编制内容
    private String dcContent;
    //编制时间
    private Date dcEditTime;
    //编制人
    private String dcEditor;
    private Integer state;
    private Date stateTime;
    private String dcCreateUname;
    private String dcCreateUid;
    private Date dcCreateTime;
    //施工单位
    private String dcConstruUnit;
    //施工单位项目负责人
    private String dcConstruPm;
    //监理单位
    private String dcSuperviUnit;
    //监理单位项目负责人
    private String dcSuperviPm;
    //设计单位
    private String dcDesignUnit;
    //设计单位项目负责人
    private String dcDesignPm;
    private String teId;
}
