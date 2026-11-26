package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SysFileRequest;
import com.jpmsoft.common.model.request.SysTaskRequest;
import com.jpmsoft.common.model.request.UserTaskRequest;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.jpbpm.pojo.dto.AgencyStatisticsDto;
import com.jpmsoft.jpbpm.pojo.dto.BatchhandlingTaskDto;
import com.jpmsoft.jpbpm.pojo.dto.GetTaskDto;
import com.jpmsoft.jpbpm.pojo.entity.Flow;
import com.jpmsoft.jpbpm.pojo.entity.FlowRule;
import com.jpmsoft.jpbpm.pojo.entity.Task;
import com.jpmsoft.jpbpm.pojo.entity.TaskProcessin;
import com.jpmsoft.jpbpm.pojo.vo.AgencyStatisticsVo;
import com.jpmsoft.jpbpm.pojo.vo.AutomaticTasks;
import com.jpmsoft.jpbpm.pojo.vo.TaskCoordinationTaskVo;
import com.jpmsoft.jpbpm.pojo.vo.TaskCountVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface TaskService extends IService<Task> {

    /**
     * 获取代办信息
     *
     * @param request
     * @return
     */
    BaseResponse Gettask(GetTaskDto request,String customerId);

    List<Task> getTaskListByFiles(List<String> ids);
    Task GetNewTaskobj(FlowRule r, TaskProcessin his, Task old_task, Flow flow, String nextUser, String ActHour, String taskname,String customerId);

    void deleteTaskByIdOrTaskEntityID(String taskId, String TaskEntityID,String customerId);

    void deleteTaskByTaskEntityID(String TaskEntityID,String customerId);

    void deleteTaskByTaskEntityIDAndTaskFlowAndTaskSendUser(String taskEntityId, String taskFlow, String taskSendUser);

    Integer seleteTaskCountByTaskEntityID(String TaskEntityID,String customerId);

    void updateTaskUserByTaskId(String taskUser, String taskId);

    void updateTaskCoordinationByTaskId(String taskCoordination, String taskId);


    List<Task> getTaskListByTaskIdAndTaskEntityId(String taskId, String taskEntityId,String customerId);

    BaseResponse batchSendTask(BatchhandlingTaskDto requestData);

    Task getById(String id);

    /**
     * 代办列表
     *
     * @param fileid
     * @return
     */
    List<Task> todolists(String fileid,String customerId);


    /**
     * 我的事务(PM)
     * 获取用户代办列表
     *
     * @param userId
     * @param request
     * @return
     */
    BaseResponse getUserTaskList(String userId, UserTaskRequest request,String customerId,boolean isDock);

    /**
     * 流程管理
     * 进行中流程
     *
     * @param request
     * @return
     */
    BaseResponse getTaskList(SysTaskRequest request,String customerId);

    BaseResponse setTaskStatus(String taskid);


    BaseResponse GetFileTask(String fileId, String userId,String customerId);


    Integer getUserTaskListCount(String userId,String customerId);

    BaseResponse getTaskById(String id,String customerId);

    void sendToDoTask(Task task, String tpid, String state, String subState,String  token,String customerId,String tCode,String year);

    /**
     * 统计用户代办数量
     *
     * @param request
     * @return
     */
    List<AgencyStatisticsVo> statisticsUserAgency(AgencyStatisticsDto request,String customerId);


    List<Task> getTaskByEntityIdAndCurrentActId(String entityId,String actId ,String customerId);

    void  deleteTaskByEntityIdAndCurrentActId(String entityId,String actId ,String taskId,String customerId);

    BaseResponse deleteTaskById(String id, String fileId, LogUserInfoVo user);

    BaseResponse  createTask(Task request, LogUserInfoVo user,String customerId,String token,String tCode,String year);


    List<TaskCountVo> getPrescription(Map<String, Object> params);


    List<AutomaticTasks> getAutomaticTasks(Map<String, Object> params);

    BaseResponse  getTaskListByUserId(String userId, UserTaskRequest request, String customerId, boolean isDock);


    void generateReminder(String customerId);
    BaseResponse getUserTaskListCJ(String userId, TaskCoordinationTaskVo request,String customerId);

    BaseResponse getUserTodoList(String uId, SysFileRequest request, String customerId);

    BaseResponse getUserTodoCount(String uId, String customerId);

    BaseResponse getNoDataFlow(Integer state, Boolean voidData);
}
