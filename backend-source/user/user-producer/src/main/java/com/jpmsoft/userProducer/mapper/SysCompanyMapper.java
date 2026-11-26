package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.userProducer.pojo.entity.SysCompany;
import com.jpmsoft.userProducer.pojo.vo.SysCompanyVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface SysCompanyMapper extends BaseMapper<SysCompany> {


    @Select("select Con_Party_B_Id as cId from Con_Contract where Con_Id=#{conId} union all" +
            " select Con_Party_B1 as cId from Con_Contract where Con_Id=#{conId} union all" +
            " select Con_Party_B2 as cId from Con_Contract where Con_Id=#{conId} union all" +
            " select Con_Party_B3 as cId from Con_Contract where Con_Id=#{conId} union all" +
            " select Con_Party_B4 as cId from Con_Contract where Con_Id=#{conId} union all" +
            " select Con_Party_B5 as cId from Con_Contract where Con_Id=#{conId}")
    List<String> getContractPartyB(@Param("conId") String conId);

    List<SysCompany> getCompanyList(@Param("page") IPage<SysCompany> iPage, @Param("params") Map<String, Object> query);

    List<SysCompanyVo> getCompanyByPId(@Param("page") Page<SysCompanyVo> page, @Param("params") Map<String, Object> map);
}
