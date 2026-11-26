package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;
import com.jpmsoft.userProducer.constants.BaseMsg;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class FlowKeyWordDto extends BaseModeRequest implements Serializable {
    private static final long serialVersionUID = -7905710491597160665L;

    @NotBlank(message = BaseMsg.kIdIsNull)
    public  String kId;
    public  String kCode;
    public  String kDescription;

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
}
