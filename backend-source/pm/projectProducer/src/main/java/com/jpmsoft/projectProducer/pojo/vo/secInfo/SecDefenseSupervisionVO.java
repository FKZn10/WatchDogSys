package com.jpmsoft.projectProducer.pojo.vo.secInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 质询答辩监督组人员
 *
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:31
 */
@Data
public class SecDefenseSupervisionVO implements Serializable {

    private static final long serialVersionUID = 8239084240963531230L;
    private String sdsId;
    private String sqId;
    private String sdsUname;
    private String sdsPhone;
    private String sdsCompany;
    private String sdsRemark;
    private Integer sdsSort;

    private String sdsCompanyName;
}
