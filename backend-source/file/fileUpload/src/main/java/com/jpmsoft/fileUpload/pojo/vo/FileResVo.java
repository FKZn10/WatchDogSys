package com.jpmsoft.fileUpload.pojo.vo;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class FileResVo implements Serializable {

    private static final long serialVersionUID = -9198591596205850482L;
    private String uid;
    private String name;
    private String status = "done";
    private String url;
    private String noHostUrl;
    private String openUrl;
    private String relationid;
    private Date uploadTime;
    private Long fileSize;
    private String fileSuffix;
    //上传时录入信息
    private String fileInputName;
    private String fileInputCode;
    private Date fileInputTime;
    private String fileInputVersion;
    /**
     * true不可删除
     * false或者缺省可删除
     */
    private boolean fileNotRemove;
    private String adName;
    private String adCode;
    private String adId;
    private String adLink;
    private Boolean isExist;
    private String uploadUser;
}