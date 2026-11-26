package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.userProducer.pojo.entity.FlowUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface FlowUserMapper extends BaseMapper<FlowUser> {


    @Select("select  DISTINCT  FlowName from SYS_FlowUser   left  join   SYS_Flow on FlowID=FlowUserFlow where FlowUserRole=#{flowUserRole} ;")
    List<String>  getFlowByFlowUserRole(@Param("flowUserRole") String flowUserRole);
}
