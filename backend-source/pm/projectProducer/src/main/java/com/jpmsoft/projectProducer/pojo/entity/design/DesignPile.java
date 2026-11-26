package com.jpmsoft.projectProducer.pojo.entity.design;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 桩基检测
 * Created By:李文涛 aaron
 * Created Time:2022/6/22 13:55
 */
@Data
@TableName("Design_Pile")
public class DesignPile {

    @TableId(type = IdType.INPUT)
    private String dpId;
    //项目id
    private String pId;
    //合同id
    private String conId;
    //标段id
    private String secId;
    //标段名称
    private String dpSecName;
    //检测类型
    private String dpType;
    //项目负责人
    private String dpPm;
    //开始时间
    private Date dpStartTime;
    //结束时间
    private Date dpEndTime;
    //检测内容
    private String dpContent;
    private Integer state;
    private Date stateTime;
    private String dpCreateUid;
    private String dpCreateUname;
    private Date dpCreateTime;
    private String teId;
}
