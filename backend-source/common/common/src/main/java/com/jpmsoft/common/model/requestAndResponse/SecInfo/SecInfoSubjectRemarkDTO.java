package com.jpmsoft.common.model.requestAndResponse.SecInfo;

import lombok.Data;

import java.util.Date;

/**
 * 招标计划信息 合同外工程及费用 备注
 */
@Data
public class SecInfoSubjectRemarkDTO {
    private String id;

    /**
     * 关联id
     */
    private String mainId;

    /**
     * 估算概算科目id
     */
    private String subIebId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 备注
     */
    private String remark;
}
