package com.jpmsoft.fileUpload.pojo.vo;


import java.io.Serializable;

/**
 * 需要归档的文件列表
 */
public class ArchiveFileRecordVo implements Serializable {

    private static final long serialVersionUID = -1893120267048765748L;

    private String fileId;
    private String fileName;
    private Long fileSize;
    private String fileSuffix;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
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

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }
}
