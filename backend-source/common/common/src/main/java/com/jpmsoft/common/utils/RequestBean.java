package com.jpmsoft.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * zhangl
 * 请求参数封装
 *
 * @param <T>
 */
public class RequestBean<T> implements Serializable {
    private static final long serialVersionUID = -6351210629803310653L;

    private String type;

    private String baseService;

    public String getType() {
        return type;
    }

    public String getBaseService() {
        return baseService;
    }

    public void setBaseService(String baseService) {
        this.baseService = baseService;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String message;
    /**
     * 请求参数
     */
    private T info;

    public T getInfo() {
        if (info == null && infos != null && infos.size() > 0) {
            ObjectMapper mapper = new ObjectMapper();
            return infos.get(0);
        } else {
            return info;
        }
    }

    private List<T> infos;

    public List<T> getInfos() {
        if (this.infos != null && this.infos.size() > 0) {
            return this.infos;
        } else {
            List<T> infoList = new ArrayList<T>();
            if (this.info != null) {
                infoList.add(this.info);
            }
            return infoList;
        }
    }

    public void setInfos(List<T> infos) {
        this.infos = infos;
        if (infos != null && infos.size() > 0) {
            this.info = infos.get(0);
        }
    }

    public RequestBean() {

    }

    public RequestBean(String type, String message, T info, List<T> infos) {
        this.type = type;
        this.message = message;
        this.info = info;
        this.infos = infos;
    }



}
