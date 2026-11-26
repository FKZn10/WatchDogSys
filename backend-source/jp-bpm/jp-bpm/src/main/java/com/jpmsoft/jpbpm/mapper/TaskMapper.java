package com.jpmsoft.jpbpm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.response.SysFileResponse;
import com.jpmsoft.common.model.response.TaskResponse;
import com.jpmsoft.jpbpm.pojo.dto.AgencyStatisticsDto;
import com.jpmsoft.jpbpm.pojo.entity.Task;
import com.jpmsoft.jpbpm.pojo.vo.AgencyStatisticsVo;
import com.jpmsoft.jpbpm.pojo.vo.AutomaticTasks;
import com.jpmsoft.jpbpm.pojo.vo.TaskCoordinationTaskVo;
import com.jpmsoft.jpbpm.pojo.vo.TaskCountVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper
public interface TaskMapper extends BaseMapper<Task> {


    @Delete("delete from  SYS_Task where TaskID=#{taskId} or TaskEntityID=#{TaskEntityID}")
    void deleteTaskByIdOrTaskEntityID(@Param("taskId") String taskId, @Param("TaskEntityID") String TaskEntityID);


    @Delete("delete from  SYS_Task where TaskEntityID=#{TaskEntityID}")
    void deleteTaskByTaskEntityID(@Param("TaskEntityID") String TaskEntityID);


    @Delete("delete from  SYS_Task where TaskEntityID=#{taskEntityId} and TaskFlow=#{taskFlow} and TaskSendUser=#{taskSendUser}")
    Integer deleteTaskByTaskEntityIDAndTaskFlowAndTaskSendUser(@Param("taskEntityId") String taskEntityId, @Param("taskFlow") String taskFlow, @Param("taskSendUser") String taskSendUser);


    List<TaskResponse> getUserTaskList(@Param("params") Map<String, Object> params);

    List<TaskResponse> getNeedMassageTask(@Param("newDate") String newDate);

    Integer getUserTaskListCount(@Param("params") Map<String, Object> params);

    List<TaskResponse> getTaskList(@Param("params") Map<String, Object> params);

    Integer getTaskListCount(@Param("params") Map<String, Object> params);

    List<AgencyStatisticsVo> statisticsUserAgency(@Param("params") AgencyStatisticsDto params, @Param("teId") String teId);


    List<TaskCountVo> getPrescription(@Param("params") Map<String, Object> params);


    List<AutomaticTasks> getAutomaticTasks(@Param("params") Map<String, Object> params);

    Integer insertTask(@Param("list") List<Task> list);

    List<TaskResponse> getUserTaskListSubmeter(@Param("params") Map<String, Object> params);
    Integer getUserTaskListCountSubmeter(@Param("params") Map<String, Object> params);
    List<TaskResponse> getNeedMassageTaskSubmeter(@Param("newDate") String newDate);
    List<TaskResponse> getTaskListSubmeter(@Param("params") Map<String, Object> params);
    Integer getTaskListCountSubmeter(@Param("params") Map<String, Object> params);
    List<AgencyStatisticsVo> statisticsUserAgencySubmeter(@Param("params") AgencyStatisticsDto params, @Param("teId") String teId);
    List<TaskCountVo> getPrescriptionSubmeter (@Param("params") Map<String, Object> params);




    List<String> getTaskCoordinationTaskKey(@Param("params") Map<String, Object> params,@Param("key") String key);
    Integer getTaskCoordinationTaskCount(@Param("params") Map<String, Object> params);
    List<TaskCoordinationTaskVo>  getTaskCoordinationTask(@Param("params") Map<String, Object> params);

    List<SysFileResponse> getUserTodoList(@Param("page") Page<SysFileResponse> page, @Param("uId") String uId, @Param("customerId") String customerId, @Param("params") Map<String, Object> request, @Param("states") List<Integer> states);

    Integer getUserTodoCount(@Param("uId") String uId, @Param("customerId") String customerId, @Param("states") List<Integer> states);

    List<String> getUserTodoListTaskKey(@Param("params") Map<String, Object> request, @Param("uId") String uId, @Param("customerId") String customerId, @Param("key") String key, @Param("states") List<Integer> states);

    List<String> getExistFileIds(@Param("tableName") String tableName, @Param("primaryKey") String primaryKey, @Param("fileIds") Set<String> fileIds);
}
