package com.jpmsoft.fileUpload.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/16 9:10
 */
@Data
public class DownloadUrlDto implements Serializable {

    private static final long serialVersionUID = 2823469000163180752L;
    private String fileIds;
    private String url;
    private String openUrl;
    private String noHostUrl;
}
