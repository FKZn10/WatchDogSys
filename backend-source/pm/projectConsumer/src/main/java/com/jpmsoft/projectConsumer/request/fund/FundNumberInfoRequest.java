package com.jpmsoft.projectConsumer.request.fund;

import com.jpmsoft.common.model.BasePageEntity;
import lombok.Data;

@Data
public class FundNumberInfoRequest extends BasePageEntity {
    private String id;
    private String pId;
    private String pName;
    private String fnName;
    private String fnCode;
    private String prId;
}
