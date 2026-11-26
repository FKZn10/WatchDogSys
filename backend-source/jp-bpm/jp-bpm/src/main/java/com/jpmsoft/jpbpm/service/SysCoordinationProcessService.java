package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.FilerecordRequest;
import com.jpmsoft.common.model.request.SysCoordinationProcessRequest;
import com.jpmsoft.common.model.request.UserCoordinationProcessRequest;
import com.jpmsoft.jpbpm.pojo.entity.SysCoordinationProcess;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface SysCoordinationProcessService extends IService<SysCoordinationProcess> {
    /**
     * 沟通记录
     *
     * @param request
     * @return
     */
    BaseResponse havetobereadinglist(FilerecordRequest request);

    /**
     * 我的事务
     * 流程反馈记录
     *
     * @param userId
     * @param request
     * @return
     */
    BaseResponse getUserCoordinationProcessList(String userId, UserCoordinationProcessRequest request,String customerId);


    /**
     * 流程管理
     * 沟通记录
     *
     * @param request
     * @return
     */
    BaseResponse getCoordinationProcessList(SysCoordinationProcessRequest request,String customerId);



    BaseResponse getCoordinationProcessByfileid(FilerecordRequest requst,String customerId);

    List<SysCoordinationProcess>   GetysCoordinationProcessbyFileid(String Fileid,String customerId);
}
