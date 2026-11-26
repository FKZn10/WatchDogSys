package com.jpmsoft.common.model.requestAndResponse.Contract;

import lombok.Data;


@Data
public class ContractLedgerForWJTParam {

    private Integer page;

    private Integer size;

    private String projectId;

    private String projectName;

    private String contractName;

    private String pId;

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }
}
