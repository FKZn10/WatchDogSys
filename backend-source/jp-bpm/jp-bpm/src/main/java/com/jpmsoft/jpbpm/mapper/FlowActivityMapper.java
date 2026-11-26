package com.jpmsoft.jpbpm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.jpbpm.pojo.entity.FlowActivity;
import com.jpmsoft.jpbpm.pojo.vo.UserInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface FlowActivityMapper extends BaseMapper<FlowActivity> {
    List<UserInfoVo> getPersonnelSettings(@Param("uIds") List<String> uIds, @Param("teId") String teId);

    Map<String, Object> getSecInfoById(@Param("id") String id);

    Map<String, Object> getPreProjectById(@Param("id") String id);

    Map<String, Object> getConContractById(@Param("id") String id);
}
