package com.jpmsoft.fileUpload.pojo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 断点续传响应VO
 * Created By: AI Assistant
 * Created Time:2026/04/30
 */
@Data
@Accessors(chain = true)
public class ChunkUploadResVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件ID（合并完成后返回）
     */
    private String fileId;

    /**
     * 下载URL（合并完成后返回）
     */
    private String url;

    /**
     * 无主机URL（合并完成后返回）
     */
    private String noHostUrl;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件后缀
     */
    private String fileSuffix;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 文件输入编码
     */
    private String fileInputCode;

    /**
     * 文件输入名称
     */
    private String fileInputName;

    /**
     * 归档目录ID
     */
    private String adId;

    /**
     * 归档链接
     */
    private String adLink;

    /**
     * 是否为重复文件
     */
    private boolean duplicateFile = false;

    /**
     * 上传状态：chunking-分片上传中，merged-已合并
     */
    private String uploadStatus;

    /**
     * 已上传分片数
     */
    private Integer uploadedChunks;

    /**
     * 总分片数
     */
    private Integer totalChunks;
}
