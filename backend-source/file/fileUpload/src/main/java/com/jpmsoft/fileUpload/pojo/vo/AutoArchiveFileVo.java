package com.jpmsoft.fileUpload.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 自动归档文件信息
 * Created By:李文涛 aaron
 * Created Time:2022/6/19 13:09
 */
@Data
public class AutoArchiveFileVo implements Serializable {


    private static final long serialVersionUID = 5793734028008094325L;
    private String fileId;
    /**
     * 关联ID 前端传入
     */
    private String fileRelationId;
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 文件大小
     */
    private Long fileSize;
    private String fileSuffix;
    private String fileName;
    private Date fileUploadTime;
    private String fileCreateUId;
    private String fileCreateUName;
    private String fileCode;
    private String tableName;
    private String tableCode;
    private String columnName;
    private String columnCode;
    private Integer uploadStatus;
    private Integer archive;
    private Integer fileLocation;
    private String fileUnarchiveId;
    //标记是否为已被替换文件
    private Integer fileReplace;
    private String fileEntityId;
    //上传时录入信息
    private String fileInputName;
    private String fileInputCode;
    private Date fileInputTime;
    private String fileInputVersion;
    /**
     * 归档目录Id
     */
    private String adId;

    /**
     * 流程文档主要信息
     */
    private String tCode;
    private String pId;
    private String secId;
    private String conId;
    private String sysFileId;

    /**
     * 文件MD5唯一标识
     */
    private String fileMd5Code;
}
