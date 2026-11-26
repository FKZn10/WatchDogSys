package com.jpmsoft.common.model.requestAndResponse;

import lombok.Data;

import java.io.Serializable;

/**
 * 内部使用文件上传到指定目录
 *
 * @Author: 李文涛 aaron
 * @date: 2023/10/30 10:57
 */
@Data
public class InnerFileUploadAppointDTO implements Serializable {

    private static final long serialVersionUID = -3590390164713612073L;

    /**
     * 是否归档根目录
     */
    private boolean archiveDir;
    /**
     * 文件路径
     */
    private String path;
    private String fileName;
    private String newPath;
    private String url;
}
