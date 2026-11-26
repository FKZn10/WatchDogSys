package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.dto.contract.ContractPermissionCompanyDTO;
import com.jpmsoft.projectProducer.pojo.dto.contract.ContractPermissionUserDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.ContractPermissionCompany;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/2/6 16:47
 */
public interface ContractPermissionCompanyMapper extends BaseMapper<ContractPermissionCompany> {


    @Select(" select  max(cpc_Sort) from Contract_Permission_Company where c_id=#{cid}")
    Integer selectMaxSort(@Param("cid") String cid);


    List<ContractPermissionCompanyDTO> getPermissionCompanyList(@Param("page") IPage<ContractPermissionCompanyDTO> page, @Param("params") ContractPermissionCompanyDTO params);

}
