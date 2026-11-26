package com.jpmsoft.projectProducer.pojo.entity.oa;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 工作报告（日报、周报、月报、季报、年报）
 */
@Data
@TableName("OA_Work_Report")
public class OaWorkReport {

    @TableId(type = IdType.INPUT)
    private String wrId;
    private String pId;
    private String secId;
    private String conId;
    private String wrDId;
    private String wrCId;
    private String wrCode;
    private Date wrDate;
    private Date wrStartTime;
    private Date wrEndTime;
    private String wrTitle;
    private String wrContent;
    private String wrDoc;
    private String wrCreateUid;
    private String wrCreateUname;
    private Date wrCreateTime;
    private String wrType;
    private Date wrStateTime;
    private Integer wrState;
    private String wrProjectOverview;
    private String wrProjectSchedule;
    private Integer wrYear;
    private Integer wrQuarter;
    private Integer wrMonth;
    private String wrWeather;
    private String wrWindDirection;
    private Float wrWindMax;
    private Float wrWindMin;
    private Float wrWind;
    private Float wrTemperatureHigh;
    private Float wrTemperatureLow;
    private String wrContentQs;
    private String wrContentConstruct;
    private String wrContentOther;
    private String wrContractorId;


    private String teId;


    private String editType;


    private String contentFile;


    private Integer replyState;


    private String owrSubmit;


    private String wrDateVar;


    private String wrSign;
}
