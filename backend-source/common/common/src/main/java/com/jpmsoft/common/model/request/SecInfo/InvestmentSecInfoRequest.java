package com.jpmsoft.common.model.request.SecInfo;

import lombok.Data;

@Data
public class InvestmentSecInfoRequest {

    private String projectId;

    //标段名称/费用名称
    private String isbdSectionName;

    //投资估算拆分表id
    private String ieId;

    //投资估算拆分详细表id
    private String ieDetailId;

    //投资概算拆分表id
    private String ibId;

    //投资概算拆分详细表id
    private String ibDetailId;


}
