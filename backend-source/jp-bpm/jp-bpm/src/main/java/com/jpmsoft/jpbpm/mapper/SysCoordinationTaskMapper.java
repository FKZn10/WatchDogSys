package com.jpmsoft.jpbpm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.CoordinationTaskResponse;
import com.jpmsoft.jpbpm.pojo.dto.AgencyStatisticsDto;
import com.jpmsoft.jpbpm.pojo.entity.SysCoordinationTask;
import com.jpmsoft.jpbpm.pojo.vo.AgencyStatisticsVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysCoordinationTaskMapper extends BaseMapper<SysCoordinationTask> {

    /**
     * 批量新增
     *
     * @param entitys
     */
    void insertCoordinationTaskByBatchs(@Param("entitys") List<SysCoordinationTask> entitys);

    @Delete("delete from   SYS_CoordinationTask where  CT_TaskID=#{taskId}")
    void deleteCoordinationTaskByTaskId(@Param("taskId") String taskId);

    List<CoordinationTaskResponse> getUserCoordinationTaskList(@Param("params") Map<String, Object> params);
    Integer getUserCoordinationTaskListCount(@Param("params") Map<String, Object> params);

    List<CoordinationTaskResponse> getCoordinationTaskList(@Param("params") Map<String, Object> params);
    Integer getCoordinationTaskListCount(@Param("params") Map<String, Object> params);

    List<AgencyStatisticsVo> statisticsUserAgency(@Param("params") AgencyStatisticsDto params,@Param("teId") String teId);


    @Delete("delete   from SYS_CoordinationTask where  CT_TaskID in (select  TaskID from  SYS_Task where TaskEntityID=#{taskEntityId} and TaskFlow=#{taskFlow} and TaskSendUser=#{taskSendUser})")
    Integer deleteByTaskEntityIDAndTaskFlowAndTaskSendUser(@Param("taskEntityId") String taskEntityId, @Param("taskFlow") String taskFlow, @Param("taskSendUser") String taskSendUser);


    @Delete("delete   from SYS_CoordinationTask where  CT_TaskID in (select  TaskID from   SYS_Task where TaskCurrentActID=#{actId} and  TaskID=#{taskId} and  TaskEntityID=#{entityId})")
    Integer deleteByEntityIdAndCurrentActId(@Param("entityId") String entityId,@Param("actId") String actId ,@Param("taskId") String taskId);


    @Delete("delete   from SYS_CoordinationTask where  CT_TaskID in (select  TaskID from  SYS_Task where TaskID=#{taskId} or TaskEntityID=#{TaskEntityID})")
    void deleteByIdOrTaskEntityID(@Param("taskId") String taskId, @Param("TaskEntityID") String TaskEntityID);


}
