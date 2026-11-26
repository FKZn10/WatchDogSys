package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("Pro_Image_Progress_Comment")
public class ProImageProgressComment {
    @TableId(type = IdType.INPUT)
    private String id;
    private String ipId;
    private String content;
    private String createUid;
    private String createUname;
    private Date createTime;
}
