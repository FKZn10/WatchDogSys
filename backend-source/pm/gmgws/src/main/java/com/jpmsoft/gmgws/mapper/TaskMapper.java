package com.jpmsoft.gmgws.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.gmgws.pojo.entity.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TaskMapper extends BaseMapper<Task> {


    List<Task> queryNodes(@Param("flowIds") List<String> flowIds);
}
