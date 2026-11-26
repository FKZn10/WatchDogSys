package com.jpmsoft.common.model.request.Contract;

import lombok.Data;

@Data
public class EngineeringFundApplicationRejectRequest {

    // 申请id
    private String payApplyId;

    // 意见
    private String opinion;

}
