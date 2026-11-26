package com.jpmsoft.jpbpm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.jpbpm.pojo.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    @Select("select U_ID,C_ID,U_Name from  SYS_User where U_State!=0 and U_ID in(${uId});")
    List<Map<String,Object>> getSomeFieldsUser(@Param("uId") String uId);


    List<Map>   getDInfo(@Param("uIds") List<String> uIds);

    List<Map>   getPdInfo(@Param("uIds") List<String> uIds);
    List<Map>  getUidByUidInfo(@Param("params") Map<String, Object> params);

    Set<String> getUserRoleNames(@Param("uId") String uId);
}
