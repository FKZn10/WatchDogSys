package com.jpmsoft.fileUpload.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 通用导入日志
 *
 * @Author: 李文涛 aaron
 * @date: 2024/2/20 15:24
 */
@Data
@TableName("Sys_Imp_Log")
public class SysImpLog {

    @TableId(type = IdType.INPUT)
    private String id;
    private Date impTime;
    private String impUname;
    private String impUid;
    private String impPath;
    private String impFileName;
    private Long impFileSize;
    private String impResult;

}
