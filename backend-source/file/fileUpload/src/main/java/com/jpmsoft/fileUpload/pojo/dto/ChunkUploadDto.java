package com.jpmsoft.fileUpload.pojo.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 断点续传DTO
 * Created By: AI Assistant
 * Created Time:2026/04/30
 */
@Data
@Accessors(chain = true)
public class ChunkUploadDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @NotBlank(message = "用户ID不能为空")
    private String uId;

    /**
     * 功能编码
     */
    @NotBlank(message = "功能编码不能为空")
    private String code;

    /**
     * 文件关联ID
     */
    @NotBlank(message = "文件关联ID不能为空")
    private String fileRelationId;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 表编码
     */
    private String tableCode;

    /**
     * 列名
     */
    private String columnName;

    /**
     * 列编码
     */
    private String columnCode;

    /**
     * 文件输入名称
     */
    private String fileInputName;

    /**
     * 文件输入编码
     */
    private String fileInputCode;

    /**
     * 文件输入版本
     */
    private String fileInputVersion;

    /**
     * 文件输入时间
     */
    private String fileInputTime;

    /**
     * 归档目录ID
     */
    private String adId;

    /**
     * Sys_File id 即表单业务ID
     */
    private String fileId;

    /**
     * true不可删除 false或者缺省可删除
     */
    private boolean fileNotRemove;

    /**
     * 文件唯一标识（用于合并分片）
     */
    @NotBlank(message = "文件唯一标识不能为空")
    private String fileIdentifier;

    /**
     * 原始文件名
     */
    @NotBlank(message = "文件名不能为空")
    private String fileName;

    /**
     * 当前分片索引（从0开始）
     */
    private Integer chunkIndex;

    /**
     * 总分片数
     */
    private Integer totalChunks;

    /**
     * 分片大小
     */
    private Long chunkSize;

    /**
     * 文件总大小
     */
    private Long fileSize;

    /**
     * 是否为最后一个分片
     */
    private Boolean isLastChunk;

    /**
     * 上传类型：root-普通上传，archive-归档上传
     */
    private String updateType = "root";

    /**
     * 文件状态：0-未保存，1-已保存
     */
    private Integer status = 0;
}
