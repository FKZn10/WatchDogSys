package com.jpmsoft.fileUpload.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 批量下载表单相关文件
 * Created By:李文涛 aaron
 * Created Time:2022/6/30 11:39
 */
@Data
public class FormBatchDownDto  implements Serializable {
    private static final long serialVersionUID = 3573538291692266714L;

    /**
     * 表单ID集合
     */
    private List<String> entityIds;

}
