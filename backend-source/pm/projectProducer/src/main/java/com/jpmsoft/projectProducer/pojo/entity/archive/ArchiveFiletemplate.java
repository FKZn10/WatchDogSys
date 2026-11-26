package com.jpmsoft.projectProducer.pojo.entity.archive;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 归档文档模板
 */
@Data
@TableName("Archive_FileTemplate")
public class ArchiveFiletemplate {

    @TableId(type = IdType.INPUT)
    private String atId;
    private String adId;
    private String atTCode;
    private Integer atOrder;
    private String atType;
    private String atCreateUid;
    private String atCreateUname;
    private Date atCreateTime;
    private Integer atState;
}
