package com.jpmsoft.fileUpload.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * ocr技术基础配置
 *
 * @Author: 李文涛 aaron
 * @date: 2025/2/14 9:13
 */
@Data
public class OcrConfigDTO implements Serializable {

    private static final long serialVersionUID = 4525325202435637009L;
    private String accessKey;
    private String secretKey;
}
