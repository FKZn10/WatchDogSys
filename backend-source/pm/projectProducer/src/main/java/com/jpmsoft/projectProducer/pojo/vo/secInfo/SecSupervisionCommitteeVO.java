package com.jpmsoft.projectProducer.pojo.vo.secInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 监督委员会
 *
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:31
 */
@Data
public class SecSupervisionCommitteeVO implements Serializable {


    private static final long serialVersionUID = 2797623645977795024L;
    private String ssdId;
    private String sccId;
    private String ssdUname;
    private String ssdPhone;
    private String ssdCompany;
    private String ssdRemark;
    private Integer ssdSort;

    private String ssdCompanyName;
}
