package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.SysContractAuthorityResponse;
import com.jpmsoft.projectProducer.pojo.entity.contract.SysContractAuthority;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface SysContractAuthorityMapper extends BaseMapper<SysContractAuthority> {

    @Select("select Con_Id from  SYS_Contract_Authority where Ca_C_Id=#{cId}")
    List<String> getConIdByCidFromContractAuthority(@Param("cId") String cId);


    List<SysContractAuthorityResponse> getContractAuthorityList(@Param("page") IPage<SysContractAuthorityResponse> page, @Param("params") Map<String, Object> params);

}
