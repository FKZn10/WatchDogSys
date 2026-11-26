package com.jpmsoft.jpbpm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.jpbpm.pojo.entity.SysPositionPRole;
import com.jpmsoft.jpbpm.pojo.vo.SysPositionPRoleAndUidVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysPositionPRoleMapper extends BaseMapper<SysPositionPRole> {

    List<SysPositionPRoleAndUidVo>   getUidByRuleIdAndPid(@Param("params") Map<String, String> params);
}
