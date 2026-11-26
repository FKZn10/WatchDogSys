package com.jpmsoft.projectProducer.pojo.entity.other;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
@TableName("Reply_Info")
public class ReplyInfo extends BaseEntity {
    private  String id;
    private  String sourceId;
    private  String sourceCode;
    private  String projectId;
    private  String conId;
    private  String riCode;
    private  String riTitle;
    private  String riContent;
    private  String fileRelationId;
}
