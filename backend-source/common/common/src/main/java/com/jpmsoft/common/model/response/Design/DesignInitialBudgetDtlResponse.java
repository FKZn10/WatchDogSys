package com.jpmsoft.common.model.response.Design;

import java.math.BigDecimal;

public class DesignInitialBudgetDtlResponse {

    private String dibdId;

    //初步设计表id
    private String diId;

    //概算主表id
    private String bdId;

    //概算明细id
    private String bddId;

    //项目及费用名称
    private String bddName;

    //合价（投资）
    private BigDecimal bddTotal;


    public String getDibdId() {
        return dibdId;
    }

    public void setDibdId(String dibdId) {
        this.dibdId = dibdId;
    }

    public String getDiId() {
        return diId;
    }

    public void setDiId(String diId) {
        this.diId = diId;
    }

    public String getBdId() {
        return bdId;
    }

    public void setBdId(String bdId) {
        this.bdId = bdId;
    }

    public String getBddId() {
        return bddId;
    }

    public void setBddId(String bddId) {
        this.bddId = bddId;
    }

    public String getBddName() {
        return bddName;
    }

    public void setBddName(String bddName) {
        this.bddName = bddName;
    }

    public BigDecimal getBddTotal() {
        return bddTotal;
    }

    public void setBddTotal(BigDecimal bddTotal) {
        this.bddTotal = bddTotal;
    }
}
