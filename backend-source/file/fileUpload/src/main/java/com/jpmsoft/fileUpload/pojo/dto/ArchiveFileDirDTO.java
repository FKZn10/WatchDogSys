package com.jpmsoft.fileUpload.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/4/17 13:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArchiveFileDirDTO implements Serializable {

    private static final long serialVersionUID = -1583779196619595415L;
    private String adId;
    private String path;
}
