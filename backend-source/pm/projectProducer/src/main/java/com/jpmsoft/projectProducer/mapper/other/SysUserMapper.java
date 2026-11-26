package com.jpmsoft.projectProducer.mapper.other;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.SysUser;
import com.jpmsoft.projectProducer.pojo.vo.sys.SysUserVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface SysUserMapper extends BaseMapper<SysUser> {

    List<SysUserVo> getUserListByMoreCondition(@Param("params") Map<String, Object> params);

    List<SysUserVo> getUesrInfoByIds(@Param("uIds") List<String> uIds);

    List<SysUserVo> getUserByPid(@Param("ids")List<String> ids);

    @Select("select count(*) from pre_project where  Te_Id=#{teId} ")
    Integer getTenantProjectCount(@Param("teId") String teId);

    @Select("select count(*) from Sys_User where  Te_Id=#{teId} ")
    Integer getTenantUserCount(@Param("teId") String teId);

    List<SysUserVo> getComPanyUser(@Param("dName") List<String> dName,@Param("pName") List<String> pName);

    Set<String> getUserRoleNames(@Param("uId") String uId);
}
