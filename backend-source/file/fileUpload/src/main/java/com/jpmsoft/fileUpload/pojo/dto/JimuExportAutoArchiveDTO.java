package com.jpmsoft.fileUpload.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 积木报表导出自动归档配置
 * jimuExportAutoArchive
 *
 * @Author: 李文涛 aaron
 * @date: 2023/9/12 9:12
 */
@Data
public class JimuExportAutoArchiveDTO implements Serializable {

    private boolean autoArchive;
    /**
     * 积木报表导出PDF地址
     */
    private String exportPdfUrl;
    /**
     * 创建人ID 用于创建虚拟
     */
    private String createUid;
}
