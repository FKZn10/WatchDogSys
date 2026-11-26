package com.jpmsoft.custom.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.custom.pojo.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    Set<String> getUserRoleNames(@Param("uId") String uId);
}
