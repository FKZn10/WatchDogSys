package com.jpmsoft.fileUpload.pojo.dto;

import com.jpmsoft.fileUpload.pojo.entity.SysFileAnnex;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 通用上传 归档附件
 *
 * @Author: 李文涛 aaron
 * @date: 2024/4/23 13:37
 */
@Data
public class FormDataImpArchiveDTO implements Serializable {

    private String pId;
    private String adCode;
    private List<SysFileAnnex> files;
}
