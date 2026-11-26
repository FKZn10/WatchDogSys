package com.jpmsoft.userProducer.pojo.vo;

import java.io.Serializable;
import java.util.List;

public class PowerTreeVo implements Serializable {
    private static final long serialVersionUID = -6531874187916772690L;
    /// 节点id
    public String key;
    /// 节点名称
    public String title;
    /// 子集
    public List<PowerTreeVo> children;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<PowerTreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<PowerTreeVo> children) {
        this.children = children;
    }
}
