package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.Contract.ConContractResponse;
import com.jpmsoft.projectProducer.pojo.dto.contract.ContractPermissionUserDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.ContractPermissionUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/2/6 15:19
 */
public interface ContractPermissionUserMapper extends BaseMapper<ContractPermissionUser> {

    @Select(" select  max(cpu_Sort) from Contract_Permission_User where u_id=#{uid}")
    Integer selectMaxSort(@Param("uid") String uid);

    @Select(" select  max(cpu_Sort) from Contract_Permission_User where con_id=#{conId}")
    Integer selectContractMaxSort(@Param("conId") String conId);

    List<ContractPermissionUserDTO> selectUserPage(@Param("page") IPage<ContractPermissionUserDTO> page, @Param("params") ContractPermissionUserDTO params);

    List<ConContractResponse> selectContractPage(@Param("page") IPage<ConContractResponse> page, @Param("params") ContractPermissionUserDTO params);

}
