package com.jpmsoft.userProducer.pojo.dto;

import lombok.Data;

/**
 * 模板字段
 * Created By:李文涛 aaron
 * Created Time:2022/7/5 10:06
 */
@Data
public class SysPayFieldDto {

    private static final long serialVersionUID = -6536543318768798582L;

    private String id;
    private String pfName;
    private String pfCode;
    private String pfType;
    private String pfFormula;
    private String pfVerification;
    private String pfRemark;
    private String pfSort;
    private String pfWrite;

}
