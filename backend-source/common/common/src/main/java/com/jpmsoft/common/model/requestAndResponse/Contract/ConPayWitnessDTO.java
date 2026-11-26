package com.jpmsoft.common.model.requestAndResponse.Contract;

import lombok.Data;

import java.util.Date;

/**
 * 合同支付现场检测关联表
 *
 * @Author: 李文涛 aaron
 * @date: 2023/10/30 17:29
 */
@Data
public class ConPayWitnessDTO {
    private String id;
    private String payId;
    private String wiId;
    private Integer pwSort;

    private String conId;
    private String wiUnitId;
    private String wiUnitContacts;
    private String wiUnitContactsTel;
    private String wiUnitName;
    private String wiOrderNo;
    private Date wiSamplingStartDate;
    private Date wiSamplingEndDate;
    private Date wiStartDate;
    private Date wiEndDate;

}
