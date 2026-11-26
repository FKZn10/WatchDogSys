package com.jpmsoft.fileUpload.pojo.dto;

/**
 * 附件模板
 * 对应表名和表的主键名称
 */
public class FileTemplateTableAndPrimaryDto {

    private String tTable;
    private String tPrimaryKey;

    public String gettTable() {
        return tTable;
    }

    public void settTable(String tTable) {
        this.tTable = tTable;
    }

    public String gettPrimaryKey() {
        return tPrimaryKey;
    }

    public void settPrimaryKey(String tPrimaryKey) {
        this.tPrimaryKey = tPrimaryKey;
    }
}
