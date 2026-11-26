package com.jpmsoft.common.model.request.Archive;

import java.util.List;

/**
 * 归档时请求文件服务 生成归档文件信息和FileAnnex信息
 */
public class FileServerToArchiveRequest {

    private List<ArchiveFileRequest> datas;
    private String apId;
    private String fileCode;
    private Integer apState;

    public List<ArchiveFileRequest> getDatas() {
        return datas;
    }

    public void setDatas(List<ArchiveFileRequest> datas) {
        this.datas = datas;
    }

    public String getApId() {
        return apId;
    }

    public void setApId(String apId) {
        this.apId = apId;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    public Integer getApState() {
        return apState;
    }

    public void setApState(Integer apState) {
        this.apState = apState;
    }
}
