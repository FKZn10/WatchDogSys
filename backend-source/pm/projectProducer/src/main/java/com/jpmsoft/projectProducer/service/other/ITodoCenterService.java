package com.jpmsoft.projectProducer.service.other;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.*;


/**
 * 我的事务(PM)
 * 内部转发调用JBPM服务内接口
 */
public interface ITodoCenterService {

    /**
     * 我的事务PM
     * 用户待办流程列表
     *
     * @param userId
     * @param request
     * @return
     */
    BaseResponse getUserTaskList(String userId, UserTaskRequest request);


    /**
     * 我的事务PM
     * 用户已办流程列表
     *
     * @param userId
     * @param request
     * @return
     */
    BaseResponse getUserTaskProcessList(String userId, UserTaskProcessinRequest request);

    /**
     * 我的事务PM
     * 待反馈流程
     *
     * @param userId
     * @param request
     * @return
     */
    BaseResponse getUserCoordinationTaskList(String userId, UserCoordinationTaskRequest request);

    /**
     * 用户抄送流程
     *
     * @param userId  用户ID
     * @param request 请求参数
     * @return
     */
    BaseResponse getUserCarbonCopyList(String userId, UserCarbonCopyRequest request);

    /**
     * 我的事务
     * 流程反馈记录
     *
     * @param userId  用户ID
     * @param request 请求参数
     * @return
     */
    BaseResponse getUserCoordinationProcessList(String userId, UserCoordinationProcessRequest request);

    BaseResponse getUserToDoSummary(String userId);
}
