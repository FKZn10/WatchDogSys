package com.jpmsoft.custom.pojo.dto;

import lombok.Data;

/**
 * 文件字段
 *
 * @Author: 李文涛 aaron
 * @date: 2023/5/30 10:40
 */
@Data
public class FileCodeDTO {
    /**
     * relationId
     */
    private String filedKey;
    /**
     * fileIds
     */
    private String filedListKey;
}
