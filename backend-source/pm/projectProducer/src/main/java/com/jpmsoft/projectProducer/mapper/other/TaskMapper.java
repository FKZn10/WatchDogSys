package com.jpmsoft.projectProducer.mapper.other;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.jpmsoft.projectProducer.pojo.entity.other.Task;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TaskMapper extends BaseMapper<Task> {


    @Delete("delete from  SYS_Task where TaskID=#{taskId} or TaskEntityID=#{TaskEntityID}")
    void deleteTaskByIdOrTaskEntityID(@Param("taskId") String taskId, @Param("TaskEntityID") String TaskEntityID);


    @Delete("delete from  SYS_Task where TaskEntityID=#{TaskEntityID}")
    void deleteTaskByTaskEntityID(@Param("TaskEntityID") String TaskEntityID);


    @Delete("delete from  SYS_Task where TaskEntityID=#{taskEntityId} and TaskFlow=#{taskFlow} and TaskSendUser=#{taskSendUser}")
    Integer deleteTaskByTaskEntityIDAndTaskFlowAndTaskSendUser(@Param("taskEntityId") String taskEntityId, @Param("taskFlow") String taskFlow, @Param("taskSendUser") String taskSendUser);

    @Delete("delete from  SYS_Task where TaskEntityID=#{taskEntityId} and TaskUser!=#{taskUser}")
    Integer deleteTaskByTaskEntityIDAndUid(@Param("taskEntityId") String taskEntityId, @Param("taskUser") String taskUser);
}
