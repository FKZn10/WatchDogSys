package com.jpmsoft.userProducer.service;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.*;

/**
 * 服务内RestTemplate调用
 * 调用 BPM服务  流程管理模块
 */
public interface IFlowRestTemplateService {

    /**
     * 流程管理
     * 抄送记录
     *
     * @param request
     * @return
     */
    BaseResponse getCarbonCopyList(SysCarbonCopyRequest request,String token);

    /**
     * 流程管理
     * 流程实例
     * reuqest.started ==True 则为已启动流程
     *
     * @param request
     * @return
     */
    BaseResponse getFileList(SysFileRequest request,String token);

    /**
     * 流程管理
     * 已部署流程
     *
     * @param request
     * @return
     */
    BaseResponse getFlowList(SysFlowRequest request, String token);

    /**
     * 流程管理
     * 沟通任务
     *
     * @param request
     * @return
     */
    BaseResponse getCoordinationTaskList(SysCoordinationTaskRequest request,String token);

    /**
     * 流程管理
     * 进行中流程
     *
     * @param request
     * @return
     */
    BaseResponse getTaskList(SysTaskRequest request,String token);

    /**
     * 流程管理
     * 沟通记录
     *
     * @param request
     * @return
     */
    BaseResponse getCoordinationProcessList(SysCoordinationProcessRequest request,String token);
}
