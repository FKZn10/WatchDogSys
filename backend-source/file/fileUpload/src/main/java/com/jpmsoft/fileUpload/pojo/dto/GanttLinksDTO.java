package com.jpmsoft.fileUpload.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/4/10 9:29
 */
@Data
public class GanttLinksDTO implements Serializable {

    private String linkId;
    private String linkSource;
    private String linkTarget;
    private Integer linkType;
}
