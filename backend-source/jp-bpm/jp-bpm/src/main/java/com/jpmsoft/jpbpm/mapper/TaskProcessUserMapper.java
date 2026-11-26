package com.jpmsoft.jpbpm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.jpbpm.pojo.entity.TaskProcessUser;
import com.jpmsoft.jpbpm.pojo.vo.SysTaskProcessUserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TaskProcessUserMapper extends BaseMapper<TaskProcessUser> {
  List<SysTaskProcessUserVo> selectTpuUserInfo (@Param("tpIds") List<String> tpIds,@Param("teId") String teId,@Param("tableCode") String tableCode);
}
