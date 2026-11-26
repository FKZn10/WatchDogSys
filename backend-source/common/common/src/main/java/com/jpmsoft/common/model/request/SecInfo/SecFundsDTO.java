package com.jpmsoft.common.model.request.SecInfo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 标段经费选择
 *
 * @Author: 李文涛 aaron
 * @date: 2024/2/19 8:39
 */
@Data
public class SecFundsDTO implements Serializable {

    private static final long serialVersionUID = -8812310953238710127L;

    private String id;
    private String secId;
    private String sfCard;
    private String sfAccount;
    private String sfName;
    private String sfSubject;
    private String sfDepartment;
    private String sfHead;
    private Integer sfSort;
    private String sfCode;
    private String sfHeadAccount;
    private String sfHeadName;
    private String sfDepartmentCode;
    private String sfDepartmentName;
    private BigDecimal sfMoney;
    private String sfMain;
}
