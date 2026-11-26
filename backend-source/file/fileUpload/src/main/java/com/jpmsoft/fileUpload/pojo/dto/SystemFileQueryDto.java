package com.jpmsoft.fileUpload.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/10 9:58
 */
@Data
public class SystemFileQueryDto extends BaseModeRequest implements Serializable {

    private static final long serialVersionUID = -1406235643975555054L;

    @JsonProperty("pId")
    private String pId;
    private String secId;
    private String conId;
    private String uploadUid;
    private String fileName;
    private String fileCreateUName;
    @JsonProperty("pName")
    private String pName;
    private String tableCode;
    /**
     * 附件后缀名
     */
    private List<String> fileSuffixes;
}
