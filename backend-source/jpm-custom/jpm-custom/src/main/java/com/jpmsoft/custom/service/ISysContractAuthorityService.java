package com.jpmsoft.custom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.ContractAuthorityDTO;
import com.jpmsoft.custom.pojo.entity.SysContractAuthority;

import java.util.List;

public interface ISysContractAuthorityService extends IService<SysContractAuthority> {

    /**
     * 根据单位获取其带权限的合同ID集合
     *
     * @param cId
     * @return
     */
    List<String> getConIdByCidFromContractAuthority(String cId);


    /**
     * 合同权限以人为主 单位授权为辅助
     * 人禁用或者授权 则直接忽略单位授权
     * 人未授权 则以单位授权为主
     *
     * @param cId
     * @param uid
     * @return
     */
    ContractAuthorityDTO getConIdByCidFromContractAuthority(String cId, String uid);
}
