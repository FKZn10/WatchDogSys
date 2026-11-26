package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.contract.ContractPermissionUserDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.ContractPermissionUser;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/2/6 15:19
 */
public interface IContractPermissionUserService extends IService<ContractPermissionUser> {

    BaseResponse addContractOne(String uid, String conId,String token);

    BaseResponse addContractUserBatch(List<String> userIds, String conId, String token);

    BaseResponse addContractAll(String uid, String token);

    BaseResponse deleteContractAll(String uid);

    BaseResponse deleteByConIdAndUid(String uid, String conId);

    BaseResponse deleteById(String id);

    BaseResponse selectUserPage(ContractPermissionUserDTO request);

    BaseResponse selectContractPage(ContractPermissionUserDTO request);

    BaseResponse getCurrentUserConIdList(String token);
}
