package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.userProducer.pojo.entity.SysTenantRule;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/8/28 16:28
 */
public interface SysTenantRuleMapper extends BaseMapper<SysTenantRule> {


    @Select("select count(*) from pre_project where  Te_Id=#{teId} ")
    Integer getTenantProjectCount(@Param("teId") String teId);

    @Select("select count(*) from Sys_User where  Te_Id=#{teId} ")
    Integer getTenantUserCount(@Param("teId") String teId);
}
