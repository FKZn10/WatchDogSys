package com.jpmsoft.fileUpload.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.common.model.BasePageEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 归档文件名称配置
 *
 * @Author: 李文涛 aaron
 * @date: 2024/8/29 14:01
 */
@Data
public class ArchiveFileNameDTO extends BasePageEntity implements Serializable {

    private static final long serialVersionUID = 7841249912590079700L;
    private String id;
    private String afnName;
    private Boolean afnStatus;
    private String tableCode;
    private String columnCode;
    private String createUid;
    private String createUname;
    private Date createTime;
    private String updateUid;
    private String updateUname;
    private Date updateTime;
    private String afnDelimiter;
}
