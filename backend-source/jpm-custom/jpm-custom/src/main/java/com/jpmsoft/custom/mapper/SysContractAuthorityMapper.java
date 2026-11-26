package com.jpmsoft.custom.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.custom.pojo.entity.SysContractAuthority;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface SysContractAuthorityMapper extends BaseMapper<SysContractAuthority> {

    @Select("select Con_Id from  SYS_Contract_Authority where Ca_C_Id=#{cId}")
    List<String> getConIdByCidFromContractAuthority(@Param("cId") String cId);
}
