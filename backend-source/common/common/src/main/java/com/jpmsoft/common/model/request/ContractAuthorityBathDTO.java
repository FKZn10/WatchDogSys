package com.jpmsoft.common.model.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 合同权限配置 批量操作入参
 * @Author: 李文涛 aaron
 * @date: 2024/9/23 16:28
 */
@Data
public class ContractAuthorityBathDTO implements Serializable {

    private List<String>  conIds;
    private List<String>  companyIds;
    private List<String>  userIds;
    private String caType;
    /**
     * 批量删除 delete
     * 批量添加 add
     */
    private String action;
}
