package com.jpmsoft.jpbpm.pojo.dto;

import java.util.List;


public class ParameterDto {

    private String keyname;
    private String vlue;
    private List<ParameterDto> vlues;

    public String getKeyname() {
        return keyname;
    }

    public void setKeyname(String keyname) {
        this.keyname = keyname;
    }

    public String getVlue() {
        return vlue;
    }

    public void setVlue(String vlue) {
        this.vlue = vlue;
    }

    public List<ParameterDto> getVlues() {
        return vlues;
    }

    public void setVlues(List<ParameterDto> vlues) {
        this.vlues = vlues;
    }
}
