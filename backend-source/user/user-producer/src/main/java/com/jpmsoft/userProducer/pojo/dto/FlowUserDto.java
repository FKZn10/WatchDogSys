package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;

import java.io.Serializable;

public class FlowUserDto extends BaseModeRequest implements Serializable {
    private static final long serialVersionUID = 5229676153617803192L;
    public  String  flowuserid;
    public  String  flowuserflow;
    public  String  flowuseract;
    public  String  flowuserrole;

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
