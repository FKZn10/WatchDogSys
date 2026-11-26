package com.jpmsoft.fileUpload.pojo.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public class FileAnnexInfoVo {
    @JsonProperty("fileId")
    @ExcelProperty("id")
    @ColumnWidth(40)
    private String fileId;
    /**
     * 关联ID 前端传入
     */
    @JsonProperty("fileRelationId")
    @ExcelProperty("关联ID")
    @ColumnWidth(40)
    private String fileRelationId;
    /**
     * 文件路径
     */
    @JsonProperty("filePath")
    @ExcelProperty("文件路径")
    @ColumnWidth(40)
    private String filePath;
    /**
     * 文件大小
     */
    @JsonProperty("fileSize")
    @ExcelProperty("文件路径")
    @ColumnWidth(40)
    private Long fileSize;
    @JsonProperty("fileSuffix")
    @ExcelProperty("文件后缀")
    @ColumnWidth(40)
    private String fileSuffix;
    @JsonProperty("fileName")
    @ExcelProperty("文件名称")
    @ColumnWidth(40)
    private String fileName;
    @JsonProperty("fileUploadTime")
    @ExcelProperty("文件上传时间")
    @ColumnWidth(40)
    private Date fileUploadTime;
    private String fileCreateUId;
    @JsonProperty("fileCreateUName")
    @ExcelProperty("文件上传人员")
    @ColumnWidth(40)
    private String fileCreateUName;
    @JsonProperty("fileCode")
    @ExcelProperty("文档编码")
    @ColumnWidth(40)
    private String fileCode;
    private String tableName;
    private String tableCode;
    private String columnName;
    private String columnCode;

    private List<String> fileCreateUNames;


    private Integer page;

    private Integer size;

    /**
     * 是否确认业务上传
     * 0:业务层面未上传
     * 1：业务层面已上传
     */
    private Integer uploadStatus;
    /**
     * 是否已归档
     * 0 未归档
     * 1 已归档
     */
    private Integer archive;
    /**
     * 附件上传位置
     * 0：普通上传路径
     * 1：归档上传路径
     */
    private Integer fileLocation;
    private String fileUnarchiveId;
    /**
     * 标记是否为已被替换文件
     * 1:已被替换
     * 0:未替换
     */
    private Integer fileReplace;
    private String fileEntityId;
    //上传时录入信息
    private String fileInputName;
    private String fileInputCode;
    private Date fileInputTime;
    private String fileInputVersion;
    /**
     * 附件来源
     * 01：用户上传
     * 02：系统关联（历史上传文件）
     */
    private String fileSourceType;
    /**
     * 来源ID
     */
    private String fileSourceId;
    /**
     * 归档目录ID
     */
    private String adId;
    /**
     * 文件MD5唯一标识
     */
    private String fileMd5Code;
    /**
     * 是否已保存到OSS
     */
    private boolean fileUpOss;

    //file表部分信息
    private String sysFileId;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileRelationId() {
        return fileRelationId;
    }

    public void setFileRelationId(String fileRelationId) {
        this.fileRelationId = fileRelationId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getFileUploadTime() {
        return fileUploadTime;
    }

    public void setFileUploadTime(Date fileUploadTime) {
        this.fileUploadTime = fileUploadTime;
    }

    public String getFileCreateUId() {
        return fileCreateUId;
    }

    public void setFileCreateUId(String fileCreateUId) {
        this.fileCreateUId = fileCreateUId;
    }

    public String getFileCreateUName() {
        return fileCreateUName;
    }

    public void setFileCreateUName(String fileCreateUName) {
        this.fileCreateUName = fileCreateUName;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
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

    public Integer getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(Integer uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public Integer getArchive() {
        return archive;
    }

    public void setArchive(Integer archive) {
        this.archive = archive;
    }

    public Integer getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(Integer fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getFileUnarchiveId() {
        return fileUnarchiveId;
    }

    public void setFileUnarchiveId(String fileUnarchiveId) {
        this.fileUnarchiveId = fileUnarchiveId;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getFileReplace() {
        return fileReplace;
    }

    public void setFileReplace(Integer fileReplace) {
        this.fileReplace = fileReplace;
    }

    public String getFileEntityId() {
        return fileEntityId;
    }

    public List<String> getFileCreateUNames() {
        return fileCreateUNames;
    }

    public void setFileCreateUNames(List<String> fileCreateUNames) {
        this.fileCreateUNames = fileCreateUNames;
    }

    public void setFileEntityId(String fileEntityId) {
        this.fileEntityId = fileEntityId;
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

    public Date getFileInputTime() {
        return fileInputTime;
    }

    public void setFileInputTime(Date fileInputTime) {
        this.fileInputTime = fileInputTime;
    }

    public String getFileInputVersion() {
        return fileInputVersion;
    }

    public void setFileInputVersion(String fileInputVersion) {
        this.fileInputVersion = fileInputVersion;
    }

    public String getFileSourceType() {
        return fileSourceType;
    }

    public void setFileSourceType(String fileSourceType) {
        this.fileSourceType = fileSourceType;
    }

    public String getFileSourceId() {
        return fileSourceId;
    }

    public void setFileSourceId(String fileSourceId) {
        this.fileSourceId = fileSourceId;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getFileMd5Code() {
        return fileMd5Code;
    }

    public void setFileMd5Code(String fileMd5Code) {
        this.fileMd5Code = fileMd5Code;
    }

    public boolean isFileUpOss() {
        return fileUpOss;
    }

    public void setFileUpOss(boolean fileUpOss) {
        this.fileUpOss = fileUpOss;
    }

    public String getSysFileId() {
        return sysFileId;
    }

    public void setSysFileId(String sysFileId) {
        this.sysFileId = sysFileId;
    }
}