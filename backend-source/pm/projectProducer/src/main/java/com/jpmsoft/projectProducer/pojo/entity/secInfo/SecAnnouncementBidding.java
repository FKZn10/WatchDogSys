package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 招标公告发布
 */
@Data
@TableName("Sec_Announcement_Bidding")
public class SecAnnouncementBidding {

    @TableId(type = IdType.INPUT)
    private String sabId;
    private String secId;
    //公告实际发布时间
    private Date sabAnnouncementTime;
    //实际截标时间
    private Date sabClosBidTime;
    //公告发布
    private String sabAnnouncement;
    private String sabRemarks;
    private String sabCreateUid;
    private String sabCreateUname;
    private Date sabCreateTime;
    private Integer state;
    private Date stateTime;
    private String teId;
}
