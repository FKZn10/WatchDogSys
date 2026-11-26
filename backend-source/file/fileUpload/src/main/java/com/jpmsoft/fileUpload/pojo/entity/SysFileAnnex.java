package com.jpmsoft.fileUpload.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@TableName("SYS_FileAnnex")
@Accessors(chain = true)
public class SysFileAnnex {

    @TableId(type = IdType.INPUT)
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
    /**
     * 是否确认业务上传
     * 0:业务层面未上传
     * 1：业务层面已上传
     */
    private Integer uploadStatus;
    /**
     * 是否已归档
     * 0 未归档
     * 1 已归档
     */
    private Integer archive;
    /**
     * 附件上传位置
     * 0：普通上传路径
     * 1：归档上传路径
     */
    private Integer fileLocation;
    private String fileUnarchiveId;
    /**
     * 标记是否为已被替换文件
     * 1:已被替换
     * 0:未替换
     */
    private Integer fileReplace;
    private String fileEntityId;
    //上传时录入信息
    private String fileInputName;
    private String fileInputCode;
    private Date fileInputTime;
    private String fileInputVersion;
    /**
     * 附件来源
     * 01：用户上传
     * 02：系统关联（历史上传文件）
     */
    private String fileSourceType;
    /**
     * 来源ID
     */
    private String fileSourceId;
    /**
     * 归档目录ID
     */
    private String adId;
    /**
     * 文件MD5唯一标识
     */
    private String fileMd5Code;
    /**
     * 是否已保存到OSS
     */
    private boolean fileUpOss;
    /**
     * 租户ID
     */
    private String teId;

    /**
     * true不可删除
     * false或者缺省可删除
     */
    private boolean fileNotRemove;

    private boolean fileJimuReport;

    /**
     * 用于标识文件历史
     */
    private String fileHistoryId;

    private Integer fileIsHistory;
}
