package com.jpmsoft.fileUpload.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.fileUpload.pojo.entity.SysFileAnnex;
import lombok.Data;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/16 14:23
 */
@Data
public class ProFileAnnex extends SysFileAnnex {
    @JsonProperty("pName")
    private String pName;
    private String adName;
    private String adCode;
    private String adLink;
}
