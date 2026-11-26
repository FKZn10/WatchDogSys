package com.jpmsoft.projectProducer.pojo.vo.investment;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/30 11:37
 */
@Data
public class BudgetCompanyVo implements Serializable {

    private String bdPreparedBy;
    private String bdAuditUnit;
    private String bdPreparedByName;
    private String bdAuditUnitName;
    private String bdPreparedByAbbName;
    private String bdAuditUnitAbbName;

}
