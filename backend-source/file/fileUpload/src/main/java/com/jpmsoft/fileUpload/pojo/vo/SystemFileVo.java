package com.jpmsoft.fileUpload.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 选择系统文件
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/16 8:53
 */
@Data
public class SystemFileVo extends FileResVo {

    private static final long serialVersionUID = -2661862510482449529L;
    @JsonProperty("pName")
    private String pName;
    private String fileCreateUName;
    private String fileCreateUId;
}
