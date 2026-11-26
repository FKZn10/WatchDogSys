package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.userProducer.pojo.dto.RoleDto;
import com.jpmsoft.userProducer.pojo.entity.SysRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface SysRoleMapper extends BaseMapper<SysRole> {

    @Select("select mn.* from SYS_Role mn left join SYS_Role_Power rp on rp.Role_ID=mn.Role_ID where rp.Power_ID=#{powerId} order by mn.Role_Sort ASC ")
    List<SysRole> getRoleByPower(@Param("powerId") String powerId);


    List<RoleDto> getRoleList(@Param("page") IPage<RoleDto> page, @Param("params") Map<String, Object> params);
}
