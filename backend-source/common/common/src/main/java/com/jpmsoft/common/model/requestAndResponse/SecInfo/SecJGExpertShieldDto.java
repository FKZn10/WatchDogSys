package com.jpmsoft.common.model.requestAndResponse.SecInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 专家抽取屏蔽表 （建管）
 *
 */
@Data
public class SecJGExpertShieldDto extends SecJGSelectionRecordDtlModel implements Serializable {

    private static final long serialVersionUID = 3220346605659322334L;

    private String id;
    private String srId;
    private String exId;

}
