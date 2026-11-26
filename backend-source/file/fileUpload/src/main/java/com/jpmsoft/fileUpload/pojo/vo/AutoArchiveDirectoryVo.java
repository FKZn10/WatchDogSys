package com.jpmsoft.fileUpload.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 自动归档目录信息
 * Created By:李文涛 aaron
 * Created Time:2022/6/19 13:11
 */
@Data
public class AutoArchiveDirectoryVo implements Serializable {

    private static final long serialVersionUID = 6088777570154738191L;

    private String tCode;
    private String adId;
    private String adParentId;
    private String adName;
}
