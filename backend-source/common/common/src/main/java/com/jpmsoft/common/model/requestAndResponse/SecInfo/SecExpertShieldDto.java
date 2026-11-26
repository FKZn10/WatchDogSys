package com.jpmsoft.common.model.requestAndResponse.SecInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 专家抽取屏蔽表
 *
 * @author: 李文涛 aaron
 * @date: 2022/10/10 14:01
 */
@Data
public class SecExpertShieldDto extends SecSelectionRecordDtlModel implements Serializable {

    private static final long serialVersionUID = 3220346605659322334L;

    private String id;
    private String srId;
    private String exId;

}
