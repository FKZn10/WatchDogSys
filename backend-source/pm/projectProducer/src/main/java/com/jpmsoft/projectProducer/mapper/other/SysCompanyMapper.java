package com.jpmsoft.projectProducer.mapper.other;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.SysCompany;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface SysCompanyMapper extends BaseMapper<SysCompany> {


    @Select("select Con_Party_B_Id as cId from Con_Contract where Con_Id=#{conId} union all" +
            " select Con_Party_B1 as cId from Con_Contract where Con_Id=#{conId} union all" +
            " select Con_Party_B2 as cId from Con_Contract where Con_Id=#{conId} union all" +
            " select Con_Party_B3 as cId from Con_Contract where Con_Id=#{conId} union all" +
            " select Con_Party_B4 as cId from Con_Contract where Con_Id=#{conId} union all" +
            " select Con_Party_B5 as cId from Con_Contract where Con_Id=#{conId}")
    List<String> getContractPartyB(@Param("conId") String conId);
}
