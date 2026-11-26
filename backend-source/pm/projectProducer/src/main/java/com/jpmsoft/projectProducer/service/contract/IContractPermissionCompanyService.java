package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.contract.ContractPermissionCompanyDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.ContractPermissionCompany;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/2/6 16:47
 */
public interface IContractPermissionCompanyService extends IService<ContractPermissionCompany> {

    BaseResponse addPermissionCompany(String conId, String cId, String token);

    BaseResponse deletePermissionCompanyById(String id);

    BaseResponse getPermissionCompanyList(ContractPermissionCompanyDTO request);
}
