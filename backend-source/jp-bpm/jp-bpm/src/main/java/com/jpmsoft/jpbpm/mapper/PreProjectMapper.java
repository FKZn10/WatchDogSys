package com.jpmsoft.jpbpm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.jpbpm.pojo.entity.PreProject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PreProjectMapper extends BaseMapper<PreProject> {
    @Select("select  P_Competent_Department   from  Pre_Project where  P_ID=#{projectId}")
    String getDidByPid(@Param("projectId") String projectId);
}
