package com.jpmsoft.fileUpload.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 成果文件打包下载
 *
 * @Author: 李文涛 aaron
 * @date: 2024/5/16 16:00
 */
@Data
public class ArchievementsDTO implements Serializable {

    private static final long serialVersionUID = 3853285372490569794L;
    private String annexId;
    private String fileName;
    @JsonProperty("pName")
    private String pName;
    private String proDirId;
    private String cpdName;

    private String path;
}
