package com.jpmsoft.common.model.request.SecInfo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: 李文涛 aaron
 * @date: 2022/7/24 20:33
 */
@Data
public class SecControlCpyDto implements Serializable {

    private static final long serialVersionUID = 1638117326267393371L;
    private String id;
    private String cpId;
    private String sccAuditUnit;
    private String sccUnitPrice;
    private Date createTime;
}
