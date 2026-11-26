package com.jpmsoft.common.model.parentClass;

public class SecGeneralityModel {

    private Integer secFlowState;
    private int page;
    private int size;
    //主表排序字段
    private String sortFiled;
    //查询结果字段排序
    private String sortSideFiled;
    private String sortType;
    private String pId;
    private String fileTitle;
    private String fileCode;

    public Integer getSecFlowState() {
        return secFlowState;
    }

    public void setSecFlowState(Integer secFlowState) {
        this.secFlowState = secFlowState;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSortFiled() {
        return sortFiled;
    }

    public void setSortFiled(String sortFiled) {
        this.sortFiled = sortFiled;
    }

    public String getSortSideFiled() {
        return sortSideFiled;
    }

    public void setSortSideFiled(String sortSideFiled) {
        this.sortSideFiled = sortSideFiled;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getFileTitle() {
        return fileTitle;
    }

    public void setFileTitle(String fileTitle) {
        this.fileTitle = fileTitle;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }
}
