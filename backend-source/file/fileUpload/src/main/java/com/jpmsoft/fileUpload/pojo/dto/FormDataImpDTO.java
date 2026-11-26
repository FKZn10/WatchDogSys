package com.jpmsoft.fileUpload.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * 通用模板导入
 *
 * @Author: 李文涛 aaron
 * @date: 2024/1/18 8:56
 */
@Data
public class FormDataImpDTO {
    private String topJson;
    /**
     * 表头行数
     * 第一行为 0
     */
    private int topIndex;
    /**
     * 表头行表字段集合(值为数据库字段 例项目编码P_Code) index即第几列
     * 前端传入topJson转换获得
     */
    private List<String> tops;
}
