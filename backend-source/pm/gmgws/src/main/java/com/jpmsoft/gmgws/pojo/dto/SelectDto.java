package com.jpmsoft.gmgws.pojo.dto;

import java.io.Serializable;
import java.util.List;

public class SelectDto implements Serializable {

    private static final long serialVersionUID = 6536661790333181367L;
    private String id;
    private String code;
    private List<String> codes;
    private String teId;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<String> getCodes() {
        return codes;
    }

    public void setCodes(List<String> codes) {
        this.codes = codes;
    }

    public String getTeId() {
        return teId;
    }

    public void setTeId(String teId) {
        this.teId = teId;
    }
}
