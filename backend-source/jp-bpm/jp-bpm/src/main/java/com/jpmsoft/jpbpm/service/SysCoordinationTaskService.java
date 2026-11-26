package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SysCoordinationTaskRequest;
import com.jpmsoft.common.model.request.TaskRequest;
import com.jpmsoft.common.model.request.UserCoordinationTaskRequest;
import com.jpmsoft.jpbpm.pojo.dto.AgencyStatisticsDto;
import com.jpmsoft.jpbpm.pojo.dto.InitiateCommunicationDto;
import com.jpmsoft.jpbpm.pojo.dto.StopCommunicatingDto;
import com.jpmsoft.jpbpm.pojo.dto.SubmitCommunicateDto;
import com.jpmsoft.jpbpm.pojo.entity.SysCoordinationTask;
import com.jpmsoft.jpbpm.pojo.vo.AgencyStatisticsVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SysCoordinationTaskService extends IService<SysCoordinationTask> {

    /**
     * 发起沟通
     *
     * @param request
     * @return
     */
    BaseResponse initiateCommunication(InitiateCommunicationDto request,String customerId,String token);

    BaseResponse submitCommunicatecom(InitiateCommunicationDto request,String customerId);

    /**
     * 提交沟通
     *
     * @param request
     * @return
     */
    BaseResponse submitCommunicate(SubmitCommunicateDto request,String customerId);


    /**
     * 停止沟通
     *
     * @param request
     * @return
     */
    BaseResponse stopCommunicating(StopCommunicatingDto request,String customerId);


    /**
     * 沟通列表
     *
     * @param request
     * @return
     */
    BaseResponse tobereadinglist(TaskRequest request);

    /**
     * 我的事务(PM)
     * 获取用户待反馈流程
     *
     * @param userId
     * @param request
     * @return
     */
    BaseResponse getUserCoordinationTaskList(String userId, UserCoordinationTaskRequest request,String customerId);

    /**
     * 获取用户待反馈数量
     *
     * @param userId
     * @return
     */
    Integer getUserCoordinationTaskListCount(String userId,String customerId);

    /**
     * 流程管理
     * 沟通任务
     *
     * @param request
     * @return
     */
    BaseResponse getCoordinationTaskList(SysCoordinationTaskRequest request,String customerId);


    /**
     * 获取用户代办汇总
     *
     * @param userId
     * @return
     */
    BaseResponse getUserToDoSummary(String userId,String customerId);

    List<AgencyStatisticsVo> statisticsUserAgency(AgencyStatisticsDto request,String customerId );



    void  deleteByTaskEntityIDAndTaskFlowAndTaskSendUser(String taskEntityId, String taskFlow, String taskSendUser);



    void  deleteByEntityIdAndCurrentActId(String entityId,String actId ,String taskId,String customerId);


    void deleteByEntityId(String entityId,String customerId);

    void deleteByIdOrTaskEntityID(String taskId, String TaskEntityID,String customerId);

    void deleteByTaskId(String taskId,String customerId);


    BaseResponse deleteById(String  id,String customerId);
}
