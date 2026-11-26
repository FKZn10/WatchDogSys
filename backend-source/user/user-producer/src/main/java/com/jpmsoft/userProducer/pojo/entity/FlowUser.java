package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("SYS_FlowUser")
public class FlowUser {
    @TableId(type = IdType.INPUT)
    public String flowuserid;
    public String flowuserflow;
    public String flowuseract;
    public String flowuserrole;

    public String getFlowuserid() {
        return flowuserid;
    }

    public void setFlowuserid(String flowuserid) {
        this.flowuserid = flowuserid;
    }

    public String getFlowuserflow() {
        return flowuserflow;
    }

    public void setFlowuserflow(String flowuserflow) {
        this.flowuserflow = flowuserflow;
    }

    public String getFlowuseract() {
        return flowuseract;
    }

    public void setFlowuseract(String flowuseract) {
        this.flowuseract = flowuseract;
    }

    public String getFlowuserrole() {
        return flowuserrole;
    }

    public void setFlowuserrole(String flowuserrole) {
        this.flowuserrole = flowuserrole;
    }
}
