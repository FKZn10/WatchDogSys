package com.jpmsoft.projectConsumer.pojo.dto.secInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 监督委员会
 *
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:31
 */
@Data
public class SecSupervisionDtlsDTO implements Serializable {


    private static final long serialVersionUID = 2797623645977795024L;
    private String ssdId;
    private String scsId;
    private String ssdUname;
    private String ssdPhone;
    private String ssdCompany;
    private String ssdRemark;
    private Integer ssdSort;
}
