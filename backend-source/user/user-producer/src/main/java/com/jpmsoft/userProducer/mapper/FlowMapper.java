package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.userProducer.pojo.entity.Flow;
import com.jpmsoft.userProducer.pojo.entity.FlowActivity;
import com.jpmsoft.userProducer.pojo.entity.FlowRule;
import com.jpmsoft.userProducer.pojo.entity.FlowUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface FlowMapper extends BaseMapper<Flow> {


    @Select("update  SYS_Flow set FlowEnable='0' where FlowProject=#{projectId} and FlowCompany=#{comId} and T_Code=#{tId} and FlowID!=#{fId}")
    String updateFlowenable(@Param("projectId") String projectId, @Param("tId") String tId, @Param("fId") String fId,@Param("comId") String comId);

    Integer insertFlow(@Param("sqlList") List<String> sqlList);


    Integer insertFlowActivity(@Param("list") List<FlowActivity> List,@Param("teId") String teId);

    Integer insertFlowActivitys(@Param("sqlstr") String sqlstr);


    Integer insertFlowUser(@Param("list") List<FlowUser> List,@Param("teId") String teId);

    Integer insertFlowRule(@Param("list") List<FlowRule> List,@Param("teId") String teId);
}
