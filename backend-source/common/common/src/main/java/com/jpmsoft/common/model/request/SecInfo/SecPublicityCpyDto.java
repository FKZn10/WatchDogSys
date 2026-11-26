package com.jpmsoft.common.model.request.SecInfo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 中标成交单位
 *
 * @author: 李文涛 aaron
 * @date: 2022/7/25 10:43
 */
@Data
public class SecPublicityCpyDto implements Serializable {

    private static final long serialVersionUID = 3482619931295575430L;

    private String id;
    private String prId;
    private String spcUnit;
    private BigDecimal spcAmt;
    private Date createTime;
    private String spcDescribe;

}
