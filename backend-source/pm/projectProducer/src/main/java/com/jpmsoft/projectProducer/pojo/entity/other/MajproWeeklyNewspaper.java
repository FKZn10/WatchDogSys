package com.jpmsoft.projectProducer.pojo.entity.other;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("majpro_weekly_newspaper")
public class MajproWeeklyNewspaper {
    @TableId(type = IdType.INPUT)
    private String id;
    private String proname;
    private String proid;
    private String principal;
    private String proewunit;
    private String probuunit;
    private String content;
    private String feedback;
    private String provisualschedule;
    private String prophotourl;
    private String profileurl;
    private Date uploadtime;
    private String appopinion;
    private Date appoptime;
    private String leaderAttachmentUrl;
    private Integer state;
    private Date stateTime;
    private Date createTime;
    private String createUid;
    private String createUname;
    private String uuid;
    private String pId;
}