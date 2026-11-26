package com.jpmsoft.projectProducer.pojo.entity.archive;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 归档文件信息
 */
@Data
@TableName("Archive_File")
public class ArchiveFile {
    @TableId(type = IdType.INPUT)
    private String afId;
    private String apId;
    private Integer afOrder;
    private String afCode;
    private String afName;
    private String afType;
    private Integer afPageNumber;
    private Float afFileSize;
    private Integer afDpi;
    private String afCreateUid;
    private String afCreateUname;
    private Date afCreateTime;
    private String afFileId;
    private String afAnnexId;
    private Integer afState;
    private Integer afFrom;
    private String teId;
}
