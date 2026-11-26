package com.jpmsoft.fileUpload.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 对象存储OSS配置信息
 *
 * @Author: 李文涛 aaron
 * @date: 2022/12/19 14:16
 */
@Data
public class OssClientParamsDto implements Serializable {

    /**
     * 是否启用oss对象存储
     */
    private boolean enableOss;
    /**
     * oss内文件根目录
     */
    private String ossRootDir;
    /**
     * oss内归档文件根目录
     */
    private String ossArchiveDir;
    private String endpoint;
    private String namespace;
    private String accessKey;
    private String secretKey;

}
