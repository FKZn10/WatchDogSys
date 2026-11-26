package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.userProducer.pojo.entity.SysPositionRoleP;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface SysPositionRolePMapper extends BaseMapper<SysPositionRoleP> {


    List<Map<String,String>> getPositionRoleP(@Param("pId") String pId, @Param("uId") String uId);

}
