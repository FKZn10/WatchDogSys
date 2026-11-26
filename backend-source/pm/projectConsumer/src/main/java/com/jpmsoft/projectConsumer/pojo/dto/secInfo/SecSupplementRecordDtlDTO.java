package com.jpmsoft.projectConsumer.pojo.dto.secInfo;

import lombok.Data;

import java.util.Date;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/11/27 14:39
 */
@Data
public class SecSupplementRecordDtlDTO {

    private String srdId;
    private String exId;
    private String sesId;
    private Integer srdCount;
    /**
     * 选取类型(抽取|自选)
     */
    private String srdType;
    private Integer srdAvoid;
    private Integer srdAttend;
    private Integer srdVacation;
    private Date srdTime;
    private String srdMark;
}
