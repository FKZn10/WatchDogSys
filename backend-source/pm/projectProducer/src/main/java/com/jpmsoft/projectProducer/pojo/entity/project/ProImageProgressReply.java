package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("Pro_Image_Progress_Reply")
public class ProImageProgressReply {
    @TableId(type = IdType.INPUT)
    private String id;
    private String ipcId;
    private String content;
    private String iprId;
    private String replyUname;
    private String replyUid;
    private String createUid;
    private String createUname;
    private Date createTime;
}
