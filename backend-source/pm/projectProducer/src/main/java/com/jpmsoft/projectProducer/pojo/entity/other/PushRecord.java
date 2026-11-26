package com.jpmsoft.projectProducer.pojo.entity.other;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 推送记录
 */
@Data
@TableName("Push_Record")
public class PushRecord {

    @TableId(type = IdType.INPUT)
    private String prId;
    private Date prTime;
    private Integer prState;
    private String prDataId;
    private String prType;
    private String prUrl;
    private String prRequest;
    private String prResponse;
    private String prMsgId;
}
