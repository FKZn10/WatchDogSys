package com.jpmsoft.fileUpload.pojo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件上传返回
 *
 * @author: 李文涛 aaron
 * @date: 2022/10/20 8:53
 */
@Data
@Accessors(chain = true)
public class UploadResVo implements Serializable {

    private String id;
    private String url;
    private String fileName;
    private String fileSuffix;
    private Long fileSize;
    private String fileInputCode;
    private String fileInputName;
    private Date fileInputTime;
    private String fileInputVersion;
    private String adId;
    private String adLink;
    private String noHostUrl;
    private boolean duplicateFile = false;
}
