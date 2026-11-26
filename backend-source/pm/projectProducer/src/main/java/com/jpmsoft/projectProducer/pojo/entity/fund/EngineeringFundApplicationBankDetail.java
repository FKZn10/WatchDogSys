package com.jpmsoft.projectProducer.pojo.entity.fund;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("Engineering_Fund_Application_Bank_Detail")
public class EngineeringFundApplicationBankDetail {
    private String id;
    private String efaId;
    private String baId;
}
