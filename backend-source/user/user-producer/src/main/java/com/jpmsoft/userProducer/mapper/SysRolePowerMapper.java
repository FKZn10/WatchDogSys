package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.userProducer.pojo.entity.SysRolePower;
import com.jpmsoft.userProducer.pojo.vo.RolePowerVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface SysRolePowerMapper extends BaseMapper<SysRolePower> {


    @Select("select b.Role_Name as \"roleName\",a.Power_Id as \"powerId\"  from  " +
            "SYS_Role_Power a  left join  SYS_Role b on a.Role_ID=b.Role_ID")
    List<RolePowerVo> getRoleNameByPowerId();

    @Select("select b.Role_Name as \"roleName\",a.Power_Id as \"powerId\"  from  " +
            "SYS_Role_Power a  left join  SYS_Role b on a.Role_ID=b.Role_ID  where b.Te_Id=#{teId}")
    List<RolePowerVo> getRoleNameByPowerIdByTeId(@Param("teId") String teId);
}
