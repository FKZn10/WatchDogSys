package com.jpmsoft.fileUpload.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 选择上传系统文件
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/9 9:27
 */
@Data
public class SystemUploadDto implements Serializable {

    private static final long serialVersionUID = 6850703265825616186L;
    /**
     * 新标段附件关联ID
     */
    private String fileRelationId;
    /**
     * 系统历史文件ID合集
     */
    private List<String> fileIds;

    private String tableName;
    private String tableCode;
    private String columnName;
    private String columnCode;
    private String createUname;
    private String createUid;
    /**
     * true不可删除
     * false或者缺省可删除
     */
    private boolean fileNotRemove;
}
