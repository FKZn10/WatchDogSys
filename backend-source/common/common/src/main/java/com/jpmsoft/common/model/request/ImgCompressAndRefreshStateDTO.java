package com.jpmsoft.common.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 图片上传状态刷新以及生成压缩图片
 * relation id对应图片ID有值则按需删除
 * 图片ID无值则直接删除relation id对应的文件
 * relation id 和图片ID一对一不支持一对多
 *
 * @Author: 李文涛 aaron
 * @date: 2024/9/5 13:45
 */
@Data
public class ImgCompressAndRefreshStateDTO implements Serializable {
    /**
     * 图片文件relation id
     */
    private String imgRelationId;
    /**
     * 图片ID
     */
    private String imgIds;
    /**
     * 压缩后图片relation id
     */
    private String compressRelationId;
    /**
     * 压缩后图片id
     */
    private String compressIds;
    /**
     * 原图下载地址（不带IP以及域名前缀）
     */
    private String imgUrl;
    /**
     * 压缩后图片（不带IP以及域名前缀）
     */
    private String compressUrl;
}
