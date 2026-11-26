package com.jpmsoft.jpbpm.pojo.vo;

import java.util.List;

public class FileRelationIdVo {
     public String FileRelationid;
     public List<String>  Relationids;
     public List<ActityinfoVo>   Actityinfos;

    public String getFileRelationid() {
        return FileRelationid;
    }

    public void setFileRelationid(String fileRelationid) {
        FileRelationid = fileRelationid;
    }

    public List<String> getRelationids() {
        return Relationids;
    }

    public void setRelationids(List<String> relationids) {
        Relationids = relationids;
    }

    public List<ActityinfoVo> getActityinfos() {
        return Actityinfos;
    }

    public void setActityinfos(List<ActityinfoVo> actityinfos) {
        Actityinfos = actityinfos;
    }
}



