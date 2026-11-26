package com.jpmsoft.fileUpload.pojo.dto;

import lombok.Data;

/**
 * FileAnnexTableAndColumn 和FileTemplateTableAndPrimary
 * 组装得到的模型 用于组成SQL查询指定表的指定字段得到fileRelationId
 */
@Data
public class FileTableColumnAndPrimaryDto {

    private String primaryKey;
    private String tableName;
    private String fileColumnName;

}
