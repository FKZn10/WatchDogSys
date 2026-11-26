package com.jpmsoft.projectProducer.pojo.entity.fund;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("Fund_Number_Info")
public class FundNumberInfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -9069857049957924551L;
    private String id;
    private String pId;
    private String fnName;
    private String fnCode;
    private String prId;
    private Double fnAmount;
    private Date fnReceiptVerificationDate;
}
