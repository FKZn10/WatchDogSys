package com.jpmsoft.projectProducer.pojo.dto.secInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 清标小组清单
 *
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:31
 */
@Data
public class SecClearTeamDtlDTO implements Serializable {


    private static final long serialVersionUID = 4366593016143478598L;
    private String ctdId;
    private String sctId;
    private String ctdUname;
    private String ctdPhone;
    private String ctdCompany;
    private String ctdRemark;
    private Integer ctdSort;
}
