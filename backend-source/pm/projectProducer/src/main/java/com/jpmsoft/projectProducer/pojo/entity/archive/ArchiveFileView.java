package com.jpmsoft.projectProducer.pojo.entity.archive;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 文件查看记录
 */
@Data
@TableName("Archive_File_View")
public class ArchiveFileView {

    @TableId(type = IdType.INPUT)
    private String afvId;
    private String afId;
    private String afvUid;
    private String afvUname;
    private Date afvTime;
    private String teId;
}
