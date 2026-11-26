package com.jpmsoft.common.model.requestAndResponse.Design;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/9/25 14:13
 */
@Data
public class DesignConstructionDtlDTO implements Serializable {
    private String id;
    private String dcId;
    private String dcdSpecialty;
    private String dcdFile;
    private String dcdFileIds;
}
