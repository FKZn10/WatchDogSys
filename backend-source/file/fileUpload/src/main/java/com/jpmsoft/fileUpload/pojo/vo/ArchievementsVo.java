package com.jpmsoft.fileUpload.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ArchievementsVo implements Serializable {
    private static final long serialVersionUID = -7616022492785716918L;
    private String fileId;
    private String fileName;
    @JsonProperty("pId")
    private String pId;
    @JsonProperty("pName")
    private String pName;
    private String cpdName;
    private String fileCreateUName;
    private Date fileUploadTime;
    private String downUrl;
    private String noHostUrl;
    private String annexId;
    private String cpdFileCode;
    private Date cpdReplyTime;
    private String cpdAdministration;
}
