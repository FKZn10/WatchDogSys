package com.jpmsoft.fileUpload.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 压缩图片配置
 * 基础配置编码jpmCompressImgCcConf
 *
 * @Author: 李文涛 aaron
 * @date: 2024/9/5 14:45
 */
@Data
public class CompressImgDTO implements Serializable {

    private int height = 200;
    private int width = 150;
}
