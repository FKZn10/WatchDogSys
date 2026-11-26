package com.jpmsoft.fileUpload.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 附件下载日志
 *
 * @Author: 李文涛 aaron
 * @date: 2023/9/28 9:28
 */
@Data
public class SysDownloadLogDTO extends BaseModeRequest {
    private String id;
    private String dlUid;
    private String dlUname;
    private String dlFileId;
    private String dlFileName;
    private Date dlTime;
    private String dlFormId;

    private List<String> dlTimes;
}

