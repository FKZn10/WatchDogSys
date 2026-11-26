package com.jpmsoft.projectProducer.pojo.dto.departmentP;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class PositionUserPDto implements Serializable {

    private static final long serialVersionUID = -4058186712564166L;
    private String pupId;
    @NotBlank(message ="项目岗位ID不能为空")
    private String ppId;
    @NotBlank(message = "用户ID不能为空")
    private String uId;
    private String type;
    private Integer pupSort;
    private String pdId;

    public String getPupId() {
        return pupId;
    }

    public void setPupId(String pupId) {
        this.pupId = pupId;
    }

    public String getPpId() {
        return ppId;
    }

    public void setPpId(String ppId) {
        this.ppId = ppId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPupSort() {
        return pupSort;
    }

    public void setPupSort(Integer pupSort) {
        this.pupSort = pupSort;
    }

    public String getPdId() {
        return pdId;
    }

    public void setPdId(String pdId) {
        this.pdId = pdId;
    }
}
