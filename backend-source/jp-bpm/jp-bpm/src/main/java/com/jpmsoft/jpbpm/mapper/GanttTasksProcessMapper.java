package com.jpmsoft.jpbpm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.jpbpm.pojo.entity.GanttTasksProcess;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;

@Mapper
public interface GanttTasksProcessMapper extends BaseMapper<GanttTasksProcess> {


    @Select("select sum(Gtp_Rate) as \"taskRate\" from Gantt_Tasks_Process where Task_Id=#{taskId} and Gtp_State=7")
    Float getGtpRateSum(@Param("taskId") String taskId);


    @Update("update Gantt_Tasks set Task_Progress=#{taskRate},Task_Finish_Time=#{finishTime} where Task_Id=#{taskId}")
    Integer updateGanttTasksProgressAndFinishTime(@Param("taskId") String taskId,@Param("taskRate") Float taskRate,@Param("finishTime") Date finishTime);

    @Update("update Gantt_Tasks set Task_Progress=#{taskRate} where Task_Id=taskId")
    Integer updateGanttTasksProgress(@Param("taskId") String taskId,@Param("taskRate") Float taskRate);
}
