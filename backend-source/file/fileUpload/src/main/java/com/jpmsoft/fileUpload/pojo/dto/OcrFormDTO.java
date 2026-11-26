package com.jpmsoft.fileUpload.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/2/14 9:41
 */
@Data
public class OcrFormDTO implements Serializable {

    private static final long serialVersionUID = -3547495920670119915L;

    /**
     * 表单中文标题
     */
    private String key;
    /**
     * 是否需要检索主键
     */
    private Boolean searchPrimary = false;
    /**
     * 检索主键时 检索的数据表名
     */
    private String table;
    /**
     * 检索主键时  检索的条件字段名
     */
    private String field;
    /**
     * 检索主键时 检索的主键字段名
     */
    private String idKey;
    /**
     * 检索主键时 返回的ID参数
     */
    private String idValue;
    /**
     * ocr识别返回的内容信息
     */
    private String content;
}
