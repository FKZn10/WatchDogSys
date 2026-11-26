package com.jpmsoft.fileUpload.pojo.dto;

import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;

/**
 * lucene全文索引
 * 需要创建索引的文件信息
 *
 * @Author: 李文涛 aaron
 * @date: 2023/7/24 10:47
 */
@Data
public class LuceneFileDTO implements Serializable {
    /**
     * 归档文件信息ID
     */
    private String afId;
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件保存位置 归档目录|常规目录
     */
    private Integer fileLocation;
}
