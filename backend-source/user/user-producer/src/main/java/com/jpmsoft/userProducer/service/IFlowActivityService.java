package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.userProducer.pojo.dto.FlowActivityDto;
import com.jpmsoft.userProducer.pojo.entity.FlowActivity;

public interface IFlowActivityService extends IService<FlowActivity> {


    /**
     * 新增流程步骤信息
     *
     * @param request
     * @return
     */
    void CreateFlowActivity(FlowActivityDto request);


    /**
     * 删除流程步骤信息
     *
     * @param flowId
     * @return
     */
    void DeleteFlowActivity(String flowId);


}
