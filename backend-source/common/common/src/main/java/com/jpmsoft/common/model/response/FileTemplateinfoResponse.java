package com.jpmsoft.common.model.response;

public class FileTemplateinfoResponse {
    public String fileid;
    public Object taskboj;
    public Object filetemplate;
    public Object fileobj;
    public Object versionUsed;


    public Object getFileobj() {
        return fileobj;
    }

    public void setFileobj(Object fileobj) {
        this.fileobj = fileobj;
    }

    public Object getFiletemplate() {
        return filetemplate;
    }

    public void setFiletemplate(Object filetemplate) {
        this.filetemplate = filetemplate;
    }

    public Object getTaskboj() {
        return taskboj;
    }



    public void setTaskboj(Object taskboj) {
        this.taskboj = taskboj;
    }

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    public Object getVersionUsed() {
        return versionUsed;
    }

    public void setVersionUsed(Object versionUsed) {
        this.versionUsed = versionUsed;
    }
}
