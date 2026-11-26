package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("SYS_FlowKeyWord")
public class FlowKeyWord {
    @TableId(type = IdType.INPUT)
    private String kId;
    private String kCode;
    private String kDescription;
    private String teId;

    public String getkId() {
        return kId;
    }

    public void setkId(String kId) {
        this.kId = kId;
    }

    public String getkCode() {
        return kCode;
    }

    public void setkCode(String kCode) {
        this.kCode = kCode;
    }

    public String getkDescription() {
        return kDescription;
    }

    public void setkDescription(String kDescription) {
        this.kDescription = kDescription;
    }

    public String getTeId() {
        return teId;
    }

    public void setTeId(String teId) {
        this.teId = teId;
    }
}
