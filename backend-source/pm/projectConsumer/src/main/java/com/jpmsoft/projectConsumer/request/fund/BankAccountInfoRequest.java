package com.jpmsoft.projectConsumer.request.fund;

import com.jpmsoft.common.model.BasePageEntity;
import lombok.Data;

@Data
public class BankAccountInfoRequest extends BasePageEntity {
    private String id;
    private String accountType;
    private String pId;
    private String pName;
    private String companyID;
    private String companyName;
}
