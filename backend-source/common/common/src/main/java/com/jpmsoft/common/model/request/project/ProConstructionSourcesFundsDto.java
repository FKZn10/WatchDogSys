package com.jpmsoft.common.model.request.project;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目建设计划资金来源
 *
 * @author: 李文涛 aaron
 * @date: 2022/11/7 15:37
 */
@Data
public class ProConstructionSourcesFundsDto implements Serializable {

    private static final long serialVersionUID = -9149676565075249796L;
    private String fsId;
    private String cplId;
    private String fsCode;
    private Date fsCreateTime;
    private Float fsScale;
    private BigDecimal fsMoney;
    private String fsRemark;
    private Integer fsSort;
}
