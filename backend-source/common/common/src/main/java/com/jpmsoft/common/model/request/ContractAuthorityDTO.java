package com.jpmsoft.common.model.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 合同权限配置信息
 * @Author: 李文涛 aaron
 * @date: 2024/9/23 17:12
 */
@Data
public class ContractAuthorityDTO {

    /**
     * 授权访问的合同ID集合
     */
    private List<String> enables=new ArrayList<>();
    /**
     * 禁止访问的合同ID集合
     */
    private List<String> disables=new ArrayList<>();
}
