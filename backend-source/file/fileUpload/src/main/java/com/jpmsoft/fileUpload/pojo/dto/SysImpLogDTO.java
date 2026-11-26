package com.jpmsoft.fileUpload.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 通用导入日志
 *
 * @Author: 李文涛 aaron
 * @date: 2024/2/20 15:24
 */
@Data
public class SysImpLogDTO extends BaseModeRequest implements Serializable {

    private static final long serialVersionUID = 4500917367982636797L;
    private String id;
    private Date impTime;
    private String impUname;
    private String impUid;
    private String impPath;
    private String impFileName;
    private Long impFileSize;
    private String impResult;
}
