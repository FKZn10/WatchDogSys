package com.jpmsoft.projectProducer.pojo.entity.oa;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 通知公告阅读记录
 */
@Data
@TableName("OA_Notice_Read")
public class OaNoticeRead {
    @TableId(type = IdType.INPUT)
    private String nrId;
    private String nId;
    private String nrUid;
    private String nrUname;
    private Date nrTime;
    private String isRead;
    private String teId;
}
