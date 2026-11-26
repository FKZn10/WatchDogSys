package com.jpmsoft.projectProducer.pojo.dto.secInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 入围定标委员
 *
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:31
 */
@Data
public class SecCalibrationDtlsDTO implements Serializable {

    private static final long serialVersionUID = 481801680829895661L;
    private String scdId;
    private String scsId;
    private String scdUname;
    private String scdPhone;
    private String scdCompany;
    private String scdRemark;
    private Integer scdSort;
}
