package com.jpmsoft.projectProducer.pojo.dto.secInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 质询答辩评审组人员
 *
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:31
 */
@Data
public class SecDefenseReviewDTO  implements Serializable {

    private static final long serialVersionUID = 2212325419010537058L;
    private String sdrId;
    private String sqId;
    private String sdrUname;
    private String sdrPhone;
    private String sdrCompany;
    private String sdrRemark;
    private Integer sdrSort;
}
