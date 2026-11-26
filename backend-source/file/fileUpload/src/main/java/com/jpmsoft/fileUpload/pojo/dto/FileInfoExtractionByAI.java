package com.jpmsoft.fileUpload.pojo.dto;

import lombok.Data;

/**
 * 借助视觉模型提取文档信息基础配置
 */
@Data
public class FileInfoExtractionByAI {

    private String url;
    private String model;
    private String apiKey;
    private String dpi = "100";
}
