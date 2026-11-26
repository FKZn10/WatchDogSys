package com.jpmsoft.fileUpload.pojo.dto;

import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.constants.FileMsg;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Created By:李文涛 aaron
 * Created Time:2022/6/17 11:18
 */
public class UploadDto implements Serializable {

    private static final long serialVersionUID = -401551863999629000L;
    @NotBlank(message = FileMsg.upNoParaUid)
    private String uId;
    @NotBlank(message = FileMsg.upNoParaCode)
    private String code;
    @NotBlank(message = FileMsg.upNoParaRelation)
    private String fileRelationId;
    private String tableName;
    private String tableCode;
    private String columnName;
    private String columnCode;
    private String fileInputName;
    private String fileInputCode;
    private String fileInputVersion;
    private String[] fileNames;
    private String fileInputTime;
    /**
     * 归档目录ID
     */
    private String adId;
    /**
     * Sys_File id 即表单业务ID
     * 文件表FileAnnex File_Entity_Id字段
     */
    private String fileId;
    /**
     * true不可删除
     * false或者缺省可删除
     */
    private boolean fileNotRemove;

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFileRelationId() {
        return fileRelationId;
    }

    public void setFileRelationId(String fileRelationId) {
        this.fileRelationId = fileRelationId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnCode() {
        return columnCode;
    }

    public void setColumnCode(String columnCode) {
        this.columnCode = columnCode;
    }

    public String getFileInputName() {
        return fileInputName;
    }

    public void setFileInputName(String fileInputName) {
        this.fileInputName = fileInputName;
    }

    public String getFileInputCode() {
        return fileInputCode;
    }

    public void setFileInputCode(String fileInputCode) {
        this.fileInputCode = fileInputCode;
    }

    public String getFileInputVersion() {
        return fileInputVersion;
    }

    public void setFileInputVersion(String fileInputVersion) {
        this.fileInputVersion = fileInputVersion;
    }

    public String[] getFileNames() {
        return fileNames;
    }

    public void setFileNames(String[] fileNames) {
        this.fileNames = fileNames;
    }

    public String getFileInputTime() {
        if (ValidateUtil.isEmpty(fileInputTime) || fileInputTime.equals("null")) return null;
        return fileInputTime;
    }

    public void setFileInputTime(String fileInputTime) {
        this.fileInputTime = fileInputTime;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public boolean isFileNotRemove() {
        return fileNotRemove;
    }

    public void setFileNotRemove(boolean fileNotRemove) {
        this.fileNotRemove = fileNotRemove;
    }
}
