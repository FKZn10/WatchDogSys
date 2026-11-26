package com.jpmsoft.jpbpm.pojo.dto;

public class WorkFlowFileDto {
    private  String Fileid;
    private  String TaskProseeid;
    private  String file_relation_id;
    private String Uid;
    private  String ids;
    public   String tCode;
    public String year;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getFileid() {
        return Fileid;
    }

    public void setFileid(String fileid) {
        Fileid = fileid;
    }

    public String getTaskProseeid() {
        return TaskProseeid;
    }

    public void setTaskProseeid(String taskProseeid) {
        TaskProseeid = taskProseeid;
    }

    public String getFile_relation_id() {
        return file_relation_id;
    }

    public void setFile_relation_id(String file_relation_id) {
        this.file_relation_id = file_relation_id;
    }

    public String gettCode() {
        return tCode;
    }

    public void settCode(String tCode) {
        this.tCode = tCode;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
