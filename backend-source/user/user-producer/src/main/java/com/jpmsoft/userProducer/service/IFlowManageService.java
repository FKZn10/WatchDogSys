package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.FlowDto;
import com.jpmsoft.userProducer.pojo.entity.Flow;


public interface IFlowManageService extends IService<Flow> {


    /**
     * 获取流程关键字信息列表
     *
     * @param request
     * @return
     */
    BaseResponse getFlowtList(FlowDto request);

    /**
     * 新增或修改流程关键字信息
     *
     * @param request
     * @return
     */
    BaseResponse CreateOrUpdateFlow(FlowDto request);

    /**
     * 删除流程关键字信息
     *
     * @param request
     * @return
     */
    BaseResponse DeleteFlow(FlowDto request);
    BaseResponse  setEnableDisable(String flowId,String enable,String token);

    /**
     * 查找部门、角色、岗位信息
     *
     * @param
     * @return
     */
    BaseResponse getDepartmentResponseList(String token);



    BaseResponse getFlowXmlByFlowId(String flowId);

}
